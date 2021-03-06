/**
 * 
 */
package com.wcu.inf.gowalla;

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
 * @author Raj This class is used to insert data in database
 */
public class DbInsertMain {

	java.sql.Connection con = DbConnection.getConnection();

	/**
	 * This method is used to insert nodes in db
	 * 
	 * @param nodes
	 */
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

	/**
	 * This method is used to insert edges in batches
	 * 
	 * @param list
	 */
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

	/**
	 * This is an utility to method to insert edges in db in batches
	 * 
	 * @param edges
	 * @param l
	 * @param r
	 */
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

	/**
	 * This method is used to insert edges in batches
	 * 
	 * @param locations
	 */
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

	/**
	 * This is an utility to method to insert locations in db in batches
	 * 
	 * @param list
	 * @param l
	 * @param r
	 */
	public void insertLocations(List<Location> list, int l, int r) {
		try {
			java.sql.Statement statement = con.createStatement();
			con.setAutoCommit(false);

			for (int i = l; i < r; i++) {
				Location ob = list.get(i);
				statement.addBatch("insert into location(id,latitude,longitude) values (" + ob.getId() + ","
						+ ob.getLatitude() + "," + ob.getLongitude() + ");");
			}
			statement.executeBatch();
			con.commit();
			System.out.println("Records are inserted into table from " + l + ", to " + r);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to insert check-ins in batches
	 * 
	 * @param list
	 */
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

	/**
	 * This is an utility method used to insert check-ins in batches
	 * 
	 * @param list
	 * @param l
	 * @param r
	 */
	public void insertCheckIns(List<CheckIn> list, int l, int r) {
		try {
			java.sql.Statement statement = con.createStatement();
			con.setAutoCommit(false);

			for (int i = l; i < r; i++) {
				CheckIn ob = list.get(i);
				statement.addBatch("insert into checkins(NodeId,LocationId,time) values (" + ob.getNodeId() + ","
						+ ob.getLocationId() + ",'" + ob.getDatetime() + "');");
			}
			statement.executeBatch();
			con.commit();
			System.out.println("Records are inserted into table from " + l + ", to " + r);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

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
		List<CheckIn> list = FilesReader.getCheckInsFromFile();
		// obj.insertCheckInsInBatches(list);
		Map<Integer, List<CheckIn>> map = FilesReader.getCheckInsByNodeIdFromFile();
		System.out.println(map.get(376).size());
	}

}
