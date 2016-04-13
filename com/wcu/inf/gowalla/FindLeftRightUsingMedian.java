/**
 * 
 */
package com.wcu.inf.gowalla;

import java.util.List;

import com.wcu.inf.gowalla.beans.NodeStat;

/**
 * @author Raj
 *
 */
public class FindLeftRightUsingMedian {

	public static void main(String args[]) {
		ReadFromDb dbHelper = new ReadFromDb();
		List<NodeStat> list = dbHelper.getAllNodeStatsBySortedEdgeCount();
		NodeStat a[] = new NodeStat[list.size()];
		list.toArray(a);
		int n = a.length;
		// System.out.println(a[0]);
		// System.out.println(a[n - 1]);
		// findLeftRightUsingOnlyMedian(a, 0, a.length - 2500);
		// findLeftRightUsingMeanAndMedian(a, 0, n - 2500);
		findLeftRight(a, 0, n - 2500);
	}

	public static void findLeftRight(NodeStat[] a, int l, int r) {
		while (l <= r) {
			if (r - l <= 20000) {
				System.out.println("Final result is :");
				System.out.println("l=(" + l + ") - " + a[l]);
				System.out.println("r=(" + r + ") - " + a[r]);
				System.out.println("n=" + (r - l + 1));
				return;
			}
			int m = l + (r - l) / 2;
			double median = (double) median(a, l, r);
			double mean = mean(a, l, r);
			System.out.println("l=" + l + ",r=" + r);
			System.out.println("mean=" + Math.round(mean) + ",median=" + median);

			if (mean > median) {
				l = l + 20000;
			} else {
				r = r - 20000;
			}
		}
	}

	public static void findLeftRightUsingMeanAndMedian(NodeStat[] a, int l, int r) {
		while (l <= r) {
			if (r - l <= 20000) {
				System.out.println("Final result is :");
				System.out.println("l=(" + l + ") - " + a[l]);
				System.out.println("r=(" + r + ") - " + a[r]);
				System.out.println("n=" + (r - l + 1));
				return;
			}
			int m = l + (r - l) / 2;
			double median = (double) median(a, l, r);
			double mean = mean(a, l, r);
			double percent = (mean - median) / mean;
			System.out.println("l=" + l + ",r=" + r);
			System.out.println("mean=" + Math.round(mean) + ",median=" + median + ", percent=" + (percent * 100));

			if (mean > median) {
				l = m;
			} else {
				r = m;
			}
		}
	}

	public static void findLeftRightUsingOnlyMedian(NodeStat[] a, int l, int r) {
		while (l <= r) {
			if (r - l <= 20000) {
				System.out.println("Final result is :");
				System.out.println("l=(" + l + ") - " + a[l]);
				System.out.println("r=(" + r + ") - " + a[r]);
				System.out.println("n=" + (r - l + 1));
				return;
			}
			int m = l + (r - l) / 2;
			int median = median(a, l, r);
			if (median - a[l].getEdgeCount() > a[r].getEdgeCount() - median) {
				l = l + m;
			} else {
				r = r - m;
			}
		}
	}

	public static double mean(NodeStat[] a, int l, int r) {
		double sum = 0.0, mean = 0.0;
		int n = r - l + 1;
		for (int i = l; i <= r; i++) {
			sum += a[i].getEdgeCount();
		}
		mean = (sum) / n;
		return mean;
	}

	public static int median(NodeStat a[], int l, int r) {
		int n = r - l + 1;
		int k = n / 2;
		if (n % 2 != 0) {
			return a[l + k].getEdgeCount();
		} else {
			return (a[l + k].getEdgeCount() + a[l + k - 1].getEdgeCount()) / 2;
		}
	}

	public static int median(int a[], int l, int r) {
		int n = r - l + 1;
		int k = n / 2;
		if (n % 2 != 0) {
			return a[l + k];
		} else {
			return (a[l + k] + a[l + k - 1]) / 2;
		}
	}
}
