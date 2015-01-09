package qj.codejam.practice.b;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import qj.util.funct.F1;
import qj.util.funct.P1;
import qj.util.math.Point;

public class Maze<E> {
	HashMap<Point, E> elems = new HashMap<>();

	public F1<Point, E> createElem;
	
	public E getf(Point loc) {
		E e = get(loc);
		if (e == null) {
			loc = loc.clone();
			e = createElem.e(loc);
			set(loc, e);
		}
		return e;
	}

	private void set(Point loc, E e) {
//		System.out.println("Setting " + loc + "=" + e);
		elems.put(loc, e);
//		System.out.println(elems.get(new Point(0, 0)));
	}

	private E get(Point loc) {
		return elems.get(loc);
	}

	public void eachLine(P1<List<E>> lineP) {
		int left = getLeft();
		int width = getRight() - left;
		
//		System.out.println("left=" + left);
//		System.out.println("right=" + getRight());
		
		for (int y = 0;; y++) {
			ArrayList<E> line = new ArrayList<>(width);
			for (int x = 0; x < width; x++) {
				E e = get(new Point(x + left, y));
				if (e == null) {
					return;
				}
				line.add(e);
			}
			lineP.e(line);
		}
	}

	private int getRight() {
		for (int x = 0; ; x++) {
			if (get(new Point(x, 0)) == null) {
				return x;
			}
		}
	}

	private int getLeft() {
		for (int x = 0; ; x--) {
			if (get(new Point(x, 0)) == null) {
				return x + 1;
			}
		}
	}
	
}
