package edu.handong.csee.java.chatcounter;

import java.util.HashMap;

/**
 * This class counts the number of chats in a message file to make it a csv file.
 * @author jssjp
 * 2018. 5. 29.
 */
public class ChatCounter {

	/**
	 * key is Person name, value is Number of times said
	 */
	private static HashMap<String , Integer> result;

	/**
	 * @param args -i The folder adress where the chat file is located. -o The path to the csv file.
	 * @author joonseokshim0326
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
	 * Check that CLI parameters are correct.
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
