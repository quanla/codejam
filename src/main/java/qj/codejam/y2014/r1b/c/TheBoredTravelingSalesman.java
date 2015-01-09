package qj.codejam.y2014.r1b.c;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import qj.codejam.CodeJam;
import qj.util.Cols;
import qj.util.funct.Douce;

public class TheBoredTravelingSalesman {
	public static void main(String[] args) {
		String resName = "sample.in";
		CodeJam.solver(resName, lineF -> {
			String[] split = lineF.e().split(" ");
			int zipCount = Integer.parseInt(split[0]);
			List<Douce<Integer,String>> zips1 = new ArrayList<>(zipCount);
			for (int i = 0; i < zipCount; i++) {
				zips1.add(new Douce<>(i+1, lineF.e()));
			}
			List<Douce<Integer,String>> zips = Cols.sort(zips1, Douce.get2F());
			
			int m = Integer.parseInt(split[1]);
			HashMap<Integer, List<Integer>> links = new HashMap<>();
			for (int i = 0; i < m; i++) {
				String[] split2 = lineF.e().split(" ");
				Integer from = parse(Integer.valueOf(split2[0]), zips);
				Integer to = parse(Integer.valueOf(split2[1]), zips);
				Cols.addList(from, to, links);
				Cols.addList(to, from, links);
			}
			
			return () -> {
				HashSet<Integer> needs = new HashSet<>(Cols.yield(zips, (douce) -> douce.get1()));
//				List<Integer> canStart = Cols.yield(zips, (douce) -> douce.get1());
				return cal(
//						canStart, 
						needs, links, Cols.yield(zips, Douce.<Integer,String>get2F()));
			};
		}).solve(2);
	}
	
	protected static Integer parse(Integer from,
			List<Douce<Integer, String>> zips) {
		for (int i = 0; i < zips.size(); i++) {
			Douce<Integer, String> douce = zips.get(i);
			if (from.equals(douce.get1())) {
				return i+1;
			}
		}
		throw new RuntimeException();
	}

	static String cal(
//			List<Integer> canStart, 
			Set<Integer> needs, HashMap<Integer, List<Integer>> links, List<String> zips) {
		if (Cols.isEmpty(needs)) {
			return "";
		}
		for (int i = 0; i < zips.size(); i++) {
			if (!needs.contains(i + 1) ) { // canStart
				continue;
			}
			Set<Integer> newNeeds = Cols.remove(i+1, needs);
//			if (canGo(newNeeds, links)) {
//				List<Integer> newCanStart = links.get(i+1);
				String cal = cal(
//						newCanStart, 
						newNeeds, links, zips);
				if (cal==null) {
					continue;
				}
				return zips.get(i) + cal;
//			}
			
		}
		return null;
	}
	
	private static boolean canGo(Set<Integer> needs,
			HashMap<Integer, List<Integer>> links) {
		if (needs.size() == 1) {
			return true;
		}
		for (Integer need : needs) {
			boolean canGo = false;
			List<Integer> links1 = links.get(need);
			if (links1== null) {
				throw new RuntimeException();
			}
			for (Integer link : links1) {
				if (needs.contains(link)) {
					canGo = true;
					break;
				}
			}
			if (!canGo) {
				return false;
			}
		}
		return true;
	}

}
