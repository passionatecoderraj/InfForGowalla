package com.wcu.inf.util;

import java.sql.Connection;
import java.sql.SQLException;

public class TestMain {

	public static void main(String[] argv) {
		java.sql.Connection con = DbConnection.getConnection();

		System.out.println(con);
		insertTestRow(con);
		// FileUtil.readStringFromFile();
		dateTest();
	}

	public static void dateTest() {
		String str = "2010-10-19T23:55:27Z";
		StringBuffer sb = new StringBuffer();
		sb.append(str);
		sb.replace(10, 11, " ");
		System.out.println(sb.substring(0, sb.length() - 1));
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	}

	public static String convert() {
		String str = "2010-10-19T23:55:27Z";
		StringBuffer sb = new StringBuffer();
		sb.append(str);
		sb.replace(10, 11, " ");
		System.out.println(sb.substring(0, sb.length() - 1));
		return sb.substring(0, sb.length() - 1);
	}

	public static void insertTestRow(Connection con) {
		try {
			java.sql.Statement statement = con.createStatement();
			String str = "insert into test(name,datetime) values ('dasari','" + convert() + "');";
			System.out.println(str);
			statement.executeUpdate(str);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static String getCurrentTimeStamp() {

		// java.util.Date today = new java.util.Date();
		// return dateFormat.format(today.getTime());
		java.util.Date dt = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(dt);
		return currentTime;
	}
}