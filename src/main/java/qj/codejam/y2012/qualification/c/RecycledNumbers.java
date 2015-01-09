package qj.codejam.y2012.qualification.c;

import java.util.HashSet;
import java.util.List;

import qj.codejam.CodeJam;
import qj.util.Cols;
import qj.util.LangUtil;
import qj.util.funct.F0;
import qj.util.funct.F1;
import qj.util.funct.P1;

public class RecycledNumbers {
	public static void main(String[] args) {
		CodeJam.solver("sample.in", new F1<F0<String>, F0<String>>() {public F0<String> e(F0<String> lineF) {
			int[] split = LangUtil.parseInts(lineF.e().split(" "));
			final int min = split[0];
			final int max = split[1];
			
			return new F0<String>() {public String e() {
				final int[] count = {0};
				for (int n = min; n < max; n++) {
					final int fn = n;
					eachRecycledNum("" + n, new P1<Integer>() {public void e(Integer recycled) {
						if (recycled > fn && recycled <= max) {
							count[0]++;
						}
					}});
				}
				return "" + count[0];
			}};
		}})
		.solve();
	}


	@SuppressWarnings("unchecked")
	private static void eachRecycledNum(String string,
			final P1<Integer> p1) {
		
		List<Integer> oriNum = Cols.yield(LangUtil.toObjArr(string.toCharArray()), new F1<Character,Integer>() {public Integer e(Character c) {
			return c.charValue() - '0';
		}});
		
		HashSet<Integer> notSuitable = new HashSet<Integer>();
		notSuitable.add(LangUtil.join(oriNum));
		for (int i = 1; i < oriNum.size(); i++) {
			int num = LangUtil.join(Cols.join(oriNum.subList(i, oriNum.size()), oriNum.subList(0, i)));
			if (notSuitable.add(num)) {
				p1.e(num);
			}
		}
	}
}
