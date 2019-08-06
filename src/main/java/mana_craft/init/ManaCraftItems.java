package mana_craft.init;

import mana_craft.ManaCraft;
import mana_craft.item.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import sausage_core.api.util.registry.SoundRegistryManager;

import static mana_craft.ManaCraft.IB;
import static mana_craft.item.ItemManaTools.*;
import static net.minecraft.inventory.EntityEquipmentSlot.*;
import static sausage_core.api.util.common.SausageUtils.nonnull;

@ObjectHolder(ManaCraft.MODID)
public class ManaCraftItems {
	public static final Item blue_shit = nonnull();
	public static final Item mana = nonnull();
	public static final Item orichalcum_dust = nonnull();
	public static final Item orichalcum_ingot = nonnull();
	public static final Item orichalcum_nugget = nonnull();
	public static final Item mana_coal = nonnull();
	public static final Item mana_diamond = nonnull();
	public static final Item mana_apple = nonnull();
	public static final Item mana_pork = nonnull();
	public static final Item mana_ball = nonnull();
	public static final Item mana_wand = nonnull();
	public static final Item mana_sword = nonnull();
	public static final Item mana_pickaxe = nonnull();
	public static final Item mana_shovel = nonnull();
	public static final Item mana_hoe = nonnull();
	public static final Item mana_shears = nonnull();
	public static final Item mana_helmet = nonnull();
	public static final Item mana_chestplate = nonnull();
	public static final Item mana_leggings = nonnull();
	public static final Item mana_boots = nonnull();
	public static final Item mana_dust = nonnull();
	public static final Item mana_record = nonnull();
	public static final Item mana_rod = nonnull();

	public static void init() {
		IB.addItem("blue_shit", new ItemFood(1, 0.2f, false) {
			@Override
			public int getMaxItemUseDuration(ItemStack stack) {
				return 5;
			}
		});
		IB.addItem("mana", new ItemMana());
		IB.addItem("orichalcum_ingot", new Item() {
			@Override
			public boolean isBeaconPayment(ItemStack stack) {
				return true;
			}
		});
		IB.addItem("orichalcum_nugget");
		IB.addItem("orichalcum_dust");
		IB.addItem("mana_coal", new Item() {
			@Override
			public int getItemBurnTime(ItemStack stack) {
				return 64 * 200;
			}
		});
		IB.addItem("mana_diamond");
		IB.addItem("mana_apple", new ItemManaApple());
		IB.addItem("mana_pork", new ItemManaPork());
		IB.addItem("mana_ball", new ItemManaBall());
		IB.addItem("mana_wand", new ItemManaWand());
		IB.addItem("mana_sword", new ItemManaSword());
		IB.addItem("mana_pickaxe", new ItemManaPickaxe());
		IB.addItem("mana_axe", new ItemManaAxe());
		IB.addItem("mana_shovel", new ItemManaShovel());
		IB.addItem("mana_hoe", new ItemManaHoe());
		IB.addItem("mana_shears", new ItemManaShears());
		IB.addItem("mana_helmet", new ItemManaArmor(HEAD));
		IB.addItem("mana_chestplate", new ItemManaArmor(CHEST));
		IB.addItem("mana_leggings", new ItemManaArmor(LEGS));
		IB.addItem("mana_boots", new ItemManaArmor(FEET));
		IB.addItem("mana_producer_generator", new ItemMPGenerator());
		IB.addItem("mana_dust");
		IB.addItem("mana_record", new ItemRecord("mana",
				new SoundRegistryManager(ManaCraft.MODID).register("record")) {});
		IB.addItem("mana_rod");
	}
}
