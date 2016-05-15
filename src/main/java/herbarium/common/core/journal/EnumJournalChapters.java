package herbarium.common.core.journal;

import herbarium.api.collections.JournalPageComparator;
import herbarium.api.collections.SortedArraySet;
import herbarium.api.commentarium.journal.IJournalChapter;
import herbarium.api.commentarium.journal.IJournalPage;
import herbarium.common.core.journal.renderer.ChapterHeaderRenderer;
import herbarium.common.core.journal.renderer.TitlePageRenderer;

import java.util.List;

public enum EnumJournalChapters
implements IJournalChapter {
  INTRO {
    @Override
    protected List<IJournalPage> initialize() {
      SortedArraySet<IJournalPage> pages = new SortedArraySet<>(new JournalPageComparator());
      pages.add(new DelegatedJournalPage(uuid(), 1, new TitlePageRenderer()));
      return pages;
    }
  },
  FLOWERS {
    @Override
    protected List<IJournalPage> initialize() {
      SortedArraySet<IJournalPage> pages = new SortedArraySet<>(new JournalPageComparator());
      pages.add(new DelegatedJournalPage(uuid(), 1, new ChapterHeaderRenderer(uuid(), ordinal())));
      return pages;
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
      return IndexChapterFactory.createChapter();
    }
  };

  private List<IJournalPage> pages;

  public static void init() {
    for (EnumJournalChapters value : values()) {
      value.pages = value.initialize();
    }
  }

  protected abstract List<IJournalPage> initialize();

  @Override
  public String uuid() {
    return "herbarium.journal.chapters." + this.name()
                                               .toLowerCase();
  }

  @Override
  public void insertPage(IJournalPage page) {
    this.pages.add(page);
  }

  @Override
  public List<IJournalPage> getPages() {
    return this.pages;
  }
}