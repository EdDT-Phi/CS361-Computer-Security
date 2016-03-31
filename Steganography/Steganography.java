import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.nio.file.Files;
import java.awt.*;

public class Steganography {

  // get it? byte me? no? ok.. bite me
  static byte me;
  static int count = 0, bytes = 0;
  static final int offset = 3;
  static ByteArrayInputStream is;
  static FileOutputStream stream;


  public static void main(String[] args) throws Exception  {

    if (args.length != 3)
      System.out.println("Usage: java Steganography -[ED] image file");
    if(args[0].equals("-E")) {

      encode(args[1], args[2]);
      System.out.printf("Encoded %d bytes\n", bytes);
    }
    else if(args[0].equals("-D")){
      decode(args[1], args[2]);
      System.out.printf("Recovered %d bytes\n", bytes);
    } else {
      System.out.println("Usage: java Steganography -[ED] image file");
    }


      // BufferedImage img = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);
      // File f = new File("file.bmp");
  }

  public static void decode(String image, String file) throws Exception {
    stream = new FileOutputStream(file);
    BufferedImage img = ImageIO.read(new File(image));

    System.out.printf("Decoding from \"%s\" into \"%s\"\n",image, file);
    System.out.println("height: " + img.getHeight());
    System.out.println("width: " + img.getWidth());
    System.out.println("pixels: " + img.getHeight()*img.getWidth());

    int pixel, alpha, red, green, blue;
    for(int i = 0; i < img.getHeight(); i++) {
      for(int j = 0; j < img.getWidth(); j++) {
        pixel = img.getRGB(j, i);
        alpha = (pixel >>> 24) & 0xFF;
        red = (pixel >> 16) & 0xFF;
        green = (pixel >> 8) & 0xFF;
        blue = pixel & 0xFF;

        // System.out.printf("D: %x %x %x %x\n", alpha>>>24, red, green, blue);
        if(red == 0) {
          stream.close();
          return;
        }

        me = (byte) (red & 7);
        me += (green & 7) << 3;
        me += (blue & 3) << 6;

        writeNextByte();
        if(alpha == 0) {
          stream.close();
          return;
        }

        me = (byte) ((blue >> 2) & 1);
        me += (alpha << 1) & 0xFF;


        // for (int k = 24; k < 32; k++){
        //   System.out.printf("%d\n", (alpha >> k) & 1);
        // }

        writeNextByte();

      }
    }
  }

  public static void encode(String image, String file) throws Exception{
    BufferedImage oldImg = ImageIO.read(new File(image));
    BufferedImage newImg = new BufferedImage(oldImg.getWidth(), oldImg.getHeight(), BufferedImage.TYPE_INT_ARGB);

    Graphics2D g = newImg.createGraphics();
    g.drawImage(oldImg,0,0,null);
    g.dispose();

    File secret = new File(file);
    is = new ByteArrayInputStream(Files.readAllBytes(secret.toPath()));
    String extension = image.substring(image.lastIndexOf('.')+1);
    String newName= image.substring(0, image.lastIndexOf('.'))
                    + "-steg." + extension;

    System.out.printf("Encoding from \"%s\" into \"%s\"\n",file, newName);
    System.out.println("height: " +oldImg.getHeight());
    System.out.println("width: " + oldImg.getWidth());
    System.out.println("pixels: " + oldImg.getHeight()*oldImg.getWidth());
    System.out.printf("Size of secret: %d\n", secret.length());

    // System.out.println(newImg);
    // System.out.println(oldImg);
    // System.out.println(extension.toUpperCase());
    // System.out.println(newName);
    // System.out.println(newImg.getRGB(50,50));
    // System.out.println(oldImg.getRGB(50,50));


    int pixel = 0, alpha = 0, red= 0, green = 0, blue = 0, bits, readByte;
    for(int i = 0; i < oldImg.getHeight(); i++) {
      for(int j = 0; j < oldImg.getWidth(); j++) {
        pixel = oldImg.getRGB(j, i);
        alpha = (pixel >>> 24) & 0xFF;
        red = (pixel >> 16) & 0xFF;
        green = (pixel >> 8) & 0xFF;
        blue = pixel & 0xFF;


        if ((readByte = is.read()) == -1) {
          newImg.setRGB(j, i,  (alpha<<24) | (green<<8) | blue);
          ImageIO.write(newImg, extension.toUpperCase(), new File(newName));
          return;
        }
        bytes ++;

        // System.out.printf("B: %x %x %x %x\n", alpha, red, green, blue);

        red = (red & (-1 << offset)) | (readByte & 7);
        if(red == 0) red = 1<<3;
        readByte >>= offset;
        green = (green & (-1 << offset)) | (readByte & 7);
        readByte >>= offset;
        blue = (blue & (-1 << 3)) | (readByte & 3);


        readByte = is.read();
        if (readByte == -1) {
          newImg.setRGB(j, i,  (red<<16) + (green<<8) + blue);
          ImageIO.write(newImg, extension.toUpperCase(), new File(newName));
          return;
        }
        bytes ++;

        // for (int k = 0; k < 8; k++){
          // System.out.printf("%d\n", (readByte >> k) & 1);
        // }
        // System.out.println();

        blue = (blue & ~(1 << 2)) | ((readByte & 1) << 2);
        readByte >>= 1;
        // alpha = (alpha & (-1 << 4)) + (readByte & (256-1));
        alpha = 128 + readByte;
        // alpha = 0xF0;

        // System.out.pmakrintln(readByte);

        // for (int k = 24; k < 32; k++){
        //   System.out.printf("%d\n", (alpha >> k) & 1);
        // }

        pixel = (alpha<<24) | (red<<16) | (green<<8) | blue;

        // System.out.printf("A: %x %x %x %x\n\n", alpha, red, green, blue);
        // System.out.printf("Writing: %x\n", pixel);

        // Color newColor = new Color(pixel, true);
        // System.out.printf("Color: %x\n", newColor.getRGB());
        // newImg.setRGB(j, i, newColor.getRGB());
        // System.out.printf("After color: %x\n", oldImg.getRGB(j, i));

        newImg.setRGB(j, i, pixel);
        // System.out.printf("After manual: %x\n", newImg.getRGB(j,i));
        // pixel = newImg.getRGB(j, i);
        // alpha = pixel & (0xFF << 24);
        // red = (pixel >> 16) & 0xFF;
        // green = (pixel >> 8) & 0xFF;
        // blue = pixel & 0xFF;
        // System.out.printf("AA: %x %x %x %x\n", alpha >>> 24, red, green, blue);

        // System.out.printf("After setRGB: %x\n", pixel);
      }
    }

    System.out.printf("WARN: File size truncated, can only store %d bytes\n", newImg.getHeight() * newImg.getWidth() );
    newImg.setRGB(newImg.getWidth()-1, newImg.getHeight()-1, (red<<16) + (green<<8) + blue);
    ImageIO.write(newImg, extension.toUpperCase(), new File(newName));
  }

  public static int getNextTwoBits() {
    if(count == 0) { // get new byte
      count = 8;
      int read = is.read();
      if(read == -1) {
        return -1;
      }
      bytes ++;
      me = (byte) read;
    }
    int bits = me & 11;
    count -= 2;
    me >>= 2;
    return bits;
  }

  public static void writeNextByte(){
    try {
      bytes ++;
      stream.write(me);
    } catch (IOException e) {
      e.printStackTrace();
    }
    me = 0;
  }
}