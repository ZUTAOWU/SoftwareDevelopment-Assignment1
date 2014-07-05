package asgn1Solution;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn1Question.SimulationException;

public class ActionsTests {
	private WaterLog log;
	private DamActions action;
	private Integer damCapacity;
	private Integer defaultRelease;
	private Integer jobDuration;
	private Integer windownSize;
	private Integer maxEntry;
	
	/**
	 * Initial data and create WaterLog and DamActions instance for test
	 * @throws SimulationException
	 */
	@Before
	public void init() throws SimulationException{
		damCapacity = 100;
		defaultRelease = 10;
		jobDuration = 7;
		windownSize = 7;
		maxEntry = 100;
		log = new WaterLog(windownSize, maxEntry);
		action = new DamActions(damCapacity, defaultRelease, jobDuration, log);
	}

	// help test the defaultRelease, halfRelease, DoubleRelease method in DamActions
	// calculate the new water level afer the daily release
	private Integer testWaterLevel(Integer damCapacity, Integer currentLevel, Integer release, Integer dailyConsumption, Integer dailyInflow) {
		currentLevel = currentLevel + dailyInflow - dailyConsumption - release;
		// if current level is bigger than dam capacity, then current level remain at dam cpacity
		currentLevel = currentLevel > damCapacity ? damCapacity : currentLevel;
		// if current level is less than 0, then current level remain at 0
		currentLevel = currentLevel < 0 ? 0 : currentLevel;
		return currentLevel;
	}

	// ------------------------------------------
	// test Constructor
	/**
	 * Test method : {@link asgn1Solution.DamActions#DamActions(java.lang.Integer , java.lang.Integer , java.lang.Integer , WaterLog )}.
	 * </br></br>
	 * Test if waterlog is null, then throw SimulationException
	 * @throws Exception
	 */
	@Test(expected = asgn1Question.SimulationException.class)
	public void testDamActions_1() throws Exception {
		Integer damCapacity = new Integer(1);
		Integer defaultRelease = new Integer(1);
		Integer jobDuration = new Integer(1);
		WaterLog log = null;
		DamActions result = new DamActions(damCapacity, defaultRelease, jobDuration, log);
		assertNotNull(result);
	}
	
	/**
	 * Test method : {@link asgn1Solution.DamActions#DamActions(java.lang.Integer , java.lang.Integer , java.lang.Integer , WaterLog )}.
	 * </br></br>
	 * Test if damCapacity is less than 100ML, then throw SimulationException
	 * @throws Exception
	 */
	@Test(expected = SimulationException.class)
	public void Constructor_Exception_damCapacity_Test() throws SimulationException {
		// test damCapacity is less than 100
		int damCapacity = 1;
		int defaultRelease = 10;
		int jobDuration = 14;
		WaterLog log = new WaterLog(10, 10);
		@SuppressWarnings("unused")
		DamActions action = new DamActions(damCapacity, defaultRelease, jobDuration, log);
	}

	/**
	 * Test method : {@link asgn1Solution.DamActions#DamActions(java.lang.Integer , java.lang.Integer , java.lang.Integer , WaterLog )}.
	 * </br></br>
	 * Test if defaultRelease is 0, then throw SimulationException
	 * @throws Exception
	 */
	@Test(expected = SimulationException.class)
	public void Constructor_Exception_defaultRelease_Test() throws SimulationException {
		// test defaultRelease is 0
		int damCapacity = 100;
		int defaultRelease = 0;
		int jobDuration = 14;
		WaterLog log = new WaterLog(10, 10);
		@SuppressWarnings("unused")
		DamActions action = new DamActions(damCapacity, defaultRelease, jobDuration, log);
	}
	
	/**
	 * Test method : {@link asgn1Solution.DamActions#DamActions(java.lang.Integer , java.lang.Integer , java.lang.Integer , WaterLog )}.
	 * </br></br>
	 * Test if defaultRelease is less than 0, then throw SimulationException
	 * @throws Exception
	 */
	@Test(expected = SimulationException.class)
	public void Constructor_Exception_defaultRelease_Test2() throws SimulationException {
		// test defaultRelease is less than 0
		int damCapacity = 100;
		int defaultRelease = -1;
		int jobDuration = 14;
		WaterLog log = new WaterLog(10, 10);
		@SuppressWarnings("unused")
		DamActions action = new DamActions(damCapacity, defaultRelease, jobDuration, log);
	}

