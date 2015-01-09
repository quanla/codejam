package qj.codejam.practice.b;

import java.util.Arrays;
import java.util.LinkedList;

import qj.codejam.CodeJam;
import qj.util.Cols;
import qj.util.LangUtil;
import qj.util.math.Point;

public class AlwaysTurnLeft {
	public static void main(String[] args) {
//		String resName = "sample.in";
//		String resName = "B-small-practice.in";
		String resName = "B-large-practice.in";
		CodeJam.solver(resName, lineF -> {
			String[] split = lineF.e().split(" ");
			String entranceToExit = split[0];
			String exitToEntrance = split[1];
			
			return () -> {
				Maze<Room> maze = new Maze<Room>();
				maze.createElem = Room::new;
				
				Pos currentPos = entranceToExit(entranceToExit, maze);
				exitToEntrance(exitToEntrance, currentPos, maze);
				return translate(maze);
			};
		})
		.solve();
	}
	private static Pos entranceToExit(String entranceToExit, Maze<Room> maze) {
		LinkedList<Character> steps1 = toLinkedList(entranceToExit);
		steps1.removeFirst(); // Always W
		Pos currentPos = new Pos(new Point(0, 0));
		
		maze.getf(currentPos.loc).setWall(currentPos.getBehind(), false);
		buildMaze(steps1, currentPos, maze);
		return currentPos;
	}
	
	private static void buildMaze(LinkedList<Character> steps, Pos pos,
			Maze<Room> maze) {
		for (Character walk : steps) {
			Room room = maze.getf(pos.loc);
			if (walk == 'L') {
				room.setWall(pos.getLeft(), false);
				pos.turnLeft();
			} else if (walk == 'W') {
				if (!pos.justTurned) {
					room.setWall(pos.getLeft(), true);
				}
				room.setWall(pos.facing, false);
				pos.forward();
			} else if (walk == 'R') {
				room.setWall(pos.getLeft(), true);
				room.setWall(pos.facing, true);
				pos.turnRight();
			} else {
				; // ??
			}
		}
		
	}

	static class Pos {
		public boolean justTurned = false;
		public Point loc;
		/**
		 * 0 up
		 * 1 right
		 * 2 down
		 * 3 left
		 */
		int facing = 2;
		Pos(Point loc) {
			this.loc = loc;
		}
		public int getBehind() {
			return face(facing+2);
		}
		public void turnLeft() {
			facing = getLeft();
			justTurned = true;
		}
		public void turnRight() {
			facing = getRight();
			justTurned = true;
		}
		private int face(int face) {
			return (face + 4) % 4;
		}
		public int getLeft() {
			return face(facing-1);
		}
		public int getRight() {
			return face(facing+1);
		}
		public void forward() {
			justTurned = false;
			switch (facing) {
				case 0:
					loc.y--;
					break;
				case 1:
					loc.x++;
					break;
				case 2:
					loc.y++;
					break;
				case 3:
					loc.x--;
					break;
			}
		}
		public void reverse() {
			facing = getBehind();
			justTurned = true;
		}
	}

	private static LinkedList<Character> toLinkedList(String entranceToExit) {
		return new LinkedList<>(Arrays.asList(LangUtil.toObjArr(entranceToExit.toCharArray())));
	}
	private static void exitToEntrance(String exitToEntrance, Pos currentPos, Maze<Room> maze) {
		currentPos.reverse();
		currentPos.forward();

		maze.getf(currentPos.loc).setWall(currentPos.getBehind(), false);
		LinkedList<Character> steps2 = toLinkedList(exitToEntrance);
		steps2.removeFirst(); // Always W
		buildMaze(steps2, currentPos, maze);
	}
	
	private static String translate(Maze<Room> maze) {
		
		StringBuilder sb = new StringBuilder();
		maze.eachLine((line) -> {
			sb.append("\n");
			sb.append( Cols.join(Cols.yield(line, AlwaysTurnLeft::translate), "") );
		});
		return sb.toString();
	}
	
	static String translate(Room room) {
		for (int i = 0; i < room.walls.length; i++) {
			if (room.walls[i] == null) {
				throw new RuntimeException("No info of wall " + i + " in room " + room.loc);
			}
		}
		
		if (room.walls[1]) {
			if (room.walls[3]) {
				if (room.walls[2]) {
					if (room.walls[0]) {
						throw new RuntimeException();
					}
					return room.walls[0] ? "?" : "1";
				} else {
					return room.walls[0] ? "2" : "3";
				}
			} else {
				if (room.walls[2]) {
					return room.walls[0] ? "4" : "5";
				} else {
					return room.walls[0] ? "6" : "7";
				}
			}
		} else {
			if (room.walls[3]) {
				if (room.walls[2]) {
					return room.walls[0] ? "8" : "9";
				} else {
					return room.walls[0] ? "a" : "b";
				}
			} else {
				if (room.walls[2]) {
					return room.walls[0] ? "c" : "d";
				} else {
					return room.walls[0] ? "e" : "f";
				}
			}
		}
	}
	
	static class Room {
		Boolean[] walls = new Boolean[4];
		private Point loc;
		public Room(Point loc) {
			this.loc = loc;
		}
		
		public void setWall(int at, boolean wall) {
			Boolean current = walls[at];
			if (current != null && current != wall) {
				throw new RuntimeException("Room " + loc + " already has wall " + at + " " + current + " now setting " + wall);
			}
			walls[at] = wall;
		}
	}
}
