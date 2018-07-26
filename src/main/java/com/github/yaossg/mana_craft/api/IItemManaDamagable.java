package com.github.yaossg.mana_craft.api;

import javax.annotation.Nonnegative;

/**
 * implement it to be a mana tool, weapon or armor
 * for a tool or weapon, mana will be dropped automatically
 * for a armor, mana has to be dropped by implementation
 * */
@FunctionalInterface
public interface IItemManaDamagable  {
    @Nonnegative
    int getManaValue();
}
