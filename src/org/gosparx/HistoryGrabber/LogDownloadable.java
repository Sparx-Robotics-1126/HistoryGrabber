package org.gosparx.HistoryGrabber;

public class LogDownloadable implements Downloadable{

	private static final int TOTAL_LOGS = 5;
	
	@Override
	public String[] getFilePaths(boolean getAll) {
		String[] toReturn;
		if(getAll){
			toReturn = new String[TOTAL_LOGS];
			for(int i = 0; i < TOTAL_LOGS; i++){
				toReturn[i] = "log" + i + ".txt";
			}
		}else{
			toReturn = new String[1];
			toReturn[0] =  "log" + HistoryGrabber.hg.readFromFile("loggingConfig.txt") + ".txt";
		}
		return toReturn;
	}
}
