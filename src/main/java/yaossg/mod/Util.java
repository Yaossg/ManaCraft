package yaossg.mod;

import com.google.common.collect.ImmutableMap;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.critereon.NBTPredicate;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.nbt.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import yaossg.mod.mana_craft.ManaCraft;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Util {
    public static final Random rand = new Random();
    public static final int WOODEN_PICKAXE = 0;
    public static final int STONE_PICKAXE = 1;
    public static final int IRON_PICKAXE = 2;
    public static final int DIAMOND_PICKAXE = 3;
    public static float getLightLevel(int level) {
        return level / 16f;
    }
    public static void register(Item item, String name) {
        ForgeRegistries.ITEMS.register(item.setRegistryName(name));
    }
    public static void register(Block block, String name) {
        ForgeRegistries.BLOCKS.register(block.setRegistryName(name));
        ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
    }
    public static void register(Block block, Item item, String name) {
        ForgeRegistries.BLOCKS.register(block.setRegistryName(name));
        ForgeRegistries.ITEMS.register(item.setRegistryName(block.getRegistryName()));
    }

    public static void loadModel(Item item) {
        loadModel(item, 0);
    }
    public static void loadModel(Item item, int data) {
        ModelLoader.setCustomModelResourceLocation(item, data,
                new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
    public static void loadModel(Block block) {
        loadModel(block, 0);
    }
    public static void loadModel(Block block, int data) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), data,
                new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }

    public static void applyExplosion(World world, Explosion explosion) {
        applyExplosion(world, explosion, true);
    }
    public static void applyExplosion(World world, Explosion explosion, boolean spawnParticles) {
        if (!net.minecraftforge.event.ForgeEventFactory.onExplosionStart(world, explosion)) {
            explosion.doExplosionA();
            explosion.doExplosionB(spawnParticles);
        }
    }

    public static void createThenApplyExplosin(World world, Entity entity, BlockPos pos,
               float size, boolean flaming, boolean damagesTerrain) {
        createThenApplyExplosin(world, entity, pos, size, flaming, damagesTerrain, true);
    }

    public static void createThenApplyExplosin(World world, Entity entity, BlockPos pos,
               float size, boolean flaming, boolean damagesTerrain, List<BlockPos> affectedPositions) {
        createThenApplyExplosin(world, entity, pos, size, flaming, damagesTerrain, affectedPositions, true);
    }
    public static void createThenApplyExplosin(World world, Entity entity, BlockPos pos,
               float size, boolean flaming, boolean damagesTerrain, boolean spawnParticles) {
        applyExplosion(world, new Explosion(world, entity, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5,
                size, flaming, damagesTerrain), spawnParticles);
    }

    public static void createThenApplyExplosin(World world, Entity entity, BlockPos pos,
               float size, boolean flaming, boolean damagesTerrain, List<BlockPos> affectedPositions, boolean spawnParticles) {
        applyExplosion(world, new Explosion(world, entity, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5,
                size, flaming, damagesTerrain, affectedPositions), spawnParticles);
    }

    public static void giveAdvancement(Entity player, String advance) {
        if(player.getServer() != null) {
            MinecraftServer server = player.getServer();
            if(player instanceof EntityPlayerMP) {
                EntityPlayerMP playerMP = (EntityPlayerMP)player;
                Advancement advancement = server.getAdvancementManager().getAdvancement(new ResourceLocation(advance));
                AdvancementProgress progress = playerMP.getAdvancements().getProgress(advancement);
                if (!progress.isDone()) {
                    for (String s : progress.getRemaningCriteria()) {
                        playerMP.getAdvancements().grantCriterion(advancement, s);
                    }
                }
            }
        }
    }
    public static void giveManaCraftAdvancement(Entity player, String advance) {
        giveAdvancement(player, ManaCraft.MODID + ":mana_craft/" + advance);
    }
}