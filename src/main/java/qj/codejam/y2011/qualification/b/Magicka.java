package qj.codejam.y2011.qualification.b;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import qj.codejam.CodeJam;
import qj.util.Cols;
import qj.util.LangUtil;
import qj.util.funct.F0;
import qj.util.funct.F1;

public class Magicka {
	public static void main(String[] args) {
		CodeJam.solver("sample.in", new F1<F0<String>, F0<String>>() {public F0<String> e(F0<String> lineF) {
			String[] split = lineF.e().split(" ");
			int combineCount = Integer.parseInt(split[0]);
			HashMap<Set<Character>, Character> combines = new HashMap<Set<Character>, Character>();
			for (int i = 0; i < combineCount; i++) {
				String combineStr = split[ 1 + i];
				combines.put(new HashSet<Character>(Arrays.asList(LangUtil.toObjArr(combineStr.substring(0, 2).toCharArray()))), combineStr.substring(2,3).charAt(0));
			}
			
			int opposeCount = Integer.parseInt(split[1+combineCount]);
			HashSet<Set<Character>> opposeds = new HashSet<Set<Character>>();
			for (int i = 0; i < opposeCount; i++) {
				String opposeStr = split[1 + combineCount + 1 + i];
				opposeds.add(new HashSet<Character>(Arrays.asList(LangUtil.toObjArr(opposeStr.toCharArray()))));
			}
			
			final Wizard wizard = new Wizard(combines, opposeds);
			
			final String line = split[1 + combineCount + 1 + opposeCount + 1];
			
			return new F0<String>() {public String e() {
				for (char element : line.toCharArray()) {
					wizard.acquire(element);
				}
				return "[" + Cols.join(wizard.eleList, ", ") + "]";
			}};
		}})
		.solve();
	}
	
	static class Wizard {

		private HashMap<Set<Character>, Character> combines;
		private HashSet<Set<Character>> opposeds;
		LinkedList<Character> eleList = new LinkedList<Character>();

		public Wizard(HashMap<Set<Character>, Character> combines,
				HashSet<Set<Character>> opposeds) {
			
			this.combines = combines;
			this.opposeds = opposeds;
		}

		public void acquire(Character element) {
			if (eleList.isEmpty()) {
				eleList.add(element);
				return;
			}
			
			// Can combine?
			Character combine = combine(element);
			if (combine != null) {
				eleList.removeLast();
				eleList.add(combine);
				return;
			}
			
			if (hasOpposed(element)) {
				eleList.clear();
				return;
			}
			
			eleList.add(element);
		}

		private boolean hasOpposed(Character element) {
			HashSet<Character> set = new HashSet<Character>();
			set.add(element);
			for (Character inList : eleList) {
				set.add(inList);
				if (opposeds.contains(set)) {
					return true;
				}
				set.remove(inList);
			}
			return false;
		}

		private Character combine(Character element) {
			HashSet<Character> set = new HashSet<Character>();
			set.add(element);
			set.add(eleList.getLast());
			return combines.get(set);
		}
		
	}
}
