package mana_craft.recipe;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import mana_craft.ManaCraft;
import mana_craft.api.registry.MBFuel;
import mana_craft.api.registry.MPRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.apache.commons.io.FilenameUtils;
import sausage_core.api.util.common.SausageUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static mana_craft.api.registry.ManaCraftRegistries.MB_FUELS;
import static mana_craft.api.registry.ManaCraftRegistries.MP_RECIPES;
import static mana_craft.block.ManaCraftBlocks.*;
import static mana_craft.item.ManaCraftItems.*;
import static net.minecraftforge.fml.common.registry.GameRegistry.addSmelting;

public class ManaCraftRecipes {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final List<Path> recipePaths = new ArrayList<>();
    private static final List<Path> fuelPaths = new ArrayList<>();
    public static void init(FMLPreInitializationEvent event) {

        SausageUtils.getPath(ManaCraft.class, "/assets/mana_craft/machines/").ifPresent(root -> {
            recipePaths.add(root.resolve("recipes"));
            fuelPaths.add(root.resolve("fuels"));
        });
        try {
            Path config = event.getModConfigurationDirectory().toPath().resolve("mana_craft_machines");
            if(!Files.exists(config))
                Files.createDirectories(config);
            Path recipes = config.resolve("recipes");
            if(!Files.exists(recipes))
                Files.createDirectories(recipes);
            recipePaths.add(recipes);
            Path fuels = config.resolve("fuels");
            if(!Files.exists(fuels))
                Files.createDirectories(fuels);
            fuelPaths.add(fuels);
        } catch (IOException e) {
            ManaCraft.logger.info("Failed to find directories, so no recipes or fuels load from any file", e);
            recipePaths.clear();
            fuelPaths.clear();
        }
    }

    public static void loadAll() {
        int recipes = MP_RECIPES.view().size();
        recipePaths.forEach(path -> loadEntries(walk(path), MPRecipe::parse, MP_RECIPES::register));
        ManaCraft.logger.info("loaded {} recipes from files", MP_RECIPES.view().size() - recipes);
        ManaCraft.logger.info("loaded {} recipes in total", MP_RECIPES.view().size());
        int fuels = MB_FUELS.view().size();
        fuelPaths.forEach(path -> loadEntries(walk(path), MBFuel::parse, MB_FUELS::register));
        ManaCraft.logger.info("loaded {} fuels from files", MB_FUELS.view().size() - fuels);
        ManaCraft.logger.info("loaded {} fuels in total", MB_FUELS.view().size());
    }

    private static List<Path> walk(Path value) {
        try {
            return Files.walk(value)
                    .filter(path -> "json".equals(
                            FilenameUtils.getExtension(path.getFileName().toString())))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    private static <T> void loadEntries(List<Path> paths, BiFunction<JsonContext, JsonObject, T> parser, Consumer<T> consumer) {
        if(paths.isEmpty()) return;
        JsonContext context = new JsonContext(ManaCraft.MODID);
        for (Path path : paths) {
            if("_constants.json".equals(path.getFileName().toString())) {
                try (BufferedReader reader = Files.newBufferedReader(path)) {
                    JsonObject[] json = JsonUtils.fromJson(GSON, reader, JsonObject[].class);
                    ReflectionHelper.findMethod(JsonContext.class, "loadConstants", "loadConstants", JsonObject[].class).invoke(context, (Object) json);
                } catch (Exception e) {
                    ManaCraft.logger.error("Unexpected Exception: ", e);
                }
            }
        }
        paths.removeIf(path -> path.getFileName().toString().startsWith("_"));
        paths.forEach(path -> {
            try (BufferedReader reader = Files.newBufferedReader(path)) {
                JsonObject json = JsonUtils.fromJson(GSON, reader, JsonObject.class);
                consumer.accept(parser.apply(context, json));
            } catch (Exception e) {
                ManaCraft.logger.error("Unexpected Exception: ", e);
            }
        });
    }

    public static void addSmelt() {
        addSmelting(orichalcum_dust, new ItemStack(orichalcum_ingot), 0.05f);
        addSmelting(mana_ore, new ItemStack(mana, 4), 0.3f);
        addSmelting(orichalcum_ore, new ItemStack(orichalcum_ingot), 0.4f);
        addSmelting(mana_block, new ItemStack(mana_ball, 2), 0.2f);
    }
}
