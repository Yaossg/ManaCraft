package mana_craft.proxy;

import mana_craft.ManaCraft;
import mana_craft.block.BlockManaHead;
import mana_craft.config.ManaCraftConfig;
import mana_craft.entity.ManaCraftVillagers;
import mana_craft.inventory.ManaCraftGUIs;
import mana_craft.item.ItemManaArmor;
import mana_craft.item.ItemManaTools;
import mana_craft.loot.ManaCraftLoots;
import mana_craft.potion.ManaCraftPotions;
import mana_craft.recipe.ManaCraftRecipes;
import mana_craft.subscriber.ManaCraftSubscribers;
import mana_craft.tile.TileManaBooster;
import mana_craft.tile.TileManaProducer;
import mana_craft.world.gen.ManaCraftWorldGens;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import sausage_core.api.util.common.SausageUtils;
import sausage_core.api.util.inventory.IEnumGUIHandler;

import javax.annotation.Nonnull;

import static mana_craft.block.ManaCraftBlocks.*;
import static mana_craft.item.ManaCraftItems.*;
import static net.minecraftforge.fml.common.registry.GameRegistry.addSmelting;
import static net.minecraftforge.oredict.OreDictionary.registerOre;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        IEnumGUIHandler.register(ManaCraft.instance, ManaCraftGUIs.values());
        ManaCraftRecipes.init(event);
        if(ManaCraftConfig.potion)
            ManaCraftPotions.preInit();
        ManaCraftVillagers.preInit();
    }

    static void addOre() {
        registerOre("dye", blue_shit);
        registerOre("dyeLightBlue", blue_shit);
        registerOre("blockGlass", mana_glass);
        registerOre("blockGlassHardened", mana_glass);
        registerOre("gemEmerald", mana_emerald);
        registerOre("record", mana_record);
    }

    static void addSmelt() {
        addSmelting(mana_ore, new ItemStack(mana, 4), 0.6f);
        addSmelting(mana_ingot_ore, new ItemStack(mana_ingot), 0.4f);
        addSmelting(mana_block, new ItemStack(mana_ball), 0.2f);
    }

    static void misc() {
        BlockManaHead.init();

        ItemManaTools.MANA_TOOL.setRepairItem(new ItemStack(mana_ingot));
        ItemManaArmor.MANA_ARMOR.setRepairItem(new ItemStack(mana_ingot));

        SausageUtils.registerTileEntities(ManaCraft.MODID, TileManaProducer.class, TileManaBooster.class);
        TileManaProducer.init();
    }

    public void init(FMLInitializationEvent event) {
        ManaCraftSubscribers.init();
        if(ManaCraftConfig.loot)
            ManaCraftLoots.init();
        ManaCraftVillagers.init();
        ManaCraftWorldGens.init();
        if(ManaCraftConfig.potion)
            ManaCraftPotions.init();
        addOre();
        addSmelt();
        misc();
    }

    public void postInit(FMLPostInitializationEvent event) {
        ManaCraftRecipes.loadAll();
        MinecraftForge.addGrassSeed(new ItemStack(mana), 1);
    }
}