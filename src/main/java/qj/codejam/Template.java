package qj.codejam;

import qj.codejam.CodeJam;
import qj.util.funct.F0;
import qj.util.funct.F1;

public class Template {
	public static void main(String[] args) {
		String resName = "sample.in";
//		String resName = "A-small-practice.in";
//		String resName = "A-large-practice.in";
		CodeJam.solver(resName, (F0<String> lineF) -> {
			// TODO Auto-generated method stub
			return () -> {
				// TODO Auto-generated method stub
				return cal();
			};
		})
		.solve();
	}
	
	static String cal() {
		// TODO Auto-generated method stub
		return null;
	}
}
