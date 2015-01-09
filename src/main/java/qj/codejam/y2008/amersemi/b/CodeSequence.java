package qj.codejam.y2008.amersemi.b;

import java.util.LinkedList;
import java.util.List;

import qj.codejam.CodeJam;
import qj.util.funct.F0;
import qj.util.funct.F1;

public class CodeSequence {
	public static void main(String[] args) {
//		String resName = "sample.in";
//		String resName = "B-small-practice.in";
		String resName = "B-large-practice.in";
		CodeJam.solver(resName, new F1<F0<String>, F0<String>>() {public F0<String> e(F0<String> lineF) {
			lineF.e(); // Not need this line
			String[] split = lineF.e().split(" ");
			final List<Integer> list = new LinkedList<Integer>();
			for (int i = 0; i < split.length; i++) {
				list.add(Integer.parseInt(split[i]));
			}
			return new F0<String>() {public String e() {
				Integer nextDifference = nextDifference(diffList(list));
				if (nextDifference == null) {
					return "UNKNOWN";
				}
				return "" + ( list.get(list.size()-1) + nextDifference) % 10007;
			}};
		}})
		.solve();
//		.solve(12);
		
	}


	private static List<Integer> diffList(List<Integer> list) {
		LinkedList<Integer> ret = new LinkedList<Integer>();
		Integer last = null;
		for (Integer integer : list) {
			if (last == null) {
				;
			} else {
				int diff = integer - last;
				if (diff < 0) {
					diff += 10007;
				}
				ret.add(diff % 10007);
			}
			last = integer;
		}
		return ret;
	}

	private static Integer nextDifference(List<Integer> list) {
		if (list.size() < 3) {
			return null;
		}
		
		Boolean isOddsSequenced = isOddsSequenced(list);
		
		if (isOddsSequenced== null) {
			return null;
		}
		
		if (isOddsSequenced) {
			if (list.size() % 2 ==0) {
				return list.get(0);
			} else {
				return nextDifference(getHalf(list, false));
			}
		} else {
			if (list.size() % 2 == 1) {
				return list.get(1);
			} else {
				return nextDifference(getHalf(list, true));
			}
		}
	}


	private static List<Integer> getHalf(List<Integer> list, boolean getOdds) {
		LinkedList<Integer> ret = new LinkedList<Integer>();
		
		for (int i = 0; i < list.size(); i++) {
			if (getOdds ^ i%2==1) {
				ret.add(list.get(i));
			}
		}
		return ret;
	}


	private static Boolean isOddsSequenced(List<Integer> list) {
		boolean allOddsSame = isSame(true, list);
		boolean allEvensSame = isSame(false, list);
		if (allOddsSame == allEvensSame) {
			return null;
		}
		return allOddsSame;
	}


	private static boolean isSame(boolean odds, List<Integer> list) {
		Integer feed = null;
		for (int i = 0; i < list.size(); i++) {
			if (odds ^ i%2==1) {
				if (feed == null) {
					feed = list.get(i);
				} else {
					if (!feed.equals(list.get(i))) {
						return false;
					}
				}
			}
		}
		return true;
	}
}
