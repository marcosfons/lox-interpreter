package edu.ufsj.lox;

import static edu.ufsj.lox.TokenType.*;    

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Scanner {

    private int start = 0;
    private int current = 0;
    private int line = 1;
    private final String source;
    private final List<Token> tokens = new ArrayList<Token>();

    private static final Map<String, TokenType> keywords;

    static {
        keywords = new HashMap <String, TokenType>();
        keywords.put("and", AND);
        keywords.put("class", CLASS);
        keywords.put("else", ELSE);
        keywords.put("false", FALSE);
        keywords.put("for", FOR);
        keywords.put("fun", FUN);
        keywords.put("if", IF);
        keywords.put("nil", NIL);
        keywords.put("or", OR);
        keywords.put("print", PRINT);
        keywords.put("return", RETURN);
        keywords.put("super", SUPER);
        keywords.put("this", THIS);
        keywords.put("true", TRUE);
        keywords.put("var", VAR);
        keywords.put("while", WHILE);
    }

    Scanner(String source) {
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
    
    private char peakNext() {
        if (current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }

    private void scanToken() {
        char c = advance();

        switch (c) {
            case '(': addToken(LEFT_PAREN); break;
            case ')': addToken(RIGHT_PAREN); break;

            case '{': addToken(LEFT_BRACE); break;
            case '}': addToken(RIGHT_BRACE); break;

            case '.': addToken(DOT); break;
            case ',': addToken(COMMA); break;
            case ';': addToken(SEMICOLON); break;

            case '*': addToken(STAR); break;
            case '+': addToken(PLUS); break;
            case '-': addToken(MINUS); break;

            case '"': string(); break;

            case ':': addToken(COLON); break;
            case '?': addToken(QUESTION_MARK); break;

            case ' ':
            case '\r':
            case '\t':
                break;

            case '\n':
                line++;
                break;

            case '!': addToken(match('=') ? BANG_EQUAL : BANG); break;
            case '=': addToken(match('=') ? EQUAL_EQUAL : EQUAL); break;
            case '<': addToken(match('=') ? LESS_EQUAL : LESS); break;
            case '>': addToken(match('=') ? GREATER_EQUAL : GREATER); break;

            case '/':
                if (match('/')) { // Single line comments
                    while (peak() != '\n' && !isAtEnd())
                        advance();
                } else if (match('*')) { // Multi line comments
                    boolean finishedMultilineComment = false;

                    while (!isAtEnd()) {
                        if (match('*')) { 
                            if (match('/')) { // Match */ end of multiline comment
                                finishedMultilineComment = true;
                                break;
                            }
                            continue;
                        } else if (peak() == '\n') {
                            line++;
                        }

                        advance();
                    }

                    if (isAtEnd() && !finishedMultilineComment) {
                        Lox.error(line, "Unterminated comment");
                    }
                } else { // Slash
                    addToken(SLASH);
                }
                break;

            default:
                if (isDigit(c)) {
                    number();
                } else if (isAlpha(c)) {
                    identifier();
                } else {
                    Lox.error(line, "Unexpected character: \"" + c + "\"");
                }
                break;
        }
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
               (c >= 'A' && c <= 'Z') || 
               c == '_';
    }

    private boolean isAlphaNumeric(char c) {
        return isDigit(c) || isAlpha(c);
    }

    private void identifier() {
        while(isAlphaNumeric(peak())) {
            advance();
        }

        final String text = source.substring(start, current);
        TokenType type = keywords.get(text);
        if (type == null) {
            type = IDENTIFIER;
        }
        addToken(type);
    }

    private void number() {
        while (isDigit(peak())) {
            advance();
        }

        if (peak() == '.' && isDigit(peakNext())) {
            advance();

            while (isDigit(peak())) {
                advance();
            }
        }

        addToken(NUMBER, Double.parseDouble(source.substring(start, current)));
    }

    private void string() {
        while (peak() != '"' && !isAtEnd()) {
            if (peak() == '\n') {
                line++;
            }

            advance();
        }

        if (isAtEnd()) {
            Lox.error(line, "Unterminated string");
            return;
        }

        advance();
        final String value = source.substring(start + 1, current - 1);
        addToken(STRING, value);
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

        tokens.add(new Token(EOF, null, null, line));

        return tokens;
    }

}
