package asgn1Question;

import javax.swing.JFrame;
import static javax.swing.JFrame.*;

/*
 * This class contains the main program for the dam simulator.
 * 
 * @author INB370
 * @version 1.0
 */
class Dam {

	/*
	 * Program entry point.
	 * 
	 * Creates the GUI's main frame, ready for users to begin controlling the
	 * simulation.
	 */
	public static void main(String[] args) {
		// Create the main frame
		JFrame mainFrame = new JFrame();
		// Terminate the program if the user closes the main frame
		mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		// Set the title for the main frame
		mainFrame.setTitle("Dam Simulator");
		// Add components to the frame
		mainFrame.getContentPane().add(new SimulationComponents());
		// Resize the main frame to fit its components
		mainFrame.pack();
		// Make the simulation visible
		mainFrame.setVisible(true);
	}
}
