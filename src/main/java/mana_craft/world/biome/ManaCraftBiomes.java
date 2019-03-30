package mana_craft.world.biome;

import mana_craft.ManaCraft;
import mana_craft.config.ManaCraftConfig;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

import static net.minecraftforge.common.BiomeDictionary.Type;
import static net.minecraftforge.common.BiomeDictionary.addTypes;
import static net.minecraftforge.common.BiomeManager.*;
import static net.minecraftforge.common.BiomeManager.BiomeType.WARM;
import static sausage_core.api.util.common.SausageUtils.nonnull;

@ObjectHolder(ManaCraft.MODID)
public class ManaCraftBiomes {
	public static final Biome mana = nonnull();
	public static final Biome mana_hills = nonnull();
	public static final Biome mana_chaos = nonnull();
	public static final Biome mana_chaos_hills = nonnull();

	public static void init() {
		addBiome(WARM, new BiomeEntry(mana, 6));
		addTypes(mana, Type.MAGICAL);
		addBiome(WARM, new BiomeEntry(mana_hills, 3));
		addTypes(mana_hills, Type.MAGICAL, Type.HILLS);

		addBiome(WARM, new BiomeEntry(mana_chaos, 4));
		addTypes(mana_chaos, Type.MAGICAL, Type.RARE);
		addBiome(WARM, new BiomeEntry(mana_chaos_hills, 2));
		addTypes(mana_chaos_hills, Type.MAGICAL, Type.HILLS, Type.RARE);
		if(ManaCraftConfig.village)
			addVillageBiome(mana, true);
	}
}
