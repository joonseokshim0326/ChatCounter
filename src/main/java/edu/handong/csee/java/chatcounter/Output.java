package edu.handong.csee.java.chatcounter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.util.HashMap;

public class Output {

	/**
	 * @param result
	 * @throws IOException 
	 */
	public static void outFile(String resultFileName, HashMap<String, Integer> resultMap) throws IOException {
		String filePath, fileName = null;
		filePath = resultFileName.substring(0,resultFileName.lastIndexOf("\\"));
		fileName = resultFileName.substring(resultFileName.lastIndexOf("\\")+1);
		makeResultFile(filePath, fileName, resultMap);
	}

	/**
	 * @param resultFileName
	 * @param resultMap
	 * @throws IOException 
	 */
	private static void makeResultFile(String resultFilePath, String resultFileName, HashMap<String, Integer> resultMap) throws IOException {
		FileWriter writer = new FileWriter(resultFilePath + "/" + resultFileName, true);
		BufferedWriter bw = new BufferedWriter(writer);
		bw.write("kakao_id,count");
		bw.newLine();
		for(String name : resultMap.keySet()) {
			bw.write(name + "," +resultMap.get(name));
			bw.newLine();
    	}
		bw.flush();
		bw.close();
	}

}