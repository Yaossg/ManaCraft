package mana_craft.api.registry;

import sausage_core.api.util.registry.SausageRegistry;

public interface IManaCraftRegistries {
    SausageRegistry<MPRecipe> MP_RECIPES = new SausageRegistry<>(MPRecipe.class);
    SausageRegistry<MBFuel> MB_FUELS = new SausageRegistry<>(MBFuel.class);
    SausageRegistry<ManaBoostItem> BOOST_ITEM = new SausageRegistry<>(ManaBoostItem.class);
}