package qj.codejam.y2011.qualification.c;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import qj.codejam.CodeJam;
import qj.util.MathUtil;
import qj.util.funct.F0;
import qj.util.funct.F1;

public class CandySplitting {
	public static void main(String[] args) {
		CodeJam.solver("sample.in", new F1<F0<String>, F0<String>>() {public F0<String> e(F0<String> lineF) {
			lineF.e();
			final LinkedList<Integer> bag = new LinkedList<Integer>();
			for (String string : lineF.e().split(" ")) {
				bag.add(Integer.valueOf(string));
			}
			return new F0<String>() {public String e() {
				if (isBinaryEqual(bag)) {
					return "" + (MathUtil.sumI(bag) - MathUtil.minI(bag));
				} else {
					return "NO";
				}
			}};
		}})
		.solve();
	}
	static boolean isBinaryEqual(Collection<Integer> bag) {
		Patrick patrick = new Patrick();
		for (Integer val : bag) {
			patrick.add( Integer.toBinaryString(val) );
		}
		
		return patrick.isEqual();
	}
	static class Patrick {
		ArrayList<boolean[]> total = new ArrayList<boolean[]>();
		
		public void add(String binaryString) {
			assureLength(binaryString.length());
			
			for (int i = 0; i < binaryString.length(); i++) {
				if (binaryString.charAt(i) == '1') {
					boolean[] bs = total.get(total.size() - binaryString.length() + i);
					bs[0] = !bs[0];
				}
			}
		}

		private void assureLength(int length) {
			int diff = length - total.size();
			for (int i = 0; i < diff; i++) {
				total.add(0, new boolean[] {false});
			}
		}

		public boolean isEqual() {
			for (boolean[] bs : total) {
				if (bs[0] == true) {
					return false;
				}
			}
			return true;
		}
		
	}
}
