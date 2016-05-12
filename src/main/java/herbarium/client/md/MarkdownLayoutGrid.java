package herbarium.client.md;

public final class MarkdownLayoutGrid
implements IMarkdownComponentLayout{
    private int numRows;
    private int numCols;

    public MarkdownLayoutGrid(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
    }

    @Override
    public void layout(MarkdownComponentContainer container) {
        int numComponents = container.componentCount();
        if(numComponents == 0){
            return;
        }

        if(this.numRows > 0){
            this.numCols = (numComponents + this.numRows - 1) / this.numRows;
        } else{
            this.numRows = (numComponents + this.numCols - 1) / this.numCols;
        }

        int totalGapWidth = (this.numCols - 1);
        int width = (container.getWidth() - totalGapWidth) / this.numCols;
        int extraWidth = (container.getWidth() - (width * this.numCols + totalGapWidth)) / 2;

        int totalGapHeight = (this.numRows - 1);
        int height = (container.getHeight() - totalGapHeight) / this.numRows;
        int extraHeight = (container.getHeight() - (height * this.numRows + totalGapHeight)) / 2;

        for(int c = 0, x = extraWidth; c < this.numCols; c++, x += width){
            for(int r = 0, y = extraHeight; r < this.numRows; r++, y += height){
                int i = r * this.numCols + c;
                if(i < numComponents){
                    container.componentAt(i).setGeometry(x, y, width, height);
                }
            }
        }
    }
}
