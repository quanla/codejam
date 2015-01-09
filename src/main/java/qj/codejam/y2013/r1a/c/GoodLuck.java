package qj.codejam.y2013.r1a.c;

import java.util.ArrayList;
import java.util.List;

import qj.codejam.CodeJam;
import qj.util.Cols;
import qj.util.MathUtil;
import qj.util.funct.Douce;
import qj.util.funct.F0;
import qj.util.funct.F1;

public class GoodLuck {
	public static void main(String[] args) {
		final int[] count = {0};
		String resName = "sample.in";
//		String resName = "C-small-practice-1.in";
//		String resName = "C-small-practice-2.in";
		CodeJam.solver2(resName, l1 -> {
			l1.e();
			ArrayList<Integer> split = CodeJam.toInts(l1.e().split(" "));
			final int N = split.get(1);
			final int M = split.get(2);
			
			System.out.println((long)Math.pow(M, N-1));
			
			return new Douce<Integer, F1<F0<String>,F0<String>>>(split.get(0), lineF -> {
				final List<Long> products = Cols.reverse(Cols.sort(CodeJam.toLongs(lineF.e().split(" "))));
				return new F0<String>() {public String e() {
//					if (products.get(0) > Math.pow(M, N-1)) {
//						count[0]++;
//					}
					for (Long l : products) {
						System.out.println(l + "::");
						System.out.println(MathUtil.toPrimes(l));
					}
					// TODO Auto-generated method stub
					return "";
				}};
			});
		})
		.solve(1);
		System.out.println(count[0]);
	}
}
