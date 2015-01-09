package qj.codejam.y2012.r1b.b;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import qj.codejam.CodeJam;
import qj.util.Cols;
import qj.util.funct.F0;
import qj.util.funct.F1;

public class KingdomRush {
	public static void main(String[] args) {
//		String resName = "sample.in";
		String resName = "B-small-practice.in";
//		String resName = "B-large-practice.in";
		CodeJam.solver(resName, new F1<F0<String>, F0<String>>() {public F0<String> e(F0<String> lineF) {
			int levelsCount = Integer.parseInt(lineF.e());
			LinkedList<Level> levels = new LinkedList<>();
			for (int i = 0; i < levelsCount; i++) {
				ArrayList<Integer> ints = CodeJam.toInts(lineF.e());
				levels.add( new Level(ints.get(0), ints.get(1)) );
			}
			return new F0<String>() {public String e() {
				Integer res = play(levels);
				return res == null ? "Too Bad" : "" + res;
			}};
		}})
		.solve();
	}
	
	static Integer play(LinkedList<Level> levels) {
		Player player = new Player();
		for (;;) {
			if (Cols.isEmpty(levels)) {
				// Done
				return player.gamePlayed;
			}

			// If can complete 2 star -> do it
			if (player.complete2stars(levels)) {
				continue;
			}
			
			
			Collection<Level> l1s = player.getAll1starLevels(levels);

			if (Cols.isEmpty(l1s)) {
				// Too Bad
				return null;
			}
			
			Level max2req = max2req(l1s);
			player.play(max2req);
		}
	}
	
	private static Level max2req(Collection<Level> l1s) {
		return Cols.maxE(l1s, (level) -> level.s2);
	}

	static class Player {
		int gamePlayed = 0;
		int stars = 0;
		/**
		 * 
		 * @param levels
		 * @return true if completed any
		 */
		public boolean complete2stars(LinkedList<Level> levels) {
			boolean played = false;
			for (Iterator<Level> iterator = levels.iterator(); iterator
					.hasNext();) {
				Level level = iterator.next();
				if (level.s2 <= stars) {
					play(level);
					iterator.remove();
					played = true;
				}
			}
			return played;
		}

		public void play(Level level) {
			gamePlayed ++;
			if (stars >= level.s2 && level.completed < 2) {
				stars += 2 - level.completed;
				level.completed = 2;
			} else if (stars >= level.s1 && level.completed < 1) {
				stars += 1 - level.completed;
				level.completed = 1;
			} else {
				throw new RuntimeException();
			}
		}

		public Collection<Level> getAll1starLevels(LinkedList<Level> levels) {
			LinkedList<Level> ret = new LinkedList<>();
			
			for (Level level : levels) {
				if (level.s1 <= stars && level.completed < 1) {
					ret.add(level);
				}
			}
			return ret;
		}
	}
	
	static class Level {

		private int s1;
		private int s2;
		int completed = 0;

		public Level(int s1, int s2) {
			this.s1 = s1;
			this.s2 = s2;
		}
		
	}
}
