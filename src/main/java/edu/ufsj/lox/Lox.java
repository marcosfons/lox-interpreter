package edu.ufsj.lox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Lox {

    static boolean hadError = false;
    static boolean hadRuntimeError = false;

    private static final Interpreter interpreter = new Interpreter();

    private static void run(String source) {
        final Scanner scanner = new Scanner(source);
        final List<Token> tokens = scanner.scanTokens();
        
        final Parser parser = new Parser(tokens);
        final Expr expression = parser.parse();

        if (hadError) {
            return;
        }

        interpreter.interpret(expression);

        // System.out.println(new ASTPrinter().print(expression));
    }

    private static void runFile(String filepath) throws IOException {
        final byte[] bytes = Files.readAllBytes(Paths.get(filepath));
        run(new String(bytes, Charset.defaultCharset()));

        if (hadError) System.exit(64);
        if (hadRuntimeError) System.exit(70);
    }

    private static void runPrompt() throws IOException {
        final InputStreamReader input = new InputStreamReader(System.in);
        final BufferedReader reader = new BufferedReader(input);

        for (;;) {
            System.out.print("> ");
            final String line = reader.readLine();
            if (line != null) {
                run(line);
                hadError = false;
            }
        }
    }

    static void error(int line, String message) {
        report(line, "", message);
    }

    static void error(Token token, String message) {
        if (token.type == TokenType.EOF) {
            report(token.line, " at end", message);
        } else{
            report(token.line, " at '" + token.lexeme + "'", message);
        }
    }

    static void runtimeError(RuntimeError error) {
        System.out.println(error.getMessage() + "\n[line " + error.token.line + "]");
        hadRuntimeError = true;
    }

    private static void report(int line, String where, String message) {
        System.err.println("[line " + line + "] Error " + where + "; " + message);
        hadError = true;
    }

    public static void main( String[] args ) throws IOException {
        if (args.length > 1) {
            System.out.println("Usage: jlox [script]");
            System.exit(64);
        } else if (args.length == 1) {
            runFile(args[0]);
        } else {
            runPrompt();
        }
    }
}
