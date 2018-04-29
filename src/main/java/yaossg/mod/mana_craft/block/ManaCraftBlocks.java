package yaossg.mod.mana_craft.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import yaossg.mod.mana_craft.util.Util;
import yaossg.mod.mana_craft.ManaCraft;

import static net.minecraft.item.Item.ToolMaterial;

public class ManaCraftBlocks {
    public static Block nameAs(Block block, String name) {
        return block.setCreativeTab(ManaCraft.tabMana).setUnlocalizedName(name).setRegistryName(name);
    }
    public static Block newBlock(Material material, SoundType sound, String name) {
        return nameAs(new Block(material){{setSoundType(sound);}}, name);
    }
    public static Block newBlock(Material material, SoundType sound, ToolMaterial tool, String name) {
        return nameAs(new Block(material){{setSoundType(sound);setHarvestLevel("pickaxe", tool.getHarvestLevel());}}, name);
    }

    public static final Block blockMana = newBlock(Material.ROCK, SoundType.STONE, ToolMaterial.STONE, "mana_block")
            .setHardness(5).setLightLevel(Util.lightAt(7));
    public static final Block blockManaIngot = newBlock(Material.ROCK, SoundType.STONE, ToolMaterial.IRON, "mana_ingot_block")
            .setHardness(6).setLightLevel(Util.lightAt(10));
    public static final Block blockManaIngotOre = newBlock(Material.ROCK, SoundType.STONE, ToolMaterial.IRON, "mana_ingot_ore")
            .setHardness(4).setLightLevel(Util.lightAt(5));
    public static final Block blockMachineFrame = newBlock(Material.IRON, SoundType.STONE, ToolMaterial.IRON, "machine_frame")
            .setHardness(5).setLightLevel(Util.lightAt(8));
    public static final Block blockManaGlass = nameAs(new BlockGlass(Material.GLASS, false)
        {{ setSoundType(SoundType.GLASS);setHarvestLevel("pickaxe", ToolMaterial.STONE.getHarvestLevel()); }}, "mana_glass")
        .setLightLevel(Util.lightAt(9)).setHardness(2);
    public static final Block blockManaOre = nameAs(new BlockManaOre(), "mana_ore");
    public static final Block blockManaProducer = nameAs(new BlockManaProducer(), "mana_producer");
    public static final Block blockManaBooster = nameAs(new BlockManaBooster(), "mana_booster");

    public static void init() {
        Util.register(blockManaIngot);
        Util.register(blockManaOre);
        Util.register(blockManaIngotOre);
        Util.register(blockMana);
        Util.register(blockManaGlass);
        Util.register(blockManaProducer);
        Util.register(blockManaBooster);
        Util.register(blockMachineFrame);
    }

    @SideOnly(Side.CLIENT)
    public static void clientInit() {
        Util.loadModel(blockMana);
        Util.loadModel(blockManaOre);
        Util.loadModel(blockManaIngot);
        Util.loadModel(blockManaIngotOre);
        Util.loadModel(blockManaGlass);
        Util.loadModel(blockManaProducer);
        Util.loadModel(blockManaBooster);
        Util.loadModel(blockMachineFrame);
    }
}
