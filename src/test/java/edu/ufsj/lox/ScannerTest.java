package edu.ufsj.lox;

import static org.junit.Assert.assertArrayEquals;

import static edu.ufsj.lox.TokenType.*;    

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class ScannerTest {

    @Test
    public void shouldReturnCorrectTwoLetterTokens() {
        final Scanner scanner = new Scanner("== != >= <=");
        final List<Token> tokens = scanner.scanTokens();
        
        assertArrayEquals(
            new ArrayList<Token>(Arrays.asList(
                new Token(EQUAL_EQUAL, "==", null, 1), 
                new Token(BANG_EQUAL, "!=", null, 1), 
                new Token(GREATER_EQUAL, ">=", null, 1), 
                new Token(LESS_EQUAL, "<=", null, 1), 
                new Token(EOF, null, null, 1)
            )).toArray(),
            tokens.toArray()
        );
    }

    @Test
    public void shouldReturnCorrectOneLetterTokens() {
        final Scanner scanner = new Scanner(" ( ) { } , . - + ; / * ! = > <");
        final List<Token> tokens = scanner.scanTokens();
        
        assertArrayEquals(
            new ArrayList<Token>(Arrays.asList(
                new Token(LEFT_PAREN, "(", null, 1),
                new Token(RIGHT_PAREN, ")", null, 1),
                new Token(LEFT_BRACE, "{", null, 1),
                new Token(RIGHT_BRACE, "}", null, 1),
                new Token(COMMA, ",", null, 1),
                new Token(DOT, ".", null, 1),
                new Token(MINUS, "-", null, 1),
                new Token(PLUS, "+", null, 1),
                new Token(SEMICOLON, ";", null, 1),
                new Token(SLASH, "/", null, 1),
                new Token(STAR, "*", null, 1),
                new Token(BANG, "!", null, 1),
                new Token(EQUAL, "=", null, 1),
                new Token(GREATER, ">", null, 1),
                new Token(LESS, "<", null, 1),
                new Token(EOF, null, null, 1)
            )).toArray(),
            tokens.toArray()
        );
    }

    @Test
    public void shouldReturnTokenWithTheCorrectLineNumber() {
        final Scanner scanner = new Scanner(String.join("\n",
            "( )",
            "{ }",
            "+",
            ";"
        ));
        final List<Token> tokens = scanner.scanTokens();
        
        assertArrayEquals(
            new ArrayList<Token>(Arrays.asList(
                new Token(LEFT_PAREN, "(", null, 1),
                new Token(RIGHT_PAREN, ")", null, 1),
                new Token(LEFT_BRACE, "{", null, 2),
                new Token(RIGHT_BRACE, "}", null, 2),
                new Token(PLUS, "+", null, 3),
                new Token(SEMICOLON, ";", null, 4),
                new Token(EOF, null, null, 4)
            )).toArray(),
            tokens.toArray()
        );
    }

    @Test
    public void shouldCorrectlyIgnoreOneLineComments() {
        final Scanner scanner = new Scanner(String.join("\n",
            "( )",
            "// This is a one line comment",
            "/// Second connected comment with additional leading slash",
            "+",
            "/",
            "//", // Empty comment
            "// " // Empty comment with one whitespace
        ));
        final List<Token> tokens = scanner.scanTokens();
        
        assertArrayEquals(
            new ArrayList<Token>(Arrays.asList(
                new Token(LEFT_PAREN, "(", null, 1),
                new Token(RIGHT_PAREN, ")", null, 1),
                new Token(PLUS, "+", null, 4),
                new Token(SLASH, "/", null, 5),
                new Token(EOF, null, null, 7)
            )).toArray(),
            tokens.toArray()
        );
    }


    /// START Tests for Multiline comments

    @Test
    public void shouldCorrectlyIgnoreMultiLineComments() {
        final Scanner scanner = new Scanner(String.join("\n",
            "( )",
            "/* This is a multiline comment",
            "   This is the second line of it",
            "*/",
            "/"
        ));
        final List<Token> tokens = scanner.scanTokens();
        
        assertArrayEquals(
            new ArrayList<Token>(Arrays.asList(
                new Token(LEFT_PAREN, "(", null, 1),
                new Token(RIGHT_PAREN, ")", null, 1),
                new Token(SLASH, "/", null, 4),
                new Token(EOF, null, null, 5)
            )).toArray(),
            tokens.toArray()
        );
    }

    @Test
    public void shouldCorrectlyIgnoreMultiLineCommentsWithinOneline() {
        final Scanner scanner = new Scanner(String.join("\n",
            "( )",
            "/* This is a multiline comment in one line only */",
            "/"
        ));
        final List<Token> tokens = scanner.scanTokens();
        
        assertArrayEquals(
            new ArrayList<Token>(Arrays.asList(
                new Token(LEFT_PAREN, "(", null, 1),
                new Token(RIGHT_PAREN, ")", null, 1),
                new Token(SLASH, "/", null, 3),
                new Token(EOF, null, null, 3)
            )).toArray(),
            tokens.toArray()
        );
    }

    @Test
    public void shouldCorrectlyIgnoreMultiLineCommentsWithTokenAfterIt() {
        final Scanner scanner = new Scanner("{/* This is a multiline comment in one line only */}");
        final List<Token> tokens = scanner.scanTokens();
        
        assertArrayEquals(
            new ArrayList<Token>(Arrays.asList(
                new Token(LEFT_PAREN, "{", null, 1),
                new Token(RIGHT_PAREN, "}", null, 1),
                new Token(EOF, null, null, 1)
            )).toArray(),
            tokens.toArray()
        );
    }

    @Test
    public void shouldCorrectlyIgnoreMultiLineCommentsThatHasStarInIt() {
        final Scanner scanner = new Scanner(String.join("\n",
            "/* Simple example of multiplication 2 * 3 = 6 *",
            "   Finishing comment */",
            "*"
        ));
        final List<Token> tokens = scanner.scanTokens();
        
        assertArrayEquals(
            new ArrayList<Token>(Arrays.asList(
                new Token(STAR, "*", null, 3),
                new Token(EOF, null, null, 3)
            )).toArray(),
            tokens.toArray()
        );
    }

    @Test
    public void shouldCorrectlyIgnoreMultiLineCommentsWithTokenAfterIt22() {
        final Scanner scanner = new Scanner(String.join("\n",
            "/*",
            "   Finishing comment */",
            "*"
        ));
        final List<Token> tokens = scanner.scanTokens();
        
        assertArrayEquals(
            new ArrayList<Token>(Arrays.asList(
                new Token(STAR, "*", null, 3),
                new Token(EOF, null, null, 3)
            )).toArray(),
            tokens.toArray()
        );
    }

    /// END Tests for Multiline comments


    @Test
    public void shouldIdentifyTernaryTokens() {
        final Scanner scanner = new Scanner("15 > 10 ? 1 : 2");
        final List<Token> tokens = scanner.scanTokens();
        
        assertArrayEquals(
            new ArrayList<Token>(Arrays.asList(
                new Token(NUMBER, "15", 15, 1),
                new Token(GREATER, ">", null, 1),
                new Token(NUMBER, "10", 10, 1),
                new Token(QUESTION_MARK, "?", null, 1),
                new Token(NUMBER, "1", 1, 1),
                new Token(COLON, ":", null, 1),
                new Token(NUMBER, "2", 2, 1),
                new Token(EOF, null, null, 1)
            )).toArray(),
            tokens.toArray()
        );
    }

}
