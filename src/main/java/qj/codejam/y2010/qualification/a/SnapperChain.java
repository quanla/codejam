package qj.codejam.y2010.qualification.a;

import qj.codejam.CodeJam;
import qj.util.funct.F0;
import qj.util.funct.F1;

/**
 * 000
 * 100
 * 010
 * 110
 * 001
 * 101
 * 011
 * 111
 * 000
 * 10
 * 01
 * 
 * @author quanle
 *
 */
public class SnapperChain {
	public static void main(String[] args) {
//		String resName = "sample.in";
//		String resName = "A-small-practice.in";
		String resName = "A-large-practice.in";
		CodeJam.solver(resName, new F1<F0<String>, F0<String>>() {public F0<String> e(F0<String> lineF) {
			String[] split = lineF.e().split(" ");
			final int snappers = Integer.parseInt(split[0]);
			final int times = Integer.parseInt(split[1]);
			
			return new F0<String>() {public String e() {
				return (times + 1) % (int)Math.pow(2, snappers) == 0 ? "ON" : "OFF";
			}};
		}})
		.solve();
	}
}
