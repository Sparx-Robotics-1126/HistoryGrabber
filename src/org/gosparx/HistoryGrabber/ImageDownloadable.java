package org.gosparx.HistoryGrabber;

public class ImageDownloadable implements Downloadable{
	
	@Override
	public String[] getFilePaths(boolean getAll) {
		int size = Integer.parseInt(HistoryGrabber.hg.readFromFile("/ShooterPictures/photoConfig.txt"));
		String[] toReturn = new String[size + 1];
		for(int i = 0; i <= size; i++){
			toReturn[i] = "ShooterPictures/shot" + i + ".png";
		}
		byte[] b = new byte[1];
		b[0] = 0;
		HistoryGrabber.hg.writeToFile("ShooterPictures/photoConfig.txt", b, 0);
		return toReturn;
	}

}
