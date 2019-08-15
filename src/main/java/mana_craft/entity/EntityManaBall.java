package mana_craft.entity;

import mana_craft.init.ManaCraftItems;
import mana_craft.item.ItemManaApple;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import static mana_craft.config.ManaCraftConfig.invokeChance;
import static mana_craft.config.ManaCraftConfig.minSpeed;

public class EntityManaBall extends EntityThrowable {
	public static final float lowVelocity = 0.45f;
	public static final float highVelocity = 0.55f;
	public static final float defaultInaccuracy = 1f;
	public float damage = 6;
	public boolean flame = false;
	public boolean playerFriendly = false;

	public EntityManaBall(World worldIn) {
		super(worldIn);
	}

	public EntityManaBall(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn);
	}

	public EntityManaBall(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		damage = compound.getFloat("damage");
		flame = compound.getBoolean("flame");
		playerFriendly = compound.getBoolean("playerFriendly");
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		compound.setFloat("damage", damage);
		compound.setBoolean("flame", flame);
		compound.setBoolean("playerFriendly", playerFriendly);
		super.writeEntityToNBT(compound);
	}

	public EntityManaBall setDamage(float damage) {
		this.damage = damage;
		return this;
	}

	public EntityManaBall setFlame(boolean flame) {
		this.flame = flame;
		return this;
	}

	public EntityManaBall setPlayerFriendly(boolean playerFriendly) {
		this.playerFriendly = playerFriendly;
		return this;
	}

	private boolean canAttack(Entity entity) {
		return !(entity == null
				|| entity == thrower
				|| entity instanceof EntityManaShooter
				|| playerFriendly && entity instanceof EntityPlayer);
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		if (result.entityHit instanceof EntityPig && ItemManaApple.atCorner(result.entityHit)
				&& invokeChance > world.rand.nextFloat())
			ItemManaApple.appleExplosion(thrower, (EntityPig) result.entityHit);
		if (!world.isRemote && result.typeOfHit == RayTraceResult.Type.BLOCK) {
			world.setEntityState(this, (byte) 3);
			setDead();
		}
		if (canAttack(result.entityHit)) {
			if (flame)
				result.entityHit.setFire((int) damage);
			result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, thrower), damage);
		}
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (flame)
			setFire(1);
	}

	public static <T extends Entity> RenderSnowball<T> render(RenderManager renderManager) {
		return new RenderSnowball<>(renderManager, ManaCraftItems.mana_ball, Minecraft.getMinecraft().getRenderItem());
	}

	public static EntityManaBall get(World worldIn, EntityLivingBase throwerIn, boolean floating) {
		return floating ? new Floating(worldIn, throwerIn) : new EntityManaBall(worldIn, throwerIn);
	}

	public static EntityManaBall get(World worldIn, double x, double y, double z, boolean floating) {
		return floating ? new Floating(worldIn, x, y, z) : new EntityManaBall(worldIn, x, y, z);
	}

	public static class Floating extends EntityManaBall {
		public Floating(World worldIn) {
			super(worldIn);
		}

		public Floating(World worldIn, EntityLivingBase throwerIn) {
			super(worldIn, throwerIn);
		}

		public Floating(World worldIn, double x, double y, double z) {
			super(worldIn, x, y, z);
		}

		@Override
		public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
			super.shoot(x, y, z, velocity * 0.5f, inaccuracy);
		}

		@Override
		public EntityManaBall setDamage(float damage) {
			return super.setDamage(damage * 0.5f);
		}

		@Override
		protected float getGravityVelocity() {
			return 0;
		}

		@Override
		public void onUpdate() {
			super.onUpdate();
			if (motionX < minSpeed && motionY < minSpeed && motionZ < minSpeed)
				setDead();
		}
	}
}


