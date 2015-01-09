package qj.codejam.y2013.qualification.b;

import java.util.ArrayList;
import java.util.List;

import qj.codejam.CodeJam;
import qj.util.funct.F0;
import qj.util.funct.F1;
import qj.util.math.Dimension;
import qj.util.math.Matrix2;
import qj.util.math.Point;

public class Lawnmower {
	public static void main(String[] args) {
		CodeJam.solver("sample.in", new F1<F0<String>, F0<String>>() {public F0<String> e(F0<String> lineF) {
			int rows = Integer.parseInt(lineF.e().split(" ")[0]);
			
			final Lawn lawn = new Lawn();
			for (int i = 0; i < rows; i++) {
				lawn.addRow(lineF.e());
			}
			return new F0<String>() {public String e() {
				return lawn.canMow() ? "YES" : "NO";
			}};
		}})
		.solve();
	}
	
	static class Lawn {
		ArrayList<List<Integer>> rows = new ArrayList<List<Integer>>();

		public void addRow(String rowStr) {
			ArrayList<Integer> row = new ArrayList<Integer>();
			for (int i = 0; i < rowStr.length(); i++) {
				row.add(rowStr.charAt(i) - '0');
			}
			rows.add(row);
		}

		public boolean canMow() {
			return !Matrix2.each(getSize(), new F1<Point,Boolean>() {public Boolean e(Point p) {
				return !canMow(p);
			}});
		}

		protected Integer getHeight(Point p) {
			return rows.get(p.y).get(p.x);
		}

		private Dimension getSize() {
			return new Dimension(rows.get(0).size(), rows.size());
		}

		public Boolean canMow(Point p) {
			Integer height = getHeight(p);
			
			// Row
			boolean canUseRow = true;
			for (Integer i : rows.get(p.y)) {
				if (i > height) {
					canUseRow = false;
					break;
				}
			}
			if (canUseRow) {
				return true;
			}
			
			// Col
			for (List<Integer> row : rows) {
				if (row.get(p.x) > height) {
					return false;
				}
			}
			return true;
		}
		
	}
}
