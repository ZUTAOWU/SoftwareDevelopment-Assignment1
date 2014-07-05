package asgn1Solution;

import static org.junit.Assert.*;

import org.junit.Test;

import asgn1Question.SimulationException;

public class LogTests {
	// ---------------------------------------------------------------
	/**
	 * Test method : {@link asgn1Solution.WaterLog#WaterLog(java.lang.Integer, java.lang.Integer)}.
	 * <br/><br/> 
	 * Test WaterLog construct, when windowSize is less 0 then throw SimulationException
	 * @throws SimulationException
	 */
	@Test(expected = SimulationException.class)
	public void Constructor_Exception_windowSize_Test() throws SimulationException {
		int maxEntry = 7;
		int windowSize = -1;
		@SuppressWarnings("unused")
		WaterLog log = new WaterLog(windowSize, maxEntry);
	}
	
	/**
	 * Test method : {@link asgn1Solution.WaterLog#WaterLog(java.lang.Integer, java.lang.Integer)}.
	 * <br/><br/> 
	 * Test WaterLog construct, when windowSize is  0 then throw SimulationException
	 * @throws SimulationException
	 */
	@Test(expected = SimulationException.class)
	public void Constructor_Exception_windowSize_Test1() throws SimulationException {
		int maxEntry = 7;
		int windowSize = 0;
		@SuppressWarnings("unused")
		WaterLog log = new WaterLog(windowSize, maxEntry);
	}

	/**
	 * Test method : {@link asgn1Solution.WaterLog#WaterLog(java.lang.Integer, java.lang.Integer)}.
	 * <br/><br/> 
	 * Test WaterLog construct, when maxEntry is less than 0 then throw SimulationException
	 * @throws SimulationException
	 */
	@Test(expected = SimulationException.class)
	public void Constructor_Exception_maxEntry_Test() throws SimulationException {
		int maxEntry = -1;
		int windowSize = 7;
		@SuppressWarnings("unused")
		WaterLog log = new WaterLog(windowSize, maxEntry);
	}

	/**
	 * Test method : {@link asgn1Solution.WaterLog#WaterLog(java.lang.Integer, java.lang.Integer)}.
	 * <br/><br/> 
	 * Test WaterLog construct, when maxEntry is less than 0 then throw SimulationException 
	 * @throws SimulationException
	 */
	@Test(expected = SimulationException.class)
	public void Constructor_Exception_maxEntry_Test2() throws SimulationException {
		int maxEntry = -10;
		int windowSize = 7;
		@SuppressWarnings("unused")
		WaterLog log = new WaterLog(windowSize, maxEntry);
	}

	// ---------------------------------------------------------------
	/**
	 * Test method : {@link asgn1Solution.WaterLog#addEntry(java.lang.Integer)}.
	 * <br/><br/> 
	 * Test add negative entry then throw SimulationException
	 * @throws SimulationException
	 */
	@Test(expected = SimulationException.class)
	public void addEntry_Exception_Test() throws SimulationException {
		int maxEntry = 100;
		int windowSize = 7;
		WaterLog log = new WaterLog(windowSize, maxEntry);
		log.addEntry(-1);
	}

	/**
	 * Test method : {@link asgn1Solution.WaterLog#addEntry(java.lang.Integer)}.
	 * <br/><br/> 
	 * Test add entry which is bigger than maxEntry then throw SimulationException
	 * @throws SimulationException
	 */
	@Test(expected = SimulationException.class)
	public void addEntry_Exception_Test2() throws SimulationException {
		int maxEntry = 100;
		int windowSize = 7;
		WaterLog log = new WaterLog(windowSize, maxEntry);
		log.addEntry(101);
	}

	// ---------------------------------------------------------------
	/**
	 * Test method : {@link asgn1Solution.WaterLog#numEntries()}.
	 * <br/><br/> 
	 * Test numEntries() when there has no entry in the log
	 * @throws SimulationException
	 */
	@Test
	public void numEntries_Test1() throws SimulationException {
		// test 0 number entry
		int maxEntry = 100;
		int windowSize = 7;
		WaterLog log = new WaterLog(windowSize, maxEntry);
		assertEquals("0", log.numEntries());
	}

	/**
	 * Test method : {@link asgn1Solution.WaterLog#numEntries()}.
	 * <br/><br/> 
	 * Test numEntries() when there has 2 entries in the log
	 * @throws SimulationException
	 */
	@Test
	public void numEntries_Test2() throws SimulationException {
		// test 2 entries
		int maxEntry = 100;
		int windowSize = 7;
		WaterLog log = new WaterLog(windowSize, maxEntry);
		log.addEntry(10);
		log.addEntry(10);
		assertEquals("2", log.numEntries());
	}
	
