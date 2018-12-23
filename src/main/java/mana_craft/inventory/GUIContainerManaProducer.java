package mana_craft.inventory;

import mana_craft.ManaCraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.Loader;
import sausage_core.api.util.inventory.GUIContainerBase;
import sausage_core.api.util.inventory.GUIHelper;

import java.awt.*;

public class GUIContainerManaProducer extends GUIContainerBase {
    GUIContainerManaProducer(Container inventorySlotsIn) {
        super(inventorySlotsIn, 176, 166, texture);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String translateKey = "container.mana_craft.mana_producer.";
        String title = I18n.format(translateKey + "title");
        GUIHelper.drawCenteredString(this, fontRenderer, title, 6, Color.DARK_GRAY.getRGB());
    }

    public static final ResourceLocation texture = GUIHelper.getTexture(ManaCraft.MODID, "mana_producer");
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
       super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int offsetX = (width - xSize) / 2, offsetY = (height - ySize) / 2;
        ContainerManaProducer self = GUIHelper.getContainer(this);
        int textureWidth = MathHelper.ceil(22.0 * self.progress / self.work_time) % 24;
        drawTexturedModalRect(offsetX + 87, offsetY + 36, 176, 0, textureWidth, 17);
    }

}
