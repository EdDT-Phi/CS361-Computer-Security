import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.nio.file.Files;

public class Steganography {

  static byte me;
  static ByteArrayInputStream is;
  static int count = 0;

  public static void main(String[] args) throws Exception  {
    try {

      // TODO parse input
      String file  = args[2];
      is = new ByteArrayInputStream(Files.readAllBytes(new File(file).toPath()));

      BufferedImage img = null;
      try {
          img = ImageIO.read(new File("inputImage.bmp"));
      } catch (IOException e) {
          System.out.println("args[2] was not found");
          return;
      }

      int bytes = img.getWidth() * img.getHeight();
      int count = 0;

      int pixel;
      int alpha;
      int red;
      int green;
      int blue;
      for(int i = 0; i < img.getHeight(); i++){
        for(int j = 0; j < img.getWidth(); j++) {
          pixel = img.getRGB(j, i);
          alpha = pixel & (255 << 24);
          red = (pixel >> 16) & 255;
          green = (pixel >> 8) & 255;
          blue = pixel & 255;

          if(red%2 != getNextBit()%2) red ++;
          if(red > 255) red = 254;

          if(green%2 != getNextBit()%2) green ++;
          if(green > 255) green = 254;

          if(blue%2 != getNextBit()%2) blue ++;
          if(blue > 255) blue = 254;

          pixel = alpha + (red<<16) + (green<<8) + blue;
          img.setRGB(j, i, pixel);
        }
      }
    } catch (FileNotFoundException | UnsupportedEncodingException e) {
        e.printStackTrace();
    } finally {
    }


    
      // BufferedImage img = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);
      // File f = new File("file.png");
      // ImageIO.write(img, "PNG", f);
  }

  public static int getNextBit() {
    if(count == 0) { // get new byte
      if ((me = (byte) is.read()) == -1)
        return -1;
    }
    
    int bit = me & 1;
    count++;
    me >>= 1;
    return bit;
  }
}