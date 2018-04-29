package yaossg.mod.mana_craft.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import yaossg.mod.mana_craft.config.Config;
import yaossg.mod.mana_craft.item.ManaCraftItems;
import yaossg.mod.mana_craft.util.RandomBuffer;

import java.util.Random;

public class ManaDropEvent {
    public static void init() {
        if(Config.dropMana)
            MinecraftForge.EVENT_BUS.register(ManaDropEvent.class);
    }
    public static final Random random = new Random();
    public static final RandomBuffer rb = new RandomBuffer(random);
    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        Entity entity = event.getEntity();
        int loot = event.getLootingLevel();
        rb.reallocate(32);
        if(rb.getAsInt(4) + (int)(1.75 * loot) > 14)
            if(Config.dropManaApple
                    && entity instanceof EntityPig && loot != 0
                    && (loot > 4 || rb.getAsInt(2) < loot)
                    && (rb.getAsInt(4) == 0)) {
                event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ,
                        new ItemStack(ManaCraftItems.itemManaApple, 1)));
            } else {
                event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ,
                        new ItemStack(ManaCraftItems.itemMana,
                                (int) (1 + (rb.getFloat(22) + 0.5) * loot * 0.75))));
            }
    }
}
