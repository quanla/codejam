package qj.codejam.y2009.qualification.b;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import qj.util.Cols;
import qj.util.funct.F1;
import qj.util.funct.P1;
import qj.util.math.Dimension;
import qj.util.math.Matrix2;
import qj.util.math.Point;

class Map {
	ArrayList<List<Node>> nodes = new ArrayList<List<Node>>();
	LinkedList<Basin> basins = new LinkedList<Basin>();

	public void addRow(ArrayList<Integer> alts) {
		nodes.add(Cols.yield(alts, Node.contructor));
	}
	
	public void flowDown(Point from) {
		flowDown(from, new LinkedList<Point>(Collections.singleton(from)));
	}
	
	private void flowDown(Point from, Collection<Point> collecteds) {
		Collection<Point> minsAround = getMinsAround(from);
		
		if (Cols.isEmpty(minsAround)) {
			// Stop here, create new basin
			newBasin(collecteds);
			return;
		}
		
		Point minPrior = getPrior(minsAround);
		
//		System.out.println("from=" + from);
//		System.out.println("minsAround=" + minsAround);
//		System.out.println("minPrior=" + minPrior);
		Basin basin = getNode(minPrior).basin;
		if (basin != null) {
			// All collected go to this basin
			toBasin(collecteds, basin);
		} else {
			collecteds.add(minPrior);
			flowDown(minPrior, collecteds);
		}
	}

	private void toBasin(Collection<Point> collecteds, Basin basin) {
		for (Point point : collecteds) {
			getNode(point).basin = basin;
			basin.add(point);
		}
	}

	private void newBasin(Collection<Point> collecteds) {
		Basin basin = new Basin();
		basins.add(basin);
		toBasin(collecteds, basin);
	}

	private Point getPrior(Collection<Point> points) {
		if (points.size() == 1) {
			return Cols.getSingle(points);
		}
		ArrayList<Point> list = new ArrayList<Point>(points);
		Collections.sort(list, new Comparator<Point>() {public int compare(Point o1, Point o2) {
			if (o1.y != o2.y) {
				return o1.y - o2.y;
			}
			if (o1.x != o2.x) {
				return o1.x - o2.x;
			}
			return 0;
		}});
		
		return Cols.getSingle(list);
	}

	private Collection<Point> getMinsAround(Point from) {
		final int fromAlt = getAlt(from);
		
		final int[] min = {Integer.MAX_VALUE};
		final LinkedList<Point> ret = new LinkedList<Point>();
		Matrix2.eachAround4(from, getSize(), new P1<Point>() {public void e(Point arP) {
			int alt = getAlt(arP);
			if (alt >= fromAlt) {
				return;
			}
			
			if (alt < min[0]) {
				min[0] = alt;
				ret.clear();
				ret.add(arP);
			} else if (alt == min[0]) {
				ret.add(arP);
			}
		}});
		return ret;
	}

	private Dimension getSize() {
		return new Dimension(nodes.get(0).size(), nodes.size());
	}

	private int getAlt(Point from) {
		return getNode(from).alt;
	}

	public Node getNode(Point from) {
		return nodes.get(from.y).get(from.x);
	}

	public String draw() {
		char name = 'a';
		
		for (List<Node> row : nodes) {
			for (Node node : row) {
				if (node.basin.name == null) {
					node.basin.name = name++;
				}
			}
		}
		return Cols.join(Cols.yield(nodes, new F1<List<Node>,String>() {public String e(List<Node> row) {
			return Cols.join(Cols.yield(row, new F1<Node,String>() {public String e(Node node) {
				return node.basin.name.toString();
			}}), " ");
		}}), "\n");
	}

	public Collection<Point> getUnclaimedTops() {
		return Matrix2.getMaxes(getSize(), new F1<Point,Integer>() {public Integer e(Point p) {
			Node node = getNode(p);
			if (node.basin != null) {
				return null;
			}
			return node.alt;
		}});
	}

	static class Node {

		private Integer alt;
		Basin basin = null;

		static F1<Integer,Node> contructor = new F1<Integer,Node>() {public Node e(Integer alt) {
			return new Node(alt);
		}};
		public Node(Integer alt) {
			this.alt = alt;
		}
		
	}
	
	static class Basin {
		LinkedList<Point> points = new LinkedList<Point>();
		Character name = null;
		public void add(Point point) {
			points.add(point);
		}
		
	}
}