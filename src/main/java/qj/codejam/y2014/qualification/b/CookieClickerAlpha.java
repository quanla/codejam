package qj.codejam.y2014.qualification.b;

import java.text.DecimalFormat;
import java.util.ArrayList;

import qj.codejam.CodeJam;
import qj.util.funct.F0;

public class CookieClickerAlpha {
	public static void main(String[] args) {
//		String resName = "sample.in";
//		String resName = "B-small-attempt0.in";
		String resName = "B-large.in";
		CodeJam.solver(resName, (F0<String> lineF) -> {
			ArrayList<Double> doubles = CodeJam.toDoubles(lineF.e());
			Double farmCost = doubles.get(0);
			Double farmProd = doubles.get(1);
			Double required = doubles.get(2);
			
			return () -> {
				return new DecimalFormat("#.#######").format(cal(farmCost, farmProd, required));
			};
		})
		.solve();
	}
	
	static double cal(double farmCost, double farmProd, double required) {
		double rate = 2.0;
		
		double consumed = 0;
		
		for (;;) {
			Res result = solve(farmCost, farmProd, required, rate);
			
			consumed += result.consumed;
			rate += farmProd;
			if (result.done) {
				return consumed;
			}
		}
	}
	
	static Res solve(double farmCost, double farmProd, double required, double currentRate) {
		// Cal if to meet require, which way is faster
		double ifDirect = required / currentRate;
		double ifBuild1Farm = (farmCost / currentRate) + (required / (currentRate + farmProd));
//		System.out.println("currentRate=" + currentRate + ", ifDirect=" + ifDirect + ", ifBuild1Farm=" + ifBuild1Farm);
//		if (currentRate > 100000) {
//			System.exit(0);
//		}
		if (ifDirect <= ifBuild1Farm) {
			return new Res(true, ifDirect);
		} else {
			return new Res(false, farmCost / currentRate);
		}
	}
	
	static class Res {
		boolean done;
		double consumed;

		public Res(boolean done, double consumed) {
			this.done = done;
			this.consumed = consumed;
		}
	}
}
