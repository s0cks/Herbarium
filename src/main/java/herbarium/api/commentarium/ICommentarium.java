package herbarium.api.commentarium;

import herbarium.api.INBTSavable;

import java.util.Set;

public interface ICommentarium
    extends INBTSavable {
  public Set<IPage> collected();
}