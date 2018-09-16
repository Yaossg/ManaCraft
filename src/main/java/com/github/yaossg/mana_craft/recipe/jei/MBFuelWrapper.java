package com.github.yaossg.mana_craft.recipe.jei;

import com.github.yaossg.mana_craft.ManaCraft;
import com.github.yaossg.mana_craft.api.registry.IMBFuel;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.github.yaossg.mana_craft.inventory.GUIContainerManaBooster.texture;

public class MBFuelWrapper implements IRecipeWrapper {
    private final List<List<ItemStack>> input;
    private final IDrawable flame;
    private final String[] info = new String[2];
    MBFuelWrapper(IGuiHelper guiHelper, IMBFuel fuel) {
        input = Collections.singletonList(Arrays.asList(fuel.get().getMatchingStacks()));
        String translateKey = "gui.jei.category." + ManaCraft.MODID;
        info[0] = I18n.format(translateKey + ".mana_booster.level", fuel.level());
        info[1] = I18n.format(translateKey + ".mana_booster.time", fuel.time());
        flame = guiHelper.drawableBuilder(texture, 176, 0, 14, 14)
                .buildAnimated(fuel.time(), IDrawableAnimated.StartDirection.TOP, true);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(ItemStack.class, input);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        flame.draw(minecraft, 1, 0);
        minecraft.fontRenderer.drawString(info[0], 24, 4, Color.DARK_GRAY.getRGB());
        minecraft.fontRenderer.drawString(info[1], 24, 22, Color.DARK_GRAY.getRGB());
    }
}
