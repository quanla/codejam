package qj.codejam.y2013.r1b.b;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import qj.codejam.CodeJam;
import qj.util.MathUtil;
import qj.util.funct.F0;
import qj.util.funct.F1;

public class FallingDiamonds {
	public static void main(String[] args) {
//		String resName = "sample.in";
		String resName = "B-small-practice.in";
//		String resName = "B-large-practice.in";
		CodeJam.solver(resName, new F1<F0<String>, F0<String>>() {public F0<String> e(F0<String> lineF) {
			ArrayList<Integer> ints = CodeJam.toInts(lineF.e().split(" "));
			final int N = ints.get(0);
			final int X = ints.get(1);
			final int Y = ints.get(2);
			return new F0<String>() {public String e() {
				return "" + cal(N,X,Y).doubleValue();
			}};
		}})
		.solve();
	}
	
	static BigDecimal cal(int n, int x, int y) {
		x = Math.abs(x);
		y = Math.abs(y);
		long pyramidSize = pyramidSize(n);
		
		if (x+y<=pyramidSize) {
			// Inside pyramid
			return BigDecimal.ONE;
		} else if (x+y >= pyramidSize+2) {
			// Far outsize pyramid
			return BigDecimal.ZERO;
		} else if (y == pyramidSize + 1) {
			return BigDecimal.ZERO;
		}
		
		int freeDiamonds = (int) (n - area(pyramidSize));
		
		int highestReach = (freeDiamonds-1);
		if (highestReach<y) {
			return BigDecimal.ZERO;
		}
		
		int minReach = (int) ((freeDiamonds - (pyramidSize + 1)) - 1);
		if (minReach >= y) {
			return BigDecimal.ONE;
		}
		
		return cal(freeDiamonds, y, pyramidSize);
	}
	
	private static BigDecimal cal(int freeDiamonds, int y, long pyramidSize) {
		BigDecimal sum = BigDecimal.ZERO;
		List<BigDecimal> fibonacci = MathUtil.getFibonacci(freeDiamonds);
		for (int i = 0; i < fibonacci.size(); i++) {
			if (i-1>=y) {
				sum=sum.add(fibonacci.get(i));
			}
		}
		return new BigDecimal(.5).pow(freeDiamonds).multiply(sum);
//		return new BigDecimal(-1);
	}

	static long pyramidSize(final int n) {
		return MathUtil.findIntegralMatchL(0, n, new F1<Long, Integer>() {public Integer e(Long halfSize) {
			long size = halfSize*2+1;
			if (area(size) > n) {
				return -1;
			} else if (area(size + 2) <= n) {
				return 1;
			}
			return 0;
		}})*2+1;
	}
	
	static long area(long size) {
		return size*(size+1)/2; 
	}
	
	static class Test {
		public static void main(String[] args) {
		}
	}
}
