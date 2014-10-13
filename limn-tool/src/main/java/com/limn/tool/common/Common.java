package com.limn.tool.common;

public class Common {

	public static void wait(int sec){
		Long time = ((Integer) sec).longValue();
		try {
			Print.log("Wait : " + sec, 0);
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
