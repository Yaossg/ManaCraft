package yaossg.mod.mana_craft.entity;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import yaossg.mod.mana_craft.Config;
import yaossg.mod.mana_craft.item.ItemManaApple;
import yaossg.mod.mana_craft.item.ManaCraftItems;

import java.util.Random;

public class EntityManaBall extends EntityThrowable {
    public EntityManaBall(World worldIn)
    {
        super(worldIn);
    }

    public EntityManaBall(World worldIn, EntityLivingBase throwerIn)
    {
        super(worldIn, throwerIn);
    }

    public EntityManaBall(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    private static final Random random = new Random();

    @Override
    protected void onImpact(RayTraceResult result) {
        if (!world.isRemote) {
            if (result.entityHit instanceof EntityPig && Config.PES > 0 && Config.MBI && random.nextInt(16) == 0) {
                ItemManaApple.appleExplosin((EntityPig) result.entityHit);
                this.world.setEntityState(this, (byte) 3);
                this.setDead();
            }
        }
        if (result.entityHit != null) {
            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 8);
            result.entityHit.setFire(2);
        }
    }

    static class Render extends RenderSnowball<EntityManaBall> {

        public Render(RenderManager renderManagerIn) {
            super(renderManagerIn, ManaCraftItems.itemManaBall, Minecraft.getMinecraft().getRenderItem());
        }
    }
}


