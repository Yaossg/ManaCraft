package com.github.yaossg.mana_craft.inventory;

import com.github.yaossg.mana_craft.ManaCraft;
import com.github.yaossg.sausage_core.api.util.inventory.GUIHelper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;

@SideOnly(Side.CLIENT)
public class GUIContainerManaBooster extends GuiContainer {
    GUIContainerManaBooster(Container inventorySlotsIn) {
        super(inventorySlotsIn);
        xSize = 176;
        ySize = 166;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String title = I18n.format("container.mana_craft.mana_booster.title");
        GUIHelper.drawCenteredString(this, fontRenderer, title, 6, Color.DARK_GRAY.getRGB());
        ContainerManaBooster self = GUIHelper.getContainer(this);
        String translateKey = "gui.jei.category.mana_craft.mana_booster.";
        if(self.burn_time != 0) {
            fontRenderer.drawString(I18n.format(translateKey + "level", self.burn_level), 75, 24, Color.DARK_GRAY.getRGB());
            fontRenderer.drawString(I18n.format(translateKey + "time", self.burn_time), 75, 42, Color.DARK_GRAY.getRGB());
        } else {
            fontRenderer.drawString(I18n.format("container.mana_craft.mana_booster.idle"), 75, 24, Color.DARK_GRAY.getRGB());
        }
    }

    public static final ResourceLocation texture = GUIHelper.getTexture(ManaCraft.MODID, "mana_booster");

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F);

        mc.getTextureManager().bindTexture(texture);
        int offsetX = (width - xSize) / 2, offsetY = (height - ySize) / 2;

        drawTexturedModalRect(offsetX, offsetY, 0, 0, xSize, ySize);
        ContainerManaBooster self = GUIHelper.getContainer(this);
        int textureHeight = (int) Math.ceil(14.0 * self.burn_time / self.total_burn_time);
        drawTexturedModalRect(offsetX + 52, offsetY + 34 - textureHeight, 176, 14 - textureHeight, 14, textureHeight);

    }

}
