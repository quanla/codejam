package qj.codejam.y2014.qualification.a;

import java.util.ArrayList;

import qj.codejam.CodeJam;
import qj.util.Cols;
import qj.util.funct.F0;

public class MagicTrick {
	public static void main(String[] args) {
//		String resName = "sample.in";
		String resName = "A-small-attempt0.in";
		CodeJam.solver(resName, (F0<String> lineF) -> {
			int firstAnswer = Integer.parseInt(lineF.e());
			ArrayList<ArrayList<Integer>> rows1 = CodeJam.toInts(lineF, 4);
			int secondAnswer = Integer.parseInt(lineF.e());
			ArrayList<ArrayList<Integer>> rows2 = CodeJam.toInts(lineF, 4);
			return () -> {
				return cal(firstAnswer, rows1, secondAnswer, rows2);
			};
		})
		.solve();
	}
	
	static String cal(int firstAnswer, ArrayList<ArrayList<Integer>> rows1, int secondAnswer, ArrayList<ArrayList<Integer>> rows2) {
		ArrayList<Integer> ans1 = rows1.get(firstAnswer - 1);
		ArrayList<Integer> ans2 = rows2.get(secondAnswer - 1);
		
		ans1.retainAll(ans2);
		
		// Check if each 2 ans meet in 1: return
		if (ans1.size() == 1) {
			return "" + Cols.getSingle(ans1);
		} else if (ans1.size() == 0) {
			return "Volunteer cheated!";
		} else {
			return "Bad magician!";
		}
	}
}
