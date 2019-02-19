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
    public static final Item mana_axe = nonnull(); //TODO develop usage of this
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
        manager.addItem(new ItemFood(1, 0.2f, false) {
            @Override
            public int getMaxItemUseDuration(ItemStack stack) {
                return 5;
            }
        }, "blue_shit");
        manager.addItem(new ItemMana(), "mana");
        manager.addItem(new Item() {
            @Override
            public boolean isBeaconPayment(ItemStack stack) {
                return true;
            }
        }, "orichalcum_ingot");
        manager.addItem("orichalcum_nugget");
        manager.addItem(new Item() {
            @Override
            public int getItemBurnTime(ItemStack stack) {
                return 64 * 200;
            }
        }, "mana_coal");
        manager.addItem("mana_diamond");
        manager.addItem(new ItemManaApple(), "mana_apple");
        manager.addItem(new ItemManaPork(), "mana_pork");
        manager.addItem(new ItemManaBall(), "mana_ball");
        manager.addItem(new ItemManaWand(), "mana_wand");
        manager.addItem(new ItemManaSword(), "mana_sword");
        manager.addItem(new ItemManaPickaxe(), "mana_pickaxe");
        manager.addItem(new ItemManaAxe(), "mana_axe");
        manager.addItem(new ItemManaShovel(), "mana_shovel");
        manager.addItem(new ItemManaHoe(), "mana_hoe");
        manager.addItem(new ItemManaShears(), "mana_shears");
        manager.addItem(new ItemManaArmor(HEAD), "mana_helmet");
        manager.addItem(new ItemManaArmor(CHEST), "mana_chestplate");
        manager.addItem(new ItemManaArmor(LEGS), "mana_leggings");
        manager.addItem(new ItemManaArmor(FEET), "mana_boots");
        manager.addItem(new ItemMPGenerator(), "mana_producer_generator");
        manager.addItem("mana_dust");
        manager.addItem(new ItemRecord("mana",
                new SoundRegistryManager(ManaCraft.MODID).addSound("record")) {}, "mana_record");
        manager.addItem("mana_rod");
        manager.addItem("orichalcum_dust");
        manager.addItem("orichalcum_plate");
        manager.addItem("orichalcum_gear");
        manager.registerItems();
    }

}
