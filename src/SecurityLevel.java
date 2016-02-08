public class SecurityLevel {

	public static final SecurityLevel HIGH = new SecurityLevel(1);
	public static final SecurityLevel LOW = new SecurityLevel(0);
	public int level;

	public SecurityLevel(int sL) 
	{
		level = sL;
	}

	public static boolean dominates(SecurityLevel sL1, SecurityLevel sL2) {
		return sL1.level >= sL2.level;
	}
}
