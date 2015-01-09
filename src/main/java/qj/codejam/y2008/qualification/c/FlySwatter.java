package qj.codejam.y2008.qualification.c;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import qj.codejam.CodeJam;
import qj.util.MathUtil;
import qj.util.funct.F0;
import qj.util.funct.F1;
import qj.util.funct.P2;
import qj.util.funct.P3;
import qj.util.math.CircleD;
import qj.util.math.RectangleD;

public class FlySwatter {
	public static void main(String[] args) {
		// 34, 90, 99 sai
		
//		String resName = "test.in";
//		String resName = "sample.in";
//		String resName = "C-small-practice.in";
		String resName = "C-large-practice.in";
		CodeJam.solver(resName, new F1<F0<String>, F0<String>>() {public F0<String> e(F0<String> lineF) {
			String[] split = lineF.e().split(" ");
//			f, R, t, r and g;
			final double f = Double.parseDouble(split[0]);
			final double R = Double.parseDouble(split[1]);
			final double t = Double.parseDouble(split[2]);
			final double r = Double.parseDouble(split[3]);
			final double g = Double.parseDouble(split[4]);
			
			return new F0<String>() {public String e() {
				double total = R*R*Math.PI;
				
				double escapeArea = area1(f, R - t - f, r, g) * 4;
				
//				return "" + new BigDecimal((total - escapeArea)/total).toPlainString();
				return "" + new DecimalFormat("#.######").format((total - escapeArea)/total);
			}

			private double area1(double f, final double bigR, double r, double g) {
				double padding = 2*(r+f);
				final double a = g-2*f;
				final double[] totalArea = {0D};
				
				if (a <= 0) {
					return 0;
				}
				
				// Each square in bigR
				eachSquare(a, padding, bigR, new P2<Double, Double>() {public void e(Double x, Double y) {
					
					
					double intersectArea = MathUtil.intersectArea(new RectangleD(x, y, a, a), new CircleD(0d, 0d, bigR));
//					System.out.println("intersectArea=" + intersectArea);
					if (Double.isNaN( intersectArea )) {
						MathUtil.intersectArea(new RectangleD(x, y, a, a), new CircleD(0d, 0d, bigR));
//						System.out.println(11);
					}
					totalArea[0] += intersectArea;
				}});
				
				return totalArea[0];
			}};
		}})
		.solve();
	}

	public static void eachSquare(double a, double padding, double bigR, P2<Double,Double> f) {
		for (double x = padding/2; x < bigR; x+=padding+a) {
			for (double y = padding/2; y < bigR; y+=padding+a) {
				f.e(x, y);
			}
		}
	}
}
