package nl.vu.labs.phoenix.ap;


import java.io.PrintStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * A set interpreter for sets of elements of type T
 */
public class Interpreter<T extends SetInterface<BigInteger>> implements InterpreterInterface<T> {

	private char UNION_OPERATOR = '+';
	private char COMPLEMENT_OPERATOR = '-';
	private char SYMMETRIC_OPERATOR = '|';
	private char INTERSECTION_OPERATOR = '*';
	private char PRINT_OPERATOR = '?';
	private char COMMENT_OPERATOR = '/';
	private char ASSIGNMENT_OPERATOR = '=';
	private char SET_OPEN = '{';
	private char SET_CLOSE = '}';
	private char COMPLEX_SET_OPEN = '(';
	private char COMPLEX_SET_CLOSE = ')';
	private char SET_DELIMITER = ',';
	private char SPACE = ' ';

	PrintStream out = new PrintStream(System.out);
	private HashMap<Identifier, T> map = new HashMap<>();

	@Override
	public T getMemory(String v) {
		try {
			Scanner input = new Scanner(v).useDelimiter("");
			Identifier query = identifier(input);

			return map.get(query);
		} catch (APException e) {
			out.println(e.getMessage());
		}
		return null;
	}


//--------------------------------------------------------------------------------------------------
	private T natural_number_term(SetInterface<BigInteger> set, Scanner input) throws APException {
		input.next();
		if (nextCharIsDigit(input)) {
			StringBuffer x = new StringBuffer("");
			while (nextCharIsDigit(input)) {
				omitSpaces(input);
				x.append(input.next());
			}

			if (x.charAt(0) == '0' && x.length() > 1) { //?{02}
				throw new APException("natural numbers cannot start with 0");
			}

			BigInteger num1 = set.get();
			BigInteger num2 = new BigInteger(x.toString());

			if(num2.compareTo(num1) == 1){
				for (BigInteger bi = num1.add(BigInteger.ONE); bi.compareTo(num2) < 0; bi = bi.add(BigInteger.ONE)) {
					set.add(bi);
				}
				set.add(num2);
			}
			else {
				set.init();
			}

		}
		return (T) set;
	}
	private T multiplicative_operator(SetInterface<BigInteger> result, Scanner input) throws APException {
		input.next();
		omitSpaces(input);
		SetInterface<BigInteger> set = factor(input);
		result = result.intersection(set);
		return (T) result;

	}

	private T additive_operator(SetInterface<BigInteger> result, Scanner input) throws APException {
		SetInterface<BigInteger> set;
		omitSpaces(input);

		if (nextCharIs(UNION_OPERATOR, input)) {
			input.next();
			omitSpaces(input);
			set = term(input);
			result = result.union(set);
		}

		else if (nextCharIs(SYMMETRIC_OPERATOR, input)) {
			input.next();
			omitSpaces(input);
			set = term(input);
			result = result.symdiff(set);
		}

		else if (nextCharIs(COMPLEMENT_OPERATOR, input)) {
			input.next();
			omitSpaces(input);
			set = term(input);
			result = result.complement(set);
		}

		else if(nextCharIsAlphanumerical(input)){
			throw new APException("only 1 assignment per command is allowed");
		}

		else {
			//this conversion is only done for error handling
			String s = input.nextLine();
			if (s.charAt(s.length()-1) == ')') {
				throw new APException("missing opening parenthesis '(' ");
			}
			else {

				throw new APException("unrecognized Operator");
			}

		}


		return (T) result;
	}

	private T row_natural_numbers(Scanner input) throws APException {
		SetInterface<BigInteger> set = new Set<BigInteger>();

		while (!nextCharIs(SET_CLOSE, input)) {

			if (nextCharIsDigit(input)) {
				StringBuffer x = new StringBuffer("");
				while (nextCharIsDigit(input)) {
					omitSpaces(input);
					x.append(input.next());
				}

				if (x.charAt(0) == '0' && x.length() > 1) { //?{02}
					throw new APException("natural numbers cannot start with 0");
				}

				set.add(new BigInteger(x.toString()));
			}

			else if(nextCharIs('.', input)){
				input.next();
				if(nextCharIs('.', input)){
					set =natural_number_term(set, input);
				}

				else {
					throw new APException("this is not the range operator");
				}
			}

			else if (nextCharIs(SPACE, input)) {
				omitSpaces(input);

				if (nextCharIs(SET_DELIMITER, input)) {
					input.next();
				}

				else if (nextCharIsDigit(input)) {
					throw new APException("only ',' is allowed as delimiter");
				}
			}

			else if (nextCharIs(SET_DELIMITER, input)) {
				input.next();
				omitSpaces(input);
			}

			else if (!nextCharIs(SET_DELIMITER, input)) {
				throw new APException("only ',' is allowed as delimiter");
			}

			else {
				throw new APException("Missing '}' set close");
			}

		}
		return (T) set;
	}

