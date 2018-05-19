package edu.handong.csee.java.chatcounter;

import java.util.HashMap;

public class ChatCounter {

	private static HashMap<String , Integer> result;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		result = new HashMap<String , Integer>();
		try {
			validationCheck(args);
			result = Input.inputFiles(args[1]);
			Output.outFile(args[3], result);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * @param args
	 */
	private static void validationCheck(String[] args) {

	}
}
