package edu.handong.csee.java.chatcounter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Input {

	/**
	 *  <name, count>
	 */
	private static HashMap<String , Integer> parseResultMap;	

	/**
	 *  <name, <time, wordList>>
	 */
	private static HashMap<String , HashMap<String, ArrayList<String>>> chatResultMap;	


	/**
	 * @param inputPath
	 * @return
	 */

	public static HashMap<String, Integer> inputFiles(String inputPath) {
		try {
			openFolder(inputPath);
		} catch (Exception e) {

		}
		return parseResultMap;
	}

	/**
	 * @param inputPath
	 */
	private static void openFolder(String inputPath) {
		final File folder = new File(inputPath);
		listFilesForFolder(folder);
	}

	/**
	 * @param folder
	 */
	public static void listFilesForFolder(final File folder) {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				readFile(fileEntry);
			}
		}
	}

	/**
	 * @param fileEntry
	 */
	private static void readFile(File fileEntry){
		try {
			String line = "";
			ArrayList<String> lineList = new ArrayList<String>();
			FileReader fileReader = new FileReader(fileEntry);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while((line = bufferedReader.readLine()) != null) {
				lineList.add(line);
			}
			parseLines(lineList);
			calcCount();
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {

		}
	}


	/**
	 * 
	 */
	private static void calcCount() {

	}

	/**
	 * @param lines
	 */
	private static void parseLines(ArrayList<String> lines) {
		HashMap<String, String> chatMap;
		String dayString = null;
		for(String line : lines) {
			if(!line.isEmpty()) {
				if(line.charAt(0) == '-') {
					dayString = parseDayLine(line);
					System.out.println(dayString);
				}
				else if(line.charAt(0) == '[') {
					chatMap = parseChatLine(line);
					updateChatResultMap(dayString, chatMap);
				}
			}
		}
	}

	/**
	 * @param dayString
	 * @param chatMap
	 */
	private static void updateChatResultMap(String dayString, HashMap<String, String> chatMap) {
		System.out.println(dayString);
		System.out.println(chatMap);
	}

	/**
	 * @param lines
	 * @return 
	 */
	private static String parseDayLine(String line) {
		String dayString = "";
		String[] array;
		String year, month, day;
		array = line.split(" ");
		year = array[1].replaceAll("[^0-9]", "");
		month = array[2].replaceAll("[^0-9]", "");
		day = array[3].replaceAll("[^0-9]", "");
		dayString = (year + '.' + month + '.' + day);
		return dayString;
	}

	/**
	 * @param lines
	 * @return 
	 */
	private static HashMap<String, String> parseChatLine(String line) {
		HashMap<String , String> chatMap = new HashMap<>();;
		String name = "", time = "", word = "";
		chatMap.put("name", name);
		chatMap.put("time", time);
		chatMap.put("word", word);
		return chatMap;

	}

}
