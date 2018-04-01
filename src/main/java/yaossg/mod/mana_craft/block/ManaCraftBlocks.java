package yaossg.mod.mana_craft.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import yaossg.mod.Util;
import yaossg.mod.mana_craft.ManaCraft;

public class ManaCraftBlocks {
    public static final Block blockMana = new BlockBase(Material.ROCK, SoundType.STONE, Util.IRON_PICKAXE, 8).setHardness(4).setUnlocalizedName("mana_block");
    public static final Block blockManaIngot = new BlockBase(Material.ROCK, SoundType.STONE, Util.IRON_PICKAXE, 10).setHardness(5).setUnlocalizedName("mana_ingot_block");
    public static final Block blockManaIngotOre = new BlockBase(Material.ROCK, SoundType.STONE, Util.IRON_PICKAXE, 9).setHardness(4).setUnlocalizedName("mana_ingot_ore");
    public static final Block blockMachineFrame = new BlockBase(Material.IRON, SoundType.STONE, Util.IRON_PICKAXE, 11).setHardness(5).setUnlocalizedName("machine_frame");
    public static final Block blockManaGlass = new BlockGlass(Material.GLASS, false)
        {{ setSoundType(SoundType.GLASS);setHarvestLevel("pickaxe", Util.STONE_PICKAXE); }}
        .setCreativeTab(ManaCraft.tabMana)
        .setLightLevel(Util.getLightLevel(9))
        .setHardness(2)
        .setUnlocalizedName("mana_glass");


    public static final Block blockManaOre = new BlockManaOre();
    public static final Block blockManaProducer = new BlockManaProducer();
    public static final Block blockManaBooster = new BlockManaBooster();

    public static void init() {
        Util.register(blockManaIngot, "mana_ingot_block");
        Util.register(blockManaOre, "mana_ore");
        Util.register(blockManaIngotOre, "mana_ingot_ore");
        Util.register(blockMana,"mana_block");
        Util.register(blockManaGlass,"mana_glass");
        Util.register(blockManaProducer, "mana_producer");
        Util.register(blockManaBooster, "mana_booster");
        Util.register(blockMachineFrame, "machine_frame");
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
