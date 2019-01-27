package mana_craft.api.registry;

import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nonnull;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static com.google.common.base.Preconditions.checkNotNull;

/*
* Declares to be boostable by Mana Booster
* */
public final class ManaBoostable<TE extends TileEntity> {
    public final Class<TE> clazz;
    public final Predicate<TE> canBoost;
    public final Consumer<TE> boost;

    public ManaBoostable(@Nonnull Class<TE> clazz,
                         @Nonnull Predicate<TE> canBoost,
                         @Nonnull Consumer<TE> boost) {
        this.clazz = checkNotNull(clazz);
        this.canBoost = checkNotNull(canBoost);
        this.boost = checkNotNull(boost);
    }
}
