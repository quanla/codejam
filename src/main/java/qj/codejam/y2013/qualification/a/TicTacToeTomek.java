package qj.codejam.y2013.qualification.a;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import qj.codejam.CodeJam;
import qj.util.funct.F0;
import qj.util.funct.F1;
import qj.util.funct.P1;

public class TicTacToeTomek {
	public static void main(String[] args) {
		CodeJam.solver("sample.in", new F1<F0<String>, F0<String>>() {public F0<String> e(F0<String> lineF) {
			final Board board = new Board();
			for (int i = 0; i < 4; i++) {
				String row = lineF.e();
				
				board.addRow(row);
			};
			lineF.e();
			return new F0<String>() {public String e() {
				return board.getStateStr();
			}};
		}})
		.solve();
	}
	
	static class Board {
		ArrayList<List<Character>> rows = new ArrayList<List<Character>>();

		public void addRow(String rowStr) {
			ArrayList<Character> row = new ArrayList<Character>();
			for (int j = 0; j < 4; j++) {
				row.add(rowStr.charAt(j));
			}
			rows.add(row);
		}

		public String getStateStr() {
			final String[] winner = {null};
			each4cells(new P1<List<Character>>() {public void e(List<Character> row) {
				if (row.indexOf('.') > -1) {
					return;
				}
				
				if (row.indexOf('X') == -1) {
					winner[0] = "O";
				} else if (row.indexOf('O') == -1) {
					winner[0] = "X";
				} else {
					;
				}
			}});
			if (winner[0] != null) {
				return winner[0] + " won";
			}
			
			return isFull() ? "Draw" : "Game has not completed";
		}

		private boolean isFull() {
			for (List<Character> row : rows) {
				for (Character character : row) {
					if (character.equals('.')) {
						return false;
					}
				}
			}
			return true;
		}

		private void each4cells(P1<List<Character>> p1) {
			// hor cells
			for (List<Character> row : rows) {
				p1.e(row);
			}
			
			// ver cells
			for (int x = 0; x < 4; x++) {
				LinkedList<Character> verLine = new LinkedList<Character>();
				for (int y = 0; y < 4; y++) {
					verLine.add(getChar(x, y));
				}
				p1.e(verLine);
			}
			
			// 2 dias
			
			LinkedList<Character> dias1 = new LinkedList<Character>();
			LinkedList<Character> dias2 = new LinkedList<Character>();
			for (int i = 0; i < 4; i++) {
				dias1.add(getChar(i, i));
				dias2.add(getChar(i, 3 - i));
			}
			p1.e(dias1);
			p1.e(dias2);
		}

		public Character getChar(int x, int y) {
			return rows.get(y).get(x);
		}
		
	}
}
