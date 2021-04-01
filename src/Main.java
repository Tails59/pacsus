/* Generated by Together */

/**
 * This is an outline of a main program to build the operational infrastructure
 * of PACSUS. Look at the description and code for the main method.
 */
@SuppressWarnings({ "unused" })
public class Main {

	private static System_status systemStatus;
	private static Vehicle_list vehicleList;
	private static Permit_list permitList;

	static Barrier barrier1;
	static Barrier barrier2;
	static Barrier barrier3;

	static Campus_security campusSecurity1;
	static Administration_office adminOffice1;

	/**
	 * This main method builds the operational infrastructure of PACSUS as it starts
	 * up:
	 *
	 * The fixed internal "model" classes holding data are instantiated: one
	 * instance each of System status, Vehicle list and Permit list.
	 *
	 * Boundary class objects are instantiated, supplied with references to the
	 * model classes. They are presumed to put themselves on display, and register
	 * as Observers as necessary.
	 */
	public static void main(String[] args) {
		systemStatus = new System_status();
		vehicleList = new Vehicle_list();
		permitList = new Permit_list();

		Timer timer = new Timer(systemStatus, permitList); // Frame, boundary class

		barrier1 = new Barrier(systemStatus, vehicleList, "Barrier 1", 100, 550); // Frame, boundary class
		barrier2 = new Barrier(systemStatus, vehicleList, "Barrier 2", 500, 550);
		barrier3 = new Barrier(systemStatus, vehicleList, "Barrier 3", 900, 550);
		// Repeat for as many barriers as required

		campusSecurity1 = new Campus_security(systemStatus, vehicleList); // Frame, boundary class
		// Repeat for as many campus security screens as required

		adminOffice1 = new Administration_office(systemStatus, vehicleList, permitList); // Frame, boundary class
		// Repeat for as many admin office screens as required

		testPermits();
	} // main

	private static void testPermits() {
		Date now = new Date();
		now.setDayNumber(systemStatus.getToday().getDayNumber());
		Date later = new Date();
		later.setDayNumber(now.getDayNumber() + 5); // 5 days in the future

		Vehicle_info veh1 = new Vehicle_info("MW13 TWO", "Red", "Audi", "TT");
		Permanent_visitor_permit pvp1 = new Permanent_visitor_permit("Taylor", now, veh1);
		veh1.setPermit(pvp1);
		permitList.addPermit(pvp1);
		Vehicle_info veh2 = new Vehicle_info("Y976 NEN", "Blue", "VW", "Golf");
		Day_visitor_permit dvp1 = new Day_visitor_permit("Davide", "Taylor", veh2, now, now);
		veh2.setPermit(dvp1);
		permitList.addPermit(dvp1);
		Vehicle_info veh3 = new Vehicle_info("KR11 PSE", "White", "Honda", "Civic");
		University_member_permit ump = new University_member_permit("Rory", now, veh3);
		veh3.setPermit(ump);
		permitList.addPermit(ump);
		Vehicle_info veh4 = new Vehicle_info("N17 LRN", "Black", "Mercedes", "CLK 320");
		Regular_visitor_permit rvp = new Regular_visitor_permit("Almantas", "Davide", now, later, veh4);
		veh4.setPermit(rvp);
		permitList.addPermit(rvp);
	}

	public static Vehicle_list getVehicleList() {
		return vehicleList;
	}

	public static Permit_list getPermitList() {
		return permitList;
	}

	public static System_status getSystemStatus() {
		return systemStatus;
	}
}
