package mana_craft.plugin;

import mana_craft.entity.EntityManaShooter;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import sausage_core.api.core.plugin.PluginTConstructCore;
import slimeknights.tconstruct.library.smeltery.CastingRecipe;

import static mana_craft.block.ManaCraftBlocks.*;
import static mana_craft.item.ManaCraftItems.*;
import static net.minecraftforge.fml.common.registry.GameRegistry.makeItemStack;
import static slimeknights.mantle.util.RecipeMatch.of;
import static slimeknights.tconstruct.library.TinkerRegistry.*;

public class PluginTConstruct extends PluginTConstructCore {
    @Override
    public void run() {
        addMoltenFluid("mana", 0x720072, 480);
        addMoltenFluid("mana_obsidian", 0x500050, 800);
        addMoltenFluid("mana_glass", 0xfeddfe, 600);
        integrate(addMoltenFluid("orichalcum", 0xe77dde, 550), "Orichalcum");
        registerAlloy(new FluidStack(get("orichalcum"), 16),
                new FluidStack(get("mana"), 600),
                new FluidStack(get("gold"), 16));
        registerAlloy(new FluidStack(get("mana_obsidian"), 1),
                new FluidStack(get("mana"), 5),
                new FluidStack(get("obsidian"), 1));
        registerAlloy(new FluidStack(get("mana_glass"), 1000),
                new FluidStack(get("orichalcum"), 64),
                new FluidStack(get("glass"), 1000));
        registerMelting(mana, get("mana"), 100);
        registerMelting(mana_ball, get("mana"), 450);
        registerMelting(mana_ore, get("mana"), 900);
        registerMelting(mana_block, get("mana"), 900);
        registerMelting(mana_obsidian, get("mana_obsidian"), 1000);
        registerMelting(mana_glass, get("mana_glass"), 1000);
        registerMelting(mana_rod, get("mana_obsidian"), 1000);
        registerTableCasting(new ItemStack(mana), ItemStack.EMPTY, get("mana"), 100);
        ItemStack gem_cast = makeItemStack("tconstruct:cast_custom", 2, 1, "");
        ItemStack stick_cast = makeItemStack("tconstruct:cast", 0, 1, "{PartType:\"tconstruct:tool_rod\"}");
        registerTableCasting(new ItemStack(mana_ball), gem_cast, get("mana"), 450);
        registerTableCasting(new ItemStack(mana_rod), stick_cast, get("mana_obsidian"), 1000);
        registerTableCasting(new CastingRecipe(new ItemStack(mana_apple), of(Items.APPLE), get("mana"), 1800, true, false));
        registerTableCasting(new CastingRecipe(new ItemStack(mana_coal), of(Items.COAL), get("mana"), 3600, true, false));
        registerTableCasting(new CastingRecipe(new ItemStack(mana_pork), of(Items.COOKED_PORKCHOP), get("mana"), 0x10000, true, false));
        registerBasinCasting(new ItemStack(mana_block), ItemStack.EMPTY, get("mana"), 900);
        registerBasinCasting(new ItemStack(mana_obsidian), ItemStack.EMPTY, get("mana_obsidian"), 1000);
        registerBasinCasting(new ItemStack(mana_glass), ItemStack.EMPTY, get("mana_glass"), 1000);
        registerBasinCasting(new CastingRecipe(new ItemStack(mana_glass), of("blockGlass"), get("orichalcum"), 64, true, false));
        registerBasinCasting(new CastingRecipe(new ItemStack(mana_obsidian), of(Blocks.OBSIDIAN), get("mana"), 1200, true, false));
        registerEntityMelting(EntityManaShooter.class, new FluidStack(get("mana"), 200));
    }

    static Fluid get(String fluid) {
        return FluidRegistry.getFluid(fluid);
    }
}
