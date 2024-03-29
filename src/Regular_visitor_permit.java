/* Generated by Together */

/**
 * For a description of Regular visitors, follow hyperlink to the Administration
 * use case for issuing a new Regular visitor permit.
 */
public class Regular_visitor_permit extends Permit {
	/**
	 * The name of the University member hosting the visit.
	 */
	private String hostName;

	/**
	 * The date that the visit ends - entry will not be allowed after this date.
	 * 
	 * @label Ending on
	 * @clientCardinality 1
	 * @supplierCardinality 1
	 * @link aggregation
	 * @directed
	 */
	private Date expiryDate;

	/**
	 * The permit for a regular visitor extend Permit super class
	 * 
	 * @param permitHolder the name of how holds the permit
	 * @param host         the host of the visits
	 * @param issue        the issue date
	 * @param expiry       the expirying date
	 * @param firstVehicle the first vehicle used
	 */
	public Regular_visitor_permit(String permitHolder, String host, Date issue, Date expiry,
			Vehicle_info firstVehicle) {
		super(permitHolder, firstVehicle, issue);

		this.hostName = host;
		this.expiryDate = expiry;
	}

	/**
	 * Set the host Name
	 * 
	 * @param host Name the host visited by permit holder
	 */
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	/**
	 * get the host name
	 * 
	 * @return the host name
	 */
	public String getHostName() {
		return this.hostName;
	}

	/**
	 * Get the expiration Date
	 * 
	 * @return the date when the permit expire
	 */
	public Date getExpiryDate() {
		return this.expiryDate;
	}

	/**
	 * Set the expiration date
	 * 
	 * @param newDate the date of when the permit expire
	 */
	public void setExpiryDate(Date newDate) {
		this.expiryDate = newDate;
	}
}
