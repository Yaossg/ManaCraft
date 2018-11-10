package mana_craft.potion;

import mana_craft.ManaCraft;
import mana_craft.block.ManaCraftBlocks;
import mana_craft.item.ManaCraftItems;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

import static sausage_core.api.util.common.Conversions.To.item;

@ObjectHolder(ManaCraft.MODID)
public class ManaCraftPotionTypes {
    public static final PotionType mana_evoker = null;
    public static final PotionType long_mana_evoker = null;
    public static final PotionType strong_mana_evoker = null;
    public static final PotionType mega_mana_evoker = null;
    public static void init() {
        PotionHelper.addMix(PotionTypes.AWKWARD, ManaCraftItems.mana_ball, mana_evoker);
        PotionHelper.addMix(mana_evoker, Items.REDSTONE, long_mana_evoker);
        PotionHelper.addMix(mana_evoker, Items.GLOWSTONE_DUST, strong_mana_evoker);
        PotionHelper.addMix(PotionTypes.AWKWARD, item(ManaCraftBlocks.mana_foot), mega_mana_evoker);
    }
}
