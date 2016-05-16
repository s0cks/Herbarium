package truetyper;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;


/**
 * TrueTyper: Open Source TTF implementation for Minecraft.
 * Modified from Slick2D - under BSD Licensing -  http://slick.ninjacave.com/license/
 * <p/>
 * Copyright (c) 2013 - Slick2D
 * <p/>
 * All rights reserved.
 */

public class TrueTypeFont {
  public final static int
  ALIGN_LEFT = 0,
  ALIGN_RIGHT = 1,
  ALIGN_CENTER = 2;
  /**
   * Boolean flag on whether AntiAliasing is enabled or not
   */
  protected boolean antiAlias;
  /**
   * A reference to Java's AWT Font that we create our font texture from
   */
  protected Font font;
  /**
   * Array that holds necessary information about the font characters
   */
  private FloatObject[] charArray = new FloatObject[256];
  /**
   * Map of user defined font characters (Character <-> IntObject)
   */
  private Map customChars = new HashMap();
  /**
   * Font's size
   */
  private float fontSize = 0;
  /**
   * Font's height
   */
  private float fontHeight = 0;
  /**
   * Texture used to cache the font 0-255 characters
   */
  private int fontTextureID;
  /**
   * Default font texture width
   */
  private int textureWidth = 1024;
  /**
   * Default font texture height
   */
  private int textureHeight = 1024;
  /**
   * The font metrics for our Java AWT font
   */
  private FontMetrics fontMetrics;


  private int correctL = 9, correctR = 8;

  public TrueTypeFont(Font font, boolean antiAlias, char[] additionalChars) {
    this.font = font;
    this.fontSize = font.getSize() + 3;
    this.antiAlias = antiAlias;

    createSet(additionalChars);
    System.out.println("TrueTypeFont loaded: " + font + " - AntiAlias = " + antiAlias);
    fontHeight -= 1;
    if (fontHeight <= 0) fontHeight = 1;
  }


  public TrueTypeFont(Font font, boolean antiAlias) {
    this(font, antiAlias, null);
  }

  public static int loadImage(BufferedImage bufferedImage) {
    try {
      short width = (short) bufferedImage.getWidth();
      short height = (short) bufferedImage.getHeight();
      //textureLoader.bpp = bufferedImage.getColorModel().hasAlpha() ? (byte)32 : (byte)24;
      int bpp = (byte) bufferedImage.getColorModel()
                                    .getPixelSize();
      ByteBuffer byteBuffer;
      DataBuffer db = bufferedImage.getData()
                                   .getDataBuffer();
      if (db instanceof DataBufferInt) {
        int intI[] = ((DataBufferInt) (bufferedImage.getData()
                                                    .getDataBuffer())).getData();
        byte newI[] = new byte[intI.length * 4];
        for (int i = 0; i < intI.length; i++) {
          byte b[] = intToByteArray(intI[i]);
          int newIndex = i * 4;

          newI[newIndex] = b[1];
          newI[newIndex + 1] = b[2];
          newI[newIndex + 2] = b[3];
          newI[newIndex + 3] = b[0];
        }

        byteBuffer = ByteBuffer.allocateDirect(
        width * height * (bpp / 8))
                               .order(ByteOrder.nativeOrder())
                               .put(newI);
      } else {
        byteBuffer = ByteBuffer.allocateDirect(
        width * height * (bpp / 8))
                               .order(ByteOrder.nativeOrder())
                               .put(((DataBufferByte) (bufferedImage.getData()
                                                                    .getDataBuffer())).getData());
      }
      byteBuffer.flip();


      int internalFormat = GL11.GL_RGBA8,
      format = GL11.GL_RGBA;
      IntBuffer textureId = BufferUtils.createIntBuffer(1);
      ;
      GL11.glGenTextures(textureId);
      GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId.get(0));

      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);


      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
      //GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
      //GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_NEAREST);

      //GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
      //GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST_MIPMAP_LINEAR);
      //GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST_MIPMAP_NEAREST);

      GL11.glTexEnvf(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);

