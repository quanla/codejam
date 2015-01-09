package qj.codejam.y2014.qualification.c;

import qj.codejam.CodeJam;

public class GenTest {
	public static void main(String[] args) {
		CodeJam.genTest(lineF -> {
			int r = (int) (Math.random() * 50);
			int c = (int) (Math.random() * 50);
			int m = (int) (Math.random() * r*c);
			lineF.e(r + " " + c + " " + m);
		});
	}
}
