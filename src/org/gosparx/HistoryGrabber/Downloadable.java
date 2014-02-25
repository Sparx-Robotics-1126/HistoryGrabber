package org.gosparx.HistoryGrabber;

public interface Downloadable {
	
	/**
	 * Gets all of the file paths for the application to download.
	 * @param getAll - retrieve all of the files, not just the latest.
	 * @return an array of String versions of all the file paths to download.
	 */
	String[] getFilePaths(boolean getAll);
}
