package models;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RescueStatus {
	private Map<Long, Boolean> isRescued = new ConcurrentHashMap<>();
	
	public Boolean registerRescue (long rescueId) {
		try {
			isRescued.put(rescueId, false);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Boolean rescue (long rescueId) { 
		if (!isRescued.containsKey(rescueId)) {
			System.err.println("Queried rescueId is not registered.");
			return false;
		} else {
			isRescued.put(rescueId, true);
			return true;
		}
	}
	
	public Boolean isRescued (long rescueId) {
		if (!isRescued.containsKey(rescueId)) {
			System.err.println("Queried rescueId is not registered.");
			return null;
		} else {
			return isRescued.get(rescueId);	
		}
	}

	public void clear() {
		isRescued.clear();
	}
	
	public Map<Long, Boolean> getStatus () {
		return isRescued;
	}
}
