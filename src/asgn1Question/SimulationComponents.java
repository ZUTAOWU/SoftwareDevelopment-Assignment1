package asgn1Question;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import static javax.swing.border.TitledBorder.*;

import java.util.Random;

// Import the student's solution to the assignment
import asgn1Solution.DamActions;
import asgn1Solution.WaterLog;

/*
 * These are the GUI components used to run the simulation.  (You should
 * study this code to see how your classes will be used.)
 * 
 * @author INB370
 * @version 1.0
 */
@SuppressWarnings("serial")
// We don't care about binary i/o here
class SimulationComponents extends JPanel implements ActionListener {

	// Default values for the simulation parameters
	public static final Long DefaultRandomSeed = 100L;
	public static final Integer DefaultDamCapacity = 1053; // megalitres
	public static final Integer DefaultMaxInflow = 109; // megalitres
	public static final Integer DefaultMaxConsumption = 65; // megalitres
	public static final Integer DefaultDailyRelease = 50; // megalitres
	public static final Integer DefaultJobDuration = 14; // days

	// Buttons
	private JButton startButton;
	private JButton halfReleaseButton;
	private JButton defaultReleaseButton;
	private JButton doubleReleaseButton;
	private JPanel buttons;

	// Display for simulation messages
	private JTextArea display;
	private JScrollPane textScrollPane;

	// How big a margin to allow for the main frame
	final Integer mainMargin = 20; // pixels

	// How big a margin to allow around the progress bars
	final Integer progressMargin = 10; // pixels

	// Mutable text fields for simulation parameters
	private JTextField seedText;
	private JTextField capacityText;
	private JTextField maxInflowText;
	private JTextField maxConsumptionText;
	private JTextField durationText;
	private JTextField defaultReleaseText;

	// Progress bars for displaying water levels
	private JPanel progressBars;
	private JProgressBar lastWeeksLevel;
	private JProgressBar yesterdaysLevel;
	private JProgressBar todaysLevel;

	// Places where we'll add components to a frame
	private enum Position {
		MIDDLELEFT, TOPCENTRE, MIDDLECENTRE, BOTTOMCENTRE
	};

	// Options available to the user
	private enum Selection {
		HALF, DEFAULT, DOUBLE
	};

	// Simulation state
	private Integer maxDailyConsumption;
	private Integer damCapacity;
	private Integer maxDailyInflow;
	private Integer jobDuration;
	private Integer defaultReleaseAmount;
	private Random randomNumberGenerator;
	private DamActions actions;
	private WaterLog log;

	/*
	 * Create a new simulation and initialise all of the contained GUI
	 * components
	 */
	public SimulationComponents() {
		// Initialize the GUI Components
		initialiseComponents();
	}

