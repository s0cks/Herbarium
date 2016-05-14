package herbarium.api.collections;

import herbarium.api.commentarium.journal.IJournalChapter;

import java.util.Comparator;

public final class JournalChapterComparator
implements Comparator<IJournalChapter>{
  @Override
  public int compare(IJournalChapter t0, IJournalChapter t1) {
    return Integer.compare(t0.ordinal(), t1.ordinal());
  }
}