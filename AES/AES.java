import java.util.*;
import java.io.*;
import java.lang.*;
import java.nio.file.Files;

class AES {

   private static final boolean DEBUG = false;

   final static byte[][] byteSubs = {
      {0x63, (byte) 0x7C, (byte) 0x77, (byte) 0x7B, (byte) 0xF2, (byte) 0x6B, (byte) 0x6F, (byte) 0xC5, (byte) 0x30, (byte) 0x01, (byte) 0x67, (byte) 0x2B, (byte) 0xFE, (byte) 0xD7, (byte) 0xAB, (byte) 0x76},
      {(byte) 0xCA, (byte) 0x82, (byte) 0xC9, (byte) 0x7D, (byte) 0xFA, (byte) 0x59, (byte) 0x47, (byte) 0xF0, (byte) 0xAD, (byte) 0xD4, (byte) 0xA2, (byte) 0xAF, (byte) 0x9C, (byte) 0xA4, (byte) 0x72, (byte) 0xC0},
      {(byte) 0xB7, (byte) 0xFD, (byte) 0x93, (byte) 0x26, (byte) 0x36, (byte) 0x3F, (byte) 0xF7, (byte) 0xCC, (byte) 0x34, (byte) 0xA5, (byte) 0xE5, (byte) 0xF1, (byte) 0x71, (byte) 0xD8, (byte) 0x31, (byte) 0x15},
      {(byte) 0x04, (byte) 0xC7, (byte) 0x23, (byte) 0xC3, (byte) 0x18, (byte) 0x96, (byte) 0x05, (byte) 0x9A, (byte) 0x07, (byte) 0x12, (byte) 0x80, (byte) 0xE2, (byte) 0xEB, (byte) 0x27, (byte) 0xB2, (byte) 0x75},
      {(byte) 0x09, (byte) 0x83, (byte) 0x2C, (byte) 0x1A, (byte) 0x1B, (byte) 0x6E, (byte) 0x5A, (byte) 0xA0, (byte) 0x52, (byte) 0x3B, (byte) 0xD6, (byte) 0xB3, (byte) 0x29, (byte) 0xE3, (byte) 0x2F, (byte) 0x84},
      {(byte) 0x53, (byte) 0xD1, (byte) 0x00, (byte) 0xED, (byte) 0x20, (byte) 0xFC, (byte) 0xB1, (byte) 0x5B, (byte) 0x6A, (byte) 0xCB, (byte) 0xBE, (byte) 0x39, (byte) 0x4A, (byte) 0x4C, (byte) 0x58, (byte) 0xCF},
      {(byte) 0xD0, (byte) 0xEF, (byte) 0xAA, (byte) 0xFB, (byte) 0x43, (byte) 0x4D, (byte) 0x33, (byte) 0x85, (byte) 0x45, (byte) 0xF9, (byte) 0x02, (byte) 0x7F, (byte) 0x50, (byte) 0x3C, (byte) 0x9F, (byte) 0xA8},
      {(byte) 0x51, (byte) 0xA3, (byte) 0x40, (byte) 0x8F, (byte) 0x92, (byte) 0x9D, (byte) 0x38, (byte) 0xF5, (byte) 0xBC, (byte) 0xB6, (byte) 0xDA, (byte) 0x21, (byte) 0x10, (byte) 0xFF, (byte) 0xF3, (byte) 0xD2},
      {(byte) 0xCD, (byte) 0x0C, (byte) 0x13, (byte) 0xEC, (byte) 0x5F, (byte) 0x97, (byte) 0x44, (byte) 0x17, (byte) 0xC4, (byte) 0xA7, (byte) 0x7E, (byte) 0x3D, (byte) 0x64, (byte) 0x5D, (byte) 0x19, (byte) 0x73},
      {(byte) 0x60, (byte) 0x81, (byte) 0x4F, (byte) 0xDC, (byte) 0x22, (byte) 0x2A, (byte) 0x90, (byte) 0x88, (byte) 0x46, (byte) 0xEE, (byte) 0xB8, (byte) 0x14, (byte) 0xDE, (byte) 0x5E, (byte) 0x0B, (byte) 0xDB},
      {(byte) 0xE0, (byte) 0x32, (byte) 0x3A, (byte) 0x0A, (byte) 0x49, (byte) 0x06, (byte) 0x24, (byte) 0x5C, (byte) 0xC2, (byte) 0xD3, (byte) 0xAC, (byte) 0x62, (byte) 0x91, (byte) 0x95, (byte) 0xE4, (byte) 0x79},
      {(byte) 0xE7, (byte) 0xC8, (byte) 0x37, (byte) 0x6D, (byte) 0x8D, (byte) 0xD5, (byte) 0x4E, (byte) 0xA9, (byte) 0x6C, (byte) 0x56, (byte) 0xF4, (byte) 0xEA, (byte) 0x65, (byte) 0x7A, (byte) 0xAE, (byte) 0x08},
      {(byte) 0xBA, (byte) 0x78, (byte) 0x25, (byte) 0x2E, (byte) 0x1C, (byte) 0xA6, (byte) 0xB4, (byte) 0xC6, (byte) 0xE8, (byte) 0xDD, (byte) 0x74, (byte) 0x1F, (byte) 0x4B, (byte) 0xBD, (byte) 0x8B, (byte) 0x8A},
      {(byte) 0x70, (byte) 0x3E, (byte) 0xB5, (byte) 0x66, (byte) 0x48, (byte) 0x03, (byte) 0xF6, (byte) 0x0E, (byte) 0x61, (byte) 0x35, (byte) 0x57, (byte) 0xB9, (byte) 0x86, (byte) 0xC1, (byte) 0x1D, (byte) 0x9E},
      {(byte) 0xE1, (byte) 0xF8, (byte) 0x98, (byte) 0x11, (byte) 0x69, (byte) 0xD9, (byte) 0x8E, (byte) 0x94, (byte) 0x9B, (byte) 0x1E, (byte) 0x87, (byte) 0xE9, (byte) 0xCE, (byte) 0x55, (byte) 0x28, (byte) 0xDF},
      {(byte) 0x8C, (byte) 0xA1, (byte) 0x89, (byte) 0x0D, (byte) 0xBF, (byte) 0xE6, (byte) 0x42, (byte) 0x68, (byte) 0x41, (byte) 0x99, (byte) 0x2D, (byte) 0x0F, (byte) 0xB0, (byte) 0x54, (byte) 0xBB, (byte) 0x16}};