	/**
	 * Test method : {@link asgn1Solution.DamActions#DamActions(java.lang.Integer , java.lang.Integer , java.lang.Integer , WaterLog )}.
	 * </br></br>
	 * Test if jobDuration is 0, then throw SimulationException
	 * @throws Exception
	 */
	@Test(expected = SimulationException.class)
	public void Constructor_Exception_jobDuration_Test() throws SimulationException {
		// test jobDuration is 0 or less than 0
		int damCapacity = 100;
		int defaultRelease = 10;
		int jobDuration = 0;
		WaterLog log = new WaterLog(10, 10);
		@SuppressWarnings("unused")
		DamActions action = new DamActions(damCapacity, defaultRelease, jobDuration, log);
	}
	
	/**
	 * Test method : {@link asgn1Solution.DamActions#DamActions(java.lang.Integer , java.lang.Integer , java.lang.Integer , WaterLog )}.
	 * </br></br>
	 * Test if jobDuration is less than 0, then throw SimulationException
	 * @throws Exception
	 */
	@Test(expected = SimulationException.class)
	public void Constructor_Exception_jobDuration_Test2() throws SimulationException {
		// test jobDuration is 0 or less than 0
		int damCapacity = 100;
		int defaultRelease = 10;
		int jobDuration = -1;
		WaterLog log = new WaterLog(10, 10);
		@SuppressWarnings("unused")
		DamActions action = new DamActions(damCapacity, defaultRelease, jobDuration, log);
	}
	// ------------------------------------------
	// test public void defaultRelease(Integer todaysConsumption, Integer todaysInflow)
	/**
	 * Test method : {@link asgn1Solution.DamActions#defaultRelease(java.lang.Integer , java.lang.Integer)}.
	 * </br></br>
	 * Test if todaysConsumption is less than 0, then throw SimulationException
	 * @throws Exception
	 */
	@Test(expected = SimulationException.class)
	public void defaultRelease_Exception_Test1() throws SimulationException {
		// test todaysConsumption is less than 0
		action.defaultRelease(-1, 10);
	}

	/**
	 * Test method : {@link asgn1Solution.DamActions#defaultRelease(java.lang.Integer , java.lang.Integer)}.
	 * </br></br>
	 * Test if todaysInflow is less than 0,then throw SimulationException
	 * @throws Exception
	 */
	@Test(expected = SimulationException.class)
	public void defaultRelease_Exception_Test2() throws SimulationException {
		// test todaysInflow is less than 0
		action.defaultRelease(10, -1);
	}

	
	/**
	 * Test method : {@link asgn1Solution.DamActions#defaultRelease(java.lang.Integer, java.lang.Integer)}.
	 * </br></br>
	 * Test after defaultRelease, if the current water level is correct
	 * @throws Exception
	 */
	@Test
	public void defaultRelease_Test() throws SimulationException {
		// test after default release, if the result is correct
		Integer todaysConsumption = 10;
		Integer todaysInflow = 10;
		action.defaultRelease(todaysConsumption, todaysInflow);

		Integer currentLv = damCapacity / 2;
		assertEquals(testWaterLevel(damCapacity, currentLv, defaultRelease, todaysConsumption, todaysInflow), log.getEntry(0));

		todaysConsumption = 30;
		todaysInflow = 10;
		currentLv = log.getEntry(0);
		action.defaultRelease(todaysConsumption, todaysInflow);
		assertEquals(testWaterLevel(damCapacity, currentLv, defaultRelease, todaysConsumption, todaysInflow), log.getEntry(0));

		todaysConsumption = 30;
		todaysInflow = 120;
		currentLv = log.getEntry(0);
		action.defaultRelease(todaysConsumption, todaysInflow);
		assertEquals(testWaterLevel(damCapacity, currentLv, defaultRelease, todaysConsumption, todaysInflow), log.getEntry(0));

		todaysConsumption = 120;
		todaysInflow = 10;
		currentLv = log.getEntry(0);
		action.defaultRelease(todaysConsumption, todaysInflow);
		assertEquals(testWaterLevel(damCapacity, currentLv, defaultRelease, todaysConsumption, todaysInflow), log.getEntry(0));
	}
	
