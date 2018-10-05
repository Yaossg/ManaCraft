package mana_craft.sound;

import mana_craft.ManaCraft;
import net.minecraft.util.SoundEvent;
import sausage_core.api.util.registry.SoundRegistryManager;

public class ManaCraftSounds {
    public static final SoundRegistryManager manager = new SoundRegistryManager(ManaCraft.MODID);
    public static final SoundEvent record = manager.addSound("record");
    public static void init() {}
}
