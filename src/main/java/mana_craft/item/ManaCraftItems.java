package mana_craft.item;

import mana_craft.ManaCraft;
import mana_craft.sound.ManaCraftSounds;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import sausage_core.api.util.registry.IBRegistryManager;

import javax.annotation.Nullable;
import java.util.List;

import static mana_craft.item.ItemManaTools.*;
import static net.minecraft.inventory.EntityEquipmentSlot.*;

public class ManaCraftItems {
    public static final IBRegistryManager manager = new IBRegistryManager(ManaCraft.MODID, ManaCraft.tabMana);
    public static final Item blueShit = manager.addItem(new Item(), "blue_shit");
    public static final Item mana = manager.addItem(new Item(), "mana");
    public static final Item manaIngot = manager.addItem(new Item(), "mana_ingot");
    public static final Item manaNugget = manager.addItem(new Item(), "mana_nugget");
    public static final Item manaCoal = manager.addItem(new Item() {
        @Override
        public int getItemBurnTime(ItemStack stack) {
            return 64 * 200;
        }
    }, "mana_coal");
    public static final Item manaDiamond = manager.addItem(new Item(), "mana_diamond");
    public static final Item manaApple = manager.addItem(new ItemManaApple(), "mana_apple");
    public static final Item manaPork = manager.addItem(new ItemManaPork(), "mana_pork");
    public static final Item manaBall = manager.addItem(new ItemManaBall(), "mana_ball");
    public static final Item manaWand = manager.addItem(new ItemManaWand(), "mana_wand");
    public static final Item manaSword = manager.addItem(new ItemManaSword(), "mana_sword");
    public static final Item manaPickaxe = manager.addItem(new ItemManaPickaxe(), "mana_pickaxe");
    public static final Item manaAxe = manager.addItem(new ItemManaAxe(), "mana_axe");
    public static final Item manaShovel = manager.addItem(new ItemManaShovel(), "mana_shovel");
    public static final Item manaHoe = manager.addItem(new ItemManaHoe(), "mana_hoe");
    public static final Item manaShears = manager.addItem(new ItemManaShears(), "mana_shears");
    public static final Item manaHelmet = manager.addItem(new ItemManaArmor(HEAD), "mana_helmet");
    public static final Item manaChestplate = manager.addItem(new ItemManaArmor(CHEST), "mana_chestplate");
    public static final Item manaLeggings = manager.addItem(new ItemManaArmor(LEGS), "mana_leggings");
    public static final Item manaBoots = manager.addItem(new ItemManaArmor(FEET), "mana_boots");
    public static final Item manaEmerald = manager.addItem(new Item() {
        @Override
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            tooltip.add(I18n.format("tooltip.mana_craft.emerald"));
        }
    }, "mana_emerald");
    public static final Item mpGenerator = manager.addItem(new ItemMPGenerator(), "mana_producer_generator");
    public static final Item manaDust = manager.addItem(new Item(), "mana_dust");
    public static final Item manaRecord = manager.addItem(new ItemRecord("mana", ManaCraftSounds.record) {}, "mana_record");
    public static final Item manaRod = manager.addItem(new Item(), "mana_rod");
    public static void init() {
        ManaCraftItems.manager.registerAll();
        ItemManaTools.MANA_TOOL.setRepairItem(new ItemStack(manaIngot));
        ItemManaArmor.MANA_ARMOR.setRepairItem(new ItemStack(manaIngot));
    }

}
