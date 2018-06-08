package com.github.yaossg.mana_craft.inventory;

import com.github.yaossg.mana_craft.ManaCraft;
import com.github.yaossg.sausage_core.api.util.IGUIManager;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GUIContainerManaBooster extends GuiContainer
{
    public GUIContainerManaBooster(Container inventorySlotsIn) {
        super(inventorySlotsIn);
        this.xSize = 176;
        this.ySize = 166;

    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String translateKey = "container.mana_craft.mana_booster.";
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
        String title = I18n.format(translateKey + "title");
        this.drawString(this.fontRenderer, title,
                (this.xSize - this.fontRenderer.getStringWidth(title)) / 2, 6,0x404040);
        if(mouseX >= 79 + offsetX && mouseX < 95 + offsetX && mouseY >= 30 + offsetY && mouseY < 46 + offsetY) {
            ContainerManaBooster self = (ContainerManaBooster)this.inventorySlots;
            String hover = self.burn_time != 0
                    ? I18n.format(translateKey + "hover", (int)(100 * (float)self.burn_time / self.total_burn_time), self.burn_level)
                    : I18n.format(translateKey + "hoverIdle");
            this.drawHoveringText(hover, mouseX - offsetX, mouseY - offsetY);
        }
    }

    private static final ResourceLocation texture = IGUIManager.getTexture(ManaCraft.MODID, "mana_booster");
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(texture);
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;

        this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);
        ContainerManaBooster self = (ContainerManaBooster) this.inventorySlots;
        int textureHeight = (int) Math.ceil(12.0 * self.burn_time / self.total_burn_time);
        this.drawTexturedModalRect(offsetX + 81, offsetY + 45 - textureHeight, 176, 12 - textureHeight, 14, textureHeight);

    }

}
