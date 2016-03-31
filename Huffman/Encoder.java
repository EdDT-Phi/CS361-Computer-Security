import java.util.*;
import java.io.*;

// https://rosettacode.org/wiki/Huffman_coding#Java


abstract class HuffmanTree implements Comparable<HuffmanTree> {
    public final int frequency; // the frequency of this tree
    public HuffmanTree(int freq) { frequency = freq; }

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
                trees.offer(new HuffmanLeaf(charFreqs[i], (char)i));

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
            HuffmanLeaf leaf = (HuffmanLeaf)tree;

            // print out character, frequency, and code for this leaf (which is just the prefix)
            System.out.println((char) (leaf.value + 'a') + "\t" + leaf.frequency + "\t" + prefix);
            bits[leaf.value] = prefix.toString();
        } else if (tree instanceof HuffmanNode) {
            HuffmanNode node = (HuffmanNode)tree;

            // traverse left
            prefix.append('0');
            printCodes(node.left, prefix);
            prefix.deleteCharAt(prefix.length()-1);

            // traverse right
            prefix.append('1');
            printCodes(node.right, prefix);
            prefix.deleteCharAt(prefix.length()-1);
        }
    }

    static String[] bits = new String[26];
    public static void main(String[] args) {
        String test = "this is an example for huffman encoding";

        if (args.length != 2)
            System.out.println("Usage: java Encoder {frequencyFile} k");

        Scanner lineScanner;
        int[] prob = new int[26];
        int sum = 0, i = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(new File(args[0])))) {
            String line;
            while ((line = br.readLine()) != null) {
                prob[i] = Integer.parseInt(line);
                sum += prob[i++];
            }
        } catch(Exception ignored) {
            System.out.println("Nah");
            return;
        }
        float entropy = 0;
        for(int n: prob){
            if(n > 0)
                entropy += n * Math.log(n / (double)sum) / Math.log(2);
        }

        entropy /= -sum;

        System.out.printf("Entropy: %f\n", entropy);

        // build tree
        HuffmanTree tree = buildTree(prob);

        // print out results
        System.out.println("SYMBOL\tWEIGHT\tHUFFMAN CODE");
        printCodes(tree, new StringBuffer());


        makeVolumeOfText(prob, sum, 100);
    }

    public static void makeVolumeOfText (int[] prob, int symbolCount, int k)
    {
        try (PrintWriter out1  = new PrintWriter(new FileWriter(new File("testText.txt")))) {
            int index;
            //Add a character k times
            for (int i = 0; i < k; i++)
            {
                //Find a random number from 0 to symbolCount
                int randomNum = (int) Math.floor(Math.random() * symbolCount);
                for (index = 0; index < prob.length; index++)
                {
                    //Subtract from the randomNum the value in each index of prob[] till you reach 0 to iterate to the correct index.
                    randomNum -= prob[index];
                    if (randomNum <= 0)
                        break;
                }
                //Now that you have the index, add the letter that pertains to that index to the file
                out1.append((char)('a' + index));
            }
        } catch (Exception ignored) {
            System.out.println("Nahh");
        }
    }
}