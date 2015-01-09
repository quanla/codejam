package qj.codejam.y2008.qualification.a;

import java.util.LinkedList;
import java.util.List;

import qj.codejam.CodeJam;
import qj.util.Cols;

public class SavingTheUniverse {
	public static void main(String[] args) {
//		String resName = "sample.in";
		String resName = "A-large-practice.in";
//		String resName = "A-small-practice.in";
		CodeJam.solver(resName, lineF -> {
			int seCount = Integer.parseInt(lineF.e());
			final LinkedList<String> ses = new LinkedList<String>();
			for (int i = 0; i < seCount; i++) {
				ses.add(lineF.e());
			}
			
			final LinkedList<String> words = new LinkedList<String>();
			int wCount = Integer.parseInt(lineF.e());
			for (int i = 0; i < wCount; i++) {
				words.add(lineF.e());
			}
			
			return () -> "" + howManySwitches(words, ses);
		})
		.solve();
	}


	private static int howFarCanGo(String se, List<String> words) {
		int indexOf = words.indexOf(se);
		if (indexOf==-1) {
			return words.size();
		}
		return indexOf;
	}


	public static int howManySwitches(final List<String> words,
			final List<String> ses) {
		if (words.size() == 0) {
			return 0;
		}
		
		int goSize = Cols.<String>maxI(ses, se -> howFarCanGo(se, words));
		
		if (goSize==words.size()) {
			return 0;
		}
		
		return 1 + howManySwitches(words.subList(goSize, words.size()), ses);
	}
}
