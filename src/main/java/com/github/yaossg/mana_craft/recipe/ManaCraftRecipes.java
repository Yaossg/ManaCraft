package com.github.yaossg.mana_craft.recipe;

import com.github.yaossg.mana_craft.ManaCraft;
import com.github.yaossg.mana_craft.api.registry.IMBFuel;
import com.github.yaossg.mana_craft.api.registry.IMPRecipe;
import com.github.yaossg.mana_craft.api.registry.ManaCraftRegistries;
import com.github.yaossg.mana_craft.block.ManaCraftBlocks;
import com.github.yaossg.mana_craft.item.ManaCraftItems;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.stream.Stream;

import static com.github.yaossg.mana_craft.api.registry.ManaCraftRegistries.fuel2load;
import static com.github.yaossg.mana_craft.api.registry.ManaCraftRegistries.recipe2load;

public class ManaCraftRecipes {
    static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    static Path root;
    static final String meta = "/assets/mana_craft/machines/";
    static void addSmelting() {
        GameRegistry.addSmelting(ManaCraftBlocks.manaOre, new ItemStack(ManaCraftItems.mana, 4), 0.6f);
        GameRegistry.addSmelting(ManaCraftBlocks.manaIngotOre, new ItemStack(ManaCraftItems.manaIngot), 0.4f);
        GameRegistry.addSmelting(ManaCraftBlocks.manaBlock, new ItemStack(ManaCraftItems.manaBall), 0.2f);
    }
    static Stream<Path> walk(String child) {
        try {
            return Files.walk(root.resolve(child)).filter(path0 -> "json".equals(FilenameUtils.getExtension(path0.toString())));
        } catch (Exception e) {
            return Stream.empty();
        }
    }
    static void initRoot() {
        URI uri;
        try {
            uri = ManaCraftRecipes.class.getResource(meta).toURI();
        } catch (URISyntaxException e) {
            ManaCraft.logger.error("Unexpected Exception: ", e);
            return;
        }
        switch (uri.getScheme()) {
            case "file":
                root = Paths.get(uri);
                break;
            case "jar":
                try {
                    root = FileSystems.newFileSystem(uri, Collections.emptyMap()).getPath(meta);
                } catch (IOException e) {
                    ManaCraft.logger.error("Unexpected Exception: ", e);
                }
        }
    }
    static void addPath2Load() {
        walk("recipes/").forEach(recipe2load::add);
        walk("fuels/").forEach(fuel2load::add);
    }
    public static void preInit() {
        addSmelting();
        initRoot();
        addPath2Load();
    }
    public static void postInit() {
        loadRecipes();
        loadFuels();
    }
    public static void loadRecipes() {
        JsonContext context = new JsonContext(ManaCraft.MODID);
        recipe2load.forEach(path0 -> {
            try (BufferedReader reader = Files.newBufferedReader(path0)) {
                JsonObject json = JsonUtils.fromJson(GSON, reader , JsonObject.class);
                ManaCraftRegistries.addRecipe(IMPRecipe.parse(context, json), path0.toString());
            } catch (IOException e) {
                ManaCraft.logger.error("Unexpected Exception: ", e);
            }
        });
        ManaCraft.logger.info("loaded {} recipes", ManaCraftRegistries.recipes.size());
    }
    public static void loadFuels() {
        JsonContext context = new JsonContext(ManaCraft.MODID);
        fuel2load.forEach(path0 -> {
            try (BufferedReader reader = Files.newBufferedReader(path0)) {
                JsonObject json = JsonUtils.fromJson(GSON, reader, JsonObject.class);
                ManaCraftRegistries.addFuel(IMBFuel.parse(context, json), path0.toString());
            } catch (IOException e) {
                ManaCraft.logger.error("Unexpected Exception: ", e);
            }
        });
        ManaCraft.logger.info("loaded {} fuels", ManaCraftRegistries.fuels.size());
    }


}
