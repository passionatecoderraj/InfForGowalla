/**
 * 
 */
package com.wcu.inf.gowalla;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wcu.inf.gowalla.beans.CheckIn;
import com.wcu.inf.gowalla.beans.Edge;
import com.wcu.inf.gowalla.beans.Node;
import com.wcu.inf.gowalla.beans.NodeStat;
import com.wcu.inf.util.DbConnection;

/**
 * @author Raj
 * 
 *         This class is used to read data from database
 */
public class ReadFromDb {

	java.sql.Connection con = DbConnection.getConnection();

	/**
	 * This method is used to get check-in of a location with id 'loc_id and
	 * node id of 'id'
	 * 
	 * @param list
	 * @param loc_id
	 * @param id
	 * @return
	 */
	public CheckIn getCheckInById(List<CheckIn> list, int loc_id, int id) {
		CheckIn obj = null;
		for (CheckIn c : list) {
			if (c.getLocationId() == loc_id && c.getNodeId() == id)
				return c;
		}

		return obj;
	}

	/**
	 * @param from
	 * @param to
	 * @return
	 * 
	 * 		This method returns true if check-in 'to' occurred after check-in
	 *         'from'
	 */
	public boolean toVisitedAfterFrom(CheckIn from, CheckIn to) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		boolean flag = false;
		try {
			Date d1 = df.parse(from.getDatetime());
			Date d2 = df.parse(to.getDatetime());
			if (d1.compareTo(d2) < 0) {
				flag = true;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return flag;
	}

	/**
	 * @param ids
	 * @return
	 * 
	 * 		This method returns the check-ins for list of ids
	 */
	public List<CheckIn> getCheckInsForNodeIds(List<Integer> ids) {
		List<CheckIn> list = new ArrayList<>();
		try {
			java.sql.Statement statement = con.createStatement();
			String qry = null;

			for (int id : ids) {
				qry = "select nodeid,locationid,time from checkins where NodeId=" + id;
				ResultSet rs = statement.executeQuery(qry);
				while (rs.next()) {
					list.add(new CheckIn(rs.getInt("nodeid"), rs.getInt("locationid"), rs.getString("time")));
				}
			}
			// System.out.println(list.size());
		} catch (

		SQLException e)

		{
			e.printStackTrace();
		}
		return list;

	}

	/**
	 * @return This method returns all node to edges map
	 */
	public Map<Integer, List<Integer>> getAllEdgesInMap() {
		Map<Integer, List<Integer>> map = new HashMap<>();
		int from, to;
		List<Integer> list;
		try {
			java.sql.Statement statement = con.createStatement();

			String qry = "select fromnodeid,tonodeid from edge order by id";
			ResultSet rs = statement.executeQuery(qry);

			while (rs.next()) {
				from = rs.getInt("fromnodeid");
				to = rs.getInt("tonodeid");
				if (map.containsKey(from)) {
					map.get(from).add(to);
				} else {
					list = new ArrayList<>();
					list.add(to);
					map.put(from, list);
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;

	}

	/**
	 * @return
	 * 
	 * 		This method returns the all check-ins of all nodes
	 */
	public Map<Integer, List<CheckIn>> getAllCheckins() {
		Map<Integer, List<CheckIn>> map = new HashMap<>();
		CheckIn obj;
		List<CheckIn> list;
		try {
			java.sql.Statement statement = con.createStatement();
			String qry = null;

			qry = "select nodeid,locationid,time from checkins";
			ResultSet rs = statement.executeQuery(qry);
			while (rs.next()) {
				obj = new CheckIn(rs.getInt("nodeid"), rs.getInt("locationid"), rs.getString("time"));
				if (map.containsKey(obj.getNodeId())) {
					map.get(obj.getNodeId()).add(obj);
				} else {
					list = new ArrayList<>();
					list.add(obj);
					map.put(obj.getNodeId(), list);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;

	}

	/**
	 * @param nodeid
	 * @return This method returns the check-ins of a nodeid
	 */
	public List<CheckIn> getCheckInsForNodeId(int nodeid) {
		List<CheckIn> list = new ArrayList<>();
		try {
			java.sql.Statement statement = con.createStatement();
			String qry = null;

			qry = "select nodeid,locationid,time from checkins where NodeId=" + nodeid;
			ResultSet rs = statement.executeQuery(qry);
			while (rs.next()) {
				list.add(new CheckIn(rs.getInt("nodeid"), rs.getInt("locationid"), rs.getString("time")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;

	}

	/**
	 * @param id
	 * @return This method returns the edge of a node id 'id'
	 */
	public List<Edge> getEdgesByNodeId(int id) {
		List<Edge> edges = new ArrayList<>();
		try {
			java.sql.Statement statement = con.createStatement();
			String qry = "select tonodeid from edge where fromnodeid =" + id;
			ResultSet rs = statement.executeQuery(qry);
			while (rs.next()) {
				edges.add(new Edge(id, rs.getInt("tonodeid")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return edges;
	}

	/**
	 * @return This method returns all edge in a db
	 */
	public List<Edge> getAllEdges() {
		List<Edge> edges = new ArrayList<>();
		try {
			java.sql.Statement statement = con.createStatement();
			String qry = "select fromnodeid,tonodeid from edge";
			ResultSet rs = statement.executeQuery(qry);
			while (rs.next()) {
				edges.add(new Edge(rs.getInt("fromnodeid"), rs.getInt("tonodeid")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return edges;
	}

	/**
	 * This method returns the all edge count for all nodes
	 * 
	 * @return
	 */
	public Map<Integer, Integer> getAllEdgeCountForAllNodes() {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		try {
			java.sql.Statement statement = con.createStatement();
			String qry = "select fromnodeid,count(tonodeid) as EdgeCount from edge group by fromnodeid";
			ResultSet rs = statement.executeQuery(qry);
			while (rs.next()) {
				map.put(rs.getInt("fromnodeid"), rs.getInt("fromnodeid"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return map;
	}

	/**
	 * This method returns all node ids in a list
	 * 
	 * @return
	 */
	public List<Integer> getAllNodes() {
		List<Integer> nodes = new ArrayList<>();
		try {
			java.sql.Statement statement = con.createStatement();
			String qry = "select id from node";
			ResultSet rs = statement.executeQuery(qry);
			while (rs.next()) {
				nodes.add(rs.getInt("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nodes;
	}

	/**
	 * This method returns the location count by node id
	 * 
	 * @return
	 */
	public List<CheckIn> getLocationsCountByNodeId() {
		List<CheckIn> list = new ArrayList<>();
		try {
			java.sql.Statement statement = con.createStatement();
			String qry = "select nodeid,count(LocationId) as LocationCount from checkins group by nodeid";
			ResultSet rs = statement.executeQuery(qry);
			while (rs.next()) {
				list.add(new CheckIn(rs.getInt("nodeid"), rs.getInt("LocationCount")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * This method updates nodestats edge of edge count by node id
	 * 
	 * @param map
	 */
	public void updateNodeStatsEdgeOfEdgeCountByNodeId(Map<Node, List<Edge>> map) {
		try {
			java.sql.Statement statement = con.createStatement();
			con.setAutoCommit(false);

			for (Node c : map.keySet()) {
				statement.addBatch("update nodestats set EdgeOfEdgeCount=" + c.getEdgeOfEdgeCount() + " where NodeId="
						+ c.getId());
			}
			statement.executeBatch();
			con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	/**
	 * This method updates nodestats location count by node id
	 * 
	 * @param list
	 */
	public void updateNodeStatsLocationCountByNodeId(List<CheckIn> list) {
		try {
			java.sql.Statement statement = con.createStatement();
			con.setAutoCommit(false);

			for (CheckIn c : list) {
				statement.addBatch("update nodestats set LocationCount=" + c.getLocationCount() + " where NodeId="
						+ c.getNodeId());
			}
			statement.executeBatch();
			con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	/**
	 * This method returns all NodeStats Sorted by Type
	 * 
	 * @param type
	 * @return
	 */
	public List<NodeStat> getAllNodeStatsBySortedType(Type type) {
		List<NodeStat> list = new ArrayList<>();
		String sortBy = "edgecount";
		if (Type.EdgeCount.value == type.value) {
			sortBy = "edgecount";
		} else if (Type.InfluenceEdgeCount.value == type.value) {
			sortBy = "LocationCount";
		} else if (Type.LocationCount.value == type.value) {
			sortBy = "InfEdgeCount";
		} else if (Type.InfluenceLocationCount.value == type.value) {
			sortBy = "InfLocCount";
		}
		try {
			java.sql.Statement statement = con.createStatement();
			String qry = "select nodeid,edgecount,EdgeOfEdgeCount,LocationCount,InfEdgeCount,InfLocCount,InfEdgePercent,InfLocPercent from nodestats order by "
					+ sortBy + ";";

			ResultSet rs = statement.executeQuery(qry);
			while (rs.next()) {
				list.add(new NodeStat(rs.getInt("nodeid"), rs.getInt("edgecount"), rs.getInt("EdgeOfEdgeCount"),
						rs.getInt("LocationCount"), rs.getInt("InfEdgeCount"), rs.getInt("InfLocCount"),
						rs.getDouble("InfEdgePercent"), rs.getDouble("InfLocPercent")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * This method returns Derviced NodeStats by Edge Count
	 * 
	 * @return
	 */
	public List<NodeStat> getAllDerivedNodeStatsBySortedEdgeCount() {
		List<NodeStat> list = new ArrayList<>();

		try {
			java.sql.Statement statement = con.createStatement();
			String qry = "select nodeid,edgecount,EdgeOfEdgeCount,LocationCount,InfEdgeCount,InfLocCount,InfEdgePercent,InfLocPercent from derivednodestats where order by edgecount;";

			ResultSet rs = statement.executeQuery(qry);
			while (rs.next()) {
				list.add(new NodeStat(rs.getInt("nodeid"), rs.getInt("edgecount"), rs.getInt("EdgeOfEdgeCount"),
						rs.getInt("LocationCount"), rs.getInt("InfEdgeCount"), rs.getInt("InfLocCount"),
						rs.getDouble("InfEdgePercent"), rs.getDouble("InfLocPercent")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * This method returns All node stats sorted by edge count
	 * 
	 * @return
	 */
	public List<NodeStat> getAllNodeStatsBySortedEdgeCount() {
		List<NodeStat> list = new ArrayList<>();

		try {
			java.sql.Statement statement = con.createStatement();
			String qry = "select nodeid,edgecount,EdgeOfEdgeCount,LocationCount,InfEdgeCount,InfLocCount,InfEdgePercent,InfLocPercent from nodestats where EdgeCount<=83 and LocationCount>0 order by edgecount;";

			ResultSet rs = statement.executeQuery(qry);
			while (rs.next()) {
				list.add(new NodeStat(rs.getInt("nodeid"), rs.getInt("edgecount"), rs.getInt("EdgeOfEdgeCount"),
						rs.getInt("LocationCount"), rs.getInt("InfEdgeCount"), rs.getInt("InfLocCount"),
						rs.getDouble("InfEdgePercent"), rs.getDouble("InfLocPercent")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * This method returns all nodestats sorted by location count
	 * 
	 * @return
	 */
	public List<NodeStat> getAllNodeStatsBySortedLocationCount() {
		List<NodeStat> list = new ArrayList<>();

		try {
			java.sql.Statement statement = con.createStatement();
			String qry = "select nodeid,edgecount,EdgeOfEdgeCount,LocationCount,InfEdgeCount,InfLocCount,InfEdgePercent,InfLocPercent from nodestats where LocationCount<=373 and LocationCount>0 order by edgecount;";

			ResultSet rs = statement.executeQuery(qry);
			while (rs.next()) {
				list.add(new NodeStat(rs.getInt("nodeid"), rs.getInt("edgecount"), rs.getInt("EdgeOfEdgeCount"),
						rs.getInt("LocationCount"), rs.getInt("InfEdgeCount"), rs.getInt("InfLocCount"),
						rs.getDouble("InfEdgePercent"), rs.getDouble("InfLocPercent")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * This method returns all nodestats sorted by influenced edge count
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public List<NodeStat> getAllNodeStatsBySortedInfEdgeCount(int min, int max) {
		List<NodeStat> list = new ArrayList<>();
		try {
			java.sql.Statement statement = con.createStatement();
			String qry = "select nodeid,edgecount,EdgeOfEdgeCount,LocationCount,InfEdgeCount,InfLocCount,InfEdgePercent,InfLocPercent from nodestats where LocationCount>0 and InfEdgeCount<="
					+ max + " and InfEdgeCount>=" + min + " order by InfEdgeCount;";

			ResultSet rs = statement.executeQuery(qry);
			while (rs.next()) {
				list.add(new NodeStat(rs.getInt("nodeid"), rs.getInt("edgecount"), rs.getInt("EdgeOfEdgeCount"),
						rs.getInt("LocationCount"), rs.getInt("InfEdgeCount"), rs.getInt("InfLocCount"),
						rs.getDouble("InfEdgePercent"), rs.getDouble("InfLocPercent")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * This method returns nodestats with min edge count as 'minEdgeCount' and
	 * max edge count as 'maxEdgeCount' min location count as 'minLocationCount'
	 * and max location count as 'maxLocationCount'
	 * 
	 * 
	 * @param minEdgeCount
	 * @param maxEdgeCount
	 * @param minLocationCount
	 * @param maxLocationCount
	 * @return
	 */
	public List<NodeStat> getNodeStats(int minEdgeCount, int maxEdgeCount, int minLocationCount, int maxLocationCount) {
		List<NodeStat> list = new ArrayList<>();
		try {
			java.sql.Statement statement = con.createStatement();
			String qry = "select nodeid,edgecount,EdgeOfEdgeCount,LocationCount,InfEdgeCount,InfLocCount,InfEdgePercent,InfLocPercent from nodestats where LocationCount>0 and edgecount<="
					+ maxEdgeCount + " and edgecount>=" + minEdgeCount + " and LocationCount<=" + maxLocationCount
					+ " and LocationCount>=" + minLocationCount + ";";

			ResultSet rs = statement.executeQuery(qry);
			while (rs.next()) {
				list.add(new NodeStat(rs.getInt("nodeid"), rs.getInt("edgecount"), rs.getInt("EdgeOfEdgeCount"),
						rs.getInt("LocationCount"), rs.getInt("InfEdgeCount"), rs.getInt("InfLocCount"),
						rs.getDouble("InfEdgePercent"), rs.getDouble("InfLocPercent")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * This method returns all nodeStats sorted by Influence Location Count
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public List<NodeStat> getAllNodeStatsBySortedInfLocationCount(int min, int max) {
		List<NodeStat> list = new ArrayList<>();
		try {
			java.sql.Statement statement = con.createStatement();
			String qry = "select nodeid,edgecount,EdgeOfEdgeCount,LocationCount,InfEdgeCount,InfLocCount,InfEdgePercent,InfLocPercent from nodestats where LocationCount>0 and InfEdgeCount<="
					+ max + " and InfEdgeCount>=" + min + " order by LocationCount ";

			ResultSet rs = statement.executeQuery(qry);
			while (rs.next()) {
				list.add(new NodeStat(rs.getInt("nodeid"), rs.getInt("edgecount"), rs.getInt("EdgeOfEdgeCount"),
						rs.getInt("LocationCount"), rs.getInt("InfEdgeCount"), rs.getInt("InfLocCount"),
						rs.getDouble("InfEdgePercent"), rs.getDouble("InfLocPercent")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * This method updates the Derived Node Status rank for given NodeStats list
	 * 
	 * @param list
	 */
	public void updateDerivedNodeStatsRank(List<NodeStat> list) {
		try {
			java.sql.Statement statement = con.createStatement();
			con.setAutoCommit(false);
			String qry = null;
			for (int i = 0; i < list.size(); i++) {
				NodeStat ob = list.get(i);
				qry = "update derivednodestats set edgerank=" + ob.getEdgerank() + ", locrank=" + ob.getLocrank()
						+ " where nodeid=" + ob.getId() + ";";
				statement.addBatch(qry);
			}
			int res[] = statement.executeBatch();
			int count = 0;
			for (int i = 0; i < res.length; i++) {
				if (res[i] > 0)
					count++;
			}
			System.out.println("updated count=" + count);
			System.out.println("-----------");
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to insert or update derived nodestats
	 * 
	 * @param minEdgeCount
	 * @param maxEdgeCount
	 * @param minLocationCount
	 * @param maxLocationCount
	 */
	public void insertOrUpdateDerivedNodeStats(int minEdgeCount, int maxEdgeCount, int minLocationCount,
			int maxLocationCount) {
		List<NodeStat> list = getNodeStats(minEdgeCount, maxEdgeCount, minLocationCount, maxLocationCount);
		try {
			java.sql.Statement statement = con.createStatement();
			con.setAutoCommit(false);
			String qry = null;
			for (int i = 0; i < list.size(); i++) {
				NodeStat ob = list.get(i);
				qry = "insert into derivednodestats(nodeid,edgecount,EdgeOfEdgeCount,LocationCount,InfEdgeCount,InfLocCount,InfEdgePercent,infLocPercent) select "
						+ ob.getId() + "," + ob.getEdgeCount() + "," + ob.getEdgeOfEdgeCount() + ","
						+ ob.getLocationCount() + "," + ob.getInfEdgeCount() + "," + ob.getInfLocationCount() + ","
						+ ob.getInfEdgePercent() + "," + ob.getInfEdgePercent()
						+ " where not exists(select * from derivednodestats where nodeid =" + ob.getId() + ");";
				statement.addBatch(qry);
			}
			int res[] = statement.executeBatch();
			int count = 0;
			for (int i = 0; i < res.length; i++) {
				if (res[i] > 0)
					count++;
			}
			System.out.println("updated count=" + count);
			System.out.println("-----------");
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * this method is used to derive NodeStats
	 * 
	 * @return
	 */
	public List<NodeStat> getAllDerivedNodeStats() {
		List<NodeStat> list = new ArrayList<>();
		try {
			java.sql.Statement statement = con.createStatement();
			String qry = "select nodeid,edgecount,EdgeOfEdgeCount,LocationCount,InfEdgeCount,InfLocCount,InfEdgePercent,InfLocPercent from derivednodestats; ";

			ResultSet rs = statement.executeQuery(qry);
			while (rs.next()) {
				list.add(new NodeStat(rs.getInt("nodeid"), rs.getInt("edgecount"), rs.getInt("EdgeOfEdgeCount"),
						rs.getInt("LocationCount"), rs.getInt("InfEdgeCount"), rs.getInt("InfLocCount"),
						rs.getDouble("InfEdgePercent"), rs.getDouble("InfLocPercent")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * this method is used to insert or update Derive node stats
	 * 
	 * @param list
	 * @param l
	 * @param r
	 */
	public void insertOrUpdateDerivedNodeStats(List<NodeStat> list, int l, int r) {
		try {
			java.sql.Statement statement = con.createStatement();
			con.setAutoCommit(false);
			String qry = null;
			for (int i = l; i <= r; i++) {
				NodeStat ob = list.get(i);
				qry = "insert into derivednodestats(nodeid,edgecount,EdgeOfEdgeCount,LocationCount,InfEdgeCount,InfLocCount,InfEdgePercent,infLocPercent) select "
						+ ob.getId() + "," + ob.getEdgeCount() + "," + ob.getEdgeOfEdgeCount() + ","
						+ ob.getLocationCount() + "," + ob.getInfEdgeCount() + "," + ob.getInfLocationCount() + ","
						+ ob.getInfEdgePercent() + "," + ob.getInfEdgePercent()
						+ " where not exists(select * from derivednodestats where nodeid =" + ob.getId() + ");";
				// System.out.println(qry);
				statement.addBatch(qry);
			}
			int res[] = statement.executeBatch();
			int count = 0;
			for (int i = 0; i < res.length; i++) {
				if (res[i] > 0)
					count++;
			}
			System.out.println("updated count=" + count);
			con.commit();
			System.out.println("Records are inserted/updated into table from " + l + ", to " + r);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to update NodeStats' influenced edge count
	 * 
	 * @param list
	 */
	public void updateNodeStatsInfEdgeCount(List<NodeStat> list) {
		try {
			java.sql.Statement statement = con.createStatement();
			con.setAutoCommit(false);

			for (NodeStat c : list) {
				statement.addBatch(
						"update nodestats set InfEdgeCount=" + c.getInfEdgeCount() + " where NodeId=" + c.getId());
			}
			statement.executeBatch();
			con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to update NodeStats' influenced location count
	 * 
	 * @param list
	 */
	public void updateNodeStatsInfLocationCount(List<NodeStat> list) {
		try {
			java.sql.Statement statement = con.createStatement();
			con.setAutoCommit(false);

			for (NodeStat c : list) {
				statement.addBatch(
						"update nodestats set InfLocCount=" + c.getInfLocationCount() + " where NodeId=" + c.getId());
			}
			statement.executeBatch();
			con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to update NodeStats' influenced edge count and
	 * influenced location count
	 *
	 * @param list
	 */
	public void updateNodeStatsInfEdgeCountAndInfLocationCount(List<NodeStat> list) {
		try {
			java.sql.Statement statement = con.createStatement();
			con.setAutoCommit(false);

			for (NodeStat c : list) {
				statement.addBatch("update nodestats set InfEdgeCount=" + c.getInfEdgeCount() + ",InfLocCount="
						+ c.getInfLocationCount() + " where NodeId=" + c.getId());
			}
			statement.executeBatch();
			con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	/**
	 * @param map
	 *            This method is used to update NodeStats' influenced edge count
	 */
	public void updateNodeStatsInfEdgeCount(Map<Node, List<Edge>> map) {
		try {
			java.sql.Statement statement = con.createStatement();
			con.setAutoCommit(false);

			for (Node c : map.keySet()) {
				statement.addBatch("update nodestats set EdgeOfEdgeCount=" + c.getEdgeOfEdgeCount() + " where NodeId="
						+ c.getId());
			}
			statement.executeBatch();
			con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ReadFromDb obj = new ReadFromDb();

		/**
		 * // int nodeid = 376; int nodeid = 1234;
		 * 
		 * List<Edge> edges = obj.getEdgesByNodeId(nodeid);
		 * 
		 * List<Integer> ids = new ArrayList<>(); for (Edge e : edges) {
		 * ids.add(e.getTo()); }
		 * 
		 * List<CheckIn> checkinsOfEdges = obj.getCheckInsForNodeIds(ids); List
		 * <CheckIn> checkInsOfNode = obj.getCheckInsForNodeId(nodeid);
		 * obj.foc(checkInsOfNode, edges, checkinsOfEdges); //
		 * obj.focMaps(nodeid, edges);
		 * 
		 */
	}

	/**
	 * @param nodeid
	 * @param edges
	 *            This method accepts node id and edges
	 */
	public void focMaps(int nodeid, List<Edge> edges) {
		int friends_count = 0;
		System.out.println(edges.size());

		Set<CheckIn> set = new HashSet<CheckIn>();
		Map<Integer, List<CheckIn>> map = getAllCheckins();
		System.out.println(map.size());

		boolean flag = false;
		for (Edge e : edges) {
			flag = false;
			for (CheckIn c : map.get(nodeid)) {
				for (CheckIn c2 : map.get(e.getTo())) {
					if (null == c2)
						continue;
					if (toVisitedAfterFrom(c, c2)) {
						set.add(c);
						flag = true;
					}
				}
			}

			if (flag) {
				friends_count++;
			}
		}

		System.out.println(set.size() + "(" + map.get(nodeid).size() + ")");
		System.out.println(friends_count + "(" + edges.size() + ")");
	}

	/**
	 * 
	 * @param checkInsOfNode
	 * @param edges
	 * @param checkinsOfEdges
	 */
	public void foc(List<CheckIn> checkInsOfNode, List<Edge> edges, List<CheckIn> checkinsOfEdges) {
		int friends_count = 0;
		int count = 0;
		int location_count = 0;
		Set<CheckIn> set = new HashSet<CheckIn>();
		int loc_count = 0;
		int maxLocCount = 0;
		boolean flag = false;
		for (Edge e : edges) {
			flag = false;
			loc_count = 0;
			for (CheckIn c : checkInsOfNode) {
				CheckIn c2 = getCheckInById(checkinsOfEdges, c.getLocationId(), e.getTo());
				if (null == c2)
					continue;
				if (toVisitedAfterFrom(c, c2)) {
					set.add(c);
					count++;
					loc_count++;
					// if (count < 10)
					// System.out.println(c + ", " + c2);
					flag = true;
				}
			}

			maxLocCount = Math.max(maxLocCount, loc_count);
			if (flag) {
				friends_count++;
			}
		}

		System.out.println(set.size() + "(" + checkInsOfNode.size() + ")");
		// System.out.println(friends_count + "(" + checkinsOfEdges.size() +
		// ")");
		System.out.println(friends_count + "(" + edges.size() + ")");
	}
}
