package qj.codejam.practice.a;

import java.util.Arrays;
import java.util.List;

import qj.codejam.CodeJam;
import qj.util.LangUtil;

public class AlienNumbers {
	public static void main(String[] args) {
		CodeJam.solver("A-large-practice.in", lineF -> {
			String[] split = lineF.e().split(" ");
			String number = split[0];
			String srcLang = split[1];
			String targetLang = split[2];
			return () ->{
				Lang src = createLang(srcLang);
				int decNum = src.parse(number);
//				System.out.println("num=" + decNum);
				return createLang(targetLang).format(decNum);
			};
		})
		.solve();
	}
	
	static class Lang {

		List<Character> digits;

		public Lang(char[] digits) {
			this.digits = Arrays.asList(LangUtil.toObjArr(digits));
		}

		public int parse(String number) {
			int num = 0;
			for (char c : number.toCharArray()) {
				num = num*digits.size() + digits.indexOf(c);
			}
			return num;
		}

		public String format(int num) {
			StringBuilder sb = new StringBuilder();
			
			for (;num > 0;) {
				int last = num % digits.size();
				num /= digits.size();
				
				sb.insert(0, digits.get(last));
			}
			
			return sb.toString();
		}
		
	}
	
	private static Lang createLang(String digits) {
		return new Lang(digits.toCharArray());
	}
}
