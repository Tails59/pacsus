import java.util.Hashtable;

/* Generated by Together */

/**
 * Permit list manages the collection of permits currently issued and not yet cancelled (or expired).
 * It handles most of the use cases in the Administration section. Note that each Permit must have a
 * unique permit holder name (so a HashTable is probably a good implementation of the collection, with
 * permit holder name as key).
 *
 * There will only be one instance of this class.
 */
public class Permit_list {
    /** The Permit list maintains a collection of the Permits currently issued.
     *
     * This association must be implemented by an attribute holding a collection data structure (for
     * example: array, hash table - the latter is recommended).
     *
     * Note that no two Permits may have the same permit holder name (this information is not represented diagrammatically).
     * @associates Permit
     * @label Contains
     * @clientCardinality 1
     * @supplierCardinality 0..*
     * @directed*/
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
    
    public boolean checkPermit(String name) {
    	return lnkPermit != null && lnkPermit.containsKey(name);
    }
    
    public Permit getPermit(String name) {
    	if (lnkPermit == null || !lnkPermit.containsKey(name)) {
        	return null;
    	} else {
    		return lnkPermit.get(name);
    	}
    }
    
    public boolean cancelPermit(String name) {
    	boolean permitFound=false;
    	if (lnkPermit != null && lnkPermit.containsKey(name)) {
    		lnkPermit.remove(name);
    		permitFound=true;
    	} else {
    		permitFound=false;
    	}
    	return permitFound;
    }
    
    
}
