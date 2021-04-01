import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* Generated by Together */

/**
 * Vehicle list manages the collection of vehicles currently associated with
 * permits. It handles checks of whether vehicles are allowed through the
 * barriers, records warnings, and various other functions. Note that each
 * Vehicle_info object must have a unique registration number, to allow sensible
 * checking and recording of entries onto the campus (so a HashTable is probably
 * a good implementation of the collection, with registration number as key).
 *
 * There will only be one instance of this class.
 */
public class Vehicle_list {
	/**
	 * The Vehicle list maintains a collection of the known Vehicle_infos associated
	 * with all permits. This association must be implemented by an attribute
	 * holding a collection data structure (for example: array, hash table - the
	 * latter is recommended).
	 *
	 * Note that no two Vehicle_infos may have the same registration number (this
	 * information is not represented diagrammatically) - this is to guarantee
	 * consistency with the constraint that no vehicle is associated with more than
	 * one permit.
	 *
	 * Note that, given a registration number, the Vehicle_list can look up the
	 * appropriate Vehicle_info instance, and via that it can obtain the vehicle's
	 * permit information.
	 * 
	 * @associates Vehicle_info
	 * @label Contains
	 * @clientCardinality 1
	 * @supplierCardinality 0..*
	 * @directed
	 */
	private Hashtable<Vehicle_info, Permit> vehicleList;

	Vehicle_list() {
		vehicleList = new Hashtable<Vehicle_info, Permit>();
	}

	public Hashtable<Vehicle_info, Permit> getVehicleList() {
		return this.vehicleList;
	}

	public void addNew(Vehicle_info veh_info, Permit permit) throws Exception {
		Permit old = vehicleList.put(veh_info, permit);

		if (old != null) {
			vehicleList.put(veh_info, old);
			throw new Exception("Vehicle already assigned to permit");
		}
	}

	public void remove(Vehicle_info veh_info) {
		vehicleList.remove(veh_info);
	}

	public boolean checkPermit(String reg) {
		Set<Vehicle_info> keySet = vehicleList.keySet();

		Iterator<Vehicle_info> iterator = keySet.iterator();

		while (iterator.hasNext()) {
			if (iterator.next().getRegistration().equals(reg)) {
				return true;
			}
		}

		return false;
	}

	public Permit getPermit(Vehicle_info veh) {
		return vehicleList.get(veh);
	}

	public Vehicle_info getVehicle(Permit p) { // might be not usable anymore because we have several vehicles per
												// permit, method below getVehicles() replaces this
		//
		List<Vehicle_info> keys = new ArrayList<Vehicle_info>(vehicleList.keySet());
		List<Permit> values = new ArrayList<Permit>(vehicleList.values());

		for (int i = 0; i < vehicleList.size(); i++) {
			if (values.get(i) == p) {
				return keys.get(i);
			}
		}
		return null;
	}

	public Vehicle_info[] getVehicles(Permit p) {
		//
		int permittedVehicles = 0;
		Vehicle_info[] vehicles;
		List<Permit> values = new ArrayList<Permit>(vehicleList.values());
		//
		for (int i = 0; i < vehicleList.size(); i++) {
			if (values.get(i) == p) {
				permittedVehicles++;
			}
		}
		//
		vehicles = new Vehicle_info[permittedVehicles];
		//
		Set<Vehicle_info> keySet = vehicleList.keySet();
		Iterator<Vehicle_info> iterator = keySet.iterator();
		int index = 0;

		while (iterator.hasNext()) {
			Vehicle_info veh = iterator.next();
			if (veh.getPermit().equals(p)) {
				vehicles[index] = veh;
				index++;
			}
		}
		return vehicles;
	}

	public boolean issueWarning(String regNo) {
		final int MAX_WARNINGS = 3;
		if (getAPermit(regNo) != null) {
			Permit toWarn = getAPermit(regNo);
			if (toWarn.getWarnings() == MAX_WARNINGS) {
				return false;
			} else {
				toWarn.addWarning();
				System.out.println("Warnings: " + toWarn.getWarnings());
				return true;
			}
		} else {
			return false;
		}
	}

	public Permit getAPermit(String reg) {
		//
		Permit aPermit = null;
		if (checkPermit(reg)) {
			Set<Vehicle_info> keySet = vehicleList.keySet();

			Iterator<Vehicle_info> iterator = keySet.iterator();

			while (iterator.hasNext()) {
				Vehicle_info veh = iterator.next();
				if (veh.getRegistration().equals(reg)) {
					aPermit = vehicleList.get(veh);
				}
			}
		}
		return aPermit;
	}

	public boolean canPass(Permit p) {
		if (p.canPassBarrier()) {
			p.addEntry();
			return true;

		}

		return false;
	}
}
