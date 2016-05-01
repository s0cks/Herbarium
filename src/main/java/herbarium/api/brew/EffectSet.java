package herbarium.api.brew;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public final class EffectSet
implements Iterable<IEffect>{
    private final List<IEffect> internal = new LinkedList<>();

    public void add(IEffect effect){
        for(IEffect e : this.internal){
            if(e.equals(effect)){
                return;
            }
        }

        this.internal.add(effect);
    }

    @Override
    public Iterator<IEffect> iterator() {
        return this.internal.iterator();
    }

    public int size(){
        return this.internal.size();
    }

    public void remove(int index){
        this.internal.remove(index);
    }

    public IEffect get(int index){
        return this.internal.get(index);
    }
}