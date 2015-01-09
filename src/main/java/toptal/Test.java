package toptal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import qj.util.LangUtil;

public class Test {
	public static void main(String[] args) throws IOException {
//		System.out.println(anagrams("horse"));
		System.out.println(anagrams("able"));
		System.out.println(anagrams("apple"));
		System.out.println(anagrams("spot"));
		System.out.println(anagrams("reset"));
	}

	private static LinkedList<String> anagrams(String word) throws IOException {
		LinkedList<String> ret = new LinkedList<>();
		char[] tar1 = word.toCharArray();
		Arrays.sort(tar1);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(Test.class.getResourceAsStream("wl.txt")));
		for (String line; (line = br.readLine()) != null;) {
			char[] in1 = line.toCharArray();
			Arrays.sort(in1);
			
			if (Arrays.equals(tar1, in1)) {
				ret.add(line);
			}
		}
		
		br.close();
		
		return ret;
	}
}
