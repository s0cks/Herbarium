package herbarium.common.core.journal;

import herbarium.api.HerbariumApi;
import herbarium.api.brew.effects.IEffect;
import herbarium.api.collections.EffectComparators;
import herbarium.api.collections.JournalPageComparator;
import herbarium.api.collections.SortedArraySet;
import herbarium.api.commentarium.journal.IJournalPage;
import herbarium.common.core.journal.pages.DelegatedJournalPage;
import herbarium.common.core.journal.renderer.ChapterHeaderRenderer;
import herbarium.common.core.journal.renderer.EffectPageRenderer;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public final class EffectChapterFactory {
  public static SortedArraySet<IJournalPage> createChapter() {
    SortedArraySet<IJournalPage> pages = new SortedArraySet<>(new JournalPageComparator());
    pages.add(new DelegatedJournalPage(EnumJournalChapters.EFFECTS.uuid(), 0,
                                       new ChapterHeaderRenderer(EnumJournalChapters.EFFECTS.uuid(),
                                                                 EnumJournalChapters.EFFECTS.ordinal())));

    Queue<IEffect> effects = new LinkedList<>(HerbariumApi.EFFECT_MANAGER.allEffects());
    Collections.sort(((LinkedList<IEffect>) effects), EffectComparators.UUID);

    int pageOrdinal = 0;
    clearIt:
    while (!effects.isEmpty()) {
      IEffect[] e = new IEffect[7];
      for (int i = 0; i <= 6; i++) {
        e[i] = effects.remove();
        if (effects.isEmpty()) break clearIt;
      }

      pages.add(new DelegatedJournalPage("herbarium.journal.pages.effects_" + pageOrdinal, pageOrdinal, new
                                                                                                            EffectPageRenderer(e)));
    }

    return pages;
  }
}