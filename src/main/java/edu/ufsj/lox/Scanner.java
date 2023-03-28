package edu.ufsj.lox;

import java.util.ArrayList;
import java.util.List;

class Scanner {

    private int start = 0;
    private int current = 0;
    private int line = 0;
    private final String source;
    private final List<Token> tokens = new ArrayList<Token>();

    Scanner(String source) {
        this.source = source;
    }

    public boolean isAtEnd() {
        return false;
    }

    public void scanToken() {

    }

    public List<Token> scanTokens() {
        int start, current;

        start = current;
        scanToken();

        tokens.add(new Token(TokenType.EOF, "", null, true));

        return tokens;
    }

}
