/**
 * 
 */
package com.wcu.inf.gowalla;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wcu.inf.gowalla.beans.CheckIn;
import com.wcu.inf.gowalla.beans.Edge;
import com.wcu.inf.gowalla.beans.Node;

/**
 * @author Raj
 *
 */
public class UpdateNodeStats {

	ReadFromDb dbHelper = new ReadFromDb();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UpdateNodeStats obj = new UpdateNodeStats();
		// obj.updateEdgeCount();
	//	obj.updateEdgeOfEdgeCount();
		// obj.updateLocationCount();
	}

	public void updateEdgeCount() {
		List<Edge> nodes = dbHelper.getAllEdges();
		System.out.println(nodes.size());
	}

	public void updateEdgeOfEdgeCount() {
		List<Edge> list = dbHelper.getAllEdges();

		Map<Node, List<Edge>> map = new HashMap<Node, List<Edge>>();
		for (Edge e : list) {
			Node n = new Node(e.getFrom());
			if (map.containsKey(n)) {
				map.get(n).add(e);
			} else {
				List<Edge> lst = new ArrayList<Edge>();
				lst.add(e);
				map.put(n, lst);
			}
		}

		int edgeOfEdgeCount = 0;
		for (Node n : map.keySet()) {
			edgeOfEdgeCount = 0;
			for (Edge e : map.get(n)) {
				edgeOfEdgeCount += map.get(new Node(e.getTo())).size();
			}
			n.setEdgeOfEdgeCount(edgeOfEdgeCount);
		}
		dbHelper.updateNodeStatsEdgeOfEdgeCountByNodeId(map);
	}

	public void updateLocationCount() {
		List<CheckIn> list = dbHelper.getLocationsCountByNodeId();
		System.out.println(list.size());
		dbHelper.updateNodeStatsLocationCountByNodeId(list);
	}

}
