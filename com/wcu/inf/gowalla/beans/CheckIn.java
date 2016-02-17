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

	@Override
	public String toString() {
		return "CheckIn [nodeId=" + nodeId + ", locationId=" + locationId + ", datetime=" + datetime + "]";
	}

}
