package mana_craft.entity;

import mana_craft.ManaCraft;
import mana_craft.block.ManaCraftBlocks;
import mana_craft.config.ManaCraftConfig;
import mana_craft.item.ManaCraftItems;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

import static mana_craft.config.ManaCraftConfig.armor;
import static mana_craft.config.ManaCraftConfig.toughness;
import static net.minecraft.entity.SharedMonsterAttributes.*;

public class EntityManaShooter extends EntityGolem implements IRangedAttackMob {
    private static final DataParameter<Boolean> DYING = EntityDataManager.createKey(EntityManaShooter.class, DataSerializers.BOOLEAN);
    private static final int total = 60;
    int since = total;

    public EntityManaShooter(World worldIn) {
        super(worldIn);
        setSize(0.9f, 2.55f);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataManager.register(DYING, Boolean.FALSE);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setBoolean("dying", dataManager.get(DYING));
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        if(compound.getBoolean("dying"))
            dataManager.register(DYING, Boolean.TRUE);
    }

    @Override
    public void onUpdate() {
        if(isEntityAlive()) {
            if(getHealth() < getEntityAttribute(MAX_HEALTH).getBaseValue() * ManaCraftConfig.ratio)
                dataManager.set(DYING, Boolean.TRUE);
            if(dataManager.get(DYING)) {
                if(since == total) playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1, 0.8f);
                if(--since < 0 && !world.isRemote) {
                    setDead();
                    final Random random = getRNG();
                    int times = 36 + random.nextInt(24);
                    for(int i = 0; i < times; ++i) {
                        EntityManaBall ball = EntityManaBall.get(world, this, false)
                                .setDamage(5).setPlayerFriendly(true).setFlame(isBurning());
                        ball.shoot(this, random.nextFloat() * 360 - 180,
                                random.nextFloat() * 360 - 180, 0,
                                EntityManaBall.lowVelocity, EntityManaBall.defaultInaccuracy);
                        world.spawnEntity(ball);
                    }
                    world.createExplosion(this, posX, posY, posZ, 3, false);
                    if(!world.isRemote && random.nextInt(64) == 0)
                        dropItem(ManaCraftItems.mana_record, 1);
                }
            }
        }
        super.onUpdate();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_CREEPER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_CREEPER_DEATH;
    }

    @Override
    protected void initEntityAI() {
        tasks.addTask(1, new EntityAIMoveTowardsTarget(this, 0.8, 16) {
            @Override
            public boolean shouldExecute() {
                return EntityManaShooter.this.dataManager.get(DYING) && super.shouldExecute();
            }
        });
        tasks.addTask(2, new EntityAIAttackRanged(this, 0.35, 35, 10) {
            @Override
            public boolean shouldContinueExecuting() {
                return !EntityManaShooter.this.dataManager.get(DYING) && super.shouldContinueExecuting();
            }
        });
        tasks.addTask(3, new EntityAIMoveToBlock(this, 0.45, 64) {
            @Override
            protected boolean shouldMoveTo(World worldIn, BlockPos pos) {
                return worldIn.getBlockState(pos).getBlock() == ManaCraftBlocks.mana_lantern;
            }
        });
        tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8));
        tasks.addTask(5, new EntityAILookIdle(this));
        targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityLiving.class, 10,
                true, false, IMob.MOB_SELECTOR));
        targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, EntityPlayer.class));
    }

    @Override
    protected int decreaseAirSupply(int air) {
        return air;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(MAX_HEALTH).setBaseValue(120);
        getEntityAttribute(ARMOR).setBaseValue(armor[0] + armor[1] + armor[2] + armor[3]);
        getEntityAttribute(ARMOR_TOUGHNESS).setBaseValue(toughness);
        getEntityAttribute(MOVEMENT_SPEED).setBaseValue(0.4);
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
        EntityManaBall ball = EntityManaBall.get(world, this, false)
                .setDamage(5).setPlayerFriendly(true).setFlame(isBurning());
        double d1 = target.posX - posX;
        double d2 = target.posY + target.getEyeHeight() - 1 - ball.posY;
        double d3 = target.posZ - posZ;
        ball.shoot(d1, d2 + MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2, d3, 1.2f, EntityManaBall.defaultInaccuracy);
        playSound(SoundEvents.ENTITY_SNOWMAN_SHOOT, 1, 1 / (getRNG().nextFloat() * 0.4f + 0.8f));
        world.spawnEntity(ball);
    }

    @Override
    public void setSwingingArms(boolean swingingArms) {}

    public static class Render extends RenderLiving<EntityManaShooter> {
        //copy from ModelCreeper excluding creeper's armor
        static class Model extends ModelBase {
            public ModelRenderer head = new ModelRenderer(this, 0, 0);
            public ModelRenderer body = new ModelRenderer(this, 16, 16);
            public ModelRenderer leg1 = new ModelRenderer(this, 0, 16);
            public ModelRenderer leg2 = new ModelRenderer(this, 0, 16);
            public ModelRenderer leg3 = new ModelRenderer(this, 0, 16);
            public ModelRenderer leg4 = new ModelRenderer(this, 0, 16);

            public Model() {
                head.addBox(-4, -8, -4, 8, 8, 8, 0);
                head.setRotationPoint(0, 6, 0);
                body.addBox(-4, 0, -2, 8, 12, 4, 0);
                body.setRotationPoint(0, 6, 0);
                leg1.addBox(-2, 0, -2, 4, 6, 4, 0);
                leg1.setRotationPoint(-2, 18, 4);
                leg2.addBox(-2, 0, -2, 4, 6, 4, 0);
                leg2.setRotationPoint(2, 18, 4);
                leg3.addBox(-2, 0, -2, 4, 6, 4, 0);
                leg3.setRotationPoint(-2, 18, -4);
                leg4.addBox(-2, 0, -2, 4, 6, 4, 0);
                leg4.setRotationPoint(2, 18, -4);
            }

            public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
                setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
                head.render(scale);
                body.render(scale);
                leg1.render(scale);
                leg2.render(scale);
                leg3.render(scale);
                leg4.render(scale);
            }

            public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
                head.rotateAngleY = netHeadYaw * 0.017453292f;
                head.rotateAngleX = headPitch * 0.017453292f;
                leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount;
                leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f + (float)Math.PI) * 1.4f * limbSwingAmount;
                leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f + (float)Math.PI) * 1.4f * limbSwingAmount;
                leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount;
            }
        }

        public Render(RenderManager renderManager) {
            super(renderManager, new Model(), 0.5f);
        }

        @Override
        protected void preRenderCallback(EntityManaShooter shooter, float partialTickTime) {
            float progress = ((total - shooter.since) + partialTickTime) / total;
            float wave = 1 + MathHelper.sin(progress * 100.0F) * progress * 0.01f;
            progress *= progress;
            float horizontal = (1 + progress * 0.4f) * wave * 1.5f;
            float vertical = (1 + progress * 0.1f) / wave * 1.5f;
            GlStateManager.scale(horizontal, vertical, horizontal);
        }

        private static final ResourceLocation texture = new ResourceLocation(ManaCraft.MODID, "textures/entity/mana_shooter.png");
        @Nullable
        @Override
        protected ResourceLocation getEntityTexture(EntityManaShooter entity) {
            return texture;
        }
    }

}
