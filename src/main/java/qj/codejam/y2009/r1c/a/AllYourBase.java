package qj.codejam.y2009.r1c.a;

import java.math.BigInteger;
import java.util.HashMap;

import qj.codejam.CodeJam;
import qj.util.funct.Douce;
import qj.util.funct.F0;

public class AllYourBase {

	public static void main(String[] args) {
//		String resName = "A-small-practice.in";
		String resName = "A-large-practice.in";
//		String resName = "sample.in";
		CodeJam.solver(resName, (F0<String> lineF) -> {
			String line = lineF.e();
			return () -> {
				return "" + cal(line);
			};
		})
		.solve();
	}
	
	static BigInteger cal(String line) {
		Douce<String, Integer> nb = toNumber(line);
		String number = nb.get1();
		Integer smallestBase = nb.get2();
		
		return BigInteger.valueOf(Long.parseLong(number, smallestBase));
	}

	
	private static Douce<String,Integer> toNumber(String line) {
		char[] feed = {'1', '0',
		               '2',
		               '3',
		               '4',
		               '5',
		               '6',
		               '7',
		               '8',
		               '9',
		               'a',
		               'b',
		               'c',
		               'd',
		               'e',
		               'f',
		               'g',
		               'h',
		               'i',
		               'j'
		};
		
		HashMap<Character,Character> map = new HashMap<>();
		int s = 0;
		StringBuilder number = new StringBuilder();
		
		for (int i = 0; i < line.length(); i++) {
			Character c = line.charAt(i);
			if (!map.containsKey(c)) {
				map.put(c, feed[s++]);
			}
			number.append(map.get(c));
		}
		
		return new Douce<String, Integer>(number.toString(), Math.max(s, 2));
	}
}
