package com.cnipr.pss.search;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Test {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		ExecutorService exec = Executors.newCachedThreadPool();
		List<String> l = new ArrayList<String>();
		ArrayList<Future<List<String>>> res = new ArrayList<Future<List<String>>>();
		for (int i = 0; i < Integer.parseInt(args[3]); i++) {// 生成5个线程，每个线程的id分别是1，2，3，4，5
			res.add(exec.submit(new Multi(args[0], args[1], "",
					2, "", "", args[2],i*10000,(i+1)*10000)));
		}
		int sum = 0;
		for (Future<List<String>> f : res) {
			try {
				List<String> ll = f.get();
				l.addAll(ll);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(l.size());
		Map<Object, Integer> map = new HashMap<Object, Integer>();
		for (Object o : l) {
			map.put(o, map.get(o) == null ? 1 : map.get(o) + 1);
		}
		long end = System.currentTimeMillis();
		System.out.println("PSS-分类统计检索：" + (end - start));
		exec.shutdown();// 关闭线程池
	}

}

//class multi implements Callable<List<String>> {
//	private static int i = 0;
//	List<String> id;
//
//	public multi() {
//		i++;
//		id = Integer.toString(i);
//	}
//
//	public String call() {
//		return id;
//	}
//}
