package herbarium.common.core.commentarium;

import herbarium.api.commentarium.pages.IPage;
import herbarium.api.commentarium.pages.IPageGroup;
import herbarium.api.commentarium.pages.IPageRenderer;
import net.minecraft.util.text.translation.I18n;

public final class Page
    implements IPage {
  private final String title;
  private final IPageRenderer renderer;
  private final IPageGroup group;
  private boolean flipped = false;

  protected Page(PageBuilder builder) {
    this.title = builder.title;
    this.renderer = builder.renderer;
    this.group = builder.group;
  }

  @Override
  public String title() {
    return this.title;
  }

  @Override
  public String uuid() {
    return "herbarium.page." + this.title.toLowerCase();
  }

  @Override
  public String description() {
    return I18n.translateToLocal(this.uuid());
  }

  @Override
  public IPageRenderer renderer() {
    return this.renderer;
  }

  @Override
  public IPageGroup group() {
    return this.group;
  }

  @Override
  public boolean flipped() {
    return this.flipped;
  }

  @Override
  public void flip() {
    this.flipped = !this.flipped;
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof Page
               && ((Page) obj).uuid()
                              .equals(this.uuid());
  }
}