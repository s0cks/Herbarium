package herbarium.api.commentarium.journal;

import java.util.List;

public interface IJournalChapter{
  public int ordinal();
  public String uuid();
  public void insertPage(IJournalPage page);
  public List<IJournalPage> getPages();
}