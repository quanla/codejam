package qj.codejam;

import java.io.File;
import java.util.zip.ZipOutputStream;

import qj.codejam.y2014.r1b.b.NewLotteryGame;
import qj.ui.DesktopUI4;
import qj.util.Clipboard;
import qj.util.Cols;
import qj.util.FileUtil;
import qj.util.IOUtil;
import qj.util.RegexUtil;
import qj.util.StringUtil;
import qj.util.ZipUtil;
import qj.util.funct.P2;

public class BuildSourceZip {
//	public static void main1(String[] args) {
//		System.out.println(Cols.toString(System.getProperties()));
//	}
	
	public static void main(String[] args) {
		ClassAndPackage cap = getMainClass(NewLotteryGame.class);
		
		buildSourceZip(cap);
	}

	private static void buildSourceZip(ClassAndPackage cap) {
		String outFile = "target/" + cap.clazz + "_src.zip";
		ZipOutputStream out = new ZipOutputStream(FileUtil.fileOutputStream(outFile, false));
		P2<byte[], String> zw = ZipUtil.zipWriteBytes(out, "/");
		
		readme(cap, zw);
		
		codejam(zw);
		
		testpackage(cap.packeze, zw);
		
//		quanutilcore(zw);
		
		IOUtil.close(out);
		String path = new File(outFile).getAbsolutePath();
		Clipboard.copy(path);
		System.out.println("Output file is written to (copied):\n" + path);
	}
	
	private static void quanutilcore(final P2<byte[], String> zw) {
		zw.e(FileUtil.readFileToBytes("../quan-util-core/.classpath"), "quan-util-core/.classpath");
		zw.e(FileUtil.readFileToBytes("../quan-util-core/.project"), "quan-util-core/.project");

		FileUtil.eachFile(new File("../quan-util-core/src/main/java"), new P2<File,String>() {public void e(File f, String relPath) {
			zw.e(FileUtil.readFileToBytes(f), Cols.join("/", "quan-util-core/src/main/java", relPath, f.getName()));
		}});
	}
	private static void testpackage(final String packeze, final P2<byte[], String> zw) {
		zw.e(FileUtil.readFileToBytes(".classpath"), "codejam/.classpath");
		zw.e(FileUtil.readFileToBytes(".project"), "codejam/.project");
		FileUtil.eachFile(new File("src/main/java/" + packeze.replaceAll("\\.", "/")), new P2<File,String>() {public void e(File f, String relPath) {
			if (
					(f.getName().endsWith(".in") && !f.getName().equals("sample.in")) ||
					f.getName().endsWith(".txt")
					) {
				return;
			}
			zw.e(FileUtil.readFileToBytes(f), Cols.join("/", "codejam/src/main/java/" + packeze.replaceAll("\\.", "/"), relPath, f.getName()));
		}});
	}
	private static void codejam(P2<byte[], String> zw) {
		zw.e(FileUtil.readFileToBytes("src/main/java/qj/codejam/CodeJam.java"), "codejam/src/main/java/qj/codejam/CodeJam.java");
	}
	public static void readme(ClassAndPackage cap, P2<byte[], String> zw) {
		String readmeContent = StringUtil.simpleTemplateApply(Cols.<String>map_s(
				"jreVersion", System.getProperty("java.version"),
				"sourceFile", "codejam/src/main/java/" + cap.packeze.replaceAll("\\.", "/") + "/" + cap.clazz + ".java"
				), IOUtil.toString(BuildSourceZip.class.getResourceAsStream("README.tmpl")));
		zw.e(
				readmeContent.getBytes(), 
				"README.txt");
	}
	
	private static ClassAndPackage getMainClass(Class<?> clazz) {
		ClassAndPackage cap = new ClassAndPackage();
		cap.clazz = clazz.getSimpleName();
		cap.packeze = clazz.getPackage().getName();
		System.out.println("cap.clazz=" + cap.clazz);
		System.out.println("cap.packeze=" + cap.packeze);
		return cap;
	}
	
	private static ClassAndPackage getMainClass() {
		String str = DesktopUI4.prompt2();
		ClassAndPackage cap = new ClassAndPackage();
		cap.packeze = RegexUtil.getString("^package (.*?);", 1, str);
		cap.clazz = RegexUtil.getString("class (.*?) ", 1, str);
		return cap;
	}

	static class ClassAndPackage {
		String clazz;
		String packeze;
	}
}
