package nl.vu.labs.phoenix.ap;

import java.math.BigInteger;
import java.util.Scanner;

public class Main {

	private void start() {
		InterpreterInterface<Set<BigInteger>> interpreter = new Interpreter<Set<BigInteger>>();
		while (true){
			Scanner in = new Scanner(System.in);
			String line = in.nextLine();

			SetInterface<BigInteger> printSet = interpreter.eval(line);

			if(printSet != null) {
				System.out.print("Printed set below \n");
				PrintSet(printSet);
			}
		}
		//1. Create a scanner on System.in
		//2. call interpreter.eval() on each line
	}

	private void PrintSet(SetInterface<BigInteger> printSet){
		SetInterface<BigInteger> new_set = printSet.copy();

		for (int i = 0; i < printSet.size(); i++) {
			BigInteger x = new_set.get();
			System.out.print(x + " ");
			new_set.remove(x);
		}

		System.out.println();

	}



	public static void main(String[] args)  {
		new Main().start();
	}
}
