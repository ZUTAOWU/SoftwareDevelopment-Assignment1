package asgn1Solution;

/*
 * Class that invokes the DamActions and WaterLog constructors with median values.
 * This is because they are not included in the interface.
 */
class ConstructorTest {
	public static void main(String[] argv) throws Exception {
		Integer zero = new Integer(0);
		WaterLog wl = new WaterLog(zero, zero);
		DamActions da = new DamActions(zero, zero, zero, wl);
		
		wl = new WaterLog(null, null);
		da = new DamActions(null, null, null, null);
	}
}