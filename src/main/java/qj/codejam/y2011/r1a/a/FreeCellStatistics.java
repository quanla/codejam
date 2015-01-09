package qj.codejam.y2011.r1a.a;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

import qj.codejam.CodeJam;

/**
 * TODO Not done
 * @author Quan
 *
 */
public class FreeCellStatistics {
	public static void main(String[] args) {
//		String resName = "sample.in";
//		String resName = "A-small-practice.in";
		String resName = "A-large-practice.in";
		CodeJam.solver(resName, lineF -> {
			
			String[] split = lineF.e().split(" ");
			BigInteger n = new BigInteger(split[0]);
			Integer pd = Integer.parseInt(split[1]);
			Integer pg = Integer.parseInt(split[2]);
			
			return () -> cal(n, pd, pg) ? "Possible" : "Broken";
		}).solve();
	}
	
	static boolean cal(BigInteger n, int pd, int pg) {
		if (pg == 0) {
			return pd == 0;
		} else if (pg == 100) {
			return pd == 100;
		}
		int gcd = BigInteger.valueOf(pd).gcd(BigInteger.valueOf(100)).intValue();
		int dMin = 100 / gcd;
		return n.compareTo(BigInteger.valueOf(dMin)) >= 0;
	}
}
