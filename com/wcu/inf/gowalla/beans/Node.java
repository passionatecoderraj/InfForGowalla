/**
 * 
 */
package com.wcu.inf.gowalla.beans;

/**
 * @author Raj
 *
 */
public class Node {
	private int id;
	private int edgeCount;
	private int edgeOfEdgeCount;

	public Node(int id) {
		super();
		this.id = id;
	}

	public Node(int id, int edgeCount, int edgeOfEdgeCount) {
		super();
		this.id = id;
		this.edgeCount = edgeCount;
		this.edgeOfEdgeCount = edgeOfEdgeCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Node other = (Node) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Node [id=" + id + ", edgeCount=" + edgeCount + ", edgeOfEdgeCount=" + edgeOfEdgeCount + "]";
	}

}