   final static byte[][] invByteSubs = {
      {(byte) 0x52, (byte) 0x09, (byte) 0x6A, (byte) 0xD5, (byte) 0x30, (byte) 0x36, (byte) 0xA5, (byte) 0x38, (byte) 0xBF, (byte) 0x40, (byte) 0xA3, (byte) 0x9E, (byte) 0x81, (byte) 0xF3, (byte) 0xD7, (byte) 0xFB},
      {(byte) 0x7C, (byte) 0xE3, (byte) 0x39, (byte) 0x82, (byte) 0x9B, (byte) 0x2F, (byte) 0xFF, (byte) 0x87, (byte) 0x34, (byte) 0x8E, (byte) 0x43, (byte) 0x44, (byte) 0xC4, (byte) 0xDE, (byte) 0xE9, (byte) 0xCB},
      {(byte) 0x54, (byte) 0x7B, (byte) 0x94, (byte) 0x32, (byte) 0xA6, (byte) 0xC2, (byte) 0x23, (byte) 0x3D, (byte) 0xEE, (byte) 0x4C, (byte) 0x95, (byte) 0x0B, (byte) 0x42, (byte) 0xFA, (byte) 0xC3, (byte) 0x4E},
      {(byte) 0x08, (byte) 0x2E, (byte) 0xA1, (byte) 0x66, (byte) 0x28, (byte) 0xD9, (byte) 0x24, (byte) 0xB2, (byte) 0x76, (byte) 0x5B, (byte) 0xA2, (byte) 0x49, (byte) 0x6D, (byte) 0x8B, (byte) 0xD1, (byte) 0x25},
      {(byte) 0x72, (byte) 0xF8, (byte) 0xF6, (byte) 0x64, (byte) 0x86, (byte) 0x68, (byte) 0x98, (byte) 0x16, (byte) 0xD4, (byte) 0xA4, (byte) 0x5C, (byte) 0xCC, (byte) 0x5D, (byte) 0x65, (byte) 0xB6, (byte) 0x92},
      {(byte) 0x6C, (byte) 0x70, (byte) 0x48, (byte) 0x50, (byte) 0xFD, (byte) 0xED, (byte) 0xB9, (byte) 0xDA, (byte) 0x5E, (byte) 0x15, (byte) 0x46, (byte) 0x57, (byte) 0xA7, (byte) 0x8D, (byte) 0x9D, (byte) 0x84},
      {(byte) 0x90, (byte) 0xD8, (byte) 0xAB, (byte) 0x00, (byte) 0x8C, (byte) 0xBC, (byte) 0xD3, (byte) 0x0A, (byte) 0xF7, (byte) 0xE4, (byte) 0x58, (byte) 0x05, (byte) 0xB8, (byte) 0xB3, (byte) 0x45, (byte) 0x06},
      {(byte) 0xD0, (byte) 0x2C, (byte) 0x1E, (byte) 0x8F, (byte) 0xCA, (byte) 0x3F, (byte) 0x0F, (byte) 0x02, (byte) 0xC1, (byte) 0xAF, (byte) 0xBD, (byte) 0x03, (byte) 0x01, (byte) 0x13, (byte) 0x8A, (byte) 0x6B},
      {(byte) 0x3A, (byte) 0x91, (byte) 0x11, (byte) 0x41, (byte) 0x4F, (byte) 0x67, (byte) 0xDC, (byte) 0xEA, (byte) 0x97, (byte) 0xF2, (byte) 0xCF, (byte) 0xCE, (byte) 0xF0, (byte) 0xB4, (byte) 0xE6, (byte) 0x73},
      {(byte) 0x96, (byte) 0xAC, (byte) 0x74, (byte) 0x22, (byte) 0xE7, (byte) 0xAD, (byte) 0x35, (byte) 0x85, (byte) 0xE2, (byte) 0xF9, (byte) 0x37, (byte) 0xE8, (byte) 0x1C, (byte) 0x75, (byte) 0xDF, (byte) 0x6E},
      {(byte) 0x47, (byte) 0xF1, (byte) 0x1A, (byte) 0x71, (byte) 0x1D, (byte) 0x29, (byte) 0xC5, (byte) 0x89, (byte) 0x6F, (byte) 0xB7, (byte) 0x62, (byte) 0x0E, (byte) 0xAA, (byte) 0x18, (byte) 0xBE, (byte) 0x1B},
      {(byte) 0xFC, (byte) 0x56, (byte) 0x3E, (byte) 0x4B, (byte) 0xC6, (byte) 0xD2, (byte) 0x79, (byte) 0x20, (byte) 0x9A, (byte) 0xDB, (byte) 0xC0, (byte) 0xFE, (byte) 0x78, (byte) 0xCD, (byte) 0x5A, (byte) 0xF4},
      {(byte) 0x1F, (byte) 0xDD, (byte) 0xA8, (byte) 0x33, (byte) 0x88, (byte) 0x07, (byte) 0xC7, (byte) 0x31, (byte) 0xB1, (byte) 0x12, (byte) 0x10, (byte) 0x59, (byte) 0x27, (byte) 0x80, (byte) 0xEC, (byte) 0x5F},
      {(byte) 0x60, (byte) 0x51, (byte) 0x7F, (byte) 0xA9, (byte) 0x19, (byte) 0xB5, (byte) 0x4A, (byte) 0x0D, (byte) 0x2D, (byte) 0xE5, (byte) 0x7A, (byte) 0x9F, (byte) 0x93, (byte) 0xC9, (byte) 0x9C, (byte) 0xEF},
      {(byte) 0xA0, (byte) 0xE0, (byte) 0x3B, (byte) 0x4D, (byte) 0xAE, (byte) 0x2A, (byte) 0xF5, (byte) 0xB0, (byte) 0xC8, (byte) 0xEB, (byte) 0xBB, (byte) 0x3C, (byte) 0x83, (byte) 0x53, (byte) 0x99, (byte) 0x61},
      {(byte) 0x17, (byte) 0x2B, (byte) 0x04, (byte) 0x7E, (byte) 0xBA, (byte) 0x77, (byte) 0xD6, (byte) 0x26, (byte) 0xE1, (byte) 0x69, (byte) 0x14, (byte) 0x63, (byte) 0x55, (byte) 0x21, (byte) 0x0C, (byte) 0x7D}};

