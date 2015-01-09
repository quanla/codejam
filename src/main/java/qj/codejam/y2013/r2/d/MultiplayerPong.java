package qj.codejam.y2013.r2.d;

import qj.codejam.CodeJam;
import qj.util.funct.F0;
import qj.util.funct.F1;

public class MultiplayerPong {
	public static void main(String[] args) {
		String resName = "sample.in";
		CodeJam.solver(resName, new F1<F0<String>, F0<String>>() {
			public F0<String> e(F0<String> lineF) {
				String[] split = lineF.e().split(" ");
				int a = Integer.parseInt(split[0]);
				int b = Integer.parseInt(split[1]);
				
				String[] split2 = lineF.e().split(" ");
				int leftTeamSize  = Integer.parseInt(split2[0]);
				int rightTeamSize = Integer.parseInt(split2[1]);
				
				
				
				// TODO Auto-generated method stub
				return new F0<String>() {
					public String e() {
						// TODO Auto-generated method stub
						return null;
					}
				};
			}
		}).solve();
	}
}
