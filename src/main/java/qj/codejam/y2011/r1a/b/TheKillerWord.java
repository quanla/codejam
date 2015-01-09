package qj.codejam.y2011.r1a.b;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import qj.codejam.CodeJam;
import qj.util.Cols;
import qj.util.funct.Douce;
import qj.util.funct.P1;

public class TheKillerWord {
	public static void main(String[] args) {
//		String resName = "test.in";
		String resName = "sample.in";
//		String resName = "B-small-practice.in";
//		String resName = "B-large-practice.in";
		CodeJam.solver(resName, lineF -> {
			String[] split = lineF.e().split(" ");
			int dicCount = Integer.parseInt(split[0]);
			int sequenceCount = Integer.parseInt(split[1]);
			
			LinkedList<String> words = new LinkedList<>();
			LinkedList<String> sequences = new LinkedList<>();
			for (int i = 0; i < dicCount; i++) {
				words.add(lineF.e());
			}
			for (int i = 0; i < sequenceCount; i++) {
				sequences.add(lineF.e());
			}
			return () -> Cols.join(cal(words, sequences), " ");
		}).solve(2);
	}
	
	static List<String> cal(LinkedList<String> words, LinkedList<String> sequences) {
		return Cols.yield(sequences, (sequence) -> cal(words, sequence));
	}
	
	static String cal(Collection<String> words, String sequence) {
		int[] max = {-1};
		String[] ret = {null};
		P1<Douce<String, Integer>> accept = acceptF(max, ret, sequence);
		for (Collection<String> wordGroup : Cols.group(words, (word) -> word.length()).values()) {
			Douce<String, Integer> calGroup = calGroup(wordGroup, sequence);
			accept.e(calGroup);
		}
//		System.out.println("max=" + max[0]);
		return ret[0];
		
	}

	private static P1<Douce<String, Integer>> acceptF(int[] max, String[] ret, String sequence) {
		P1<Douce<String, Integer>> accept = new P1<Douce<String, Integer>>() {public void e(Douce<String, Integer> calGroup) {
//			if (calGroup.get1().equals("pepper")) {
//				calGroup.set2(0);
//			}
			if (max[0] == 0+ calGroup.get2()) {
				if (compareBySequence(ret[0], (calGroup.get1()), sequence) ) {
					ret[0] = calGroup.get1();
				} else {
					;
				}
			} else if (max[0] < calGroup.get2()) {
				max[0] = calGroup.get2();
				ret[0] = calGroup.get1();
			} else {
				;
			}
		}};
		return accept;
	}

	protected static boolean compareBySequence(String string, String string2,
			String sequence) {
//		return string.compareTo(string2) > 0;
		for (int i=0;;i++) {
			if (i== string.length()) {
				return false;
			}
			if (i== string2.length()) {
				return true;
			}
			int i1 = sequence.indexOf(string.charAt(i));
			int i2 = sequence.indexOf(string2.charAt(i));
			
			if (i1==i2) {
				continue;
			}
			return i1 > i2;
		}
	}

	private static Douce<String,Integer> calGroup(Collection<String> wordGroup, String sequence) {
		
		if (sequence.length() == 0) {
			if (Cols.isEmpty(wordGroup) || wordGroup.size() > 2) {
				throw new RuntimeException();
			}
			return new Douce<>(Cols.getSingle(wordGroup), 0);
		}
		if (Cols.isEmpty(wordGroup)) {
			throw new RuntimeException();
		}
		if (wordGroup.size() == 1) {
			return new Douce<>(Cols.getSingle(wordGroup), 0);
		}
		
		int[] max = {-1};
		String[] ret = {null};
		P1<Douce<String, Integer>> accept = acceptF(max, ret, sequence);
		
		for (int i = 0; i < sequence.length(); i++) {
			String c = "" + sequence.charAt(i);
			
			// Proceed?
			if (!isValidChar(c, wordGroup)) {
				continue;
			}
			
			int iF = i;
			eachCaseRight(c, wordGroup, new P1<Collection<String>>() {public void e(Collection<String> rightGroup) {
				accept.e(calGroup(rightGroup, sequence.substring(iF + 1)));
			}});
			
			Collection<String> whenWrong = whenWrong(c, wordGroup);
			if (Cols.isNotEmpty(whenWrong)) {
				Douce<String, Integer> calGroup = calGroup(whenWrong, sequence.substring(i + 1));
				accept.e(new Douce<>(calGroup.get1(), calGroup.get2() + 1));
			}
			
			return new Douce<>(ret[0], max[0]);
		}
		
		throw new RuntimeException();
	}

	private static Collection<String> whenWrong(String c, Collection<String> wordGroup) {
		return Cols.filter(wordGroup, (word) -> !word.contains(c));
	}

	private static void eachCaseRight(String c, Collection<String> wordGroup,
			P1<Collection<String>> p1) {
		for (Collection<String> rightGroup : Cols.group(wordGroup, (word) -> happens(c, word)).values()) {
			p1.e(rightGroup);
		};
	}

	private static List<Integer> happens(String c, String word) {
		LinkedList<Integer> ret = new LinkedList<>();
		for (int index = 0;;) {
			int i = word.indexOf(c, index);
			if (i==-1) {
				return ret;
			}
			ret.add(i);
			index = i + 1;
		}
	}

	private static boolean isValidChar(String c, Collection<String> wordGroup) {
		for (String word : wordGroup) {
			if (word.contains(c)) {
				return true;
			}
		}
		return false;
	}
}
