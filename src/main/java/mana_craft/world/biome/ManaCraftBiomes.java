package mana_craft.world.biome;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import static net.minecraftforge.common.BiomeDictionary.Type;
import static net.minecraftforge.common.BiomeDictionary.addTypes;
import static net.minecraftforge.common.BiomeManager.*;
import static net.minecraftforge.common.BiomeManager.BiomeType.WARM;

public class ManaCraftBiomes {
    public static final Biome mana = BiomeMana.get();
    public static final Biome manaHills = BiomeMana.getHills();
    public static final Biome manaChaos = BiomeManaChaos.get();
    public static final Biome manaChaosHills = BiomeManaChaos.getHills();
    public static void init() {
        ForgeRegistries.BIOMES.register(mana.setRegistryName("mana"));
        addBiome(WARM, new BiomeEntry(mana, 6));
        addTypes(mana, Type.MAGICAL);
        ForgeRegistries.BIOMES.register(manaHills.setRegistryName("mana_hills"));
        addBiome(WARM, new BiomeEntry(manaHills, 3));
        addTypes(manaHills, Type.MAGICAL, Type.HILLS);

        ForgeRegistries.BIOMES.register(manaChaos.setRegistryName("mana_chaos"));
        addBiome(WARM, new BiomeEntry(manaChaos, 4));
        addTypes(manaChaos, Type.MAGICAL, Type.RARE);
        ForgeRegistries.BIOMES.register(manaChaosHills.setRegistryName("mana_chaos_hills"));
        addBiome(WARM, new BiomeEntry(manaChaosHills, 2));
        addTypes(manaChaosHills, Type.MAGICAL, Type.HILLS, Type.RARE);

        addVillageBiome(mana, true);
    }
}
