package qj.codejam.y2009.qualification.b;

import java.util.ArrayList;
import java.util.Collection;

import qj.codejam.CodeJam;
import qj.util.Cols;
import qj.util.funct.F0;
import qj.util.funct.F1;
import qj.util.math.Point;

public class Watersheds {
	public static void main(String[] args) {
//		String resName = "sample.in";
//		String resName = "B-small-practice.in";
		String resName = "B-large-practice.in";
		CodeJam.solver(resName, new F1<F0<String>, F0<String>>() {public F0<String> e(F0<String> lineF) {
			
			String[] split = lineF.e().split(" ");
			int rows = Integer.parseInt(split[0]);
			final Map map = new Map();
			for (int i = 0; i < rows; i++) {
				String[] rowSplit = lineF.e().split(" ");
				ArrayList<Integer> alts = new ArrayList<Integer>();
				for (String altStr : rowSplit) {
					alts.add(Integer.valueOf(altStr));
				}
				map.addRow(alts);
			}
			
			return new F0<String>() {public String e() {
				return cal(map);
			}};
		}})
		.solve();
	}
	public static String cal(final Map map) {
		Collection<Point> tops = map.getUnclaimedTops();
//		System.out.println("====== tops=" + tops);
		if (Cols.isEmpty(tops)) {
			return "\n" + map.draw();
		}
		
		for (Point top : tops) {
			map.flowDown(top);
		}
		return cal(map);
	}
}
