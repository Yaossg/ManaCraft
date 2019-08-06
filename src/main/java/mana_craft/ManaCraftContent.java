package mana_craft;

import mana_craft.api.registry.ManaCraftRegistries;
import mana_craft.block.BlockManaHead;
import mana_craft.config.ManaCraftConfig;
import mana_craft.entity.EntityManaBall;
import mana_craft.entity.EntityManaShooter;
import mana_craft.init.ManaCraftVillagers;
import mana_craft.init.*;
import mana_craft.inventory.ManaCraftGUIs;
import mana_craft.item.ItemManaArmor;
import mana_craft.item.ItemManaTools;
import mana_craft.loot.ManaCraftLoots;
import mana_craft.potion.PotionManaEvoker;
import mana_craft.tile.TileManaBooster;
import mana_craft.tile.TileManaProducer;
import mana_craft.world.gen.ManaCraftWorldGens;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import sausage_core.api.core.ienum.IEnumGUIHandler;
import sausage_core.api.core.plugin.PluginLoader;
import sausage_core.api.registry.SCFRecipeManager;
import sausage_core.api.util.client.Colors;
import sausage_core.api.util.common.SausageUtils;

import static mana_craft.ManaCraft.*;
import static mana_craft.ManaCraft.MODID;
import static mana_craft.init.ManaCraftBlocks.*;
import static mana_craft.init.ManaCraftItems.*;
import static net.minecraftforge.fml.common.registry.EntityEntryBuilder.create;
import static net.minecraftforge.fml.common.registry.GameRegistry.addSmelting;
import static net.minecraftforge.oredict.OreDictionary.registerOre;
import static sausage_core.api.util.common.Conversions.To.item;
import static sausage_core.api.util.registry.IPotionTypeLoader.*;
import static sausage_core.api.util.registry.IPotionTypeLoader.simpleVanilla;

public class ManaCraftContent {
	static final PluginLoader pluginPrimer = new PluginLoader(MODID);
	private static int nextEntityID = 0;

	static void initEntities() {
		ForgeRegistries.ENTITIES.registerAll(
				create().entity(EntityManaBall.class)
						.id("mana_ball", nextEntityID++).name("")
						.tracker(64, 8, true).build(),
				create().entity(EntityManaBall.Floating.class)
						.id("mana_floating_ball", nextEntityID++).name("")
						.tracker(64, 10, true).build(),
				create().entity(EntityManaShooter.class)
						.id("mana_shooter", nextEntityID++)
						.name("mana_craft.ManaShooter")
						.tracker(64, 3, true)
						.egg(Colors.MAGENTA, Colors.DIM_GRAY).build()
		);
	}

	public static void initRecipes() {
		new ManaCraftRegistries();
		SausageUtils.getPath(ManaCraftRegistries.class, "/assets/mana_craft/defaults/").ifPresent(root -> {
			SCFRecipeManager.where(new ResourceLocation(MODID, "mp_recipe")).addDefaults(MODID, root.resolve("recipes"));
			SCFRecipeManager.where(new ResourceLocation(MODID, "mb_fuel")).addDefaults(MODID, root.resolve("fuels"));
		});
	}

	public static void preInit() {
		ManaCraftBlocks.init();
		ManaCraftItems.init();
		ManaCraftBiomes.preInit();
		ManaCraftEnchantments.init();
		initEntities();
		Potion mana_evoker = P.register("mana_evoker", new PotionManaEvoker());
		P.register(mana_evoker, simpleVanilla(VanillaBaseDurations.LONG,
				() -> Ingredient.fromItems(mana_apple), VanillaVariant.LONG, VanillaVariant.STRONG,
				ISimpleVariant.of("mega", d -> d * 5 / 2, a -> a + 2, (t, u) -> PotionHelper.addMix(
						PotionTypes.THICK, Ingredient.fromItems(item(mana_foot)), u))));
		ManaCraftVillagers.preInit();
		initRecipes();
		IEnumGUIHandler.register(instance, ManaCraftGUIs.values());
		if (ManaCraftConfig.ticPlugin)
			pluginPrimer.register("tconstruct", "mana_craft.plugin.PluginTConstruct");
	}

	public static void initOre() {
		registerOre("dye", blue_shit);
		registerOre("oreOrichalcum", orichalcum_ore);
		registerOre("blockOrichalcum", orichalcum_block);
		registerOre("ingotOrichalcum", orichalcum_ingot);
		registerOre("dustOrichalcum", orichalcum_dust);
		registerOre("nuggetOrichalcum", orichalcum_nugget);
		registerOre("dyeLightBlue", blue_shit);
		registerOre("blockGlass", mana_glass);
		registerOre("blockGlassHardened", mana_glass);
		registerOre("record", mana_record);
	}

	public static void initSmelting() {
		addSmelting(orichalcum_dust, new ItemStack(orichalcum_ingot), 0.05f);
		addSmelting(mana_ore, new ItemStack(mana, 4), 0.3f);
		addSmelting(orichalcum_ore, new ItemStack(orichalcum_ingot), 0.4f);
		addSmelting(mana_block, new ItemStack(mana_ball, 2), 0.2f);
	}

	public static void init() {
		if (ManaCraftConfig.loot)
			ManaCraftLoots.init();
		ManaCraftVillagers.init();
		ManaCraftWorldGens.init();
		ManaCraftBiomes.init();
		initOre();
		initSmelting();
		BlockManaHead.init();
		ItemManaTools.MANA_TOOL.setRepairItem(new ItemStack(orichalcum_ingot));
		ItemManaArmor.MANA_ARMOR.setRepairItem(new ItemStack(orichalcum_ingot));
		SausageUtils.registerTileEntities(MODID, TileManaProducer.class, TileManaBooster.class);
		TileManaProducer.init();
		pluginPrimer.execute();
	}
}