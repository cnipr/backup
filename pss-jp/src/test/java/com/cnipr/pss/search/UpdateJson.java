package com.cnipr.pss.search;

import java.util.Date;

import postgres.jdbc.PostgresTest3;

public class UpdateJson extends Thread{
	
	private String threadName;
	private Long from;
	private Long to;
	private int batchSize;
	private PostgresTest3 postgres = new PostgresTest3();
	
	@Override
	public void run() {
//		System.out.println(new Date() + "["+ threadName +"]START:" + from);
		long batchNum = (to - from + batchSize - 1)/batchSize;
		for (int i = 0; i < batchNum; i++) {
			long startIndex = from + batchSize*i;
			long endIndex = startIndex + batchSize;
			if (endIndex > to) {
				endIndex = to;
			}
			long start = System.currentTimeMillis();
			postgres.updateJson(startIndex, endIndex); 
			
			long consume = System.currentTimeMillis() - start;
			System.out.println(new Date() + "["+ threadName + "]" + startIndex + "-" + endIndex + "耗时:" + consume); 
		}
//		System.out.println(new Date() + "["+ threadName +"]END" + to);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int threadNums = 10;
		int first = 0;
//		int last = 319,5760;	
		int last = 320 * 10000;	
//		last = 10;
		for (int i = 0; i < threadNums; i++) {
			String threadName = "Thread-" + (i+1);
			long from = first + (last - first)/threadNums * i;
			long to = first + (last - first)/threadNums * (i+1);
			System.out.println(threadName + "," + from + "," + to);
			UpdateJson thread = new UpdateJson(threadName,from,to,1000);
			thread.start();
		}
		
//		new PostgresTest().getAll();
	}
	
	public UpdateJson() {
		super();
	}

	public UpdateJson(String threadName, Long from, Long to, int batchSize) {
		super();
		this.threadName = threadName;
		this.from = from;
		this.to = to;
		this.batchSize = batchSize;
	}

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public Long getFrom() {
		return from;
	}

	public void setFrom(Long from) {
		this.from = from;
	}

	public Long getTo() {
		return to;
	}

	public void setTo(Long to) {
		this.to = to;
	}

	public int getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}
}
