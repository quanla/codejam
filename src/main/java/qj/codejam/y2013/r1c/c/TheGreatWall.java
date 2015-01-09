package qj.codejam.y2013.r1c.c;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import qj.codejam.CodeJam;
import qj.util.ArrayUtil;
import qj.util.funct.F0;
import qj.util.funct.F1;
import qj.util.math.Range;

public class TheGreatWall {
	public static void main(String[] args) {
//		String resName = "sample.in";
//		String resName = "sample.in";
//		String resName = "C-small-practice.in";
		String resName = "C-large-practice.in";
		CodeJam.solver(resName, new F1<F0<String>, F0<String>>() {public F0<String> e(F0<String> lineF) {
			int tribeCount = Integer.parseInt(lineF.e());
			
			List<Strike> strikes1 = new ArrayList<Strike>();
			for (int tribeI = 0; tribeI < tribeCount; tribeI++) {
				ArrayList<Integer> split = CodeJam.toInts(lineF.e().split(" "));
				int day = split.get(0);
				int totalAttack = split.get(1);
				Range pos = new Range(split.get(2), split.get(3));
				int strength = split.get(4);
				int deltaDay = split.get(5);
				int deltaPos = split.get(6);
				int deltaStrength = split.get(7);
				
				for (int j = 0; j < totalAttack; j++) {
					strikes1.add(new Strike(tribeI + 1, 
							day + j*deltaDay, 
							pos.shiftRight(j*deltaPos), 
							strength + j*deltaStrength));
				}
			}
			Collections.sort(strikes1);
			LinkedList<Strike> strikes = new LinkedList<>(strikes1);
			strikes1 = null;
			
			return new F0<String>() {public String e() {
				int totalSuccess = 0;
				
				int[] points = compressStrikePoints(strikes);
				
				updateCompressingStrikes(points, strikes);
				
				Wall wall = new Wall(points.length);
				points = null;
				
				for (;strikes.size() > 0;) {
					Collection<Strike> nextStrikes = retrieveNextStrikes(strikes);
//					System.out.println("========= Day " + Cols.getSingle(nextStrikes).day
////							+ " (" + wall.sections.size() + ")"
//							+ " (" + strikes.size() + ")"
//							);
					
					for (Strike strike : nextStrikes) {
						boolean defended = wall.defend(strike);
//						System.out.println("Tribe " + strike.tribe + " attack " + strike.pos + " strength " + strike.strength + " " + (defended ? "FAILED" : "SUCCEED"));
						if (!defended) {
							totalSuccess++;
						}
					}
					wall.upgrade(nextStrikes);
				}
				
				return "" + totalSuccess;
			}};
		}})
		.solve();
	}
	
	static int[] compressStrikePoints(Collection<Strike> strikes) {
		int[] treeSet = new int[strikes.size() * 2];
		int i =0;
		for (Iterator<Strike> iterator = strikes.iterator(); iterator.hasNext();i+=2) {
			Strike strike = iterator.next();
			Range pos = strike.pos;
			treeSet[i] = (pos.getFrom());
			treeSet[i+1] = (pos.getTo());
		}
		Arrays.sort(treeSet);
		return ArrayUtil.removeDuplicates2(treeSet);
		
	}
	private static void updateCompressingStrikes(int[] points, List<Strike> strikes) {
		for (Strike strike : strikes) {
			int from = Arrays.binarySearch(points, strike.pos.getFrom());
			int to = Arrays.binarySearch(points, strike.pos.getTo());
			strike.pos = new Range(from, to);
		}
	}
	
	/**
	 * Retrieve and remove
	 * @param strikes
	 * @return
	 */
	static Collection<Strike> retrieveNextStrikes(LinkedList<Strike> strikes) {
		LinkedList<Strike> ret = new LinkedList<Strike>();
		Strike first = strikes.removeFirst();
		ret.add(first);
		for (;strikes.size() > 0;) {
			Strike attempt = strikes.getFirst();
			if (attempt.day == first.day) {
				strikes.removeFirst();
				ret.add(attempt);
			} else {
				break;
			}
		}
		return ret;
	}
	static class Wall {
		int[] wallHeights;

		public Wall(int length) {
			wallHeights = new int[length];
		}

		public boolean defend(Strike strike) {
			boolean canDefense = true;
			
			for (int pos = strike.pos.getFrom(); pos < strike.pos.getTo(); pos++) {
				if (wallHeights[pos] < strike.strength) {
					canDefense = false;
				}
			}
			return canDefense;
		}


		public void upgrade(Collection<Strike> nextStrikes) {
			for (Strike strike : nextStrikes) {
				for (int pos = strike.pos.getFrom(); pos < strike.pos.getTo(); pos++) {
					if (wallHeights[pos] < strike.strength) {
						wallHeights[pos] = strike.strength;
					}
				}
			}
		}
		
	}
	static class Strike implements Comparable<Strike> {

		private int day;
		private Range pos;
		private int strength;
//		private int tribe;

		public Strike(int tribe, int day, Range pos, int strength) {
//			this.tribe = tribe;
			this.day = day;
			this.pos = pos;
			this.strength = strength;
		}

		@Override
		public int compareTo(Strike o) {
			return day - o.day;
		}
		
	}
}
