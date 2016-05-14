package herbarium.common.core.journal;

import herbarium.api.collections.JournalChapterComparator;
import herbarium.api.commentarium.journal.IJournalChapter;
import herbarium.api.commentarium.journal.IJournalPage;
import herbarium.common.core.journal.pages.DelegatedJournalPage;
import herbarium.common.core.journal.renderer.ContentsPageRenderer;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.SortedSet;
import java.util.TreeSet;

public final class IndexFactory {
  public static List<IJournalPage> createChapter(){
    SortedSet<IJournalChapter> chapters = new TreeSet<>(new JournalChapterComparator());
    for(EnumJournalChapters chapter : EnumJournalChapters.values()) chapters.add(chapter);

    List<IJournalPage> pages = new LinkedList<>();

    int pageCount = 0;
    int count = 0;
    int ordinal = 0;
    while(!chapters.isEmpty()){
      List<ContentsPageRenderer.ContentsData> datas = new LinkedList<>();
      IJournalChapter chapter = chapters.first();
      chapters.remove(chapter);

      datas.add(new ContentsPageRenderer.ContentsData(chapter.uuid(), true, ordinal++));
      count++;

      if(chapter.getPages() == null || chapter.getPages().isEmpty()) continue;
      Queue<IJournalPage> ps = new LinkedList<>(chapter.getPages());
      while(!ps.isEmpty()){
        IJournalPage page = ps.remove();
        datas.add(new ContentsPageRenderer.ContentsData(page.uuid(), false, ordinal++));
        count++;

        if(count >= 13){
          pages.add(new DelegatedJournalPage(page.uuid(), pageCount++, new ContentsPageRenderer(datas)));
          datas.clear();
          count = 0;
        }
      }
    }

    return pages;
  }
}