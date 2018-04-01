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
    public static class ItemBase extends Item {{
        this.setCreativeTab(ManaCraft.tabMana);
    }}
    public static final Item itemBlueShit = new ItemBase().setUnlocalizedName("blue_shit");
    public static final Item itemMana = new ItemBase().setUnlocalizedName("mana");
    public static final Item itemManaIngot = new ItemBase().setUnlocalizedName("mana_ingot");
    public static final Item itemManaNugget = new ItemBase().setUnlocalizedName("mana_nugget");
    public static final Item itemManaApple = new ItemManaApple();
    public static final Item itemManaSword = new ItemManaTool.ItemManaSword();
    public static final Item itemManaPickaxe = new ItemManaTool.ItemManaPickaxe();
    public static final Item itemManaAxe = new ItemManaTool.ItemManaAxe();
    public static final Item itemManaShovel = new ItemManaTool.ItemManaShovel();
    public static final Item itemManaHoe = new ItemManaTool.ItemManaHoe();
    public static final Item itemManaShears = new ItemManaTool.ItemManaShears();
    public static final Item itemManaHelmet = new ItemManaArmor(EntityEquipmentSlot.HEAD).setUnlocalizedName("mana_helmet");
    public static final Item itemManaChestplate = new ItemManaArmor(EntityEquipmentSlot.CHEST).setUnlocalizedName("mana_chestplate");
    public static final Item itemManaLeggings = new ItemManaArmor(EntityEquipmentSlot.LEGS).setUnlocalizedName("mana_leggings");
    public static final Item itemManaBoots = new ItemManaArmor(EntityEquipmentSlot.FEET).setUnlocalizedName("mana_boots");

    public static void init() {
        Util.register(itemBlueShit,"blue_shit");
        OreDictionary.registerOre("dyeLightBlue", itemBlueShit);
        Util.register(itemMana, "mana");
        Util.register(itemManaIngot, "mana_ingot");
        Util.register(itemManaNugget, "mana_nugget");
        Util.register(itemManaApple, "mana_apple");
        Util.register(itemManaSword, "mana_sword");
        Util.register(itemManaPickaxe, "mana_pickaxe");
        Util.register(itemManaAxe, "mana_axe");
        Util.register(itemManaShovel, "mana_shovel");
        Util.register(itemManaHoe, "mana_hoe");
        Util.register(itemManaShears, "mana_shears");
        Util.register(itemManaHelmet, "mana_helmet");
        Util.register(itemManaChestplate, "mana_chestplate");
        Util.register(itemManaLeggings, "mana_leggings");
        Util.register(itemManaBoots, "mana_boots");

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
