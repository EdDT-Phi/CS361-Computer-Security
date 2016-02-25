import java.io.*;
import java.nio.file.Files;

public class CovertChannel {

    public static boolean VERBOSE = false;
    static PrintWriter writer;

    public static void main(String args[]) throws Exception {


        try {
            writer = new PrintWriter("log", "UTF-8");


            ByteArrayInputStream is;


            long startTime = System.nanoTime();
            String file;
            if (args.length == 1)
              file = args[0];
            else if (args.length == 2) {
                if (!args[0].toLowerCase().equals("v"))
                    return;
                VERBOSE = true;
                 file = args[1];
            } else {
                System.out.println("Usage: java CovertChannel [v] inputFile");
                return;
            }
            is = new ByteArrayInputStream(Files.readAllBytes(new File(file).toPath()));

            new SecureSystem(file);
            byte me;
            int bytes = 0;
            while ((me = (byte) is.read()) != -1) {
                bytes++;
                for (int i = 0; i < 8; i++) {
                    if ((me & 1) == 0) {
                        instruction("CREATE HAL OBJ");
                    }

                    lyleInstructions();
                    me >>= 1;
                }
            }

            long duration = System.nanoTime() - startTime;

            System.out.println("Execution took: " + duration / 1000000 + " milliseconds");
            System.out.println("Execution speed: " + (bytes * 8) / (duration / 1000000) + " bits/ms");


        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
    }

    public static void lyleInstructions() {
        instruction("CREATE LYLE OBJ");
        instruction("WRITE LYLE OBJ 1");
        instruction("READ LYLE OBJ");
        instruction("DESTROY LYLE OBJ");
        instruction("RUN LYLE");
    }

    public static void instruction(String line) {
        if (VERBOSE) {
            writer.println(line);
        }
        SecureSystem.execute(line);
    }

}
