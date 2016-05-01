package herbarium.api.commentarium;

import java.util.Set;

public interface IPageManager{
    public IPageGenerator generator();
    public Set<IPage> all();
    public IPage get(String uuid);
    public void register(IPage page);
}