	/**
	 * Test method : {@link asgn1Solution.WaterLog#numEntries()}.
	 * <br/><br/>
	 * Test there has more entries than window size, The total number of such entries may be greater than the window size
	 * @throws SimulationException
	 */
	@Test
	public void numEntries_Test3() throws SimulationException {
		// test more entries
		int maxEntry = 100;
		int windowSize = 7;
		WaterLog log = new WaterLog(windowSize, maxEntry);
		for (int i = 0; i < 10; i++) {
			log.addEntry(10);
		}
		assertEquals("10", log.numEntries());
	}
	// ---------------------------------------------------------------
	// test public void getEntry(Integer newEntry)
	/**
	 * Test method : {@link asgn1Solution.WaterLog#getEntry(java.lang.Integer)}.
	 * <br/><br/>
	 * Test if the given index is not in the valid range of indices for a log with this window size 
	 * throw SimulationException
	 * @throws SimulationException
	 */
	@Test(expected = SimulationException.class)
	public void getEntry_Exception_Test1() throws SimulationException {
		int maxEntry = 100;
		int windowSize = 7;
		WaterLog log = new WaterLog(windowSize, maxEntry);
		log.getEntry(1);
	}
	
	/**
	 * Test method : {@link asgn1Solution.WaterLog#getEntry(java.lang.Integer)}.
	 * <br/><br/>
	 * Test if the given index is not in the valid range of indices for a log with this window size 
	 * throw SimulationException
	 * @throws SimulationException
	 */
	@Test(expected = SimulationException.class)
	public void getEntry_Exception_Test2() throws SimulationException {
		int maxEntry = 100;
		int windowSize = 7;
		WaterLog log = new WaterLog(windowSize, maxEntry);
		log.getEntry(-7);
	}
	
	/**
	 * Test method : {@link asgn1Solution.WaterLog#getEntry(java.lang.Integer)}.
	 * <br/><br/>
	 * Test if the given index is not in the valid range of indices for a log with this window size 
	 * throw SimulationException
	 * @throws SimulationException
	 */
	@Test(expected = SimulationException.class)
	public void getEntry_Exception_Test3() throws SimulationException {
		int maxEntry = 100;
		int windowSize = 7;
		WaterLog log = new WaterLog(windowSize, maxEntry);
		log.getEntry(-10);
	}
	
	/**
	 * Test method : {@link asgn1Solution.WaterLog#getEntry(java.lang.Integer)}.
	 * <br/><br/>
	 * Test get the current entry
	 * @throws SimulationException
	 */
	@Test
	public void getEntry_Test1() throws SimulationException {
		// test normal getEntry
		int maxEntry = 100;
		int windowSize = 7;
		WaterLog log = new WaterLog(windowSize, maxEntry);
		log.addEntry(10);
		assertEquals(Integer.valueOf(10),log.getEntry(0));
	}
	
	/**
	 * Test method : {@link asgn1Solution.WaterLog#getEntry(java.lang.Integer)}.
	 * <br/><br/>
	 * Test if there is no such entry then return null
	 * @throws SimulationException
	 */
	@Test
	public void getEntry_Test2() throws SimulationException {
		int maxEntry = 100;
		int windowSize = 7;
		WaterLog log = new WaterLog(windowSize, maxEntry);
		log.addEntry(10);
		assertEquals(null,log.getEntry(-1));
	}
	
	/**
	 * Test method : {@link asgn1Solution.WaterLog#getEntry(java.lang.Integer)}.
	 * <br/><br/>
	 * Test get every entry in window size
	 * @throws SimulationException
	 */
	@Test
	public void getEntry_Test3() throws SimulationException {
		int maxEntry = 100;
		int windowSize = 7;
		WaterLog log = new WaterLog(windowSize, maxEntry);
		for(int i = 0 ; i < 10 ; i++ ) {
			log.addEntry(i);
		}
		assertEquals(Integer.valueOf(9),log.getEntry(0));
		assertEquals(Integer.valueOf(8),log.getEntry(-1));
		assertEquals(Integer.valueOf(7),log.getEntry(-2));
		assertEquals(Integer.valueOf(6),log.getEntry(-3));
		assertEquals(Integer.valueOf(5),log.getEntry(-4));
		assertEquals(Integer.valueOf(4),log.getEntry(-5));
		assertEquals(Integer.valueOf(3),log.getEntry(-6));
	}
	
