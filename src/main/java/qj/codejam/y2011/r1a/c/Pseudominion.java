package qj.codejam.y2011.r1a.c;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import qj.codejam.CodeJam;
import qj.tool.graph.Graph;
import qj.tool.graph.Node;
import qj.util.Cols;
import qj.util.funct.F1;
import qj.util.funct.F2;

public class Pseudominion {
	public static void main(String[] args) {
//		String resName = "test.in";
//		String resName = "test2.in";
//		String resName = "sample.in";
//		String resName = "sample2.in";
//		String resName = "C-small-practice.in";
		String resName = "C-large-practice.in";
		CodeJam.solver(resName, lineF -> {
			int inhandcount = Integer.parseInt(lineF.e());
			ArrayList<Card> inhands = new ArrayList<>(inhandcount);
			for (int i = 0; i < inhandcount; i++) {
				inhands.add( toCard(lineF.e()) );
			}
			int deckCount = Integer.parseInt(lineF.e());
			ArrayList<Card> inDecks = new ArrayList<>(deckCount);
			for (int i = 0; i < deckCount; i++) {
				inDecks.add(toCard(lineF.e()));
			}
			index(inhands, inDecks);
			return () -> "" + max(inhands, inDecks, 1, 0);
		}).solve();
	}
	
	static void index(ArrayList<Card> inhands, ArrayList<Card> inDecks) {
		int i = 0;
		for (; i < inhands.size(); i++) {
			inhands.get(i).index = i;
		}
		for (int j = 0; j < inDecks.size(); j++) {
			inDecks.get(j).index = j + i;
		}
	}
	
	static int max(List<Card> inhands, List<Card> inDecks, int turn, int depth) {
		int hand = inhands.size();
		inhands.addAll(inDecks);
//		int total = inhands.size();
		
		List<Card> hasTurns = Cols.remove(inhands, (card) -> card.turns > 0);
		List<Card> c0s = Cols.remove(inhands, (card) -> card.turns == 0 && card.c == 0);
		List<Card> c1s = Cols.remove(inhands, (card) -> card.turns == 0 && card.c == 1);
		List<Card> c2s = Cols.remove(inhands, (card) -> card.turns == 0 && card.c == 2);
		
		assert inhands.isEmpty();
		
		F2<Integer,Integer,Integer> c0sum = c0sum(c0s);

		Graph<State> graph = new Graph<State>();
		graph.resolveI((state, addLink) -> {
			if (state.turns <= 0) {
				return;
			}
			
			if (state.t < hasTurns.size()) {
				Card t = hasTurns.get(state.t);
				if (t.index < state.hand) {
					addLink.e(t.s, new State(
							state.hand + t.c, 
							state.turns + t.turns - 1, 
							state.t + 1, 
							state.c1, 
							state.c2));
					return;
				}
			}

			if (state.c1 < c1s.size()) {
				Card c1 = c1s.get(state.c1);
				if (c1.index < state.hand) {
					addLink.e(c1.s, new State(
							state.hand + c1.c, 
							state.turns - 1, 
							state.t, 
							state.c1 + 1, 
							state.c2));

					addLink.e(0, new State(
							state.hand, 
							state.turns, 
							state.t, 
							state.c1 + 1, 
							state.c2));
				}
			}
			
			if (state.c2 < c2s.size()) {
				Card c2 = c2s.get(state.c2);
				if (c2.index < state.hand) {
					addLink.e(c2.s, new State(
							state.hand + c2.c, 
							state.turns - 1, 
							state.t, 
							state.c1, 
							state.c2 + 1));
					
					addLink.e(0, new State(
							state.hand, 
							state.turns, 
							state.t, 
							state.c1, 
							state.c2 + 1));
				}
			}
			
			addLink.e(c0sum.e(state.turns, state.hand), new State(0, 0, 0, 0, 0));
		});
		Node<State> startNode = graph.build(new State(hand));
		return (int)graph.longestPath(startNode);
	}
	
	private static F2<Integer, Integer, Integer> c0sum(List<Card> c0s) {
		Cols.sort(c0s, (c) -> -c.s);
		return (obj, hand) -> {
			int count = obj;
			int sum = 0;
			for (int i = 0; i < c0s.size() && count > 0; i++) {
				Card card = c0s.get(i);
				if (card.index < hand) {
					sum += card.s;
					count --;
				}
			}
			return sum;
		};
	}

	static class State {
		public State(int hand) {
			this.hand = hand;
			turns = 1;
			t = 0;
			c1 = 0;
			c2 = 0;
		}
		public State(int hand, int turns, int t, int c1, int c2) {
			this.hand = hand;
			this.turns = turns;
			this.t = t;
			this.c1 = c1;
			this.c2 = c2;
		}

		int hand;
		int turns;
		int t;
		int c1;
		int c2;
		
		@Override
		public String toString() {
			return "State [hand=" + hand + ", turns=" + turns + ", t=" + t
					+ ", c1=" + c1 + ", c2=" + c2 + "]";
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + c1;
			result = prime * result + c2;
			result = prime * result + hand;
			result = prime * result + t;
			result = prime * result + turns;
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			State other = (State) obj;
			if (c1 != other.c1)
				return false;
			if (c2 != other.c2)
				return false;
			if (hand != other.hand)
				return false;
			if (t != other.t)
				return false;
			if (turns != other.turns)
				return false;
			return true;
		}
	}
	
	static Card toCard(String string) {
		ArrayList<Integer> ints = CodeJam.toInts(string);
		return new Card(
				ints.get(0),
				ints.get(1),
				ints.get(2)
				);
	}
	
	static class Card {
		int index;
		int c;
		int s;
		int turns;
		public Card(int c, int s, int t) {
			this.c = c;
			this.s = s;
			this.turns = t;
		}
	}
}
