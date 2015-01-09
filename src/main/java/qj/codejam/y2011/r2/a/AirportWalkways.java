package qj.codejam.y2011.r2.a;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import qj.codejam.CodeJam;
import qj.util.Cols;

public class AirportWalkways {
	public static void main(String[] args) {
//		String resName = "sample.in";
//		String resName = "A-small-practice.in";
		String resName = "A-large-practice.in";
		CodeJam.solver(resName, lineF -> {
			int[] ints = CodeJam.toIntArray(lineF.e());
			int x = ints[0];
			int s = ints[1];
			int r = ints[2];
			int t = ints[3];
			int n = ints[4];
			ArrayList<Walkway> walkways = new ArrayList<>(n);
			for (int i = 0; i < n; i++) {
				int[] i2 = CodeJam.toIntArray(lineF.e());
				walkways.add(new Walkway(i2[1] - i2[0],i2[2]));
			}
			return () -> new DecimalFormat("0.0########").format(cal(x,s,r,t, walkways));
		}).solve();
	}
	static double cal(int x, int s, int r, double t, ArrayList<Walkway> walkways1) {
		LinkedList<Walkway> walkways = join(walkways1);
		
		int walkDist0 = x - Cols.sum(walkways, (w) -> w.d);
		walkways.addFirst(new Walkway(walkDist0, 0));
		
		double total = 0d;
		for (Walkway w : walkways) {
			double runTime = Math.min(t, (double)w.d / (w.w + r));
			double runDist = runTime * (w.w+r);
			double walkDist = (double)w.d - runDist;
			double walkTime = walkDist / (w.w + s);
			
			total += walkTime + runTime;
			t-=runTime;
		}
		
		return total;
	}
	
	private static LinkedList<Walkway> join(ArrayList<Walkway> walkways1) {
		Collections.sort(walkways1, (w1,w2) -> w1.w - w2.w);
		LinkedList<Walkway> walkways = Cols.merge(walkways1, (w1, w2) -> w1.w != w2.w ? null : new Walkway(w1.d + w2.d, w1.w));
		return walkways;
	}

	static class Walkway {
		int d;
		int w;
		public Walkway(int d, int w) {
			this.d = d;
			this.w = w;
		}
		@Override
		public String toString() {
			return "Walkway [d=" + d + ", w=" + w + "]";
		}
		
	}
}
