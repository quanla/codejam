package qj.codejam.y2013.r1b.c;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import qj.codejam.CodeJam;
import qj.util.Cols;
import qj.util.DebugUtil;
import qj.util.StringChange;
import qj.util.StringUtil;
import qj.util.funct.F0;
import qj.util.funct.F1;
import qj.util.funct.Fs;
import qj.util.funct.P1;

/**
 * Check with analysis if this solution is best
 * @author quanle
 *
 */
public class GarbledEmail {
	public static void main(String[] args) {
		String file = "garbled_email_dictionary.txt";
//		String file = "test_dictionary.txt";

//		String resName = "sample.in";
//		String resName = "test.in";
		String resName = "C-small-practice.in";
		
		final Matcher matcher = getMatcher(file);
		CodeJam.solver(resName, new F1<F0<String>, F0<String>>() {public F0<String> e(F0<String> lineF) {
			final String line = lineF.e();
			return new F0<String>() {public String e() {
				return "" + minChanges(0, -10, line, null, matcher);
			}};
		}})
		.solve();
		
	}
	
	static Integer minChanges(int index, int lastWrong, String line, Integer notMoreChangesThan, Matcher matcher) {
		if (index== line.length()) {
			return 0;
		}
		Integer[] min = {null};
		for (int i = 1; i < 10 && line.length() >= index+i; i++) {
			final int i1 = i;
			eachStar(lastWrong + 5, line.substring(index, index+i), new F1<String,Boolean>() {public Boolean e(String stared) {
				if (matcher.index.contains(stared)) {
					
					int wrongCount = StringUtil.countHappens('*', stared);
					if (notMoreChangesThan != null && wrongCount > notMoreChangesThan) {
						return true;
					}
					Integer nextChanges = minChanges(index + i1, stared.lastIndexOf('*')-stared.length(), line, min[0] == null ? null : min[0] - wrongCount, matcher);
					int min1;
					if (nextChanges == null) {
						return false;
//						min1 = wrongCount;
					} else {
						min1 = wrongCount + nextChanges;
					}
					min[0] = min[0]==null ? min1 : Math.min(min[0], min1);
					
					return true;
				}
				
				return false;
			}});
		}
		return min[0];
	}

	public static Matcher getMatcher(String file) {
		try {
			InputStream in = GarbledEmail.class.getResourceAsStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			Matcher matcher = new Matcher();
			for (String str;(str=br.readLine()) != null;) {
				matcher.addWord(str);
			}
			in.close();
			return matcher;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static void eachStar(int from, String in, F1<String, Boolean> p1) {
		
		each5(from, in.length(), new F1<List<Integer>,Boolean>() {public Boolean e(List<Integer> list) {
			List<StringChange> changes = Cols.yield(list, obj -> StringChange.replace(obj, obj+1, "*"));
			return p1.e(StringChange.apply(changes, in));
		}});
	}

	private static void each5(int from, int length, F1<List<Integer>,Boolean> f1) {
		LinkedList<Integer> list = new LinkedList<>();
		f1.e(list);
		
		each5(from, length, list, new F1<List<Integer>,Boolean>() {public Boolean e(List<Integer> list2) {
			
			if (f1.e(list2)) {
				return true;
			}
			if (each5(list.get(list.size() - 1) + 5, length, list, this)) {
				return true;
			}
			
			return false;
		}});
	}

	private static boolean each5(int from, int length, LinkedList<Integer> list,
			F1<List<Integer>,Boolean> f1) {
		for (int i = Math.max(from, 0); i < length; i++) {
			list.add(i);
			if (f1.e(list)) {
				return true;
			}
			list.removeLast();
		}
		return false;
	}

	static class Matcher implements Serializable {

		HashSet<String> index = new HashSet<>();
		public void addWord(String str) {
			eachStar(0, str, Fs.f1(Fs.store(index), false));
		}
	}
}
