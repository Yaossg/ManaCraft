package mana_craft.entity;

import mana_craft.ManaCraft;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

import static mana_craft.block.ManaCraftBlocks.*;
import static mana_craft.item.ManaCraftItems.*;
import static net.minecraft.entity.passive.EntityVillager.*;
import static net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import static net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import static sausage_core.api.util.common.Conversions.To.stack;

@ObjectHolder(ManaCraft.MODID)
public class ManaCraftVillagers {
    public static final VillagerProfession mana_priest = null;

    private static ITradeList item2gem(ItemStack item, int min, int max) {
        return item2gem(item, new PriceInfo(min, max));
    }

    private static ITradeList item2gem(ItemStack item, PriceInfo buy) {
        return (merchant, recipeList, random) -> {
            int i = buy.getPrice(random);
            recipeList.add(new MerchantRecipe(stack(item, i < 0 ? 1 : i), stack(Items.EMERALD, i < 0 ? -i : 1)));
        };
    }

    private static ITradeList gem2item(ItemStack item, int min, int max) {
        return gem2item(item, new PriceInfo(min, max));
    }

    private static ITradeList gem2item(ItemStack item, PriceInfo sell) {
        return (merchant, recipeList, random) -> {
            int i = sell.getPrice(random);
            recipeList.add(new MerchantRecipe(stack(Items.EMERALD, i < 0 ? 1 : i), stack(item, i < 0 ? -i : 1)));
        };
    }

    private static ITradeList enchanted(Item item, int min, int max) {
        return enchanted(item, new PriceInfo(min, max));
    }
    private static ITradeList enchanted(Item item, PriceInfo buy) {
        return new ListEnchantedItemForEmeralds(item, buy);
    }

    static final ITradeList tradeMana = item2gem(new ItemStack(mana), 13, 17);
    static final ITradeList tradeIngot = item2gem(new ItemStack(mana_ingot), 4, 6);
    static final ITradeList tradeDiamond = item2gem(new ItemStack(mana_diamond), -11, -4);
    static final ITradeList tradeShears = gem2item(new ItemStack(mana_shears), 4, 5);


    public static void init() {
        new VillagerCareer(mana_priest, "mana")
                .addTrade(1, tradeMana,
                        gem2item(new ItemStack(mana_glass), -4, -2),
                        gem2item(new ItemStack(mana_apple), 1, 1))
                .addTrade(2,
                        item2gem(new ItemStack(mana_pork), 1, 2),
                        gem2item(new ItemStack(machine_frame), 5, 7));
        new VillagerCareer(mana_priest, "mana_tool")
                .addTrade(1, tradeMana, tradeShears,
                        enchanted(mana_shovel, 7, 9))
                .addTrade(2, tradeIngot,
                        enchanted(mana_hoe, 8, 11))
                .addTrade(3, tradeDiamond,
                        enchanted(mana_pickaxe, 11, 15));
        new VillagerCareer(mana_priest, "mana_weapon")
                .addTrade(1, tradeMana, tradeShears,
                        gem2item(new ItemStack(mana_ball), 1, 1))
                .addTrade(2, tradeIngot,
                        enchanted(mana_wand, 7, 9))
                .addTrade(3, tradeDiamond,
                        enchanted(mana_sword,10, 15));
        new VillagerCareer(mana_priest, "mana_armor")
                .addTrade(1, tradeMana, tradeShears,
                        enchanted(mana_boots, 6, 9))
                .addTrade(2, tradeIngot,
                        enchanted(mana_helmet, 6, 9),
                        enchanted(mana_leggings, 9, 13))
                .addTrade(3, tradeDiamond,
                        enchanted(mana_chestplate, 13, 17));
        new VillagerCareer(mana_priest, "mana_summoner")
                .addTrade(1, tradeMana,
                        gem2item(new ItemStack(mana_dust), 1, 1))
                .addTrade(2, tradeIngot,
                        gem2item(new ItemStack(mana_foot), 3, 5),
                        gem2item(new ItemStack(mana_body), 7, 10))
                .addTrade(3, tradeDiamond,
                        gem2item(new ItemStack(mana_head), 17, 23));
    }
}