   final static int[] LogTable = {
      0,   0,  25,   1,  50,   2,  26, 198,  75, 199,  27, 104,  51, 238, 223,   3,
      100,   4, 224,  14,  52, 141, 129, 239,  76, 113,   8, 200, 248, 105,  28, 193,
      125, 194,  29, 181, 249, 185,  39, 106,  77, 228, 166, 114, 154, 201,   9, 120,
      101,  47, 138,   5,  33,  15, 225,  36,  18, 240, 130,  69,  53, 147, 218, 142,
      150, 143, 219, 189,  54, 208, 206, 148,  19,  92, 210, 241, 64,  70, 131,  56,
      102, 221, 253,  48, 191,   6, 139,  98, 179,  37, 226, 152,  34, 136, 145,  16,
      126, 110,  72, 195, 163, 182,  30,  66,  58, 107,  40,  84, 250, 133,  61, 186,
      43, 121,  10,  21, 155, 159,  94, 202,  78, 212, 172, 229, 243, 115, 167,  87,
      175,  88, 168,  80, 244, 234, 214, 116,  79, 174, 233, 213, 231, 230, 173, 232,
      44, 215, 117, 122, 235,  22,  11, 245,  89, 203,  95, 176, 156, 169,  81, 160,
      127,  12, 246, 111,  23, 196,  73, 236, 216,  67,  31,  45, 164, 118, 123, 183,
      204, 187,  62,  90, 251,  96, 177, 134,  59,  82, 161, 108, 170,  85,  41, 157,
      151, 178, 135, 144,  97, 190, 220, 252, 188, 149, 207, 205, 55,  63,  91, 209,
      83,  57, 132,  60,  65, 162, 109,  71,  20,  42, 158,  93,  86, 242, 211, 171,
      68,  17, 146, 217,  35,  32,  46, 137, 180, 124, 184,  38, 119, 153, 227, 165,
      103,  74, 237, 222, 197,  49, 254,  24,  13,  99, 140, 128, 192, 247, 112,   7};

