package qj.codejam.y2012.qualification.b;

import java.util.LinkedList;

import qj.codejam.CodeJam;
import qj.util.Cols;
import qj.util.funct.F0;
import qj.util.funct.F1;

public class DancingWithTheGooglers {
	public static void main(String[] args) {
		CodeJam.solver("sample.in", new F1<F0<String>, F0<String>>() {public F0<String> e(F0<String> lineF) {
			String[] split = lineF.e().split(" ");
//			int count = Integer.parseInt(split[0]);
			final int surp = Integer.parseInt(split[1]);
			final int least = Integer.parseInt(split[2]);
			final LinkedList<Integer> col = new LinkedList<Integer>();
			for (int i = 3; i < split.length; i++) {
				col.add(Integer.valueOf(split[i]));
			}
			return new F0<String>() {public String e() {
				int sure = Cols.count(col, new F1<Integer, Boolean>() {public Boolean e(Integer obj) {
					return obj >= least * 3 - 2;
				}});
				
				int needSurp = Cols.count(col, new F1<Integer, Boolean>() {public Boolean e(Integer obj) {
					return obj > 0 && obj < least * 3 - 2 && obj >= least * 3 - 4;
				}});
				return "" + (sure + Math.min(needSurp, surp));
			}};
		}})
		.solve();
	}
}
