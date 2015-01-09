package qj.codejam.y2013.r2.c;

import java.util.ArrayList;
import java.util.List;

import qj.util.Cols;
import qj.util.funct.F1;

public class CardGame<V> {

	public F1<V,V> nextCardValue;

	public List<V> solve(ArrayList<Card<V>> cards) {
		
		for (Card<V> card : cards) {
			if (card.val() == null) {
				for (;;) {
					V attemptValue = nextCardValue.e(card.tempV);
					card.tempV = attemptValue;
					if (attemptValue==null) {
//						System.out.println("Card " + card.label + " ");
						// That's it, this rout is blocked at this depth
						return null;
					}
//					System.out.println("Card " + card.label + " attempt val " + attemptValue);
					
					if (allGood(card, cards)){// ok
						// Try other cards
						List<V> solve = solve(cards);
						if (solve != null) {
							return solve;
						} else {
//							System.out.println("Card " + card.label + " attempt " + attemptValue + " is wrong in deeper level");
							// Route blocked somewhere, try other value
							continue;
						}
					} else {// Some cond is wrong, check other value
//						System.out.println("Card " + card.label + " attempt val " + attemptValue + " is wrong immediately");
						
//						if (card.label.equals("1") && card.val().equals(5)) {
//							System.out.println(cards);
//						}
						continue;
					}
				}
			}
		}
		// All done
		return Cols.yield(cards, Card.<V>valF());
	}
	
	static <V> boolean allGood(Card<V> card, ArrayList<Card<V>> cards) {
		if (!card.cond.e()) {
			return false;
		}
		
		for (Card<V> card1 : cards) {
			if (card1 == card) {
				continue;
			}
			if (!card1.cond.e()) {
				return false;
			}
		}
		return true;
	}
}
