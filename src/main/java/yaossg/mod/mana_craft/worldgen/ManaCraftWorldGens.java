package yaossg.mod.mana_craft.worldgen;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.registry.GameRegistry;
import yaossg.mod.mana_craft.Config;
import yaossg.mod.mana_craft.block.ManaCraftBlocks;

import java.util.Random;

public class ManaCraftWorldGens {
    private static BlockPos randBlockPos(int chunkX, int chunkZ, Random rand, int highest) {
        return new BlockPos((chunkX << 4) + rand.nextInt(16), 1 + rand.nextInt(highest), (chunkZ << 4) + rand.nextInt(16));
    }
    private static final WorldGenMinable MANA = new WorldGenMinable(ManaCraftBlocks.blockManaOre.getDefaultState(), Config.mana_ore_size);
    private static final WorldGenMinable MANAINGOT = new WorldGenMinable(ManaCraftBlocks.blockManaIngotOre.getDefaultState(), Config.mana_ingot_ore_size);
    public static void init()
    {
        if(Config.weight > 0)
            GameRegistry.registerWorldGenerator((random, chunkX, chunkZ, world, chunkGenerator, chunkProvider) -> {
                for(int i = 0; i < Config.mana_ore_times; ++i)
                    MANA.generate(world, random, randBlockPos(chunkX, chunkZ, random, Config.mana_ore_height));
                for(int i = 0; i < Config.mana_ingot_ore_times; ++i)
                    MANAINGOT.generate(world, random, randBlockPos(chunkX, chunkZ, random, Config.mana_ingot_ore_height));
                if(random.nextInt(Config.mixture_chance - 1) == 0) {
                    BlockPos genPos = randBlockPos(chunkX, chunkZ, random, Config.mixture_height);
                    for (int i = 0; i < Config.mixture_times; ++i) {
                        MANA.generate(world, random, genPos);
                        MANAINGOT.generate(world, random, genPos);
                    }
                }
            }, Config.weight);
    }

}
