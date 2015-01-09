package qj.codejam.y2013.r2.c;

import qj.util.funct.F0;
import qj.util.funct.F1;

public class Card<V> {
	V fixedV;
	V tempV;
	
	String label;

	public Card(String label) {
		this.label = label;
	}

	public F0<Boolean> cond;

	public static <V> F1<Card<V>,V> valF() {
		return (c) -> c.val();
	}

	public V val() {
		return fixedV != null ? fixedV : tempV;
	}

	public void fixValue(V v) {
		fixedV = v;
	}

	@Override
	public String toString() {
		return "Card " + label + "=" + val();
	}
}
