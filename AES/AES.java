import java.util.*;
import java.io.*;

class AES {

   static int[][] byteSubs = 
   {
      {0x63, 0x7C, 0x77, 0x7B, 0xF2, 0x6B, 0x6F, 0xC5, 0x30, 0x01, 0x67, 0x2B, 0xFE, 0xD7, 0xAB, 0x76},
      {0xCA, 0x82, 0xC9, 0x7D, 0xFA, 0x59, 0x47, 0xF0, 0xAD, 0xD4, 0xA2, 0xAF, 0x9C, 0xA4, 0x72, 0xC0},
      {0xB7, 0xFD, 0x93, 0x26, 0x36, 0x3F, 0xF7, 0xCC, 0x34, 0xA5, 0xE5, 0xF1, 0x71, 0xD8, 0x31, 0x15},
      {0x04, 0xC7, 0x23, 0xC3, 0x18, 0x96, 0x05, 0x9A, 0x07, 0x12, 0x80, 0xE2, 0xEB, 0x27, 0xB2, 0x75},
      {0x09, 0x83, 0x2C, 0x1A, 0x1B, 0x6E, 0x5A, 0xA0, 0x52, 0x3B, 0xD6, 0xB3, 0x29, 0xE3, 0x2F, 0x84},
      {0x53, 0xD1, 0x00, 0xED, 0x20, 0xFC, 0xB1, 0x5B, 0x6A, 0xCB, 0xBE, 0x39, 0x4A, 0x4C, 0x58, 0xCF},
      {0xD0, 0xEF, 0xAA, 0xFB, 0x43, 0x4D, 0x33, 0x85, 0x45, 0xF9, 0x02, 0x7F, 0x50, 0x3C, 0x9F, 0xA8},
      {0x51, 0xA3, 0x40, 0x8F, 0x92, 0x9D, 0x38, 0xF5, 0xBC, 0xB6, 0xDA, 0x21, 0x10, 0xFF, 0xF3, 0xD2},
      {0xCD, 0x0C, 0x13, 0xEC, 0x5F, 0x97, 0x44, 0x17, 0xC4, 0xA7, 0x7E, 0x3D, 0x64, 0x5D, 0x19, 0x73},
      {0x60, 0x81, 0x4F, 0xDC, 0x22, 0x2A, 0x90, 0x88, 0x46, 0xEE, 0xB8, 0x14, 0xDE, 0x5E, 0x0B, 0xDB},
      {0xE0, 0x32, 0x3A, 0x0A, 0x49, 0x06, 0x24, 0x5C, 0xC2, 0xD3, 0xAC, 0x62, 0x91, 0x95, 0xE4, 0x79},
      {0xE7, 0xC8, 0x37, 0x6D, 0x8D, 0xD5, 0x4E, 0xA9, 0x6C, 0x56, 0xF4, 0xEA, 0x65, 0x7A, 0xAE, 0x08},
      {0xBA, 0x78, 0x25, 0x2E, 0x1C, 0xA6, 0xB4, 0xC6, 0xE8, 0xDD, 0x74, 0x1F, 0x4B, 0xBD, 0x8B, 0x8A},
      {0x70, 0x3E, 0xB5, 0x66, 0x48, 0x03, 0xF6, 0x0E, 0x61, 0x35, 0x57, 0xB9, 0x86, 0xC1, 0x1D, 0x9E},
      {0xE1, 0xF8, 0x98, 0x11, 0x69, 0xD9, 0x8E, 0x94, 0x9B, 0x1E, 0x87, 0xE9, 0xCE, 0x55, 0x28, 0xDF},
      {0x8C, 0xA1, 0x89, 0x0D, 0xBF, 0xE6, 0x42, 0x68, 0x41, 0x99, 0x2D, 0x0F, 0xB0, 0x54, 0xBB, 0x16}
   };
   static int[][] rcon = {
   {0x01, 0, 0, 0},{0x02, 0, 0, 0},{0x04, 0, 0, 0},{0x08, 0, 0, 0},{0x10, 0, 0, 0},
   {0x20, 0, 0, 0},{0x40, 0, 0, 0},{0x80, 0, 0, 0},{0x1b, 0, 0, 0},{0x36, 0, 0, 0},
   {0x6c, 0, 0, 0},{0xd8, 0, 0, 0},{0xab, 0, 0, 0},{0x4d, 0, 0, 0},{0x9a, 0, 0, 0}};
   public static void main(String[] args) {
      if(args.length != 3) {
         System.out.println("Usage: java AES option keyFile inputFile");
      }

      // String stringKey     =  "FFEEDDCCBBAA00998877665544332211FFEEDDCCBBAA00998877665544332211";
      String stringKey  =  "0000000000000000000000000000000000000000000000000000000000000000";
      String input      =  "00112233445566778899AABBCCDDEEFF";

      // System.out.println("key length: " + key.length() + "\n");

      int[][] key = new int[60][4];
      int[][] block = new int[4][4];

      for(int i = 0; i < stringKey.length()/2; ++i) {
         key[i/4][i%4] = Integer.decode("0x" + stringKey.substring(i*2 , i*2 + 2).toLowerCase());
      }


      for(int i = 0; i < input.length()/2; ++i) {
         block[i/4][i%4] = Integer.decode("0x" + input.substring(i*2, i*2 + 2).toLowerCase());
      }
      System.out.println("Plaintext:");
      printArr(block, 4);

      keyExpansion(key);

      System.out.println("\nCipherKey:");
      printArr(key, 8);

      System.out.println("\nExpansion:");
      printArr(key, 32);


      encode(key, block);
   }

