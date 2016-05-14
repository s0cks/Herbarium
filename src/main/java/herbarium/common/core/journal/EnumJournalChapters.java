package herbarium.common.core.journal;

import herbarium.api.collections.JournalPageComparator;
import herbarium.api.collections.SortedArraySet;
import herbarium.api.commentarium.journal.IJournalChapter;
import herbarium.api.commentarium.journal.IJournalPage;
import herbarium.common.core.journal.pages.DelegatedJournalPage;
import herbarium.common.core.journal.renderer.TitlePageRenderer;

import java.util.LinkedList;
import java.util.List;

public enum EnumJournalChapters
implements IJournalChapter{
  INTRO {
    @Override
    protected List<IJournalPage> initialize() {
      SortedArraySet<IJournalPage> pages = new SortedArraySet<>(new JournalPageComparator());
      pages.add(new DelegatedJournalPage("herbarium.journal.intro.title", 0, new TitlePageRenderer()));
      return pages;
    }
  },
  FLOWERS {
    @Override
    protected List<IJournalPage> initialize() {
      return new LinkedList<>();
    }
  },
  EFFECTS {
    @Override
    protected List<IJournalPage> initialize() {
      return EffectChapterFactory.createChapter();
    }
  },
  INDEX() {
    @Override
    protected List<IJournalPage> initialize() {
      return IndexFactory.createChapter();
    }
  };

  private List<IJournalPage> pages;

  protected abstract List<IJournalPage> initialize();

  @Override
  public String uuid() {
    return "herbarium.journal.chapters." + this.name().toLowerCase();
  }

  public static void init(){
    for(EnumJournalChapters value : values()){
      value.pages = value.initialize();
    }
  }

  @Override
  public void insertPage(IJournalPage page){
    this.pages.add(page);
  }

  @Override
  public List<IJournalPage> getPages() {
    return this.pages;
  }
}