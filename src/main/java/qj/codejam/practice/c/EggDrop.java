package qj.codejam.practice.c;

import java.util.ArrayList;
import java.util.HashMap;

import qj.codejam.CodeJam;
import qj.util.funct.Douce;
import qj.util.funct.F0;
import qj.util.funct.F1;

public class EggDrop {
	static double MAX = Math.pow(2, 32);
	
	public static void main(String[] args) {
//		String resName = "sample.in";
//		String resName = "C-small-practice.in";
		String resName = "C-large-practice.in";
		
		FloorMatrix floorMatrix = new FloorMatrix();
		buildMatrix(floorMatrix);
		
		CodeJam.solver(resName, lineF -> {
			ArrayList<Integer> ints = CodeJam.toInts(lineF.e());
			int f = ints.get(0);
			int d = ints.get(1);
			int b = ints.get(2);
			
			return () -> {
				return floorMatrix.getFloor(d, b) + " " + min(f, (d1) -> floorMatrix.getFloor(d1, b)) + " " + min(f, (b1) -> floorMatrix.getFloor(d, b1));
			};
		})
		.solve();
	}
	
	static void buildMatrix(FloorMatrix floorMatrix) {
//		int maxB = 100000000;
		for (int d = 1; d< 100000; d++) {
			for (int b = 1; b < 33; b++) {
				long f = floorMatrix.getFloorF(d, b);
//				if (f == -1) {
//					if (b == 2) {
//						System.out.println("d=" + d);
//						return;
//					} else {
//						maxB = d;
//					}
//				}
			}
		}
	}
	
	static long min(int target, F1<Integer,Long> testf) {
		for (int i = 0;; i++) {
			long f1 = testf.e(i);
			if (f1 == -1 || f1 >= target) {
				return i;
			}
		}
	}
	
	static class FloorMatrix {
		HashMap<Douce<Integer,Integer>, Long> map = new HashMap<>();
		
		public long getFloor(int d, int b) {
			Long floor = getFloor(d, b, false);
			if (floor==null) {
				return -1;
			}
			return floor;
		}
		public Long getFloor(int d, int b1, boolean buildCache) {
			int b = b1 > d ? d : b1;
			
			if (d==0) {
				return 0L;
			}
			if (b == 0) {
				return 0L;
			}
			if (d == 1) {
				return 1L;
			}
			if (b==1) {
				return (long) d;
			}
			
			if (!buildCache) {
				return map.get(new Douce<>(d,b));
			}
			
			if (b==d) {
				return addCache(d,b, () -> (long) (Math.pow(2, b) - 1));
			}
			
			return addCache(d, b, () -> {
				long lower;
				long higher;
				if ((lower = getFloorF(d-1, b-1)) == -1 || (higher = getFloorF(d-1, b)) == -1) {
					return (long) -1;
				}
				return lower + higher + 1;
			});

		}
		
		public long getFloorF(int d, int b) {
			return getFloor(d, b, true);
		}

		private long addCache(int d, int b, F0<Long> f) {
			Douce<Integer, Integer> key = new Douce<>(d,b);
			Long cached = map.get(key);
			if (cached == null) {
				cached = f.e();
				if (cached > MAX) {
					cached = (long) -1;
				}
				map.put(key, cached);
			}
			return cached;
		}
	}
	
}
