package mana_craft.item;

import mana_craft.ManaCraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import sausage_core.api.util.registry.IBRegistryManager;
import sausage_core.api.util.registry.SoundRegistryManager;

import javax.annotation.Nullable;
import java.util.List;

import static mana_craft.item.ItemManaTools.*;
import static net.minecraft.inventory.EntityEquipmentSlot.*;
import static sausage_core.api.util.common.SausageUtils.nonnull;

@ObjectHolder(ManaCraft.MODID)
public class ManaCraftItems {
    public static final Item blue_shit = nonnull();
    public static final Item mana = nonnull();
    public static final Item mana_ingot = nonnull();
    public static final Item mana_nugget = nonnull();
    public static final Item mana_coal = nonnull();
    public static final Item mana_diamond = nonnull();
    public static final Item mana_apple = nonnull();
    public static final Item mana_pork = nonnull();
    public static final Item mana_ball = nonnull();
    public static final Item mana_wand = nonnull();
    public static final Item mana_sword = nonnull();
    public static final Item mana_pickaxe = nonnull();
    public static final Item mana_axe = nonnull();
    public static final Item mana_shovel = nonnull();
    public static final Item mana_hoe = nonnull();
    public static final Item mana_shears = nonnull();
    public static final Item mana_helmet = nonnull();
    public static final Item mana_chestplate = nonnull();
    public static final Item mana_leggings = nonnull();
    public static final Item mana_boots = nonnull();
    public static final Item mana_emerald = nonnull();
    public static final Item mana_producer_generator = nonnull();
    public static final Item mana_dust = nonnull();
    public static final Item mana_record = nonnull();
    public static final Item mana_rod = nonnull();

    public static void init() {
        Manager.manager.addItem(new ItemFood(1, 0.2f, false) {
            @Override
            public int getMaxItemUseDuration(ItemStack stack) {
                return 5;
            }
        }, "blue_shit");
        Manager.manager.addItem(new ItemMana(), "mana");
        Manager.manager.addItem(new Item() {
            @Override
            public boolean isBeaconPayment(ItemStack stack) {
                return true;
            }
        }, "mana_ingot");
        Manager.manager.addItem("mana_nugget");
        Manager.manager.addItem(new Item() {
            @Override
            public int getItemBurnTime(ItemStack stack) {
                return 64 * 200;
            }
        }, "mana_coal");
        Manager.manager.addItem("mana_diamond");
        Manager.manager.addItem(new ItemManaApple(), "mana_apple");
        Manager.manager.addItem(new ItemManaPork(), "mana_pork");
        Manager.manager.addItem(new ItemManaBall(), "mana_ball");
        Manager.manager.addItem(new ItemManaWand(), "mana_wand");
        Manager.manager.addItem(new ItemManaSword(), "mana_sword");
        Manager.manager.addItem(new ItemManaPickaxe(), "mana_pickaxe");
        Manager.manager.addItem(new ItemManaAxe(), "mana_axe");
        Manager.manager.addItem(new ItemManaShovel(), "mana_shovel");
        Manager.manager.addItem(new ItemManaHoe(), "mana_hoe");
        Manager.manager.addItem(new ItemManaShears(), "mana_shears");
        Manager.manager.addItem(new ItemManaArmor(HEAD), "mana_helmet");
        Manager.manager.addItem(new ItemManaArmor(CHEST), "mana_chestplate");
        Manager.manager.addItem(new ItemManaArmor(LEGS), "mana_leggings");
        Manager.manager.addItem(new ItemManaArmor(FEET), "mana_boots");
        Manager.manager.addItem(new Item() {
            @Override
            public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
                tooltip.add(I18n.format("tooltip.mana_craft.emerald"));
            }
        }, "mana_emerald");
        Manager.manager.addItem(new ItemMPGenerator(), "mana_producer_generator");
        Manager.manager.addItem("mana_dust");
        Manager.manager.addItem(new ItemRecord("mana",
                new SoundRegistryManager(ManaCraft.MODID).addSound("record")) {}, "mana_record");
        Manager.manager.addItem("mana_rod");
        Manager.manager.registerItems();
    }

    public interface Manager {
        IBRegistryManager manager = new IBRegistryManager(ManaCraft.MODID, ManaCraft.tabMana);

    }
}