	private T set(Scanner input) throws APException {
		SetInterface<BigInteger> set = new Set<BigInteger>();
		omitSpaces(input);
		if (!nextCharIs(SET_OPEN, input)) {
			throw new APException("expression is missing");
		}

		else if (nextCharIs(SET_OPEN, input)) {
			input.next();
			omitSpaces(input);

			if (nextCharIsLetter(input)) {
				throw new APException("only natural numbers are allowed in the set");
			}

			else if (nextCharIs('-', input)) {
				throw new APException("natural number cannot be negative");
			}

			else if (nextCharIs('+', input)) {
				throw new APException("natural number cannot be signed");
			}

			else if (!nextCharIsDigit(input)) {
				input.next();
				return (T) set;
			}

			set = row_natural_numbers(input);
		}
		input.next();
		return (T) set;
	}

	private T complex_factor(Scanner input) throws APException {

		if (nextCharIs(COMPLEX_SET_OPEN, input)) {
			input.next();
		}

		else {
			throw new APException("missing opening parenthesis");
		}

		omitSpaces(input);
		SetInterface<BigInteger> result = term(input);

		while (input.hasNext() && !nextCharIs(COMPLEX_SET_CLOSE, input)) {
			result = additive_operator(result, input);
		}

		omitSpaces(input);

		if (nextCharIs(COMPLEX_SET_CLOSE, input)) {
			input.next();
		}

		else {
			throw new APException("missing closing parenthesis ')'");
		}

		return (T) result;

	}

	private T factor(Scanner input) throws APException {
		if (nextCharIsLetter(input)) {
			omitSpaces(input);
			Identifier identifier = identifier(input);

			if (map.get(identifier) == null) {
				throw new APException("variable does not exist");
			}

			else {
				return map.get(identifier);
			}
		}

		else if (nextCharIs(COMPLEX_SET_OPEN, input)) {
			return complex_factor(input);
		}

		else {
			SetInterface<BigInteger> set = set(input);
			return (T) set;
		}

	}

	private T term(Scanner input) throws APException {

		SetInterface<BigInteger> result = factor(input);

		while (!nextCharIs(UNION_OPERATOR, input)) {
			omitSpaces(input);

			if (nextCharIs(INTERSECTION_OPERATOR, input)) {
				result = multiplicative_operator(result, input);
			}

			else {
				break;
			}
		}
		return (T) result;
	}

	private boolean boolean_operator(Scanner input) throws APException {
		boolean result = false;
		omitSpaces(input);
		SetInterface<BigInteger> set1 = term(input);
		omitSpaces(input);

		if (nextCharIs('=', input)){
			input.next();
			omitSpaces(input);
			SetInterface<BigInteger> set2 = term(input);
			omitSpaces(input);
			if (set1ContainsSet2(set1, set2.copy()) && set1ContainsSet2(set2, set1.copy())){
				result = true;
			}
			else {
				result = false;
			}
		}

		else if (nextCharIs('<', input)){

			input.next();
			omitSpaces(input);
			SetInterface<BigInteger> set2 = term(input);
			omitSpaces(input);
			if (set1ContainsSet2(set2, set1.copy())){
				result = true;
			}
			else {
				result = false;
			}
		}

		else if (nextCharIs('>', input)){

			input.next();
			omitSpaces(input);
			SetInterface<BigInteger> set2 = term(input);
			omitSpaces(input);
			if (set1ContainsSet2(set1, set2.copy())){
				result = true;
			}
			else {
				result = false;
			}
		}

		return result;
	}
	private boolean boolean_expression(Scanner input) throws APException {

		if (nextCharIs(COMPLEX_SET_OPEN, input)) {
			input.next();
		}

		else {
			throw new APException("missing opening parenthesis");
		}

		omitSpaces(input);
		boolean result = boolean_operator(input);
		omitSpaces(input);

		if (nextCharIs(COMPLEX_SET_CLOSE, input)) {
			input.next();
			omitSpaces(input);
		}

		else {
			throw new APException("missing closing parenthesis ')'");
		}

		return result;
	}
	private T if_expression(Scanner input) throws APException {
		omitSpaces(input);
		boolean expression = boolean_expression(input);

		omitSpaces(input);
		checkString(input, "then");
		omitSpaces(input);
		SetInterface<BigInteger> set = complex_factor(input);
		omitSpaces(input);
		checkString(input, "else");
		omitSpaces(input);
		SetInterface<BigInteger> set2 = complex_factor(input);
		omitSpaces(input);

		if (expression){
			return (T) set;
		}
		else {
			return (T) set2;
		}

	}
	private T subexpression(Scanner input) throws APException {
		SetInterface<BigInteger> result = new Set<BigInteger>();
		result = term(input);

		while (input.hasNext() && !nextCharIs(SET_CLOSE, input)) {
			result = additive_operator(result, input);
		}

		return (T) result;
	}

