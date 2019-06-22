package mzinin.petproject.s2e2;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;


class TokenizerTests {

    @Test
    void oneOperatorTwoOperandsTest() {
        final Tokenizer tokenizer = createTokenizer();
        tokenizer.addOperator("+");

        final List<Token> actualTokens = tokenizer.tokenize("A + B");

        final List<Token> expectedTokens = Arrays.asList(new Token(TokenType.ATOM, "A"),
                                                         new Token(TokenType.OPERATOR, "+"),
                                                         new Token(TokenType.ATOM, "B"));
        assertIterableEquals(expectedTokens, actualTokens);
    }

    @Test
    void twoOperatorsThreeOperandsTest() {
        final Tokenizer tokenizer = createTokenizer();
        tokenizer.addOperator("+");

        final List<Token> actualTokens = tokenizer.tokenize("A + B + C");

        final List<Token> expectedTokens = Arrays.asList(new Token(TokenType.ATOM, "A"),
                                                         new Token(TokenType.OPERATOR, "+"),
                                                         new Token(TokenType.ATOM, "B"),
                                                         new Token(TokenType.OPERATOR, "+"),
                                                         new Token(TokenType.ATOM, "C"));
        assertIterableEquals(expectedTokens, actualTokens);
    }

    private Tokenizer createTokenizer() {
        Tokenizer result = new Tokenizer();
        return result;
    }
}
