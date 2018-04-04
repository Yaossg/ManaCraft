package yaossg.mod.mana_craft.item;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import yaossg.mod.Util;
import yaossg.mod.mana_craft.ManaCraft;

public class ManaCraftItems {

    private static Item newItem(String name) {
        return nameAs(new Item(), name);
    }
    private static Item nameAs(Item item, String name) {
        return item.setCreativeTab(ManaCraft.tabMana).setUnlocalizedName(name).setRegistryName(name);
    }

    public static final Item itemBlueShit = newItem("blue_shit");
    public static final Item itemMana = newItem("mana");
    public static final Item itemManaIngot = newItem("mana_ingot");
    public static final Item itemManaNugget = newItem("mana_nugget");
    public static final Item itemManaApple = nameAs(new ItemManaApple(), "mana_apple");
    public static final Item itemManaSword = nameAs(new ItemManaTool.ItemManaSword(), "mana_sword");
    public static final Item itemManaPickaxe = nameAs(new ItemManaTool.ItemManaPickaxe(), "mana_pickaxe");
    public static final Item itemManaAxe = nameAs(new ItemManaTool.ItemManaAxe(), "mana_axe");
    public static final Item itemManaShovel = nameAs(new ItemManaTool.ItemManaShovel(), "mana_shovel");
    public static final Item itemManaHoe = nameAs(new ItemManaTool.ItemManaHoe(), "mana_hoe");
    public static final Item itemManaShears = nameAs(new ItemManaTool.ItemManaShears(), "mana_shears");
    public static final Item itemManaHelmet = nameAs(new ItemManaArmor(EntityEquipmentSlot.HEAD),"mana_helmet");
    public static final Item itemManaChestplate = nameAs(new ItemManaArmor(EntityEquipmentSlot.CHEST),"mana_chestplate");
    public static final Item itemManaLeggings = nameAs(new ItemManaArmor(EntityEquipmentSlot.LEGS),"mana_leggings");
    public static final Item itemManaBoots = nameAs(new ItemManaArmor(EntityEquipmentSlot.FEET),"mana_boots");

    public static void init() {
        Util.register(itemBlueShit);
        Util.register(itemMana);
        Util.register(itemManaIngot);
        Util.register(itemManaNugget);
        Util.register(itemManaApple);
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

        OreDictionary.registerOre("dyeLightBlue", itemBlueShit);

    }
    @SideOnly(Side.CLIENT)
    public static void clientInit() {
        Util.loadModel(itemBlueShit);
        Util.loadModel(itemMana);
        Util.loadModel(itemManaIngot);
        Util.loadModel(itemManaNugget);
        Util.loadModel(itemManaApple);
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
