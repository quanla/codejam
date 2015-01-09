package qj.codejam.y2014.qualification.d;

import java.text.DecimalFormat;
import java.util.LinkedList;

import qj.util.Cols;

public class GenTest {

	public static void main(String[] args) {
		for (int i = 0; i < 50; i++) {
			System.out.println(1000);
			System.out.println(line());
			System.out.println(line());
		}
	}

	private static String line() {
		LinkedList<String> list = new LinkedList<>();
		for (int i = 0; i < 1000; i++) {
			list.add(new DecimalFormat("#.#####").format(Math.random()));
		}
		return Cols.join(list, " ");
	}
}
