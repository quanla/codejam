package qj.codejam.y2013.r1c.a;

import java.math.BigDecimal;
import java.util.List;

import qj.codejam.CodeJam;
import qj.util.funct.F0;
import qj.util.funct.F1;
import qj.util.funct.P1;
import qj.util.math.Range;

public class Consonants {
	public static void main(String[] args) {
		String resName = "sample.in";
//		String resName = "A-small-practice.in";
//		String resName = "A-large-practice.in";
		CodeJam.solver(resName, new F1<F0<String>, F0<String>>() {public F0<String> e(F0<String> lineF) {
			String[] split = lineF.e().split(" ");
			final String word = split[0];
			final int n = Integer.parseInt(split[1]);
			return new F0<String>() {public String e() {
				final BigDecimal[] total = {BigDecimal.ZERO};
				final Range[] prevRange = {null};
				eachConseConso(word, n, new P1<Range>() {public void e(Range range) {
					total[0] = total[0].add( new BigDecimal((range.getFrom() - (prevRange[0]==null ? 0 : prevRange[0].getFrom()+1) + 1)).multiply(new BigDecimal(word.length() - range.getTo() + 1)));
					prevRange[0] = range;
				}});
				return "" + total[0];
				
			}};
		}})
		.solve();
	}
	static void eachConseConso(String word, int n, P1<Range> p1) {
		int count = 0;
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if (isConso(c)) {
				count ++;
				if (count>=n) {
					p1.e(new Range(i-n+1, i+1));
				}
			} else {
				count=0;
			}
		}
	}
	private static boolean isConso(char c) {
		return 
				c != 'a' &&
				c != 'e' &&
				c != 'i' &&
				c != 'o' &&
				c != 'u'
				;
	}
}
