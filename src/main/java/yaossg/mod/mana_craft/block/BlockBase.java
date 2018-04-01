package yaossg.mod.mana_craft.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import yaossg.mod.Util;
import yaossg.mod.mana_craft.ManaCraft;

public class BlockBase extends Block {
    public BlockBase(Material material, SoundType sound) {
        super(material);
        this.setSoundType(sound);
        this.setCreativeTab(ManaCraft.tabMana);
    }
    public BlockBase(Material material, SoundType sound, int pick) {
        this(material, sound);
        this.setHarvestLevel("pickaxe", pick);
    }
    public BlockBase(Material material, SoundType sound, int pick, int light) {
        this(material, sound, pick);
        this.setLightLevel(Util.getLightLevel(light));
    }
}
