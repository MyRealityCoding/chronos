package de.myreality.chronos.tests;

import java.util.Date;

import de.myreality.chronos.logging.CLog;

public class DateTest {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		Date date = new Date();
			
		System.out.println(date);
		System.out.println(date);
		Thread.sleep(5000);
		//date = new Date();
		System.out.println(date);
		System.out.println(date);
		CLog.warn("Hello World!");
		
	}

}
