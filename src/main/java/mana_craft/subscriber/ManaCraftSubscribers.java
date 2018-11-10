package mana_craft.subscriber;

import mana_craft.ManaCraft;
import mana_craft.block.ManaCraftBlocks;
import mana_craft.config.ManaCraftConfig;
import mana_craft.enchantment.EnchantmentFloating;
import mana_craft.enchantment.EnchantmentManaEvoker;
import mana_craft.enchantment.EnchantmentManaRecycler;
import mana_craft.enchantment.ManaCraftEnchantments;
import mana_craft.entity.EntityManaBall;
import mana_craft.entity.EntityManaShooter;
import mana_craft.item.ManaCraftItems;
import mana_craft.potion.PotionManaEvoker;
import mana_craft.world.biome.BiomeMana;
import mana_craft.world.biome.BiomeManaChaos;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import sausage_core.api.util.registry.SoundRegistryManager;

import java.awt.*;

import static net.minecraftforge.fml.common.registry.EntityEntryBuilder.create;

@Mod.EventBusSubscriber(modid = ManaCraft.MODID)
public class ManaCraftSubscribers {
    public static void init() {
        MinecraftForge.EVENT_BUS.register(ManaToolSubscriber.class);
        if(ManaCraftConfig.dropManaChance > 0)
            MinecraftForge.EVENT_BUS.register(ManaDropSubscriber.class);
    }

    @SubscribeEvent
    public static void addBlock(RegistryEvent.Register<Block> event) {
        ManaCraftBlocks.Manager.init();
        ManaCraftBlocks.Manager.manager.registerBlocks();
    }

    @SubscribeEvent
    public static void addItems(RegistryEvent.Register<Item> event) {
        SoundRegistryManager manager = new SoundRegistryManager(ManaCraft.MODID);
        ManaCraftItems.Manager.init(manager.addSound("record"));
        ManaCraftBlocks.Manager.manager.registerItems();
    }

    @SubscribeEvent
    public static void addBiomes(RegistryEvent.Register<Biome> event) {
        event.getRegistry().registerAll(
                BiomeMana.get().setRegistryName("mana"),
                BiomeMana.getHills().setRegistryName("mana_hills"),
                BiomeManaChaos.get().setRegistryName("mana_chaos"),
                BiomeManaChaos.getHills().setRegistryName("mana_chaos_hills"));
    }

    @SubscribeEvent
    public static void addEnchantments(RegistryEvent.Register<Enchantment> event) {
        event.getRegistry().registerAll(new EnchantmentFloating(), new EnchantmentManaEvoker(), new EnchantmentManaRecycler());
        ManaCraftEnchantments.init();
    }
    //Entities have no holders
    private static int nextEntityID = 0;
    @SubscribeEvent
    public static void addEntities(RegistryEvent.Register<EntityEntry> event) {
        event.getRegistry().registerAll(
                create().entity(EntityManaBall.class)
                    .id(new ResourceLocation(ManaCraft.MODID, "mana_ball"), nextEntityID++)
                    .name("ManaBall").tracker(64, 8, true).build(),
                create().entity(EntityManaBall.Floating.class)
                        .id(new ResourceLocation(ManaCraft.MODID, "mana_floating_ball"), nextEntityID++)
                        .name("FloatingManaBall").tracker(64, 10, true).build(),
                create().entity(EntityManaShooter.class)
                        .id(new ResourceLocation(ManaCraft.MODID, "mana_shooter"), nextEntityID++)
                        .name("ManaShooter").tracker(64, 3, true)
                        .egg(Color.MAGENTA.getRGB(), Color.BLACK.brighter().getRGB()).build()
        );
    }

//    @ObjectHolder    note: this is the only instance of (Potion) mana_evoker kept
    public static Potion mana_evoker;

    @SubscribeEvent
    public static void addPotions(RegistryEvent.Register<Potion> event) {
        event.getRegistry().register(mana_evoker = new PotionManaEvoker().setRegistryName("mana_evoker"));
    }

    @SubscribeEvent
    public static void addPotionTypes(RegistryEvent.Register<PotionType> event) {
        event.getRegistry().registerAll(new PotionType(new PotionEffect(mana_evoker, 3000)).setRegistryName("mana_evoker"),
                new PotionType("mana_evoker", new PotionEffect(mana_evoker, 8000)).setRegistryName("long_mana_evoker"),
                new PotionType("mana_evoker", new PotionEffect(mana_evoker, 1200, 1)).setRegistryName("strong_mana_evoker"),
                new PotionType("mana_evoker", new PotionEffect(mana_evoker, 12000, 2)).setRegistryName("mega_mana_evoker"));
    }

//    see above addItems(RegistryEvent.Register<Item>)
//    @SubscribeEvent
//    public static void addSoundEvents(RegistryEvent.Register<SoundEvent> event) {
//    }

    @SubscribeEvent
    public static void addVillagerProfessions(RegistryEvent.Register<VillagerRegistry.VillagerProfession> event) {
        event.getRegistry().register(new VillagerRegistry.VillagerProfession(
                "mana_craft:mana_priest",
                "mana_craft:textures/entity/mana_priest.png",
                "mana_craft:textures/entity/zombie_mana_priest.png"));
    }

    @SubscribeEvent
    public static void loadModels(ModelRegistryEvent event) {
        ManaCraftItems.Manager.manager.loadAllModel();
        ManaCraftBlocks.Manager.manager.loadAllModel();
    }
}
