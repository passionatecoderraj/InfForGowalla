/**
 * 
 */
package com.wcu.inf.gowalla;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import com.wcu.inf.gowalla.beans.CheckIn;
import com.wcu.inf.gowalla.beans.Edge;
import com.wcu.inf.gowalla.beans.Location;
import com.wcu.inf.util.DbConnection;

/**
 * @author Raj
 *
 */
public class CsvGenerator {

	java.sql.Connection con = DbConnection.getConnection();

	private final static String FILEPATH = "C:\\raj\\study\\univ\\research\\data\\gowalla\\";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CsvGenerator obj = new CsvGenerator();
		// Set<Integer> nodes = FilesReader.getNodesFromFile();
		// obj.generateNodes(nodes);
		// List<Edge> edges = FilesReader.getEdgesFromFile();
		// obj.generateEdges(edges);
		Set<Location> locations = FilesReader.getLocationsFromFile();
		// obj.generateLocations(locations);
		// List<CheckIn> list = FilesReader.getCheckInsFromFile();
		// obj.generateCheckIns(list);
	}

	private void generateNodes(Set<Integer> nodes) {
		try {
			FileWriter writer = new FileWriter(FILEPATH + "nodes.csv");
			writer.append("NodeId");
			writer.append('\n');

			for (int id : nodes) {
				writer.append(Integer.toString(id));
				writer.append('\n');
			}

			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void generateEdges(List<Edge> edges) {
		try {
			FileWriter writer = new FileWriter(FILEPATH + "edges.csv");
			writer.append("FkFromNodeId");
			writer.append(',');
			writer.append("FkToNodeId");
			writer.append('\n');

			for (Edge ob : edges) {
				writer.append(Integer.toString(ob.getFrom()));
				writer.append(',');
				writer.append(Integer.toString(ob.getTo()));
				writer.append('\n');
			}

			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void generateLocations(Set<Location> locations) {
		try {
			FileWriter writer = new FileWriter(FILEPATH + "locations.csv");
			writer.append("id");
			writer.append(',');
			writer.append("latitude");
			writer.append(',');
			writer.append("longitude");
			writer.append('\n');

			for (Location ob : locations) {
				writer.append(Integer.toString(ob.getId()));
				writer.append(',');
				writer.append(Double.toString(ob.getLatitude()));
				writer.append(',');
				writer.append(Double.toString(ob.getLongitude()));
				writer.append('\n');
			}

			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void generateCheckIns(List<CheckIn> list) {
		try {
			FileWriter writer = new FileWriter(FILEPATH + "checkins.csv");
			writer.append("FkNodeId");
			writer.append(',');
			writer.append("FkLocationId");
			writer.append(',');
			writer.append("time");
			writer.append('\n');

			for (CheckIn ob : list) {
				writer.append(Integer.toString(ob.getNodeId()));
				writer.append(',');
				writer.append(Integer.toString(ob.getLocationId()));
				writer.append(',');
				writer.append(ob.getDatetime());
				writer.append('\n');
			}

			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void insertCheckIns(List<CheckIn> list) {
		try {
			java.sql.Statement statement = con.createStatement();
			con.setAutoCommit(false);

			for (CheckIn ob : list) {
				statement.addBatch("insert into location(FkNodeId,FkLocationId,day) values (" + ob.getNodeId() + ","
						+ ob.getLocationId() + "," + ob.getDatetime() + ");");
			}
			statement.executeBatch();
			con.commit();
			System.out.println("Records are inserted into table!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