	// ---------------------------------------------------------------
	// test public Integer variation()
	/**
	 * Test method : {@link asgn1Solution.WaterLog#variation()}.
	 * <br/><br/>
	 * Test if there are no entries in the log, variation() should throw SimulationException
	 * @throws SimulationException
	 */
	@Test(expected = SimulationException.class)
	public void variation_Exception_Test1() throws SimulationException {
		// test Exception
		int maxEntry = 100;
		int windowSize = 7;
		WaterLog log = new WaterLog(windowSize, maxEntry);
		log.variation();
	}
	
	/**
	 * Test method : {@link asgn1Solution.WaterLog#variation()}.
	 * <br/><br/>
	 * Test if there is only have one entry, the variation should be 0
	 * @throws SimulationException
	 */
	@Test
	public void variation_Test1() throws SimulationException {
		// test only have one entry, the variation should be 0
		int maxEntry = 100;
		int windowSize = 7;
		WaterLog log = new WaterLog(windowSize, maxEntry);
		log.addEntry(1);
		assertEquals(Integer.valueOf(0),log.variation());
	}
	
	/**
	 * Test method : {@link asgn1Solution.WaterLog#variation()}.
	 * <br/><br/>
	 * Test if most recent and oldest entry are equal, the variation should be 0
	 * @throws SimulationException
	 */
	@Test
	public void variation_Test2() throws SimulationException {
		// test 2 equal entries
		int maxEntry = 100;
		int windowSize = 7;
		WaterLog log = new WaterLog(windowSize, maxEntry);
		log.addEntry(1);
		log.addEntry(1);
		assertEquals(Integer.valueOf(0),log.variation());
	}
	
	/**
	 * Test method : {@link asgn1Solution.WaterLog#variation()}.
	 * <br/><br/>
	 * Test normal variation (positive)
	 * @throws SimulationException
	 */
	@Test
	public void variation_Test3() throws SimulationException {
		// test positive variation
		int maxEntry = 100;
		int windowSize = 7;
		WaterLog log = new WaterLog(windowSize, maxEntry);
		log.addEntry(1);
		log.addEntry(2);
		assertEquals(Integer.valueOf(1),log.variation());
	}
	
	/**
	 * Test method : {@link asgn1Solution.WaterLog#variation()}.
	 * <br/><br/>
	 * Test normal variation (negative)
	 * @throws SimulationException
	 */
	@Test
	public void variation_Test4() throws SimulationException {
		// test negative variation
		int maxEntry = 100;
		int windowSize = 7;
		WaterLog log = new WaterLog(windowSize, maxEntry);
		log.addEntry(1);
		log.addEntry(0);
		assertEquals(Integer.valueOf(-1),log.variation());
	}
	
	/**
	 * Test method : {@link asgn1Solution.WaterLog#variation()}.
	 * <br/><br/>
	 * Test normal variation
	 * @throws SimulationException
	 */
	@Test
	public void variation_Test5() throws SimulationException {
		// test log contain more entries
		int maxEntry = 100;
		int windowSize = 7;
		WaterLog log = new WaterLog(windowSize, maxEntry);
		log.addEntry(10);
		log.addEntry(1);
		log.addEntry(100);
		assertEquals(Integer.valueOf(90),log.variation());
	}
	
	/**
	 * Test method : {@link asgn1Solution.WaterLog#variation()}.
	 * <br/><br/>
	 * Test normal variation
	 * @throws SimulationException
	 */
	@Test
	public void variation_Test6() throws SimulationException {
		// test log contain more entries
		int maxEntry = 100;
		int windowSize = 7;
		WaterLog log = new WaterLog(windowSize, maxEntry);
		log.addEntry(100);
		log.addEntry(1);
		log.addEntry(10);
		assertEquals(Integer.valueOf(-90),log.variation());
	}
	
	/**
	 * Test method : {@link asgn1Solution.WaterLog#variation()}.
	 * <br/><br/>
	 * Test variation when add more entries than windows size
	 * @throws SimulationException
	 */
	@Test
	public void variation_Test7() throws SimulationException {
		// test log contain more entries, more than windowSize
		int maxEntry = 100;
		int windowSize = 7;
		WaterLog log = new WaterLog(windowSize, maxEntry);
		log.addEntry(100);
		for (int i = 0; i < 10; i++) {
			log.addEntry(1);
		}
		log.addEntry(10);
		assertEquals(Integer.valueOf(9),log.variation());
	}
}