   final static int[] AlogTable = {
      1,   3,   5,  15,  17,  51,  85, 255,  26,  46, 114, 150, 161, 248,  19,  53,
      95, 225,  56,  72, 216, 115, 149, 164, 247,   2,   6,  10,  30, 34, 102, 170,
      229,  52,  92, 228,  55,  89, 235,  38, 106, 190, 217, 112, 144, 171, 230,  49,
      83, 245,   4,  12,  20,  60,  68, 204,  79, 209, 104, 184, 211, 110, 178, 205,
      76, 212, 103, 169, 224,  59,  77, 215,  98, 166, 241,   8,  24, 40, 120, 136,
      131, 158, 185, 208, 107, 189, 220, 127, 129, 152, 179, 206,  73, 219, 118, 154,
      181, 196,  87, 249,  16,  48,  80, 240,  11,  29,  39, 105, 187, 214,  97, 163,
      254,  25,  43, 125, 135, 146, 173, 236,  47, 113, 147, 174, 233,  32,  96, 160,
      251,  22,  58,  78, 210, 109, 183, 194,  93, 231,  50,  86, 250,  21,  63,  65,
      195,  94, 226,  61,  71, 201,  64, 192,  91, 237,  44, 116, 156, 191, 218, 117,
      159, 186, 213, 100, 172, 239,  42, 126, 130, 157, 188, 223, 122, 142, 137, 128,
      155, 182, 193,  88, 232,  35, 101, 175, 234,  37, 111, 177, 200,  67, 197,  84,
      252,  31,  33,  99, 165, 244,   7,   9,  27,  45, 119, 153, 176, 203,  70, 202,
      69, 207,  74, 222, 121, 139, 134, 145, 168, 227,  62,  66, 198, 81, 243,  14,
      18,  54,  90, 238,  41, 123, 141, 140, 143, 138, 133, 148, 167, 242,  13,  23,
      57,  75, 221, 124, 132, 151, 162, 253,  28,  36, 108, 180, 199, 82, 246,   1};