   public static void encode(int[][] key, int[][] block) {
      int round = 0;
      addRoundKey(block, round++, key);

      for(int i = 0; i < 13; i++) {
         for(int[] row: block)
            subBytes(row);
         System.out.printf("After subBytes:\n");
         printAsLine(block);



         shiftRows(block);
         
         // MIX COLUMNS

         addRoundKey(block, round++, key);
      }

      for(int[] row: block)
            subBytes(row);
         
      shiftRows(block);
      addRoundKey(block, round++, key);
   }

   public static void addRoundKey(int[][] block, int start, int[][] key) {
      for(int i = 0; i < 4; i++) {
         XOR(block[1], key[start*4 + i]);
      }
      System.out.printf("After addRoundKey(%d):\n", start);
      printAsLine(block);
   }

   public static void printAsLine(int[][] block) {
      for(int[] row: block) {
         for(int i: row) {
            System.out.printf("%x", i);
         }
      }
      System.out.println();
   }

   public static void shiftRows(int[][] block) {
      int temp = block[0][1];
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

      System.out.printf("After shiftRows:\n");
      printAsLine(block);
   }


   public static void keyExpansion(int[][] key){
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

         } else {
            key[i] = Arrays.copyOf(key[i-1], key[i-1].length);
            XOR(key[i], key[i - 8]);
         }
      }
   }

   public static void subBytes(int[] word) {
      for(int i = 0; i < 4; i++) {
         word[i] = byteSubs[word[i] >> 4][word[i] & 0xF];
      } 
   }

   public static void XOR(int[] word1, int[] word2) {
      for(int i = 0; i < 4; i++) {
         word1[i] ^= word2[i];
      } 
   }

   public static void printArr(int[][] arr, int n) {
      for(int i =0; i < 4; i++) {
         for(int j = 0; j < n; ++j) {
            if(j != 0 && j % 4 == 0)
               System.out.print(" ");
            if(arr[j][i] < 16) {
               System.out.printf("0%x", arr[j][i]);
            } else {
               System.out.printf("%x", arr[j][i]);
            }
         }
         System.out.println();
      }
   }

   public static int[] rotate(int[] word, int i) {
      int[] copy = new int[4];
      copy[0] = word[(0 + i)%4];
      copy[1] = word[(1 + i)%4];
      copy[2] = word[(2 + i)%4];
      copy[3] = word[(3 + i)%4];
      return copy;
      // return new {i[1], i[2], i[3], i[0]};
   }

   // // http://www.cs.utsa.edu/~wagner/laws/AESkeys.html
   // void KeyExpansion(byte[] key, word[] w, int Nw) {
   //    int Nr = Nk + 6;
   //    w = new byte[4*Nb*(Nr+1)];
   //    int temp;
   //    int i = 0;
   //    while ( i < Nk) {
   //       w[i] = word(key[4*i], key[4*i+1], key[4*i+2], key[4*i+3]);
   //       i++;
   //    }
   //    i = Nk;
   //    while(i < Nb*(Nr+1)) {
   //       temp = w[i-1];
   //       if (i % Nk == 0)
   //          temp = SubWord(RotWord(temp)) ^ Rcon[i/Nk];
   //       else if (Nk > 6 && (i%Nk) == 4)
   //          temp = SubWord(temp);
   //       w[i] = w[i-Nk] ^ temp;
   //       i++;
   //    }
   // }
}