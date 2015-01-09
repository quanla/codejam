package qj.codejam.y2010.qualification.c;

import java.util.LinkedList;
import java.util.List;

import qj.codejam.CodeJam;
import qj.util.Cols;
import qj.util.MathUtil;
import qj.util.funct.F0;
import qj.util.funct.F1;

/**
 * TODO : Not use 3 steps optimize yet
 * @author quanle
 *
 */
public class ThemePark {
	public static void main(String[] args) {
		String resName = "sample.in";
//		String resName = "C-small-practice.in";
//		String resName = "C-large-practice.in";
		CodeJam.solver(resName, new F1<F0<String>, F0<String>>() {public F0<String> e(F0<String> lineF) {
			String[] split = lineF.e().split(" ");
			final int times = Integer.parseInt(split[0]);
			final int seats = Integer.parseInt(split[1]);
			
			final LinkedList<Integer> queue = new LinkedList<Integer>(CodeJam.toInts(lineF.e().split(" ")));
			final LinkedList<Integer> savedQueue = new LinkedList<Integer>(queue);
			
			return new F0<String>() {public String e() {
				long earned = 0;
				
				F0<Integer> normal = new F0<Integer>() {public Integer e() {
					List<Integer> boardedPeople = board(queue, seats);
					queue.addAll(boardedPeople);
					return MathUtil.sumI(boardedPeople);
				}};
				F0<Integer> f0 = normal;
				LinkedList<Integer> saveds = new LinkedList<Integer>();
				for (int i = 0; i < times; i++) {
					if (f0 == normal) {
						if (i > 0 && savedQueue.equals(queue)) {
							System.out.println("Found repeat");
							f0 = Cols.loopF(saveds);
							earned += f0.e();
						} else {
							Integer val = f0.e();
							earned += val;
							saveds.add(val);
						}
					} else {
						earned += f0.e();
					}
				}
				
				return "" + earned;
			}};
		}})
//		.singleCase()
		.solve();
	}
	
	private static List<Integer> board(LinkedList<Integer> queue, int seats) {
		LinkedList<Integer> boarded = new LinkedList<Integer>();
		while (queue.peek() <= seats) {
			Integer group = queue.removeFirst();
			seats -= group;
			boarded.add( group );
			if (queue.isEmpty()) {
				break;
			}
		}
		return boarded;
	}
}
