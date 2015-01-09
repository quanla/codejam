package qj.codejam.y2008.qualification.b;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import qj.codejam.CodeJam;
import qj.util.Cols;
import qj.util.DateUtil;
import qj.util.funct.F0;
import qj.util.funct.F1;

public class TrainTimetable {
	public static void main(String[] args) {
//		String resName = "sample.in";
//		String resName = "B-small-practice.in";
		String resName = "B-large-practice.in";
		CodeJam.solver(resName, new F1<F0<String>, F0<String>>() {public F0<String> e(F0<String> lineF) {
			long turnTime = Integer.parseInt(lineF.e()) * DateUtil.MINUTE_LENGTH;
			String[] split = lineF.e().split(" ");
			int na = Integer.parseInt(split[0]);
			int nb = Integer.parseInt(split[1]);
			
			SimpleDateFormat df = new SimpleDateFormat("HH:mm");
			final List<Long> needAtA = new ArrayList<Long>();
			final List<Long> readyAtB = new ArrayList<Long>();
			final List<Long> needAtB = new ArrayList<Long>();
			final List<Long> readyAtA = new ArrayList<Long>();
			for (int i = 0; i < na; i++) {
				String[] timeSplit = lineF.e().split(" ");
				needAtA.add(DateUtil.parse(timeSplit[0], df).getTime());
				readyAtB.add(DateUtil.parse(timeSplit[1], df).getTime() + turnTime);
			}
			for (int i = 0; i < nb; i++) {
				String[] timeSplit = lineF.e().split(" ");
				needAtB.add(DateUtil.parse(timeSplit[0], df).getTime());
				readyAtA.add(DateUtil.parse(timeSplit[1], df).getTime() + turnTime);
			}
			
			return new F0<String>() {public String e() {
				return solve(readyAtA, needAtA) + " " + solve(readyAtB, needAtB);
			}};
		}})
		.solve();
	}

	private static int solve(List<Long> readys, List<Long> needs) {
		if (needs.size() == 0) {
			return 0;
		}
		
		readys = Cols.reverse(Cols.sort(readys));
		needs = Cols.reverse(Cols.sort(needs));
		
		for (Long ready : readys) {
			Long need = needs.get(0);
			if (ready <= need) {
				needs.remove(0);
				if (needs.size() == 0) {
					return 0;
				}
			}
		}
		
		return needs.size();
	}
}
