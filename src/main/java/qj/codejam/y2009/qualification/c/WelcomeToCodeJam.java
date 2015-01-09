package qj.codejam.y2009.qualification.c;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

import qj.codejam.CodeJam;
import qj.util.Cols;
import qj.util.LangUtil;
import qj.util.MathUtil;
import qj.util.RegexUtil;
import qj.util.funct.F0;
import qj.util.funct.F1;
import qj.util.funct.Fs;
import qj.util.funct.P0;
import qj.util.funct.P1;

public class WelcomeToCodeJam {
	public static void main(String[] args) {
//		String resName = "sample.in";
//		String resName = "C-small-practice.in";
		String resName = "C-large-practice.in";
		CodeJam.solver(resName, new F1<F0<String>, F0<String>>() {public F0<String> e(F0<String> lineF) {
			final String line = lineF.e();
			
			return new F0<String>() {public String e() {
				
				return "" + new DecimalFormat("0000").format(count("welcome to code jam", line));
			}};
		}})
		.solve();
	}


	private static int count(String target, String content) {
		int[] track = new int[target.length()];
		for (int i = 0; i < content.length(); i++) {
			char c = content.charAt(i);
			
			for (int j = 0; j < track.length; j++) {
				if (c == target.charAt(j)) {
					if (j==0) {
						track[j] ++;
					} else {
						track[j] += track[j-1];
						track[j] = track[j] % 10000;
					}
				}
			}
		}
		return track[track.length - 1];
	}
}
