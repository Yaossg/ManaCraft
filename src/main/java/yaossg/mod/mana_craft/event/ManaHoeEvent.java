package yaossg.mod.mana_craft.event;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import yaossg.mod.NBTs;
import yaossg.mod.mana_craft.Util;
import yaossg.mod.mana_craft.block.ManaCraftBlocks;
import yaossg.mod.mana_craft.item.ManaCraftItems;


public class ManaHoeEvent {
    public static void init() {
        MinecraftForge.EVENT_BUS.register(ManaHoeEvent.class);
    }
    @SubscribeEvent
    public static void onUseHoe(UseHoeEvent event) {
        ItemStack item = event.getCurrent();
        if(item.getItem().equals(ManaCraftItems.itemManaHoe)) {
            World world = event.getWorld();
            Block block = world.getBlockState(event.getPos()).getBlock();
            if (block.equals(ManaCraftBlocks.blockMana) && ItemStack.areItemStacksEqual(item, new ItemStack(ManaCraftItems.itemManaHoe))) {
                item.setStackDisplayName("Final Hoe of Mana");
                item.addEnchantment(Enchantments.SHARPNESS, 7);
                item.addEnchantment(Enchantments.FIRE_ASPECT, 3);
                item.getOrCreateSubCompound("display").setTag("Lore", NBTs.asList(
                        I18n.format("message.hoe")));
                world.setBlockToAir(event.getPos());
                Util.createThenApplyExplosin(world, event.getEntity(), event.getPos(), 1.25f,
                        true, false);
                event.getEntityPlayer().addExperience(10);
                Util.giveManaCraftAdvancement(event.getEntityPlayer(), "final_hoe");
                event.setResult(Event.Result.ALLOW);
            }
        }
    }
}
