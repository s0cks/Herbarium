package herbarium.common.core.commentarium;

import herbarium.api.commentarium.pages.IPageGroup;
import herbarium.api.commentarium.pages.IPageRenderer;

public final class PageBuilder {
  protected IPageGroup group;
  protected String title;
  protected IPageRenderer renderer;

  public PageBuilder setTitle(String title) {
    this.title = title;
    return this;
  }

  public PageBuilder setGroup(IPageGroup group) {
    this.group = group;
    return this;
  }

  public PageBuilder setRenderer(IPageRenderer renderer) {
    this.renderer = renderer;
    return this;
  }

  public Page build() {
    return new Page(this);
  }
}