package qj.codejam.y2014.r1a.c;

import java.util.ArrayList;

import qj.codejam.CodeJam;
import qj.util.funct.F0;
import qj.util.funct.F1;

public class ProperShuffle {
	public static void main(String[] args) {
//		String resName = "sample.in";
		String resName = "C-small-attempt1.in";
		CodeJam.solver(resName, new F1<F0<String>, F0<String>>() {
			public F0<String> e(F0<String> lineF) {
				lineF.e();
				ArrayList<Integer> sequence = CodeJam.toInts(lineF.e());
				return new F0<String>() {
					public String e() {
						return cal(sequence) ? "GOOD" : "BAD";
					}
				};
			}
		}).solve();
	}
	
	static boolean cal(ArrayList<Integer> sequence) {
		double diff = diff (sequence);
		
		return diff < 0.6;
	}

	private static double diff(ArrayList<Integer> sequence) {
		int count = 0;
		for (int i = 0; i < sequence.size() - 1; i++) {
			if (sequence.get(i) < sequence.get(i + 1)) {
				count++;
			}
		}
		int total = sequence.size()-1;
		return (double)count / total;
	}
}
