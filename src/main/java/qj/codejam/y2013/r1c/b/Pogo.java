package qj.codejam.y2013.r1c.b;

import static java.lang.Math.*;

import java.util.ArrayList;
import java.util.LinkedList;

import qj.codejam.CodeJam;
import qj.util.Cols;
import qj.util.funct.F0;
import qj.util.funct.F1;

public class Pogo {
	public static void main(String[] args) {
//		String resName = "sample.in";
//		String resName = "B-small-practice.in";
		String resName = "B-large-practice.in";
		CodeJam.solver(resName, new F1<F0<String>, F0<String>>() {public F0<String> e(F0<String> lineF) {
			ArrayList<Integer> ints = CodeJam.toInts(lineF.e());
			int x = ints.get(0);
			int y = ints.get(1);
			return new F0<String>() {public String e() {
				int n = 0;
				int sum = 0;
				while (sum < abs(x) + abs(y) || (sum + x + y) % 2 == 1) {
					n++;
					sum+=n;
				}

//				System.out.println("n=" + n);
				int x1 = x;
				int y1 = y;
				LinkedList<String> steps = new LinkedList<>();
				for (;n>0;n--) {
					if (abs(x1) > abs(y1)) {
						if (x1>0) {
							steps.add("E");
							x1-=n;
						} else {
							steps.add("W");
							x1+=n;
						}
					} else {
						if (y1 > 0) {
							steps.add("N");
							y1-=n;
						} else {
							steps.add("S");
							y1+=n;
						}
					}
				}
//				System.out.println("x1=" + x1 + ", y1=" + y1);
				
				return Cols.join(Cols.reverse(steps), "");
			}};
		}})
		.solve();
	}
}
