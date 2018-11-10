package mana_craft.world.biome;

import mana_craft.ManaCraft;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

import static net.minecraftforge.common.BiomeDictionary.Type;
import static net.minecraftforge.common.BiomeDictionary.addTypes;
import static net.minecraftforge.common.BiomeManager.*;
import static net.minecraftforge.common.BiomeManager.BiomeType.WARM;

@ObjectHolder(ManaCraft.MODID)
public class ManaCraftBiomes {
    public static final Biome mana = null;
    public static final Biome mana_hills = null;
    public static final Biome mana_chaos = null;
    public static final Biome mana_chaos_hills = null;
    public static void init() {
        addBiome(WARM, new BiomeEntry(mana, 6));
        addTypes(mana, Type.MAGICAL);
        addBiome(WARM, new BiomeEntry(mana_hills, 3));
        addTypes(mana_hills, Type.MAGICAL, Type.HILLS);

        addBiome(WARM, new BiomeEntry(mana_chaos, 4));
        addTypes(mana_chaos, Type.MAGICAL, Type.RARE);
        addBiome(WARM, new BiomeEntry(mana_chaos_hills, 2));
        addTypes(mana_chaos_hills, Type.MAGICAL, Type.HILLS, Type.RARE);

        addVillageBiome(mana, true);
    }
}
