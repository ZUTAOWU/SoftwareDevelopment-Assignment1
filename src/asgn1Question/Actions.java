package asgn1Question;

/**
 * <p>
 * This interface defines the actions needed to control the dam
 * simulation.
 * </p><p>
 * The assumed scenario is that each morning, as temporary dam manager, you must
 * choose how much excess water <em>X</em> to release downriver from the dam.
 * There are three options, a default "normal" release, half of this amount,
 * or double this amount.  (For environmental reasons, there must always be
 * <em>some</em> water released downriver.)  Having made this choice, the amount of
 * water released cannot be changed again until the following morning.
 * </p><p>
 * Once you have chosen the daily downriver release <em>X</em>, the simulator
 * will then randomly choose the additional amount of water <em>Y</em> consumed that
 * day for domestic and agricultural purposes.  The total outflow from the dam during
 * the day is thus <em>X</em>&nbsp;+&nbsp;<em>Y</em>.
 * </p><p>
 * The simulator will also randomly choose a daily inflow <em>Z</em> to the dam,
 * consisting of water flowing in from the catchment region upstream and rainfall
 * directly entering the dam.  Therefore, if the water level in the dam at the
 * start of the day was <em>W</em>, then the level the following morning will be
 * <em>W</em>&nbsp;+&nbsp;<em>Z</em>&nbsp;&#8722;&nbsp;(<em>X</em>&nbsp;+&nbsp;<em>Y</em>).
 * </p><p>
 * Based on this daily calculation, the simulation may end in one of three ways:
 * <ul>
 * <li>The water level at the beginning of the day is below the minimal acceptable
 * threshold for operation of the dam's outlet towers, which is defined 
 * here to be one quarter of the dam's total capacity.  In this case you
 * have failed as dam manager by not keeping the dam full enough to function
 * properly.</li>
 * <li>The water level calculated for the beginning of the day exceeds the dam's
 * capacity, in which case the dam itself will be full to capacity and any
 * extra water will have spilled over into the valley below.  In this case you
 * have failed as dam manager by potentially endangering the public.</li>
 * <li>The current day exceeds the duration of your stint as temporary dam
 * manager.  In this case you have successfully completed your job.</li>
 * </ul>
 * </p><p>
 * Your third and fourth tasks in the assignment are to implement this
 * interface as class <code>DamActions</code> and produce a
 * <em>comprehensive</em> unit test suite for it.
 * </p><p>
 * <dl>
 * <dt><strong>Constructor:</strong></dt>
 * <dd> To connect to the provided GUI, your
 * <code>DamActions</code> class must provide a constructor with the following
 * signature, as well as all the methods below.  The constructor must
 * initialise the dam's log for the first day.  To start the simulation, we assume
 * that the initial water level in the dam is one half of its capacity, <em>rounded down</em>
 * if the capacity is an odd number, e.g., if the dam's capacity is 1,243ML then the
 * water level on the first day should be 621ML.  We also assume that the dam control
 * system is not intended for use with tiny amounts of water, and therefore require
 * the dam capacity to be at least 100ML.  (NB: The water level meters in the
 * simulator show the level as a percentage.  Half of the dam's capacity is
 * considered to be its ideal capacity for meeting domestic consumption and
 * agricultural needs and is thus shown as "100%".  One quarter of the dam's
 * capacity, which is its minimum operating level, is "50%".  The dam's actual
 * physical capacity, beyond which it will overflow uncontrollably, is displayed
 * as "200%".) </dd>
 * <dt><strong>Parameters:</strong></dt>
 * <dd><code>Integer damCapacity</code> - the maximum capacity of the dam, in megalitres<br>
 * <dd><code>Integer defaultRelease</code> - the default daily downriver release from the dam, in megalitres<br>
  * <code>Integer jobDuration</code> - the duration of your fixed-term job as
 * dam manager, in days<br>
 * <code>WaterLog damLog</code> - a log for recording daily water levels, in megalitres
 * </dd>
 * <dt><strong>Throws:</strong></dt>
 * <dd><code>SimulationException</code> if the dam capacity is less than 100ML, if the job
 * duration is not strictly positive (greater than zero), or if the default release is not strictly positive</dd>
 * </dl>
 * </p>
 * 
 * @author INB370
 * @version 1.1
 *
 */
public interface Actions {

