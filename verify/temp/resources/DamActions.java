package asgn1Solution;

import asgn1Question.Actions;
import asgn1Question.SimulationException;

/*
 * Non-functioning but compilable DamActions class. 
 */
public class DamActions implements Actions {
	
	public DamActions(Integer damCapacity, Integer defaultRelease, Integer jobDuration, WaterLog damLog) 
			throws SimulationException {		
	}


	@Override
	public boolean levelTooLow() throws SimulationException {
		return false;
	}

	@Override
	public boolean damOverflowed() {
		return false;
	}

	@Override
	public boolean jobDone() {
		return false;
	}

	@Override
	public void defaultRelease(Integer todaysConsumption, Integer todaysInflow)
			throws SimulationException {
	}

	@Override
	public void halfRelease(Integer todaysConsumption, Integer todaysInflow)
			throws SimulationException {
	}

	@Override
	public void doubleRelease(Integer todaysConsumption, Integer todaysInflow)
			throws SimulationException {
	}

}
