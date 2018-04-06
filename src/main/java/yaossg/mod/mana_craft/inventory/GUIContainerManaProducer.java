package yaossg.mod.mana_craft.inventory;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import yaossg.mod.mana_craft.ManaCraft;

public class GUIContainerManaProducer extends GuiContainer
{
    public GUIContainerManaProducer(Container inventorySlotsIn)
    {
        super(inventorySlotsIn);
        this.xSize = 176;
        this.ySize = 166;

    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
        String title = I18n.format("container.mana_producer.title");
        this.drawString(this.fontRenderer, title,
                (this.xSize - this.fontRenderer.getStringWidth(title)) / 2, 6,0x404040);
        if(mouseX >= 87 + offsetX && mouseX < 110 + offsetX && mouseY >= 36 + offsetY && mouseY < 54 + offsetY) {
            ContainerManaProducer self = (ContainerManaProducer)this.inventorySlots;
            String hover = I18n.format("container.mana_producer.hover", (int)(100 * (float)self.work_time / self.total_work_time));
            this.drawHoveringText(hover, mouseX - offsetX, mouseY - offsetY);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(new ResourceLocation(ManaCraft.MODID + ":textures/gui/container/mana_producer.png"));
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;

        this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);
        ContainerManaProducer self = (ContainerManaProducer)this.inventorySlots;
        int textureWidth = ((int) Math.ceil(22.0 * self.work_time / self.total_work_time)) % 24;
        this.drawTexturedModalRect(offsetX + 87, offsetY + 36, 177, 0, textureWidth, 17);

    }

}
