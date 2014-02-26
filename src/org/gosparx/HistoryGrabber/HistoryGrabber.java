package org.gosparx.HistoryGrabber;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

/**
 * This is a command line application that will automatically grab various files from the cRIO. You can modify this for your needs.
 * @author Alex - Team 1126 - Kmodos
 */
public class HistoryGrabber {
	
	/**
	 * The static instance of a HistoryGrabber for use in main();
	 */
	public static HistoryGrabber hg;
	
	/**
	 * An array of Downloadables. Edit this to add new Downloadables to retrieve different files.
	 */
	private static final Downloadable[] downloadables = {new LogDownloadable()};
	
	/**
	 * The nonstatic classes instance of Downloadables
	 */
	private Downloadable[] toDownload;
	
	/**
	 * The IP of the cRIO controller.
	 */
	private String ip;
	
	/**
	 * The match number. Data will be stored in /data/match + matchNumber/
	 */
	private int matchNumber;
	
	/**
	 * The team number. Used for calculating the cRIOs ip.
	 */
	private int teamNumber = 0000;
	
	/**
	 * True if we should get all of the data from the cRIO, false if we should get just the last data.
	 */
	private boolean getAll = false;

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
			hg = new HistoryGrabber(downloadables, Integer.parseInt(args[0]), Integer.parseInt(args[1]));
			if(args.length > 2){
				if(args[2].equals("-a")){
					hg.setGetAll(true);
				}
			}
			hg.commenceDownloading();
		}catch(Exception e){
			System.out.println("Invalid usage. The first argument must be your team number and the second must be the Match number!");
			System.exit(-1);
		}
		
	}
	
	/**
	 * Creates a new HistoryGrabber
	 * @param dl - an Array of Downloadables. The HistoryGrabber will getFilePaths for each of the elements
	 * @param teamNumber - the teams number
	 * @param matchNumber - the match number
	 */
	public HistoryGrabber(Downloadable[] dl, int teamNumber, int matchNumber){
		this.toDownload = dl;
		this.teamNumber = teamNumber;
		this.matchNumber = matchNumber;
		this.ip = getIP(this.teamNumber);
		this.getAll = false;
	}
	
	/**
	 * Returns the cRIO's IP
	 * @param teamNumber - the team number
	 * @return the ip of the cRIO
	 */
	private String getIP(int teamNumber){
		return "10." + new DecimalFormat("00.00").format(teamNumber/100.0) + ".2";
	}
	
	/**
	 * Sets the new value for getAll  
	 * @param newValue - the new value of getAll
	 */
	public void setGetAll(boolean newValue){
		this.getAll = newValue;
	}
	
	/**
	 * Loops through toDownloading, getting all of the file paths and then downloading each of them.
	 */
	public void commenceDownloading(){
		for(Downloadable d: toDownload){
			for(String path: d.getFilePaths(getAll)){
				downloadFiles(path);
			}
		}
	}
	
	/**
	 * Attempts to download the file at path on the cRIO
	 * @param path - the file path to the file to download on the cRIO
	 */
	private void downloadFiles(String path){
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
	
	/**
	 * Reads the first line form the file at path.
	 * @param path - the path of the file to read from
	 * @return the first line of the file at path.
	 */
	public String readFromFile(String path){
		try {
			URL ftp = new URL("ftp://" + ip + "/" + path);
			URLConnection ftpCon = ftp.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(ftpCon.getInputStream()));
			String toReturn = in.readLine();
			in.close();
			return toReturn;
		} catch (Exception e) {
			System.out.println("Error reading the file at " + path + ". Error: " + e.getMessage());
		}
		return null;
	}
	
	/**
	 * Writes to the file at path
	 * @param path - the path of the file ot write to
	 * @param b - the array of bytes to write
	 * @param offset - the offset to start at
	 */
	public void writeToFile(String path, byte[] b, int offset){
		try {
			URL ftp = new URL("ftp://" + ip + "/" + path);
			URLConnection ftpCon = ftp.openConnection();
			DataOutputStream dos = new DataOutputStream(ftpCon.getOutputStream());
			dos.write(b, offset, b.length);
			dos.close();
		} catch (Exception e) {
			System.out.println("Error writing to the file at " + path + ". Error: " + e.getMessage());
		}
	}
}