	/**
	 * Test method : {@link asgn1Solution.DamActions#defaultRelease(java.lang.Integer , java.lang.Integer)}.
	 * </br></br>
	 * Test after defaultRelease, if the current water is more than damCapacity, the water level should still remain at dam capacity
	 * @throws Exception
	 */
	@Test
	public void defaultRelease_Test1() throws SimulationException {
		// test after the water level exceed the dam capacity
		Integer todaysConsumption = 10;
		Integer todaysInflow = 50;
		action.defaultRelease(todaysConsumption, todaysInflow);

		action.defaultRelease(todaysConsumption, todaysInflow);
		assertEquals(damCapacity, log.getEntry(0));
		action.defaultRelease(todaysConsumption, todaysInflow);
		assertEquals(damCapacity, log.getEntry(0));
		action.defaultRelease(todaysConsumption, todaysInflow);
		assertEquals(damCapacity, log.getEntry(0));
		action.defaultRelease(todaysConsumption, todaysInflow);
		assertEquals(damCapacity, log.getEntry(0));
		action.defaultRelease(todaysConsumption, todaysInflow);
		assertEquals(damCapacity, log.getEntry(0));
	}
	
	/**
	 * Test method : {@link asgn1Solution.DamActions#defaultRelease(java.lang.Integer , java.lang.Integer)}.
	 * </br></br>
	 * Test after defaultRelease, if the current water is less than 0, the water level should still remain at 0
	 * @throws Exception
	 */
	@Test
	public void defaultRelease_Test2() throws SimulationException {
		// test after the water level below 0
		Integer todaysConsumption = 50;
		Integer todaysInflow = 10;
		action.defaultRelease(todaysConsumption, todaysInflow);

		action.defaultRelease(todaysConsumption, todaysInflow);
		assertEquals(Integer.valueOf(0), log.getEntry(0));
		action.defaultRelease(todaysConsumption, todaysInflow);
		assertEquals(Integer.valueOf(0), log.getEntry(0));
		action.defaultRelease(todaysConsumption, todaysInflow);
		assertEquals(Integer.valueOf(0), log.getEntry(0));
		action.defaultRelease(todaysConsumption, todaysInflow);
		assertEquals(Integer.valueOf(0), log.getEntry(0));
		action.defaultRelease(todaysConsumption, todaysInflow);
		assertEquals(Integer.valueOf(0), log.getEntry(0));
	}

	// ------------------------------------------
	// test public void halfRelease(Integer todaysConsumption, Integer todaysInflow)
	/**
	 * Test method : {@link asgn1Solution.DamActions#halfRelease(java.lang.Integer , java.lang.Integer)}.
	 * </br></br>
	 * Test if todaysConsumption is less than 0 throw SimulationException
	 * @throws Exception
	 */
	@Test(expected = SimulationException.class)
	public void halfRelease_Exception_Test1() throws SimulationException {
		// test todaysConsumption is less than 0
		action.halfRelease(-1, 10);
	}

	/**
	 * Test method : {@link asgn1Solution.DamActions#halfRelease(java.lang.Integer , java.lang.Integer)}.
	 * </br></br>
	 * Test if todaysInflow is less than 0 throw SimulationException
	 * @throws Exception
	 */
	@Test(expected = SimulationException.class)
	public void halfRelease_Exception_Test2() throws SimulationException {
		// test todaysInflow is less than 0
		DamActions action = new DamActions(damCapacity, defaultRelease, jobDuration, log);
		action.halfRelease(10, -1);
	}

	/**
	 * Test method : {@link asgn1Solution.DamActions#halfRelease(java.lang.Integer , java.lang.Integer)}.
	 * </br></br>
	 * Test after half release, if current water level is correct
	 * @throws Exception
	 */
	@Test
	public void halfRelease_Test() throws SimulationException {
		// test after half release, if the result is correct
		Integer todaysConsumption = 10;
		Integer todaysInflow = 10;
		action.halfRelease(todaysConsumption, todaysInflow);

		Integer currentLv = damCapacity / 2;
		assertEquals(testWaterLevel(damCapacity, currentLv, defaultRelease / 2, todaysConsumption, todaysInflow), log.getEntry(0));

		todaysConsumption = 30;
		todaysInflow = 10;
		currentLv = log.getEntry(0);
		action.halfRelease(todaysConsumption, todaysInflow);
		assertEquals(testWaterLevel(damCapacity, currentLv, defaultRelease / 2, todaysConsumption, todaysInflow), log.getEntry(0));

		todaysConsumption = 30;
		todaysInflow = 120;
		currentLv = log.getEntry(0);
		action.halfRelease(todaysConsumption, todaysInflow);
		assertEquals(testWaterLevel(damCapacity, currentLv, defaultRelease / 2, todaysConsumption, todaysInflow), log.getEntry(0));

		todaysConsumption = 120;
		todaysInflow = 10;
		currentLv = log.getEntry(0);
		action.halfRelease(todaysConsumption, todaysInflow);
		assertEquals(testWaterLevel(damCapacity, currentLv, defaultRelease / 2, todaysConsumption, todaysInflow), log.getEntry(0));
	}
	
