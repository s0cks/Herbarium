package herbarium.client.md;

import java.awt.Point;

public final class MarkdownComponentParagraph
extends AbstractMarkdownComponent{
    public static final int DEFAULT_MAX_HEIGHT = 50;

    private final String text;
    private final int maxHeight;

    public MarkdownComponentParagraph(String text, int maxHeight, Point position) {
        super(position);
        this.text = text;
        this.maxHeight = maxHeight;
    }

    public MarkdownComponentParagraph(String text, Point position){
        this(text, DEFAULT_MAX_HEIGHT, position);
    }

    @Override
    public void render(int x, int y, float partial) {

    }
}