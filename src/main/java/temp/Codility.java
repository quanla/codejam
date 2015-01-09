package temp;

import java.math.BigInteger;

/**
 * http://codility.com/demo/take-sample-test/
 * @author Quan
 *
 */
public class Codility {
	 public int solution(int[] A) {
        // write your code in Java SE 8
		BigInteger right = total(A);
		BigInteger left = BigInteger.ZERO;
		
		for (int i = 0; i < A.length; i++) {
			right = right.subtract(BigInteger.valueOf(A[i]));
			
			if (left.equals(right)) {
				return i;
			}
			
			left = left.add(BigInteger.valueOf(A[i]));
			
		}
		return -1;
    }

	private BigInteger total(int[] a) {
		
		BigInteger total = BigInteger.ZERO;
		for (int i : a) {
			total = total.add(BigInteger.valueOf(i));
		}
		return total;
	}
}
