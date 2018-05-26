package yaossg.mod.mana_craft.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import yaossg.mod.mana_craft.ManaCraft;
import yaossg.mod.sausage_core.api.util.IBRegistryManager;

import static net.minecraft.inventory.EntityEquipmentSlot.*;

public class ManaCraftItems {
    public static final IBRegistryManager manager = new IBRegistryManager(ManaCraft.MODID, ManaCraft.tabMana);
    public static final Item itemBlueShit = manager.addItem(new Item(), "blue_shit");
    public static final Item itemMana = manager.addItem(new Item(), "mana");
    public static final Item itemManaIngot = manager.addItem(new Item(), "mana_ingot");
    public static final Item itemManaNugget = manager.addItem(new Item(), "mana_nugget");
    public static final Item itemManaCoal = manager.addItem(new Item() {
        @Override
        public int getItemBurnTime(ItemStack stack) {
            return stack.getItem() == this ? 200 * 64 : 0;
        }
    }, "mana_coal");
    public static final Item itemManaDiamond = manager.addItem(new Item(), "mana_diamond");
    public static final Item itemManaApple = manager.addItem(new ItemManaApple(), "mana_apple");
    public static final Item itemManaPork = manager.addItem(
            new ItemFood(12,2f,true),
        "mana_pork");
    public static final Item itemManaBall = manager.addItem(new ItemManaBall(),"mana_ball");
    public static final Item itemManaWand = manager.addItem(new ItemManaWand(), "mana_wand");
    public static final Item itemManaSword = manager.addItem(new ItemManaTool.ItemManaSword(), "mana_sword");
    public static final Item itemManaPickaxe = manager.addItem(new ItemManaTool.ItemManaPickaxe(), "mana_pickaxe");
    public static final Item itemManaAxe = manager.addItem(new ItemManaTool.ItemManaAxe(), "mana_axe");
    public static final Item itemManaShovel = manager.addItem(new ItemManaTool.ItemManaShovel(), "mana_shovel");
    public static final Item itemManaHoe = manager.addItem(new ItemManaTool.ItemManaHoe(), "mana_hoe");
    public static final Item itemManaShears = manager.addItem(new ItemManaTool.ItemManaShears(), "mana_shears");
    public static final Item itemManaHelmet = manager.addItem(new ItemManaArmor(HEAD),"mana_helmet");
    public static final Item itemManaChestplate = manager.addItem(new ItemManaArmor(CHEST),"mana_chestplate");
    public static final Item itemManaLeggings = manager.addItem(new ItemManaArmor(LEGS),"mana_leggings");
    public static final Item itemManaBoots = manager.addItem(new ItemManaArmor(FEET),"mana_boots");

    public static void init() {
        manager.registerAll();
    }

    @SideOnly(Side.CLIENT)
    public static void clientInit() {
        manager.loadAllModel();
    }
}
