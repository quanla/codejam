package qj.codejam.tool;

import java.math.BigInteger;

public class TestSource {
	public static void main(String[] args) {
		BigInteger a = BigInteger.valueOf(2);
		
		BigInteger _3 = BigInteger.valueOf(3);
		a = a.add(_3);
		
		BigInteger d = a.add(_3);
		d = a.subtract(_3);
		d = a.multiply(_3);
		d = a.divide(_3);
		d = _3.add(a);
		d = _3.subtract(a);
		d = _3.multiply(a);
		d = _3.divide(a);
		
		BigInteger c = BigInteger.ZERO;
		c = c.add(a);
	}
}
