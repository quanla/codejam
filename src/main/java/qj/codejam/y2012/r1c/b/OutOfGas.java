package qj.codejam.y2012.r1c.b;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;

import qj.codejam.CodeJam;
import qj.util.Cols;
import qj.util.math.LineD;
import qj.util.math.PointD;

public class OutOfGas {
	public static void main(String[] args) {
//		String resName = "sample.in";
//		String resName = "B-small-practice.in";
		String resName = "B-large-practice.in";
//		String resName = "test.in";
		CodeJam.solver(resName, lineF -> {
			String[] split = lineF.e().split(" ");
			double home = Double.parseDouble(split[0]);
			int count = Integer.parseInt(split[1]);
			LinkedList<P> ps = new LinkedList<>();
			for (int i = 0; i < count; i++) {
				ArrayList<Double> doubles = CodeJam.toDoubles(lineF.e());
				double t = doubles.get(0);
				double x = doubles.get(1);
				
				ps.add(new P(t,x));
			}
			
			ArrayList<Double> as = CodeJam.toDoubles(lineF.e());
			
			return () -> {
				
				DecimalFormat df = new DecimalFormat("0.0####################");
				StringBuilder sb = new StringBuilder();
				for (Double a : as) {
					sb.append("\n");
					sb.append(df.format(cal(ps, home, a)));
				}
				return sb.toString();
			};
		})
		.solve();
	}
	
	static double cal(LinkedList<P> ps, double home, Double a) {
		double wait = findMinWait(ps, home, a);
		
		return wait + minTime(home, a);
		
	}
	
	private static double minTime(double home, double a) {
		double d = home;
		return t(a, d);
	}

	private static double t(double a, double d) {
		return Math.sqrt(2*d/a);
	}

	private static double findMinWait(LinkedList<P> ps, double home, Double a) {
		LinkedList<P> interestedPs = interestedPs(ps, home);
		if (Cols.isEmpty(interestedPs)) {
			return 0d;
		}
		return Cols.max(interestedPs, (p) -> {
			double timeToBeAtX = t(a, p.x);
			if (p.t > timeToBeAtX) {
				return p.t - timeToBeAtX;
			}
			return 0D;
		});
	}

	private static LinkedList<P> interestedPs(LinkedList<P> ps, double home) {
		LinkedList<P> interestedPs = new LinkedList<>();
		
		P lastP = new P(0,0);
		for (P p : ps) {
			if (p.x > home) {
				PointD meet = new LineD(0, home, p.t, home).meet(new LineD(lastP.t, lastP.x, p.t, p.x));
				interestedPs.add(new P(meet.x, meet.y));
				break;
			} else {
				interestedPs.add(p);
			}
			lastP = p;
		}
		return interestedPs;
	}

	static class P {
		double t;
		double x;
		public P(double t, double x) {
			this.t = t;
			this.x = x;
		}
//		@Override
//		public String toString() {
//			return "P [t=" + t + ", x=" + x + "]";
//		}
	}
}
