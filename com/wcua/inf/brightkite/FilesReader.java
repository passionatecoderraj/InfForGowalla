package com.wcua.inf.brightkite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wcu.inf.gowalla.beans.CheckIn;
import com.wcu.inf.gowalla.beans.Edge;
import com.wcu.inf.gowalla.beans.Location;

/**
 * @author Raj
 *
 */
public class FilesReader {

	private final static String FILEPATH = "C:\\raj\\study\\univ\\research\\data\\brightkite\\";

	public static List<Edge> getEdgesFromFile() {

		List<Edge> edges = new ArrayList<Edge>();

		try {
			BufferedReader br = new BufferedReader(new FileReader(FILEPATH + "Brightkite_edges.txt"));
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

		System.out.println(edges.size() / 2);
		return edges;
	}

	public static Map<Integer, Integer> getCheckins() {

		Map<Integer, Integer> map = new HashMap<>();

		List<CheckIn> list = new ArrayList<CheckIn>();
		CheckIn obj;
		try {
			BufferedReader br = new BufferedReader(new FileReader(FILEPATH + "Brightkite_totalCheckins.txt"));
			if (br != null) {
				System.out.println("Reading for CheckIns");
			}

			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {

				String[] a = sCurrentLine.split("	");
				map.compute(Integer.parseInt(a[0]), (key, value) -> {
					if (value != null)
						return value + 1;
					return 1;
				});
			}
		} catch (IOException e) {
			System.out.println("Unknown error occured while loading file");
			e.printStackTrace();
		}

		System.out.println(map.size());
		return map;
	}

	public static void main(String args[]) {
		getNumberOfCheckins(getCheckins());
	}

	public static Set<Integer> getNodes() {
		Set<Integer> nodes = new HashSet<>();
		for (Edge e : getEdgesFromFile()) {
			nodes.add(e.getFrom());
		}
		return nodes;
	}

	public static void getNumberOfCheckins(Map<Integer, Integer> map) {
		List<Integer> values = new ArrayList<>();
		long sum = 0;
		for (int val : map.values()) {
			values.add(val);
			sum += val;
		}
		Collections.sort(values);
		System.out.println("Edges : " + map.size());
		System.out.println("Mean:" + (sum / values.size()));
		System.out.println("Max : " + Collections.max(values));
		System.out.println("Min : " + Collections.min(values));
		System.out.println("Median : " + values.get(values.size() / 2));
	}

	public static void getNumberOfNodes(List<Edge> edges) {
		Set<Integer> nodes = new HashSet<>();
		for (Edge e : edges) {
			nodes.add(e.getFrom());
		}
		Map<Integer, Integer> countMap = new HashMap<>();
		for (Edge e : edges) {
			countMap.compute(e.getFrom(), (key, value) -> {
				if (value != null)
					return value + 1;
				return 1;
			});
		}
		List<Integer> values = new ArrayList<>();
		int sum = 0;
		for (int val : countMap.values()) {
			values.add(val);
			sum += val;
		}
		Collections.sort(values);
		System.out.println("Edges : " + edges.size());
		System.out.println("Mean:" + (sum / values.size()));
		System.out.println("Max : " + Collections.max(values));
		System.out.println("Min : " + Collections.min(values));
		System.out.println("Median : " + values.get(values.size() / 2));
	}

	public static List<CheckIn> getCheckInsFromFile() {

		List<CheckIn> list = new ArrayList<CheckIn>();

		try {
			BufferedReader br = new BufferedReader(new FileReader(FILEPATH + "Brightkite_totalCheckins.txt"));
			if (br != null) {
				System.out.println("Reading for CheckIns");
			}
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				String[] a = sCurrentLine.split("	");
				if (a.length < 5)
					continue;
				list.add(new CheckIn(Integer.parseInt(a[0]), a[4], convert(a[1])));
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

	public static Set<Location> getLocationsFromFile() {

		Set<Location> locations = new HashSet<Location>();

		try {
			BufferedReader br = new BufferedReader(new FileReader(FILEPATH + "Brightkite_totalCheckins.txt"));
			if (br != null) {
				System.out.println("Reading for locations");
			}
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				String[] a = sCurrentLine.split("	");
				if (a.length < 5)
					continue;
				locations.add(new Location(a[4], Double.parseDouble(a[2]), Double.parseDouble(a[3])));
			}
		} catch (IOException e) {
			System.out.println("Unknown error occured while loading file");
			e.printStackTrace();
		}

		System.out.println(locations.size());
		return locations;
	}

	public static Map<Integer, List<CheckIn>> getCheckInsByNodeIdFromFile() {
		Map<Integer, List<CheckIn>> map = new HashMap<>();

		List<CheckIn> list = new ArrayList<CheckIn>();
		CheckIn obj;
		try {
			BufferedReader br = new BufferedReader(new FileReader(FILEPATH + "Brightkite_totalCheckins.txt"));
			if (br != null) {
				System.out.println("Reading for CheckIns");
			}
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				String[] a = sCurrentLine.split("	");
				if (a.length < 5)
					continue;
				obj = new CheckIn(Integer.parseInt(a[0]), a[4], convert(a[1]));
				if (map.containsKey(obj.getNodeId())) {
					map.get(obj.getNodeId()).add(obj);
				} else {
					list = new ArrayList<>();
					list.add(obj);
					map.put(obj.getNodeId(), list);
				}
			}
		} catch (IOException e) {
			System.out.println("Unknown error occured while loading file");
			e.printStackTrace();
		}

		System.out.println(map.size());
		return map;
	}
}
