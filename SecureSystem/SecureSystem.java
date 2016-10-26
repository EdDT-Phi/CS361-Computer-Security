import java.util.*;

public class SecureSystem {
    public static ReferenceMonitor rf;
    public static final Scanner scan = new Scanner(System.in);

    public SecureSystem(String file) {
        rf = ReferenceMonitor.getReferenceMonitor(file);

    }


    public static void main(String args[]) {
        if(args.length < 1) System.out.println("Missing file name!");
        SecureSystem sc = new SecureSystem(args[0]);
        while (scan.hasNext()) {
            rf.executeCommand(parse(scan.nextLine().toLowerCase()));
        }
    }

    public static void execute(String line) {

        rf.executeCommand(parse(line.toLowerCase()));
    }

    private static InstructionObject parse(String line) {
        String[] args = line.split(" ");
        switch (args[0]) {
            case "read":
                if (args.length != 3)
                    return new InstructionObject(InstructionObject.BAD);

                return new InstructionObject(InstructionObject.READ, args[1], args[2]);
            case "write":
                if (args.length != 4)
                    return new InstructionObject(InstructionObject.BAD);

                try {
                    return new InstructionObject(InstructionObject.WRITE, args[1], args[2], Integer.parseInt(args[3]));
                } catch (Exception e) {
                    return new InstructionObject(InstructionObject.BAD);
                }
            case "create":
                if (args.length != 3)
                    return new InstructionObject(InstructionObject.BAD);

                return new InstructionObject(InstructionObject.CREATE, args[1], args[2]);
            case "destroy":
                if (args.length != 3)
                    return new InstructionObject(InstructionObject.BAD);

                return new InstructionObject(InstructionObject.DESTROY, args[1], args[2]);
            case "run":
                if (args.length != 2)
                    return new InstructionObject(InstructionObject.BAD);

                return new InstructionObject(InstructionObject.RUN, args[1]);
            default:
                return new InstructionObject(InstructionObject.BAD);
        }
    }
}
