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

	private int locationCount;

	public CheckIn(int nodeId, int locationCount) {
		super();
		this.nodeId = nodeId;
		this.locationCount = locationCount;
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

}
