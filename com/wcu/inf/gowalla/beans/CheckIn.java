/**
 * 
 */
package com.wcu.inf.gowalla.beans;

/**
 * @author Raj
 *
 */
public class CheckIn {

	private int nodeId;
	private int locationId;
	private String datetime;
	private String location;

	private int locationCount;

	public CheckIn(int nodeId, int locationCount) {
		super();
		this.nodeId = nodeId;
		this.locationCount = locationCount;
	}

	public CheckIn(int nodeId, String location, String datetime) {
		super();
		this.nodeId = nodeId;
		this.location = location;
		this.datetime = datetime;
	}

	public CheckIn(int nodeId, int locationId, String datetime) {
		super();
		this.nodeId = nodeId;
		this.locationId = locationId;
		this.datetime = datetime;
	}

	public int getNodeId() {
		return nodeId;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public int getLocationCount() {
		return locationCount;
	}

	public void setLocationCount(int locationCount) {
		this.locationCount = locationCount;
	}

	@Override
	public String toString() {
		return "CheckIn [nodeId=" + nodeId + ", locationId=" + locationId + ", datetime=" + datetime + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + locationId;
		result = prime * result + nodeId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CheckIn other = (CheckIn) obj;
		if (locationId != other.locationId)
			return false;
		if (nodeId != other.nodeId)
			return false;
		return true;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	

}
