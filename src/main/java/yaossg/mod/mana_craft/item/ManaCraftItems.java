package yaossg.mod.mana_craft.item;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import yaossg.mod.mana_craft.ManaCraft;
import yaossg.mod.mana_craft.util.Util;

import static net.minecraft.inventory.EntityEquipmentSlot.*;

public class ManaCraftItems {
    private static Item nameAs(Item item, String name) {
        return item.setCreativeTab(ManaCraft.tabMana).setUnlocalizedName(name).setRegistryName(name);
    }
    private static Item newItem(String name) {
        return nameAs(new Item(), name);
    }
    static Item newItemManaArmor(EntityEquipmentSlot slot, String name) {
        return nameAs(new ItemManaArmor(slot), name);
    }
    public static final Item itemBlueShit = newItem("blue_shit");
    public static final Item itemMana = newItem("mana");
    public static final Item itemManaIngot = newItem("mana_ingot");
    public static final Item itemManaNugget = newItem("mana_nugget");
    public static final Item itemManaCoal = nameAs(new Item() {
        @Override
        public int getItemBurnTime(ItemStack itemStack) {
            return itemStack.getItem() == this ? 200 * 64 : 0;
        }
    }, "mana_coal");
    public static final Item itemManaDiamond = newItem("mana_diamond");
    public static final Item itemManaApple = nameAs(new ItemManaApple(), "mana_apple");
    public static final Item itemManaBall = nameAs(new ItemManaBall(),"mana_ball");
    public static final Item itemManaWand = nameAs(new ItemManaWand(), "mana_wand");
    public static final Item itemManaSword = nameAs(new ItemManaTool.ItemManaSword(), "mana_sword");
    public static final Item itemManaPickaxe = nameAs(new ItemManaTool.ItemManaPickaxe(), "mana_pickaxe");
    public static final Item itemManaAxe = nameAs(new ItemManaTool.ItemManaAxe(), "mana_axe");
    public static final Item itemManaShovel = nameAs(new ItemManaTool.ItemManaShovel(), "mana_shovel");
    public static final Item itemManaHoe = nameAs(new ItemManaTool.ItemManaHoe(), "mana_hoe");
    public static final Item itemManaShears = nameAs(new ItemManaTool.ItemManaShears(), "mana_shears");
    public static final Item itemManaHelmet = newItemManaArmor(HEAD,"mana_helmet");
    public static final Item itemManaChestplate = newItemManaArmor(CHEST,"mana_chestplate");
    public static final Item itemManaLeggings = newItemManaArmor(LEGS,"mana_leggings");
    public static final Item itemManaBoots = newItemManaArmor(FEET,"mana_boots");

    public static void init() {
        Util.register(itemBlueShit);
        Util.register(itemMana);
        Util.register(itemManaIngot);
        Util.register(itemManaNugget);
        Util.register(itemManaApple);
        Util.register(itemManaBall);
        Util.register(itemManaCoal);
        Util.register(itemManaDiamond);
        Util.register(itemManaWand);
        Util.register(itemManaSword);
        Util.register(itemManaPickaxe);
        Util.register(itemManaAxe);
        Util.register(itemManaShovel);
        Util.register(itemManaHoe);
        Util.register(itemManaShears);
        Util.register(itemManaHelmet);
        Util.register(itemManaChestplate);
        Util.register(itemManaLeggings);
        Util.register(itemManaBoots);
    }

    @SideOnly(Side.CLIENT)
    public static void clientInit() {
        Util.loadModel(itemBlueShit);
        Util.loadModel(itemMana);
        Util.loadModel(itemManaIngot);
        Util.loadModel(itemManaNugget);
        Util.loadModel(itemManaApple);
        Util.loadModel(itemManaBall);
        Util.loadModel(itemManaCoal);
        Util.loadModel(itemManaDiamond);
        Util.loadModel(itemManaWand);
        Util.loadModel(itemManaSword);
        Util.loadModel(itemManaPickaxe);
        Util.loadModel(itemManaAxe);
        Util.loadModel(itemManaShovel);
        Util.loadModel(itemManaHoe);
        Util.loadModel(itemManaShears);
        Util.loadModel(itemManaHelmet);
        Util.loadModel(itemManaChestplate);
        Util.loadModel(itemManaLeggings);
        Util.loadModel(itemManaBoots);
    }
}
