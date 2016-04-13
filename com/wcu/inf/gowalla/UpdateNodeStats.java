/**
 * 
 */
package com.wcu.inf.gowalla;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wcu.inf.gowalla.beans.CheckIn;
import com.wcu.inf.gowalla.beans.Edge;
import com.wcu.inf.gowalla.beans.Node;
import com.wcu.inf.gowalla.beans.NodeStat;

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
		// obj.updateEdgeOfEdgeCount();
		// obj.updateLocationCount();
		obj.updateInfluenceLocationCount();
		// Map<Integer, List<Integer>> map = new
		// ReadFromDb().getAllEdgesInMap();
		// System.out.println(map.get(376).size());

		// Map<Integer, List<CheckIn>> map = new ReadFromDb().getAllCheckins();
		// System.out.println(map.get(376).size());

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

	public void updateInfluenceEdgeCount() {
		List<Integer> nodes = dbHelper.getAllNodes();
		Map<Integer, List<Integer>> allEdges = dbHelper.getAllEdgesInMap();
		Map<Integer, List<CheckIn>> allCheckIns = FilesReader.getCheckInsByNodeIdFromFile();

		int count = 2;
		int l, r, n = nodes.size();
		l = 0;
		r = l + count;
		while (r < n) {
			System.out.println("Attempting : l=" + l + ",r=" + r);

			updateInfluenceEdgeCount(nodes, allEdges, allCheckIns, l, r);
			System.out.println("Inserted : l=" + l + ",r=" + r);
			l = r;
			r = r + count;
		}
		if (l < n) {
			System.out.println("Attempting : l=" + l + ",r=" + n);
			updateInfluenceEdgeCount(nodes, allEdges, allCheckIns, l, r);
			System.out.println("Inserted : l=" + l + ",r=" + n);
		}
	}

	private void updateInfluenceEdgeCount(List<Integer> nodes, Map<Integer, List<Integer>> allEdges,
			Map<Integer, List<CheckIn>> allCheckIns, int l, int r) {
		NodeStat obj;
		List<NodeStat> list = new ArrayList<NodeStat>();

		for (int i = l; i < r; i++) {
			obj = new NodeStat(nodes.get(i));
			updateInfluenceEdgeCount(obj, allEdges, allCheckIns);
			list.add(obj);
		}

		dbHelper.updateNodeStatsInfEdgeCount(list);
	}

	private void updateInfluenceEdgeCount(NodeStat node, Map<Integer, List<Integer>> allEdges,
			Map<Integer, List<CheckIn>> allCheckIns) {
		int friends_count = 0;

		Set<CheckIn> set = new HashSet<CheckIn>();
		boolean flag = false;
		for (Integer edge : allEdges.get(node.getId())) {
			flag = false;
			if (allCheckIns.containsKey(node.getId()) && allCheckIns.containsKey(edge)) {
				for (CheckIn c : allCheckIns.get(node.getId())) {
					if (flag)
						break;
					for (CheckIn c2 : allCheckIns.get(edge)) {
						if (flag)
							break;
						if (dbHelper.toVisitedAfterFrom(c, c2)) {
							// set.add(c);
							flag = true;
						}
					}

				}
			}
			if (flag) {
				friends_count++;
			}
		}

		node.setInfEdgeCount(friends_count);
		node.setInfLocationCount(set.size());
	}

	public void updateInfluenceLocationCount() {
		List<Integer> nodes = dbHelper.getAllNodes();
		Map<Integer, List<Integer>> allEdges = dbHelper.getAllEdgesInMap();
		Map<Integer, List<CheckIn>> allCheckIns = FilesReader.getCheckInsByNodeIdFromFile();

		int count = 2;
		int l, r, n = nodes.size();
		l = 10000;
		r = l + count;
		while (r < n) {
			System.out.println("Attempti	ng : l=" + l + ",r=" + r);

			updateInfluenceLocationCount(nodes, allEdges, allCheckIns, l, r);
			System.out.println("Inserted : l=" + l + ",r=" + r);
			l = r;
			r = r + count;
		}
		if (l < n) {
			System.out.println("Attempting : l=" + l + ",r=" + n);
			updateInfluenceLocationCount(nodes, allEdges, allCheckIns, l, r);
			System.out.println("Inserted : l=" + l + ",r=" + n);
		}
	}

	private void updateInfluenceLocationCount(List<Integer> nodes, Map<Integer, List<Integer>> allEdges,
			Map<Integer, List<CheckIn>> allCheckIns, int l, int r) {
		NodeStat obj;
		List<NodeStat> list = new ArrayList<NodeStat>();

		for (int i = l; i < r; i++) {
			obj = new NodeStat(nodes.get(i));
			updateInfluenceLocationCount(obj, allEdges, allCheckIns);
			// System.out.println(obj);
			list.add(obj);
		}

		dbHelper.updateNodeStatsInfLocationCount(list);
	}

	private void updateInfluenceLocationCount(NodeStat node, Map<Integer, List<Integer>> allEdges,
			Map<Integer, List<CheckIn>> allCheckIns) {
		int location_count = 0;

		boolean flag = false;
		if (allCheckIns.containsKey(node.getId())) {
			for (CheckIn c : allCheckIns.get(node.getId())) {
				flag = false;
				for (Integer edge : allEdges.get(node.getId())) {
					if (flag)
						break;
					if (allCheckIns.containsKey(edge)) {
						for (CheckIn c2 : allCheckIns.get(edge)) {
							if (flag)
								break;
							if (dbHelper.toVisitedAfterFrom(c, c2)) {
								flag = true;
							}
						}
					}
				}
				if (flag)
					location_count++;
			}
		}
		node.setInfLocationCount(location_count);
	}

}
