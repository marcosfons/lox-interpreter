package edu.ufsj.lox;

import java.util.ArrayList;
import java.util.List;

public class Scanner {

    private int start = 0;
    private int current = 0;
    private int line = 1;
    private final String source;
    public final List<Token> tokens = new ArrayList<Token>();

    public Scanner(String source) {
        this.source = source;
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private char advance() {
        current++;
        return source.charAt(current - 1);
    }

    private boolean match(char expectedChar) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expectedChar) return false;

        current++;
        return true;
    }

    private char peak() {
        if (isAtEnd()) return '\n';
        return source.charAt(current);
    }

    private void scanToken() {
        char c = advance();

        switch (c) {
            case '(': addToken(TokenType.LEFT_PAREN); break;
            case ')': addToken(TokenType.RIGHT_PAREN); break;

            case '{': addToken(TokenType.LEFT_BRACE); break;
            case '}': addToken(TokenType.RIGHT_BRACE); break;

            case '.': addToken(TokenType.DOT); break;
            case ',': addToken(TokenType.COMMA); break;
            case ';': addToken(TokenType.SEMICOLON); break;

            case '*': addToken(TokenType.STAR); break;
            case '+': addToken(TokenType.PLUS); break;
            case '-': addToken(TokenType.MINUS); break;

            case ' ':
            case '\r':
            case '\t':
                break;

            case '\n':
                line++;
                break;

            case '!': 
                addToken(match('=') ? TokenType.BANG_EQUAL : TokenType.BANG);
                break;

            case '=': 
                addToken(match('=') ? TokenType.EQUAL_EQUAL : TokenType.EQUAL);
                break;

            case '>': 
                addToken(match('=') ? TokenType.GREATER_EQUAL : TokenType.GREATER);
                break;

            case '<': 
                addToken(match('=') ? TokenType.LESS_EQUAL : TokenType.LESS);
                break;

            case '/':
                if (match('/')) {
                    while (peak() != '\n' && !isAtEnd())
                        advance();
                } else {
                    addToken(TokenType.SLASH);
                }
                break;

            default:
                Lox.error(line, "Unexpected character: \"" + c + "\"");
                break;
        }
    }

    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        final String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }

    public List<Token> scanTokens() {
        while (!isAtEnd()) {
            start = current;
            scanToken();
        }

        tokens.add(new Token(TokenType.EOF, "",null, line));

        return tokens;
    }

}
