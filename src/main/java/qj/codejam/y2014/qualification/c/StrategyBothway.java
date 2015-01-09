package qj.codejam.y2014.qualification.c;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import qj.util.math.Dimension;
import qj.util.math.Point;
import qj.util.math.Rectangle;

public class StrategyBothway {

	public static List<List<Boolean>> both(int r, int c, int m) {
		System.out.println("both...");
		int notM = r*c - m;

		for (int i = 2; i <= notM/2 && i<Math.max(r, c); i++) {
			int left = notM % i;
//			System.out.println("left=" + left);
			if ((notM - left)/i + (left==0 ? 0 : 1) < Math.min(r, c)) {
				Rectangle rectangle;
				if (i<c) {
					rectangle = new Rectangle(0,0, i, notM/i);
				} else {
					rectangle = new Rectangle(0,0, notM/i, i);
				}
				List<List<Boolean>> ret = fill(new Dimension(c, r), true);
				rectangle.eachPoint(p -> {
					ret.get(p.y).set(p.x, false);
					return false;
				});
				
				if (left == 0) {
					return ret;
				} else {
					Collection<Point> attempt = tryFit(left, rectangle.getSize());
					if (attempt != null) {
						attempt.stream().forEach(p -> ret.get(p.y).set(p.x, true));
						return ret;
					}
				}
			}
		}
		return null;
	}

	private static Collection<Point> tryFit(int left, Dimension size) {
		if (size.width == 2 || size.height==2) {
			return null;
		}
		
		LinkedList<Point> ret = new LinkedList<>();
		
		// Fill bottom
		for (int i = 0; i < size.width - 2 && left>0; i++) {
			ret.add(new Point(size.width-i - 1, size.height - 1));
			left--;
		}
		if (left >= 2) {
			left-=2;
			ret.add(new Point(0, size.height - 1));
			ret.add(new Point(1, size.height - 1));
		}
		
		// Fill right
		for (int i = 1; i < size.height - 2 && left > 0 ; i++) {
			ret.add(new Point(size.width - 1, size.height - 1-i));
			left--;
		}
		
		if (left==0) {
			return ret;
		} else if (left==1) {
			// If can, fill 0,0
			if (size.width >= 4 && size.height >= 5) {
				ret.add(new Point(0, 0));
				return ret;
			} else {
				return null;
			}
		} else {
			throw new RuntimeException("Left > 1 : " + left);
		}
		
	}

	private static <A> ArrayList<List<A>> fill(Dimension dimension, A a) {
		ArrayList<List<A>> ret = new ArrayList<>();
		for (int y = 0; y < dimension.height; y++) {
			ArrayList<A> line = new ArrayList<>();
			for (int x = 0; x < dimension.width; x++) {
				line.add(a);
			}
			ret.add(line);
		}
		return ret;
	}
}
