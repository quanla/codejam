package qj.codejam.y2011.qualification.d;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import qj.codejam.CodeJam;
import qj.util.Cols;
import qj.util.MathUtil;
import qj.util.funct.F0;
import qj.util.funct.F1;

public class GoroSort {
	public static void main(String[] args) {
		String resName = "sample.in";
//		String resName = "test.in";
		CodeJam.solver(resName, new F1<F0<String>, F0<String>>() {public F0<String> e(F0<String> lineF) {
			lineF.e();
			final ArrayList<Integer> array = new ArrayList<Integer>();
			for (String string : lineF.e().split(" ")) {
				array.add(Integer.valueOf(string));
			};
			
			return new F0<String>() {public String e() {
				// Main task: Find all groups
				Collection<Set<Integer>> groups = findAllGroups(array);
				
//				System.out.println("groups=" + groups);
				
				return "" + MathUtil.sum(groups, new F1<Set<Integer>,Double>() {public Double e(Set<Integer> group) {
					return changes(group.size());
				}});
			}};
		}})
		.solve();
	}
	
	static Collection<Set<Integer>> findAllGroups(List<Integer> array) {
		List<Integer> sorted = Cols.sort(new ArrayList<Integer>(array));
		
		LinkedList<Set<Integer>> groups = new LinkedList<Set<Integer>>();
		for (int i = 0; i < array.size(); i++) {
			if (inGroup(i, groups)) {
				continue;
			}
			
			Set<Integer> detectGroup = detectGroup(i, array, sorted);
			if (detectGroup.size() == 1) {
				// Good number
				continue;
			}
			groups.add(detectGroup);
		}
		return groups;
	}
	
	private static Set<Integer> detectGroup(int index, List<Integer> array,
			List<Integer> sorted) {
		
		return findLoop(index, array, sorted);
		
	}

	public static Set<Integer> findLoop(int index, List<Integer> array,
			List<Integer> sorted) {
		// Where it suppose to be?
		
		TreeSet<Integer> ret = new TreeSet<Integer>();
		ret.add(index);
		int closeIndex = index;
		
		for (;;) {
			int shouldBeAtIndex = sorted.indexOf(array.get(index));
			ret.add(shouldBeAtIndex);
			
			if (sorted.indexOf(array.get(shouldBeAtIndex)) == closeIndex) {
				// Can close loop
				return ret;
			} else {
				index = shouldBeAtIndex;
			}
		}
	}

	private static boolean inGroup(int index, Collection<Set<Integer>> groups) {
		for (Set<Integer> set : groups) {
			if (set.contains(index)) {
				return true;
			}
		}
		return false;
	}

	static double changes(int i) {
		double total = 1;
		for (int j = 0; j < i; j++) {
			total *= i - j;
		}
		return total;
	}
}
