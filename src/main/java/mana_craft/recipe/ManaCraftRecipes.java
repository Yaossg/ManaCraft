package mana_craft.recipe;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import mana_craft.ManaCraft;
import mana_craft.api.registry.MBFuel;
import mana_craft.api.registry.MPRecipe;
import mana_craft.block.ManaCraftBlocks;
import mana_craft.item.ManaCraftItems;
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
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static mana_craft.api.registry.IManaCraftRegistries.*;
import static mana_craft.api.registry.IManaCraftRegistries.instance;
import static net.minecraftforge.fml.common.registry.GameRegistry.addSmelting;

public class ManaCraftRecipes {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public static void init(FMLPreInitializationEvent event) {
        addSmelting(ManaCraftBlocks.manaOre, new ItemStack(ManaCraftItems.mana, 4), 0.6f);
        addSmelting(ManaCraftBlocks.manaIngotOre, new ItemStack(ManaCraftItems.manaIngot), 0.4f);
        addSmelting(ManaCraftBlocks.manaBlock, new ItemStack(ManaCraftItems.manaBall), 0.2f);

        SausageUtils.getPath(ManaCraft.class, "/assets/mana_craft/machines/").ifPresent(root -> {
            instance().addRecipePath(ManaCraft.MODID, root.resolve("recipes"));
            instance().addFuelPath(ManaCraft.MODID, root.resolve("fuels"));
        });
        try {
            Path config = event.getModConfigurationDirectory().toPath().resolve("mana_craft_machines");
            if(!Files.exists(config))
                Files.createDirectories(config);
            Path recipes = config.resolve("recipes");
            if(!Files.exists(recipes))
                Files.createDirectories(recipes);
            instance().addRecipePath(ManaCraft.MODID, recipes);
            Path fuels = config.resolve("fuels");
            if(!Files.exists(fuels))
                Files.createDirectories(fuels);
            instance().addFuelPath(ManaCraft.MODID, fuels);
        } catch (IOException e) {
            ManaCraft.logger.info("Failed to find directories");
        }
    }

    public static void loadAll() {
        instance().recipePathView().forEach((modid, path) -> loadEntries(modid, walk(path), MPRecipe::parse, MP_RECIPES::register));
        ManaCraft.logger.info("loaded {} recipes", MP_RECIPES.view().size());
        instance().fuelPathView().forEach((modid, path) -> loadEntries(modid, walk(path), MBFuel::parse, MB_FUELS::register));
        ManaCraft.logger.info("loaded {} fuels", MB_FUELS.view().size());
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

    private static <T> void loadEntries(String modid, List<Path> paths, BiFunction<JsonContext, JsonObject, T> parser, Consumer<T> consumer) {
        if(paths.isEmpty()) return;
        JsonContext context = new JsonContext(modid);
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
}
