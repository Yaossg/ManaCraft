package yaossg.mod.mana_craft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import yaossg.mod.mana_craft.ManaCraft;

import java.util.Random;

public class ItemManaArmor extends ItemArmor {
    public static ItemArmor.ArmorMaterial MANA_ARMOR = EnumHelper.addArmorMaterial("MANA_ARMOR", ManaCraft.MODID + ":mana",
            20, new int[]{3, 6, 5, 2}, 32, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1f)
            .setRepairItem(new ItemStack(ManaCraftItems.itemManaIngot));

    ItemManaArmor(EntityEquipmentSlot equipmentSlotIn) {
        super(ItemManaArmor.MANA_ARMOR, ItemManaArmor.MANA_ARMOR.ordinal(), equipmentSlotIn);
    }

    private static final Random random = new Random();
    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if(itemStack.getMaxDamage() - itemStack.getItemDamage() < itemStack.getMaxDamage() / 20) {
            player.inventory.armorInventory.set(getEquipmentSlot().ordinal() - 2, ItemStack.EMPTY);
            int base = this.getArmorMaterial().getDamageReductionAmount(armorType);
            InventoryHelper.spawnItemStack(player.world, player.posX, player.posY, player.posZ, new ItemStack(ManaCraftItems.itemMana,
                    base + random.nextInt( 3 * base)));
        }
    }
}
