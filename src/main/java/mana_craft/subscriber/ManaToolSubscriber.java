package mana_craft.subscriber;

import mana_craft.ManaCraft;
import mana_craft.api.IItemManaDamagable;
import mana_craft.block.ManaCraftBlocks;
import mana_craft.config.ManaCraftConfig;
import mana_craft.enchantment.ManaCraftEnchantments;
import mana_craft.item.ManaCraftItems;
import sausage_core.api.util.common.Explosions;
import sausage_core.api.util.nbt.NBTs;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class ManaToolSubscriber {
    @SubscribeEvent
    public static void onPlayerDestroyItem(PlayerDestroyItemEvent event) {
        Random random = event.getEntityPlayer().getRNG();
        ItemStack stack = event.getOriginal();
        if(stack.getItem() instanceof IItemManaDamagable && !(stack.getItem() instanceof ItemArmor)) {
            EntityPlayer player = event.getEntityPlayer();
            int value = ((IItemManaDamagable) stack.getItem()).getManaValue();
            if(value <= 0) return;
            int i = value > 1 ? 1 + random.nextInt(value - 1) : 1;
            int l = EnchantmentHelper.getEnchantmentLevel(ManaCraftEnchantments.manaRecycler, stack);
            InventoryHelper.spawnItemStack(player.world, player.posX, player.posY, player.posZ,
                    new ItemStack(ManaCraftItems.mana, l + i * (random.nextInt(l + 2) + 1)));
        }
    }

    @SubscribeEvent
    public static void onUseHoe(UseHoeEvent event) {
        ItemStack item = event.getCurrent();
        if(ManaCraftConfig.finalHoe && ItemStack.areItemStacksEqual(item, new ItemStack(ManaCraftItems.manaHoe))) {
            World world = event.getWorld();
            Block block = world.getBlockState(event.getPos()).getBlock();
            if(block == ManaCraftBlocks.manaBody) {
                item.setStackDisplayName("Final Hoe of Mana");
                item.addEnchantment(Enchantments.SHARPNESS, 7);
                item.addEnchantment(Enchantments.FIRE_ASPECT, 3);
                item.getOrCreateSubCompound("display").setTag("Lore", NBTs.asList(I18n.format("message.mana_craft.hoe")));
                world.setBlockToAir(event.getPos());
                Explosions.createToApply(world, event.getEntity(), event.getPos(), 1.25f, true, false);
                event.getEntityPlayer().addExperience(10);
                ManaCraft.giveAdvancement(event.getEntityPlayer(), "final_hoe");
                event.setResult(Event.Result.ALLOW);
            }
        }
    }
}
