package com.github.yaossg.mana_craft.api;

import java.util.function.IntSupplier;

@FunctionalInterface
public interface IItemManaTool extends IntSupplier {
    int getManaValue();

    @Override
    default int getAsInt() {
        return getManaValue();
    }
}
