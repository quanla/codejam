package qj.codejam.y2014.r1b.b;

import qj.codejam.CodeJam;

public class NewLotteryGame {
	public static void main(String[] args) {
		String resName = "sample.in";
//		String resName = "B-small-attempt3.in";
		CodeJam.solver(resName, lineF -> {
			String[] split = lineF.e().split(" ");
			int a = Integer.parseInt(split[0]);
			int b = Integer.parseInt(split[1]);
			int k = Integer.parseInt(split[2]);
			return () -> "" + cal(a,b,k);
		}).solve();
	}
	
	static int cal(int a, int b, int k) {
		return a*Math.min(b, k) +
				b*Math.min(a, k) -
				Math.min(b, k)*Math.min(a, k) + other(a,b,k);
	}

	private static int other(int a, int b, int k) {
		int count = 0;
		for (int a1 = k; a1 < a; a1++) {
			count += other1(a1, b, k);
		}
		return count;
	}

	private static int other1(int a1, int b, int k) {
		
		
		// TODO Auto-generated method stub
		return 0;
	}
}
