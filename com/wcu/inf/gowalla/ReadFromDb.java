/**
 * 
 */
package com.wcu.inf.gowalla;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wcu.inf.gowalla.beans.CheckIn;
import com.wcu.inf.gowalla.beans.Edge;
import com.wcu.inf.gowalla.beans.Location;
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
		List<Edge> edges = obj.readEdgesByNodeId();
		List<CheckIn> list = obj.readCheckInsOfAllEdges(edges);

		obj.foc(edges, list);
	}

	public void foc(List<Edge> edges, List<CheckIn> list) {
		int count = 0;
		for (Edge e : edges) {
			for (CheckIn c : list) {
				if (visitTime(e.getFrom(), c) < visitTime(e.getTo(), c)) {
					count++;
				}
			}
		}
	}

	public List<CheckIn> readCheckInsOfAllEdges(List<Edge> edges) {
		List<CheckIn> list = new ArrayList<>();
		try {
			java.sql.Statement statement = con.createStatement();
			String qry = null;

			for (Edge e : edges) {
				qry = "select nodeid,locationid,time from checkins where NodeId=" + e.getTo();
				ResultSet rs = statement.executeQuery(qry);
				while (rs.next()) {
					list.add(new CheckIn(rs.getInt("nodeid"), rs.getInt("locationid"), rs.getString("time")));
				}
			}
			qry = "select nodeid,locationid,time from checkins where NodeId=" + edges.get(0).getFrom();
			ResultSet rs = statement.executeQuery(qry);
			while (rs.next()) {
				list.add(new CheckIn(rs.getInt("nodeid"), rs.getInt("locationid"), rs.getString("time")));
			}

			System.out.println(list.size());
		} catch (

		SQLException e)

		{
			e.printStackTrace();
		}
		return list;

	}

	public List<Edge> readEdgesByNodeId() {
		int id = 376;
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

}