	/*
	 * Initialise all the GUI components (including those that are not visible
	 * initially)
	 */
	private void initialiseComponents() {

		// Choose a grid layout for the main frame
		this.setLayout(new GridBagLayout());

		// Create a scrollable text area for displaying instructions and
		// messages
		display = new JTextArea(5, 40); // lines by columns
		display.setEditable(false);
		display.setLineWrap(true);
		textScrollPane = new JScrollPane(display);
		this.add(textScrollPane, positionConstraints(Position.TOPCENTRE, mainMargin));
		resetDisplay("Set the initial simulation parameters and press 'Start'\n\n");

		// Create a panel to contain the water level progress bars
		progressBars = new JPanel();
		progressBars.setVisible(true);
		progressBars.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 1, true), "Water levels", CENTER, BOTTOM));
		this.add(progressBars, positionConstraints(Position.MIDDLECENTRE, mainMargin));

		// Labelled progress bar for displaying last week's water level
		lastWeeksLevel = new JProgressBar(SwingConstants.VERTICAL);
		lastWeeksLevel.setIndeterminate(true);
		lastWeeksLevel.setStringPainted(true);
		JPanel lastWeeksPanel = new JPanel(new GridBagLayout());
		lastWeeksPanel.add(lastWeeksLevel, positionConstraints(Position.TOPCENTRE, progressMargin));
		lastWeeksPanel.add(new JLabel("Last week"), positionConstraints(Position.BOTTOMCENTRE, progressMargin));
		progressBars.add(lastWeeksPanel, positionConstraints(Position.MIDDLELEFT, mainMargin));

		// Labelled progress bar for displaying yeseterday's water level
		yesterdaysLevel = new JProgressBar(SwingConstants.VERTICAL);
		yesterdaysLevel.setIndeterminate(true);
		yesterdaysLevel.setStringPainted(true);
		JPanel yesterdaysPanel = new JPanel(new GridBagLayout());
		yesterdaysPanel.add(yesterdaysLevel, positionConstraints(Position.TOPCENTRE, progressMargin));
		yesterdaysPanel.add(new JLabel("Yesterday"), positionConstraints(Position.BOTTOMCENTRE, progressMargin));
		progressBars.add(yesterdaysPanel, positionConstraints(Position.MIDDLELEFT, mainMargin));

		// Labelled progress bar for displaying today's water level
		todaysLevel = new JProgressBar(SwingConstants.VERTICAL);
		todaysLevel.setIndeterminate(true);
		todaysLevel.setStringPainted(true);
		JPanel todayPanel = new JPanel(new GridBagLayout());
		todayPanel.add(todaysLevel, positionConstraints(Position.TOPCENTRE, progressMargin));
		todayPanel.add(new JLabel("  Today  "), positionConstraints(Position.BOTTOMCENTRE, progressMargin));
		progressBars.add(todayPanel, positionConstraints(Position.MIDDLELEFT, mainMargin));

		// Add editable panels for simulation parameters
		seedText = addParameterPanel("Random number seed:", DefaultRandomSeed);
		capacityText = addParameterPanel("Dam capacity (megalitres):", DefaultDamCapacity);
		maxInflowText = addParameterPanel("Maximum daily inflow (megalitres):", DefaultMaxInflow);
		maxConsumptionText = addParameterPanel("Maximum daily consumption (megalitres):", DefaultMaxConsumption);
		defaultReleaseText = addParameterPanel("Default downriver release (megalitres):", DefaultDailyRelease);
		durationText = addParameterPanel("Job duration (days):", DefaultJobDuration);

		// Panel to contain the buttons
		buttons = new JPanel(new GridBagLayout());
		buttons.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Dam", CENTER, BOTTOM));
		this.add(buttons, positionConstraints(Position.BOTTOMCENTRE, mainMargin));
		buttons.setVisible(true);

		// Button for starting the simulation
		startButton = new JButton("Start");
		startButton.addActionListener(this);
		buttons.add(startButton);

		// Buttons for controlling the simulation (initially unavailable)
		halfReleaseButton = new JButton(" Half ");
		halfReleaseButton.addActionListener(this);
		halfReleaseButton.setVisible(false);
		buttons.add(halfReleaseButton);
		defaultReleaseButton = new JButton("Default");
		defaultReleaseButton.addActionListener(this);
		defaultReleaseButton.setVisible(false);
		buttons.add(defaultReleaseButton);
		doubleReleaseButton = new JButton("Double");
		doubleReleaseButton.addActionListener(this);
		doubleReleaseButton.setVisible(false);
		buttons.add(doubleReleaseButton);

	}

	/*
	 * Convenience method for resetting the text in the display area
	 */
	private void resetDisplay(String initialText) {
		display.setText(initialText);
	}

	/*
	 * Convenience method for adding text to the display area without
	 * overwriting what's already there
	 */
	private void appendDisplay(String newText) {
		display.setText(display.getText() + newText);
	}

	/*
	 * Convenience method for creating a set of positioning constraints for the
	 * specific layout we want for components of our GUI
	 */
	private GridBagConstraints positionConstraints(Position location, Integer margin) {

		GridBagConstraints constraints = new GridBagConstraints();
		switch (location) {
		case TOPCENTRE:
			constraints.anchor = GridBagConstraints.NORTH;
			constraints.insets = new Insets(margin, margin, 0, margin); // top,
																		// left,
																		// bottom,
																		// right
			constraints.gridwidth = GridBagConstraints.REMAINDER; // component
																	// occupies
																	// whole row
			break;
		case MIDDLECENTRE:
			constraints.anchor = GridBagConstraints.CENTER;
			constraints.insets = new Insets(margin, margin, margin, margin); // top,
																				// left,
																				// bottom,
																				// right
			constraints.gridwidth = GridBagConstraints.REMAINDER; // component
																	// occupies
																	// whole row
			constraints.weighty = 100; // give extra vertical space to this
										// object
			break;
		case BOTTOMCENTRE:
			constraints.anchor = GridBagConstraints.SOUTH;
			constraints.insets = new Insets(margin, margin, margin, margin); // top,
																				// left,
																				// bottom,
																				// right
			constraints.gridwidth = GridBagConstraints.REMAINDER; // component
																	// occupies
																	// whole row
			constraints.weighty = 100; // give extra vertical space to this
										// object
			break;
		case MIDDLELEFT:
			constraints.anchor = GridBagConstraints.WEST;
			constraints.insets = new Insets(0, margin, 0, margin); // top, left,
																	// bottom,
																	// right
			constraints.gridwidth = GridBagConstraints.REMAINDER; // component
																	// occupies
																	// whole row
			constraints.weightx = 100; // give extra horizontal space to this
										// object
			break;
		}
		return constraints;
	}

	/*
	 * Convenience method to add a labelled, editable text field to the main
	 * frame, with a fixed label and a mutable default text value
	 */
	private JTextField addParameterPanel(String label, Number defaultValue) {
		// A parameter panel has two components, a label and a text field
		JPanel parameterPanel = new JPanel();
		JLabel parameterLabel = new JLabel(label);
		JTextField parameterText = new JTextField("" + defaultValue, 3);
		// Add the label to the parameter panel
		parameterLabel.setHorizontalAlignment(JTextField.RIGHT); // flush right
		parameterPanel.add(parameterLabel);
		// Add the text field
		parameterText.setEditable(true);
		parameterText.setHorizontalAlignment(JTextField.RIGHT); // flush right
		parameterPanel.add(parameterText);
		// Add the parameter panel to the main frame
		this.add(parameterPanel, positionConstraints(Position.MIDDLELEFT, mainMargin));
		// Return the newly-created text field (but not the label, which never
		// changes)
		return parameterText;
	}

	/*
	 * Perform an appropriate action when a button is pushed
	 */
	public void actionPerformed(ActionEvent event) {

		// Get event's source
		Object source = event.getSource();

		// Consider the alternatives (not all are available at once)
		if (source == startButton) {
			startSimulation();
		} else if (source == halfReleaseButton) {
			simulateOneDay(Selection.HALF);
			checkForEndOfSimulation();
		} else if (source == defaultReleaseButton) {
			simulateOneDay(Selection.DEFAULT);
			checkForEndOfSimulation();
		} else if (source == doubleReleaseButton) {
			simulateOneDay(Selection.DOUBLE);
			checkForEndOfSimulation();
		}
		;
	}

	/*
	 * Start the simulation by accepting the initial parameters
	 */
	private void startSimulation() {
		try {
			// Get the initial parameters as set by the user
			Long randomSeed = Long.parseLong(seedText.getText().trim());
			damCapacity = Integer.parseInt(capacityText.getText().trim());
			maxDailyConsumption = Integer.parseInt(maxConsumptionText.getText().trim());
			maxDailyInflow = Integer.parseInt(maxInflowText.getText().trim());
			defaultReleaseAmount = Integer.parseInt(defaultReleaseText.getText().trim());
			jobDuration = Integer.parseInt(durationText.getText().trim());

			// Sanity check on user's parameters (not checked in
			// the WaterLog or DamActions classes)
			if (maxDailyConsumption < 0)
				throw new SimulationException("Maximum daily consumption must be non-negative, given " + maxDailyConsumption);
			if (maxDailyInflow < 0)
				throw new SimulationException("Maximum daily inflow must be non-negative, given " + maxDailyInflow);

			// Create a log to hold 8 days worth of dam levels
			log = new WaterLog(8, damCapacity);

			// Create the actions needed to drive the simulation
			actions = new DamActions(damCapacity, defaultReleaseAmount, jobDuration, log);

			// Prevent further changes to simulation parameters
			seedText.setEnabled(false);
			capacityText.setEnabled(false);
			maxInflowText.setEnabled(false);
			maxConsumptionText.setEnabled(false);
			durationText.setEnabled(false);
			defaultReleaseText.setEnabled(false);

			// Switch buttons from set-up to simulation mode
			buttons.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 1, true), "Water release controls", CENTER, BOTTOM));
			startButton.setVisible(false);
			halfReleaseButton.setVisible(true);
			defaultReleaseButton.setVisible(true);
			doubleReleaseButton.setVisible(true);

			// Set the lengths and initial values of the progress bars
			todaysLevel.setMaximum(damCapacity);
			yesterdaysLevel.setMaximum(damCapacity);
			lastWeeksLevel.setMaximum(damCapacity);
			displayWaterLevels();

			// Initialise the random number generator
			randomNumberGenerator = new Random(randomSeed);

			// Tell the user that the simulation has started successfully
			resetDisplay("Dam simulation started\n");

			// Display the initial state as recorded in the log
			appendDisplay("-----\nDay " + log.numEntries() + "; Water level = " + log.getEntry(0) + " ML");

		} catch (NumberFormatException exception) // User has entered an invalid
													// number
		{
			appendDisplay("Invalid number - " + exception.getMessage() + "\n");
		} catch (SimulationException exception) // Dam object/s could not be
												// constructed
		{
			appendDisplay(exception.getMessage() + "\n");
		} catch (Exception exception) // Something entirely unexpected has gone
										// wrong!
		{
			appendDisplay("Unhandled exception - " + exception.getMessage() + "\n");
			throw new RuntimeException(exception);
		}
	}

	/*
	 * Display a water level in a progress bar in such a way that the maximum
	 * level is described as "200%"
	 */
	private void displayLevel(JProgressBar levelDisplay, Integer waterLevel) {
		if (waterLevel == null) {
			levelDisplay.setString("");
			levelDisplay.setIndeterminate(true);
		} else {
			levelDisplay.setValue(waterLevel);
			levelDisplay.setString(Math.round((waterLevel * 200) / (float) damCapacity) + "%");
			levelDisplay.setIndeterminate(false);
		}
	}

	/*
	 * Display recent water levels as recorded in the log
	 */
	private void displayWaterLevels() throws SimulationException {
		// Set the progress bars
		displayLevel(todaysLevel, log.getEntry(0));
		displayLevel(yesterdaysLevel, log.getEntry(-1));
		displayLevel(lastWeeksLevel, log.getEntry(-7));
		// Display the water level variation
		Integer variation = log.variation();
		progressBars.setBorder(new TitledBorder(new LineBorder(new Color(0)), "Water levels (" + (variation >= 0 ? "+" : "") + variation + " ML)", CENTER, BOTTOM));
	}

	/*
	 * Simulate the change in water level occurring in a single day, based on
	 * the user's selected downriver water release, a randomly chosen inflow and
	 * a randomly chosen amount of water consumption
	 */
	private void simulateOneDay(Selection choiceMade) {
		// Display the choice of action
		switch (choiceMade) {
		case HALF:
			appendDisplay("; Half release selected\n");
			break;
		case DEFAULT:
			appendDisplay("; Default release selected\n");
			break;
		case DOUBLE:
			appendDisplay("; Double release selected\n");
			break;
		}
		// Create today's random inflow and consumption
		Integer todaysInflow = randomNumberGenerator.nextInt(maxDailyInflow + 1);
		Integer todaysConsumption = randomNumberGenerator.nextInt(maxDailyConsumption + 1);
		appendDisplay("Today's consumption = " + todaysConsumption + " ML\n" + "Today's inflow = " + todaysInflow + " ML\n");
		// Perform the chosen action
		try {
			switch (choiceMade) {
			case HALF:
				actions.halfRelease(todaysConsumption, todaysInflow);
				break;
			case DEFAULT:
				actions.defaultRelease(todaysConsumption, todaysInflow);
				break;
			case DOUBLE:
				actions.doubleRelease(todaysConsumption, todaysInflow);
				break;
			}
			// Display the outcome
			appendDisplay("-----\nDay " + log.numEntries() + "; Water level = " + log.getEntry(0) + " ML");
			displayWaterLevels();
		} catch (Exception exception) { // Something has gone wrong with the
										// student's solution
			appendDisplay("Unhandled exception - " + exception.getMessage() + "\n");
		}
		;
	}

	/*
	 * End the simulation if one of the termination conditions has been reached
	 * by telling the user what happened and disabling the buttons
	 */
	private void checkForEndOfSimulation() {
		try {
			if (actions.damOverflowed()) {
				appendDisplay("\nThe dam has overflowed - You're fired!\n" + "-----\nClose the window to end the simulation\n");
				halfReleaseButton.setEnabled(false);
				defaultReleaseButton.setEnabled(false);
				doubleReleaseButton.setEnabled(false);
			} else if (actions.levelTooLow()) {
				appendDisplay("\nWater level is below minimum operating threshold - You're fired!\n" + "-----\nClose the window to end the simulation\n");
				halfReleaseButton.setEnabled(false);
				defaultReleaseButton.setEnabled(false);
				doubleReleaseButton.setEnabled(false);
			} else if (actions.jobDone()) {
				appendDisplay("\nCongratulations on a job well done!\n" + "-----\nClose the window to end the simulation\n");
				halfReleaseButton.setEnabled(false);
				defaultReleaseButton.setEnabled(false);
				doubleReleaseButton.setEnabled(false);
			}
			;
		} catch (Exception exception) { // Something has gone wrong with the
										// student's solution
			appendDisplay("Unhandled exception - " + exception.getMessage() + "\n");
		}
	}

}
