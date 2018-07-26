package com.github.yaossg.mana_craft.block;

import com.github.yaossg.mana_craft.ManaCraft;
import com.github.yaossg.sausage_core.api.util.registry.IBRegistryManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

import static com.github.yaossg.sausage_core.api.util.common.SausageUtils.lightLevelOf;
import static net.minecraft.item.Item.ToolMaterial;

@SuppressWarnings("unused")
public class ManaCraftBlocks {
    public static final IBRegistryManager manager = new IBRegistryManager(ManaCraft.MODID, ManaCraft.tabMana);
    public static Block addBlock(Material material, ToolMaterial tool, String name) {
        return manager.addBlock(new Block(material) {{
            setHarvestLevel("pickaxe", tool.getHarvestLevel());
        }}, name);
    }

    public static final Block manaBlock = addBlock(Material.ROCK, ToolMaterial.STONE, "mana_block")
            .setHardness(5)
            .setLightLevel(lightLevelOf(7));

    public static final Block manaIngotBlock = addBlock(Material.ROCK, ToolMaterial.IRON, "mana_ingot_block")
            .setHardness(6)
            .setLightLevel(lightLevelOf(10));

    public static final Block manaIngotOre = addBlock(Material.ROCK, ToolMaterial.IRON, "mana_ingot_ore")
            .setHardness(4)
            .setLightLevel(lightLevelOf(5));

    public static final Block manaGlass = manager.addBlock(new BlockGlass(Material.GLASS, false) {{
        setSoundType(SoundType.GLASS);
        setHarvestLevel("pickaxe", ToolMaterial.STONE.getHarvestLevel());
    }}, "mana_glass")
            .setHardness(1)
            .setLightLevel(lightLevelOf(9));

    public static final Block machineFrame = addBlock(Material.IRON, ToolMaterial.IRON, "machine_frame")
            .setHardness(5)
            .setLightLevel(lightLevelOf(11));

    public static final Block manaLantern = manager.addBlock(new Block(Material.GLASS, MapColor.PURPLE), "mana_lantern")
            .setHardness(1.2f)
            .setLightLevel(lightLevelOf(16));

    public static final Block manaOre = manager.addBlock(new BlockManaOre(), "mana_ore");
    public static final Block manaProducer = manager.addBlock(new BlockManaProducer(), "mana_producer");
    public static final Block manaBooster = manager.addBlock(new BlockManaBooster(), "mana_booster");
    public static final Block manaHead = manager.addBlock(new BlockManaHead(), "mana_head");
    public static final Block manaBody = manager.addBlock(new BlockManaBody(), "mana_body");
    public static final Block manaFoot = manager.addBlock(new BlockManaFoot(), "mana_foot");

    public static void init() {
        ManaCraftBlocks.manager.registerAll();
        BlockManaHead.init();
    }
}
