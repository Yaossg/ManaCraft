package yaossg.mod.mana_craft.item;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import yaossg.mod.mana_craft.entity.EntityManaBall;

public class ItemManaBall extends Item {
    ItemManaBall() {
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, new BehaviorProjectileDispense() {
            protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                return new EntityManaBall(worldIn, position.getX(), position.getY(), position.getZ());
            }
            @Override
            protected float getProjectileVelocity() {
                return EntityManaBall.betterVelocity;
            }
            @Override
            protected float getProjectileInaccuracy() {
                return EntityManaBall.defaultInaccuracy;
            }
        });
    }
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack item = playerIn.getHeldItem(handIn);
        if (!playerIn.capabilities.isCreativeMode)
            item.shrink(1);
        if (!worldIn.isRemote) {
            EntityManaBall entity = new EntityManaBall(worldIn, playerIn);
            entity.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0, EntityManaBall.defaultVelocity, EntityManaBall.defaultInaccuracy);
            worldIn.spawnEntity(entity);
        }
        return ActionResult.newResult(EnumActionResult.SUCCESS, item);
    }
}
