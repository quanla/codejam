package qj.codejam.y2013.r2.a;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import qj.codejam.CodeJam;
import qj.util.Cols;
import qj.util.MathUtil;
import qj.util.funct.F1;
import qj.util.funct.Triple;

public class TicketSwapping {
	public static void main(String[] args) {
//		String resName = "sample.in";
//		String resName = "A-small-practice.in";
		String resName = "A-large-practice.in";
		CodeJam.solver(resName, lineF -> {
			ArrayList<Long> longs = CodeJam.toLongs(lineF.e());
			Long n = longs.get(0);
			Long count = longs.get(1);
			ArrayList<Triple<Long, Long, Long>> groups = new ArrayList<>();
			for (long i = 0; i < count; i++) {
				ArrayList<Long> longs2 = CodeJam.toLongs(lineF.e());
				Long from = longs2.get(0);
				Long to = longs2.get(1);
				Long num = longs2.get(2);
				
				groups.add(new Triple<Long, Long, Long>(from, to, num));
			}
			return () -> "" + cal(n, groups);
		})
		.solve();
	}
	private static BigDecimal cal(Long n, List<Triple<Long, Long, Long>> groups) {
		BigDecimal normal = normal(groups, n);
//		System.out.prlongln("normal=" + normal);
		return normal.subtract(saving(groups, n)).remainder(new BigDecimal(1000002013));
	}
	
	private static BigDecimal normal(List<Triple<Long, Long, Long>> groups,
			Long n) {
		return MathUtil.sumB(groups, (g) -> {
			long distance = g.get2() - g.get1();
			
			return cost(n, distance).multiply(new BigDecimal( g.get3()));
		});
	}
	private static BigDecimal cost(Long n, long distance) {
		long th = distance -1;
		return new BigDecimal(n*distance - (th+1)*th/2);
	}
	
//	public static void main(String[] args) {
//		System.out.prlongln(cost(6, 2));
//	}
	private static BigDecimal saving(List<Triple<Long, Long, Long>> groups1,
			Long n) {
		
		Pool pool = buildPool(groups1);
		LinkedList<Triple<Long, Long, Long>> groupsOut = new LinkedList<>(Cols.sort(groups1, Triple.get2F()));
		
		BigDecimal total = BigDecimal.ZERO;
		for (;groupsOut.size() > 0;) {
			Collection<Triple<Long, Long, Long>> outs = Cols.retrieveNexts(groupsOut, Triple.get2F());
			for (Triple<Long, Long, Long> out : outs) {
				total = total.add(pool.retrieveBests(out, n));
			}
		}
//		System.out.prlongln("saving=" + total);
		return total;
	}

	private static Pool buildPool(
			List<Triple<Long, Long, Long>> groups) {

		Pool pool = new Pool(groups.size());
		for (Triple<Long, Long, Long> group : groups) {
			pool.add(group);
		}
		pool.sort();
		return pool;
	}

	static class Pool {

		ArrayList<Tickets> g;
		public Pool(int size) {
			g = new ArrayList<>(size);
		}

		public void add(Triple<Long, Long, Long> group) {
			g.add(new Tickets(group.get1(), group.get3()));
		}

		public void sort() {
			Cols.sort(g, Tickets.fromF);
		}

		public BigDecimal retrieveBests(Triple<Long, Long, Long> out, Long n) {
			BigDecimal cost = BigDecimal.ZERO;
			
			Long station = out.get2();
			long needed = out.get3();
//			System.out.println("Outting at " + station);
			
			int i = Cols.searchIndexedBinaryToHigh(g, Tickets.fromF, out.get2());
			
//			System.out.println("i=" + i);
			for (; i > -1 && needed > 0 ; i--) {
				Tickets tickets = g.get(i);
				
				if (tickets.size > 0) {
					long consumed = Math.min(needed, tickets.size);
					
//					System.out.println("Consumming " + consumed + " from " + tickets.from);
					BigDecimal dcost = cost(n, station - tickets.from).multiply(new BigDecimal( consumed));
//					System.out.println("dcost=" + dcost);
					cost = cost.add(dcost);
					tickets.size -= consumed;
					needed -= consumed;
				}
			}
			
			if (needed >0) {
				throw new RuntimeException();
			}
			
			return cost;
		}
		
	}
	
	static class Tickets {
		static F1<Tickets, Long> fromF = (t) -> t.from;
		long from;
		long size;
		public Tickets(long from, long size) {
			this.from = from;
			this.size = size;
		}
	}
}