	/**
	 * Returns true if the dam's current water level is less than its minimum operating
	 * threshold.  This is defined to be one quarter of the dam's capacity,
	 * <em>rounded down</em> if the capacity is not a whole multiple of four, e.g.,
	 * if the dam's capacity is 1,243ML then the minimum acceptable water level
	 * is 310ML.
	 * 
	 * @return <code>true</code> if the log says that the current water level is too low, 
	 * <code>false</code> otherwise
	 * @throws SimulationException if there are no entries in the log
	 */
	public boolean levelTooLow() throws SimulationException;
	
	/**
	 * Returns true if the dam's calculated water level in the past day exceeded the dam's
	 * capacity, meaning that the dam has overflowed.  (NB: Of course, the water level in
	 * the dam cannot actually exceed its physical capacity, and the log will not allow
	 * you to enter a water level greater than the dam's capacity.  Therefore, you will
	 * need to implement some other way of "remembering" that the dam overflowed.)
	 * 
	 * @return <code>true</code> if the previous day's dam level, plus the day's inflow,
	 * minus the day's total outflow, exceeded the dam's capacity, <code>false</code> otherwise
	 */
	public boolean damOverflowed();

	/**
	 * Returns true if the dam simulation has ended because the number of
	 * days originally allocated for your temporary job as dam manager
	 * has been exceeded.  (Hint: You can find out what day it
	 * is by looking in the log.)
	 * 
	 * @return <code>true</code> if the current day is greater than the initial
	 * duration for the job, <code>false</code> otherwise
	 */
	public boolean jobDone();
	
	/**
	 * <p>
	 * Simulate a day's activity in the dam when the manager presses the
	 * "Default release" button.  This consists of
	 * (1)&nbsp;calculating a new water level for the dam, and 
	 * (2)&nbsp;recording it in the daily water level log.  
     * Under normal circumstances the new water level is the previous
	 * one plus the given inflow, minus the given consumption and the chosen
	 * downriver release amount, which in this case is the default release.
	 * However, there can't be more water in the dam than its capacity,
	 * nor can there be less than zero litres of water in the dam, so the
	 * calculated water level can never be allowed to go outside this range.
	 * </p><p>
	 * (Observation: For simplicity we ignore the possibility of
	 * transient water level overflows or underflows lasting less than one day.
	 * For instance, if the dam's minimum acceptable water level is 1,000ML,
	 * its current water level is 1,100ML, the day's total outflow is 200ML
	 * and its total inflow is 250ML, then we will calculate the new water
	 * level to be an acceptable 1,150ML.  However, this ignores the possibility that all
	 * of the day's outflow occurs first, lowering the water level to an unacceptable
	 * 900ML, before any inflow occurs.  Our day-by-day simulation is thus too coarse
	 * to recognise this temporary violation of the allowed water level, although an
	 * hourly simulation may do so.  This highlights one of the challenges
	 * encountered when modelling and simulating complex real-world
	 * systems: simulation results are rarely an entirely precise reflection of
	 * real-world behaviour due to the impossibility of modelling physical
	 * systems in sufficient detail.)
	 * </p>
	 * @param todaysConsumption the amount of water required for domestic use
	 * and irrigation, in megalitres 
	 * @param todaysInflow the amount of water entering the dam from upstream
	 * and through rainfall, in megalitres
	 * @throws SimulationException if either the given consumption or inflow
	 * are negative, or if the log is empty (which makes it impossible to calculate
	 * the new level)
	 */
	public void defaultRelease(Integer todaysConsumption, Integer todaysInflow)
	throws SimulationException;

	/**
	 * Simulate a day's activity in the dam when the manager presses the
	 * "Half release" button.  It does the same thing as the
	 * <code>normalRlease</code> method except that only half the default
	 * downriver release (rounded down) is applied.
	 * 
	 * @param todaysConsumption the amount of water required for domestic use
	 * and irrigation, in megalitres 
	 * @param todaysInflow the amount of water entering the dam from upstream
	 * and through rainfall, in megalitres
	 * @throws SimulationException if either the given consumption or inflow
	 * are negative, or if the log is empty
	 */
	public void halfRelease(Integer todaysConsumption, Integer todaysInflow)
	throws SimulationException;

	/**
	 * Simulate a day's activity in the dam when the manager presses the
	 * "Double release" button.  It does the same thing as the
	 * <code>normalRelease</code> method except that double the default
	 * downriver release is applied.
	 * 
	 * @param todaysConsumption the amount of water required for domestic use
	 * and irrigation, in megalitres 
	 * @param todaysInflow the amount of water entering the dam from upstream
	 * and through rainfall, in megalitres
	 * @throws SimulationException if either the given consumption or inflow
	 * are negative, or if the log is empty
	 */
	public void doubleRelease(Integer todaysConsumption, Integer todaysInflow)
	throws SimulationException;
	
}
