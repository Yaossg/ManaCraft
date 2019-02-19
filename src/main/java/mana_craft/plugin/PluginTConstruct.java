package mana_craft.plugin;

import mana_craft.block.ManaCraftBlocks;
import mana_craft.entity.EntityManaShooter;
import mana_craft.item.ManaCraftItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import sausage_core.api.core.plugin.PluginTConstructCore;
import sausage_core.api.util.client.Colors;
import slimeknights.tconstruct.library.smeltery.CastingRecipe;

import static mana_craft.block.ManaCraftBlocks.mana_block;
import static mana_craft.block.ManaCraftBlocks.mana_ore;
import static mana_craft.item.ManaCraftItems.*;
import static net.minecraftforge.fml.common.registry.GameRegistry.makeItemStack;
import static slimeknights.mantle.util.RecipeMatch.of;
import static slimeknights.tconstruct.library.TinkerRegistry.*;

public class PluginTConstruct extends PluginTConstructCore {
    @Override
    public void run() {
        Fluid mana = addMoltenFluid("mana", Colors.PURPLE, 450);
        Fluid mana_obsidian = addMoltenFluid("mana_obsidian", Colors.fromRGB(0.25F, 0, 0.25F), 800);
        Fluid mana_glass = addMoltenFluid("mana_glass", Colors.PLUM, 600);
        Fluid orichalcum = addMoltenFluid("orichalcum", Colors.MAGENTA, 550);
        integrate(orichalcum, "Orichalcum").toolforge();
        registerAlloy(new FluidStack(orichalcum, 16),
                new FluidStack(mana, 600),
                new FluidStack(get("gold"), 16));
        registerAlloy(new FluidStack(mana_obsidian, 1),
                new FluidStack(mana, 5),
                new FluidStack(get("obsidian"), 1));
        registerAlloy(new FluidStack(mana_glass, 1000),
                new FluidStack(orichalcum, 64),
                new FluidStack(get("glass"), 1000));
        registerMelting(ManaCraftItems.mana, mana, 100);
        registerMelting(mana_ball, mana, 450);
        registerMelting(mana_ore, mana, 900);
        registerMelting(mana_block, mana, 900);
        registerMelting(ManaCraftBlocks.mana_obsidian, mana_obsidian, 1000);
        registerMelting(ManaCraftBlocks.mana_glass, mana_glass, 1000);
        registerMelting(mana_rod, mana_obsidian, 1000);
        registerTableCasting(new ItemStack(ManaCraftItems.mana), ItemStack.EMPTY, mana, 100);
        ItemStack gem_cast = makeItemStack("tconstruct:cast_custom", 2, 1, "");
        ItemStack stick_cast = makeItemStack("tconstruct:cast", 0, 1, "{PartType:\"tconstruct:tool_rod\"}");
        registerTableCasting(new ItemStack(mana_ball), gem_cast, mana, 450);
        registerTableCasting(new ItemStack(mana_rod), stick_cast, mana_obsidian, 1000);
        registerTableCasting(new CastingRecipe(new ItemStack(mana_apple), of(Items.APPLE), mana, 1800, true, false));
        registerTableCasting(new CastingRecipe(new ItemStack(mana_coal), of(Items.COAL), mana, 3600, true, false));
        registerTableCasting(new CastingRecipe(new ItemStack(mana_pork), of(Items.COOKED_PORKCHOP), mana, 0xFFFF, true, false));
        registerBasinCasting(new ItemStack(mana_block), ItemStack.EMPTY, mana, 900);
        registerBasinCasting(new ItemStack(ManaCraftBlocks.mana_obsidian), ItemStack.EMPTY, mana_obsidian, 1000);
        registerBasinCasting(new ItemStack(ManaCraftBlocks.mana_glass), ItemStack.EMPTY, mana_glass, 1000);
        registerBasinCasting(new CastingRecipe(new ItemStack(ManaCraftBlocks.mana_glass), of("blockGlass"), orichalcum, 64, true, false));
        registerBasinCasting(new CastingRecipe(new ItemStack(ManaCraftBlocks.mana_obsidian), of(Blocks.OBSIDIAN), mana, 1200, true, false));
        registerEntityMelting(EntityManaShooter.class, new FluidStack(mana, 200));
    }
}
