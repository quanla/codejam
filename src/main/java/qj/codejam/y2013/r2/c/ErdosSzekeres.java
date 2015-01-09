package qj.codejam.y2013.r2.c;

import java.util.ArrayList;
import java.util.List;

import qj.codejam.CodeJam;
import qj.util.Cols;
import qj.util.ObjectUtil;
import qj.util.funct.Douce;
import qj.util.funct.F0;
import qj.util.funct.F1;

/**
 * TODO Not done
 * @author Quan
 *
 */
public class ErdosSzekeres {
	public static void main(String[] args) {
//		String resName = "sample.in";
		String resName = "C-small-practice.in";
		CodeJam.solver(resName, new F1<F0<String>, F0<String>>() {
			public F0<String> e(F0<String> lineF) {
				lineF.e();
				ArrayList<Integer> as = CodeJam.toInts(lineF.e());
				ArrayList<Integer> bs = CodeJam.toInts(lineF.e());
				List<Douce<Integer, Integer>> cs = Cols.joinDouce(as, bs);
				return new F0<String>() {
					public String e() {
						return Cols.join(cal(cs), " ");
					}
				};
			}
		}).solve();
	}
	static List<Integer> cal(List<Douce<Integer, Integer>> cs) {
		ArrayList<Card<Integer>> cards = new ArrayList<>(cs.size());
		for (Douce<Integer, Integer> c : cs) {
			Card<Integer> card = new Card<Integer>("" + cards.size());
			cards.add(card);
			int i1= cards.size() - 1;
			
			int left = c.get1();
			int right = c.get2();
			if (left == 1 && right == 1) {
				card.fixValue(1);
			}
			
			card.cond = new F0<Boolean>() {
				public Boolean e() {
					// This value is unique
					Integer thisVal = card.val();
					if (thisVal != null) {
						if (thisVal<left || thisVal <right) {
							return false;
						}
						
						for (Card<Integer> card2 : cards) {
							if (card2 != card && ObjectUtil.equals(card2.val(), thisVal)) {
								return false;
							}
						}
					}
					
					
					// Check left & right
					if (left < Gen.maxSeqLeft(i1, Cols.randomAccessCol(cards, Card.valF()))) {
						return false;
					}
					if (right < Gen.maxSeqRight(i1, Cols.randomAccessCol(cards, Card.valF()))) {
						return false;
					}
					return true;
				}
			};
		}
		CardGame<Integer> cardGame = new CardGame<Integer>();
		
		cardGame.nextCardValue = oldValue -> {
			if (oldValue == null) {
				return 1;
			}
			if (oldValue < cs.size()) {
				return oldValue + 1;
			}
			return null;
		};
		
		return cardGame.solve(cards);
	}
}
