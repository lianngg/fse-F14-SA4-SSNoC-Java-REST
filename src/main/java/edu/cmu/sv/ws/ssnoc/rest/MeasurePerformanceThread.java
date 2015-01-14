package edu.cmu.sv.ws.ssnoc.rest;

import java.io.File;
import java.sql.SQLException;
import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.common.utils.SystemStateUtils;
import edu.cmu.sv.ws.ssnoc.data.util.DBUtils;
import edu.cmu.sv.ws.ssnoc.dto.Memory;

public class MeasurePerformanceThread implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
        Thread thisThread = Thread.currentThread();
        while (blinker == thisThread) {
    		Memory memoryData = new Memory();
    		
    		Runtime runtime = Runtime.getRuntime();
    		memoryData.setUsedVolatile((runtime.totalMemory() - runtime.freeMemory()) / 1024);
    		memoryData.setRemainingVolatile(runtime.freeMemory() / 1024);
	        
	        long freeStorage = 0, usedStorage = 0;
	        File[] roots = File.listRoots();
			for (File file : roots) {
				freeStorage += file.getFreeSpace();
				usedStorage += file.getUsableSpace();
			}
			memoryData.setUsedPersistent(usedStorage / 1024);
			memoryData.setRemainingPersistent(freeStorage / 1024);
			
    		if (memoryData.getUsedVolatile() / memoryData.getRemainingVolatile() > 4
    				|| memoryData.getUsedPersistent() / memoryData.getRemainingPersistent() > 4) {
    			SystemStateUtils.endMeasurePerformance();
    			try {
    				DBUtils.dropPerformanceMessageTablesInDB();
    			} catch (SQLException e) {
    				// TODO Auto-generated catch block
    				Log.debug("error in measurePerformanceTearDown");
    			}
    			finally {
    				this.stop();
    				Log.debug("Measure Performance Thread Stop");
    			}
    		}
        	
        	try {
				Log.debug("measure performance sleep for 60 sec");
				Thread.sleep(0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
	
	private Thread blinker;

    public void start() {
        blinker = new Thread(this);
        blinker.start();
    }
    
    public void stop() {
        blinker = null;
    }

}
