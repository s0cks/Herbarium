package herbarium.api.commentarium;

import java.util.List;
import java.util.Set;

public interface IPageManager {
  public Set<IPage> all();

  public IPage get(String uuid);

  public IPageGroup getPageGroup(String uuid);

  public List<IPageGroup> sortedGroups();

  public void register(IPage page);

  public void register(IPageGroup group);
}