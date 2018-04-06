package yaossg.mod.mana_craft.event;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import yaossg.mod.mana_craft.item.ItemManaTool;
import yaossg.mod.mana_craft.item.ManaCraftItems;

import java.util.Random;

public class ManaToolBrokenEvent {

    public static final Random random = new Random();
    public static void init() {
        MinecraftForge.EVENT_BUS.register(ManaToolBrokenEvent.class);
    }
    @SubscribeEvent
    public static void onPlayerDestroyItem(PlayerDestroyItemEvent event) {
        Item item = event.getOriginal().getItem();
        if(item instanceof ItemManaTool) {
            EntityPlayer player = event.getEntityPlayer();
            Block.spawnAsEntity(player.getEntityWorld(), player.getPosition(), new ItemStack(ManaCraftItems.itemMana,
                    1 + random.nextInt(((ItemManaTool) item).getMaxManaDropOnBreak() - 1)));
        }
    }
}
