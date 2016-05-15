package herbarium.common.core.journal;

import herbarium.api.commentarium.journal.IJournalPage;
import herbarium.api.commentarium.journal.IJournalPageRenderer;

public final class DelegatedJournalPage
implements IJournalPage {
  private final int ordinal;
  private final String uuid;
  private final IJournalPageRenderer delegate;

  public DelegatedJournalPage(String uuid, int ordinal, IJournalPageRenderer delegate) {
    this.ordinal = ordinal;
    this.uuid = uuid;
    this.delegate = delegate;
  }

  @Override
  public int ordinal() {
    return this.ordinal;
  }

  @Override
  public String uuid() {
    return this.uuid;
  }

  @Override
  public IJournalPageRenderer delegate() {
    return this.delegate;
  }
}