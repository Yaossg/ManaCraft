package yaossg.mod.mana_craft.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import yaossg.mod.Util;
import yaossg.mod.mana_craft.ManaCraft;

public class ManaCraftBlocks {

    public static Block newBlock(Material material, SoundType sound, String name) {
        return nameAs(new Block(material){{setSoundType(sound);}}, name);
    }
    public static Block newBlock(Material material, SoundType sound, int pickaxe, String name) {
        return nameAs(new Block(material){{setSoundType(sound);setHarvestLevel("pickaxe", pickaxe);}}, name);
    }

    public static Block nameAs(Block block, String name) {
        return block.setCreativeTab(ManaCraft.tabMana).setUnlocalizedName(name).setRegistryName(name);
    }

    public static final Block blockMana = newBlock(Material.ROCK, SoundType.STONE, Util.STONE_PICKAXE, "mana_block")
            .setHardness(5).setLightLevel(Util.getLightLevel(7));
    public static final Block blockManaIngot = newBlock(Material.ROCK, SoundType.STONE, Util.IRON_PICKAXE, "mana_ingot_block")
            .setHardness(6).setLightLevel(Util.getLightLevel(10));
    public static final Block blockManaIngotOre = newBlock(Material.ROCK, SoundType.STONE, Util.IRON_PICKAXE, "mana_ingot_ore")
            .setHardness(4).setLightLevel(Util.getLightLevel(5));
    public static final Block blockMachineFrame = newBlock(Material.IRON, SoundType.STONE, Util.IRON_PICKAXE, "machine_frame")
            .setHardness(5).setLightLevel(Util.getLightLevel(8));

    public static final Block blockManaGlass = nameAs(new BlockGlass(Material.GLASS, false)
        {{ setSoundType(SoundType.GLASS);setHarvestLevel("pickaxe", Util.STONE_PICKAXE); }}, "mana_glass")
        .setLightLevel(Util.getLightLevel(9)).setHardness(2);

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
