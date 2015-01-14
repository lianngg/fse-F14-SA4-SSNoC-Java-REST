package edu.cmu.sv.ws.ssnoc.rest;

import java.io.File;
import java.util.Date;


import edu.cmu.sv.ws.ssnoc.common.utils.ConverterUtils;
import edu.cmu.sv.ws.ssnoc.data.dao.DAOFactory;
import edu.cmu.sv.ws.ssnoc.data.dao.IMemoryDAO;
import edu.cmu.sv.ws.ssnoc.data.po.MemoryPO;
import edu.cmu.sv.ws.ssnoc.dto.Memory;

public class MeasureMemoryThread implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
        Thread thisThread = Thread.currentThread();
        while (blinker == thisThread) {
        	Memory memory = new Memory();

			IMemoryDAO dao = DAOFactory.getInstance().getMemoryDAO();

	        Date now = new Date();
	        memory.setCreatedTimeStamp(now.getTime() / 1000);
	        
//	        OperatingSystemMXBean bean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
//	        memory.setUsedVolatile((bean.getTotalPhysicalMemorySize() - bean.getFreePhysicalMemorySize()) / 1024);
//	        memory.setRemainingVolatile(bean.getFreePhysicalMemorySize() / 1024);
	        
	        Runtime runtime = Runtime.getRuntime();
	        memory.setUsedVolatile((runtime.totalMemory() - runtime.freeMemory()) / 1024);
	        memory.setRemainingVolatile(runtime.freeMemory() / 1024);
	        
	        long freeStorage = 0, usedStorage = 0;
	        File[] roots = File.listRoots();
			for (File file : roots) {
				freeStorage += file.getFreeSpace();
				usedStorage += file.getUsableSpace();
			}
			memory.setUsedPersistent(usedStorage / 1024);
			memory.setRemainingPersistent(freeStorage / 1024);
			
			MemoryPO po = ConverterUtils.convert(memory);

			dao.save(po);
			
			try {
				Thread.sleep(1000*60);
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
