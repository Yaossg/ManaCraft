package mana_craft.block;

import mana_craft.ManaCraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import sausage_core.api.util.registry.IBRegistryManager;

import static mana_craft.block.ManaCraftBlocks.Manager.addBlock;
import static mana_craft.block.ManaCraftBlocks.Manager.manager;
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

		static Block addBlock(String name, Material material, MapColor mapColor, ToolMaterial tool) {
			return manager.addBlock(name, new Block(material, mapColor) {{
				setHarvestLevel("pickaxe", tool.getHarvestLevel());
			}});
		}
	}

	public static void init() {
		addBlock("mana_block", Material.ROCK, MapColor.PURPLE, ToolMaterial.STONE)
				.setHardness(5).setLightLevel(lightLevelOf(7));
		manager.addBlock("orichalcum_block", new BlockOrichalcum());
		addBlock("orichalcum_ore", Material.ROCK, MapColor.STONE, ToolMaterial.IRON)
				.setHardness(4).setLightLevel(lightLevelOf(5));
		addBlock("machine_frame", Material.IRON, MapColor.PURPLE, ToolMaterial.IRON)
				.setHardness(5).setLightLevel(lightLevelOf(11));
		manager.addBlock("mana_glass", new BlockManaGlass())
				.setHardness(1).setLightLevel(lightLevelOf(9));
		manager.addBlock("mana_lantern", new Block(Material.GLASS, MapColor.PURPLE))
				.setHardness(1.2f).setLightLevel(lightLevelOf(16));
		manager.addBlock("mana_ore", new BlockManaOre());
		manager.addBlock("mana_producer", new BlockManaProducer());
		manager.addBlock("mana_booster", new BlockManaBooster());
		manager.addBlock("mana_head", new BlockManaHead());
		manager.addBlock("mana_body", new BlockManaBody());
		manager.addBlock("mana_foot", new BlockManaFoot());
		manager.addBlock("mana_obsidian", new BlockManaObsidian());
	}
}
