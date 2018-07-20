package com.github.yaossg.mana_craft.inventory;

import com.github.yaossg.mana_craft.ManaCraft;
import com.github.yaossg.sausage_core.api.util.inventory.GUIHelper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

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
        String translateKey = "container.mana_craft.mana_booster.";
        int offsetX = (width - xSize) / 2, offsetY = (height - ySize) / 2;
        String title = I18n.format(translateKey + "title");
        GUIHelper.drawCenteredString(this, fontRenderer, title, 6, 0x404040);
        if(mouseX >= 79 + offsetX && mouseX < 95 + offsetX && mouseY >= 30 + offsetY && mouseY < 46 + offsetY) {
            ContainerManaBooster self = GUIHelper.getContainer(this);
            String hover = self.burn_time != 0 ? I18n.format(translateKey + "hover",
                    (int) (100 * (float) self.burn_time / self.total_burn_time), self.burn_level) : I18n.format(translateKey + "hoverIdle");
            drawHoveringText(hover, mouseX - offsetX, mouseY - offsetY);
        }
    }

    private static final ResourceLocation texture = GUIHelper.getTexture(ManaCraft.MODID, "mana_booster");

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F);

        mc.getTextureManager().bindTexture(texture);
        int offsetX = (width - xSize) / 2, offsetY = (height - ySize) / 2;

        drawTexturedModalRect(offsetX, offsetY, 0, 0, xSize, ySize);
        ContainerManaBooster self = GUIHelper.getContainer(this);
        int textureHeight = (int) Math.ceil(12.0 * self.burn_time / self.total_burn_time);
        drawTexturedModalRect(offsetX + 81, offsetY + 45 - textureHeight, 176, 12 - textureHeight, 14, textureHeight);

    }

}
