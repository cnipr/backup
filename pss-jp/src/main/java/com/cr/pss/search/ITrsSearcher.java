package com.cnipr.pss.search;

public interface ITrsSearcher {

	// 数据权限验证前的要做的一些处理
	public void preValid();

	// 数据权限验证后的要做的一些处理
	public void postValid();

}
