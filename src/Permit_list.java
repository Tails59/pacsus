import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/* Generated by Together */

/**
 * Permit list manages the collection of permits currently issued and not yet
 * cancelled (or expired). It handles most of the use cases in the
 * Administration section. Note that each Permit must have a unique permit
 * holder name (so a HashTable is probably a good implementation of the
 * collection, with permit holder name as key).
 *
 * There will only be one instance of this class.
 */
public class Permit_list {
	/**
	 * The Permit list maintains a collection of the Permits currently issued.
	 *
	 * This association must be implemented by an attribute holding a collection
	 * data structure (for example: array, hash table - the latter is recommended).
	 *
	 * Note that no two Permits may have the same permit holder name (this
	 * information is not represented diagrammatically).
	 * 
	 * @associates Permit
	 * @label Contains
	 * @clientCardinality 1
	 * @supplierCardinality 0..*
	 * @directed
	 */
	private Hashtable<String, Permit> lnkPermit;

	public Permit_list() {
		this.lnkPermit = new Hashtable<String, Permit>();
	}

	/**
	 * add a new permit
	 * 
	 * @param permit The new permit to issue
	 * @return The permit's UNIQUE_ID field.
	 */
	public String addPermit(Permit permit) {
		lnkPermit.put(permit.getPermitHolder(), permit);

		return permit.getPermitHolder();
	}

	/**
	 * Check if a person has a permit
	 * 
	 * @param name [String] name to search for
	 * @return hasPermit [boolean] true if the person owns a permit
	 */
	public boolean checkPermit(String name) {
		return lnkPermit != null && lnkPermit.containsKey(name);
	}

	/**
	 * Get a permit owned by a person
	 * 
	 * @param name [String] permit owner to search for
	 * @return permit [Permit] The permit object owned by that person, or null if
	 *         they dont have a permit
	 */
	public Permit getPermit(String name) {
		return checkPermit(name) ? lnkPermit.get(name) : null;
	}

	/**
	 * Cancel a permit owned by a person
	 * 
	 * @param name [String] permit owner to search for
	 * @return true if the permit was cancelled, false if the permit doesnt exist or
	 *         is already cancelled
	 */
	public boolean cancelPermit(String name, Vehicle_info[] vehicles) {
		boolean permitFound = false;
		if (lnkPermit != null && lnkPermit.containsKey(name)) {

			Vehicle_list permittedVehicles = Main.getVehicleList();
			//permittedVehicles.remove(permittedVehicles.getVehicle(lnkPermit.get(name)));
			//

			// cancel old permit and remove old vehicle
			Permit p = getPermit(name);
			Vehicle_info[] v = permittedVehicles.getVehicles(p);
			for (int i = 0; i < v.length; i++) {
				permittedVehicles.remove(v[i]);
			}
			//
			if (vehicles != null) {
				List<Vehicle_info> keys = new ArrayList<Vehicle_info>(permittedVehicles.getVehicleList().keySet());

				Vehicle_info veh;
				int ammount = permittedVehicles.getVehicleList().size();
				//
				for (int i = 0; i < ammount; i++) {
					veh = keys.get(i);
					if (veh.getPermit().equals(p)) {
						permittedVehicles.remove(vehicles[i]);
					}
				}
			}

			lnkPermit.remove(name);
			permitFound = true;
		} else {
			permitFound = false;
		}
		return permitFound;
	}

	/**
	 * Reset all permits at the start of the year
	 * 
	 * @param today
	 */
	public void resetPermits(Date today) {
		List<String> keys = new ArrayList<String>(lnkPermit.keySet());

		Permit aPermit = null;
		Date visit = new Date();
		int permits = lnkPermit.size();

		for (int i = 0; i < permits; i++) {
			aPermit = lnkPermit.get(keys.get(i));
			aPermit.resetEntry();
			visit.setDayNumber(0);
			if (aPermit instanceof Day_visitor_permit) {
				visit.setDayNumber(((Day_visitor_permit) aPermit).getActiveDate().getDayNumber());
			} else if (aPermit instanceof Regular_visitor_permit) {
				visit.setDayNumber(((Regular_visitor_permit) aPermit).getExpiryDate().getDayNumber());
			}
			if (visit.getDayNumber() > 0) {
				if (visit.getDayNumber() < today.getDayNumber()) {

					cancelPermit(aPermit.permitHolder, null);
					permits--;
					System.out.println("Cancelled: " + aPermit.getPermitHolder());
				}

			}
			if (today.getDayNumber() == 1) {
				aPermit.removeWarnings(aPermit.getWarnings());
				aPermit.resetEntries();
				aPermit.unsuspend();

			}
		}
	}
}
