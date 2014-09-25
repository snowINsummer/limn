package com.limn.tool.common;

import com.limn.log.RunLog;

public class Print {
	
	/**
	 * 输出
	 * @param log
	 * @param style 1 green , 2 red , 3 yellow ,4 sold black,else black
	 */
	public static void log(String log,int style){
		if(RunLog.isStart()){
			RunLog.printLog(log, style);
		}else{
			System.out.println(log);
		}
	}
	
	
	/**
	 * 输出
	 * @param log
	 * @param style 1 green , 2 red , 3 yellow ,4 sold black,else black
	 */
	public static void debugLog(String log,int style){
		if(RunLog.isStart()){
			RunLog.printLog(log, style);
		}else{
			System.out.println(log);
		}
	}
}