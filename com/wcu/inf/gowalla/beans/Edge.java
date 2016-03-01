/**
 * 
 */
package com.wcu.inf.gowalla.beans;

/**
 * @author Raj
 *
 */
public class Edge {
	private int id;
	private int from;
	private int to;

	public Edge() {

	}

	public Edge(int id, int from, int to) {
		super();
		this.id = id;
		this.from = from;
		this.to = to;
	}

	public Edge(int from) {
		super();
		this.from = from;
	}

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Edge [from=" + from + ", to=" + to + "]";
	}

}
