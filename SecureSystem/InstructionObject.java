public class InstructionObject {
	public static final int 
    READ = 1, 
    WRITE = 2,
    CREATE = 3,
    DESTROY = 4,
    RUN = 5,
    BAD = -1;
	public int type, value;
	public String subjectName, objectName;

  // For runs
  public InstructionObject(int type, String subjectName) {
    this.type = type;
    this.subjectName = subjectName;
  }

	// For reads
	public InstructionObject(int type, String subjectName, String objectName) {
		this.type = type;
		this.subjectName = subjectName;
		this.objectName = objectName;
	}

	// For writes
	public InstructionObject(int type, String subjectName, String objectName, int value) {
		this.type = type;
		this.subjectName = subjectName;
		this.objectName = objectName;
		this.value = value;
	}

	// For bad instructions
	public InstructionObject(int type) {
		this.type = type;
	}
}
