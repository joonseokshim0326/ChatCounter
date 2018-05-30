package edu.handong.csee.java.chatcounter;

import java.util.HashMap;
/**
 * class ChatCounter
 * @author jssjp
 */
public class ChatCounter {

	private static HashMap<String , Integer> result;

	/**
	 * main method
	 * @param args
	 */
	public static void main(String[] args) {
		result = new HashMap<String , Integer>();
		try {
			if(validationCheck(args)) {
				result = Input.inputFiles(args[1]);
				Output.outFile(args[3], result);
			}else {
				throw new ArgsErrorException("Args are error");
			}
		} 
		catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Checking validation method
	 * @param args
	 * @return 
	 */
	private static boolean validationCheck(String[] args) {
		if(args == null) {
			return false;
		}
		if(args.length != 4) {
			return false;
		}
		return true;
	}
}
