package org.gosparx.HistoryGrabber;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

/**
 * This is a command line application that will automatically grab various files from the cRIO. You can modify this for your needs.
 * @author Alex - Team 1126 - Kmodos
 */
public class HistoryGrabber {
	private static int teamNumber = 0000;
	private static boolean getAll = false;
	private static final Downloadable[] files = {};
	private static String ip;
	private static int matchNumber;

	/**
	 * @param args[0] - Team Number
	 * 		  args[1] - Match Number
	 * 		  args[2] - (optional) "-a" Signals to get all of the files, not just the most recent
	 */
	public static void main(String[] args){
		if(args.length < 2){
			System.out.println("Invailid usage. You must at least provide your team number and match number.");
			System.exit(-1);
		}
		try{
			teamNumber = Integer.parseInt(args[0]);
			ip = getIP(teamNumber);
			matchNumber = Integer.parseInt(args[1]);
		}catch(Exception e){
			System.out.println("Invalid usage. The first argument must be your team number and the second must be the Match number!");
			System.exit(-1);
		}
		if(args.length > 2){
			if(args[1].equals("-a")){
				getAll = true;
			}
		}
		for(Downloadable d: files){
			for(String path: d.getFilePaths(getAll)){
				downloadFile(path);
			}
		}
	}
	
	private static void downloadFile(String path){
		try {
			URL ftp = new URL("ftp://" + ip + "/" + path);
			URLConnection ftpCon = ftp.openConnection();
			BufferedInputStream in = new BufferedInputStream(ftpCon.getInputStream());
			FileOutputStream out = new FileOutputStream("/data/match" + matchNumber + "/" +  path);
			int i;
			while((i = in.read())!= -1){
				out.write(i);
			}
			out.close();
			in.close();
			System.out.println("The file at " + path + " was download");
		} catch (Exception e) {
			System.out.println("Error downloading the file at " + path + ". Error: " + e.getMessage());
		}
	}
	
	private static String getIP(int teamNumber){
		return "10." + new DecimalFormat("00.00").format(teamNumber/100.0) + ".2";
	}
}
