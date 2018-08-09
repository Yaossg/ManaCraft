package com.github.yaossg.mana_craft.world.gen;

import com.github.yaossg.mana_craft.ManaCraft;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

import static net.minecraft.world.gen.structure.MapGenStructureIO.*;
import static net.minecraftforge.fml.common.registry.VillagerRegistry.*;

public class ManaCraftWorldGens {
    public static void init() {
        GameRegistry.registerWorldGenerator(new WorldGenMana(), ManaCraft.MODID.hashCode());
        registerStructureComponent(StructureVillageMP.class, ManaCraft.MODID + ":ViMP");
        instance().registerVillageCreationHandler(new StructureVillageMP.Handler());
        registerStructureComponent(StructureVillageML.class, ManaCraft.MODID + ":ViML");
        instance().registerVillageCreationHandler(new StructureVillageML.Handler());
    }
}
