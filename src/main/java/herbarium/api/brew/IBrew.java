package herbarium.api.brew;

import herbarium.api.INBTSavable;
import herbarium.api.brew.effects.IEffect;

import java.util.List;

public interface IBrew
extends INBTSavable{
    public List<IEffect> effects();
}