	/**
	 * Test method : {@link asgn1Solution.DamActions#halfRelease(java.lang.Integer , java.lang.Integer)}.
	 * </br></br>
	 * Test after halfRelease, if the current water is more than damCapacity, the water level should still remain at dam capacity
	 * @throws Exception
	 */
	@Test
	public void halfRelease_Test1() throws SimulationException {
		// test after the water level exceed the dam capacity
		Integer todaysConsumption = 10;
		Integer todaysInflow = 50;

		action.halfRelease(todaysConsumption, todaysInflow);
		assertEquals(Integer.valueOf(85), log.getEntry(0));
		action.halfRelease(todaysConsumption, todaysInflow);
		assertEquals(damCapacity, log.getEntry(0));
		action.halfRelease(todaysConsumption, todaysInflow);
		assertEquals(damCapacity, log.getEntry(0));
		action.halfRelease(todaysConsumption, todaysInflow);
		assertEquals(damCapacity, log.getEntry(0));
		action.halfRelease(todaysConsumption, todaysInflow);
		assertEquals(damCapacity, log.getEntry(0));
	}
	
	/**
	 * Test method : {@link asgn1Solution.DamActions#halfRelease(java.lang.Integer , java.lang.Integer)}.
	 * </br></br>
	 * Test after halfRelease, if the current water is less than 0, the water level should still remain at 0
	 * @throws Exception
	 */
	@Test
	public void halfRelease_Test2() throws SimulationException {
		// test after the water level is less than 0
		Integer todaysConsumption = 50;
		Integer todaysInflow = 10;

		action.halfRelease(todaysConsumption, todaysInflow);
		assertEquals(Integer.valueOf(5), log.getEntry(0));
		action.halfRelease(todaysConsumption, todaysInflow);
		assertEquals(Integer.valueOf(0), log.getEntry(0));
		action.halfRelease(todaysConsumption, todaysInflow);
		assertEquals(Integer.valueOf(0), log.getEntry(0));
		action.halfRelease(todaysConsumption, todaysInflow);
		assertEquals(Integer.valueOf(0), log.getEntry(0));
		action.halfRelease(todaysConsumption, todaysInflow);
		assertEquals(Integer.valueOf(0), log.getEntry(0));
	}
	
	// ------------------------------------------
	// test public void doubleRelease(Integer todaysConsumption, Integer todaysInflow)
	/**
	 * Test method : {@link asgn1Solution.DamActions#doubleRelease(java.lang.Integer , java.lang.Integer)}.
	 * </br></br>
	 * Test if todaysConsumption is less than 0 throw SimulationException
	 * @throws Exception
	 */
	@Test(expected = SimulationException.class)
	public void doubleRelease_Exception_Test1() throws SimulationException {
		// test todaysConsumption is less than 0
		action.doubleRelease(-1, 10);
	}

	/**
	 * Test method : {@link asgn1Solution.DamActions#doubleRelease(java.lang.Integer , java.lang.Integer)}.
	 * </br></br>
	 * Test if todaysInflow is less than 0 throw SimulationException
	 * @throws Exception
	 */
	@Test(expected = SimulationException.class)
	public void doubleRelease_Exception_Test2() throws SimulationException {
		// test todaysInflow is less than 0
		DamActions action = new DamActions(damCapacity, defaultRelease, jobDuration, log);
		action.doubleRelease(10, -1);
	}


