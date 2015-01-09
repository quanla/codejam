package qj.codejam.y2013.r2.b;

import qj.codejam.CodeJam;

/**
 * TODO Not done
 * @author Quan
 *
 */
public class ManyPrizes {
	public static void main(String[] args) {
//		String resName = "sample.in";
		String resName = "B-small-practice.in";
		CodeJam.solver(resName, lineF -> {
			String[] split = lineF.e().split(" ");
			int n = Integer.parseInt(split[0]);
			int p = Integer.parseInt(split[1]);
			return () -> cal(n, p);
		})
		.solve();
	}
	
	static String cal(int n, int p) {
		return "" + sure(n, p) + " " + (int)(Math.pow(2, n) - sure(n, (int) (Math.pow(2, n)-p)) - 2);
	}

	private static int sure(int n, int p) {
		/**
		 * p = 1 maxL=0 -> 0 max 0 L 
		 * p = 2 maxL=0 -> 1 max 1 L
		 * p = 3 maxL=0 -> 2 max 1 L
		 * p = 4 maxL=0 -> 3 max 1 L
		 * p = 5 maxL=1 -> 4 max 1 L
		 * p = 6 maxL=1 -> 5 max 2 L
		 * p = 7 maxL=2 -> 6 max 2 L
		 * p = 8 maxL=3 -> 7 max 3 L
		 */
		int maxL = maxL((int) Math.pow(2, n), p);
//		System.out.println("maxL=" + maxL);
		return maxNumForMaxL(maxL, n);
	}

	private static int maxNumForMaxL(int maxL, int n) {
		if (maxL == n) {
			return (int)(Math.pow(2, n) - 1);
		}
		return (int)(Math.pow(2, maxL + 1) - 2);
	}

	private static int maxL(int max, int p) {
//		if (p>=max) {
//			p=max-1;
//		}
		for (int i = 0;;i++) {
			max /=2;
			if (p <= max || max==0) {
				return i;
			}
			p-=max;
		}
	}
	
	public static class Temp {
		public static void main(String[] args) {
			System.out.println(maxL(8, 7));
		}
	}
}
