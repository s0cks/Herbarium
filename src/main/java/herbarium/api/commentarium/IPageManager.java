package herbarium.api.commentarium;

import java.util.Set;

public interface IPageManager{
    public IPageGenerator generator();
    public Set<IPage> all();
    public void register(IPage page);
}