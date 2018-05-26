package yaossg.mod.mana_craft.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import yaossg.mod.mana_craft.ManaCraft;
import yaossg.mod.sausage_core.api.util.IBRegistryManager;
import yaossg.mod.sausage_core.api.util.Utils;

import static net.minecraft.item.Item.ToolMaterial;

public class ManaCraftBlocks {
    public static Block addBlock(Material material, SoundType sound, ToolMaterial tool, String name) {
        return manager.addBlock(new Block(material){{setSoundType(sound);setHarvestLevel("pickaxe", tool.getHarvestLevel());}}, name);
   }

    public static final IBRegistryManager manager = new IBRegistryManager(ManaCraft.MODID, ManaCraft.tabMana);
    public static final Block blockMana = addBlock(Material.ROCK, SoundType.STONE, ToolMaterial.STONE, "mana_block")
            .setHardness(5).setLightLevel(Utils.lightLevelOf(7));
    public static final Block blockManaIngot = addBlock(Material.ROCK, SoundType.STONE, ToolMaterial.IRON, "mana_ingot_block")
            .setHardness(6).setLightLevel(Utils.lightLevelOf(10));
    public static final Block blockManaIngotOre = addBlock(Material.ROCK, SoundType.STONE, ToolMaterial.IRON, "mana_ingot_ore")
            .setHardness(4).setLightLevel(Utils.lightLevelOf(5));
    public static final Block blockMachineFrame = addBlock(Material.IRON, SoundType.STONE, ToolMaterial.IRON, "machine_frame")
            .setHardness(5).setLightLevel(Utils.lightLevelOf(8));
    public static final Block blockManaGlass = manager.addBlock(new BlockGlass(Material.GLASS, false)
        {{ setSoundType(SoundType.GLASS);setHarvestLevel("pickaxe", ToolMaterial.STONE.getHarvestLevel()); }}
            .setHardness(2).setLightLevel(Utils.lightLevelOf(9)), "mana_glass");
    public static final Block blockManaOre = manager.addBlock(new BlockManaOre(), "mana_ore");
    public static final Block blockManaProducer = manager.addBlock(new BlockManaProducer(), "mana_producer");
    public static final Block blockManaBooster = manager.addBlock(new BlockManaBooster(), "mana_booster");

    public static void init() {
        manager.registerAll();
    }

    @SideOnly(Side.CLIENT)
    public static void clientInit() {
        manager.loadAllModel();
    }
}