	/**
	 * Test method : {@link asgn1Solution.DamActions#doubleRelease(java.lang.Integer, java.lang.Integer)}.
	 * </br></br>
	 * Test after doubleRelease, if the current water level is correct
	 * @throws Exception
	 */
	@Test
	public void doubleRelease_Test() throws SimulationException {
		// test after double release, if the result is correct
		Integer todaysConsumption = 10;
		Integer todaysInflow = 10;
		action.doubleRelease(todaysConsumption, todaysInflow);

		Integer currentLv = damCapacity / 2;
		assertEquals(testWaterLevel(damCapacity, currentLv, defaultRelease * 2, todaysConsumption, todaysInflow), log.getEntry(0));

		todaysConsumption = 30;
		todaysInflow = 10;
		currentLv = log.getEntry(0);
		action.doubleRelease(todaysConsumption, todaysInflow);
		assertEquals(testWaterLevel(damCapacity, currentLv, defaultRelease * 2, todaysConsumption, todaysInflow), log.getEntry(0));

		todaysConsumption = 30;
		todaysInflow = 120;
		currentLv = log.getEntry(0);
		action.doubleRelease(todaysConsumption, todaysInflow);
		assertEquals(testWaterLevel(damCapacity, currentLv, defaultRelease * 2, todaysConsumption, todaysInflow), log.getEntry(0));

		todaysConsumption = 120;
		todaysInflow = 10;
		currentLv = log.getEntry(0);
		action.doubleRelease(todaysConsumption, todaysInflow);
		assertEquals(testWaterLevel(damCapacity, currentLv, defaultRelease * 2, todaysConsumption, todaysInflow), log.getEntry(0));
	}
	
	/**
	 * Test method : {@link asgn1Solution.DamActions#doubleRelease(java.lang.Integer , java.lang.Integer)}.
	 * </br></br>
	 * Test after doubleRelease, if the current water is more than damCapacity, the water level should still remain at dam capacity
	 * @throws Exception
	 */
	@Test
	public void doubleRelease_Test1() throws SimulationException {
		// test after the water level exceed the dam capacity
		Integer todaysConsumption = 10;
		Integer todaysInflow = 50;

		action.doubleRelease(todaysConsumption, todaysInflow);
		assertEquals(Integer.valueOf(70), log.getEntry(0));
		action.doubleRelease(todaysConsumption, todaysInflow);
		assertEquals(Integer.valueOf(90), log.getEntry(0));
		action.doubleRelease(todaysConsumption, todaysInflow);
		assertEquals(damCapacity, log.getEntry(0));
		action.doubleRelease(todaysConsumption, todaysInflow);
		assertEquals(damCapacity, log.getEntry(0));
		action.doubleRelease(todaysConsumption, todaysInflow);
		assertEquals(damCapacity, log.getEntry(0));
	}
	
	
	/**
	 * Test method : {@link asgn1Solution.DamActions#doubleRelease(java.lang.Integer , java.lang.Integer)}.
	 * </br></br>
	 * Test after doubleRelease, if the current water is less than 0, the water level should still remain at 0
	 * @throws Exception
	 */
	@Test
	public void doubleRelease_Test2() throws SimulationException {
		// test after the water level below 0
		Integer todaysConsumption = 50;
		Integer todaysInflow = 10;

		action.doubleRelease(todaysConsumption, todaysInflow);
		assertEquals(Integer.valueOf(0), log.getEntry(0));
		action.doubleRelease(todaysConsumption, todaysInflow);
		assertEquals(Integer.valueOf(0), log.getEntry(0));
		action.doubleRelease(todaysConsumption, todaysInflow);
		assertEquals(Integer.valueOf(0), log.getEntry(0));
		action.doubleRelease(todaysConsumption, todaysInflow);
		assertEquals(Integer.valueOf(0), log.getEntry(0));
		action.doubleRelease(todaysConsumption, todaysInflow);
		assertEquals(Integer.valueOf(0), log.getEntry(0));
	}
	
	// ------------------------------------------
	// test public boolean damOverflowed()
	/**
	 * Test method : {@link asgn1Solution.DamActions#damOverflowed()}.
	 * </br></br>
	 * Test after default release, the dam is overflowed
	 * @throws Exception
	 */
	@Test
	public void damOverflowed_Test1() throws SimulationException {
		Integer todaysConsumption = 10;
		Integer todaysInflow = 90;
		action.defaultRelease(todaysConsumption, todaysInflow);
		Integer currentLv = damCapacity / 2;
		assertEquals(testWaterLevel(damCapacity, currentLv, defaultRelease, todaysConsumption, todaysInflow), log.getEntry(0));
		assertTrue(action.damOverflowed());
	}
	
