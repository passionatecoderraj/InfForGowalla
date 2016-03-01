/**
 * 
 */
package com.wcu.inf.gowalla;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Raj
 *
 */
public class DateTest {

	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
		String dt1 = "2010-10-08 19:19:10";
		String dt2 = "2010-10-07 19:19:10";
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1 = df1.parse(dt1);
		System.out.println(d1);
		Date d2 = df1.parse(dt2);
		System.out.println(d2);
		System.out.println(d1.compareTo(d2));
	}

}
