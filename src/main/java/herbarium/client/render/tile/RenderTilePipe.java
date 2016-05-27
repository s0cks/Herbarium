package herbarium.client.render.tile;

import herbarium.client.ClientEventHandler;
import herbarium.common.Herbarium;
import herbarium.common.core.brew.piping.BrewPipingHelper;
import herbarium.common.tiles.brewing.TileEntityPipe;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.BlockPart;
import net.minecraft.client.renderer.block.model.BlockPartFace;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.renderer.block.model.ModelRotation;
import net.minecraft.client.renderer.block.model.SimpleBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.io.InputStream;
import java.io.InputStreamReader;

public final class RenderTilePipe
extends TileEntitySpecialRenderer<TileEntityPipe> {
  private static final ResourceLocation texture = new ResourceLocation("herbarium", "textures/models/pipe.png");
  private static final TextureAtlasSprite textureSprite = ClientEventHandler.instance().pipeTexture();
  private static final FaceBakery faceBarkery = new FaceBakery();

  private static IBakedModel create(String state) {
    try (InputStream in = Herbarium.proxy.getClient()
                                         .getResourceManager()
                                         .getResource(new ResourceLocation("herbarium", "models/block/pipe_" + state + ".json"))
                                         .getInputStream()) {
      ModelBlock model = ModelBlock.deserialize(new InputStreamReader(in));
      SimpleBakedModel.Builder builder = new SimpleBakedModel.Builder(model, model.createOverrides()).setTexture(textureSprite);
      for(BlockPart part : model.getElements()){
        for(EnumFacing facing : part.mapFaces.keySet()){
          BlockPartFace face = part.mapFaces.get(facing);
          if(face.cullFace == null){
            builder.addGeneralQuad(faceBarkery.makeBakedQuad(part.positionFrom, part.positionTo, face, textureSprite, facing, ModelRotation.X0_Y0, part.partRotation, false, part.shade));
          } else{
            builder.addFaceQuad(ModelRotation.X0_Y0.rotate(face.cullFace), faceBarkery.makeBakedQuad(part.positionFrom, part.positionTo, face, textureSprite, facing, ModelRotation.X0_Y0, part.partRotation, false, part.shade));
          }
        }
      }
      return builder.makeBakedModel();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static final IBakedModel joint = create("joint");
  private static final IBakedModel up = create("up");
  private static final IBakedModel down = create("down");
  private static final IBakedModel west = create("west");
  private static final IBakedModel east = create("east");
  private static final IBakedModel north = create("north");
  private static final IBakedModel south = create("south");

  @Override
  public void renderTileEntityAt(TileEntityPipe te, double x, double y, double z, float partialTicks, int destroyStage) {
    GlStateManager.pushMatrix();
    GlStateManager.translate(x, y, z);
    GlStateManager.enableBlend();
    GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

    GlStateManager.pushMatrix();
    this.render(joint);
    GlStateManager.popMatrix();

    for(EnumFacing facing : EnumFacing.values()){
      TileEntity connected = BrewPipingHelper.getConnected(te.getWorld(), te.getPos(), facing);
      if(connected != null){
        GlStateManager.pushMatrix();
        switch(facing){
          case UP: this.render(up); break;
          case DOWN: this.render(down); break;
          case EAST: this.render(east); break;
          case WEST: this.render(west); break;
          case NORTH: this.render(north); break;
          case SOUTH: this.render(south); break;
          default: break;
        }
        GlStateManager.popMatrix();
      }
    }

    GlStateManager.disableBlend();
    GlStateManager.popMatrix();
  }

  private void render(IBakedModel model) {
    Herbarium.proxy.getClient().renderEngine.bindTexture(texture);
    Herbarium.proxy.dispatcher()
                   .getBlockModelRenderer()
                   .renderModelBrightnessColor(model, 1.0F, 1.0F, 1.0F, 1.0F);
  }
}