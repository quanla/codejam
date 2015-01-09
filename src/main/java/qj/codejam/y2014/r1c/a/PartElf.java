package qj.codejam.y2014.r1c.a;

import java.math.BigInteger;
import java.util.Collections;
import java.util.HashSet;

import qj.codejam.CodeJam;
import qj.util.MathUtil;
import qj.util.funct.F0;

public class PartElf {

	public static void main(String[] args) {
//		String resName = "sample.in";
//		String resName = "A-small-practice.in";
		String resName = "A-large-practice.in";
		
		CodeJam.solver(resName, (F0<String> lineF) -> {
			String[] split = lineF.e().split("/");
			BigInteger a = new BigInteger(split[0]);
			BigInteger b = new BigInteger(split[1]);
			
			return () -> {
				if (!check(a,b)) {
					return "impossible";
				}
				Integer cal = cal(a, b, 0);
				return cal == null ? "impossible" : "" + cal;
			};
		})
		.solve();
	}
	
	static BigInteger p40 = BigInteger.valueOf(2).pow(40);
	private static boolean check(BigInteger a, BigInteger b) {
		BigInteger gcd = a.gcd(b);
		if (!gcd.equals(BigInteger.ONE)) {
			a = a.divide(gcd);
			b = b.divide(gcd);
		}
		
		if (!isDivisibleBy2(b)) {
			return false;
		}
		if (b.compareTo(p40) > 0) {
			return false;
		}
		return true;
	}

	static BigInteger _2 = BigInteger.valueOf(2);
	private static boolean isDivisibleBy2(BigInteger b) {
		BigInteger[] dr = b.divideAndRemainder(_2);
		if (!dr[1].equals(BigInteger.ZERO)) {
			return false;
		}
		if (dr[0].equals(BigInteger.ONE)) {
			return true;
		}
		return isDivisibleBy2(dr[0]);
	}

	static Integer cal(BigInteger a, BigInteger b, int level) {
		
		if (b.subtract(a).compareTo(a) <= 0) {
			return level + 1;
		}
		if (level == 40) {
			return null;
		}
		return cal(a.multiply(BigInteger.valueOf(2)), b, level + 1);
	}
}
