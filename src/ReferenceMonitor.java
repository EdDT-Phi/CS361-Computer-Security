import java.util.*;
import java.io.*;

public class ReferenceMonitor {

    private static FileOutputStream stream;

    class SecurityLevel {

      public static final int HIGH = 1;
      public static final int LOW = 0;
      private int level;

      public SecurityLevel(int sL) {
          level = sL;
      }

      public SecurityLevel(SecurityLevel sL) {
          level = sL.level;
      }

      public boolean dominates(SecurityLevel sL2) {
          return this.level >= sL2.level;
      }
    }

    class ssSubject {
        private String name;
        private SecurityLevel sL;
        private int tempValue = 0;
        private int curBit = 0;
        private byte byteToWrite = 0;

        private ssSubject(String name, SecurityLevel sL) {
            this.name = name;
            this.sL = sL;
        }


        private void run() {
            if (name.toLowerCase().equals("lyle")) {
                byteToWrite += tempValue << curBit;
                curBit++;
                if (curBit >= 8) {

                    // output to file
                    try {
                        stream.write(byteToWrite);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    curBit = 0;
                    byteToWrite = 0;
                }
            }
        }
    }

    private static ReferenceMonitor refMon = new ReferenceMonitor();
    private static HashMap<String, ssSubject> subjects;
    private static ObjectManager om = new ObjectManager();

    private ReferenceMonitor() {
        subjects = new HashMap<>();
        SecurityLevel low = new SecurityLevel(SecurityLevel.LOW);
        subjects.put("lyle", new ssSubject("lyle", new SecurityLevel(SecurityLevel.LOW)));
        subjects.put("hal", new ssSubject("hal", new SecurityLevel(SecurityLevel.HIGH)));
    }

    public static ReferenceMonitor getReferenceMonitor(String fileName) {
        try {
            stream = new FileOutputStream(fileName + ".out");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return refMon;
    }

    public void executeCommand(InstructionObject io) {
        if (io.type == InstructionObject.READ)
            om.executeRead(io);
        else if (io.type == InstructionObject.WRITE)
            om.executeWrite(io);
        else if (io.type == InstructionObject.CREATE)
            om.executeCreate(io);
        else if (io.type == InstructionObject.DESTROY)
            om.executeDestroy(io);
        else if (io.type == InstructionObject.RUN)
            subjects.get(io.subjectName).run();

    }

    private static class ObjectManager {


        private ObjectManager() {
        }

        class ssObject {
            public String name;
            public SecurityLevel sL;
            public int intValue = 0;

            public ssObject(String name, SecurityLevel sL) {
                this.name = name;
                this.sL = sL;
            }
        }

        private static HashMap<String, ssObject> objects = new HashMap<>();

        private void executeWrite(InstructionObject io) {
            ssSubject subj = subjects.get(io.subjectName);
            ssObject obj = objects.get(io.objectName);
            if (subj == null || obj == null) {
                return;
            }
            if (obj.sL.dominates(subj.sL))
                obj.intValue = io.value;
        }

        private void executeRead(InstructionObject io) {
            ssSubject subj = subjects.get(io.subjectName);
            ssObject obj = objects.get(io.objectName);
            if (subj == null || obj == null) {
                return;
            }

            if (subj.sL.dominates(obj.sL))
                subj.tempValue = obj.intValue;
            else
                subj.tempValue = 0;
        }

        private void executeDestroy(InstructionObject io) {
            ssSubject subj = subjects.get(io.subjectName);
            ssObject obj = objects.get(io.objectName);

            if (subj == null || obj == null) {
                return;
            }

            if (obj.sL.dominates(subj.sL)) {
                objects.remove(io.objectName);
            }

        }

        private void executeCreate(InstructionObject io) {
            ssSubject subj = subjects.get(io.subjectName);
            ssObject obj = objects.get(io.objectName);

            if (subj == null || obj != null) {
                return;
            }

            obj = new ssObject(io.objectName, subj.sL);

            objects.put(io.objectName.toLowerCase(), obj);
        }


        public void printState() {
            System.out.println("The current state is:");

            for (ssObject obj : objects.values()) {
                System.out.println("	" + obj.name + " has value: "
                        + obj.intValue);
            }

            for (ssSubject subj : subjects.values()) {
                System.out.println("	" + subj.name + " has recently read: "
                        + subj.tempValue);
            }
        }
    }
}
