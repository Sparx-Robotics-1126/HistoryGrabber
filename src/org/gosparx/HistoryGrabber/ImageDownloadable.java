package org.gosparx.HistoryGrabber;

public class ImageDownloadable implements Downloadable{

	@Override
	public String[] getFilePaths(boolean getAll) {
		int size = Integer.parseInt(HistoryGrabber.hg.readFromFile("ShooterPictures/photoConfig.txt"));
		String[] toReturn = new String[size];
		for(int i = 0; i < size; i++){
			toReturn[i] = "ShooterPictures/Shot" + i + ".png";
		}
		HistoryGrabber.hg.incrementTotal(toReturn.length + 1);
		return toReturn;
	}
}
