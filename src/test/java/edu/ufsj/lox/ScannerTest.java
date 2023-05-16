package edu.ufsj.lox;

import static org.junit.Assert.assertArrayEquals;

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
                new Token(TokenType.EQUAL_EQUAL, "==", null, 1), 
                new Token(TokenType.BANG_EQUAL, "!=", null, 1), 
                new Token(TokenType.GREATER_EQUAL, ">=", null, 1), 
                new Token(TokenType.LESS_EQUAL, "<=", null, 1), 
                new Token(TokenType.EOF, null, null, 1)
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
                new Token(TokenType.LEFT_PAREN, "(", null, 1),
                new Token(TokenType.RIGHT_PAREN, ")", null, 1),
                new Token(TokenType.LEFT_BRACE, "{", null, 1),
                new Token(TokenType.RIGHT_BRACE, "}", null, 1),
                new Token(TokenType.COMMA, ",", null, 1),
                new Token(TokenType.DOT, ".", null, 1),
                new Token(TokenType.MINUS, "-", null, 1),
                new Token(TokenType.PLUS, "+", null, 1),
                new Token(TokenType.SEMICOLON, ";", null, 1),
                new Token(TokenType.SLASH, "/", null, 1),
                new Token(TokenType.STAR, "*", null, 1),
                new Token(TokenType.BANG, "!", null, 1),
                new Token(TokenType.EQUAL, "=", null, 1),
                new Token(TokenType.GREATER, ">", null, 1),
                new Token(TokenType.LESS, "<", null, 1),
                new Token(TokenType.EOF, null, null, 1)
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
                new Token(TokenType.LEFT_PAREN, "(", null, 1),
                new Token(TokenType.RIGHT_PAREN, ")", null, 1),
                new Token(TokenType.LEFT_BRACE, "{", null, 2),
                new Token(TokenType.RIGHT_BRACE, "}", null, 2),
                new Token(TokenType.PLUS, "+", null, 3),
                new Token(TokenType.SEMICOLON, ";", null, 4),
                new Token(TokenType.EOF, null, null, 4)
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
                new Token(TokenType.LEFT_PAREN, "(", null, 1),
                new Token(TokenType.RIGHT_PAREN, ")", null, 1),
                new Token(TokenType.PLUS, "+", null, 4),
                new Token(TokenType.SLASH, "/", null, 5),
                new Token(TokenType.EOF, null, null, 7)
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
                new Token(TokenType.LEFT_PAREN, "(", null, 1),
                new Token(TokenType.RIGHT_PAREN, ")", null, 1),
                new Token(TokenType.SLASH, "/", null, 4),
                new Token(TokenType.EOF, null, null, 5)
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
                new Token(TokenType.LEFT_PAREN, "(", null, 1),
                new Token(TokenType.RIGHT_PAREN, ")", null, 1),
                new Token(TokenType.SLASH, "/", null, 3),
                new Token(TokenType.EOF, null, null, 3)
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
                new Token(TokenType.LEFT_PAREN, "{", null, 1),
                new Token(TokenType.RIGHT_PAREN, "}", null, 1),
                new Token(TokenType.EOF, null, null, 1)
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
                new Token(TokenType.STAR, "*", null, 3),
                new Token(TokenType.EOF, null, null, 3)
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
                new Token(TokenType.STAR, "*", null, 3),
                new Token(TokenType.EOF, null, null, 3)
            )).toArray(),
            tokens.toArray()
        );
    }

    /// END Tests for Multiline comments

}
