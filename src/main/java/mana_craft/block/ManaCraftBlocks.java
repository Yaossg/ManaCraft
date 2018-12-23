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
import static sausage_core.api.util.common.SausageUtils.nonnull;

@ObjectHolder(ManaCraft.MODID)
public class ManaCraftBlocks {
    public static final Block mana_block = nonnull();
    public static final Block mana_ingot_block = nonnull();
    public static final Block mana_ingot_ore = nonnull();
    public static final Block mana_glass = nonnull();
    public static final Block machine_frame = nonnull();
    public static final Block mana_lantern = nonnull();
    public static final Block mana_ore = nonnull();
    public static final Block mana_producer = nonnull();
    public static final Block mana_booster = nonnull();
    public static final Block mana_head = nonnull();
    public static final Block mana_body = nonnull();
    public static final Block mana_foot = nonnull();
    public static final Block mana_obsidian = nonnull();

    public static void init() {
        Manager.addBlock(Material.ROCK, ToolMaterial.STONE, "mana_block")
                .setHardness(5).setLightLevel(lightLevelOf(7));
        Manager.manager.addBlock(new Block(Material.ROCK) {
            { setHarvestLevel("pickaxe", ToolMaterial.IRON.getHarvestLevel()); }

            @Override
            public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon) {
                return true;
            }

        }, "mana_ingot_block").setHardness(6).setLightLevel(lightLevelOf(10));

        Manager.addBlock(Material.ROCK, ToolMaterial.IRON, "mana_ingot_ore")
                .setHardness(4).setLightLevel(lightLevelOf(5));
        Manager.addBlock(Material.IRON, ToolMaterial.IRON, "machine_frame")
                .setHardness(5).setLightLevel(lightLevelOf(11));
        Manager.manager.addBlock(new BlockManaGlass(), "mana_glass")
                .setHardness(1).setLightLevel(lightLevelOf(9));
        Manager.manager.addBlock(new Block(Material.GLASS, MapColor.PURPLE), "mana_lantern")
                .setHardness(1.2f).setLightLevel(lightLevelOf(16));
        Manager.manager.addBlock(new BlockManaOre(), "mana_ore");
        Manager.manager.addBlock(new BlockManaProducer(), "mana_producer");
        Manager.manager.addBlock(new BlockManaBooster(), "mana_booster");
        Manager.manager.addBlock(new BlockManaHead(), "mana_head");
        Manager.manager.addBlock(new BlockManaBody(), "mana_body");
        Manager.manager.addBlock(new BlockManaFoot(), "mana_foot");
        Manager.manager.addBlock(new BlockManaObsidian(), "mana_obsidian");
    }

    public interface Manager {
        IBRegistryManager manager = new IBRegistryManager(ManaCraft.MODID, ManaCraft.tabMana);
        static Block addBlock(Material material, ToolMaterial tool, String name) {
            return manager.addBlock(new Block(material) {{
                setHarvestLevel("pickaxe", tool.getHarvestLevel());
            }}, name);
        }

    }

}
