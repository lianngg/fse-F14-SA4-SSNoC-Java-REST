package edu.cmu.sv.ws.ssnoc.common.utils;
import java.util.Date;

public class SystemStateUtils {

    public static boolean inMemoryDatabaseMode; // In-Memory database mode for Unit Test.
	public final static int NORMAL = 1;
	public final static int MEASUREPREFORMANCE = 2;
	private static Date startDate;
	private static Date endDate;
	private static int SystemState = NORMAL;
	private static int postNumber = 0;
	private static int getNumber = 0;
	
	private static boolean setSystemState (int newState) {
		if (newState == MEASUREPREFORMANCE && SystemState == NORMAL) {
			startDate = new Date();
			postNumber = 0;
			getNumber = 0;
			SystemState = MEASUREPREFORMANCE;
			return true;
		}
		else if (newState == NORMAL && SystemState == MEASUREPREFORMANCE) {
			endDate = new Date();
			SystemState = NORMAL;
			return true;
		}
		else {
			return false;
		}
	}
	
	public static void startMeasurePerformance () {
		setSystemState(MEASUREPREFORMANCE);
	}
	
	public static void endMeasurePerformance () {
		setSystemState(NORMAL);
	}
	
	public static int getSystemState () {
		return SystemState;
	}
	
	public static void increasePostNumber () {
		if (SystemState == MEASUREPREFORMANCE) {
			postNumber ++;
		}
	}
	
	public static void increaseGetNumber () {
		if (SystemState == MEASUREPREFORMANCE) {
			getNumber ++;
		}
	}
	
	public static int getPostPerSecond () {
		double second = (startDate.getTime() - endDate.getTime()) / 1000;
		return (int)(postNumber / second + 0.5);
	}
	
	public static int getGetPerSecond () {
		double second = (startDate.getTime() - endDate.getTime()) / 1000;
		return (int)(getNumber / second + 0.5);
	}
}
