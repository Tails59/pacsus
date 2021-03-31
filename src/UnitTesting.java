import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import org.junit.jupiter.api.*;

/**
 * These JUnit tests check the main components of the Administration Office use
 * cases (http://www.cs.stir.ac.uk/~sma/PACSUS/index.html#R6) as well as a few
 * other components of the software that are necessary for this to be done.
 * 
 * The Main method is called before any testing is run, to instantiate core parts
 * of the program (SystemStatus, Barriers, Campus Security etc.). The testing is 
 * done partly through simulating user input in the UI where appropriate. This helps
 * take over the role of user testing, speeding up the development process.
 * 
 * After these tests are run successfully and the program is useable, further
 * black box testing would be done by the end users on their own system.
 * 
 * @author Taylor Paige
 *
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UnitTesting {
	private static Date now;
	private static Date later;

	private static Day_visitor_permit dayVisitor;
	private static Permanent_visitor_permit permanentVisitor;
	private static University_member_permit universityMember;
	private static Regular_visitor_permit regularVisitor;

	/**
	 * Setup some global things
	 * 
	 * @throws Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		Main.main(null);
		now = new Date();
		now.setDayNumber(1);

		later = new Date();
		later.setDayNumber(now.getDayNumber() + 5); // 5 days in the future
	}

	/**
	 * Convinience method to open a barrier with a reg
	 * 
	 * @param reg [String] reg to check
	 * @return true if the barrier has opened
	 */
	static boolean openBarrier(String reg) {
		Main.barrier1.regNo.setText(reg);
		Main.barrier1.submit.doClick();

		return Main.barrier1.isRaised();
	}

	/**
	 * Close the barrier after a vehicle has cleared
	 * 
	 * @return true if the barrier is now closed
	 */
	static boolean vehicleClear() {
		Main.barrier1.vehicleClear.doClick();

		return !Main.barrier1.isRaised();
	}

	/**
	 * Create a Day_visitor_permit for testing
	 * 
	 * Use Case: Issue new permit: Day Visitor
	 */
	@Test
	@Order(1)
	void createDayVisitorPermit() {
		dayVisitor = new Day_visitor_permit("Davide", "Taylor", new Vehicle_info("Y976 NEN", "Blue", "VW", "Golf"), now,
				now);

		assert (dayVisitor != null);
	}

	/**
	 * Create a Permanent_visitor_permit for testing
	 * 
	 * Use Case: Issue new permit: Permanent Visitor
	 */
	@Test
	@Order(2)
	void createPermanentVisitorPermit() {
		permanentVisitor = new Permanent_visitor_permit("Taylor", now,
				new Vehicle_info("MW13 TWO", "Red", "Audi", "TT"));

		assert (permanentVisitor != null);
	}

	/**
	 * Create a University_member_permit for testing
	 * 
	 * Use Case: Issue new permit: University Member
	 */
	@Test
	@Order(3)
	void createMemberPermit() {
		universityMember = new University_member_permit("Rory", now,
				new Vehicle_info("KR11 PSE", "White", "Honda", "Civic"));

		assert (universityMember != null);
	}

	/**
	 * Create a Regular_visitor_permit for testing
	 * 
	 * Use Case: Issue new permit: Regular Visitor
	 */
	@Test
	@Order(4)
	void createRegularVisitorPermit() {
		regularVisitor = new Regular_visitor_permit("Almantas", "Davide", now, later,
				new Vehicle_info("N11 222", "Black", "Mercedes", "CLK 320"));

		assert (regularVisitor != null);
	}

	/**
	 * Check all barriers are raised when system is inactive Initially the system is
	 * inactive, which means all barriers should be raised.
	 * 
	 * Use case: Allowed to pass (system inactive)
	 */
	@Test
	@Order(5)
	void barrierOpenSystemInactive() {
		assert (Main.barrier1.isRaised());
		assert (Main.barrier2.isRaised());
		assert (Main.barrier3.isRaised());
	}

	/**
	 * Ensure the system activates when the button is pressed When the system is
	 * activated, all barriers should be lowered
	 * 
	 * Use case: Activate barrier system
	 */
	@Test
	@Order(6)
	void activateSystem() {
		Main.getSystemStatus().setStatus(true);
		assert (Main.getSystemStatus().getStatus());
	}

	/**
	 * Ensures barriers are down when the system turns on All barriers should be
	 * lowered when the system activates (done in the previous test), so check if
	 * this has happened
	 */
	@Test
	@Order(7)
	void barrierClosedSystemActive() {
		assert (!Main.barrier1.isRaised());
		assert (!Main.barrier2.isRaised());
		assert (!Main.barrier3.isRaised());
	}

	/**
	 * Check if the barrier opens when the system is active and the vehicle has a
	 * permit
	 * 
	 * Simulate typing a valid registration number then pressing the "submit"
	 * button. The barrier should open (line 162) and then simulate the barrier
	 * closing when the vehicle clear button is pushed (line 165)
	 * 
	 * Use Case: Allowed to pass: permit OK Use Case: Permitted car has cleared the
	 * barrier
	 */
	@Test
	@Order(8)
	void openBarrierGoodPermit() {
		assert (openBarrier("N11 222"));
		assert (vehicleClear());
	}

	/**
	 * Test whether an invalid permit can pass
	 * 
	 * Simulate typing an invalid registration number in the UI, the barrier should
	 * stay closed.
	 * 
	 * Use Case: Prevented from passing: not allowed
	 */
	@Test
	@Order(9)
	void openBarrierBadPermit() {
		assert (!openBarrier("RAN DOM"));
	}

	/**
	 * Modify a permit, and check whether the changes allow it to enter
	 * 
	 * Simulate changing a permit's registration number, and check whether their new
	 * registration will let them pass through the barrier system
	 * 
	 * Use Case: modify permit
	 */
	@Test
	@Order(10)
	void openBarrierChangedPermit() {
		Main.adminOffice1.tf_PermitNumberMod.setText("Rory");
		Main.adminOffice1.btnGetInfo.doClick();
		Main.adminOffice1.tf_RegNumberMod.setText("RAN DOM");
		Main.adminOffice1.submitBtnMod.doClick();

		// Check if the new reg opens the barrier
		assert (openBarrier("RAN DOM"));
	}

	/**
	 * Record a first warning on a permit
	 * 
	 * Add a warning against a permit, this should be the first warning so the
	 * permit will not be suspended
	 * 
	 * Use case: Add warning
	 */
	@Test
	@Order(11)
	void recordFirstWarning() {
		regularVisitor.addWarning();

		assert (regularVisitor.warnings == 1);
		// Barrier should still open, only 1 warning
		assert (openBarrier("N11 222"));
	}

	/**
	 * Record a second warning on a permit
	 * 
	 * Record a second warning against a permit, the permit wont be suspended yet
	 * but this is good to test boundary data.
	 * 
	 * Use case: Add warning
	 */
	@Test
	@Order(12)
	void recordSecondWarning() {
		regularVisitor.addWarning();

		assert (regularVisitor.warnings == 2);
		// Barrier should still open, only 2 warnings
		assert (openBarrier("N11 222"));
	}

	/**
	 * Record a third warning on a permit, causing a suspension
	 * 
	 * Add a third warning to a permit, this should cause the permit to be
	 * automatically suspended and they wont be allowed through the barriers.
	 * 
	 * Use case: Add warning/suspend permit
	 */
	@Test
	@Order(13)
	void recordThirdWarning() {
		regularVisitor.addWarning();

		assert (regularVisitor.warnings == 3);
		// Barrier shouldn't open they have 3 warnings now
		assert (!openBarrier("N11 222"));
	}

	/**
	 * Remove a warning, and hence unsuspend the permit
	 * 
	 * Remove a warning from a permit, if they were previously suspended they
	 * shouldn't be any longer
	 * 
	 * Use case: Delete warning (re-activate suspended permit)
	 */
	@Test
	@Order(14)
	void removeWarning() {
		regularVisitor.removeWarnings(1);

		assert (regularVisitor.warnings == 2);

		// Back to 2 warnings and automatically unsuspended,
		// barrier should open
		assert (openBarrier("N11 222"));
	}

	/**
	 * Cancel a permit and check whether it will still be allowed through
	 * 
	 * Cancel a permit and then simulate it trying to pass through the barriers, the
	 * barrier should remain lowered.
	 */
	@Test
	@Order(15)
	void cancelPermit() {
		Main.getPermitList().cancelPermit("Almantas");
		assert (!openBarrier("N11 222"));
	}

	/**
	 * Check whether administrators can enquire about a permit holder
	 * 
	 * Simulate searching for a permit holder ("Taylor") and checking whether the
	 * system prints out anything other than "Permit not found", it would be too
	 * unwieldy to check whether the system prints out valid data for every part of
	 * the permit, so its best to have this double checked via human
	 * 
	 * Use case: Status enquiry
	 */
	@Test
	@Order(16)
	void statusEnquiry() {
		Main.adminOffice1.tf_Status.setText("Taylor");
		Main.adminOffice1.btnSubmitEnquiry.doClick();

		assert (!Main.adminOffice1.tp_Enquiry.getText().equals("Permit not found"));
	}

	/**
	 * Perform an enquiry on a permit that doesnt exist
	 * 
	 * Simulate searching for a permit holder ("Invalid name") with some junk data,
	 * to see whether the system correctly returns a message informing the user that
	 * the permit doesnt exist.
	 * 
	 * Use case: Status enquiry
	 */
	@Test
	@Order(17)
	void statusEnquiryNoPermit() {
		Main.adminOffice1.tf_Status.setText("Invalid name");
		Main.adminOffice1.btnSubmitEnquiry.doClick();

		assert (Main.adminOffice1.tp_Enquiry.getText().equals("Permit not found"));
	}
}
