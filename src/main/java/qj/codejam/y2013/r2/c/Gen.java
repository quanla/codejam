package qj.codejam.y2013.r2.c;

import java.util.ArrayList;
import java.util.Collections;

import qj.util.Cols;
import qj.util.Cols.RandomAccessCol;
import qj.util.funct.Douce;
import qj.util.funct.F2;
import qj.util.funct.Fs;

/*


/8\  /5\  /6\  /4\  /1\  /3\  /2\  /7\
1 5  1 4  2 4  1 3  1 1  2 2  2 1  3 1  

/3\  /2\  /5\  /8\  /6\  /4\  /7\  /1\
1 3  1 2  2 3  3 4  3 3  2 2  4 2  1 1  
*/
public class Gen {
	public static void main(String[] args) {
		print();
	}

	private static ArrayList<Integer> list() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 1; i <= 8; i++) {
			list.add(i);
		}
		Collections.shuffle(list);
		return list;
	}

	private static void print() {
		
		for (;;) {
			ArrayList<Integer> list1 = list();
			RandomAccessCol<Integer> list = Cols.randomAccessCol(list1, Fs.f1());
	//		System.out.println("/" + Cols.join(list, "\\  /") + "\\");
			ArrayList<Douce<Integer, Integer>> ds = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {
				Douce<Integer, Integer> douce = new Douce<>(maxSeqLeft(i, list), maxSeqRight(i, list));
				ds.add(douce);
				
				
	//			System.out.print(maxSeqLeft(i, list) + " " + maxSeqRight(i, list) + "  ");
			}
			if (Cols.eachPair(ds, new F2<Douce<Integer, Integer>,Douce<Integer, Integer>,Boolean>() {public Boolean e(Douce<Integer, Integer> a,
					Douce<Integer, Integer> b) {
				return a.equals(b);
			}})) {
				System.out.println("!");
				// Not a good example
				continue;
			};
	
			System.out.println("/" + Cols.join(list1, "\\  /") + "\\");
			for (int i = 0; i < list.size(); i++) {
				Integer left = ds.get(i).get1();
				Integer right = ds.get(i).get2();
				System.out.print(left + " " + right + "  ");
			}
			break;
		}
	}

	public static int maxSeqLeft(int i, RandomAccessCol<Integer> list) {
		if (i == 0) {
			return 1;
		}
		
		Integer now = list.get(i);
		if (now == null) {
			return 1;
		}
		int max = 1;
		for (int j = i - 1; j > -1; j--) {
			Integer atJ = list.get(j);
			if (atJ != null) {
				if (atJ < now) {
					max = Math.max(max, maxSeqLeft(j, list) + 1);
				}
			}
		}
		return max;
	}
	public static int maxSeqRight(int i, RandomAccessCol<Integer> list) {
		if (i == list.size() - 1) {
			return 1;
		}
		
		Integer now = list.get(i);
		if (now == null) {
			return 1;
		}

		int max = 1;
		for (int j = i + 1; j < list.size(); j++) {
			Integer atJ = list.get(j);
			if (atJ != null) {
				if (atJ < now) {
					max = Math.max(max, maxSeqRight(j, list) + 1);
				}
			}
		}
		return max;
	}
	
}
