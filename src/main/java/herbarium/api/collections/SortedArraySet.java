package herbarium.api.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

public final class SortedArraySet<E>
extends ArrayList<E>
implements Set<E> {
  private final Comparator<E> comparator;

  public SortedArraySet(Comparator<E> comparator){
    this.comparator = comparator;
  }

  @Override
  public boolean add(E e){
    if(this.contains(e)) return false;
    boolean ret = super.add(e);
    Collections.sort(this, this.comparator);
    return ret;
  }
}