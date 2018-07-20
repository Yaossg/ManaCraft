package com.github.yaossg.mana_craft.village;

import com.github.yaossg.mana_craft.block.ManaCraftBlocks;
import com.github.yaossg.mana_craft.config.ManaCraftConfig;
import com.github.yaossg.mana_craft.item.ManaCraftItems;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

import static com.github.yaossg.sausage_core.api.util.common.Conversions.To.stack;
import static net.minecraft.entity.passive.EntityVillager.*;
import static net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

public class ManaCraftTrade {
    @ObjectHolder("minecraft:librarian")
    public static final VillagerRegistry.VillagerProfession librarian = null;
    @ObjectHolder("minecraft:priest")
    public static final VillagerRegistry.VillagerProfession priest = null;

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

    static final ITradeList tradeBook = new ListEnchantedBookForEmeralds();
    static final ITradeList tradeMana = item2gem(new ItemStack(ManaCraftItems.mana), 13, 17);
    static final ITradeList tradeIngot = item2gem(new ItemStack(ManaCraftItems.manaIngot), 4, 6);
    static final ITradeList tradeDiamond = item2gem(new ItemStack(ManaCraftItems.manaDiamond), -11, -4);
    static final ITradeList tradeShears = gem2item(new ItemStack(ManaCraftItems.manaShears), 4, 5);

    @SuppressWarnings("ConstantConditions")
    public static void init() {
        if(ManaCraftConfig.enchanter)
            new VillagerRegistry.VillagerCareer(librarian, "enchanter")
                    .addTrade(1, tradeBook, tradeBook, tradeBook)
                    .addTrade(2, tradeBook, tradeBook, tradeBook)
                    .addTrade(3, tradeBook, tradeBook, tradeBook)
                    .addTrade(4, tradeBook, tradeBook, tradeBook)
                    .addTrade(5, tradeBook, tradeBook, tradeBook);

        new VillagerRegistry.VillagerCareer(priest, "mana")
                .addTrade(1, tradeMana,
                        gem2item(new ItemStack(ManaCraftBlocks.manaGlass), -4, -2),
                        gem2item(new ItemStack(ManaCraftItems.manaApple), 1, 1))
                .addTrade(2,
                        item2gem(new ItemStack(ManaCraftItems.manaPork), 1, 2),
                        gem2item(new ItemStack(ManaCraftBlocks.machineFrame), 5, 7));
        new VillagerRegistry.VillagerCareer(priest, "mana_tool")
                .addTrade(1, tradeMana, tradeShears,
                        enchanted(ManaCraftItems.manaShovel, 7, 9))
                .addTrade(2, tradeIngot,
                        enchanted(ManaCraftItems.manaHoe, 8, 11))
                .addTrade(3,tradeDiamond,
                        enchanted(ManaCraftItems.manaPickaxe, 11, 15));
        new VillagerRegistry.VillagerCareer(priest, "mana_weapon")
                .addTrade(1, tradeMana, tradeShears,
                        gem2item(new ItemStack(ManaCraftItems.manaBall), 1, 1))
                .addTrade(2, tradeIngot,
                        enchanted(ManaCraftItems.manaWand, 7, 9))
                .addTrade(3,tradeDiamond,
                        enchanted(ManaCraftItems.manaSword,10, 15));
        new VillagerRegistry.VillagerCareer(priest, "mana_armor")
                .addTrade(1, tradeMana, tradeShears,
                        enchanted(ManaCraftItems.manaBoots, 6, 9))
                .addTrade(2, tradeIngot,
                        enchanted(ManaCraftItems.manaHelmet, 6, 9),
                        enchanted(ManaCraftItems.manaLeggings, 9, 13))
                .addTrade(3,tradeDiamond,
                        enchanted(ManaCraftItems.manaChestplate,13, 17));
    }
}
