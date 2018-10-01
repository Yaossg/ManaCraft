package mana_craft.potion;

import mana_craft.ManaCraft;
import mana_craft.block.ManaCraftBlocks;
import mana_craft.item.ManaCraftItems;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import static sausage_core.api.util.common.Conversions.To.item;

public class ManaCraftPotions {
    public static final ResourceLocation texture = new ResourceLocation(ManaCraft.MODID, "textures/gui/potions.png");
    public static final Potion manaEvoker = new PotionManaEvoker();
    public static final PotionType manaEvokerType = new PotionType(new PotionEffect(manaEvoker, 3000));
    public static final PotionType longManaEvokerType = new PotionType("mana_evoker", new PotionEffect(manaEvoker, 8000));
    public static final PotionType strongManaEvokerType = new PotionType("mana_evoker", new PotionEffect(manaEvoker, 1200, 1));
    public static final PotionType megaManaEvokerType = new PotionType("mana_evoker", new PotionEffect(manaEvoker, 12000, 2));

    public static void init() {
        ForgeRegistries.POTIONS.register(manaEvoker.setRegistryName("mana_evoker"));
        ForgeRegistries.POTION_TYPES.register(manaEvokerType.setRegistryName("mana_evoker"));
        ForgeRegistries.POTION_TYPES.register(longManaEvokerType.setRegistryName("long_mana_evoker"));
        ForgeRegistries.POTION_TYPES.register(strongManaEvokerType.setRegistryName("strong_mana_evoker"));
        ForgeRegistries.POTION_TYPES.register(megaManaEvokerType.setRegistryName("mega_mana_evoker"));
        PotionHelper.addMix(PotionTypes.AWKWARD, ManaCraftItems.manaDust, manaEvokerType);
        PotionHelper.addMix(manaEvokerType, Items.REDSTONE, longManaEvokerType);
        PotionHelper.addMix(manaEvokerType, Items.GLOWSTONE_DUST, strongManaEvokerType);
        PotionHelper.addMix(PotionTypes.AWKWARD, item(ManaCraftBlocks.manaFoot), megaManaEvokerType);
    }
}
