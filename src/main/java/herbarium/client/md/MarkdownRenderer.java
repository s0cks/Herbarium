package herbarium.client.md;

import herbarium.common.Herbarium;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.parboiled.Parboiled;
import org.pegdown.Extensions;
import org.pegdown.Parser;
import org.pegdown.ast.AbbreviationNode;
import org.pegdown.ast.AnchorLinkNode;
import org.pegdown.ast.AutoLinkNode;
import org.pegdown.ast.BlockQuoteNode;
import org.pegdown.ast.BulletListNode;
import org.pegdown.ast.CodeNode;
import org.pegdown.ast.DefinitionListNode;
import org.pegdown.ast.DefinitionNode;
import org.pegdown.ast.DefinitionTermNode;
import org.pegdown.ast.ExpImageNode;
import org.pegdown.ast.ExpLinkNode;
import org.pegdown.ast.HeaderNode;
import org.pegdown.ast.HtmlBlockNode;
import org.pegdown.ast.InlineHtmlNode;
import org.pegdown.ast.ListItemNode;
import org.pegdown.ast.MailLinkNode;
import org.pegdown.ast.Node;
import org.pegdown.ast.OrderedListNode;
import org.pegdown.ast.ParaNode;
import org.pegdown.ast.QuotedNode;
import org.pegdown.ast.RefImageNode;
import org.pegdown.ast.RefLinkNode;
import org.pegdown.ast.ReferenceNode;
import org.pegdown.ast.RootNode;
import org.pegdown.ast.SimpleNode;
import org.pegdown.ast.SpecialTextNode;
import org.pegdown.ast.StrikeNode;
import org.pegdown.ast.StrongEmphSuperNode;
import org.pegdown.ast.SuperNode;
import org.pegdown.ast.TableBodyNode;
import org.pegdown.ast.TableCaptionNode;
import org.pegdown.ast.TableCellNode;
import org.pegdown.ast.TableColumnNode;
import org.pegdown.ast.TableHeaderNode;
import org.pegdown.ast.TableNode;
import org.pegdown.ast.TableRowNode;
import org.pegdown.ast.TextNode;
import org.pegdown.ast.VerbatimNode;
import org.pegdown.ast.Visitor;
import org.pegdown.ast.WikiLinkNode;
import org.pegdown.plugins.PegDownPlugins;

import java.awt.Point;
import java.io.InputStream;

public final class MarkdownRenderer
implements Visitor{
    private final IMarkdownScreen screen;
    private final int yPad;
    private int x;
    private int y;

    public MarkdownRenderer(IMarkdownScreen screen, int yPad){
        this.screen = screen;
        this.yPad = yPad;
    }

    public static MarkdownComponentContainer render(ResourceLocation loc){
        MarkdownComponentContainer container = new MarkdownComponentContainer();
        (new MarkdownRenderer(container, 10)).render(Herbarium.proxy.getClient(), loc);
        return container;
    }

    public void render(Minecraft mc, ResourceLocation loc){
        try(InputStream is = mc.getResourceManager().getResource(loc).getInputStream()){
            Parser parser = Parboiled.createParser(
                    Parser.class,
                    Extensions.TABLES|Extensions.QUOTES|Extensions.FENCED_CODE_BLOCKS,
                    2000L,
                    Parser.DefaultParseRunnerProvider,
                    PegDownPlugins.NONE
            );
            parser.parse(IOUtils.toCharArray(is)).accept(this);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void visit(AbbreviationNode node) {
    }

    @Override
    public void visit(AnchorLinkNode node) {

    }

    @Override
    public void visit(AutoLinkNode node) {

    }

    @Override
    public void visit(BlockQuoteNode node) {
    }

    @Override
    public void visit(BulletListNode node) {

    }

    @Override
    public void visit(CodeNode node) {
    }

    @Override
    public void visit(DefinitionListNode node) {

    }

    @Override
    public void visit(DefinitionNode node) {

    }

    @Override
    public void visit(DefinitionTermNode node) {

    }

    @Override
    public void visit(ExpImageNode node) {
        System.out.println("[1]Loading image: " + node.title);
        MarkdownComponentImage image;
        this.screen.register(image = new MarkdownComponentImage(node.url, new Point(this.x, this.y)));
        this.y += image.getHeight() + this.yPad;
    }

    @Override
    public void visit(ExpLinkNode node) {

    }

    @Override
    public void visit(HeaderNode node) {

    }

    @Override
    public void visit(HtmlBlockNode node) {

    }

    @Override
    public void visit(InlineHtmlNode node) {

    }

    @Override
    public void visit(ListItemNode node) {

    }

    @Override
    public void visit(MailLinkNode node) {

    }

    @Override
    public void visit(OrderedListNode node) {

    }

    @Override
    public void visit(ParaNode node) {
        this.visitChildren(node);
    }

    @Override
    public void visit(QuotedNode node) {

    }

    @Override
    public void visit(ReferenceNode node) {

    }

    @Override
    public void visit(RefImageNode node) {
    }

    @Override
    public void visit(RefLinkNode node) {

    }

    @Override
    public void visit(RootNode node) {
        this.visitChildren(node);
    }

    @Override
    public void visit(SimpleNode node) {

    }

    @Override
    public void visit(SpecialTextNode node) {

    }

    @Override
    public void visit(StrikeNode node) {

    }

    @Override
    public void visit(StrongEmphSuperNode node) {

    }

    @Override
    public void visit(TableBodyNode node) {

    }

    @Override
    public void visit(TableCaptionNode node) {

    }

    @Override
    public void visit(TableCellNode node) {

    }

    @Override
    public void visit(TableColumnNode node) {

    }

    @Override
    public void visit(TableHeaderNode node) {

    }

    @Override
    public void visit(TableNode node) {

    }

    @Override
    public void visit(TableRowNode node) {

    }

    @Override
    public void visit(VerbatimNode node) {

    }

    @Override
    public void visit(WikiLinkNode node) {

    }

    @Override
    public void visit(TextNode node) {
        MarkdownComponentText comp = new MarkdownComponentText(node.getText(), new Point(this.x, this.y));
        this.y += comp.getHeight() + this.yPad;
        this.screen.register(comp);
    }

    @Override
    public void visit(SuperNode node) {
        this.visitChildren(node);
    }

    @Override
    public void visit(Node node) {
        this.visitChildren(node);
    }

    private void visitChildren(Node node){
        for(Node n : node.getChildren()){
            n.accept(this);
        }
    }
}