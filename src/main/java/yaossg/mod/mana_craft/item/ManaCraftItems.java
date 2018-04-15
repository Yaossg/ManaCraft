package yaossg.mod.mana_craft.item;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import yaossg.mod.mana_craft.ManaCraft;
import yaossg.mod.mana_craft.Util;
import yaossg.mod.mana_craft.entity.EntityManaBall;

public class ManaCraftItems {

    private static ItemArmor.ArmorMaterial MANA_ARMOR = EnumHelper.addArmorMaterial("MANA_ARMOR", ManaCraft.MODID + ":mana",
            10, new int[]{3, 6, 5, 2}, 32, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1f);
    static Item newItemManaArmor(EntityEquipmentSlot slot, String name) {
        return nameAs(new ItemArmor(MANA_ARMOR, MANA_ARMOR.ordinal(), slot), name);
    }

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
    public static final Item itemManaBall = nameAs(new Item() {
        @Override
        public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
            ItemStack item = playerIn.getHeldItem(handIn);
            if (!playerIn.capabilities.isCreativeMode)
                item.shrink(1);
            if (!worldIn.isRemote) {
                EntityManaBall entity = new EntityManaBall(worldIn, playerIn);
                entity.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0, 0.4f, 1);
                worldIn.spawnEntity(entity);
            }
            return ActionResult.newResult(EnumActionResult.SUCCESS, item);
        }
    },"mana_ball");
    public static final Item itemManaWand = nameAs(new ItemManaWand(), "mana_wand");
    public static final Item itemManaSword = nameAs(new ItemManaTool.ItemManaSword(), "mana_sword");
    public static final Item itemManaPickaxe = nameAs(new ItemManaTool.ItemManaPickaxe(), "mana_pickaxe");
    public static final Item itemManaAxe = nameAs(new ItemManaTool.ItemManaAxe(), "mana_axe");
    public static final Item itemManaShovel = nameAs(new ItemManaTool.ItemManaShovel(), "mana_shovel");
    public static final Item itemManaHoe = nameAs(new ItemManaTool.ItemManaHoe(), "mana_hoe");
    public static final Item itemManaShears = nameAs(new ItemManaTool.ItemManaShears(), "mana_shears");
    public static final Item itemManaHelmet = newItemManaArmor(EntityEquipmentSlot.HEAD,"mana_helmet");
    public static final Item itemManaChestplate = newItemManaArmor(EntityEquipmentSlot.CHEST,"mana_chestplate");
    public static final Item itemManaLeggings = newItemManaArmor(EntityEquipmentSlot.LEGS,"mana_leggings");
    public static final Item itemManaBoots = newItemManaArmor(EntityEquipmentSlot.FEET,"mana_boots");

    public static void init() {
        Util.register(itemBlueShit);
        Util.register(itemMana);
        Util.register(itemManaIngot);
        Util.register(itemManaNugget);
        Util.register(itemManaApple);
        Util.register(itemManaBall);
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

        OreDictionary.registerOre("dyeLightBlue", itemBlueShit);

        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(itemManaBall, new BehaviorProjectileDispense() {
            protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                return new EntityManaBall(worldIn, position.getX(), position.getY(), position.getZ());
            }
        });
    }

    @SideOnly(Side.CLIENT)
    public static void clientInit() {
        Util.loadModel(itemBlueShit);
        Util.loadModel(itemMana);
        Util.loadModel(itemManaIngot);
        Util.loadModel(itemManaNugget);
        Util.loadModel(itemManaApple);
        Util.loadModel(itemManaBall);
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
