package spoon;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import spoon.Launcher;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.declaration.CtField;
import spoon.reflect.visitor.filter.TypeFilter;

public class SpoonStarter {

	public static void main(String[] args) {

		// List taken to add all queries found in instance, local and global scope
		ArrayList<String> query = new ArrayList<String>();

		Launcher launcher = new Launcher();
		// launcher.addInputResource("C:\\SPOON_Workspace\\spoon\\src\\main\\java\\jdbc\\StaticCodeForJDBC.java");
		launcher.addInputResource("C:\\Users\\CSS\\eclipse-workspace\\demoParser\\src\\main\\java\\spoon\\DynamicCodeForJDBC.java");
		launcher.buildModel();
		CtModel model = launcher.getModel();

		// Fetching instance scope query
		for (CtField<?> ctField : model.getElements(new TypeFilter<>(CtField.class))) {
			CtExpression<?> defaultExpression = ctField.getDefaultExpression();

			if ((defaultExpression.toString().toLowerCase().contains("select"))
					&& (defaultExpression.toString().toLowerCase().contains("from"))
					|| (defaultExpression.toString().toLowerCase().contains("select"))
					|| (defaultExpression.toString().toLowerCase().contains("from"))) {
//				System.out.println("Key ----- " + ctField.getSimpleName());
//				System.out.println("Value ----- " + defaultExpression);
				Matcher m = Pattern.compile("\\((.*?)\\)").matcher(defaultExpression.toString());
				if (m.find()) {
					query.add(m.group(1));
				} else {
					query.add(defaultExpression.toString());
				}

			}
		}
		// Fetching local and global scope query
		for (CtLocalVariable<?> variable : model.getElements(new TypeFilter<>(CtLocalVariable.class))) {
			CtExpression<?> defaultExpression = variable.getDefaultExpression();
			if ((defaultExpression.toString().toLowerCase().contains("select"))
					&& (defaultExpression.toString().toLowerCase().contains("from"))
					|| (defaultExpression.toString().toLowerCase().contains("select"))
					|| (defaultExpression.toString().toLowerCase().contains("from"))) {
//				System.out.println("Key ----- " + variable.getSimpleName());
//				System.out.println("Value ----- " + defaultExpression);
				Matcher m = Pattern.compile("\\((.*?)\\)").matcher(defaultExpression.toString());
				/*
				 * Query can be in 2 ways : 1. ResultSet rs =
				 * stmt.executeQuery("SELECT id,first,last FROM Employees"); or 2. static final
				 * String STATIC_QUERY = "SELECT id,first,last FROM Employees"; 2.a ResultSet rs
				 * = stmt.executeQuery(STATIC_QUERY);
				 */
				if (m.find()) {
					query.add(m.group(1).toString());
				} else {
					query.add(defaultExpression.toString());
				}

			}
		}

		// Printing list as string
		String[] str = new String[query.size()];
		for (int i = 0; i < query.size(); i++) {
			str[i] = query.get(i).replaceAll("^\"|\"$", "");
		}

		// For printing Static and Dynamic query
		String finalq = "";
		if (query.size() == 1) {
			finalq = concatQuery(str, "select"); // For static query
		} else {
			finalq = concatQuery(str, "select") + " " + concatQuery(str, "from"); // For Dynamic query
		}

		System.out.println(finalq);

	}

	//Method made to except query word and arrange in order as in  SQL
	public static String concatQuery(String str[], String word) {
		String finalQuery = "";
		for (String k : str) {
			if (k.toLowerCase().contains(word)) {
				finalQuery = finalQuery.concat(k).replaceAll("^\"|\"$", "");
				break;
			}

		}
		return finalQuery;
	}
}