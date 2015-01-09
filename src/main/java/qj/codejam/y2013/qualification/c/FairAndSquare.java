package qj.codejam.y2013.qualification.c;

import qj.codejam.CodeJam;
import qj.util.StringUtil;
import qj.util.funct.F0;
import qj.util.funct.F1;

public class FairAndSquare {
	public static void main(String[] args) {
		CodeJam.solver("sample.in", new F1<F0<String>, F0<String>>() {public F0<String> e(F0<String> lineF) {
			String[] split = lineF.e().split(" ");			
			final int from = Integer.parseInt(split[0]);
			final int to = Integer.parseInt(split[1]);
			return new F0<String>() {public String e() {
				int from1 = (int)Math.ceil(Math.sqrt(from));
				int to1 = (int)Math.sqrt(to);
				int count = 0;
				for (int num = from1; num <= to1; num++) {
					if (isPalindrome(num) && isPalindrome(num*num)) {
						count ++;
					}
				}
				return "" + count;
			}};
		}})
		.solve();
	}


	private static boolean isPalindrome(int num) {
		String str = "" + num;
		return str.equals("" + Integer.parseInt(StringUtil.reverse(str)));
	}
}
