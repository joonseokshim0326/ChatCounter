package edu.handong.csee.java.chatcounter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * This class for input of program
 * 
 * @author jssjp
 */
public class Input {

	/**
	 * key is Person name, value is Number of times said
	 */
	private static HashMap<String, Integer> parseResultMap = new HashMap<>();

	/**
	 * <key is Person name, <key is The time of the chat, value is Conversation
	 * contents>>
	 */
	private static HashMap<String, HashMap<String, ArrayList<String>>> chatResultMap = new HashMap<>();

	/**
	 * Stack of all chat files to analyze.
	 */
	private static Stack<File> allFileStack = new Stack<>();

	/**
	 * Reads the file in its path and analyzes the number of conversations.
	 * 
	 * @param inputPath
	 *            The folder where the conversation file is located.
	 * @return
	 */
	public static HashMap<String, Integer> inputFiles(String threadNumber, String inputPath) {
		try {
			openFolder(threadNumber, inputPath);
			calcCount();
		} catch (Exception e) {

		}
		return parseResultMap;
	}

	/**
	 * Start reading the folder.
	 * 
	 * @param inputPath
	 */
	private static void openFolder(String threadNumber, String inputPath) {
		int threadNum;
		allFileStack = new Stack<>();
		final File folder = new File(inputPath);
		makeAllFileStack(folder);
		threadNum = Integer.parseInt(threadNumber);
		if (threadNum != 1) {
			readAllFileStackThreads(threadNum);
		} else {
			readAllFileStack();
		}
	}

