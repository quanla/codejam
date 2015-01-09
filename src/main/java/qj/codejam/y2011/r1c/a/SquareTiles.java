package qj.codejam.y2011.r1c.a;

import java.math.BigInteger;

import qj.codejam.CodeJam;
import qj.util.ArrayUtil;
import qj.util.Cols;
import qj.util.funct.F0;

public class SquareTiles {
	public static void main(String[] args) {
//		String resName = "sample.in";
//		String resName = "A-small-practice.in";
		String resName = "A-large-practice.in";
		CodeJam.solver(resName, lineF -> {
			int rowsCount = Integer.parseInt(lineF.e().split(" ")[0]);
			char[][] rows = new char[rowsCount][];
			for (int i = 0; i < rowsCount; i++) {
				rows[i] = lineF.e().toCharArray();
			}
			
			return new F0<String>() {
				public String e() {
					boolean success = cal(rows);
					if (!success) {
						return "\nImpossible";
					}
					return "\n" + display(rows);
				}
			};
		}).solve();
	}
	static String display(char[][] rows) {
		return Cols.join(Cols.yield(rows, (row) -> ArrayUtil.join(row, "")), "\n");
	}
	static boolean cal(char[][] rows) {
		
		
		for (int y = 0; y < rows.length; y++) {
			char[] row = rows[y];
			for (int x = 0; x < row.length; x++) {
				
				char c = row[x];
				if (c == '#') {
					if (y == rows.length -1 || 
							x == row.length - 1) {
						return false;
					}
					if (
							row[x+1] != '#' ||
							rows[y+1][x] != '#' ||
							rows[y+1][x+1] != '#'
							) {
						return false;
					}
						
					row[x] = '/';
					row[x+1] = '\\';
					rows[y+1][x] = '\\';
					rows[y+1][x+1] = '/';
				}
				
			}
		}
		return true;
	}
}
