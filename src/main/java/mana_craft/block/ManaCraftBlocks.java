package mana_craft.block;

import mana_craft.ManaCraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import sausage_core.api.util.registry.IBRegistryManager;

import static net.minecraft.item.Item.ToolMaterial;
import static sausage_core.api.util.common.SausageUtils.lightLevelOf;

@ObjectHolder(ManaCraft.MODID)
public class ManaCraftBlocks {
    public static final Block mana_block = null;
    public static final Block mana_ingot_block = null;
    public static final Block mana_ingot_ore = null;
    public static final Block mana_glass = null;
    public static final Block machine_frame = null;
    public static final Block mana_lantern = null;
    public static final Block mana_ore = null;
    public static final Block mana_producer = null;
    public static final Block mana_booster = null;
    public static final Block mana_head = null;
    public static final Block mana_body = null;
    public static final Block mana_foot = null;
    public static final Block mana_obsidian = null;
    public interface Manager {
        IBRegistryManager manager = new IBRegistryManager(ManaCraft.MODID, ManaCraft.tabMana);
        static Block addBlock(Material material, ToolMaterial tool, String name) {
            return manager.addBlock(new Block(material) {{
                setHarvestLevel("pickaxe", tool.getHarvestLevel());
            }}, name);
        }

        static void init() {
            addBlock(Material.ROCK, ToolMaterial.STONE, "mana_block")
                    .setHardness(5).setLightLevel(lightLevelOf(7));
            manager.addBlock(new Block(Material.ROCK) {
                { setHarvestLevel("pickaxe", ToolMaterial.IRON.getHarvestLevel()); }

                @Override
                public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon) {
                    return true;
                }

            }, "mana_ingot_block").setHardness(6).setLightLevel(lightLevelOf(10));

            addBlock(Material.ROCK, ToolMaterial.IRON, "mana_ingot_ore")
                    .setHardness(4).setLightLevel(lightLevelOf(5));
            addBlock(Material.IRON, ToolMaterial.IRON, "machine_frame")
                    .setHardness(5).setLightLevel(lightLevelOf(11));
            manager.addBlock(new BlockManaGlass(), "mana_glass")
                    .setHardness(1).setLightLevel(lightLevelOf(9));
            manager.addBlock(new Block(Material.GLASS, MapColor.PURPLE), "mana_lantern")
                    .setHardness(1.2f).setLightLevel(lightLevelOf(16));
            manager.addBlock(new BlockManaOre(), "mana_ore");
            manager.addBlock(new BlockManaProducer(), "mana_producer");
            manager.addBlock(new BlockManaBooster(), "mana_booster");
            manager.addBlock(new BlockManaHead(), "mana_head");
            manager.addBlock(new BlockManaBody(), "mana_body");
            manager.addBlock(new BlockManaFoot(), "mana_foot");
            manager.addBlock(new BlockManaObsidian(), "mana_obsidian");
        }
    }

}
