package qj.codejam.practice.d;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import qj.codejam.CodeJam;
import qj.util.Cols;
import qj.util.funct.F0;
import qj.util.funct.F1;
import qj.util.funct.P1;
import qj.util.math.Point;

public class ShoppingPlan {
	public static void main(String[] args) {
//		String resName = "D-small-practice.in";
		String resName = "D-large-practice.in";
		CodeJam.solver(resName, new F1<F0<String>, F0<String>>() {public F0<String> e(F0<String> lineF) {
			ArrayList<Integer> ints = CodeJam.toInts(lineF.e().split(" "));
//				Integer itemsCount = ints.get(0);
			Integer storesCount = ints.get(1);
			Integer priceOfGas = ints.get(2);
			
			String[] itemsStr = lineF.e().split(" ");
			
			HashSet<String> items = toItems(itemsStr);
			HashSet<String> perishables = toPerishables(itemsStr);
			
			LinkedList<Store> stores = getStores(storesCount, lineF);
			
			Layout layout = new Layout(priceOfGas, stores, perishables);
			
			return new F0<String>() {
				@Override
				public String e() {
					State state = new State(items, layout);
					State min = findMinTravelCost(state, true);
					return "" + new DecimalFormat("#.0000000").format(min.cost);
				}
			};
		}}).solve();
	}

	static class Travel {
		State state;
		double cost;
		public Travel(State state, double cost) {
			this.state = state;
			this.cost = cost;
		}
	}
	private static State findMinTravelCost(State initState, boolean debug) {
		if (initState.itemsToBuy.isEmpty()) {
			return initState;
		}
		
		double[] min = {Double.MAX_VALUE};
		State[] minTravel = {null};
		for (Store store : initState.layout.stores) {
			List<Item> has = Cols.filter(store.items, ((item) -> {
				return initState.itemsToBuy.contains(item.name);
			}));
			if (Cols.isNotEmpty(has)) {
				Cols.eachSubSetAndSelf(new HashSet<Item>(has), new P1<Set<Item>>() {public void e(Set<Item> itemSet) {
					State state = startWith(initState, store, itemSet);
					if (min[0] > state.cost) {
						min[0] = state.cost;
						minTravel[0] = state;
					}
				}});
				
			}
		}
		return minTravel[0];
	}

	static State startWith(State state, Store store, Set<Item> itemSet) {
		state = state.gotoStore(store, itemSet);
		
		if (state.needGoHome) {
			Point home = new Point(0,0);
			state = state.didGo(state.loc.distance(home) * state.layout.priceOfGas, home, state.itemsToBuy, false);
		}
		
		if (state.itemsToBuy.isEmpty() && !state.loc.equals(new Point(0,0))) {
			return state.goHome();
		}
		
		return findMinTravelCost(state, false);
	}
	static class Layout {

		int priceOfGas;
		LinkedList<Store> stores;
		HashSet<String> perishables;

		public Layout(int priceOfGas, 
				LinkedList<Store> stores,
				HashSet<String> perishables) {
			this.priceOfGas = priceOfGas;
			this.stores = stores;
			this.perishables = perishables;
		}
		
	}
	static class HistoryItem {
		Point go;
		double cost;
		public HistoryItem(Point go, double cost) {
			this.go = go;
			this.cost = cost;
		}
		@Override
		public String toString() {
			return "[go=" + go + ", cost=" + cost + "]";
		}
		
	}
	static class State {
		Point loc;
		
		private HashSet<String> itemsToBuy;
		private Layout layout;
//		private LinkedList<HistoryItem> history;
		double cost;

		private boolean needGoHome;
		
		private State(Point loc, HashSet<String> itemsToBuy, Layout layout) {
			this.loc = loc;
			this.itemsToBuy = itemsToBuy;
			this.layout = layout;
		}
		
		public State didGo(double cost, Point loc, HashSet<String> itemsToBuy, boolean needGoHome) {
			State state = new State(loc, itemsToBuy, this.layout);
			
//			LinkedList<HistoryItem> history = new LinkedList<>(this.history);
//			history.add(new HistoryItem(loc, cost));
//			state.history = history;
			state.needGoHome = needGoHome;
			state.cost = this.cost + cost;
			return state;
		}

		public State(HashSet<String> itemsToBuy, Layout layout) {
			this.itemsToBuy = itemsToBuy;
			this.layout = layout;
			loc = new Point(0, 0);
//			history = new LinkedList<>();
			needGoHome = false;
			cost = 0;
		}

		public State goHome() {
			Point to = new Point(0, 0);
			return this.didGo(loc.distance(to) * layout.priceOfGas, to, itemsToBuy, false);
		}

		public State gotoStore(Store store, Set<Item> itemSet) {
			double cost = loc.distance(store.loc) * layout.priceOfGas;
			
			boolean needGoHome = false;
			HashSet<String> newItemsToBuy = new HashSet<>(itemsToBuy);
			
			for (Item item : itemSet) {
				newItemsToBuy.remove(item.name);
				cost += item.price;
				
				if (layout.perishables.contains(item.name)) {
					needGoHome = true;
				}
			}
			
			return this.didGo(cost, store.loc, newItemsToBuy, needGoHome);
		}
		
	}
	
	static class GoResult {
		double cost;
		State newState;
		public GoResult(double cost, State newState) {
			this.cost = cost;
			this.newState = newState;
		}
	}
	protected static LinkedList<Store> getStores(int count, F0<String> lineF) {
		LinkedList<Store> stores = new LinkedList<>();
		for (int i = 0; i < count; i++) {
			String[] split = lineF.e().split(" ");
			int x = Integer.parseInt(split[0]);
			int y = Integer.parseInt(split[1]);
			
			Point loc = new Point(x, y);
			
			LinkedList<Item> items = new LinkedList<>();
			for (int j = 2; j < split.length; j++) {
				String[] split2 = split[j].split(":");
				
				Item item = new Item();
				item.name = split2[0];
				item.price = Integer.parseInt(split2[1]);
				items.add(item);
			}
			Store store = new Store();
			store.loc = loc;
			store.items = items;
			stores.add(store);
		}
		return stores;
	}
	
	static class Store {
		Point loc;
		Collection<Item> items;
		@Override
		public String toString() {
			return "Store [loc=" + loc + ", items=" + items + "]";
		}
	}
	static class Item {
		String name;
		int price;
		@Override
		public String toString() {
			return "Item [name=" + name + ", price=" + price + "]";
		}
	}

	static HashSet<String> toItems(String[] names) {
		HashSet<String> ret = new HashSet<>();
		for (String itemStr : names) {
			if (itemStr.endsWith("!")) {
				ret.add(itemStr.substring(0, itemStr.length() - 1));
			} else {
				ret.add(itemStr);
			}
		}
		return ret;
	}
	
	static HashSet<String> toPerishables(String[] names) {
		HashSet<String> ret = new HashSet<>();
		for (String itemStr : names) {
			if (itemStr.endsWith("!")) {
				ret.add(itemStr.substring(0, itemStr.length() - 1));
			}
		}
		return ret;
	}
	
}
