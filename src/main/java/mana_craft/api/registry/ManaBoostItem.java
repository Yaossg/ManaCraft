package mana_craft.api.registry;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

import javax.annotation.Nonnull;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Carries information about a type of {@link TileEntity} that can be boosted by Mana Booster
 * */
public final class ManaBoostItem<TE extends TileEntity> {
    /**
     * class of the {@link TileEntity},
     * all instance of this class can be boosted by <code>this</code>
     * */
    public final Class<TE> clazz;
    /**
     * check if this {@link TileEntity} can be boosted now, always call before {@link #boost}
     * */
    public final Predicate<TE> canBoost;
    /**
     * define how to boost this {@link TileEntity},
     * usually call {@link ITickable#update()}
     * */
    public final Consumer<TE> boost;

    public ManaBoostItem(@Nonnull Class<TE> clazz,
                         @Nonnull Predicate<TE> canBoost,
                         @Nonnull Consumer<TE> boost) {
        this.clazz = checkNotNull(clazz);
        this.canBoost = checkNotNull(canBoost);
        this.boost = checkNotNull(boost);
    }
}