	/**
	 * @param threadNum
	 *            Use the thread to read the file on the stack until the stack is
	 *            empty.
	 */
	private static void readAllFileStackThreads(int threadNum) {
		ArrayList<Thread> threads = new ArrayList<Thread>();
		for (int i = 0; i < threadNum; i++) {
			Thread t = new Thread(new ReadFileThread(i));
			t.start();
			threads.add(t);
		}
		for (int i = 0; i < threads.size(); i++) {
			try {
				threads.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * Reads the files in the stack until the stack is empty.
	 */
	public static void readAllFileStack() {
		String fileName, ext;
		while (!allFileStack.empty()) {
			final File fileEntry = allFileStack.pop();
			fileName = fileEntry.getName();
			ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
			if (ext.equals("csv")) {
				readFile_CSV(fileEntry);
			} else if (ext.equals("txt")) {
				readFile(fileEntry);
			}
		}
	}

	/**
	 * If there is a folder, it recurs and Add it to the list if it is a file.
	 * 
	 * @param folder
	 */
	private static void makeAllFileStack(File folder) {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				makeAllFileStack(fileEntry);
			} else {
				allFileStack.push(fileEntry);
			}
		}
	}

	/**
	 * @param fileEntry
	 * Read the CSV file one line at a time and send it to the parser.
	 */
	private static void readFile_CSV(File fileEntry) {
		try {
			String line = "";
			ArrayList<String> lineList = new ArrayList<String>();
			InputStreamReader fileReader = new InputStreamReader(new FileInputStream(fileEntry), "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				lineList.add(line);
			}
			parseLines_CSV(lineList);
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {

		}
	}

	/**
	 * @param lines
	 * Analyze one line in the csv file and update the map.
	 * 
	 */
	private static void parseLines_CSV(ArrayList<String> lines) {
		int index;
		String[] array;
		HashMap<String, String> chatMap = new HashMap<String, String>();
		String dayString = null;
		index = 0;
		for (String line : lines) {
			if (index == 0) {
				index++;
			} else {
				chatMap = new HashMap<String, String>();
				array = line.split(",");
				dayString = array[0].split(" ")[0];
				chatMap.put("name", array[1].substring(1, array[1].length() - 1));
				chatMap.put("time", array[0].split(" ")[1]);
				chatMap.put("word", array[2].substring(1, array[2].length() - 1));
				updateChatResultMap(dayString, chatMap);
			}
		}
	}

	/**
	 * Read the text file one line at a time and send it to the parser.
	 * 
	 * @param fileEntry
	 */
	private static void readFile(File fileEntry) {
		try {
			String line = "";
			ArrayList<String> lineList = new ArrayList<String>();
			InputStreamReader fileReader = new InputStreamReader(new FileInputStream(fileEntry), "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
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
	 * Create a map with the number of conversations.
	 */
	private static void calcCount() {
		int chatCount = 0;
		for (String name : chatResultMap.keySet()) {
			chatCount = 0;
			for (String timeStamp : chatResultMap.get(name).keySet()) {
				chatCount += chatResultMap.get(name).get(timeStamp).size();
			}
			parseResultMap.put(name, chatCount);
		}
		System.out.println(parseResultMap);
	}

	/**
	 * Read one line to separate the conversation or date and send it to the
	 * analyzer.
	 * 
	 * @param lines
	 */
	private static void parseLines(ArrayList<String> lines) {
		HashMap<String, String> chatMap;
		String dayString = null;
		for (String line : lines) {
			if (!line.isEmpty()) {
				if (line.charAt(0) == '-') {
					dayString = parseDayLine(line);
					System.out.println(dayString);
				} else if (line.charAt(0) == '[') {
					chatMap = parseChatLine(line);
					updateChatResultMap(dayString, chatMap);
				}
			}
		}
	}

	/**
	 * Find the name value and put the time and content.
	 * 
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

		if (!chatResultMap.containsKey(chatMap.get("name"))) {
			chatResultMap.put(name, timeStringMap);
		}

		timeStringMap = chatResultMap.get(name);

		if (!timeStringMap.containsKey(timeStamp)) {
			timeStringMap.put(timeStamp, wordArrayList);
		}
		wordArrayList = timeStringMap.get(timeStamp);
		if (!wordArrayList.contains(word)) {
			wordArrayList.add(word);
		}
	}

	/**
	 * Only time values are extracted from line.
	 * 
	 * @param line
	 *            One line read from the file.
	 * @return
	 */
	private static String parseDayLine(String line) {
		// line = line.replaceAll("\\s+", "");
		// line = line.replaceAll("-", "");
		String dayString = "";
		String[] array;
		String year, month, day;
		array = line.split(" ");
		year = array[1].replaceAll("[^0-9]", "");
		month = array[2].replaceAll("[^0-9]", "");
		month = attachZero(month);
		day = array[3].replaceAll("[^0-9]", "");
		day = attachZero(day);
		dayString = (year + '-' + month + '-' + day);
		return dayString;
	}

	/**
	 * @param str
	 * @return For single digit numbers, prefix it with 0.
	 */
	private static String attachZero(String str) {
		if (str.length() < 2) {
			str = "0" + str;
		}
		return str;
	}

	/**
	 * Extracts the name and time conversation from the line.
	 * 
	 * @param line
	 *            One line read from the file.
	 * @return
	 */
	private static HashMap<String, String> parseChatLine(String line) {
		HashMap<String, String> chatMap = new HashMap<>();
		String name = "name", time = "time", word = "word";
		name = line.substring(1, line.indexOf(']'));
		line = line.substring(line.indexOf(']') + 1);
		time = line.substring((line.indexOf('[') + 1), line.indexOf(']'));
		time = timeTo24HourSystem(time);
		word = line.substring(line.indexOf(']') + 2);
		chatMap.put("name", name);
		chatMap.put("time", time);
		chatMap.put("word", word);
		return chatMap;

	}

	/**
	 * @param time
	 * @return Converts time String from a 12-hour system to a 24-hour system.
	 */
	private static String timeTo24HourSystem(String time) {
		String[] array = time.split(" ");
		String[] timeArray = array[1].split(":");
		if (array[0].equals("오전")) {
			time = attachZero(timeArray[0]) + ":" + timeArray[1];
		} else if (array[0].equals("오후")) {
			timeArray[0] = (Integer.parseInt(timeArray[0]) + 12) + "";
			time = timeArray[0] + ":" + timeArray[1];
		}
		return time;
	}

}
