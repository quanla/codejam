package qj.codejam.y2013.qualification.d;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import qj.codejam.CodeJam;
import qj.util.Cols;
import qj.util.MathUtil;
import qj.util.funct.F0;
import qj.util.funct.F1;

public class Treasure {
	public static void main(String[] args) {
//		String resName = "sample.in";
//		String resName = "D-small-practice.in";
		String resName = "D-large-practice.in";
		CodeJam.solver(resName, new F1<F0<String>, F0<String>>() {public F0<String> e(F0<String> lineF) {
			Integer chestCount = CodeJam.toInts(lineF.e().split(" ")).get(1);
			final ArrayList<Integer> startKeys = CodeJam.toInts(lineF.e().split(" "));
			final LinkedList<Chest> chests = new LinkedList<Chest>();
			for (int i = 0; i < chestCount; i++) {
				LinkedList<Integer> list = new LinkedList<Integer>(CodeJam.toInts(lineF.e().split(" ")));
				Chest chest = new Chest();
				chest.name = "" + (i+1);
				chest.open = list.removeFirst();
				list.removeFirst();
				chest.contains = list;
				chests.add(chest);
			}
			return new F0<String>() {public String e() {
//				System.out.println("startKeys=" + startKeys);
//				System.out.println(Cols.toString(chests));
				
				List<String> canOpenAll = canOpenAll(startKeys, chests);
				return canOpenAll == null ? "IMPOSSIBLE" : Cols.join(canOpenAll, " ");
			}};
		}})
		.solve();
	}
	
	static List<String> canOpenAll(List<Integer> keys, List<Chest> chests) {
		if (Cols.isEmpty(chests)) {
			return Collections.emptyList();
		}
		
		if (!surCheck(keys, chests)) {
			return null;
		}
		
		for (int i = 0; i < chests.size(); i++) {
			Chest chest = chests.get(i);
			int hasKey = keys.indexOf(chest.open);
			if (hasKey > -1) {
				List<String> sub = canOpenAll(Cols.addAll(chest.contains, Cols.splice(hasKey, 1, keys)), Cols.splice(i, 1, chests));
				if (sub != null) {
					return Cols.addFirst(chest.name, sub);
				}
			}
		}
//		System.out.println("Can not open : " + chests + "");
//		return null;
		throw new RuntimeException("Why?");
	}
	
	private static boolean surCheck(List<Integer> keys, List<Chest> chests) {
		// Need to have more keys than required
		if (!hasEnoughKeys(keys, chests)) {
//			System.out.println("Not enough keys");
			return false;
		}
		
		if (!canAccessAllKeyTypes(keys, chests)) {
//			System.out.println("Can't access all key types");
			return false;
		}
		
		
		
		// If can access all key types
		
		return true;
	}

	private static boolean canAccessAllKeyTypes(List<Integer> keys,
			List<Chest> chests) {
		HashSet<Integer> requiredKeys = new HashSet<Integer>(Cols.yield(chests, new F1<Chest,Integer>() {public Integer e(Chest obj) {
			return obj.open;
		}} ));
		
		Set<Integer> processingKeys = new HashSet<Integer>(keys);
		HashSet<Integer> avaiKeys = new HashSet<Integer>();
		
		requiredKeys.removeAll(processingKeys);
		if (requiredKeys.isEmpty()) {
			return true;
		}
		
		Map<Integer, Collection<Chest>> index = Cols.indexMulti(chests, new F1<Chest,Integer>() {public Integer e(Chest obj) {
			return obj.open;
		}});
		
		for (;!processingKeys.isEmpty();) {
			// get new new-keys
			Set<Integer> newKeys = getNewKeys(processingKeys, avaiKeys, index);
			
			requiredKeys.removeAll(newKeys);
			if (requiredKeys.isEmpty()) {
				return true;
			}
			
			avaiKeys.addAll(processingKeys);
			processingKeys = newKeys;
		}
		
		return false;
	}

	private static Set<Integer> getNewKeys(Set<Integer> processingKeys,
			HashSet<Integer> avaiKeys, Map<Integer, Collection<Chest>> index) {
		HashSet<Integer> ret = new HashSet<Integer>();
		for (Integer key : processingKeys) {
			
			Collection<Chest> collection = index.get(key);
			if (collection == null) {
				continue;
			}
			for (Chest chest : collection) {
				for (Integer newKey : chest.contains) {
					if (!avaiKeys.contains(newKey) && !processingKeys.contains(newKey)) {
						ret.add(newKey);
					}
				}
			}
		}
		return ret;
	}

	public static boolean hasEnoughKeys(List<Integer> keys, List<Chest> chests) {
		HashMap<Integer, int[]> hasKeys = new HashMap<Integer,int[]>();
		for (Integer key : keys) {
			MathUtil.add(key, 1, hasKeys);
		}
		
		for (Chest chest : chests) {
			for (Integer contain : chest.contains) {
				MathUtil.add(contain, 1, hasKeys);
			}
		}
		
		for (Chest chest : chests) {
			MathUtil.add(chest.open, -1, hasKeys);
		}
		
		return !Cols.any(hasKeys.values(), new F1<int[], Boolean>() {public Boolean e(int[] obj) {
			return obj[0] < 0;
		}});
	}

	static class Chest {
		protected String name;
		Integer open;
		List<Integer> contains;
		@Override
		public String toString() {
			return "[open=" + open + ", contains=" + contains + "]";
		}
	}
}
