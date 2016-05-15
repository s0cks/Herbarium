package herbarium.common.core.journal;

import herbarium.api.collections.JournalChapterComparator;
import herbarium.api.collections.SortedArraySet;
import herbarium.api.commentarium.journal.IJournal;
import herbarium.api.commentarium.journal.IJournalChapter;
import herbarium.api.commentarium.journal.IJournalFactory;
import herbarium.api.commentarium.journal.IJournalPage;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Collections;

public final class JournalFactory
implements IJournalFactory {
  @Override
  public IJournal create(EntityPlayer player) {
    Journal journal = new Journal();
    Collections.addAll(journal.chapters, EnumJournalChapters.values());
    return journal;
  }

  private static final class Journal
  implements IJournal {
    private final SortedArraySet<IJournalChapter> chapters = new SortedArraySet<>(new JournalChapterComparator());

    private int currentChapter;
    private int currentPage;

    @Override
    public IJournalPage left() {
      if (this.currentPage == 0) {
        if (this.currentChapter == 0) {
          return null;
        }

        IJournalChapter lastChapter = this.chapters.get(this.currentChapter - 1);
        if (lastChapter.getPages()
                       .isEmpty()) return null;
        return lastChapter.getPages()
                          .get(lastChapter.getPages()
                                          .size() - 1);
      } else {
        return this.chapters.get(this.currentChapter)
                            .getPages()
                            .get(this.currentPage - 1);
      }
    }

    @Override
    public IJournalPage right() {
      if (this.chapters.get(this.currentChapter)
                       .getPages()
                       .isEmpty()) return null;
      return this.chapters.get(this.currentChapter)
                          .getPages()
                          .get(this.currentPage);
    }

    @Override
    public void advance() {
      this.currentPage++;

      IJournalChapter currChapt = this.chapters.get(this.currentChapter);
      if (this.currentPage >= currChapt.getPages()
                                       .size()) {
        this.currentChapter++;
        if (this.currentChapter >= this.chapters.size()) {
          this.currentChapter = this.chapters.size() - 1;
          this.currentPage--;
          return;
        }

        this.currentPage = 0;
      }
    }

    @Override
    public void retreat() {
      this.currentPage--;

      if (this.currentPage < 0) {
        this.currentChapter--;
        if (this.currentChapter < 0) {
          this.currentChapter = 0;
          return;
        }

        this.currentPage = this.chapters.get(this.currentChapter)
                                        .getPages()
                                        .size() - 1;
      }
    }
  }
}