package edu.handong.csee.java.chatcounter;


import java.util.HashMap;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

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

	static String threadNum;
	static String inputPath;
	static String outputPath;
	/**
	 * @param args -c number of threads -i The folder adress where the chat file is located. -o The path to the csv file.
	 * @author joonseokshim0326
	 */
	public static void main(String[] args) {
		result = new HashMap<String , Integer>();
		try {
			if(validationCheck(args)) {
				result = Input.inputFiles(threadNum, inputPath);
				Output.outFile(outputPath, result);
			}else {
				throw new ArgsErrorException("Args are error.");
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
	 * @throws ArgsErrorException 
	 */
	private static boolean validationCheck(String[] args) throws ArgsErrorException {
		
		Options options = new Options();
		CommandLineParser parser = new DefaultParser();
		
		options.addOption("c", true, "Number of threads");
		options.addOption("i", true, "The path where the chat files are located.");
		options.addOption("o", true, "Path to save the file.");
		
		CommandLine cmd;
		try {
			cmd = parser.parse( options, args);
			threadNum = cmd.getOptionValue("c");
			inputPath = cmd.getOptionValue("i");
			outputPath = cmd.getOptionValue("o");
			if(threadNum == null){
				throw new ArgsErrorException("Args are error. (Number of threads is null.)");
			}
			else if(inputPath == null){
				throw new ArgsErrorException("Args are error. (Text file path value is null.)");
			}else if(outputPath == null){
				throw new ArgsErrorException("Args are error. (output path value is null.)");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return true;
	}
}
