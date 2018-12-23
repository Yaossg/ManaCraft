package mana_craft.api.registry;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class ManaBoostable<TE extends TileEntity> {
    public final Class<TE> clazz;
    public final Predicate<TE> canBoost;
    public final Consumer<TE> boost;

    public ManaBoostable(Class<TE> clazz,
                         Predicate<TE> canBoost,
                         Consumer<TE> boost) {
        this.clazz = clazz;
        this.canBoost = canBoost;
        this.boost = boost;
    }

}
