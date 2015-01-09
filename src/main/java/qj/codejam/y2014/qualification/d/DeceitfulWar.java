package qj.codejam.y2014.qualification.d;

import java.util.ArrayList;
import java.util.LinkedList;

import qj.codejam.CodeJam;
import qj.util.Cols;
import qj.util.funct.F0;

/**
 * How he win:
 *  Pick min to win max first
 *  If can't win any way, pick min num to loose
 * 
 * How she cheated:
 *  Fake loose: lure out best of him by ?
 *  Fake win: make him accept loose by telling max num (when gives min better than him)
 */
public class DeceitfulWar {

	public static void main(String[] args) {
//		String resName = "sample.in";
//		String resName = "test.in";
		String resName = "D-large.in";
		CodeJam.solver(resName, (F0<String> lineF) -> {
			lineF.e(); // Rejected
			ArrayList<Double> naomi = CodeJam.toDoubles(lineF.e());
			ArrayList<Double> ken = CodeJam.toDoubles(lineF.e());

			naomi.sort(null);
			ken.sort(null);
			
			return () -> {
				return cal(naomi, ken);
			};
		})
		.solve();
	}
	
	static String cal(ArrayList<Double> naomi, ArrayList<Double> ken) {
//		System.out.println(naomi);
//		System.out.println(ken);
		return war(ken, naomi, true) + " " + (ken.size() - war(naomi, ken, false));
	}
	
	static int war(ArrayList<Double> enemy1, ArrayList<Double> me1, boolean deceived) {
		LinkedList<Double> enemy = new LinkedList<>(enemy1);
		LinkedList<Double> me = new LinkedList<>(me1);
		
		int meWin = 0;
		L1:
		for (;;) {
			if (enemy.size() == 0) {
				break;
			}
			Double enemyNum = enemy.removeFirst();
			
			for (;;) {
				if (me.size() == 0) {
					break L1;
				}
				
				if (me.getLast() < enemyNum) {
					// Can't win anyway
					break L1;
				}
				
				Double meNum = me.removeFirst();
				if (meNum < enemyNum) {
					if (deceived) {
						// Fake loose to lure best of him
						enemy.removeLast();
					}
					continue;
				} else {
					meWin ++;
					break;
				}
			}
		}
		
		return meWin;
	}
}
