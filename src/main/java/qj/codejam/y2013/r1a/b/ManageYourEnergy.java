package qj.codejam.y2013.r1a.b;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import qj.codejam.CodeJam;
import qj.util.Cols;
import qj.util.funct.F0;
import qj.util.funct.F1;

public class ManageYourEnergy {
	public static void main(String[] args) {
//		String resName = "sample.in";
		String resName = "B-small-practice.in";
//		String resName = "B-large-practice.in";
		CodeJam.solver(resName, new F1<F0<String>, F0<String>>() {public F0<String> e(F0<String> lineF) {
			ArrayList<Integer> ints1 = CodeJam.toInts(lineF.e().split(" "));
			final int maxE = ints1.get(0);
			final int regain = ints1.get(1);
			final ArrayList<Long> activities = CodeJam.toLongs(lineF.e().split(" "));
			return new F0<String>() {public String e() {
				return "" + cal(maxE, regain, activities);
			}};
		}})
		.solve();
	}
	
	private static BigDecimal cal(int maxE, int regain,
			ArrayList<Long> activities) {
		
		if (regain != 0) {
		
			int safeDis = safeDis(maxE, regain);
			ArrayList<Integer> prioIndex = prioIndex(activities);
			Long[] spendPlan = new Long[activities.size()];
			for (Integer index : prioIndex) {
				long avai = val( toAhead(index, safeDis, spendPlan), regain, maxE);
				long min = maxE - val(toBack(index, safeDis, spendPlan), regain, maxE);
				spendPlan[index] = avai < min ? 0 : avai - min;
			}
			
			return sum(activities, spendPlan);
		} else {
			// Only 1st activ (max) is spent with full e, other: 1
			Long maxActVal = Cols.max(activities);
			return new BigDecimal(maxActVal * maxE);
		}
	}

	private static BigDecimal sum(ArrayList<Long> activities, Long[] spendPlan) {
		BigDecimal total = BigDecimal.ZERO;
		for (int i = 0; i < spendPlan.length; i++) {
			long spend = spendPlan[i];
			total = total.add(BigDecimal.valueOf(spend * activities.get(i)));
		}
		return total;
	}

	private static long val(Integer dis, long regain, long maxE) {
		if (dis == null) {
			return maxE;
		}
		return (long)dis*regain;
	}

	private static Integer toBack(int index, int safeDis, Long[] spendPlan) {
		for (int dis = 1;dis < safeDis && dis <= index;dis++) {
			if (spendPlan[index - dis] != null) {
				return dis;
			}
		}
		return null;
	}

	private static Integer toAhead(Integer index, int safeDis, Long[] spendPlan) {
		for (int dis = 1;dis < safeDis && index + dis < spendPlan.length;dis++) {
			if (spendPlan[index + dis] != null) {
				return dis;
			}
		}
		return null;
	}

	private static int safeDis(int maxE, int regain) {
		return (int) Math.ceil((double)maxE / regain);
	}
	

	private static ArrayList<Integer> prioIndex(final ArrayList<Long> activities) {
		ArrayList<Integer> prio = new ArrayList<Integer>(activities.size());
		for (int i = 0; i < activities.size(); i++) {
			prio.add(i);
		}
		
		Collections.sort(prio, new Comparator<Integer>() {public int compare(Integer i1, Integer i2) {
			Long v1 = activities.get(i1);
			Long v2 = activities.get(i2);
			if (!v1.equals(v2)) {
				return v1 > v2 ? -1 : 1;
			}
			return i1 - i2;
		}});
		return prio;
	}
}
