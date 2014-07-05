package asgn1Question;

/**
 * This class represents any exceptions generated during the
 * simulation.  If you find that the parameters provided to your
 * methods are invalid or some other unrecoverable error has
 * occurred then you should throw a new instance of
 * this class.
 * 
 * @author INB370 
 * @version 1.0
 */
@SuppressWarnings("serial") // We're not interested in binary i/o here
public class SimulationException extends Exception {
	
	/**
	 * Creates a new instance of SimulationException.
	 * 
	 * @param message an informative message about the problem encountered
	 */
	public SimulationException(String message) {
		super("Simulation Exception: " + message);
	}
}
