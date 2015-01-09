package qj.codejam.y2014.r1a.a;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import qj.codejam.CodeJam;
import qj.util.Cols;
import qj.util.MathUtil;
import qj.util.funct.Douce;
import qj.util.funct.F0;
import qj.util.funct.F1;
import qj.util.funct.Fs;
import qj.util.funct.P1;

public class ChargingChaos {
	public static void main(String[] args) {
//		String resName = "sample.in";
//		String resName = "A-small-practice.in";
		String resName = "A-large-practice.in";
		CodeJam.solver(resName, new F1<F0<String>, F0<String>>() {
			public F0<String> e(F0<String> lineF) {
				lineF.e();//.split(" ");
				String[] outlets = lineF.e().split(" ");
				String[] devices = lineF.e().split(" ");
				return new F0<String>() {
					public String e() {
						Integer cal = cal(outlets, devices);
						return cal == null ? "NOT POSSIBLE" : "" + cal;
					}
				};
			}
		}).solve(12);
	}
	
	static Integer cal(String[] outlets, String[] devices) {
		Douce<Set<Integer>, Set<Integer>> flippables = flippable(outlets, devices);
		if (flippables == null) {
			return null;
		}

		LinkedList<Set<Integer>> col = new LinkedList<Set<Integer>>();
		Cols.eachSubSetAndSelf(flippables.get1(), Fs.store(col));
		Cols.sort(col, (e) -> e.size());
		
		
		
		F1<Set<Integer>,Boolean> check = new F1<Set<Integer>,Boolean>() {public Boolean e(Set<Integer> flippeds1) {
			Set<Integer> flippeds = Cols.merge(flippables.get2(), flippeds1);
			return match(outlets, devices, flippeds);
		}};
		
		if (check.e(Collections.emptySet())) {
			return flippables.get2().size();
		}
		
		for (Set<Integer> set : col) {
			if (check.e(set)) {
				return flippables.get2().size() + set.size();
			}
		}
		return null;
	}

	protected static boolean match(String[] outlets, String[] devices,
			Set<Integer> flippeds) {
		Set<String> flip = flip(outlets, flippeds);
		HashSet<String> dvs = new HashSet<>(Arrays.asList(devices));
		return flip.equals(dvs);
	}

	private static Set<String> flip(String[] outlets, Set<Integer> flippeds) {
		if (Cols.isEmpty(flippeds)) {
			return new HashSet<>(Arrays.asList(outlets));
		}
		HashSet<String> ret = new HashSet<>();
		for (String outlet : outlets) {
			
			ret.add(flip(outlet, flippeds));
		}
		return ret;
	}

	private static String flip(String outlet, Set<Integer> flippeds) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < outlet.length(); i++) {
			char c = outlet.charAt(i);
			if (flippeds.contains(i)) {
				sb.append(c == '0' ? '1' : '0');
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	private static Douce<Set<Integer>,Set<Integer>> flippable(String[] outlets, String[] devices) {
		HashSet<Integer> flippables = new HashSet<>();
		HashSet<Integer> mustFlip = new HashSet<>();
		for (int i = 0; i < outlets[0].length(); i++) {
			int flippable = flippable(i, outlets, devices);
			if (flippable == 0) {
				return null;
			} else if (flippable == 1) {
				flippables.add(i);
			} else if (flippable == 2) {
				mustFlip.add(i);
			} else if (flippable == 3) {
//				mustFlip.add(i); must not flip
			}
		}
		return new Douce<>(flippables, mustFlip);
	}

	private static int flippable(int i, String[] outlets, String[] devices) {
		int total = outlets.length;
		
		int oc = count0(i, outlets);
		int dc = count0(i, devices);
		
		if (oc == dc || total - oc == dc) {
			if (oc == total / 2 && total % 2 == 0) {
				return 1;
			} else if (oc == dc) {
				return 3;
			} else {
				return 2;
			}
		} else {
			return 0;
		}
	}

	private static int count0(int i, String[] outlets) {
		int count = 0;
		for (String o : outlets) {
			if (o.charAt(i) == '0') {
				count ++;
			}
		}
		return count;
	}
}
