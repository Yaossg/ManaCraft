package com.github.yaossg.mana_craft.recipe;

import com.github.yaossg.mana_craft.ManaCraft;
import com.github.yaossg.mana_craft.block.ManaCraftBlocks;
import com.github.yaossg.mana_craft.item.ManaCraftItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.github.yaossg.mana_craft.api.registry.ManaCraftRegistries.instance;
import static net.minecraftforge.fml.common.registry.GameRegistry.addSmelting;

public class ManaCraftRecipes {
    public static void init(FMLPreInitializationEvent event) {
        addSmelting(ManaCraftBlocks.manaOre, new ItemStack(ManaCraftItems.mana, 4), 0.6f);
        addSmelting(ManaCraftBlocks.manaIngotOre, new ItemStack(ManaCraftItems.manaIngot), 0.4f);
        addSmelting(ManaCraftBlocks.manaBlock, new ItemStack(ManaCraftItems.manaBall), 0.2f);

        instance().getPath(ManaCraft.class, "/assets/mana_craft/machines/").ifPresent(root -> {
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
}
