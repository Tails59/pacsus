
import java.util.Observable;

/* Generated by Together */

/**
 * This class holds key control information for the operation of the barrier
 * system.
 *
 * It extends Observable, and notifies its observers whenever either the date
 * changes (caused by the Timer class), or the barrier system activity status
 * changes (caused by an instance of the Campus security class), or the barrier
 * event log changes (caused by an instance of the Barrier class).
 *
 * There will only be one instance of this class.
 */
@SuppressWarnings({ "unused", "deprecation" })
public class System_status extends Observable {
	/**
	 * This attribute is the central indication of the activity status of the whole
	 * barrier system. It is set/unset by messages from instances of the Campus
	 * security class.
	 */
	private boolean systemActive = false;

	/**
	 * An array of strings showing recent attempts to pass through the barriers
	 * (both successful and unsuccessful). The last 20 should be enough? These are
	 * intended for display on the Campus_security screens.
	 */

	private String[] log = new String[20];

	private Campus_security security;

	/**
	 * This attribute is kept up to date by the Timer.
	 * 
	 * @clientCardinality 1
	 * @supplierCardinality 1
	 * @link aggregation
	 * @label Contains
	 * @directed
	 */
	private Date today;

	private int nextIndex;

	/**
	 * Set todays date
	 * 
	 * @param date [Date] current date
	 */
	public void setToday(Date date) { // might need to be changed
		today = date;
		System.out.println("Date: " + today.getDayNumber());
		setChanged();
		notifyObservers();
	}

	/**
	 * Get today's date
	 * 
	 * @return today [Date] current date
	 */
	public Date getToday() {
		return today;
	}

	/**
	 * Set the current system status
	 * 
	 * @param status [boolean] Status: true = on, false = off.
	 */
	public void setStatus(boolean status) {
		systemActive = status;
		setChanged();
		notifyObservers();
	}

	/**
	 * Get the current system status
	 * 
	 * @return status [boolean] true if the system is active
	 */
	public boolean getStatus() {
		return systemActive;
	}

	/**
	 * Record an entry through the barriers
	 * 
	 * @param regNo   [String] vehicle's registration number
	 * @param entered [boolean] True if the vehicle was permitted through, false
	 *                otherwise (i.e. their permit was suspended)
	 */
	public void recordEntry(String regNo, boolean entered) {
		if (nextIndex < 20) {
			if (entered) {

				log[nextIndex] = "\n[DAY " + today.getDayNumber() + "] Successful entry for " + regNo;
				nextIndex++;

			} else {
				log[nextIndex] = "\n[DAY " + today.getDayNumber() + "] Unsuccessful entry for " + regNo;
				nextIndex++;
			}
		} else {
			nextIndex = 20;
			for (int i = 0; i < log.length - 1; i++) {
				log[i] = log[i + 1];
			}
			if (entered) {

				log[nextIndex] = "\n[DAY " + today.getDayNumber() + "] Successful entry for " + regNo;

			} else {
				log[nextIndex] = "\n[DAY " + today.getDayNumber() + "] Unsuccessful entry for " + regNo;

			}
		}
		// setChanged();
	}

	/**
	 * Add the entry to the log, to be displayed in Campus Security
	 * 
	 * @param veh          the vehicle information
	 * @param entryAllowed if the entry was allowed or not
	 */
	public void addEntry(Vehicle_info veh, boolean entryAllowed) {
		String entryState = "";
		if (entryAllowed)
			entryState = "allowed";
		else
			entryState = "denied";
		String entryInfo = "Day " + today.getDayNumber() + ". Registration: " + veh.getRegistration() + ", entry "
				+ entryState + ".";
		int entries = 0;

		for (int i = 0; i < log.length; i++) {
			if (log[i] != null) {
				entries++;
			}
		}

		if (entries < log.length) {
			log[entries] = entryInfo;
		} else {
			for (int i = 0; i < log.length - 1; i++) {
				log[i] = log[i + 1];
			}
			log[log.length - 1] = entryInfo;
		}
	}

	/**
	 * return the logs
	 * 
	 * @return the array of log
	 */
	public String[] getLog() {
		return log;
	}

}
