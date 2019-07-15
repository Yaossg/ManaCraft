package mana_craft.guide;

import amerifrance.guideapi.api.GuideAPI;
import amerifrance.guideapi.api.GuideBook;
import amerifrance.guideapi.api.IGuideBook;
import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.BookBinder;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.category.CategoryItemStack;
import amerifrance.guideapi.entry.EntryItemStack;
import amerifrance.guideapi.page.PageText;
import mana_craft.ManaCraft;
import mana_craft.init.ManaCraftBlocks;
import mana_craft.init.ManaCraftItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sausage_core.api.util.client.Colors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

@GuideBook
public class ManaCraftGuide implements IGuideBook {
	public static Book book;
	public static ItemStack stack;

	private void add(Map<ResourceLocation, EntryAbstract> entries, String category, String entry, ItemStack icon, IPage... pages) {
		String name = String.join(".", "guide", ManaCraft.MODID, category, entry);
		entries.put(new ResourceLocation(category, entry), new EntryItemStack(Arrays.asList(pages), name, icon));
	}

	@Nullable
	@Override
	public Book buildBook() {
		PageText empty = new PageText("Right here in the darkness there's nothing left for you to do");

		ResourceLocation guide = new ResourceLocation(ManaCraft.MODID, "guide");
		List<CategoryAbstract> categories = new ArrayList<>();

		Map<ResourceLocation, EntryAbstract> origin = new LinkedHashMap<>();

		add(origin, "origin", "power_of_nature", new ItemStack(ManaCraftItems.mana), empty);
		add(origin, "origin", "hardly_hardened", new ItemStack(ManaCraftItems.orichalcum_ingot), empty);
		add(origin, "origin", "deep_in_the_dark", new ItemStack(ManaCraftItems.mana_shears), empty);
		add(origin, "origin", "get_in_gear", new ItemStack(ManaCraftItems.mana_chestplate), empty);
		add(origin, "origin", "wonderful_wand", new ItemStack(ManaCraftItems.mana_wand), empty);

		categories.add(new CategoryItemStack(origin, "guide.mana_craft.CATEGORY.origin", new ItemStack(ManaCraftItems.mana)));

		Map<ResourceLocation, EntryAbstract> vector = new LinkedHashMap<>();

		add(vector, "vector", "ancient_magician", new ItemStack(Items.GLASS_BOTTLE), empty);
		add(vector, "vector", "around_world", new ItemStack(ManaCraftBlocks.mana_lantern), empty);
		add(vector, "vector", "like_a_reactor", new ItemStack(ManaCraftBlocks.mana_producer), empty);
		add(vector, "vector", "let_it_swifter", new ItemStack(ManaCraftBlocks.mana_booster), empty);
		add(vector, "vector", "mouthful", new ItemStack(ManaCraftBlocks.mana_head), empty);

		categories.add(new CategoryItemStack(vector, "guide.mana_craft.CATEGORY.vector", new ItemStack(ManaCraftItems.mana_rod)));

		Map<ResourceLocation, EntryAbstract> misc = new LinkedHashMap<>();

		add(misc, "misc", "blue_or_blur", new ItemStack(ManaCraftItems.blue_shit), empty);
		add(misc, "misc", "tinkers_support", new ItemStack(Blocks.FURNACE), empty);
		add(misc, "misc", "piggy_mana", new ItemStack(ManaCraftItems.mana_pork), empty);
		add(misc, "misc", "declaration", new ItemStack(Items.PAPER));
		add(misc, "misc", "known_issue", new ItemStack(Blocks.BARRIER));

		categories.add(new CategoryItemStack(misc, "guide.mana_craft.CATEGORY.misc", new ItemStack(ManaCraftItems.mana_dust)));

		BookBinder binder = new BookBinder(guide);
		binder.setColor(Colors.MAGENTA);
		binder.setGuideTitle("guide.mana_craft.title");
		binder.setItemName("guide.mana_craft.title");
		binder.setCreativeTab(ManaCraft.TAB);
		binder.setSpawnWithBook();
		categories.forEach(binder::addCategory);
		return book = binder.build();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleModel(@Nonnull ItemStack bookStack) {
		GuideAPI.setModel(book, new ResourceLocation(ManaCraft.MODID, "guide"), "inventory");
	}
}