	/**
	 * Test method : {@link asgn1Solution.DamActions#damOverflowed()}.
	 * </br></br>
	 * Test after default release, the dam is not overflowed
	 * @throws Exception
	 */
	@Test
	public void damOverflowed_Test2() throws SimulationException {
		Integer todaysConsumption = 10;
		Integer todaysInflow = 50;
		Integer currentLv = damCapacity / 2;
		action.defaultRelease(todaysConsumption, todaysInflow);
		assertEquals(testWaterLevel(damCapacity, currentLv, defaultRelease, todaysConsumption, todaysInflow), log.getEntry(0));
		assertFalse(action.damOverflowed());
	}
	
	/**
	 * Test method : {@link asgn1Solution.DamActions#damOverflowed()}.
	 * </br></br>
	 * Test after halfRelease, the dam is overflowed
	 * @throws Exception
	 */
	@Test
	public void damOverflowed_Test3() throws SimulationException {
		Integer todaysConsumption = 10;
		Integer todaysInflow = 90;
		action.halfRelease(todaysConsumption, todaysInflow);
		Integer currentLv = damCapacity / 2;
		assertEquals(testWaterLevel(damCapacity, currentLv, defaultRelease / 2, todaysConsumption, todaysInflow), log.getEntry(0));
		assertTrue(action.damOverflowed());
	}
	
	/**
	 * Test method : {@link asgn1Solution.DamActions#damOverflowed()}.
	 * </br></br>
	 * Test after halfRelease, the dam is not overflowed
	 * @throws Exception
	 */
	@Test
	public void damOverflowed_Test4() throws SimulationException {
		Integer todaysConsumption = 10;
		Integer todaysInflow = 50;
		Integer currentLv = damCapacity / 2;
		action.halfRelease(todaysConsumption, todaysInflow);
		assertEquals(testWaterLevel(damCapacity, currentLv, defaultRelease / 2, todaysConsumption, todaysInflow), log.getEntry(0));
		assertFalse(action.damOverflowed());
	}
	
	/**
	 * Test method : {@link asgn1Solution.DamActions#damOverflowed()}.
	 * </br></br>
	 * Test after doubleRelease, the dam is overflowed
	 * @throws Exception
	 */
	@Test
	public void damOverflowed_Test5() throws SimulationException {
		Integer todaysConsumption = 10;
		Integer todaysInflow = 90;
		action.doubleRelease(todaysConsumption, todaysInflow);
		Integer currentLv = damCapacity / 2;
		assertEquals(testWaterLevel(damCapacity, currentLv, defaultRelease * 2, todaysConsumption, todaysInflow), log.getEntry(0));
		assertTrue(action.damOverflowed());
	}
	
	/**
	 * Test method : {@link asgn1Solution.DamActions#damOverflowed()}.
	 * </br></br>
	 * Test after doubleRelease, the dam is not overflowed
	 * @throws Exception
	 */
	@Test
	public void damOverflowed_Test6() throws SimulationException {
		Integer todaysConsumption = 10;
		Integer todaysInflow = 50;
		Integer currentLv = damCapacity / 2;
		action.doubleRelease(todaysConsumption, todaysInflow);
		assertEquals(testWaterLevel(damCapacity, currentLv, defaultRelease * 2, todaysConsumption, todaysInflow), log.getEntry(0));
		assertFalse(action.damOverflowed());
	}
	
	/**
	 * Test method : {@link asgn1Solution.DamActions#damOverflowed()}.
	 * </br></br>
	 * Test after some release, the dam is overflowed
	 * @throws Exception
	 */
	@Test
	public void damOverflowed_Test7() throws SimulationException {
		Integer todaysConsumption = 5;
		Integer todaysInflow = 25;
		action.defaultRelease(todaysConsumption, todaysInflow);
		assertFalse(action.damOverflowed());
		action.defaultRelease(todaysConsumption, todaysInflow);
		assertFalse(action.damOverflowed());
		action.defaultRelease(todaysConsumption, todaysInflow);
		assertFalse(action.damOverflowed());
		action.defaultRelease(todaysConsumption, todaysInflow);
		assertFalse(action.damOverflowed());
		action.defaultRelease(todaysConsumption, todaysInflow);
		assertFalse(action.damOverflowed());
		action.defaultRelease(todaysConsumption, todaysInflow);
		assertTrue(action.damOverflowed());
	}
	
