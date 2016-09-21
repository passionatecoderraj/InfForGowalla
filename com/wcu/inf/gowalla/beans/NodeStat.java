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
	private double infEdgePercent;
	private double infLocationPercent;
	private int edgerank;
	private int locrank;

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

	public NodeStat(int id, int edgeCount, int edgeOfEdgeCount, int locationCount, int infEdgeCount,
			int infLocationCount, double infEdgePercent, double infLocationPercent) {
		super();
		this.id = id;
		this.edgeCount = edgeCount;
		this.edgeOfEdgeCount = edgeOfEdgeCount;
		this.locationCount = locationCount;
		this.infEdgeCount = infEdgeCount;
		this.infLocationCount = infLocationCount;
		this.infEdgePercent = infEdgePercent;
		this.infLocationPercent = infLocationPercent;
	}

	public double getInfEdgePercent() {
		return infEdgePercent;
	}

	public void setInfEdgePercent(double infEdgePercent) {
		this.infEdgePercent = infEdgePercent;
	}

	public double getInfLocationPercent() {
		return infLocationPercent;
	}

	public void setInfLocationPercent(double infLocationPercent) {
		this.infLocationPercent = infLocationPercent;
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

	public int getEdgerank() {
		return edgerank;
	}

	public void setEdgerank(int edgerank) {
		this.edgerank = edgerank;
	}

	public int getLocrank() {
		return locrank;
	}

	public void setLocrank(int locrank) {
		this.locrank = locrank;
	}

	@Override
	public String toString() {
		return "NodeStat [id=" + id + ", edgeCount=" + edgeCount + ", edgeOfEdgeCount=" + edgeOfEdgeCount
				+ ", locationCount=" + locationCount + ", infEdgeCount=" + infEdgeCount + ", infLocationCount="
				+ infLocationCount + ", infEdgePercent=" + infEdgePercent + ", infLocationPercent=" + infLocationPercent
				+ ", edgerank=" + edgerank + ", locrank=" + locrank + "]";
	}



}
