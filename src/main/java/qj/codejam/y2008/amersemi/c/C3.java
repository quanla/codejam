package qj.codejam.y2008.amersemi.c;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import qj.codejam.CodeJam;
import qj.util.Cols;
import qj.util.funct.F0;
import qj.util.funct.F1;
import qj.util.funct.P1;

public class C3 {
	public static void main(String[] args) {
//		String resName = "sample.in";
		String resName = "C-small-practice.in";
//		String resName = "C-large-practice.in";
//		String resName = "test.in";
		CodeJam.solver(resName, new F1<F0<String>, F0<String>>() {public F0<String> e(F0<String> lineF) {
			String[] split = lineF.e().split(" ");
			final int submissions = Integer.parseInt(split[0]);
			final int questions = Integer.parseInt(split[1]);
			
			final Matrix matrix = new Matrix();
			for (int i = 0; i < questions; i++) {
				String[] responseSplit = lineF.e().split(" ");

				List<Double> line = new LinkedList<Double>();
				for (String resp : responseSplit) {
					Double respVal = Double.valueOf(resp);
					if (respVal == 0) {
						continue;
					}
					line.add(respVal);
				}
				Collections.sort(line);
				line = Cols.reverse(line);
				matrix.add(line);
			}
			
			// Solve
			return new F0<String>() {public String e() {
				Path path = Path.zeros(questions);
				
				final TreeSet<Feed> q = new TreeSet<Feed>();
				q.add(new Feed(path, matrix.cal(path)));
				final HashSet<Path> visiteds = new HashSet<Path>();
				double total = 0;
				for (int i = 0; i < submissions && !q.isEmpty(); i++) {
					Feed f = q.pollFirst();
					total += f.val;
					f.path.next(new P1<Path>() {public void e(Path p) {
						if (visiteds.contains(p) || q.size() > submissions * questions) {
							return;
						}
						double val = matrix.cal(p);
						if (val==0) {
							return;
						}
						q.add(new Feed(p, val));
						visiteds.add(p);
					}});
				}
				
				return "" + total;
			}};
			

		}})
		.solve();
	}
	
	static class Feed implements Comparable<Feed> {
		public Feed(Path path, double val) {
			this.path = path;
			this.val = val;
		}
		Path path;
		double val;
		@Override
		public int compareTo(Feed o) {
			return val > o.val ? -1 : 1;
		}
		
	}
	
	static class Path {
		
		List<Integer> poses = new ArrayList<Integer>();
		static Path zeros(int size) {
			Path p = new Path();
			for (int i = 0; i < size; i++) {
				p.poses.add(0);
			}
			return p;
		}
		public void next(P1<Path> p1) {
			for (int i = 0; i < poses.size(); i++) {
				p1.e(increase(i));
			}
		}
		private Path increase(int i) {
			Path p = new Path();
			for (int j = 0; j < poses.size(); j++) {
				p.poses.add(i!=j ? poses.get(j) : poses.get(j) + 1);
			}
			return p;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((poses == null) ? 0 : poses.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Path other = (Path) obj;
			if (poses == null) {
				if (other.poses != null)
					return false;
			} else if (!poses.equals(other.poses))
				return false;
			return true;
		}
		
	}
	
	static class Matrix {
		final LinkedList<List<Double>> vals = new LinkedList<List<Double>>();

		public void add(List<Double> line) {
			vals.add(line);
		}

		public double cal(Path path) {
			double ret = 1;
			for (int i = 0; i < path.poses.size(); i++) {
				Integer pos = path.poses.get(i);
				List<Double> line = vals.get(i);
				if (pos >= line.size()) {
					return 0;
				}
				ret *= line.get(pos);
			}
			return ret;
		}
		
	}
}
