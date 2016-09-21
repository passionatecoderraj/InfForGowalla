/**
 * 
 */
package com.wcua.inf.brightkite;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.wcu.inf.gowalla.beans.NodeStat;

/**
 * @author Raj
 *
 */
public class FindLeftRightUsingMedian {

	public static void main(String args[]) {
		ReadFromDb dbHelper = new ReadFromDb();
		//insertOrUpdateDerivedNodeStats(dbHelper);
		 assignRankToDerivedNodeStats(dbHelper);
		// List<NodeStat> list = dbHelper.getAllNodeStatsBySortedEdgeCount();
		// findLeftRightUsingMeanAndMedian(list, 0, list.size() - 500,
		// Type.EdgeCount);
		// List<NodeStat> list =
		// dbHelper.getAllNodeStatsBySortedLocationCount();
		// findLeftRightUsingMeanAndMedian(list, 0, list.size() - 1700,
		// Type.LocationCount);
	}

	public static void assignRankToDerivedNodeStats(ReadFromDb dbHelper) {
		List<NodeStat> list = null;
		int n, median, rank;

		list = dbHelper.getAllDerivedNodeStats();
		n = list.size();

		System.out.println(n);
		Collections.sort(list, new Comparator<NodeStat>() {
			@Override
			public int compare(NodeStat o1, NodeStat o2) {
				return o2.getEdgeCount() - o1.getEdgeCount();
			}
		});
		median = median(list, 0, n - 1, Type.EdgeCount);

		Collections.sort(list, new Comparator<NodeStat>() {
			@Override
			public int compare(NodeStat o1, NodeStat o2) {
				double delta = o2.getInfEdgePercent() - o1.getInfEdgePercent();
				if (delta > 0)
					return 1;
				if (delta < 0)
					return -1;
				return 0;

			}
		});
		rank = n / 2;

		for (NodeStat node : list) {
			if (node.getEdgeCount() > median) {
				node.setEdgerank(rank--);
			}
		}

		Collections.sort(list, new Comparator<NodeStat>() {
			@Override
			public int compare(NodeStat o1, NodeStat o2) {
				return o2.getLocationCount() - o1.getLocationCount();
			}
		});
		median = median(list, 0, n - 1, Type.LocationCount);
		Collections.sort(list, new Comparator<NodeStat>() {
			@Override
			public int compare(NodeStat o1, NodeStat o2) {
				double delta = o2.getInfLocationPercent() - o1.getInfLocationPercent();
				if (delta > 0)
					return 1;
				if (delta < 0)
					return -1;
				return 0;
			}
		});
		rank = n / 2;
		for (NodeStat node : list) {
			if (node.getLocationCount() > median) {
				node.setLocrank(rank--);
			}
		}

		Collections.sort(list, new Comparator<NodeStat>() {
			@Override
			public int compare(NodeStat o1, NodeStat o2) {
				if (o2.getEdgerank() == o1.getEdgerank())
					return o2.getLocrank() - o1.getLocrank();
				return o2.getEdgerank() - o1.getEdgerank();
			}
		});

		for (int i = 0; i < 10; i++) {
			System.out.println(list.get(i));
		}
		dbHelper.updateDerivedNodeStatsRank(list);
	}

	static int left, right;

	public static void insertOrUpdateDerivedNodeStats(ReadFromDb dbHelper) {
		List<NodeStat> list = null;
		int n;
		int minEdgeCount = 0, maxEdgeCount = 0, minLocationCount = 0, maxLocationCount = 0;

		left = 0;
		right = 0;
		list = dbHelper.getAllNodeStatsBySortedEdgeCount();
		n = list.size();
		findLeftRightUsingMeanAndMedian(list, 0, n - 1, Type.EdgeCount);
		minEdgeCount = list.get(left).getEdgeCount();
		maxEdgeCount = list.get(right).getEdgeCount();

		left = 0;
		right = 0;
		list = dbHelper.getAllNodeStatsBySortedLocationCount();
		n = list.size();
		findLeftRightUsingMeanAndMedian(list, 0, n - 1, Type.LocationCount);
		minLocationCount = list.get(left).getLocationCount();
		maxLocationCount = list.get(right).getLocationCount();

		System.out.println("me1=" + minEdgeCount + ",me2=" + maxEdgeCount);
		System.out.println("ml1=" + minLocationCount + ",ml2=" + maxLocationCount);

		dbHelper.insertOrUpdateDerivedNodeStats(minEdgeCount, maxEdgeCount, minLocationCount, maxLocationCount);
	}

