import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.nio.file.Files;

public class Steganography {

  static byte me;
  static ByteArrayInputStream is;
  static int count = 0;
  static int bytes = 0;
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

    int pixel, alpha, red, green, blue;
    for(int i = 0; i < img.getHeight(); i++) {
      for(int j = 0; j < img.getWidth(); j++) {
        pixel = img.getRGB(j, i);
        alpha = pixel & (255 << 24);
        red = (pixel >> 16) & 255;
        green = (pixel >> 8) & 255;
        blue = pixel & 255;

        if(red == 0) {
          stream.close();
          return;
        }
        writeNextBit(red % 2);
        if(green == 0) {
          stream.close();
          return;
        }
        writeNextBit(green % 2);
        if(blue == 0) {
          stream.close();
          return;
        }
        writeNextBit(blue % 2);
      }
    }
  }

  public static void encode(String image, String file) throws Exception{
    BufferedImage img = ImageIO.read(new File(image));
    File secret = new File(file);
    is = new ByteArrayInputStream(Files.readAllBytes(secret.toPath()));
    System.out.printf("Encoding from \"%s\" into \"%s-steg.bmp\"\nheight: %d\nwidth: %d\npixels: %d\n", file, image, img.getHeight(), img.getWidth(), img.getHeight()*img.getWidth());
    System.out.printf("Size of secret: %d\n", secret.length());

    String newName= image.substring(0, image.lastIndexOf('.'))
                    + "-steg" +
                    image.substring(image.lastIndexOf('.'));


    int pixel = 0, alpha = 0, red= 0, green = 0, blue = 0, bit;
    for(int i = 0; i < img.getHeight(); i++) {
      for(int j = 0; j < img.getWidth(); j++) {
        pixel = img.getRGB(j, i);
        alpha = pixel & (255 << 24);
        red = (pixel >> 16) & 255;
        green = (pixel >> 8) & 255;
        blue = pixel & 255;

        if((bit = getNextBit()) == -1) {
          setPixel(img, alpha + (green<<8) + blue, i , j);
          ImageIO.write(img, "BMP", new File(newName));
          return;
        }
        if(red%2 != bit%2) red ++;
        if(red > 255) red = 254;

        if((bit = getNextBit()) == -1) {
          setPixel(img, alpha + (red<<16) + blue, i , j);
          ImageIO.write(img, "BMP", new File(newName));
          return;
        }
        if(green%2 != bit%2) green ++;
        if(green > 255) green = 254;

        if((bit = getNextBit()) == -1) {
          setPixel(img, alpha + (red<<16) + (green<<8), i , j);
          ImageIO.write(img, "BMP", new File(newName));
          return;
        }
        if(blue%2 != bit%2) blue ++;
        if(blue > 255) blue = 254;

        setPixel(img, alpha + (red<<16) + (green<<8) + blue, i , j);
      }
    }

    System.out.printf("WARN: File size truncated, can only store %d bytes\n", img.getHeight() * img.getWidth() / 8);
    setPixel(img, alpha + (red<<16) + (green<<8), img.getHeight()-1 , img.getWidth()-1);
    ImageIO.write(img, "BMP", new File(newName));
  }

  public static int getNextBit() {
    if(count == 0) { // get new byte
      count = 8;
      int read = is.read();
      if(read == -1) {
        return -1;
      }
      bytes ++;
      me = (byte) read;
    }
    int bit = me & 1;
    count--;
    me >>= 1;
    return bit;
  }

  public static void writeNextBit(int bit){
    me += bit << count;
    count++;
    if (count >= 8) {
      // output to file
      try {
        bytes ++;
        stream.write(me);
      } catch (IOException e) {
        e.printStackTrace();
      }
      count = 0;
      me = 0;
    }
  }

  public static void setPixel(BufferedImage img, int pixel, int i, int j) {
    img.setRGB(j, i, pixel);
  }
}