	/**
	 * Test method : {@link asgn1Solution.DamActions#damOverflowed()}.
	 * </br></br>
	 * Test if the dam's calculated water level in the past day exceeded the dam's capacity, the dam has overflowed
	 * no matter what happened later
	 * @throws Exception
	 */
	@Test
	public void damOverflowed_Test8() throws SimulationException {
		// test if in the past few days the water level exceeded the dam's capacity
		Integer todaysConsumption = 5;
		Integer todaysInflow = 25;
		action.defaultRelease(todaysConsumption, todaysInflow);
		assertFalse(action.damOverflowed());
		action.defaultRelease(todaysConsumption, todaysInflow);
		assertFalse(action.damOverflowed());
		action.defaultRelease(todaysConsumption, todaysInflow);
		assertFalse(action.damOverflowed());
		action.defaultRelease(todaysConsumption, todaysInflow);
		assertFalse(action.damOverflowed());
		action.defaultRelease(todaysConsumption, todaysInflow);
		assertFalse(action.damOverflowed());
		action.defaultRelease(todaysConsumption, todaysInflow);
		assertTrue(action.damOverflowed());
		
		todaysConsumption = 50;
		todaysInflow = 0;
		action.defaultRelease(todaysConsumption, todaysInflow);
		assertTrue(action.damOverflowed());
		
		todaysConsumption = 50;
		todaysInflow = 0;
		action.defaultRelease(todaysConsumption, todaysInflow);
		assertTrue(action.damOverflowed());
		
	}
	
	// ------------------------------------------
	// test public boolean levelTooLow()
	/**
	 * Test method : {@link asgn1Solution.DamActions#levelTooLow()}.
	 * </br></br>
	 * Test after some releases, if the water level is less than one quarter of the dam's capacity, the water level is too low
	 * @throws Exception
	 */
	@Test
	public void levelTooLow_Test1() throws SimulationException {
		Integer todaysConsumption = 10;
		Integer todaysInflow = 10;
		action.doubleRelease(todaysConsumption, todaysInflow);
		assertFalse(action.levelTooLow());
		action.doubleRelease(todaysConsumption, todaysInflow);
		assertTrue(action.levelTooLow());
		action.doubleRelease(todaysConsumption, todaysInflow);
		assertTrue(action.levelTooLow());
		action.doubleRelease(todaysConsumption, todaysInflow);
		assertTrue(action.levelTooLow());
	}
	
	/**
	 * Test method : {@link asgn1Solution.DamActions#levelTooLow()}.
	 * </br></br>
	 * Test after some releases, if the water level is less than one quarter of the dam's capacity, the water level is too low.
	 * Then water level is become normal level again. 
	 * which is different from damOverflowed
	 * @throws Exception
	 */
	@Test
	public void levelTooLow_Test2() throws SimulationException {
		Integer todaysConsumption = 10;
		Integer todaysInflow = 10;
		action.halfRelease(todaysConsumption, todaysInflow);
		assertFalse(action.levelTooLow());
		action.halfRelease(todaysConsumption, todaysInflow);
		assertFalse(action.levelTooLow());
		action.doubleRelease(todaysConsumption, todaysInflow);
		assertTrue(action.levelTooLow());
		action.doubleRelease(todaysConsumption, todaysInflow);
		assertTrue(action.levelTooLow());
		
		todaysConsumption = 1;
		todaysInflow = 55;
		action.doubleRelease(todaysConsumption, todaysInflow);
		assertFalse(action.levelTooLow());
	}
	
	// ------------------------------------------
	// test public boolean jobDone()
	/**
	 * Test method : {@link asgn1Solution.DamActions#levelTooLow()}.
	 * </br></br>
	 * Test after defaultReleases, JobDone method will show the correct result
	 * @throws Exception
	 */
	@Test
	public void jobDone_Test1() throws SimulationException {
		Integer todaysConsumption = 10;
		Integer todaysInflow = 10;
		action.defaultRelease(todaysConsumption, todaysInflow);
		assertFalse(action.jobDone());
		action.defaultRelease(todaysConsumption, todaysInflow);
		assertFalse(action.jobDone());
		action.defaultRelease(todaysConsumption, todaysInflow);
		assertFalse(action.jobDone());
		action.defaultRelease(todaysConsumption, todaysInflow);
		assertFalse(action.jobDone());
		action.defaultRelease(todaysConsumption, todaysInflow);
		assertFalse(action.jobDone());
		action.defaultRelease(todaysConsumption, todaysInflow);
		assertFalse(action.jobDone());
		action.defaultRelease(todaysConsumption, todaysInflow);
		assertTrue(action.jobDone());
	}
	
