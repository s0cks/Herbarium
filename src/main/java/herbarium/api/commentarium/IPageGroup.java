package herbarium.api.commentarium;

import java.util.List;

public interface IPageGroup{
    public String uuid();
    public String localizedName();
    public int ordinal();
    public List<IPage> all();
}