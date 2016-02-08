import java.util.*;

public class ReferenceMonitor {
	private static ReferenceMonitor refMon = new ReferenceMonitor();
	private static HashMap<String, ssSubject> subjects = new HashMap<String, ssSubject>();

	private ReferenceMonitor() {
	}

	public static ReferenceMonitor getReferenceMonitor() {
		return refMon;
	}

	public void createNewObject(String name, SecurityLevel sL) {
		ObjectManager.createNewObject(name, sL);
	}

	public void createSubject(String name, SecurityLevel sL) {
		subjects.put(name.toLowerCase(), new ssSubject(name, sL, 0));
	}

	public void executeCommand(InstructionObject io) {
		if (io.type == InstructionObject.READ)
			ObjectManager.executeRead(io);
		else if (io.type == InstructionObject.WRITE)
			ObjectManager.executeWrite(io);
		else
			System.out.println("Bad Instruction");
		ObjectManager.printState();
	}

	private static class ObjectManager {
		private static HashMap<String, ssObject> objects = new HashMap<String, ssObject>();

		private static void createNewObject(String name, SecurityLevel sL) {
			objects.put(name.toLowerCase(), new ssObject(name, sL, 0));
		}

		private static void executeWrite(InstructionObject io) {
			ssSubject subj = subjects.get(io.subjectName);
			ssObject obj = objects.get(io.objectName);
			if (subj == null || obj == null) {
				System.out.println("Bad Instruction");
				return;
			}
			if (SecurityLevel.dominates(obj.sL, subj.sL))
				obj.intValue = io.value;
			System.out.println(subj.name + " writes value " + io.value + " to "
					+ obj.name);
		}

		private static void executeRead(InstructionObject io) {
			ssSubject subj = subjects.get(io.subjectName);
			ssObject obj = objects.get(io.objectName);
			if (subj == null || obj == null) {
				System.out.println("Bad Instruction");
				return;
			}
			if (SecurityLevel.dominates(subj.sL, obj.sL))
			subj.tempValue = obj.intValue;
			System.out.println(subj.name + " reads " + obj.name);
		}

		public static void printState() {
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
