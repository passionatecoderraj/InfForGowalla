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
import java.util.List;
import java.util.Map;

import com.wcu.inf.gowalla.beans.CheckIn;
import com.wcu.inf.gowalla.beans.Edge;
import com.wcu.inf.gowalla.beans.Node;
import com.wcu.inf.util.DbConnection;

/**
 * @author Raj
 *
 */
public class ReadFromDb {

	java.sql.Connection con = DbConnection.getConnection();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ReadFromDb obj = new ReadFromDb();

		int nodeid = 148;

		List<Edge> edges = obj.getEdgesByNodeId(nodeid);

		List<Integer> ids = new ArrayList<>();
		for (Edge e : edges) {
			ids.add(e.getTo());
		}

		List<CheckIn> checkinsOfEdges = obj.getCheckInsForNodeIds(ids);
		List<CheckIn> checkInsOfNode = obj.getCheckInsForNodeId(nodeid);
		obj.foc(checkInsOfNode, edges, checkinsOfEdges);
	}

	public void foc(List<CheckIn> checkInsOfNode, List<Edge> edges, List<CheckIn> checkinsOfEdges) {
		int friends_count = 0;
		int count = 0;
		int location_count = 0;
		
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
					count++;
					loc_count++;
					if (count < 10)
						System.out.println(c + ", " + c2);
					flag = true;
				}
			}

			maxLocCount = Math.max(maxLocCount, loc_count);
			if (flag) {
				friends_count++;
			}
		}

		System.out.println(maxLocCount);
		System.out.println(count);
		System.out.println(friends_count + "(" + checkinsOfEdges.size() + ")");
		System.out.println(friends_count + "(" + edges.size() + ")");
	}

	public CheckIn getCheckInById(List<CheckIn> list, int loc_id, int id) {
		CheckIn obj = null;
		for (CheckIn c : list) {
			if (c.getLocationId() == loc_id && c.getNodeId() == id)
				return c;
		}

		return obj;
	}

	private boolean toVisitedAfterFrom(CheckIn from, CheckIn to) {
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
			System.out.println(list.size());
		} catch (

		SQLException e)

		{
			e.printStackTrace();
		}
		return list;

	}

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

}
