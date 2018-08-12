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

public class GUIContainerManaProducer extends GuiContainer {
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
        GUIHelper.drawCenteredString(this, fontRenderer, title, 6, Color.DARK_GRAY.getRGB());
        if(!Loader.isModLoaded("jei"))
        if(mouseX >= 87 + offsetX && mouseX < 110 + offsetX && mouseY >= 36 + offsetY && mouseY < 54 + offsetY) {
            ContainerManaProducer self = GUIHelper.getContainer(this);
            String hover = self.work_time != 0 ? I18n.format(translateKey + "hover",
                    (int) (100 * (float) self.work_time / self.total_work_time)) : I18n.format(translateKey + "hoverIdle");
            drawHoveringText(hover, mouseX - offsetX, mouseY - offsetY);
        }
    }

    public static final ResourceLocation texture = GUIHelper.getTexture(ManaCraft.MODID, "mana_producer");

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F);

        mc.getTextureManager().bindTexture(texture);
        int offsetX = (width - xSize) / 2, offsetY = (height - ySize) / 2;

        drawTexturedModalRect(offsetX, offsetY, 0, 0, xSize, ySize);
        ContainerManaProducer self = GUIHelper.getContainer(this);
        int textureWidth = ((int) Math.ceil(22.0 * self.work_time / self.total_work_time)) % 24;
        drawTexturedModalRect(offsetX + 87, offsetY + 36, 176, 0, textureWidth, 17);

    }

}
