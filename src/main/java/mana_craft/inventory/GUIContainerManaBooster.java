package mana_craft.inventory;

import mana_craft.ManaCraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import sausage_core.api.core.inventory.GUIContainerBase;
import sausage_core.api.util.client.GUIHelper;

import java.awt.*;

public class GUIContainerManaBooster extends GUIContainerBase {
    GUIContainerManaBooster(Container inventorySlotsIn) {
        super(inventorySlotsIn, 176, 144, texture);
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
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int offsetX = (width - xSize) / 2, offsetY = (height - ySize) / 2;
        ContainerManaBooster self = GUIHelper.getContainer(this);
        int textureHeight = MathHelper.ceil(14.0 * self.burn_time / self.total_burn_time);
        drawTexturedModalRect(offsetX + 52, offsetY + 34 - textureHeight, 176, 14 - textureHeight, 14, textureHeight);
    }
}
