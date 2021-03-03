/* Generated by Together */

/**
 * Each instance of Vehicle_info models an actual physical vehicle, so no two instances
 * may have the same registration number. There is no direct notation for this. Instead
 * the Vehicle list object collecting the Vehicles must enforce it by checking new additions
 * - the use of a HashTable in Vehicle_list with the registration number as key is helpful
 * with this.
 *
 * This class must contain at least the registration number, and may have any other attributes
 * that the University may from time to time find useful (such as car make, model, colour...).
 */
@SuppressWarnings("unused")
public class Vehicle_info {
    /**
     * The vehicle's registration number.
     */
    private String regNo;

    /**
     * Each vehicle (and so each registration number) is associated with exactly one permit,
     * which describes its authorization to enter the campus (or not!).
     *
     * This attribute references the permit associated with this vehicle. It alows the permit
     * status of any vehicle to be accessed via the Vehicle_list knowing only the registration
     * number - for example for barrier checks, recording warnings, and so on.
     *
     * Note that many vehicles may be associated with the same permit.
     * @supplierCardinality 1
     * @clientCardinality 1..*
     * @label Allowed entry by
     * @directed
     */
    private Permit lnkPermit;
}
