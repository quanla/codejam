package qj.codejam.y2012.qualification.a;

import java.util.HashMap;

import qj.codejam.CodeJam;
import qj.util.funct.F0;
import qj.util.funct.F1;

public class SpeakingInTongues {
	public static void main(String[] args) {
		HashMap<Character, Character> etg = new HashMap<Character, Character>();
		final HashMap<Character, Character> gte = new HashMap<Character, Character>();
		buildMap("aoz", "yeq", etg, gte);
		buildMap("our language is impossible to understand", "ejp mysljylc kd kxveddknmc re jsicpdrysi", etg, gte);
		buildMap("there are twenty six factorial possibilities", "rbcpc ypc rtcsra dkh wyfrepkym veddknkmkrkcd", etg, gte);
		buildMap("so it is okay if you want to just give up", "de kr kd eoya kw aej tysr re ujdr lkgc jv", etg, gte);
		
		CodeJam.solver("sample.in", new F1<F0<String>, F0<String>>() {public F0<String> e(F0<String> lineF) {
			final String line = lineF.e();
			return new F0<String>() {public String e() {
				return translate(line, gte);
			}};
		}})
		.solve();
	}
	


	private static String translate(String line, HashMap<Character,Character> map) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < line.length(); i++) {
			sb.append(map.get(line.charAt(i)));
		}
		return sb.toString();
	}

	private static void buildMap(String english, String gres,
			HashMap<Character, Character> etg, HashMap<Character,Character> gte) {
		for (int i = 0; i < english.length(); i++) {
			char e = english.charAt(i);
			char g = gres.charAt(i);
			etg.put(e, g);
			gte.put(g, e);
		}
	}
}
