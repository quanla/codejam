package qj.codejam.y2011.r2.d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import qj.codejam.CodeJam;
import qj.tool.path.PathFinder;
import qj.util.Cols;

/**
 * TODO Not done: too slow
 * @author Quan
 *
 */
public class AIWar {
	public static void main(String[] args) {
//		String resName = "sample.in";
//		String resName = "D-small-practice.in";
		String resName = "D-large-practice.in";
		CodeJam.solver(resName, lineF -> {
			lineF.e();
			
			HashMap<Integer, Set<Integer>> links = new HashMap<Integer,Set<Integer>>();
			for (String string : lineF.e().split(" ")) {
				String[] split = string.split(",");
				Integer from = Integer.valueOf(split[0]);
				Integer to = Integer.valueOf(split[1]);
				
				Cols.addSet(from, to, links);
				Cols.addSet(to, from, links);
			}
			return () -> cal(links);
		}).solve(6);
	}
	
	static String cal(HashMap<Integer,Set<Integer>> links) {
		LinkedList<Set<Integer>> steps = PathFinder.getSteps(Collections.singleton(0), 1, (n) -> links.get(n));
		
//		System.out.println("steps:\n" + Cols.toString(steps));
		
		return "" + (steps.size() - 2) + " " + (max(new ArrayList<>(steps), links) - steps.size() + 1);
	}

	private static int max(ArrayList<Set<Integer>> steps,
			HashMap<Integer, Set<Integer>> links) {
		int beforeLastStepIndex = steps.size()-2;
		Set<Integer> beforeLastStep = steps.get(beforeLastStepIndex);
		int max = 0;
		for (Integer node : links.get(1)) {
			if (beforeLastStep.contains(node)) {
				// This node is before last
				max = Math.max(max, max(node, beforeLastStepIndex, steps, links));
			}
		}
		
		
		
		return max;
	}

	private static int max(Integer node, int stepIndex,
			ArrayList<Set<Integer>> steps, HashMap<Integer, Set<Integer>> links) {
		if (stepIndex == 0) {
			return links.get(node).size() + 1;
		}
		
		int max = 0;
		Set<Integer> lastStep = steps.get(stepIndex - 1);
		for (Integer lastNode : links.get(node)) {
			if (lastStep.contains(lastNode)) {
				max = Math.max(max, max(node, lastNode, stepIndex, steps, links));
			}
		}
		return max;
	}

	private static int max(Integer node, Integer lastNode, int stepIndex,
			ArrayList<Set<Integer>> steps, HashMap<Integer, Set<Integer>> links) {
		if (stepIndex == 1) {
			HashSet<Integer> set = new HashSet<Integer>(links.get(node));
			set.addAll(links.get(0));
			return set.size();
		}
		
		int max = 0;
		Set<Integer> step = steps.get(stepIndex - 2);
		for (Integer pnode : links.get(lastNode)) {
			if (step.contains(pnode)) {
				max = Math.max(max, max(lastNode, pnode, stepIndex - 1, steps, links) + newLinkCount(node, lastNode, pnode, links));
			}
		}
		return max;
	}

	private static int newLinkCount(Integer node, Integer lastNode,
			Integer pnode, HashMap<Integer, Set<Integer>> links) {
		Set<Integer> set1 = links.get(lastNode);
		Set<Integer> set2 = links.get(pnode);
		
		int count = 0;
		for (Integer n : links.get(node)) {
			if (!set1.contains(n) && !set2.contains(n)) {
				count ++;
			}
		}
		return count;
	}
}
