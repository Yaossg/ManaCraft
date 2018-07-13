package com.github.yaossg.mana_craft.api;

import java.util.function.IntSupplier;

/**
 * @author Yaossg
 * implement it to be a mana tool or weapon
 * HAVE NO EFFECT ON ARMOR
 * */
@FunctionalInterface
public interface IItemManaTool extends IntSupplier {
    int getManaValue();

    @Override
    default int getAsInt() {
        return getManaValue();
    }
}