   final static byte[][] rcon = {
      {(byte) 0x01, 0, 0, 0},{(byte) 0x02, 0, 0, 0},{(byte) 0x04, 0, 0, 0},{(byte) 0x08, 0, 0, 0},{(byte) 0x10, 0, 0, 0},
      {(byte) 0x20, 0, 0, 0},{(byte) 0x40, 0, 0, 0},{(byte) 0x80, 0, 0, 0},{(byte) 0x1b, 0, 0, 0},{(byte) 0x36, 0, 0, 0},
      {(byte) 0x6c, 0, 0, 0},{(byte) 0xd8, 0, 0, 0},{(byte) 0xab, 0, 0, 0},{(byte) 0x4d, 0, 0, 0},{(byte) 0x9a, 0, 0, 0}};


   static ByteArrayInputStream inputReader;
   public static void main(String[] args) {
      if(args.length != 2) {
         System.out.println("Usage: java AES option inputFile");
         return;
      }

      boolean encode;
      String stringKey;
      String outputFileName = args[1];


      if (args[0].toLowerCase().equals("e")) {
         encode = true;
         outputFileName += ".enc";
      } else if (args[0].toLowerCase().equals("d")){
         encode = false;
         outputFileName += ".dec";
      } else {
         System.out.println("option must be one of \"e\" or \"d\"");
         return;
      }

      try(Scanner keyFile = new Scanner(System.in);){
         
         System.out.print("Enter key: ");
         stringKey = keyFile.next();

         if(stringKey.length() < 32)
         {
            System.out.printf("Key is not long enough\n");
            return;
         }

      } catch (Exception e) {
         System.out.printf("There was a problem\n");
         return;
      }

      try{
         inputReader = new ByteArrayInputStream(Files.readAllBytes(new File(args[1]).toPath()));
      } catch (Exception e) {
         System.out.printf("%s is an invalid input file\n", args[1]);
         return;
      }


      byte[][] key = new byte[60][4];
      byte[][] block = new byte[4][4];

      // convert key to block
      for(int i = 0; i < 32; ++i) {
         key[i/4][i%4] = (byte) stringKey.charAt(i);
      }

      debug("CipherKey:");
      printArr(key, 8, " ", false);

      debug("Expansion:");
      keyExpansion(key);
      printArr(key, 32, "", false);

      String input;
      long startTime = System.nanoTime();
      int fileSize = 0;
      System.out.println("Starting process..");
      try(FileOutputStream fos = new FileOutputStream(new File(outputFileName))) {
         int read;
         while((read = inputReader.available()) > 0) {

            fileSize += 16;

            block = getNewBlock();

            if (encode) {
               debug("Plaintext:");
               printArr(block, 4, " ", false);

               debug("Ciphertext:");
               encode(key, block);
               printArr(block, 4, " ", false);

            } else {
               debug("Ciphertext:");
               printArr(block, 4, " ", false);

               decode(key, block);
               debug("Decryption of Ciphertext:");
               printArr(block, 4, " ", false);
               // debug("\nDecryption of Ciphertext:");
            }


            // fos.write(byteLine(block), 0, Math.min(read, 16));
            fos.write(block[0]);
            fos.write(block[1]);
            fos.write(block[2]);
            fos.write(block[3]);
         }

         fos.flush();
         inputReader.close();
      } catch (Exception ignore){
         System.out.println("Something went wrong");
      }
      long duration = System.nanoTime() - startTime;
      System.out.println("Execution took: " + duration / 1000000 + " milliseconds");
      System.out.printf("FileSize: %d bytes\n", fileSize);
      System.out.printf("Throughput: %f MB/sec\n", fileSize/(double) duration * 1000);

   }

   public static byte[] byteLine(byte[][] block)
   {
      byte[] result = new byte[16];
      for(int i = 0; i < 4; i++)
      {
         for(int j = 0; j < 4; j++)
         {
            result[i*4+j] = block[i][j];
         }
      }
      return result;
   }

