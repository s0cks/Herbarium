package herbarium.api.commentarium.journal;

public interface IJournal {
  public IJournalPage left();

  public IJournalPage right();

  public void advance();

  public void retreat();
}