      GLU.gluBuild2DMipmaps(GL11.GL_TEXTURE_2D, internalFormat, width, height, format, GL11.GL_UNSIGNED_BYTE, byteBuffer);
      return textureId.get(0);

    } catch (Exception e) {
      e.printStackTrace();
      System.exit(-1);
    }

    return -1;
  }

  public static boolean isSupported(String fontname) {
    Font font[] = getFonts();
    for (int i = font.length - 1; i >= 0; i--) {
      if (font[i].getName()
                 .equalsIgnoreCase(fontname))
        return true;
    }
    return false;
  }

  public static Font[] getFonts() {
    return GraphicsEnvironment.getLocalGraphicsEnvironment()
                              .getAllFonts();
  }

  public static byte[] intToByteArray(int value) {
    return new byte[]{
    (byte) (value >>> 24),
    (byte) (value >>> 16),
    (byte) (value >>> 8),
    (byte) value
    };
  }

  public void destroy() {
    IntBuffer scratch = BufferUtils.createIntBuffer(1);
    scratch.put(0, fontTextureID);
    GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    GL11.glDeleteTextures(scratch);
  }

  public void setCorrection(boolean on) {
    if (on) {
      correctL = 2;
      correctR = 1;
    } else {
      correctL = 0;
      correctR = 0;
    }
  }

  private BufferedImage getFontImage(char ch) {
    // Create a temporary image to extract the character's size
    BufferedImage tempfontImage = new BufferedImage(1, 1,
                                                    BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = (Graphics2D) tempfontImage.getGraphics();
    if (antiAlias == true) {
      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                         RenderingHints.VALUE_ANTIALIAS_ON);
    }
    g.setFont(font);
    fontMetrics = g.getFontMetrics();
    float charwidth = fontMetrics.charWidth(ch) + 8;

    if (charwidth <= 0) {
      charwidth = 7;
    }
    float charheight = fontMetrics.getHeight() + 3;
    if (charheight <= 0) {
      charheight = fontSize;
    }

    // Create another image holding the character we are creating
    BufferedImage fontImage;
    fontImage = new BufferedImage((int) charwidth, (int) charheight,
                                  BufferedImage.TYPE_INT_ARGB);
    Graphics2D gt = (Graphics2D) fontImage.getGraphics();
    if (antiAlias == true) {
      gt.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_ON);
    }
    gt.setFont(font);

    gt.setColor(Color.WHITE);
    int charx = 3;
    int chary = 1;
    gt.drawString(String.valueOf(ch), (charx), (chary)
                                               + fontMetrics.getAscent());

    return fontImage;

  }

  private void createSet(char[] customCharsArray) {
    // If there are custom chars then I expand the font texture twice
    if (customCharsArray != null && customCharsArray.length > 0) {
      textureWidth *= 2;
    }

    // In any case this should be done in other way. Texture with size 512x512
    // can maintain only 256 characters with resolution of 32x32. The texture
    // size should be calculated dynamicaly by looking at character sizes.

    try {

      BufferedImage imgTemp = new BufferedImage(textureWidth, textureHeight, BufferedImage.TYPE_INT_ARGB);
      Graphics2D g = (Graphics2D) imgTemp.getGraphics();

      g.setColor(new Color(0, 0, 0, 1));
      g.fillRect(0, 0, textureWidth, textureHeight);

      float rowHeight = 0;
      float positionX = 0;
      float positionY = 0;

      int customCharsLength = (customCharsArray != null)
                              ? customCharsArray.length
                              : 0;

      for (int i = 0; i < 256 + customCharsLength; i++) {

        // get 0-255 characters and then custom characters
        char ch = (i < 256)
                  ? (char) i
                  : customCharsArray[i - 256];

        BufferedImage fontImage = getFontImage(ch);

        FloatObject newIntObject = new FloatObject();

        newIntObject.width = fontImage.getWidth();
        newIntObject.height = fontImage.getHeight();

        if (positionX + newIntObject.width >= textureWidth) {
          positionX = 0;
          positionY += rowHeight;
          rowHeight = 0;
        }

        newIntObject.storedX = positionX;
        newIntObject.storedY = positionY;

        if (newIntObject.height > fontHeight) {
          fontHeight = newIntObject.height;
        }

        if (newIntObject.height > rowHeight) {
          rowHeight = newIntObject.height;
        }

        // Draw it here
        g.drawImage(fontImage, (int) positionX, (int) positionY, null);

        positionX += newIntObject.width;

        if (i < 256) { // standard characters
          charArray[i] = newIntObject;
        } else { // custom characters
          customChars.put(ch, newIntObject);
        }

        fontImage = null;
      }

      fontTextureID = loadImage(imgTemp);


      //.getTexture(font.toString(), imgTemp);

    } catch (Exception e) {
      System.err.println("Failed to create font.");
      e.printStackTrace();
    }
  }

  private void drawQuad(float drawX, float drawY, float drawX2, float drawY2,
                        float srcX, float srcY, float srcX2, float srcY2, float... rgba) {
    float DrawWidth = drawX2 - drawX;
    float DrawHeight = drawY2 - drawY;
    float TextureSrcX = srcX / textureWidth;
    float TextureSrcY = srcY / textureHeight;
    float SrcWidth = srcX2 - srcX;
    float SrcHeight = srcY2 - srcY;
    float RenderWidth = (SrcWidth / textureWidth);
    float RenderHeight = (SrcHeight / textureHeight);
    Tessellator t = Tessellator.getInstance();
    VertexBuffer vb = t.getBuffer();

    vb.pos(drawX, drawY, 0)
      .tex(TextureSrcX, TextureSrcY)
      .color(rgba[0], rgba[1], rgba[2], rgba[3])
      .endVertex();
    vb.pos(drawX, drawY + DrawHeight, 0)
      .tex(TextureSrcX, TextureSrcY + RenderHeight)
      .color(rgba[0], rgba[1], rgba[2], rgba[3])
      .endVertex();
    vb.pos(drawX + DrawWidth, drawY + DrawHeight, 0)
      .tex(TextureSrcX + RenderWidth, TextureSrcY + RenderHeight)
      .color(rgba[0], rgba[1], rgba[2], rgba[3])
      .endVertex();
    vb.pos(drawX + DrawWidth, drawY, 0)
      .tex(TextureSrcX + RenderWidth, TextureSrcY)
      .color(rgba[0], rgba[1], rgba[2], rgba[3])
      .endVertex();
  }

  public float getWidth(String whatchars) {
    return this.fontMetrics.stringWidth(whatchars);
  }

