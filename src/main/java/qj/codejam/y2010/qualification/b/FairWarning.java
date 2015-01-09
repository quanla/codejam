package qj.codejam.y2010.qualification.b;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import qj.codejam.CodeJam;
import qj.util.MathUtil;
import qj.util.funct.F0;
import qj.util.funct.F1;

public class FairWarning {
	public static void main(String[] args) {
//		String resName = "sample.in";
//		String resName = "B-small-practice.in";
		String resName = "B-large-practice.in";
		CodeJam.solver(resName, new F1<F0<String>, F0<String>>() {public F0<String> e(F0<String> lineF) {
			String[] split = lineF.e().split(" ");
			final List<BigDecimal> list = new ArrayList<BigDecimal>();
			for (int i = 1; i < split.length; i++) {
				list.add(new BigDecimal( split[i] ));
			}
			return new F0<String>() {public String e() {
				Collections.sort(list);
				LinkedList<BigDecimal> subtracts = new LinkedList<BigDecimal>();
				for (int i = 1; i < list.size(); i++) {
					BigDecimal subtract = list.get(i).subtract(list.get(i - 1));
					if (!subtract.equals(BigDecimal.ZERO)) {
						subtracts.add(subtract);
					}
				}
				BigDecimal commonDivision = MathUtil.commonDivision(subtracts);
				BigDecimal remainder = list.get(0).remainder(commonDivision);
				if (remainder.equals(BigDecimal.ZERO)) {
					return "0";
				}
				return "" + commonDivision.subtract(remainder);
			}};
		}})
		.solve();
	}
}
