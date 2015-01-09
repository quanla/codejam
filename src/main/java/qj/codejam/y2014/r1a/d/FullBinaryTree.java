package qj.codejam.y2014.r1a.d;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import qj.codejam.CodeJam;
import qj.util.Cols;
import qj.util.funct.Douce;
import qj.util.funct.F0;
import qj.util.funct.F1;

public class FullBinaryTree {
	public static void main(String[] args) {
		String resName = "sample.in";
//		String resName = "B-small-attempt0.in";
		CodeJam.solver(resName, new F1<F0<String>, F0<String>>() {
			public F0<String> e(F0<String> lineF) {
				int count = Integer.parseInt(lineF.e());
				LinkedList<Douce<Integer, Integer>> links = new LinkedList<>();
				for (int i = 0; i < count - 1; i++) {
					String[] split = lineF.e().split(" ");
					Douce<Integer, Integer> link = new Douce<>(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
					links.add(link);
				}
				return new F0<String>() {
					public String e() {
						return "" + cal(links);
					}
				};
			}
		}).solve();
	}
	static int cal(LinkedList<Douce<Integer, Integer>> links) {
		if (links.size() == 1) {
			return 1;
		}
		HashMap<Integer, List<Integer>> nodes = buildNodes(links);
		
		int changes = 0;
		
		// Cut > 3 nodes
		changes += cutMoreThan3(nodes);
		
		changes += cutSmaller2(nodes);
		
		if (get2s(nodes).size()==0) {
			changes += cutSmaller3IfNo2(nodes);
		}
		
		return changes;

	}
	
	private static int cutSmaller3IfNo2(HashMap<Integer, List<Integer>> nodes) {
		int changes = 0;
		
		Collection<Integer> toCut = null;
		Integer fromNode = null;
		for (Integer node : get3s(nodes)) {
			Collection<Integer> smallestBranch = getSmallestBranch(node, nodes);
			if (toCut==null || toCut.size()>smallestBranch.size()) {
				toCut = smallestBranch;
				fromNode = node;
			}
		}
		changes+= toCut.size();
		cut(toCut, fromNode, nodes);
		
		return changes;
	}
	
	private static int cutSmaller2(HashMap<Integer, List<Integer>> nodes) {
		int changes = 0;
		
		for (Collection<Integer> get2s;(get2s = get2s(nodes)).size() > 1;) {
			
			Collection<Integer> toCut = null;
			Integer fromNode = null;
			for (Integer node : get2s) {
				Collection<Integer> smallestBranch = getSmallestBranch(node, nodes);
				if (toCut==null || toCut.size()>smallestBranch.size()) {
					toCut = smallestBranch;
					fromNode = node;
				}
			}
			changes+= toCut.size();
			cut(toCut, fromNode, nodes);
		}
		
		return changes;
	}
	private static Collection<Integer> get2s(HashMap<Integer, List<Integer>> nodes) {
		LinkedList<Integer> ret = new LinkedList<>();
		
		for (Entry<Integer, List<Integer>> e : nodes.entrySet()) {
			if (e.getValue().size() == 2) {
				ret.add(e.getKey());
			}
		}
		return ret;
	}
	private static Collection<Integer> get3s(HashMap<Integer, List<Integer>> nodes) {
		LinkedList<Integer> ret = new LinkedList<>();
		
		for (Entry<Integer, List<Integer>> e : nodes.entrySet()) {
			if (e.getValue().size() == 3) {
				ret.add(e.getKey());
			}
		}
		return ret;
	}
	private static int cutMoreThan3(HashMap<Integer, List<Integer>> nodes) {
		int count = 0;
		int size = nodes.size();
		for (int node = 1; node <= size; node++) {
			for (;;) {
				List<Integer> branches = nodes.get(node);
				if (branches == null || branches.size() <= 3) {
					break;
				}
				// Find trunk to cut
				Collection<Integer> toCut = getSmallestBranch(node,
						nodes);
				count += toCut.size();
				cut(toCut, node, nodes);
			}
		}
		return count;
	}
	private static Collection<Integer> getSmallestBranch(
			Integer node, 
			HashMap<Integer, List<Integer>> nodes) {
		Collection<Integer> smallest = null;
		for (Integer branch : nodes.get(node)) {
			Collection<Integer> shouldCut = shouldCut(branch, node, nodes);
			if (smallest == null || smallest.size() > shouldCut.size()) {
				smallest = shouldCut;
			}
		}
		return smallest;
	}
	private static void cut(Collection<Integer> cutNodes, Integer node, HashMap<Integer, List<Integer>> nodes) {
		for (Integer cut : cutNodes) {
			nodes.remove(cut);
		}
		for (Entry<Integer, List<Integer>> entry : nodes.entrySet()) {
			entry.getValue().removeAll(cutNodes);
		}
	}
	private static Collection<Integer> shouldCut(Integer branch, Integer node, HashMap<Integer, List<Integer>> nodes) {
		LinkedList<Integer> subs = new LinkedList<>();
		for (Integer sub : nodes.get(branch)) {
			if (sub.equals(node)) {
				continue;
			}
			subs.addAll( shouldCut(sub, branch, nodes));
		}
		subs.add(branch);
		return subs;
	}
	private static HashMap<Integer, List<Integer>> buildNodes(LinkedList<Douce<Integer, Integer>> links) {
		HashMap<Integer, List<Integer>> ret = new HashMap<Integer,List<Integer>>();
		for (Douce<Integer, Integer> link : links) {
			Cols.addList(link.get1(), link.get2(), ret);
			Cols.addList(link.get2(), link.get1(), ret);
		}
		return ret;
	}
}
