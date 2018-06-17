package com.github.yaossg.mana_craft.inventory;

import com.github.yaossg.mana_craft.ManaCraft;
import com.github.yaossg.sausage_core.api.util.IGUIManager;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GUIContainerManaProducer extends GuiContainer
{
    GUIContainerManaProducer(Container inventorySlotsIn) {
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
        String translateKey = "container.mana_craft.mana_producer.";
        int offsetX = (width - xSize) / 2, offsetY = (height - ySize) / 2;
        String title = I18n.format(translateKey + "title");
        drawString(fontRenderer, title,
                (xSize - fontRenderer.getStringWidth(title)) / 2, 6,0x404040);
        if(mouseX >= 87 + offsetX && mouseX < 110 + offsetX && mouseY >= 36 + offsetY && mouseY < 54 + offsetY) {
            ContainerManaProducer self = (ContainerManaProducer)inventorySlots;
            String hover = self.work_time != 0
                    ? I18n.format(translateKey + "hover", (int)(100 * (float)self.work_time / self.total_work_time))
                    : I18n.format(translateKey + "hoverIdle");
            drawHoveringText(hover, mouseX - offsetX, mouseY - offsetY);
        }
    }

    private static final ResourceLocation texture = IGUIManager.getTexture(ManaCraft.MODID, "mana_producer");
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F);

        mc.getTextureManager().bindTexture(texture);
        int offsetX = (width - xSize) / 2, offsetY = (height - ySize) / 2;

        drawTexturedModalRect(offsetX, offsetY, 0, 0, xSize, ySize);
        ContainerManaProducer self = (ContainerManaProducer)inventorySlots;
        int textureWidth = ((int) Math.ceil(22.0 * self.work_time / self.total_work_time)) % 24;
        drawTexturedModalRect(offsetX + 87, offsetY + 36, 177, 0, textureWidth, 17);

    }

}
