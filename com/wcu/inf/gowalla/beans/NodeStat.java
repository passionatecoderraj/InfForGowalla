/**
 * 
 */
package com.wcu.inf.gowalla.beans;

/**
 * @author Raj
 *
 */
public class NodeStat {
	private int id;
	private int edgeCount;
	private int edgeOfEdgeCount;
	private int locationCount;
	private int infEdgeCount;
	private int infLocationCount;

	public NodeStat(int id) {
		super();
		this.id = id;
	}

	public NodeStat(int id, int infEdgeCount, int infLocationCount) {
		super();
		this.id = id;
		this.infEdgeCount = infEdgeCount;
		this.infLocationCount = infLocationCount;
	}

	
	public NodeStat(int id, int edgeCount, int edgeOfEdgeCount, int locationCount, int infEdgeCount,
			int infLocationCount) {
		super();
		this.id = id;
		this.edgeCount = edgeCount;
		this.edgeOfEdgeCount = edgeOfEdgeCount;
		this.locationCount = locationCount;
		this.infEdgeCount = infEdgeCount;
		this.infLocationCount = infLocationCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getInfEdgeCount() {
		return infEdgeCount;
	}

	public void setInfEdgeCount(int infEdgeCount) {
		this.infEdgeCount = infEdgeCount;
	}

	public int getInfLocationCount() {
		return infLocationCount;
	}

	public void setInfLocationCount(int infLocationCount) {
		this.infLocationCount = infLocationCount;
	}

	
	public int getEdgeCount() {
		return edgeCount;
	}

	public void setEdgeCount(int edgeCount) {
		this.edgeCount = edgeCount;
	}

	public int getEdgeOfEdgeCount() {
		return edgeOfEdgeCount;
	}

	public void setEdgeOfEdgeCount(int edgeOfEdgeCount) {
		this.edgeOfEdgeCount = edgeOfEdgeCount;
	}

	public int getLocationCount() {
		return locationCount;
	}

	public void setLocationCount(int locationCount) {
		this.locationCount = locationCount;
	}

	@Override
	public String toString() {
		return "NodeStat [id=" + id + ", edgeCount=" + edgeCount + ", edgeOfEdgeCount=" + edgeOfEdgeCount
				+ ", locationCount=" + locationCount + ", infEdgeCount=" + infEdgeCount + ", infLocationCount="
				+ infLocationCount + "]";
	}

	

}
