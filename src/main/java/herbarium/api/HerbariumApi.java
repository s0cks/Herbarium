package herbarium.api;

import herbarium.api.brew.effects.IEffectManager;
import herbarium.api.brew.effects.IEffectTracker;
import herbarium.api.commentarium.IPageManager;
import herbarium.api.commentarium.IPageTracker;
import herbarium.api.ruins.IRuinManager;

public final class HerbariumApi{
    // Pages
    public static IPageManager PAGE_MANAGER;
    public static IPageTracker PAGE_TRACKER;

    // Flowers
    public static IFlowerManager FLOWER_MANAGER;

    // Ruins
    public static IRuinManager RUIN_MANAGER;

    // Effects
    public static IEffectTracker EFFECT_TRACKER;
    public static IEffectManager EFFECT_MANAGER;
}