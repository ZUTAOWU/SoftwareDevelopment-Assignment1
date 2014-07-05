package asgn1Question;

/**
 * <p>
 * A log is a series of values, recording how some quantifiable property
 * has changed over time.  Although such a series
 * could conceivably grow without bound, here we require only a fixed
 * "window" of recent entries to be kept (which allows us to implement the
 * <code>Log</code> class in a space-efficient way).  The size of the
 * window must be at least one for the log to be useful.
 * </p><p>
 * The characteristic feature of the <code>Log</code> type is that
 * entries in the series are <em>indexed in reverse</em>, using non-positive
 * values, in accordance with the notion that the type implements a log of
 * <em>past</em> values.  Thus the most recent entry is at index 0, the
 * previous entry is at index &#8722;1, etc.  For a log with window size
 * <em>N</em> the oldest entry stored is at index &#8722;<em>N</em>&nbsp;+&nbsp;1.
 * </p><p>
 * Conceptually we could create a generic <code>Log</code> class capable
 * of storing values of different types.  However, for simplicity, we
 * assume here that log entries are always natural numbers, represented
 * by non-negative <code>Integer</code>s.
 * </p><p>
 * Your first two tasks in the assignment are to implement this
 * interface as class <code>WaterLog</code>, and to produce a
 * <em>comprehensive</em> unit test suite for it.
 * </p><p>
 * <dl>
 * <dt><strong>Constructor:</strong></dt>
 * <dd> To connect to the provided GUI, your <code>WaterLog</code> class must
 * provide a constructor with the following signature, as well as all the
 * methods below.  (Observation: We've expressed this requirement as a
 * comment because it isn't possible to define a constructor in an interface.)
 * </dd>
 * <dt><strong>Parameters:</strong></dt>
 * <dd><code>Integer windowSize</code> - the (minimum) number of entries that
 * the log must retain<br>
 * <code>Integer maxEntry</code> - the largest value that will ever be
 * entered in the log<br>
 * <dt><strong>Throws:</strong></dt>
 * <dd><code>SimulationException</code> - if <code>windowSize</code> is not
 * strictly positive (i.e., greater than zero)<br>
 * <code>SimulationException</code> - if <code>maxEntry</code> is negative
 * (i.e., less than zero)
 * </dd>
 * </dl>
 * </p>
 * 
 * @author INB370
 * @version 1.0
 *
 */
public interface Log {

	/**
	 * Adds a new entry to the log.  Existing entries are shifted
	 * along one place in the window accordingly.
	 * 
	 * @param newEntry the new value to be added to the log
	 * @throws SimulationException if the given value is
	 * not in the range 0 to <code>maxEntry</code>, inclusive
	 */
	public void addEntry(Integer newEntry) throws SimulationException;
	
	/**
	 * Returns the log entry at the given index.  For a log with window
	 * size <em>N</em>, index 0 is the most recently added entry and
	 * index &#8722;<em>N</em>&nbsp;+&nbsp;1 is the oldest.  If there is no log
	 * entry at the given index, because too few entries have been made to
	 * the log so far, <code>null</code> is returned.
	 * Since this is not a valid log entry it alerts the caller to the fact
	 * that the requested entry does not exist.
	 * 
	 * @param index the index of the required entry
	 * @return the log entry at the specified position or <code>null</code>
	 * if there is no such entry
	 * @throws SimulationException if the given index is
	 * not in the valid range of indices for a log with this window size (i.e.,
	 * not between &#8722;<em>N</em>&nbsp;+&nbsp;1
	 * and 0, inclusive, for a window of size <em>N</em>)
	 */
	public Integer getEntry(Integer index) throws SimulationException;
	
	/**
	 * Returns the numerical difference between the most recent and oldest
	 * entries in the log.  This value will be positive if the most recent entry
	 * is larger than the oldest (indicating that the measured quantity has increased),
	 * negative if the most recent entry is less than the oldest (indicating
	 * that the measured quantity has decreased) or zero if they are the same (indicating
	 * no overall change in the quantity of interest). 
	 * Note that the "oldest" entry in the log will be the first entry made if
	 * the total number of entries to date is equal to or less than the window
	 * size&nbsp;<em>N</em>.  Otherwise, it is the entry at index
	 * &#8722;<em>N</em>&nbsp;+&nbsp;1.
	 * 
	 * @return the numerical difference between the most recent and oldest entries
	 * retained in the log's fixed-size window
	 * @throws SimulationException if there are no entries
	 * in the log
	 */
	public Integer variation() throws SimulationException;
	
	/**
	 * Returns the total number of log entries that have been made
	 * since the log was constructed.  (NB: The total  number of
	 * such entries may be greater than the "window" size.)
	 * 
	 * @return the number of log entries made so far
	 */
	public Integer numEntries();
	
}
