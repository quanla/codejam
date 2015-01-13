package qj.codejam;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import qj.util.Clipboard;
import qj.util.FileUtil;
import qj.util.IOUtil;
import qj.util.LangUtil;
import qj.util.LangUtil4;
import qj.util.LanguageUtil;
import qj.util.funct.Douce;
import qj.util.funct.F0;
import qj.util.funct.F1;
import qj.util.funct.F2;
import qj.util.funct.P1;
import qj.util.funct.RetCache;

public class CodeJam {

	public static Solver solver2(String resName, F1<F0<String>,Douce<Integer,F1<F0<String>, F0<String>>>> preReadReadAndSolve) {
		Class mainClass = LangUtil4.getTraceClass(1);
		Solver solver = new Solver();
		solver.preReadReadAndSolve = preReadReadAndSolve;
		solver.mainClass = mainClass;
		solver.resName = resName;
		solver.outputFileName = mainClass.getSimpleName() + "_" + resName.replace(".in", ".out");
		return solver;
	}
	private static Solver solver(Class<?> mainClass, String resName, F1<F0<String>,Douce<Integer,F1<F0<String>, F0<String>>>> preReadReadAndSolve) {
		Solver solver = new Solver();
		solver.preReadReadAndSolve = preReadReadAndSolve;
		solver.mainClass = mainClass;
		solver.resName = resName;
		solver.outputFileName = "target/" + mainClass.getSimpleName() + "_" + resName.replace(".in", ".out");
		return solver;
	}
	public static Solver solver(String resName, F1<F0<String>, F0<String>> readAndSolve) {
		return solver(LangUtil4.getTraceClass(1), resName, normalLines(readAndSolve));
	}
	public static Solver solver(InputStream in, final F1<F0<String>, F0<String>> readAndSolve) {
		Solver solver = new Solver();
		solver.preReadReadAndSolve = normalLines(readAndSolve);
		solver.in = in;
		solver.outputFileName = "codeJam.out";
		return solver;
	}
	public static F1<F0<String>,Douce<Integer,F1<F0<String>, F0<String>>>> normalLines(
			final F1<F0<String>, F0<String>> readAndSolve) {
		return new F1<F0<String>,Douce<Integer,F1<F0<String>, F0<String>>>>() {public Douce<Integer,F1<F0<String>, F0<String>>> e(F0<String> lineF) {
			final int caseCount = Integer.parseInt(lineF.e());

			return new Douce<Integer,F1<F0<String>, F0<String>>>(caseCount, readAndSolve);
		}};
	}
	
	public static class Solver {

		public String resName;
		public Class mainClass;
		public InputStream in;
		public F1<F0<String>,Douce<Integer,F1<F0<String>, F0<String>>>> preReadReadAndSolve;
		public String outputFileName;
		
		public Solver() {
		}

		public void solve(final int runCaseNum) {
			final int[] caseNum = {0};

			eachCase(new F2<List<String>, F0<String>, Boolean>() {public Boolean e(List<String> inputLines, F0<String> solveF) {
				caseNum[0]++;
				if (caseNum[0] != runCaseNum) {
					return false;
				}
				
				// Not returned! This is the case to run
				System.out.println("Input of case #" + runCaseNum + ": ");
				for (String inputLine : inputLines) {
					System.out.println(inputLine);
				}
				System.out.println("Output of case #" + runCaseNum + ": " + solveF.e());
				return true;
			}});
		}
		
		boolean singleCase = false;
		
		public void solve() {
			long start = System.currentTimeMillis();

			// Prepare output
			final FileOutputStream out = FileUtil.fileOutputStream(outputFileName, false);
			final P1<String> output = new P1<String>() {public void e(String obj) {
				try {
					out.write((obj + "\n").getBytes());
					System.out.println(obj);
				} catch (IOException e1) {
					throw new RuntimeException(e1.getMessage(), e1);
				}
			}};

			// Case counter
			final int[] caseNum = {0};
			
			// Start looping
			eachCase(new F2<List<String>,F0<String>,Boolean>() {public Boolean e(List<String> inputLines, F0<String> solveF) {
				caseNum[0]++;
				if (singleCase && caseNum[0]==1) {
					output.e("Case #" + 1 + ":");
				}
				try {
					String result = solveF.e();
					if (singleCase) {
						output.e(result);

					} else {
						output.e("Case #" + caseNum[0] + ":" + (result != null && result.startsWith("\n")? result : " " + result) );
					}
				} catch (Exception e) {
					System.err.println("Found exception with case " + (caseNum[0]) + ":");
					for (String inputLine : inputLines) {
						System.err.println(inputLine);
					}
					e.printStackTrace();
					return true;
				}

				return false;
			}});
			
			// Finishing
			IOUtil.close(out);
			String path = new File(outputFileName).getAbsolutePath();
			Clipboard.copy(path);
			System.out.println("Done in " + LanguageUtil.translateMillis(System.currentTimeMillis() - start));
			System.out.println("Saved output file to (copied): \n" + path);
		}

