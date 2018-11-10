package mana_craft.world.gen;

import mana_craft.ManaCraft;
import mana_craft.block.ManaCraftBlocks;
import mana_craft.world.biome.BiomeMana;
import net.minecraft.block.BlockBone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.BiomeEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static net.minecraft.world.gen.structure.MapGenStructureIO.registerStructureComponent;
import static net.minecraftforge.fml.common.registry.VillagerRegistry.instance;

public class ManaCraftWorldGens {
    public static void init() {
        GameRegistry.registerWorldGenerator(new WorldGenMana(), ManaCraft.MODID.hashCode());

        registerStructureComponent(StructureVillageMP.class, ManaCraft.MODID + ":ViMP");
        instance().registerVillageCreationHandler(new StructureVillageMP.Handler());
        registerStructureComponent(StructureVillageML.class, ManaCraft.MODID + ":ViML");
        instance().registerVillageCreationHandler(new StructureVillageML.Handler());

        MinecraftForge.TERRAIN_GEN_BUS.register(ManaCraftWorldGens.class);
    }

    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void onGetVillageBlock(BiomeEvent.GetVillageBlockID event) {
        if(event.getBiome() instanceof BiomeMana) {
            IBlockState original = event.getOriginal();
            event.setReplacement(original);
            event.setResult(Event.Result.DENY);
            if(original.getBlock() == Blocks.LOG || original.getBlock() == Blocks.LOG2)
                event.setReplacement(Blocks.BONE_BLOCK.getDefaultState().withProperty(BlockBone.AXIS, EnumFacing.Axis.Y));

            if(original.getBlock() == Blocks.GRASS_PATH)
                event.setReplacement(ManaCraftBlocks.mana_block.getDefaultState());

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
