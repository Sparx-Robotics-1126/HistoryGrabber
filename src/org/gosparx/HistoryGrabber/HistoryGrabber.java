package org.gosparx.HistoryGrabber;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import org.apache.commons.net.ftp.FTPClient;

/**
 * This is a command line application that will automatically grab various files from the cRIO. You can modify this for your needs.
 * @author Alex - Team 1126 - Kmodos
 */
public class HistoryGrabber extends Thread{

	/**
	 * The static instance of a HistoryGrabber for use in main();
	 */
	public static HistoryGrabber hg;

	/**
	 * An array of Downloadables. Edit this to add new Downloadables to retrieve different files.
	 */
	public static final Downloadable[] downloadables = {new LogDownloadable(), new ImageDownloadable()};

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
	 * The total number of files downloaded
	 */
	private int total;

	/**
	 * The number of files that were sucessfully downloaded
	 */
	private int completed;

	private FTPClient ftp;

	/**
	 * @param - 
	 */
	public static void main(String[] args){
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GUI.getInstance();
			}
		});
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
		ftp = new FTPClient();
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
	 * Attempts to download the file at path on the cRIO
	 * @param path - the file path to the file to download on the cRIO
	 */
	private void downloadFile(String path){
		try {
			File outFile = new File("C:/.HistoryGrabberData/match" + matchNumber + "/" + path);
			if(!outFile.exists()){
				outFile.getParentFile().mkdirs();
			}
			FileOutputStream out = new FileOutputStream(outFile);
			ftp.retrieveFile("/" + path, out);
			System.out.println("The file at " + path + " was download, now deleting");
			completed ++;
			out.close();
			deleteFile(path);
		} catch (Exception e) {
			e.printStackTrace();
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
			downloadFile(path);
			FileInputStream fis = new FileInputStream(new File("C:/.HistoryGrabberData/match" + matchNumber + "/" + path));
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));
			String toReturn = in.readLine();
			in.close();
			fis.close();
			return toReturn;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error reading the file at " + path + ". Error: " + e.getMessage());
		}
		return null;
	}

	/**
	 * Deletes the file at path.
	 * @param path - the path to delete the file at.
	 */
	public void deleteFile(String path){
		try{
			ftp.dele(path);
			System.out.println("File at " + path + " was deleted.");
		}catch (Exception e){
			e.printStackTrace();
			System.out.println("Error deleting file at " + path + ". Error: " + e.getMessage());
		}
	}

	/**
	 * Connects, and downloads all files.
	 */
	public void run(){
		try {
			ftp.connect(ip, 21);
			ftp.login("", "");
			System.out.println("Connected");
			for(Downloadable d: toDownload){
				for(String path: d.getFilePaths(getAll)){
					System.out.println(path);
					GUI.getInstance().updateBar(completed + 1 + " file(s) of " + total, (int) (((((completed + 1)/1.0)/total)) * 100));
					downloadFile(path);
				}
			}
			GUI.getInstance().popupBox(completed, total);
			ftp.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Increase the total amount of files by amount amount
	 * @param amount - the amount ot increment the total by
	 */
	public void incrementTotal(int amount){
		total += amount;
	}	
}
