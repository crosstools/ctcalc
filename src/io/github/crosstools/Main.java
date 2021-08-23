package io.github.crosstools;

import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

	static void run(String text) throws Throwable {
		Parser parser = new Parser(text);
		Node tree = parser.parse();

		if (tree != null) {
			Interpreter interpreter = new Interpreter();
			Values.Number result = interpreter.visit(tree);
			System.out.println(result.value());
		}

	}

	static void help() {
		System.out.println("usage: ctcalc [-h | --help] [file?...]");
	}

    public static void main(String[] args) throws Throwable {
		switch (args.length) {
			case 0 -> {
				Scanner scanner = new Scanner(System.in);
				String line;

				System.out.println("ctcalc");

				System.out.print(">>> ");
				System.out.flush();

				while (scanner.hasNextLine()) {
					line = scanner.nextLine();
					if (line.trim().equals("exit") || line.trim().equals("exit()")) {
						break;
					}
					try {
						run(line);
					} catch (RuntimeException e) {
						System.err.println(e.getMessage());
						System.err.flush();
					} finally {
						TimeUnit.MILLISECONDS.sleep(100); // print error before print ">>> "
						System.out.print(">>> ");
						System.out.flush();
					}
				}

				scanner.close();
			}
			case 1 -> {
				if (Objects.equals(args[0], "-h") || Objects.equals(args[0], "--help")) {
					help();
				} else {
					System.out.println("Unimplemented");
				}
			}
			default -> {
				help();
			}
		}
    }
}
