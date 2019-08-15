package mana_craft.plugin;

import mana_craft.entity.EntityManaShooter;
import mana_craft.init.ManaCraftBlocks;
import mana_craft.init.ManaCraftItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import sausage_core.api.annotation.LoadClass;
import sausage_core.api.util.client.Colors;
import slimeknights.tconstruct.library.smeltery.CastingRecipe;

import static mana_craft.init.ManaCraftBlocks.mana_block;
import static mana_craft.init.ManaCraftBlocks.mana_ore;
import static mana_craft.init.ManaCraftItems.*;
import static net.minecraftforge.fml.common.registry.GameRegistry.makeItemStack;
import static sausage_core.api.util.common.TConstructSupport.addMolten;
import static sausage_core.api.util.common.TConstructSupport.get;
import static slimeknights.mantle.util.RecipeMatch.of;
import static slimeknights.tconstruct.library.TinkerRegistry.*;

@LoadClass(construct = true, modRequired = "tconstruct", when = LoadClass.When.INIT)
public class PluginTConstruct {
	public PluginTConstruct() {
		Fluid mana = addMolten("mana", Colors.PURPLE, 450);
		Fluid mana_obsidian = addMolten("mana_obsidian", 0xFF400040, 800);
		Fluid mana_glass = addMolten("mana_glass", Colors.PLUM, 600);
		Fluid orichalcum = addMolten("orichalcum", Colors.MAGENTA, 550);
		integrate(orichalcum, "Orichalcum").toolforge();
		registerAlloy(new FluidStack(orichalcum, 16),
				new FluidStack(mana, 60),
				new FluidStack(get("gold"), 16));
		registerAlloy(new FluidStack(mana_obsidian, 2),
				new FluidStack(mana, 1),
				new FluidStack(get("obsidian"), 2));
		registerAlloy(new FluidStack(mana_glass, 125),
				new FluidStack(orichalcum, 8),
				new FluidStack(get("glass"), 125));
		registerMelting(ManaCraftItems.mana, mana, 10);
		registerMelting(mana_ball, mana, 45);
		registerMelting(mana_ore, mana, 90);
		registerMelting(mana_block, mana, 90);
		registerMelting(ManaCraftBlocks.mana_obsidian, mana_obsidian, 1000);
		registerMelting(ManaCraftBlocks.mana_glass, mana_glass, 1000);
		registerMelting(mana_rod, mana_obsidian, 1000);
		registerTableCasting(new ItemStack(ManaCraftItems.mana), ItemStack.EMPTY, mana, 10);
		ItemStack gem_cast = makeItemStack("tconstruct:cast_custom", 2, 1, "");
		ItemStack stick_cast = makeItemStack("tconstruct:cast", 0, 1, "{PartType:\"tconstruct:tool_rod\"}");
		registerTableCasting(new ItemStack(mana_ball), gem_cast, mana, 45);
		registerTableCasting(new ItemStack(mana_rod), stick_cast, mana_obsidian, 1000);
		registerTableCasting(new CastingRecipe(new ItemStack(mana_apple), of(Items.APPLE), mana, 180, true, false));
		registerTableCasting(new CastingRecipe(new ItemStack(mana_coal), of(Items.COAL), mana, 360, true, false));
		registerTableCasting(new CastingRecipe(new ItemStack(mana_pork), of(Items.COOKED_PORKCHOP), mana, 0x2018, true, false));
		registerBasinCasting(new ItemStack(mana_block), ItemStack.EMPTY, mana, 90);
		registerBasinCasting(new ItemStack(ManaCraftBlocks.mana_obsidian), ItemStack.EMPTY, mana_obsidian, 1000);
		registerBasinCasting(new ItemStack(ManaCraftBlocks.mana_glass), ItemStack.EMPTY, mana_glass, 1000);
		registerBasinCasting(new CastingRecipe(new ItemStack(ManaCraftBlocks.mana_glass), of("blockGlass"), orichalcum, 64, true, false));
		registerBasinCasting(new CastingRecipe(new ItemStack(ManaCraftBlocks.mana_obsidian), of(Blocks.OBSIDIAN), mana, 120, true, false));
		registerEntityMelting(EntityManaShooter.class, new FluidStack(mana, 20));
	}
}
