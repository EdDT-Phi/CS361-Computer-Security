import java.util.*;
import java.io.*;

// https://rosettacode.org/wiki/Huffman_coding#Java


abstract class HuffmanTree implements Comparable<HuffmanTree> {
    public final int frequency; // the frequency of this tree

    public HuffmanTree(int freq) {
        frequency = freq;
    }

    // compares on the frequency
    public int compareTo(HuffmanTree tree) {
        return frequency - tree.frequency;
    }
}

class HuffmanLeaf extends HuffmanTree {
    public final char value; // the character this leaf represents

    public HuffmanLeaf(int freq, char val) {
        super(freq);
        value = val;
    }
}

class HuffmanNode extends HuffmanTree {
    public final HuffmanTree left, right; // subtrees

    public HuffmanNode(HuffmanTree l, HuffmanTree r) {
        super(l.frequency + r.frequency);
        left = l;
        right = r;
    }
}

public class Encoder {
    // input is an array of frequencies, indexed by character code
    public static HuffmanTree buildTree(int[] charFreqs) {
        PriorityQueue<HuffmanTree> trees = new PriorityQueue<HuffmanTree>();
        // initially, we have a forest of leaves
        // one for each non-empty character
        for (int i = 0; i < charFreqs.length; i++)
            if (charFreqs[i] > 0)
                trees.offer(new HuffmanLeaf(charFreqs[i], (char) i));

        assert trees.size() > 0;
        // loop until there is only one tree left
        while (trees.size() > 1) {
            // two trees with least frequency
            HuffmanTree a = trees.poll();
            HuffmanTree b = trees.poll();

            // put into new node and re-insert into queue
            trees.offer(new HuffmanNode(a, b));
        }
        return trees.poll();
    }

    public static void printCodes(HuffmanTree tree, StringBuffer prefix) {
        assert tree != null;
        if (tree instanceof HuffmanLeaf) {
            HuffmanLeaf leaf = (HuffmanLeaf) tree;

            // print out character, frequency, and code for this leaf (which is just the prefix)
            System.out.println(letter.get((int) leaf.value) + "\t" + leaf.frequency + "\t" + prefix);
            bits[leaf.value] = prefix.toString();
        } else if (tree instanceof HuffmanNode) {
            HuffmanNode node = (HuffmanNode) tree;

            // traverse left
            prefix.append('0');
            printCodes(node.left, prefix);
            prefix.deleteCharAt(prefix.length() - 1);

            // traverse right
            prefix.append('1');
            printCodes(node.right, prefix);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    static String[] bits = new String[26];
    static int numLetters = 0;
    static HashMap<Integer, String> letter = new HashMap<Integer, String>();

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java Encoder frequencyFile k [j]");
        }

        Scanner lineScanner;
        int[] prob = new int[26];
        int sum = 0;
        int k = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(new File(args[0])))) {
            String line;
            k = Integer.parseInt(args[1]);
            while ((line = br.readLine()) != null) {
                prob[numLetters] = Integer.parseInt(line);
                letter.put(numLetters, (char) (numLetters + 'A') + "");
                sum += prob[numLetters++];
            }
        } catch (Exception ignored) {
            System.out.println("Nah");
            return;
        }
        float entropy = 0;
        for (int n : prob) {
            if (n > 0) {
                entropy += n * Math.log(n / (double) sum) / Math.log(2);
            }
        }

        entropy /= -sum;

        // build tree
        HuffmanTree tree = buildTree(prob);

        // print out results
        System.out.println("SYMBOL\tWEIGHT\tHUFFMAN CODE");
        printCodes(tree, new StringBuffer());

        makeVolumeOfText(prob, sum, k);

        int bitsEncode = encode(1, numLetters);
        decode(1);


        int[] prob2 = new int[numLetters * numLetters];
        bits = new String[numLetters * numLetters];
        for (int i = 0; i < numLetters; i++) {
            for (int j = 0; j < numLetters; j++) {
                letter.put(i * numLetters + j, "" + (char) (i + 'A') + (char) (j + 'A'));
                prob2[i * numLetters + j] = prob[i] * prob[j];
            }
        }

        HuffmanTree tree2 = buildTree(prob2);

        // print out results
        System.out.println("\nSYMBOL\tWEIGHT\tHUFFMAN CODE");
        printCodes(tree2, new StringBuffer());


        System.out.printf("\nEntropy: %f\n", entropy);
        System.out.printf("Bits per symbol 1: %f\n", bitsEncode/ (double) k);
        System.out.println("Diff 1: " + entropy * k * 100 / bitsEncode + "%");

//        makeVolumeOfText(prob2, sum * sum, k);
        bitsEncode = encode(2, numLetters);
        System.out.printf("\nBits per symbol 2: %f\n", bitsEncode/ (double) k);
        System.out.println("Diff 2: " + entropy * k * 100 / bitsEncode + "%");
        decode(2);

        if(args.length == 3){
            int j = Integer.parseInt(args[2]);
        }
    }

    public static int encode(int num, int numLetters) {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(new File("testText")));
             PrintWriter printer = new PrintWriter(new FileWriter(new File("testText.enc" + num)))) {
            String line;
            while ((line = br.readLine()) != null) {
                for(int index = 0; index < line.length(); index += num) {
                    String letters = line.substring(index, index + num);
                    String encryption = "";
                    int i = 0;
                    while (encryption.equals("")) {
                        if (num == 1) {
                            encryption = bits[letters.charAt(0) - 'A'] + "\n";
                        } else {
                            encryption = bits[(letters.charAt(0) - 'A') * numLetters + letters.charAt(1) - 'A'] + "\n";
                        }
                    }
                    count += encryption.length();
                    printer.append(encryption);
                }
            }
        } catch (Exception ignored) {
            System.out.println("Nahhh");
        }
        return count;
    }

    public static void decode(int num) {
        try (BufferedReader br = new BufferedReader(new FileReader(new File("testText.enc" + num)));
             PrintWriter printer = new PrintWriter(new FileWriter(new File("testText.dec" + num)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String decryption = "";
                int i = 0;
                while (decryption.equals("")) {
                    if (bits[i++].equals(line)) {
                        decryption = letter.get(i-1); // (char) ('A' + i - 1) + "";
                    }
                }
                printer.append(decryption);
            }
        } catch (Exception ignored) {
            System.out.println("Nahhhh");
        }
    }

    public static void makeVolumeOfText(int[] prob, int symbolCount, int k) {
        try (PrintWriter out1 = new PrintWriter(new FileWriter(new File("testText")))) {
            int index;
            //Add a character k times
            for (int i = 0; i < k; i++) {
                //Find a random number from 0 to symbolCount
                int randomNum = (int) Math.floor(Math.random() * symbolCount);
                for (index = 0; index < prob.length; index++) {
                    //Subtract from the randomNum the value in each index of prob[] till you reach 0 to iterate to the correct index.
                    randomNum -= prob[index];
                    if (randomNum <= 0)
                        break;
                }
                //Now that you have the index, add the letter that pertains to that index to the file
                out1.append(letter.get(index));
            }
        } catch (Exception ignored) {
            System.out.println("Nahh");
        }
    }
}