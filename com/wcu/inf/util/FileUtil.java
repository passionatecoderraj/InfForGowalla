package com.wcu.inf.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Raj
 *
 */
public class FileUtil {

	private final static String FILEPATH = "C:\\raj\\study\\univ\\research\\data\\gowalla\\Gowalla_totalCheckins.txt";

	
	
	/*
	 * Reads encrypted.text file and returns the entire file contents as single
	 * String
	 */
	public static String readStringFromFile() {

		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(FILEPATH));
			if (br != null) {
				System.out.println("file is read");
			}
			String sCurrentLine;
			int count = 0;
			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
				if (++count == 100)
					return null;
			}
		} catch (IOException e) {
			System.out.println("Unknown error occured while loading file");
			e.printStackTrace();
		}

		return sb.toString();
	}

}
