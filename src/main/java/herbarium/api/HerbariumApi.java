package herbarium.api;

import herbarium.api.brew.IMixerFactory;
import herbarium.api.commentarium.IPageManager;
import herbarium.api.commentarium.IPageTracker;

public final class HerbariumApi{
    public static IPageManager PAGE_MANAGER;
    public static IPageTracker PAGE_TRACKER;
    public static IMixerFactory MIXER_FACTORY;
}