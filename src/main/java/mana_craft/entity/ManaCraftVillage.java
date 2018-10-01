package mana_craft.entity;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import static mana_craft.block.ManaCraftBlocks.*;
import static mana_craft.item.ManaCraftItems.*;
import static net.minecraft.entity.passive.EntityVillager.*;
import static net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import static net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import static sausage_core.api.util.common.Conversions.To.stack;

public class ManaCraftVillage {
    public static final VillagerProfession mana_priest =
            new VillagerProfession("mana_craft:mana_priest",
                    "mana_craft:textures/entity/mana_priest.png",
                    "mana_craft:textures/entity/zombie_mana_priest.png");

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
    static final ITradeList tradeIngot = item2gem(new ItemStack(manaIngot), 4, 6);
    static final ITradeList tradeDiamond = item2gem(new ItemStack(manaDiamond), -11, -4);
    static final ITradeList tradeShears = gem2item(new ItemStack(manaShears), 4, 5);


    public static void init() {
        ForgeRegistries.VILLAGER_PROFESSIONS.register(mana_priest);
        new VillagerCareer(mana_priest, "mana")
                .addTrade(1, tradeMana,
                        gem2item(new ItemStack(manaGlass), -4, -2),
                        gem2item(new ItemStack(manaApple), 1, 1))
                .addTrade(2,
                        item2gem(new ItemStack(manaPork), 1, 2),
                        gem2item(new ItemStack(machineFrame), 5, 7));
        new VillagerCareer(mana_priest, "mana_tool")
                .addTrade(1, tradeMana, tradeShears,
                        enchanted(manaShovel, 7, 9))
                .addTrade(2, tradeIngot,
                        enchanted(manaHoe, 8, 11))
                .addTrade(3, tradeDiamond,
                        enchanted(manaPickaxe, 11, 15));
        new VillagerCareer(mana_priest, "mana_weapon")
                .addTrade(1, tradeMana, tradeShears,
                        gem2item(new ItemStack(manaBall), 1, 1))
                .addTrade(2, tradeIngot,
                        enchanted(manaWand, 7, 9))
                .addTrade(3, tradeDiamond,
                        enchanted(manaSword,10, 15));
        new VillagerCareer(mana_priest, "mana_armor")
                .addTrade(1, tradeMana, tradeShears,
                        enchanted(manaBoots, 6, 9))
                .addTrade(2, tradeIngot,
                        enchanted(manaHelmet, 6, 9),
                        enchanted(manaLeggings, 9, 13))
                .addTrade(3, tradeDiamond,
                        enchanted(manaChestplate, 13, 17));
        new VillagerCareer(mana_priest, "mana_summoner")
                .addTrade(1, tradeMana,
                        gem2item(new ItemStack(manaDust), 1, 1))
                .addTrade(2, tradeIngot,
                        gem2item(new ItemStack(manaFoot), 3, 5),
                        gem2item(new ItemStack(manaBody), 7, 10))
                .addTrade(3, tradeDiamond,
                        gem2item(new ItemStack(manaHead), 17, 23));
    }
}
