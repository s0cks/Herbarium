package herbarium.common.core.journal;

import herbarium.api.collections.JournalPageComparator;
import herbarium.api.collections.SortedArraySet;
import herbarium.api.commentarium.journal.IJournalChapter;
import herbarium.api.commentarium.journal.IJournalPage;
import herbarium.common.core.journal.renderer.ChapterHeaderRenderer;
import herbarium.common.core.journal.renderer.ContentsPageRenderer;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public final class IndexChapterFactory {
  public static List<IJournalPage> createChapter() {
    Queue<IJournalChapter> chapters = new LinkedList<>();
    chapters.add(EnumJournalChapters.FLOWERS);
    chapters.add(EnumJournalChapters.EFFECTS);

    SortedArraySet<IJournalPage> pages = new SortedArraySet<>(new JournalPageComparator());
    pages.add(new JournalPage(EnumJournalChapters.INDEX.uuid(), 0,
                              new ChapterHeaderRenderer(EnumJournalChapters.INDEX.uuid(),
                                                        EnumJournalChapters.INDEX.ordinal())));

    int pageCount = 0;
    int count = 0;
    int ordinal = 0;
    while (!chapters.isEmpty()) {
      List<ContentsPageRenderer.ContentsData> datas = new LinkedList<>();
      IJournalChapter chapter = chapters.remove();

      datas.add(new ContentsPageRenderer.ContentsData(chapter.uuid(), true, ordinal++));
      count++;

      if (chapter.getPages() == null || chapter.getPages()
                                               .isEmpty()) {
        continue;
      }

      Queue<IJournalPage> ps = new LinkedList<>(chapter.getPages());
      ps.remove(); // Remove header
      while (!ps.isEmpty()) {
        IJournalPage page = ps.remove();
        datas.add(new ContentsPageRenderer.ContentsData(page.uuid(), false, ordinal++));

        count++;
        if (count >= 13) {
          pages.add(new JournalPage("herbarium.journal.pages.index." + pageCount, pageCount++, new
                                                                                               ContentsPageRenderer(datas)));
          datas.clear();
          count = 0;
        } else if (ps.size() <= 14) {
          break;
        }
      }

      if (!ps.isEmpty()) {
        while (!ps.isEmpty()) {
          IJournalPage page = ps.remove();
          datas.add(new ContentsPageRenderer.ContentsData(page.uuid(), false, ordinal++));
        }

        pages.add(new JournalPage("herbarium.journal.pages.index." + pageCount, pageCount++, new
                                                                                             ContentsPageRenderer(datas)));
      }
    }

    return pages;
  }
}