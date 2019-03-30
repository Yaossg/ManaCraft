package mana_craft.item;

import mana_craft.ManaCraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import sausage_core.api.util.registry.IBRegistryManager;
import sausage_core.api.util.registry.SoundRegistryManager;

import static mana_craft.item.ItemManaTools.*;
import static mana_craft.item.ManaCraftItems.Manager.manager;
import static net.minecraft.inventory.EntityEquipmentSlot.*;
import static sausage_core.api.util.common.SausageUtils.nonnull;

@ObjectHolder(ManaCraft.MODID)
public class ManaCraftItems {
	public static final Item blue_shit = nonnull();
	public static final Item mana = nonnull();
	public static final Item orichalcum_dust = nonnull();
	public static final Item orichalcum_gear = nonnull();
	public static final Item orichalcum_plate = nonnull();
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

	public interface Manager {
		IBRegistryManager manager = new IBRegistryManager(ManaCraft.MODID, ManaCraft.tabMana);
	}

	public static void init() {
		manager.addItem("blue_shit", new ItemFood(1, 0.2f, false) {
			@Override
			public int getMaxItemUseDuration(ItemStack stack) {
				return 5;
			}
		});
		manager.addItem("mana", new ItemMana());
		manager.addItem("orichalcum_ingot", new Item() {
			@Override
			public boolean isBeaconPayment(ItemStack stack) {
				return true;
			}
		});
		manager.addItem("orichalcum_nugget");
		manager.addItem("mana_coal", new Item() {
			@Override
			public int getItemBurnTime(ItemStack stack) {
				return 64 * 200;
			}
		});
		manager.addItem("mana_diamond");
		manager.addItem("mana_apple", new ItemManaApple());
		manager.addItem("mana_pork", new ItemManaPork());
		manager.addItem("mana_ball", new ItemManaBall());
		manager.addItem("mana_wand", new ItemManaWand());
		manager.addItem("mana_sword", new ItemManaSword());
		manager.addItem("mana_pickaxe", new ItemManaPickaxe());
		manager.addItem("mana_axe", new ItemManaAxe());
		manager.addItem("mana_shovel", new ItemManaShovel());
		manager.addItem("mana_hoe", new ItemManaHoe());
		manager.addItem("mana_shears", new ItemManaShears());
		manager.addItem("mana_helmet", new ItemManaArmor(HEAD));
		manager.addItem("mana_chestplate", new ItemManaArmor(CHEST));
		manager.addItem("mana_leggings", new ItemManaArmor(LEGS));
		manager.addItem("mana_boots", new ItemManaArmor(FEET));
		manager.addItem("mana_producer_generator", new ItemMPGenerator());
		manager.addItem("mana_dust");
		manager.addItem("mana_record", new ItemRecord("mana",
				new SoundRegistryManager(ManaCraft.MODID).addSound("record")) {});
		manager.addItem("mana_rod");
		manager.addItem("orichalcum_dust");
		manager.addItem("orichalcum_plate");
		manager.addItem("orichalcum_gear");
		manager.registerItems();
	}
}
