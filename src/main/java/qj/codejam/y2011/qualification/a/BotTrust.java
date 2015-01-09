package qj.codejam.y2011.qualification.a;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import qj.codejam.CodeJam;
import qj.util.Cols;
import qj.util.funct.F0;
import qj.util.funct.F1;
import qj.util.funct.Fs;

public class BotTrust {
	public static void main(String[] args) {
		String resName = "sample.in";
//		String resName = "test.in";
		
		final Map<String, Integer> robotIndex = Cols.map("O", 0, "B", 1);
		
		CodeJam.solver(resName, new F1<F0<String>, F0<String>>() {public F0<String> e(F0<String> lineF) {
			String[] split = lineF.e().split(" ");
			final LinkedList<Task> sequence = new LinkedList<Task>();
			for (int i = 1; i < split.length; i+=2) {
				String robotName = split[i + 0];
				int button = Integer.parseInt(split[i + 1]);
				
				sequence.add(new Task(robotIndex.get(robotName), button));
			}
			
			return new F0<String>() {public String e() {
				VirtualWorld virtualWorld = new VirtualWorld(2);
				
				int time;
				for (time = 0;!sequence.isEmpty();) {
					virtualWorld.giveDirectives(sequence);
					
					time++;
//					System.out.println("======= Time=" + time + " task=" + sequence.peek());
//					for (Robot robot : virtualWorld.robots) {
//						System.out.println("Robot" + robot.robotIndex + " at " + robot.position
////								+ ", " + (robot.directive != null ? "has" : "doesn't have") + " directive"
//								);
//					}
					
					virtualWorld.timeGoOn();
				}
				
				return "" + time;
			}};
		}})
		.solve();
	}
	
	static class Robot {
		private int position;
		F0<Boolean> directive;
		private int robotIndex;

		public Robot(int robotIndex, int position) {
			this.robotIndex = robotIndex;
			this.position = position;
		}
	}
	
	static class VirtualWorld {
		List<Robot> robots = new ArrayList<Robot>();
		
		public VirtualWorld(int robotCount) {
			for (int i = 0; i < robotCount; i++) {
				robots.add(new Robot(i, 1));
			}
		}

		public void giveDirectives(LinkedList<Task> sequence) {
			
			// Primary task
			Task task = sequence.peek();
			Robot mainRobot = robots.get(task.robot);
			if (mainRobot.directive != null) {
				;
			} else if (mainRobot.position == task.button) {
				mainRobot.directive = pressButton(mainRobot, task.button, sequence);
			} else {
				mainRobot.directive = move(mainRobot, task.button);
			}
			
			for (int i = 0; i < robots.size(); i++) {
				Robot robot = robots.get(i);
				if (robot.directive == null) {
					Task nextTask = nextTask(i, sequence);
					if (nextTask != null) {
						robot.directive = move(robot, nextTask.button);
					} else {
						robot.directive = idle(robot);
					}
				}
			}
			
		}

		private F0<Boolean> idle(final Robot robot) {
			return new F0<Boolean>() {public Boolean e() {
//				System.out.println(".. robot " + robot.robotIndex + " idle at " + robot.position);
				return false;
			}};
		}

		private Task nextTask(int robot, LinkedList<Task> sequence) {
			for (Task task : sequence) {
				if (task.robot == robot) {
					return task;
				}
			}
			return null;
		}

		private F0<Boolean> pressButton(final Robot robot, final int button, final LinkedList<Task> sequence) {
			return new F0<Boolean>() {public Boolean e() {
//				System.out.println("@@ robot " + robot.robotIndex + " pushing button " + button);
				sequence.removeFirst();
				return true;
			}};
		}

		private F0<Boolean> move(final Robot robot, final int button) {
			return new F0<Boolean>() {public Boolean e() {
				if (robot.position < button) {
					robot.position++;
//					System.out.println(">> robot " + robot.robotIndex + " moving to " + robot.position);
				} else if (robot.position > button) {
					robot.position--;
//					System.out.println("<< robot " + robot.robotIndex + " moving to " + robot.position);
				} else {
//					System.out.println(".. robot " + robot.robotIndex + " stays at " + robot.position);
				}
				return robot.position == button;
			}};
		}

		public boolean hasDirective() {
			for (Robot robot : robots) {
				if (robot.directive != null) {
					return true;
				}
			}
			return false;
		}

		public void timeGoOn() {
			for (Robot robot : robots) {
				if (robot.directive != null) {
					if (robot.directive.e()) {
						robot.directive = null;
					}
//				} else {
//					System.out.println("AAAAA robot " + robot.robotIndex + " has no directive");
				}
			}
		}
		
	}
	
	static class Task {
		int robot;
		int button;
		public Task(int robot, int button) {
			this.robot = robot;
			this.button = button;
		}
		@Override
		public String toString() {
			return "[robot=" + robot + ", button=" + button + "]";
		}
	}
}
