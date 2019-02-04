package mana_craft.api.registry;

import sausage_core.api.util.registry.SausageRegistry;

public class ManaCraftRegistries {
    public static final SausageRegistry<MPRecipe> MP_RECIPES = new SausageRegistry<>(MPRecipe.class);
    public static final SausageRegistry<MBFuel> MB_FUELS = new SausageRegistry<>(MBFuel.class);
    public static final SausageRegistry<ManaBoostItem> BOOST_ITEM = new SausageRegistry<>(ManaBoostItem.class);
}