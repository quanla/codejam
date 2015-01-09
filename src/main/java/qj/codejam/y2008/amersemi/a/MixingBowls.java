package qj.codejam.y2008.amersemi.a;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import qj.codejam.CodeJam;
import qj.util.Cols;
import qj.util.funct.F0;
import qj.util.funct.F1;

public class MixingBowls {
	public static void main(String[] args) {
		String resName = "A-small-practice.in";
//		String resName = "A-large-practice.in";
//		String resName = "sample.in";
		CodeJam.solver(resName, (F0<String> lineF) -> {
			int mixturesCount = Integer.parseInt(lineF.e());
			final HashMap<String, List<String>> recipes = new HashMap<>();
			
			String toMake = null;
			for (int i = 0; i < mixturesCount; i++) {
				String[] split = lineF.e().split(" ");
				String name = split[0];
				LinkedList<String> mixtures = new LinkedList<String>();
				for (int j = 2; j < split.length; j++) {
					if (Character.isUpperCase(split[j].charAt(0))) {
						mixtures.add(split[j]);
					}
				}
				recipes.put(name, mixtures);
				if (i==0) {
					toMake = name;
				}
			}
			final String targetMixture = toMake;
			
			return () -> "" + require(targetMixture, recipes);
		})
		.solve();
	}


	private static int require(String mixture,
			HashMap<String, List<String>> recipes) {
		List<String> ingredients = recipes.get(mixture);
		if (Cols.isEmpty(ingredients)) {
			return 1;
		}
		List<Integer> ingReqs = new ArrayList<Integer>();
		for (String ingredient : ingredients) {
			int ingReq = require(ingredient, recipes);
			ingReqs.add(ingReq);
		}
		Collections.sort(ingReqs);
		ingReqs = Cols.reverse(ingReqs);
		int ret = ingredients.size() + 1;
		for (int i = 0; i < ingReqs.size(); i++) {
			ret = Math.max(ret, ingReqs.get(i) + i);
		}
		return ret;
	}
}
