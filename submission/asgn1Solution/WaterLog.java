package asgn1Solution;

import java.util.LinkedList;
import asgn1Question.SimulationException;

public class WaterLog {
	/*
	 * damWaterLevel track down every record of water level
	 * the newest water level always insert in front of the link list
	 *| the newest | -> ...... -> | the oldest |
	*/
	private LinkedList<Integer> damWaterLevel = new LinkedList<Integer>();
	private Integer windowSize;
	private Integer maxEntry;

	/**
	 * This WaterLog constructor is used for initializing the log system
	 * @param windowSize - the (minimum) number of entries(Water Level) that the log must retain
	 * @param maxEntry - the largest value(Water Level) that will ever be entered in the log
	 * @throws SimulationException - if windowSize is not strictly positive (i.e., greater than zero) if <code>maxEntry</code> is negative
	 * (i.e., less than zero)
	 */
	public WaterLog(Integer windowSize, Integer maxEntry) throws SimulationException {
		// handle SimulationException
		if (windowSize <= 0) {
			throw new SimulationException("windowSize is not strictly positive.");
		}

		if (maxEntry < 0) {
			throw new SimulationException("maxEntry(Largest water level) is negative.");
		}
		//----------------------------
		this.maxEntry = maxEntry;
		this.windowSize = windowSize;
	}

	/**
	 * @see asgn1Question.Log#addEntry(java.lang.Integer)
	 */
	public void addEntry(Integer newEntry) throws SimulationException {
		// handle SimulationException
		if(newEntry < 0) {
			throw new SimulationException("The new entry(water level) is less than the 0.");
		}
		
		if(newEntry > maxEntry) {
			throw new SimulationException("The newEntry(water level) is larger than the max entry.");
		}
		//----------------------------
		damWaterLevel.addFirst(newEntry);
	}

	/**
	 * @see asgn1Question.Log#numEntries()
	 */
	public String numEntries() {
		return String.valueOf(damWaterLevel.size());
	}

	/**
	 * @see asgn1Question.Log#getEntry(java.lang.Integer)
	 */
	public Integer getEntry(Integer index) throws SimulationException {
		// handle SimulationException
		if (index > 0 || index < -windowSize + 1) {
			throw new SimulationException("Index is invalid");
		}
		//----------------------------
		int link_Index = -index;//convert from entry index to linked list index
		if(link_Index >= damWaterLevel.size()) {
			return null;
		}else{
			return damWaterLevel.get(link_Index);
		}
	}

	/**
	 * @see asgn1Question.Log#variation()
	 */
	public Integer variation() throws SimulationException {
		// handle SimulationException
		if (damWaterLevel.size() == 0) {
			throw new SimulationException("There are no entries in the log");
		}
		//----------------------------
		if (damWaterLevel.size() == 1) {
			return 0; //if water level are the same return 0 (include only one water level in the log)
		}
		// return the difference between the most recent and oldest water level in the log
		int oldestIndex = damWaterLevel.size() <= windowSize ? damWaterLevel.size() - 1 : windowSize - 1;
		return damWaterLevel.getFirst() - damWaterLevel.get(oldestIndex);
	}
}