	/**
	 * Test method : {@link asgn1Solution.DamActions#levelTooLow()}.
	 * </br></br>
	 * Test after halfReleases, JobDone method will show the correct result
	 * @throws Exception
	 */
	@Test
	public void jobDone_Test2() throws SimulationException {
		Integer todaysConsumption = 10;
		Integer todaysInflow = 10;
		action.halfRelease(todaysConsumption, todaysInflow);
		assertFalse(action.jobDone());
		action.halfRelease(todaysConsumption, todaysInflow);
		assertFalse(action.jobDone());
		action.halfRelease(todaysConsumption, todaysInflow);
		assertFalse(action.jobDone());
		action.halfRelease(todaysConsumption, todaysInflow);
		assertFalse(action.jobDone());
		action.halfRelease(todaysConsumption, todaysInflow);
		assertFalse(action.jobDone());
		action.halfRelease(todaysConsumption, todaysInflow);
		assertFalse(action.jobDone());
		action.halfRelease(todaysConsumption, todaysInflow);
		assertTrue(action.jobDone());
	}
	
	/**
	 * Test method : {@link asgn1Solution.DamActions#levelTooLow()}.
	 * </br></br>
	 * Test after doubleReleases, JobDone method will show the correct result
	 * @throws Exception
	 */
	@Test
	public void jobDone_Test3() throws SimulationException {
		Integer todaysConsumption = 10;
		Integer todaysInflow = 10;
		action.doubleRelease(todaysConsumption, todaysInflow);
		assertFalse(action.jobDone());
		action.doubleRelease(todaysConsumption, todaysInflow);
		assertFalse(action.jobDone());
		action.doubleRelease(todaysConsumption, todaysInflow);
		assertFalse(action.jobDone());
		action.doubleRelease(todaysConsumption, todaysInflow);
		assertFalse(action.jobDone());
		action.doubleRelease(todaysConsumption, todaysInflow);
		assertFalse(action.jobDone());
		action.doubleRelease(todaysConsumption, todaysInflow);
		assertFalse(action.jobDone());
		action.doubleRelease(todaysConsumption, todaysInflow);
		assertTrue(action.jobDone());
	}
	
	/**
	 * Test method : {@link asgn1Solution.DamActions#levelTooLow()}.
	 * </br></br>
	 * Test if job duration is only 1 day , so after 1 defaultRelease it should show job done
	 * @throws Exception
	 */
	@Test
	public void jobDone_Test4() throws SimulationException {
		// test jobDuration = 1
		jobDuration = 1;
		log = new WaterLog(windownSize, maxEntry);
		action = new DamActions(damCapacity, defaultRelease, jobDuration, log);
		Integer todaysConsumption = 20;
		Integer todaysInflow = 10;
		action.defaultRelease(todaysConsumption, todaysInflow);
		assertTrue(action.jobDone());
	}
	
	/**
	 * Test method : {@link asgn1Solution.DamActions#levelTooLow()}.
	 * </br></br>
	 * Test if job duration is only 1 day , so after 1 halfRelease it should show job done
	 * @throws Exception
	 */
	@Test
	public void jobDone_Test5() throws SimulationException {
		// test jobDuration = 1
		jobDuration = 1;
		log = new WaterLog(windownSize, maxEntry);
		action = new DamActions(damCapacity, defaultRelease, jobDuration, log);
		Integer todaysConsumption = 20;
		Integer todaysInflow = 10;
		action.halfRelease(todaysConsumption, todaysInflow);
		assertTrue(action.jobDone());
	}
	
	/**
	 * Test method : {@link asgn1Solution.DamActions#levelTooLow()}.
	 * </br></br>
	 * Test if job duration is only 1 day , so after 1 doubleRelease it should show job done
	 * @throws Exception
	 */
	@Test
	public void jobDone_Test6() throws SimulationException {
		// test jobDuration = 1
		jobDuration = 1;
		log = new WaterLog(windownSize, maxEntry);
		action = new DamActions(damCapacity, defaultRelease, jobDuration, log);
		Integer todaysConsumption = 20;
		Integer todaysInflow = 10;
		action.doubleRelease(todaysConsumption, todaysInflow);
		assertTrue(action.jobDone());
	}

}
