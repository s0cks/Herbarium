package herbarium.api.commentarium;

public interface IPage {
  public String title();

  public String uuid();

  public String description();

  public IPageRenderer renderer();

  public IPageGroup group();

  public boolean flipped();

  public void flip();
}