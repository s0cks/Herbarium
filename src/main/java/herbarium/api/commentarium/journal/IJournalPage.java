package herbarium.api.commentarium.journal;

public interface IJournalPage{
  public int ordinal();
  public String uuid();
  public IJournalPageRenderer delegate();
}