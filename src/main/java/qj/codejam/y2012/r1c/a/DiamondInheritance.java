package qj.codejam.y2012.r1c.a;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import qj.codejam.CodeJam;
import qj.util.funct.F0;
import qj.util.funct.F1;

/**
 * Wrong
 * @author Quan
 *
 */
public class DiamondInheritance {
	public static void main(String[] args) {
//		String resName = "sample.in";
		String resName = "A-small-practice.in";
		CodeJam.solver(resName, new F1<F0<String>, F0<String>>() {public F0<String> e(F0<String> lineF) {
			int count = Integer.parseInt(lineF.e());
			LinkedList<List<Integer>> build = new LinkedList<>();
			for (int i = 0; i < count; i++) {
				ArrayList<Integer> ints = CodeJam.toInts(lineF.e());
				build.add(ints.subList(1, ints.size()));
			}
			return new F0<String>() {public String e() {
				return hasDiamond(build) ? "Yes" : "No";
			}};
		}})
		.solve();
	}
	
	static boolean hasDiamond(LinkedList<List<Integer>> build) {
		Classes cls = new Classes(build.size());
		for (int classNum = 1; classNum <= build.size(); classNum++) {
			List<Integer> inhe = build.get(classNum - 1);
			for (Integer upCls : inhe) {
				if (cls.addUp(upCls, classNum)) {
					return true;
				}
			}
		}
		return false;
	}
	
	static class Classes {
		ArrayList<Class> cls;

		public Classes(int size) {
			cls = new ArrayList<>(size);
			for (int i = 0; i < size; i++) {
				cls.add(new Class());
			}
		}

		private boolean addUp(int upCls, int thisCls) {
			if (eachUp(upCls, new F1<Integer,Boolean>() {public Boolean e(Integer up) {
				Class thisClass = cls.get(thisCls - 1);
				if (!thisClass.ups.add(up)) {
					return true;
				}
				
				eachDown(thisCls, new F1<Integer,Boolean>() {public Boolean e(Integer thisDownNum) {
					Class thisDown = cls.get(thisDownNum - 1);
					if (!thisDown.ups.add(up)) {
						return true;
					}
					return false;
				}});
				
				return false;
			}})) {
				return true;
			};
			
			if (eachDown(thisCls, new F1<Integer,Boolean>() {public Boolean e(Integer down) {
				Class upClass = cls.get(upCls - 1);
				if (!upClass.downs.add(down)) {
					return true;
				}
				
				eachDown(upCls, new F1<Integer,Boolean>() {public Boolean e(Integer upUpNum) {
					Class upUp = cls.get(upUpNum - 1);
					if (!upUp.downs.add(down)) {
						return true;
					}
					return false;
				}});
				
				return false;
			}})) {
				return true;
			};
			
			
			return false;
		}

		private boolean eachUp(int upCls, F1<Integer,Boolean> f1) {
			if (f1.e(upCls)) {
				return true;
			}
			Class upClass = cls.get(upCls - 1);
			for (Integer up : upClass.ups) {
				if (f1.e(up)) {
					return true;
				}
			}
			return false;
		}
		private boolean eachDown(int downCls, F1<Integer,Boolean> f1) {
			if (f1.e(downCls)) {
				return true;
			}
			Class downClass = cls.get(downCls - 1);
			for (Integer down : downClass.downs) {
				if (f1.e(down)) {
					return true;
				}
			}
			return false;
		}

		private boolean addDown(int classNum, Class upClass) {
			if (!upClass.downs.add(classNum)) {
				return true;
			}
			for (Integer up : upClass.ups) {
				if (!cls.get(up - 1).downs.add(classNum)) {
					return true;
				}
			}
			return false;
		}
	}
	
	static class Class {
		HashSet<Integer> ups = new HashSet<>();
		HashSet<Integer> downs = new HashSet<>();
		@Override
		public String toString() {
			return "{ups=" + ups + ", downs=" + downs + "}";
		}
	}
}
