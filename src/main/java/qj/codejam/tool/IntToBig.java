package qj.codejam.tool;

import java.awt.Dimension;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JDialog;

import qj.ui.DesktopUI4;
import qj.util.Cols;
import qj.util.RegexUtil;
import qj.util.SwingUtil;
import qj.util.funct.Douce;
import qj.util.math.Range;
import qj.util.swing.SwingLists;
import qj.util.swing.SwingLists.ListItem;


public class IntToBig {
	String from = "int";
	String to = "BigInteger";
	
//	String from = "double";
//	String to = "BigDecimal";
	
	public static void main(String[] args) {
//		String source = null;
		String source = DesktopUI4.prompt2();
		
		IntToBig intToBig = new IntToBig();
		List<Douce<String, Range>> vars = intToBig.listIntVars(source);
		
		JDialog dialog = SwingUtil.dialogStrong("Choose variable", null);
		dialog.setPreferredSize(new Dimension(300, 400));
		dialog.add(SwingLists.createList(Cols.yield(vars, (v) -> {
			return new ListItem(v.get1(), () -> {
				dialog.dispose();
				Range range = v.get2();
				System.out.println(v.get1() + "::" + range);
				
				DesktopUI4.alert2(source.substring(0, range.getFrom()) +
						intToBig.replace(v.get1(), source.substring(range.getFrom(), range.getTo())) + 
						source.substring(range.getTo()));
			});
		})));
		
		SwingUtil.show(dialog);
	}
	
	String toBI(String old) {
		if (old.equals("0")) {
			return to + ".ZERO";
		}
		if (old.equals("1")) {
			return to + ".ONE";
		}
		return to + ".valueOf(" + old + ")";
	}
	
	static String toBICal(String old) {
		if (old.equals("+")) {
			return "add";
		}
		if (old.equals("-")) {
			return "subtract";
		}
		if (old.equals("*")) {
			return "multiply";
		}
		if (old.equals("/")) {
			return "divide";
		}
		throw new RuntimeException(old);
	}
	
	String replace(String var, String sourcePiece) {
		// Replace declaration
		sourcePiece = RegexUtil.replaceAll(sourcePiece, from + " " + var + " = (\\d+);", (m)-> {
			return to + " " + var + " = " + toBI(m.group(1)) + ";";
		});
		
		sourcePiece = sourcePiece.replaceFirst("\\b" + from + " " + var + "\\b", to + " " + var + "");
		
		// Replace quick assignments
		sourcePiece = RegexUtil.replaceAll(sourcePiece, var + " ([-+*/])= (\\d+);", (m)-> {
			return var + " = " + var + "." + toBICal(m.group(1)) + "(" + toBI(m.group(2)) + ");";
		});
		
		// Replace inline expression
		sourcePiece = RegexUtil.replaceAll(sourcePiece, var + " ([-+*/]) (\\w+)", (m)-> {
			return var + "." + toBICal(m.group(1)) + "(" + toBI(m.group(2)) + ")";
		});
		sourcePiece = RegexUtil.replaceAll(sourcePiece, "(\\w+) ([-+*/]) " + var, (m)-> {
			return toBI(m.group(1)) + "." + toBICal(m.group(2)) + "(" + var + ")";
		});
		
		// Replace misc
		sourcePiece = sourcePiece.replaceAll(to + ".valueOf\\(" + var + "\\)", var);
		
		
		return sourcePiece;
	}

	private List<Douce<String,Range>> listIntVars(String source) {
		
		LinkedList<Douce<String, Range>> ret = new LinkedList<>();
		RegexUtil.each("(\\t+)" + from + " \\b(\\w+)\\b", source, (m)-> {
			String var = m.group(2);
			int tabCount = m.group(1).length();
			Matcher matcher = Pattern.compile("\\t{0," + (tabCount-1) + "}\\}").matcher(source);
			matcher.find(m.end());
			Douce<String, Range> douce = new Douce<>(var, new Range(m.end(1), matcher.start()));
			ret.add(douce);
		});
		return ret;
	}
}
