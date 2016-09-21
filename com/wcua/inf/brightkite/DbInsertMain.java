/**
 * 
 */
package com.wcua.inf.brightkite;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wcu.inf.gowalla.beans.CheckIn;
import com.wcu.inf.gowalla.beans.Edge;
import com.wcu.inf.gowalla.beans.Location;
import com.wcu.inf.util.DbConnection;

/**
 * @author Raj
 *
 */
public class DbInsertMain {

	java.sql.Connection con = DbConnection.getConnection();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DbInsertMain obj = new DbInsertMain();
		// Set<Integer> nodes = FilesReader.getNodesFromFile();
		// obj.insertNodes(nodes);
		// List<Edge> edges = FilesReader.getEdgesFromFile();
		// obj.insertEdgesInBatches(edges);
		// Set<Location> locations = FilesReader.getLocationsFromFile();
		// obj.insertLocationsInBatches(locations);
		// List<CheckIn> list = FilesReader.getCheckInsFromFile();
		// obj.insertCheckInsInBatches(list);
		// Map<Integer, List<CheckIn>> map =
		// FilesReader.getCheckInsByNodeIdFromFile();
		// System.out.println(map.get(376).size());
		// obj.insertNodes(FilesReader.getNodes());
		// obj.insertEdgesInBatches(FilesReader.getEdgesFromFile());
		// FilesReader.getLocationsFromFile();
		// obj.insertLocationsInBatches(FilesReader.getLocationsFromFile());
		// FilesReader.getCheckInsFromFile();
		obj.insertCheckInsInBatches(FilesReader.getCheckInsFromFile());
	}

	public void insertNodes(Set<Integer> nodes) {
		try {
			java.sql.Statement statement = con.createStatement();
			con.setAutoCommit(false);

			for (int id : nodes) {
				statement.addBatch("insert into node(id) values (" + id + ");");
			}
			statement.executeBatch();
			con.commit();
			System.out.println("Records are inserted into table!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertEdgesInBatches(List<Edge> list) {
		int count = 100000;
		int l, r, n = list.size();
		l = 0;
		r = l + count;
		while (r < n) {
			System.out.println("l=" + l + ",r=" + r);
			insertEdges(list, l, r);
			l = r;
			r = r + count;
		}
		if (l < n) {
			System.out.println("l=" + l + ",n=" + n);
			insertEdges(list, l, n);
		}
	}

	public void insertEdges(List<Edge> edges, int l, int r) {
		try {
			java.sql.Statement statement = con.createStatement();
			con.setAutoCommit(false);

			for (int i = l; i < r; i++) {
				Edge ob = edges.get(i);
				statement.addBatch(
						"insert into edge(fromnodeid,tonodeid) values (" + ob.getFrom() + "," + ob.getTo() + ");");
			}
			statement.executeBatch();
			con.commit();
			System.out.println("Records are inserted into table from " + l + ", to " + r);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertLocationsInBatches(Set<Location> locations) {
		List<Location> list = new ArrayList<Location>();
		for (Location ob : locations) {
			list.add(ob);
		}
		int count = 100000;
		int l, r, n = list.size();
		l = 0;
		r = l + count;
		while (r < n) {
			System.out.println("l=" + l + ",r=" + r);
			insertLocations(list, l, r);
			l = r;
			r = r + count;
		}
		if (l < n) {
			System.out.println("l=" + l + ",n=" + n);
			insertLocations(list, l, n);
		}
	}

	public void insertLocations(List<Location> list, int l, int r) {
		try {
			java.sql.Statement statement = con.createStatement();
			con.setAutoCommit(false);

			for (int i = l; i < r; i++) {
				Location ob = list.get(i);
				String st = "insert into location(id,latitude,longitude) values ('" + ob.getLocation() + "',"
						+ ob.getLatitude() + "," + ob.getLongitude() + ");";
				statement.addBatch(st);
			}
			statement.executeBatch();
			con.commit();
			System.out.println("Records are inserted into table from " + l + ", to " + r);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertCheckInsInBatches(List<CheckIn> list) {
		int count = 100000;
		int l, r, n = list.size();
		l = 0;
		r = l + count;
		while (r < n) {
			System.out.println("l=" + l + ",r=" + r);
			insertCheckIns(list, l, r);
			l = r;
			r = r + count;
		}
		if (l < n) {
			System.out.println("l=" + l + ",n=" + n);
			insertCheckIns(list, l, n);
		}
	}

	public void insertCheckIns(List<CheckIn> list, int l, int r) {
		try {
			java.sql.Statement statement = con.createStatement();
			con.setAutoCommit(false);

			for (int i = l; i < r; i++) {
				CheckIn ob = list.get(i);
				statement.addBatch("insert into checkins(NodeId,LocationId,time) values (" + ob.getNodeId() + ",'"
						+ ob.getLocation() + "','" + ob.getDatetime() + "');");
			}	
			statement.executeBatch();
			con.commit();
			System.out.println("Records are inserted into table from " + l + ", to " + r);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
