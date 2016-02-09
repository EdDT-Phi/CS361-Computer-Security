import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SecureSystem {
	public static Scanner scan;
	public static ReferenceMonitor rf = ReferenceMonitor.getReferenceMonitor();

	public static void main(String args[]) 
  {
		
		System.out.println(args[0]);
		
//		scan = new Scanner(System.in);
		try {
			scan = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		
		
		// LOW and HIGH are constants defined in the SecurityLevel
		// class, such that HIGH dominates LOW.
		SecurityLevel low = SecurityLevel.LOW;
		SecurityLevel high = SecurityLevel.HIGH;

		// We add two subjects, one high and one low.

		rf.createSubject("Lyle", low);
		rf.createSubject("Hal", high);

		// We add two objects, one high and one low.
		rf.createNewObject("Lobj", low);
		rf.createNewObject("Hobj", high);

		while (scan.hasNext()) {
			rf.executeCommand(parse(scan.nextLine().toLowerCase()));
		}
	}

	private static InstructionObject parse(String line) 
  {
		Scanner scanLine = new Scanner(line);
		String subjectName, objectName;
		int value;
		try {
			String type = scanLine.next();
			if (type.equals("read")) {
				subjectName = scanLine.next();
				objectName = scanLine.next();

				if (scanLine.hasNext()) {
					scanLine.close();
					return new InstructionObject(-1);
				}

				scanLine.close();
				return new InstructionObject(1, subjectName, objectName);
			} else if (type.equals("write")) {

				subjectName = scanLine.next();
				objectName = scanLine.next();
				value = scanLine.nextInt();

				if (scanLine.hasNext()) {
					scanLine.close();
					return new InstructionObject(-1);
				}

				scanLine.close();
				return new InstructionObject(2, subjectName, objectName, value);
			} else {
				scanLine.close();
				return new InstructionObject(-1);
			}
		} catch (Exception e) {
			scanLine.close();
			return new InstructionObject(-1);
		}
	}
}
