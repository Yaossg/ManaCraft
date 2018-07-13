package com.github.yaossg.mana_craft.api;

import java.util.function.IntSupplier;

/**
 * @author Yaossg
 * implement it to be a mana tool, weapon or armor
 * */
@FunctionalInterface
public interface IItemManaDamagable extends IntSupplier {
    int getManaValue();

    @Override
    default int getAsInt() {
        return getManaValue();
    }
}
