package qj.codejam.y2014.qualification.c;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import qj.codejam.CodeJam;
import qj.codejam.CodeJam.Solver;
import qj.util.Cols;
import qj.util.SystemUtil;
import qj.util.funct.F0;
import qj.util.funct.F1;
import qj.util.funct.F3;
import qj.util.funct.Fs;
import qj.util.math.Dimension;
import qj.util.math.Matrix2;
import qj.util.math.Point;
import qj.util.math.Rectangle;

/**
 * Bad source !!!
 */
public class MinesweeperMaster {

	static LinkedList<F3<Integer, Integer, Integer, List<List<Boolean>>>> strategies = new LinkedList<>();
	static {
//		strategies.add(MinesweeperMaster::simple);
		strategies.add(MinesweeperMaster::top);
		strategies.add(MinesweeperMaster::left);
		strategies.add(StrategyBothway::both);
	}
	public static void main(String[] args) {
//		String resName = "sample.in";
//		String resName = "test.in";
		String resName = "C-small-practice.in";
		Solver solver = CodeJam.solver(resName, (F0<String> lineF) -> {
			ArrayList<Integer> ints = CodeJam.toInts(lineF.e());
			int R = ints.get(0);
			int C = ints.get(1);
			int M = ints.get(2);
			return () -> {
				return cal(R,C,M);
			};
		});
//		solver.solve(53);
		solver.solve();
//		for (int i = 34; ; i++) {
//			solver.solve(i);
//			SystemUtil.readLine();
//		}
	}
	
	static String cal(int r, int c, int m) {
		
		for (F3<Integer, Integer, Integer, List<List<Boolean>>> strategy : strategies) {
			List<List<Boolean>> result = strategy.e(r, c, m);
			if (result != null) {
				Point clicked = findC(result, r*c-m == 1);
				if (clicked != null) {
					return show(result, clicked);
				}
			}
		}
		return "\nImpossible";
	}

	private static String show(List<List<Boolean>> list2, Point c) {
		List<List<String>> strList2 = new LinkedList<>();
		for (int y = 0; y < list2.size(); y++) {
			List<Boolean> line = list2.get(y);
			
			List<String> strLine = new LinkedList<>();
			for (int x = 0; x < line.size(); x++) {
				boolean hasMine = line.get(x);
				strLine.add(hasMine ? "*" : (c.x == x && c.y==y) ? "c" : ".");
			}
			strList2.add(strLine);
		}

		return "\n" + Cols.join(Cols.yield(strList2, Cols.<String>joinF("")), "\n");
	}

	private static Point findC(List<List<Boolean>> list2, boolean only1) {
		if (only1) {
			for (int y = 0; y < list2.size(); y++) {
				List<Boolean> line = list2.get(y);
				for (int x = 0; x < line.size(); x++) {
					Boolean hasMine = line.get(x);
					if (!hasMine) {
						return new Point(x, y);
					}
				}
			}
		}
		Dimension dimension = new Dimension(list2.get(0).size(), list2.size());
		Rectangle rect1 = new Rectangle(new Point(0, 0), dimension);
		Point[] found = new Point[] {null};
		Matrix2.each(rect1.getSize(), ( p) -> {
			boolean[] hasMine = {false};
			if (list2.get(p.y).get(p.x)) {
				hasMine[0] = true;
			}
			Matrix2.eachAround(p, rect1.getSize(), ( pAround) -> {
				if (!rect1.contains(pAround)) {
					return;
				}
				if (list2.get(pAround.y).get(pAround.x)) {
					hasMine[0] = true;
				}
			});
			if (!hasMine[0]) {
				found[0] = p;
			}
			return found[0] != null;
		});
		return found[0];
	}

	private static List<List<Boolean>> left(int r, int c, int m) {
		System.out.println("left...");
		return flipDia(top(c, r, m));
	}
	private static <A> List<List<A>> flipDia(List<List<A>> matrix) {
		if (matrix==null) {
			return null;
		}
		ArrayList<List<A>> ret = new ArrayList<>();
		for (int i = 0; i < matrix.get(0).size(); i++) {
			ret.add(new ArrayList<>());
		}
		for (List<A> line : matrix) {
			for (int x = 0; x < line.size(); x++) {
				A a = line.get(x);
				ret.get(x).add(a);
			}
		}
		return ret;
	}

	private static List<List<Boolean>> top(int r, int c, int m) {
		System.out.println("top...");
		int notM = r*c - m;

		if (r*c-m == 1) {
			return fillTop(r, c, m);
		}
		
		if (c > 1 && notM < c*2) {
			return null;
		}
		
		int left = notM % c;
		if (left != 1) {
			return fillTop(r, c, m);
		} else {
			if (c==2) {
				return null;
			}
			List<List<Boolean>> ret = fillTop(r, c, m - 1);
			if (!ret.get(0).get(0)) {
				ret.get(0).set(0, true);
			}
			return ret;
		}
	}
	private static List<List<Boolean>> fillTop(int r, int c, int m) {
		int notM = r*c - m;
		LinkedList<List<Boolean>> ret = new LinkedList<>();
		for (int y = 0; y < r; y++) {
			LinkedList<Boolean> line = new LinkedList<>();
			for (int x = 0; x < c; x++) {
				if (notM > 0) {
					notM --;
					line.add(false);
				} else {
					line.add(true);
				}
			}
			ret.add(line);
		}
		return ret;
	}

}
