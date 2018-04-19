package com.cnipr.cnreference;

import com.cnipr.pss.rs.dto.OverviewSearchDTO;
import com.cnipr.pss.search.AbstractSearcher;
import com.cnipr.pss.search.impl.PatSimilarSearcher;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AbstractSearcher searcher = new PatSimilarSearcher("fmzl,syxx",
				"公开（公告）号='CN101937300A'  and  申请日>2009.07.01", 0,
				10, "", "", "");
		OverviewSearchDTO dto = (OverviewSearchDTO) searcher.excute();
		System.out.println(dto.getPatentInfoList().size());
		System.out.println(dto.getPatentInfoList().get(1).getTi());
	}

}
