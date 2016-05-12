package herbarium.common.core.commentarium;

import herbarium.api.HerbariumApi;
import herbarium.api.commentarium.IPage;
import herbarium.api.commentarium.IPageGroup;

import java.util.LinkedList;
import java.util.List;

public enum PageGroups
implements IPageGroup{
    FLOWERS("Flowers"),
    BLOCKS("Blocks"),
    EFFECTS("Effects");

    private final String name;

    PageGroups(String name){
        this.name = name;
    }

    @Override
    public String localizedName(){
        return this.name;
    }

    @Override
    public String uuid() {
        return "herbarium.pageGroup." + this.name().toLowerCase();
    }

    @Override
    public List<IPage> all() {
        List<IPage> all = new LinkedList<>();
        for(IPage page : HerbariumApi.PAGE_MANAGER.all()){
            if(page.group().equals(this)) all.add(page);
        }
        return all;
    }
}