	private T expression(Scanner input) throws APException {
		SetInterface<BigInteger> result = new Set<BigInteger>();

		if (nextCharIs('%', input)){

			if (checkString(input, "%if")){
				return if_expression(input);
			}
			else {
				throw new APException("Syntax Error");
			}
		}

		else {
			return subexpression(input);
		}
	}

	private Identifier identifier(Scanner input) throws APException {

		if (!nextCharIsLetter(input)) {
			throw new APException("identifier are not allowed to start with a number");
		}

		Identifier identifier = new Identifier();

		identifier.init(input.next().charAt(0));

		while (nextCharIsAlphanumerical(input)) {
			identifier.addChar(input.next().charAt(0));
		}

		if (nextCharIs(SPACE, input)) {
			omitSpaces(input);
			if (nextCharIsAlphanumerical(input) ){
				throw new APException("no spacings allowed in identifier");
			}

		} else if (nextCharIs('_', input)) {
			input.next();
			omitSpaces(input);

			if (nextCharIsAlphanumerical(input)) {
				throw new APException("Only alphanumerical characters are allowed in identifier");
			}
		}
		return identifier;
	}


	private T comment() {
		return null;
	}

	private T printStatement(Scanner input) throws APException {
		input.next(); //skip '?'
		omitSpaces(input);
		SetInterface<BigInteger> set = expression(input); //this should return a set from operations

		return (T) set;
	}

	private T assignment(Scanner input) throws APException {
		Identifier identifier = identifier(input);
		omitSpaces(input);

		if (nextCharIs(ASSIGNMENT_OPERATOR, input)) {
			input.next();
		}

		else {
			throw new APException("Missing equals sign");
		}

		omitSpaces(input);
		SetInterface<BigInteger> set = expression(input);

		map.put(identifier, (T) set);
		return null;
	}

	private T statement(Scanner input) throws APException {
		omitSpaces(input);

		if(!input.hasNext()){
			throw new APException("no statement");
		}

		else if (nextCharIs(COMMENT_OPERATOR, input)) {
			return comment();
		}

		else if (nextCharIs(PRINT_OPERATOR, input)) {
			return printStatement(input);
		}

		else if (nextCharIsAlphanumerical(input)) {
			return assignment(input);
		}

		else {
			throw new APException("Unrecognized symbol");
		}

	}

	@Override
	public T eval(String s) {
		Scanner input = new Scanner(s).useDelimiter("");
		SetInterface<BigInteger> set;
		try {
			set = statement(input);
			return  (T) set;
		} catch (APException e) {
			out.println(e.getMessage());
		}
		return null;
	}

	//------------------------------HELPER FUNCTIONS-------------------------------------------

	private boolean nextCharIsLetter(Scanner input) {
		return input.hasNext("[a-zA-Z]");
	}

	private boolean nextCharIsDigit(Scanner input) {
		return input.hasNext("[0-9]");
	}

	private boolean nextCharIsAlphanumerical(Scanner input) {
		if (nextCharIsLetter(input) || nextCharIsDigit(input)) {
			return true;
		}
		return false;
	}

	private void omitSpaces(Scanner input) {
		while (input.hasNext(" ")) {
			input.next();
		}
	}

	private boolean nextCharIs(char c, Scanner input) {
		return input.hasNext(Pattern.quote(c + ""));
	}

	private boolean checkString(Scanner input, String expected_string){
		StringBuffer statement = new StringBuffer("");
		for (int i = 0; i < expected_string.length(); i++) {
			statement.append(input.next().charAt(0));
		}
		String s = statement.toString();
		if(s.equals(expected_string)){
			return true;
		}
		return false;
	}

	private boolean set1ContainsSet2(SetInterface<BigInteger> set1, SetInterface<BigInteger> set2){
		int size = set2.copy().size();
		for (int i = 0; i < size; i++) {
			if(set1.contains(set2.get())){
				set2.remove(set2.get());
			}
			if (set2.isEmpty()){
				return true;
			}
		}
		return false;
	}
}