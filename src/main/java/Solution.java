import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

// you can also use imports, for example:
// import java.util.*;

// you can use System.out.println for debugging purposes, e.g.
// System.out.println("this is a debug message");

class Solution {
	static int length = 30;
	static ArrayList<Integer> powList = new ArrayList<>(length);
	// [1, -2, 4, -8, 16, -32, 64]

	static {
		int pow = 1;
		powList.add(pow);
		for (int i = 0; i < length; i++) {
			pow *= 2;
			powList.add(pow);
		}
//		System.out.println(powList);
	}
	
	public static void main(String[] args) {
		Solution solution = new Solution();
		for (int i = -100; i < 100; i++) {
			System.out.println(i + ": " + sum(solution.solution(i)));
		}
	}
	
    private static int sum(int[] B) {
		int sum = 0;
		for (int i = 0; i < B.length; i++) {
			sum+= B[i] * Math.pow(-2, i);
		}
		return sum;
	}

	public int[] solution(int M) {
    	
    	LinkedList<Integer> ret = new LinkedList<>();
    	
    	for (;;) {
    		int need = need(M);
    		
    		int index = index(need);
    		if ((need >= 0 && index % 2 ==0) || (need < 0 && index % 2 ==1)) {
    			ret.add(index);
    		} else {
    			ret.add(index);
    			ret.add(index + 1);
    		}
    		
    		M -= need;
    		
    		if (M == 0) {
    			break;
    		}
    	}
    	
		return toInts(ret);
    }
    
    private int index(int need) {
		return Collections.binarySearch(powList, Math.abs(need));
	}

	private int need(int m) {
		int index = Collections.binarySearch(powList, Math.abs(m));
		if (index < 0) {
			index = -index -1;
		}
		return powList.get(index) * (m >= 0 ? 1 : -1);
	}
	/**
     * 42 -> 32 + 10
     * 32 = -32 + 64
     * 10 = 16 + -6
     * -6 = 
     */
    /**
     * -42 = -32 + -10
     * -10 = -8 + -2
     */

	private int[] toInts(LinkedList<Integer> list) {
		int length = max(list);
		
		int[] ret = new int[length + 1];
		
		for (Integer index : list) {
			ret[index] = 1;
		}
		
		return ret;
	}
	private int max(LinkedList<Integer> list) {
		int max = -1;
		for (Integer integer : list) {
			if (integer > max) {
				max = integer;
			}
		}
		return max;
	}
    
}
