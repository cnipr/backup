package postgres.jdbc;

import java.io.UnsupportedEncodingException;

import com.cnipr.pss.rs.dto.TrsListMapSearchDTO;
import com.cnipr.pss.search.AbstractSearcher;
import com.cnipr.pss.search.impl.TrsListMapSearcher2;

public class TestCharacter {

	public static void main(String[] args) {
		String[] columns = {};
		String[] columns2 = ("权利要求书,说明书").split(",");
		AbstractSearcher test2 = new TrsListMapSearcher2("FMZL,FMSQ,SYXX,WGZL", "申请号='CN201020129292.6'",
				"RELEVANCE", 2, "21", 0, columns , columns2,0,10);
		TrsListMapSearchDTO dto = (TrsListMapSearchDTO)test2.excute();
		String claim = dto.getResultList().get(0).get("权利要求书");
		String desc = dto.getResultList().get(0).get("说明书");
		try {
			claim = new String( claim.getBytes("UTF-8") , "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}   
		System.out.println(claim);
		System.out.println(desc);
	}

}
