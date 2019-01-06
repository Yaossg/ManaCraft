package mana_craft.block;

import mana_craft.ManaCraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import sausage_core.api.util.registry.IBRegistryManager;

import static mana_craft.block.ManaCraftBlocks.Manager.*;
import static net.minecraft.item.Item.ToolMaterial;
import static sausage_core.api.util.common.SausageUtils.lightLevelOf;
import static sausage_core.api.util.common.SausageUtils.nonnull;

@ObjectHolder(ManaCraft.MODID)
public class ManaCraftBlocks {
    public static final Block mana_block = nonnull();
    public static final Block orichalcum_block = nonnull();
    public static final Block orichalcum_ore = nonnull();
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

    public interface Manager {
        IBRegistryManager manager = new IBRegistryManager(ManaCraft.MODID, ManaCraft.tabMana);
        static Block addBlock(Material material, ToolMaterial tool, String name) {
            return manager.addBlock(new Block(material) {{
                setHarvestLevel("pickaxe", tool.getHarvestLevel());
            }}, name);
        }

    }
    public static void init() {
        addBlock(Material.ROCK, ToolMaterial.STONE, "mana_block")
                .setHardness(5).setLightLevel(lightLevelOf(7));
        manager.addBlock(new Block(Material.ROCK) {
            { setHarvestLevel("pickaxe", ToolMaterial.IRON.getHarvestLevel()); }

            @Override
            public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon) {
                return true;
            }

        }, "orichalcum_block").setHardness(6).setLightLevel(lightLevelOf(10));

        addBlock(Material.ROCK, ToolMaterial.IRON, "orichalcum_ore")
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
