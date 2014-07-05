package asgn1Solution;

import asgn1Question.SimulationException;

public class DamActions {

	private final static int MIN_DAM_CAPACITY = 100;//the dam capacity should be at least 100ML
	private final static int INIT_WATER_LEVEL_PARAM = 2;//the initial dam water level is half of dam capacity
	private final static int MIN_WATER_LEVEL_PARAM = 4;//the minimum dam water level is quarter of dam capacity

	private Integer damCapacity;
	private Integer defaultRelease;
	private Integer jobDuration;
	private WaterLog log;

	private Integer currentWaterLevel;
	private Integer currentDuration;
	private Integer minLevel;

	private boolean isOverflowed;
	private boolean isLevelTooLow;
	private boolean isJobDone;

	/**
	 * This DamActions constructor is used for initializing the main Dam system
	 * @param damCapacity - the maximum capacity of the dam, in megalitres
	 * @param defaultRelease - the default daily downriver release from the dam, in megalitres
	 * @param jobDuration - the duration of your fixed-term job as dam manager, in days
	 * @param log - a log for recording daily water levels, in megalitres
	 * @throws SimulationException - if the dam capacity is less than 100ML, if the job duration is not strictly positive (greater than zero), or if the default release is not strictly positive
	 */
	public DamActions(Integer damCapacity, Integer defaultRelease, Integer jobDuration, WaterLog log) throws SimulationException {
		// handle SimulationException
		if (log == null) {
			throw new SimulationException("waterlog is null");
		}
		
		if (damCapacity < MIN_DAM_CAPACITY) {
			throw new SimulationException("Dam capacity is less than 100ML");
		}

		if (jobDuration <= 0) {
			throw new SimulationException("Job duration is not positive");
		}

		if (defaultRelease <= 0) {
			throw new SimulationException("Default release is not positive");
		}
		//-----------------------------
		this.damCapacity = damCapacity;
		this.defaultRelease = defaultRelease;
		this.jobDuration = jobDuration;
		this.log = log;

		//initial the current water level , current work duration, the minimum operating level
		currentWaterLevel = damCapacity / INIT_WATER_LEVEL_PARAM;
		currentDuration = 0;
		minLevel = damCapacity / MIN_WATER_LEVEL_PARAM;
		
		//keep the current water Level down in log
		this.log.addEntry(currentWaterLevel);

		//initial the variables,keep down if the water is over flowed or water level is too low
		isOverflowed = false;
		isLevelTooLow = false;
		//keep down if the job is already done
		isJobDone = false;
	}

	private void defaultRelease(Integer release, Integer todaysConsumption, Integer todaysInflow) throws SimulationException {
		// handle SimulationException
		if (todaysConsumption < 0) {
			throw new SimulationException("todaysConsumption is negative");
		}
		
		if(todaysInflow < 0) {
			throw new SimulationException("todaysInflow is negative");
		}
		
		if("0".equals(log.numEntries())) {
			throw new SimulationException("water log is empty");
		}
		
		//-----------------------------
		//calculate the current water level
		int currentWaterLevel = calWaterRelease(release, todaysConsumption, todaysInflow);
		
		//log down the current water level
		log.addEntry(currentWaterLevel);

		//plus one day
		currentDuration++;
		//if current water level is less than minimum water level , we think that level is too Low
		isLevelTooLow = currentWaterLevel < minLevel ? true : false;
		//if current duration is more than job duration , our job has already down
		isJobDone = jobDuration <= currentDuration ? true : false;
	}

	private int calWaterRelease(Integer release, Integer todaysConsumption, Integer todaysInflow) throws SimulationException {
		int currentWaterLevel = log.getEntry(0);
		int waterLevel = currentWaterLevel + todaysInflow - todaysConsumption - release;

		if (waterLevel < 0) {
			//if waterLevel is less than 0 , we should keep it at 0
			currentWaterLevel = 0;
		} else if (waterLevel > damCapacity) {
			//if waterLevel is more than dam capacity , we should keep it at dam capacity
			// at the same time, we should keep the record down
			currentWaterLevel = damCapacity;
			isOverflowed = true;
		} else {
			currentWaterLevel = waterLevel;
		}
		return currentWaterLevel;
	}

	/**
	 * @see asgn1Question.Actions#defaultRelease(java.lang.Integer, java.lang.Integer)
	 */
	public void defaultRelease(Integer todaysConsumption, Integer todaysInflow) throws SimulationException {
		defaultRelease(defaultRelease, todaysConsumption, todaysInflow);
	}

	/**
	 * @see asgn1Question.Actions#halfRelease(java.lang.Integer, java.lang.Integer)
	 */
	public void halfRelease(Integer todaysConsumption, Integer todaysInflow) throws SimulationException {
		defaultRelease(defaultRelease / 2, todaysConsumption, todaysInflow);
	}

	/**
	 * @see asgn1Question.Actions#doubleRelease(java.lang.Integer, java.lang.Integer)
	 */
	public void doubleRelease(Integer todaysConsumption, Integer todaysInflow) throws SimulationException {
		defaultRelease(defaultRelease * 2, todaysConsumption, todaysInflow);
	}

	/**
	 * @see asgn1Question.Actions#damOverflowed()
	 */
	public boolean damOverflowed() {
		return isOverflowed;
	}

	/**
	 * @see asgn1Question.Actions#levelTooLow()
	 */
	public boolean levelTooLow() {
		return isLevelTooLow;
	}

	/**
	 * @see asgn1Question.Actions#jobDone()
	 */
	public boolean jobDone() {
		return isJobDone;
	}

}
