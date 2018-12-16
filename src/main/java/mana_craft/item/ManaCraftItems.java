package mana_craft.item;

import mana_craft.ManaCraft;
import mana_craft.config.ManaCraftConfig;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import sausage_core.api.util.registry.IBRegistryManager;
import sausage_core.api.util.registry.SoundRegistryManager;

import javax.annotation.Nullable;
import java.util.List;

import static mana_craft.item.ItemManaTools.*;
import static net.minecraft.inventory.EntityEquipmentSlot.*;

@ObjectHolder(ManaCraft.MODID)
public class ManaCraftItems {
    public static final Item blue_shit = null;
    public static final Item mana = null;
    public static final Item mana_ingot = null;
    public static final Item mana_nugget = null;
    public static final Item mana_coal = null;
    public static final Item mana_diamond = null;
    public static final Item mana_apple = null;
    public static final Item mana_pork = null;
    public static final Item mana_ball = null;
    public static final Item mana_wand = null;
    public static final Item mana_sword = null;
    public static final Item mana_pickaxe = null;
    public static final Item mana_axe = null;
    public static final Item mana_shovel = null;
    public static final Item mana_hoe = null;
    public static final Item mana_shears = null;
    public static final Item mana_helmet = null;
    public static final Item mana_chestplate = null;
    public static final Item mana_leggings = null;
    public static final Item mana_boots = null;
    public static final Item mana_emerald = null;
    public static final Item mana_producer_generator = null;
    public static final Item mana_dust = null;
    public static final Item mana_record = null;
    public static final Item mana_rod = null;
    public interface Manager {
        IBRegistryManager manager = new IBRegistryManager(ManaCraft.MODID, ManaCraft.tabMana);

        static void init() {
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
            }, "mana_ingot");
            manager.addItem("mana_nugget");
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
            manager.addItem(new Item() {
                @Override
                public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
                    tooltip.add(I18n.format("tooltip.mana_craft.emerald"));
                }
            }, "mana_emerald");
            manager.addItem(new ItemMPGenerator(), "mana_producer_generator");
            manager.addItem("mana_dust");
            manager.addItem(new ItemRecord("mana",
                    new SoundRegistryManager(ManaCraft.MODID).addSound("record")) {}, "mana_record");
            manager.addItem("mana_rod");
            manager.registerItems();
        }
    }
}
