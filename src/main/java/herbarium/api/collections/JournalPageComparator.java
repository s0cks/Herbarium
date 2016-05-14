package herbarium.api.collections;

import herbarium.api.commentarium.journal.IJournalPage;

import java.util.Comparator;

public final class JournalPageComparator
implements Comparator<IJournalPage> {
  @Override
  public int compare(IJournalPage t0, IJournalPage t1) {
    return Integer.compare(t0.ordinal(), t1.ordinal());
  }
}