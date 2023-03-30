package edu.ufsj;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import edu.ufsj.lox.Scanner;
import edu.ufsj.lox.TokenType;

public class ScannerTest {

    @Test
    public void shouldReturnCorrectTwoLetterTokens() {
        final Scanner scanner = new Scanner("== != >= <=");

        scanner.scanTokens();
        
        assertArrayEquals(
            new ArrayList<TokenType>(Arrays.asList(
                TokenType.EQUAL_EQUAL, 
                TokenType.BANG_EQUAL, 
                TokenType.GREATER_EQUAL, 
                TokenType.LESS_EQUAL, 
                TokenType.EOF
            )).toArray(),
            scanner.tokens.stream().map(token -> token.getType()).toArray()
        );
    }

    // @Test
    // public void shouldReturnCorrectComments() {
    //     final Scanner scanner = new Scanner("== != >= <=");

    //     scanner.scanTokens();
        
    //     assertArrayEquals(
    //         new ArrayList<TokenType>(Arrays.asList(
    //             TokenType.EQUAL_EQUAL, 
    //             TokenType.BANG_EQUAL, 
    //             TokenType.GREATER_EQUAL, 
    //             TokenType.LESS_EQUAL, 
    //             TokenType.EOF
    //         )).toArray(),
    //         scanner.tokens.stream().map(token -> token.getType()).toArray()
    //     );
    // }
}
