package qj.codejam.y2011.r1c.c;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import qj.codejam.CodeJam;
import qj.util.MathUtil;
import qj.util.funct.F1;

public class PerfectHarmony {
	public static void main(String[] args) {
		String resName = "sample.in";
		CodeJam.solver(resName, lineF -> {
			ArrayList<BigInteger> bigInts = CodeJam.toBigInts(lineF.e());
			BigInteger l = bigInts.get(1);
			BigInteger h = bigInts.get(2);
			
			ArrayList<BigInteger> fs = CodeJam.toBigInts(lineF.e());
			
			return () -> {
				BigInteger cal = cal(l, h, fs);
				return cal==null ? "NO" : "" + cal;
			};
		}).solve();
	}
	static BigInteger cal(BigInteger l, BigInteger h, ArrayList<BigInteger> fs) {
		Collections.sort(fs);
		if (!fs.get(0).equals(BigInteger.ONE)) {
			fs.add(0, BigInteger.ONE);
		}
		
		if (l.equals(BigInteger.ONE)) {
			return l;
		}
		
		F1<Integer,BigInteger> gcd = gcd(fs);
		F1<Integer,BigInteger> lcm = lcm(fs);
		
		for (int i = 0; i < fs.size() - 1; i++) {
			
			// Find if F can be between f[i] and f[i+1]?
			
			if (fs.get(i).compareTo(h) > 0) {
				continue;
			}
			if (fs.get(i + 1).compareTo(l) < 0) {
				continue;
			}
			
			BigInteger lcm1 = lcm.e(i);
			BigInteger gcd1 = gcd.e(i + 1);
			
			if (!gcd1.remainder(lcm1).equals(BigInteger.ZERO)) {
				continue;
			}
			
			BigInteger between = between(lcm1, gcd1, l, h);
			
			if (between != null) {
				return between;
			}
		}
		;
		return between(gcd.e(1), null, l, h);

	}
	private static BigInteger between(BigInteger gcd1, BigInteger lcm1, BigInteger l,
			BigInteger h) {
		BigInteger i = gcd1;
		for (;;) {
			if (i.compareTo(l) >= 0 && i.compareTo(h) <= 0) {
				return i;
			}
			
			for (;;) {
				i = i.add(gcd1);
				if (lcm1 != null) {
					if (!lcm1.remainder(i).equals(BigInteger.ZERO)) {
						continue;
					}
				}
				if (i.compareTo(h) >0) {
					return null;
				}
				break;
			}
		}
	}
	
	public static class Temp {
		public static void main(String[] args) {
			System.out.println(between(
					BigInteger.valueOf(10),
					BigInteger.valueOf(20),
					BigInteger.valueOf(8),
					BigInteger.valueOf(16)
					));
		}
	}
	
	private static F1<Integer, BigInteger> lcm(ArrayList<BigInteger> fs) {
		BigInteger lcm = BigInteger.valueOf(1);
		ArrayList<BigInteger> ret = new ArrayList<BigInteger>(fs.size());
		for (int i = 0; i < fs.size(); i++) {
			lcm = MathUtil.lcm(lcm, fs.get(i));
			ret.add(lcm);
		}
//		System.out.println("lcm=" + ret);
		return obj -> ret.get(obj);
	}
	
	private static F1<Integer, BigInteger> gcd(ArrayList<BigInteger> fs) {
		BigInteger gcd = null;
		LinkedList<BigInteger> ret = new LinkedList<BigInteger>();
		for (int i = 0; i < fs.size(); i++) {
			BigInteger val = fs.get(fs.size() - 1 - i);
			gcd = gcd==null ? val : gcd.gcd( val);
			ret.add(0, gcd);
		}
//		System.out.println("gcd=" + ret);
		return obj -> ret.get(obj);
	}
}
