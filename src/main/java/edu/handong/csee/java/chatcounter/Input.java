package edu.handong.csee.java.chatcounter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Input {
	
	/**
	 *  <name, count>
	 */
	private static HashMap<String , Integer> parseResultMap = new HashMap<>();	
	
	/**
	 *  <name, <time, wordList>>
	 */
	private static HashMap<String , HashMap<String, ArrayList<String>>> chatResultMap = new HashMap<>();	
	
	
	/**
	 * @param inputPath
	 * @return
	 */
	
	public static HashMap<String, Integer> inputFiles(String inputPath) {
		try {
			openFolder(inputPath);
			calcCount();
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
			InputStreamReader fileReader = new InputStreamReader(new FileInputStream(fileEntry), "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			
			while((line = bufferedReader.readLine()) != null) {
				lineList.add(line);
			}
			parseLines(lineList);
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
		int chatCount = 0;
        for( String name : chatResultMap.keySet() ){
        	chatCount = 0;
        	for(String timeStamp : chatResultMap.get(name).keySet()) {
        		chatCount += chatResultMap.get(name).get(timeStamp).size();
        	}
        	parseResultMap.put(name, chatCount);
        }
        System.out.println(parseResultMap);
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
		String name, timeStamp, word;
 		HashMap<String, ArrayList<String>> timeStringMap = new HashMap<>();
 		ArrayList<String> wordArrayList = new ArrayList<>();
 		name = chatMap.get("name");
 		timeStamp = dayString + " " + chatMap.get("time");
 		word = chatMap.get("word");
 		
		if(!chatResultMap.containsKey(chatMap.get("name"))) {
			chatResultMap.put(name, timeStringMap);
		}
		
		timeStringMap = chatResultMap.get(name);
		
		if(!timeStringMap.containsKey(timeStamp)) {
			timeStringMap.put(timeStamp, wordArrayList);
		}
		wordArrayList = timeStringMap.get(timeStamp);
		if(!wordArrayList.contains(word)) {
			wordArrayList.add(word);
		}
	}

	/**
	 * @param lines
	 * @return 
	 */
	private static String parseDayLine(String line) {
		line = line.replaceAll("\\s+", "");
		line = line.replaceAll("-", "");
		/*String dayString = "";
		String[] array;
		String year, month, day;
		array = line.split(" ");
		year = array[1].replaceAll("[^0-9]", "");
		month = array[2].replaceAll("[^0-9]", "");
		day = array[3].replaceAll("[^0-9]", "");
		dayString = (year + '.' + month + '.' + day);*/
		return line;
	}
	
	/**
	 * @param lines
	 * @return 
	 */
	private static HashMap<String, String> parseChatLine(String line) {
 		HashMap<String , String> chatMap = new HashMap<>();;
		String name = "name", time = "time", word = "word";
		name = line.substring(1,line.indexOf(']'));
		line = line.substring(line.indexOf(']')+1);
		time = line.substring((line.indexOf('[')+1), line.indexOf(']'));
		word = line.substring(line.indexOf(']')+2);
		chatMap.put("name", name);
		chatMap.put("time", time);
		chatMap.put("word", word);
		return chatMap;
		
	}
	
}