class FloatObject {
  /**
   * Character's width
   */
  public float width;

  /**
   * Character's height
   */
  public float height;

  /**
   * Character's stored x position
   */
  public float storedX;

  /**
   * Character's stored y position
   */
  public float storedY;
}

  public float getWidth(int width) {
    float total = 0;
    FloatObject obj = charArray['W'];
    for (int i = 0; i < width; i++) {
      total += obj.width / 2;
    }
    return total;
  }

  public float getHeight() {
    return fontHeight;
  }

  public float getHeight(String HeightString) {
    return fontHeight;
  }

  public float getLineHeight() {
    return fontHeight;
  }

  public void drawString(float x, float y, String whatchars, float scaleX, float scaleY, float... rgba) {
    if (rgba.length == 0) rgba = new float[]{1f, 1f, 1f, 1f};
    drawString(x, y, whatchars, 0, whatchars.length() - 1, scaleX, scaleY, ALIGN_LEFT, rgba);
  }

  public void drawString(float x, float y, String whatchars, float scaleX, float scaleY, int format, float... rgba) {
    if (rgba.length == 0) rgba = new float[]{1f, 1f, 1f, 1f};
    drawString(x, y, whatchars, 0, whatchars.length() - 1, scaleX, scaleY, format, rgba);
  }

  public void drawString(float x, float y, String whatchars, int startIndex, int endIndex, float scaleX, float scaleY, int format, float... rgba) {
    if (rgba.length == 0) rgba = new float[]{1f, 1f, 1f, 1f};
    GL11.glPushMatrix();
    GL11.glScalef(scaleX, scaleY, 1.0f);

    FloatObject floatObject = null;
    int charCurrent;


    float totalwidth = 0;
    int i = startIndex, d, c;
    float startY = 0;


    switch (format) {
      case ALIGN_RIGHT: {
        d = -1;
        c = correctR;

        while (i < endIndex) {
          if (whatchars.charAt(i) == '\n') startY -= fontHeight;
          i++;
        }
        break;
      }
      case ALIGN_CENTER: {
        for (int l = startIndex; l <= endIndex; l++) {
          charCurrent = whatchars.charAt(l);
          if (charCurrent == '\n') break;
          if (charCurrent < 256) {
            floatObject = charArray[charCurrent];
          } else {
            floatObject = (FloatObject) customChars.get((char) charCurrent);
          }
          totalwidth += floatObject.width - correctL;
        }
        totalwidth /= -2;
      }
      case ALIGN_LEFT:
      default: {
        d = 1;
        c = correctL;
        break;
      }

    }
    GL11.glBindTexture(GL11.GL_TEXTURE_2D, fontTextureID);
    Tessellator t = Tessellator.getInstance();
    VertexBuffer vb = t.getBuffer();
    vb.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
    while (i >= startIndex && i <= endIndex) {

      charCurrent = whatchars.charAt(i);
      if (charCurrent < 256) {
        floatObject = charArray[charCurrent];
      } else {
        floatObject = (FloatObject) customChars.get((char) charCurrent);
      }

      if (floatObject != null) {
        if (d < 0) totalwidth += (floatObject.width - c) * d;
        if (charCurrent == '\n') {
          startY -= fontHeight * d;
          totalwidth = 0;
          if (format == ALIGN_CENTER) {
            for (int l = i + 1; l <= endIndex; l++) {
              charCurrent = whatchars.charAt(l);
              if (charCurrent == '\n') break;
              if (charCurrent < 256) {
                floatObject = charArray[charCurrent];
              } else {
                floatObject = (FloatObject) customChars.get((char) charCurrent);
              }
              totalwidth += floatObject.width - correctL;
            }
            totalwidth /= -2;
          }
        } else {
          drawQuad((totalwidth + floatObject.width) + x / scaleX,
                   startY + y / scaleY,
                   totalwidth + x / scaleX,
                   (startY + floatObject.height) + y / scaleY,
                   floatObject.storedX + floatObject.width,
                   floatObject.storedY + floatObject.height,
                   floatObject.storedX,
                   floatObject.storedY,
                   rgba
          );
          if (d > 0) totalwidth += (floatObject.width - c) * d;
        }
        i += d;
      }
      t.draw();

      GL11.glPopMatrix();

    }
  }
}