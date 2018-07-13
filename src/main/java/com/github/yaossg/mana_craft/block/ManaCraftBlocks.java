package com.github.yaossg.mana_craft.block;

import com.github.yaossg.mana_craft.ManaCraft;
import com.github.yaossg.sausage_core.api.util.common.SausageUtils;
import com.github.yaossg.sausage_core.api.util.registry.IBRegistryManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import static net.minecraft.item.Item.ToolMaterial;

public class ManaCraftBlocks {
    public static Block addBlock(Material material, ToolMaterial tool, String name) {
        return manager.addBlock(new Block(material) {{
            setHarvestLevel("pickaxe", tool.getHarvestLevel());
        }}, name);
    }

    public static final IBRegistryManager manager = new IBRegistryManager(ManaCraft.MODID, ManaCraft.tabMana);

    public static final Block manaBlock = addBlock(Material.ROCK, ToolMaterial.STONE, "mana_block")
            .setHardness(5f).setLightLevel(SausageUtils.lightLevelOf(7));
    public static final Block manaIngotBlock = addBlock(Material.ROCK, ToolMaterial.IRON, "mana_ingot_block")
            .setHardness(6f).setLightLevel(SausageUtils.lightLevelOf(10));
    public static final Block manaIngotOre = addBlock(Material.ROCK, ToolMaterial.IRON, "mana_ingot_ore")
            .setHardness(4f).setLightLevel(SausageUtils.lightLevelOf(5));
    public static final Block manaGlass = manager.addBlock(new BlockGlass(Material.GLASS, false) {{
        setSoundType(SoundType.GLASS);
        setHarvestLevel("pickaxe", ToolMaterial.STONE.getHarvestLevel());
    }}.setHardness(1f).setLightLevel(SausageUtils.lightLevelOf(9)), "mana_glass");
    public static final Block manaOre = manager.addBlock(new BlockManaOre(), "mana_ore");
    public static final Block machineFrame = addBlock(Material.IRON, ToolMaterial.IRON, "machine_frame")
            .setHardness(5f).setLightLevel(SausageUtils.lightLevelOf(8));
    public static final Block manaProducer = manager.addBlock(new BlockManaProducer(), "mana_producer");
    public static final Block manaBooster = manager.addBlock(new BlockManaBooster(), "mana_booster");
}
