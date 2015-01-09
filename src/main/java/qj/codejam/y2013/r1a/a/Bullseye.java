package qj.codejam.y2013.r1a.a;

import java.math.BigDecimal;
import java.util.ArrayList;

import qj.codejam.CodeJam;
import qj.util.MathUtil;
import qj.util.funct.F0;
import qj.util.funct.F1;

public class Bullseye {
	public static void main(String[] args) {
//		String resName = "sample.in";
//		String resName = "A-small-practice.in";
		String resName = "A-large-practice.in";
		CodeJam.solver(resName, new F1<F0<String>, F0<String>>() {public F0<String> e(F0<String> lineF) {
			ArrayList<BigDecimal> bigs = CodeJam.toBigs(lineF.e().split(" "));
			final BigDecimal r = bigs.get(0);
			final BigDecimal t = bigs.get(1);
			return new F0<String>() {public String e() {
				BigDecimal n = MathUtil.findIntegralMatch(BigDecimal.ZERO, t, new F1<BigDecimal, Integer>() {public Integer e(BigDecimal n) {
					if (area(n, r).compareTo(t) > 0) {
						return -1;
					} else if (area(n.add(BigDecimal.ONE), r).compareTo(t) <= 0) {
						return 1;
					}
					return 0;
				}});
				return "" + n;
			}};
		}})
		.solve();
	}
	
	public static BigDecimal area(BigDecimal n, BigDecimal r) {
//		return n * (2*r + 1) + 4*(n-1) * n/2;
//		return 2*r*n + 2nn-n;
		
		return n.multiply(r.multiply(BigDecimal.valueOf(2)).add(BigDecimal.ONE))
				.add(BigDecimal.valueOf(4).multiply(n.subtract(BigDecimal.ONE)).multiply(n).divide(BigDecimal.valueOf(2)));
	}
//	static class Test {
//		public static void main(String[] args) {
//			System.out.println(area(BigDecimal.valueOf(2), BigDecimal.ONE));
//		}
//	}
}

//n(2r+1) + 0+4+...
//2r + 1 + 2r + 1 + 4 == t
