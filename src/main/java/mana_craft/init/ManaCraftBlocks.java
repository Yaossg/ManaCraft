package mana_craft.init;

import mana_craft.ManaCraft;
import mana_craft.block.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

import static mana_craft.ManaCraft.IB;
import static net.minecraft.item.Item.ToolMaterial;
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

	static Block addBlock(String name, Material material, MapColor mapColor, ToolMaterial tool) {
		return IB.addBlock(name, new Block(material, mapColor) {{
			setHarvestLevel("pickaxe", tool.getHarvestLevel());
		}});
	}

	public static void init() {
		addBlock("mana_block", Material.ROCK, MapColor.PURPLE, ToolMaterial.STONE)
				.setHardness(5).setLightLevel(4 / 15F);
		IB.addBlock("orichalcum_block", new BlockOrichalcum());
		addBlock("orichalcum_ore", Material.ROCK, MapColor.STONE, ToolMaterial.IRON)
				.setHardness(4).setLightLevel(5 / 15F);
		addBlock("machine_frame", Material.IRON, MapColor.PURPLE, ToolMaterial.IRON)
				.setHardness(5).setLightLevel(7 / 15F);
		IB.addBlock("mana_glass", new BlockManaGlass());
		IB.addBlock("mana_lantern", new Block(Material.GLASS, MapColor.PURPLE))
				.setHardness(1).setLightLevel(15 / 15F);
		IB.addBlock("mana_ore", new BlockManaOre());
		IB.addBlock("mana_producer", new BlockManaProducer());
		IB.addBlock("mana_booster", new BlockManaBooster());
		IB.addBlock("mana_head", new BlockManaHead());
		IB.addBlock("mana_body", new BlockManaBody());
		IB.addBlock("mana_foot", new BlockManaFoot());
		IB.addBlock("mana_obsidian", new BlockManaObsidian());
	}
}
