package qj.codejam.y2009.qualification.a;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import qj.codejam.CodeJam;
import qj.util.Cols;
import qj.util.FileUtil;
import qj.util.IOUtil;
import qj.util.LangUtil;
import qj.util.RegexUtil;
import qj.util.funct.Douce;
import qj.util.funct.F0;
import qj.util.funct.F1;
import qj.util.funct.P1;

public class AlienLanguage {
	public static void main(String[] args) {
//		String resName = "sample.in";
//		String resName = "A-small-practice.in";
		String resName = "A-large-practice.in";
		CodeJam.solver2(resName, new F1<F0<String>,Douce<Integer,F1<F0<String>,F0<String>>>>() {public Douce<Integer,F1<F0<String>, F0<String>>> e(F0<String> lineF) {
			String[] firstLine = lineF.e().split(" ");
			int avaiCount = Integer.parseInt(firstLine[1]);
			final HashSet<String> avais = new HashSet<String>();
			for (int i = 0; i < avaiCount; i++) {
				String avai = lineF.e();
				avais.add(avai);
			}

			int testCount = Integer.parseInt(firstLine[2]);
			
			return new Douce<Integer, F1<F0<String>,F0<String>>>(testCount, new F1<F0<String>,F0<String>>() {public F0<String> e(F0<String> lineF) {
				final String ptn = lineF.e();
				return new F0<String>() {public String e() {
					return "" + check2(ptn, avais);
				}};
			}});
		}})
		.solve();
	}
//	public static void main1(String[] args) throws IOException {
////		String resName = "sample.in";
//		String resName = "A-small-practice.in";
//		ArrayList<String> lines = CodeJam.getLines(resName);
//		String[] firstLine = lines.get(0).split(" ");
//		int avaiCount = Integer.parseInt(firstLine[1]);
//		HashSet<String> avais = new HashSet<String>();
//		for (int i = 0; i < avaiCount; i++) {
//			avais.add(lines.get(i + 1));
//		}
//		
//		FileOutputStream out = FileUtil.fileOutputStream("AlienLanguage.out", false);
//		int testCount = Integer.parseInt(firstLine[2]);
//		for (int i = 0; i < testCount; i++) {
//			String ptn = lines.get(i + 1 + avaiCount);
//			String str = "Case #" + (i+1) + ": " + check2(ptn, avais) + "\n";
//			System.out.println(str);
//			out.write(str.getBytes());
//		}
//		IOUtil.close(out);
//	}

//	private static int check(String ptn, final HashSet<String> avais) {
//		List<Collection<Character>> list = parse(ptn);
//		final int[] countMatches = {0};
//		
//		Cols.<Collection<Character>,Character>eachLine(list, Cols.<Collection<Character>,Character>eachF(), new P1<List<Character>>() {public void e(List<Character> cs) {
//			String found = Cols.join(cs, "");
//			if (avais.contains(found)) {
//				System.out.println(found);
//				countMatches[0]++;
//			}
//		}});
//		return countMatches[0];
//	}
	private static int check2(String ptn, final HashSet<String> avais) {
		List<Collection<Character>> list = parse(ptn);
		final int[] countMatches = {0};
		
		for (String string : avais) {
			if (contain(string, list)) {
				countMatches[0] ++;
			}
		}
		
		
		return countMatches[0];
	}

	private static boolean contain(String string,
			List<Collection<Character>> list) {
		for (int i = 0; i < list.size(); i++) {
			Collection<Character> collection = list.get(i);
			if (!collection.contains(string.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	private static List<Collection<Character>> parse(String ptn) {
		final LinkedList<Collection<Character>> ret = new LinkedList<Collection<Character>>();
		RegexUtil.each(Pattern.compile("\\((\\w+)\\)|\\w"), ptn, new P1<Matcher>() {public void e(Matcher m) {
			String toAdd;
			toAdd = m.group(1) != null ? m.group(1) : m.group(0);
			ret.add(new HashSet<Character>(Arrays.asList(LangUtil.toObjArr(toAdd.toCharArray()))));
			
		}});
		return ret;
	}
}