   public static byte[][] getNewBlock()
   {
      byte[][] result = new byte[4][4];
      for(int i = 0; i < 4; i++)
      {
         for(int j = 0; j < 4; j++)
         {
            if(inputReader.available() > 0)
            {
               result[i][j] = (byte) inputReader.read();
            }
            else
            {
               result[i][j] = 0;
            }
         }
      }
      return result;
   }

   public static void decode(byte[][] key, byte[][] block) {
      int round = 14;
      addRoundKey(block, round--, key);
      invShiftRows(block);
      invSubBytes(block);

      for(int i = 0; i < 13; i++) {
         addRoundKey(block, round--, key);
         invMixColumns(block);
         invShiftRows(block);
         invSubBytes(block);
      }

      addRoundKey(block, round--, key);
   }

   public static void encode(byte[][] key, byte[][] block) {
      int round = 0;
      addRoundKey(block, round++, key);

      for(int i = 0; i < 13; i++) {
         for(byte[] row: block)
            subBytes(row);

         debug("After subBytes:");
         printAsLine(block);

         shiftRows(block);
         mixColumns(block);
         addRoundKey(block, round++, key);
      }

      for(byte[] row: block)
            subBytes(row);

      debug("After subBytes:");
      printAsLine(block);

      shiftRows(block);
      addRoundKey(block, round++, key);
   }

   public static void keyExpansion(byte[][] key){
      int val = 0;
      for(int i = 8; i < key.length; i ++) {
         if(i % 8 == 0) {
            key[i] = rotate(key[i-1], 1);
            subBytes(key[i]);
            XOR(key[i], rcon[val++]);
            XOR(key[i], key[i - 8]);

         } else if (i % 4 == 0) {
            key[i] = Arrays.copyOf(key[i-1], key[i-1].length);
            subBytes(key[i]);
            // printArr(key, 20);
            XOR(key[i], key[i - 8]);

            // printArr(key, 32, " ", false);

         } else {
            key[i] = Arrays.copyOf(key[i-1], key[i-1].length);
            XOR(key[i], key[i - 8]);
         }
      }
   }

   public static void addRoundKey(byte[][] block, int start, byte[][] key) {
      for(int i = 0; i < 4; i++) {
         XOR(block[i], key[start*4 + i]);
      }
      debug("After addRoundKey("+start+"):");
      printAsLine(block);
   }

   public static byte[] rotate(byte[] word, int i) {
      byte[] copy = new byte[4];
      copy[0] = word[(0 + i)%4];
      copy[1] = word[(1 + i)%4];
      copy[2] = word[(2 + i)%4];
      copy[3] = word[(3 + i)%4];
      return copy;
   }

   public static void invShiftRows(byte[][] block) {
      byte temp = block[0][1];
      block[0][1] = block[3][1];
      block[3][1] = block[2][1];
      block[2][1] = block[1][1];
      block[1][1] = temp;

      temp = block[0][2];
      block[0][2] = block[2][2];
      block[2][2] = temp;

      temp = block[1][2];
      block[1][2] = block[3][2];
      block[3][2] = temp;

      temp = block[0][3];
      block[0][3] = block[1][3];
      block[1][3] = block[2][3];
      block[2][3] = block[3][3];
      block[3][3] = temp;

      debug("After invShiftRows:");
      printAsLine(block);
   }

   public static void shiftRows(byte[][] block) {
      byte temp = block[0][1];
      block[0][1] = block[1][1];
      block[1][1] = block[2][1];
      block[2][1] = block[3][1];
      block[3][1] = temp;

      temp = block[0][2];
      block[0][2] = block[2][2];
      block[2][2] = temp;

      temp = block[1][2];
      block[1][2] = block[3][2];
      block[3][2] = temp;

      temp = block[0][3];
      block[0][3] = block[3][3];
      block[3][3] = block[2][3];
      block[2][3] = block[1][3];
      block[1][3] = temp;

      debug("After shiftRows:");
      printAsLine(block);
   }

   public static void invSubBytes(byte[][] block) {
      for(int i = 0; i < 4; i++) {
         for (int j = 0; j < 4; j ++) {
            block[i][j] = invByteSubs[(block[i][j] >> 4) & 0xF][block[i][j] & 0xF];
         }
      }

      debug("After subBytes:");
      printAsLine(block);
   }

