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
			if(validationCheck(args)) {
				result = Input.inputFiles(args[1]);
				Output.outFile(args[3], result);
			}else {
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * @param args
	 * @return 
	 */
	private static boolean validationCheck(String[] args) {
		return true;
	}
}
