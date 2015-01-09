package qj.codejam.y2014.r1b.a;

import java.util.LinkedList;
import java.util.List;

import qj.codejam.CodeJam;
import qj.util.Cols;
import qj.util.StringUtil;

public class TheRepeater {
	public static void main(String[] args) {
//		String resName = "sample.in";
//		String resName = "A-small-attempt0.in";
		String resName = "A-large.in";
		CodeJam.solver(resName, lineF -> {
			int n = Integer.parseInt(lineF.e());
			LinkedList<String> strs = new LinkedList<>();
			for (int i = 0; i < n; i++) {
				strs.add(lineF.e());
			}
			return () -> {
				Integer cal = cal(strs);
				return cal == null ? "Fegla Won" : "" + cal;
			};
		}).solve();
	}
	
	static Integer cal(List<String> strs) {
		int total = 0;
		for (int i = 0;StringUtil.isNotEmpty(Cols.getSingle(strs));) {
			char c = Cols.getSingle(strs).charAt(i);
			Integer fix = fix(c, strs);
			if (fix == null) {
				return null;
			}
			strs = nextChars(strs);
			total += fix;
		}
		
		for (String string : strs) {
			if (StringUtil.isNotEmpty(string)) {
				// If any has char left return null;
				return null;
			}
		}
		
		return total;
	}

	private static Integer fix(char c, List<String> strs) {
		int total = 0;
		for (String string : strs) {
			if (!string.startsWith( "" + c)) {
				return null;
			}
			total += count(c, string);
		}
		
		int changes = 0;
		long avg = Math.round((double)total / strs.size());
		for (String string : strs) {
			
			changes += Math.abs(count(c, string) - avg);
		}
		return changes;
	}

	private static int count(char c, String s) {
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) != c) {
				return i;
			}
		}
		return s.length();
	}

	private static List<String> nextChars(List<String> strs) {
		return Cols.yield(strs, s -> {
			char c = s.charAt(0);
			int i;
			for (i = 0; i < s.length(); i++) {
				if (s.charAt(i) != c) {
					break;
				}
			}
			return s.substring(i);
		});
	}

}
