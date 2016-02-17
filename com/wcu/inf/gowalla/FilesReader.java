package com.wcu.inf.gowalla;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.wcu.inf.gowalla.beans.CheckIn;
import com.wcu.inf.gowalla.beans.Edge;
import com.wcu.inf.gowalla.beans.Location;

/**
 * @author Raj
 *
 */
public class FilesReader {

	private final static String FILEPATH = "C:\\raj\\study\\univ\\research\\data\\gowalla\\";

	public static Set<Integer> getNodesFromFile() {

		Set<Integer> nodes = new HashSet<Integer>();

		try {
			BufferedReader br = new BufferedReader(new FileReader(FILEPATH + "Gowalla_edges.txt"));
			if (br != null) {
				System.out.println("Reading for nodes");
			}
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				String[] a = sCurrentLine.split("	");
				for (int i = 0; i < a.length; i++)
					nodes.add(Integer.parseInt(a[i]));
			}
		} catch (IOException e) {
			System.out.println("Unknown error occured while loading file");
			e.printStackTrace();
		}

		System.out.println(nodes.size());
		return nodes;
	}

	public static List<Edge> getEdgesFromFile() {

		List<Edge> edges = new ArrayList<Edge>();

		try {
			BufferedReader br = new BufferedReader(new FileReader(FILEPATH + "Gowalla_edges.txt"));
			if (br != null) {
				System.out.println("Reading for edges");
			}
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				String[] a = sCurrentLine.split("	");
				edges.add(new Edge(Integer.parseInt(a[0]), Integer.parseInt(a[1])));
			}
		} catch (IOException e) {
			System.out.println("Unknown error occured while loading file");
			e.printStackTrace();
		}

		System.out.println(edges.size());
		return edges;
	}

	public static Set<Location> getLocationsFromFile() {

		Set<Location> locations = new HashSet<Location>();

		try {
			BufferedReader br = new BufferedReader(new FileReader(FILEPATH + "Gowalla_totalCheckins.txt"));
			if (br != null) {
				System.out.println("Reading for locations");
			}
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				String[] a = sCurrentLine.split("	");
				locations.add(new Location(Integer.parseInt(a[4]), Double.parseDouble(a[2]), Double.parseDouble(a[3])));
			}
		} catch (IOException e) {
			System.out.println("Unknown error occured while loading file");
			e.printStackTrace();
		}

		System.out.println(locations.size());
		return locations;
	}

	public static List<CheckIn> getCheckInsFromFile() {

		List<CheckIn> list = new ArrayList<CheckIn>();

		try {
			BufferedReader br = new BufferedReader(new FileReader(FILEPATH + "Gowalla_totalCheckins.txt"));
			if (br != null) {
				System.out.println("Reading for CheckIns");
			}
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				String[] a = sCurrentLine.split("	");
				list.add(new CheckIn(Integer.parseInt(a[0]), Integer.parseInt(a[4]), convert(a[1])));
			}
		} catch (IOException e) {
			System.out.println("Unknown error occured while loading file");
			e.printStackTrace();
		}

		System.out.println(list.size());
		return list;
	}

	public static String convert(String str) {
		StringBuffer sb = new StringBuffer();
		sb.append(str);
		sb.replace(10, 11, " ");
		return sb.substring(0, sb.length() - 1);
	}

}
