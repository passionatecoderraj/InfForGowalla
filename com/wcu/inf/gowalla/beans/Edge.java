/**
 * 
 */
package com.wcu.inf.gowalla.beans;

/**
 * @author Raj
 *
 */
public class Edge {
	private int from;
	private int to;

	public Edge(int from, int to) {
		super();
		this.from = from;
		this.to = to;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	@Override
	public String toString() {
		return "Edge [from=" + from + ", to=" + to + "]";
	}

}
