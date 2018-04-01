package yaossg.mod.mana_craft.event;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import yaossg.mod.mana_craft.block.ManaCraftBlocks;

import java.util.Random;


public class OreGenerateEvent {
    private static BlockPos randBlockpos(BlockPos pos ,Random rand, int highest) {
        int posY  = rand.nextInt(highest);
        return new BlockPos(pos.getX() + rand.nextInt(16), posY, pos.getZ() + rand.nextInt(16));
    }
    public static void init() {
        MinecraftForge.ORE_GEN_BUS.register(OreGenerateEvent.class);
    }
    private static WorldGenerator ManaGen = new WorldGenMinable(ManaCraftBlocks.blockManaOre.getDefaultState(), 12);
    private static WorldGenerator ManaIngotGen = new WorldGenMinable(ManaCraftBlocks.blockManaIngotOre.getDefaultState(), 8);
    @SubscribeEvent
    public static void onGenerateMinable(OreGenEvent.GenerateMinable event) {
        BlockPos pos = event.getPos();
        Random rand = event.getRand();
        if (event.getType() == OreGenEvent.GenerateMinable.EventType.REDSTONE) {
            if (TerrainGen.generateOre(event.getWorld(), rand, ManaGen, pos, OreGenEvent.GenerateMinable.EventType.CUSTOM)) {
                ManaGen.generate(event.getWorld(), rand, randBlockpos(pos, rand, 24));
                ManaGen.generate(event.getWorld(), rand, randBlockpos(pos, rand, 24));
            }
        }
        if(event.getType() == OreGenEvent.GenerateMinable.EventType.GOLD) {
            if (TerrainGen.generateOre(event.getWorld(), rand, ManaIngotGen, pos, OreGenEvent.GenerateMinable.EventType.CUSTOM)) {
                ManaIngotGen.generate(event.getWorld(), rand, randBlockpos(pos, rand, 20));
            }
        }
        if(event.getType() == OreGenEvent.GenerateMinable.EventType.DIAMOND) {
            if(TerrainGen.generateOre(event.getWorld(), rand, ManaGen, pos, OreGenEvent.GenerateMinable.EventType.CUSTOM)
                    && TerrainGen.generateOre(event.getWorld(), rand, ManaIngotGen, pos, OreGenEvent.GenerateMinable.EventType.CUSTOM)) {
                BlockPos genPos = randBlockpos(pos, rand, 18);
                for (int i = 0; i < 5; i++) {
                    ManaGen.generate(event.getWorld(), rand, genPos);
                    ManaIngotGen.generate(event.getWorld(), rand, genPos);
                }
            }
        }
    }
}
