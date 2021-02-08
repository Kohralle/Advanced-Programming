package Assignment1;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Body {

	private static final int MAX_LENGTH_SET = 10;
	private static final char SET_OPEN = '{';
	private static final char SET_CLOSE = '}';
	private static final String OPEN_ERROR = "Missing " + SET_OPEN + "\n";
	private static final String CLOSE_ERROR = "Missing " + SET_CLOSE + "\n";

	private PrintStream out;

	public Body() {
		out = new PrintStream(System.out);
	}

	public static void main(String[] args) {
		Body program = new Body();
		program.start();
	}

	private void start() {

		// delimeter "" is used to detect no input
		Scanner in = new Scanner(System.in).useDelimiter("");

		while (true) {
			Set set1 = askSet("Give the first set: ", in);
			Set set2 = askSet("Give the second set: ", in);

			calculateAndGiveOutput(set1, set2);
		}
	}

	private Set askSet(String question, Scanner input) {
		while (true) {
			try {
				out.print(question);

				if (!input.hasNext()) {
					System.exit(0); // exiting the program
				}

				String stringInput = input.nextLine();

				if (stringInput.isEmpty()) {
					continue;
				}

				Scanner inputScanner = new Scanner(stringInput).useDelimiter("");

				return createSet(inputScanner);

			} catch (Exception exception) {
				out.println("Error: " + exception.getMessage());
			}
		}
	}

	private Set createSet(Scanner input) throws Exception {
		omitSpaces(input);

		if (!nextCharIs(input, SET_OPEN)) {
			throw new Exception(OPEN_ERROR);
		}
		nextChar(input);
		Set set = new Set();

		while (!nextCharIs(input, SET_CLOSE)) {
			omitSpaces(input);

			if (!input.hasNext()) {
				throw new Exception(CLOSE_ERROR);
			}
			if (!nextCharIsLetter(input)) {
				throw new Exception("Please start elements with an alphabetic character only\n");
			}
			Identifier identifier = new Identifier(nextChar(input));

			while (nextCharIsLetter(input) || nextCharIsDigit(input)) {
				identifier.addChar(nextChar(input));
			}
			set.addElement(identifier);

			if (set.getSize() > MAX_LENGTH_SET) {
				throw new Exception("The input may only contain up to " + MAX_LENGTH_SET + " identifiers \n");
			}
			omitSpaces(input);
		}
		if (!nextCharIs(input, SET_CLOSE)) {
			throw new Exception(CLOSE_ERROR);
		}

		nextChar(input);

		if (input.hasNext()) {
			throw new Exception("Input instead of eoln \n");
		}
		return set;
	}

	private boolean nextCharIs(Scanner in, char c) {
		return in.hasNext(Pattern.quote(c + ""));

	}

	private char nextChar(Scanner in) {
		return in.next().charAt(0);
	}

	private boolean nextCharIsLetter(Scanner in) {
		return in.hasNext("[a-zA-Z]");
	}

	private boolean nextCharIsDigit(Scanner in) {
		return in.hasNext("[0-9]");
	}

	private void omitSpaces(Scanner in) {
		while (in.hasNext(" ")) {
			nextChar(in);
		}
	}

	private void calculateAndGiveOutput(Set set1, Set set2) {
		try {
			out.println("difference = {" + set1.difference(set2).toString() + "}");
			out.println("intersection = {" + set1.intersection(set2).toString() + "}");
			out.println("union = {" + set1.union(set2).toString() + "}");
			out.println("sym. diff. = {" + set1.symmetric_difference(set2).toString() + "}");
		} catch (Exception exception) {
			out.println("Error: " + exception.getMessage());
		}
	}

}