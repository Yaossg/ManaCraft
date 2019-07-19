package mana_craft.loot;

import mana_craft.ManaCraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.ArrayUtils;

public class ManaCraftLoots {
	static final String[] NAMES = {
			"abandoned_mineshaft",
			"desert_pyramid",
			"jungle_temple",
			"simple_dungeon",
			"spawn_bonus_chest",
			"village_blacksmith",
			"junk",
			"treasure"
	};
	static final String INJECT = "inject/";

	public static void init() {
		MinecraftForge.EVENT_BUS.register(ManaCraftLoots.class);
		LootTableList.register(new ResourceLocation(ManaCraft.MODID, "mana_shooter"));
		for (String name : NAMES) LootTableList.register(new ResourceLocation(ManaCraft.MODID, INJECT + name));
	}

	@SubscribeEvent
	public static void onLootLoad(LootTableLoadEvent event) {
		String name = event.getName().toString();
		inject(event, name, "minecraft:chests/");
		inject(event, name, "minecraft:gameplay/fishing/");
	}

	static void inject(LootTableLoadEvent event, String name, String prefix) {
		if (name.startsWith(prefix)) {
			String suffix = name.substring(prefix.length());
			if (ArrayUtils.contains(NAMES, suffix)) event.getTable().addPool(getInjectPool(suffix));
		}
	}

	static LootPool getInjectPool(String name) {
		return new LootPool(
				new LootEntry[]{new LootEntryTable(
						new ResourceLocation(ManaCraft.MODID, INJECT + name), 1, 0,
						new LootCondition[0], ManaCraft.MODID + "_inject_entry"
				)}, new LootCondition[0], new RandomValueRange(1), new RandomValueRange(0, 1),
				ManaCraft.MODID + "_inject_pool"
		);
	}
}