		public void eachCase(F2<List<String>, F0<String>, Boolean> f2) {
			if (resName!=null) {
				System.out.println("Reading input: " + resName);
			}
			final BufferedReader br = new BufferedReader(new InputStreamReader(in != null ? in : mainClass.getResourceAsStream(resName)));
			
			F0<String> lineF = new F0<String>() {public String e() {
				try {
					return br.readLine();
				} catch (IOException e1) {
					throw new RuntimeException(e1.getMessage(), e1);
				}
			}};
			try {

				Douce<Integer, F1<F0<String>, F0<String>>> douce = preReadReadAndSolve.e(lineF);
				F1<F0<String>, F0<String>> readAndSolve = douce.get2();
				
				for (int i=0;i<douce.get1();i++) {
					RetCache<String> cachedLineF = new RetCache<String>(lineF);
					F0<String> caseSolver = readAndSolve.e(cachedLineF);
					if (caseSolver == null || f2.e(cachedLineF.cache, caseSolver)) {
						break;
					}
				}
			} finally {
				IOUtil.close(in);
			}
		}

		public Solver singleCase() {
			singleCase = true;
			return this;
		}
		
	}

	public static ArrayList<String> getLines(String resName) {
		return new ArrayList<String>(Arrays.asList(IOUtil.toString(LangUtil.getTraceClass(1).getResourceAsStream(resName)).split("\r?\n")));
	}
	public static int[] toIntArray(String[] split) {
		int[] ret = new int[split.length];
		for (int i = 0; i < split.length; i++) {
			ret[i] = Integer.valueOf(split[i]);
		}
		return ret;
	}
	public static ArrayList<Integer> toInts(String[] split) {
		final ArrayList<Integer> queue = new ArrayList<Integer>(split.length);
		for (String string : split) {
			queue.add(Integer.valueOf(string));
		}
		return queue;
	}
	public static ArrayList<Double> toDoubles(String str) {
		String[] split = str.split(" ");
		final ArrayList<Double> queue = new ArrayList<Double>(split.length);
		for (String string : split) {
			queue.add(Double.valueOf(string));
		}
		return queue;
	}
	public static int[] toIntArray(String str) {
		String[] split = str.split(" +");
		return toIntArray(split);
	}
	public static ArrayList<Integer> toInts(String str) {
		String[] split = str.split(" +");
		return toInts(split);
	}
	public static ArrayList<ArrayList<Integer>> toInts(F0<String> lineF, int count) {
		ArrayList<ArrayList<Integer>> ret = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			ret.add(toInts(lineF.e().split(" ")));
		}
		return ret;
	}
	public static ArrayList<BigInteger> toBigInts(String str) {
		String[] split = str.split(" +");
		return toBigInts(split);
	}
	public static ArrayList<BigInteger> toBigInts(String[] split) {
		final ArrayList<BigInteger> queue = new ArrayList<BigInteger>(split.length);
		for (String string : split) {
			queue.add(new BigInteger(string));
		}
		return queue;
	}
	public static ArrayList<BigDecimal> toBigs(String[] split) {
		final ArrayList<BigDecimal> queue = new ArrayList<BigDecimal>(split.length);
		for (String string : split) {
			queue.add(new BigDecimal(string));
		}
		return queue;
	}
	public static ArrayList<Long> toLongs(String str) {
		String[] split = str.split(" ");
		return toLongs(split);
	}
	public static ArrayList<Long> toLongs(String[] split) {
		final ArrayList<Long> queue = new ArrayList<Long>(split.length);
		for (String string : split) {
//			if (Long.valueOf(string) < 0) {
//				throw new RuntimeException();
//			}
			queue.add(Long.valueOf(string));
		}
		return queue;
	}
	public static void genTest(P1<P1<String>> lineF) {
		File fout = new File("src/main/java/" + LangUtil.getTraceClass(1).getPackage().getName().replaceAll("\\.", "/") + "/test.in");
		OutputStream out = FileUtil.fileOutputStream(fout);
		try {
			out.write("10\n".getBytes());
			
			for (int i = 0; i < 1000; i++) {
				lineF.e(line -> {
					try {
						out.write((line + "\n").getBytes());
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				});
			}
			
			IOUtil.close(out);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}

}
