package com.github.yaossg.mana_craft.event;

import com.github.yaossg.mana_craft.block.ManaCraftBlocks;
import com.github.yaossg.mana_craft.world.biome.BiomeMana;
import net.minecraft.block.BlockBone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.event.terraingen.BiomeEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ManaGenEvent {
    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void onVillageGen(BiomeEvent.GetVillageBlockID event) {
        if(event.getBiome() instanceof BiomeMana) {
            IBlockState original = event.getOriginal();
            event.setReplacement(original);
            event.setResult(Event.Result.DENY);
            if(original.getBlock() == Blocks.LOG || original.getBlock() == Blocks.LOG2)
                event.setReplacement(Blocks.BONE_BLOCK.getDefaultState().withProperty(BlockBone.AXIS, EnumFacing.Axis.Y));

            if(original.getBlock() == Blocks.GRASS_PATH)
                event.setReplacement(ManaCraftBlocks.manaBlock.getDefaultState());

            if(original.getBlock() == Blocks.COBBLESTONE)
                event.setReplacement(Blocks.STONEBRICK.getDefaultState());

            if(original.getBlock() == Blocks.STONE_STAIRS)
                event.setReplacement(Blocks.STONE_BRICK_STAIRS.getStateFromMeta(original.getBlock().getMetaFromState(original)));

            if(original.getBlock() == Blocks.PLANKS)
                event.setReplacement(Blocks.BRICK_BLOCK.getDefaultState());

            if(original.getBlock() == Blocks.OAK_STAIRS)
                event.setReplacement(Blocks.BRICK_STAIRS.getStateFromMeta(original.getBlock().getMetaFromState(original)));

            if(original.getBlock() == Blocks.OAK_FENCE)
                event.setReplacement(Blocks.NETHER_BRICK_FENCE.getDefaultState());
        }
    }
}
