package qj.codejam.y2013.r1b.a;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import qj.codejam.CodeJam;
import qj.util.MathUtil;
import qj.util.funct.Douce;
import qj.util.funct.F0;
import qj.util.funct.F1;

public class Osmos {
	public static void main(String[] args) {
//		String resName = "sample.in";
//		String resName = "A-small-practice.in";
		String resName = "A-large-practice.in";
		CodeJam.solver(resName, new F1<F0<String>, F0<String>>() {public F0<String> e(F0<String> lineF) {
			final int initSize = Integer.parseInt(lineF.e().split(" ")[0]);
			final ArrayList<Integer> others = CodeJam.toInts(lineF.e().split(" "));
			return new F0<String>() {public String e() {
				Collections.sort(others);
				
				return "" + absorb(others, initSize);
			}};
		}})
		.solve(); // 2
	}
	static int absorb(List<Integer> motes, int size) {
		System.out.println("init size=" + size);
		System.out.println("motes=" + motes);
		// Pre-check
		if (size <= 1) {
			return motes.size();
		}
		
		// List all adds required
		List<Integer> adds = adds(motes, size);
		System.out.println("adds=" + adds);
		
		// Find cut pos
		int cutPos = cutPos(adds);
		System.out.println("cutPos=" + cutPos);
		
		// Return cut pos to end + all adds to that pos
		if (cutPos == -1) {
			return MathUtil.sumI(adds);
		} else {
			return (adds.size() - cutPos) + MathUtil.sumI(adds.subList(0, cutPos));
		}
	}
	private static int cutPos(List<Integer> adds) {
		int totalAddsRequired = MathUtil.sumI(adds);
		for (int i = 0; i < adds.size(); i++) {
			int add = adds.get(i);
			if (add == 0) {
				continue;
			}
			if (totalAddsRequired > adds.size() - i) {
				return i;
			}
			totalAddsRequired -= add;
		}
		return -1;
	}
	private static List<Integer> adds(List<Integer> motes, int size) {
		LinkedList<Integer> ret = new LinkedList<Integer>();
		for (Integer mote : motes) {
			if (mote < size) {
				size += mote;
				ret.add(0);
			} else {
				Douce<Integer, Integer> douce = tryAdd(size, mote);
				Integer addCount = douce.get1();
				size = douce.get2() + mote;
				ret.add(addCount);
			}
		}
		
		return ret;
	}
	private static Douce<Integer, Integer> tryAdd(int size, int mote) {
		for (int i = 0;;i++) {
			size += size-1;
			if (size > mote) {
				return new Douce<Integer, Integer>(i+1, size);
			}
		}
	}
}
