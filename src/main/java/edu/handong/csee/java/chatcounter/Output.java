package edu.handong.csee.java.chatcounter;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This class for output of program
 * @author jssjp
 */
public class Output {

	/**
	 * Create a csv file with the contents of map.
	 * @param resultFileName
	 * @param resultMap
	 * @throws IOException
	 */
	public static void outFile(String resultFileName, HashMap<String, Integer> resultMap) throws IOException {
		List<Entry<String, Integer>> sortedEntries = null;
		String filePath, fileName = null;
		filePath = resultFileName.substring(0,resultFileName.lastIndexOf("\\"));
		fileName = resultFileName.substring(resultFileName.lastIndexOf("\\")+1);
		sortedEntries = sortMap(resultMap);
		makeResultFile(filePath, fileName, sortedEntries);
	}

	/**
	 * Create an entry by sorting the values in map in descending order.
	 * @param map
	 * @return
	 */
	static <K, V extends Comparable<? super V>> List<Entry<String, Integer>> sortMap(Map<String, Integer> map) {
 	    List<Entry<String, Integer>> sortedEntries = new ArrayList<Entry<String, Integer>>(map.entrySet());

	    Collections.sort(sortedEntries, new Comparator<Entry<String, Integer>>() {
	        @Override
	        public int compare(Entry<String, Integer> e1, Entry<String, Integer> e2) {
	            return e2.getValue().compareTo(e1.getValue());
	        }
	    });

	    return sortedEntries;
	}
	
	/**
	 * 
	 * @param resultFilePath
	 * @param resultFileName
	 * @param sortedEntries
	 * @throws IOException
	 */
	private static void makeResultFile(String resultFilePath, String resultFileName, List<Entry<String, Integer>> sortedEntries) throws IOException {
		BufferedWriter bw = new BufferedWriter
			    (new OutputStreamWriter(new FileOutputStream(resultFilePath + "/" + resultFileName), "UTF-8"));
		bw.write("kakao_id,count");
		bw.newLine();
		for(Entry<String, Integer> entry : sortedEntries){
	    	bw.write(entry.getKey() + "," +entry.getValue());
			bw.newLine();
	    }
		bw.flush();
		bw.close();
	}

}