	public static int findMax(Type type, List<NodeStat> list, int l, int r) {
		int max = Integer.MIN_VALUE;
		if (Type.EdgeCount.value == type.value) {
			for (int i = l; i <= r; i++) {
				max = Math.max(max, list.get(i).getEdgeCount());
			}
		} else if (Type.LocationCount.value == type.value) {
			for (int i = l; i <= r; i++) {
				max = Math.max(max, list.get(i).getLocationCount());
			}
		}
		return max;
	}

	public static void findLeftRightUsingMeanAndMedian(List<NodeStat> list, int l, int r, Type type) {
		while (l <= r) {
			if (r - l <= 8000) {
				System.out.println("Final result is :");
				System.out.println("l=(" + l + ") - " + list.get(l));
				System.out.println("r=(" + r + ") - " + list.get(r));
				System.out.println("n=" + (r - l + 1));
				double median = (double) median(list, l, r, type);
				double mean = mean(list, l, r, type);
				double percent = (mean - median) / median;
				System.out.println("l=" + l + ",r=" + r);
				System.out.println("mean=" + Math.round(mean) + ",median=" + median + ", percent=" + (percent * 100));
				left = l;
				right = r;
				return;
			}
			int m = l + (r - l) / 2;
			double median = (double) median(list, l, r, type);
			double mean = mean(list, l, r, type);
			double percent = (mean - median) / median;
			System.out.println("l=" + l + ",r=" + r);
			System.out.println("mean=" + Math.round(mean) + ",median=" + median + ", percent=" + (percent * 100));

			if (mean > median) {
				l = m;
			} else {
				r = m;
			}
		}
	}

	public static double mean(List<NodeStat> list, int l, int r, Type type) {
		double sum = 0.0, mean = 0.0;
		int n = r - l + 1;
		for (int i = l; i <= r; i++) {
			if (Type.EdgeCount.value == type.value) {
				sum += list.get(i).getEdgeCount();
			} else if (Type.InfluenceEdgeCount.value == type.value) {
				sum += list.get(i).getInfEdgeCount();
			} else if (Type.LocationCount.value == type.value) {
				sum += list.get(i).getLocationCount();
			} else if (Type.InfluenceLocationCount.value == type.value) {
				sum += list.get(i).getInfLocationCount();
			}
		}
		mean = (sum) / n;
		return mean;
	}

	public static int median(List<NodeStat> list, int l, int r, Type type) {
		int n = r - l + 1;
		int k = n / 2;
		if (n % 2 != 0) {
			if (Type.EdgeCount.value == type.value) {
				return list.get(l + k).getEdgeCount();
			} else if (Type.InfluenceEdgeCount.value == type.value) {
				return list.get(l + k).getInfEdgeCount();
			} else if (Type.LocationCount.value == type.value) {
				return list.get(l + k).getLocationCount();
			} else {
				return list.get(l + k).getInfLocationCount();
			}
		} else {
			if (Type.EdgeCount.value == type.value) {
				return (list.get(l + k).getEdgeCount() + list.get(l + k - 1).getEdgeCount()) / 2;
			} else if (Type.InfluenceEdgeCount.value == type.value) {
				return (list.get(l + k).getInfEdgeCount() + list.get(l + k - 1).getInfEdgeCount()) / 2;
			} else if (Type.LocationCount.value == type.value) {
				return (list.get(l + k).getLocationCount() + list.get(l + k - 1).getLocationCount()) / 2;
			} else {
				return (list.get(l + k).getInfLocationCount() + list.get(l + k - 1).getInfLocationCount()) / 2;
			}
		}
	}
}

enum Type {
	EdgeCount(1), LocationCount(2), InfluenceEdgeCount(3), InfluenceLocationCount(4);
	int value;

	private Type(int value) {
		this.value = value;
	}

}
