package mana_craft.subscriber;

import mana_craft.ManaCraft;
import mana_craft.block.ManaCraftBlocks;
import mana_craft.enchantment.EnchantmentFloating;
import mana_craft.enchantment.EnchantmentManaEvoker;
import mana_craft.enchantment.EnchantmentManaRecycler;
import mana_craft.enchantment.ManaCraftEnchantments;
import mana_craft.entity.EntityManaBall;
import mana_craft.entity.EntityManaShooter;
import mana_craft.item.ManaCraftItems;
import mana_craft.world.biome.BiomeMana;
import mana_craft.world.biome.BiomeManaChaos;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import sausage_core.api.event.InfoCardEvent;
import sausage_core.api.util.client.Colors;

import static net.minecraftforge.fml.common.registry.EntityEntryBuilder.create;

@Mod.EventBusSubscriber(modid = ManaCraft.MODID)
public class ManaCraftSubscribers {
    public static void init() {
        MinecraftForge.EVENT_BUS.register(ManaToolSubscriber.class);
        InfoCardEvent.INFO_CARD_BUS.register(ManaCraftSubscribers.class);
    }

    @SubscribeEvent
    public static void addBlock(RegistryEvent.Register<Block> event) {
        ManaCraftBlocks.init();
        ManaCraftBlocks.Manager.manager.registerBlocks();
    }

    @SubscribeEvent
    public static void addItems(RegistryEvent.Register<Item> event) {
        ManaCraftItems.init();
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

    private static int nextEntityID = 0;
    @SubscribeEvent
    public static void addEntities(RegistryEvent.Register<EntityEntry> event) {
        event.getRegistry().registerAll(
                create().entity(EntityManaBall.class)
                        .id("mana_ball", nextEntityID++).name("")
                        .tracker(64, 8, true).build(),
                create().entity(EntityManaBall.Floating.class)
                        .id("mana_floating_ball", nextEntityID++).name("")
                        .tracker(64, 10, true).build(),
                create().entity(EntityManaShooter.class)
                        .id("mana_shooter", nextEntityID++)
                        .name("mana_craft.ManaShooter")
                        .tracker(64, 3, true)
                        .egg(Colors.MAGENTA, Colors.DIMGRAY).build()
        );
    }

    @SubscribeEvent
    public static void loadModels(ModelRegistryEvent event) {
        ManaCraftItems.Manager.manager.loadAllModel();
        ManaCraftBlocks.Manager.manager.loadAllModel();
    }

    @SubscribeEvent
    public static void onInfoCard(InfoCardEvent event) {
        event.modInfo.addModTitle(ManaCraft.MODID, ManaCraft.NAME, ManaCraft.VERSION, "Yaossg")
                .withStyle(style -> style.setColor(TextFormatting.GREEN))
                .addText("Mana is the power of nature!");
        if(!Loader.isModLoaded("sausages_factory"))
            event.modInfo.newline()
                    .withStyle(style -> style.setColor(TextFormatting.GOLD).setItalic(true))
                    .addText("I know you're looking forward to Sausage's Factory. Me, too");
    }

    @SubscribeEvent
    public static void onMissingMappings(RegistryEvent.MissingMappings<Item> event) {
        for (RegistryEvent.MissingMappings.Mapping<Item> entry : event.getAllMappings())
            if(entry.key.toString().equals("mana_craft:mana_emerald")) entry.ignore();
    }
}