   public static void subBytes(byte[] word) {
      for(int i = 0; i < 4; i++) {
         word[i] = byteSubs[((word[i]) >> 4) & 0xF][word[i] & 0xF];
      }
   }

   public static void XOR(byte[] word1, byte[] word2) {
      for(int i = 0; i < 4; i++) {
         word1[i] ^= word2[i];
      }
   }

   private static byte mul (int a, byte b) {
      int inda = (a < 0) ? (a + 256) : a;
      int indb = (b < 0) ? (b + 256) : b;

      if ( (a != 0) && (b != 0) ) {
         int index = LogTable[inda] + LogTable[indb];
         int val = AlogTable[ index % 255 ];
         return (byte) val;
      } else {
          return 0;
      }
   }

   // In the following two methods, the input c is the column number in
   // your evolving state matrix st (which originally contained
   // the plaintext input but is being modified).  Notice that the state here is defined as an
   // array of bytes.  If your state is an array of integers, you'll have
   // to make adjustments.
   public static void mixColumns (byte[][] block) {
   // This is another alternate version of mixColumn, using the
   // logtables to do the computation.

      for(int i = 0; i < 4; i++) {
         byte a[] = new byte[4];

         // note that a is just a copy of st[.][c]
         for (int j = 0; j < 4; j++)
             a[j] = block[i][j];

         // This is exactly the same as mixColumns1, if
         // the mul columns somehow match the b columns there.
         block[i][0] = (byte) (mul(2,a[0]) ^ a[2] ^ a[3] ^ mul(3,a[1]));
         block[i][1] = (byte) (mul(2,a[1]) ^ a[3] ^ a[0] ^ mul(3,a[2]));
         block[i][2] = (byte) (mul(2,a[2]) ^ a[0] ^ a[1] ^ mul(3,a[3]));
         block[i][3] = (byte) (mul(2,a[3]) ^ a[1] ^ a[2] ^ mul(3,a[0]));
      }
      debug("After mixColumns:");
      printAsLine(block);
   } // mixColumn2

   public static void invMixColumns (byte[][] block) {
      for(int i = 0; i < 4; i++) {
         byte a[] = new byte[4];

         // note that a is just a copy of st[.][c]
         for (int j = 0; j < 4; j++)
             a[j] = block[i][j];

         block[i][0] = (byte) (mul(0xE,a[0]) ^ mul(0xB,a[1]) ^ mul(0xD, a[2]) ^ mul(0x9,a[3]));
         block[i][1] = (byte) (mul(0xE,a[1]) ^ mul(0xB,a[2]) ^ mul(0xD, a[3]) ^ mul(0x9,a[0]));
         block[i][2] = (byte) (mul(0xE,a[2]) ^ mul(0xB,a[3]) ^ mul(0xD, a[0]) ^ mul(0x9,a[1]));
         block[i][3] = (byte) (mul(0xE,a[3]) ^ mul(0xB,a[0]) ^ mul(0xD, a[1]) ^ mul(0x9,a[2]));
      }
   } // invMixColumn2

   public static String printAsLine(byte[][] block) {
      StringBuilder out = new StringBuilder();
      for(byte[] row: block) {
         for(byte i: row) {

            out.append((i<16 ? "0":"") + Integer.toHexString(i).toUpperCase());
            if(DEBUG) System.out.printf("%02X", i);
         }
      }
      if(DEBUG) System.out.printf("\n");
      return out.toString();
   }

   public static void printArr(byte[][] arr, int n, String delim, boolean force) {
      if (!force && !DEBUG) return;
      for(int i =0; i < 4; i++) {
         for(int j = 0; j < n; ++j) {
            if(j != 0 && j % 4 == 0)
               System.out.print(" ");
            if(arr[j][i] < 0x10) {
               System.out.printf("%02X%s", arr[j][i], delim);
            } else {
               System.out.printf("%02X%s", arr[j][i], delim);
            }
         }
         System.out.println();
      }
      System.out.println();
   }

   public static void debug(String s) {
      if (DEBUG)
         System.out.println(s);
   }
}