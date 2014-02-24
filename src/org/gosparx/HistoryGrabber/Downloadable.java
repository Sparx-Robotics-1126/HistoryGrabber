package org.gosparx.HistoryGrabber;

public interface Downloadable {
	
	/**
	 * Gets all of the file paths for the application to download.
	 * @return an array of String versions of all the file paths to download.
	 */
	String[] getFilePaths();
}
