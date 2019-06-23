package mzinin.petproject.s2e2;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;


class ConverterTests {

    @Test
    void oneOperatorTwoOperandsTest() {
        final Converter converter = createConverter();
        converter.addOperator("+", 1);

        final List<Token> inputTokens = Arrays.asList(new Token(TokenType.ATOM, "A"),
                                                      new Token(TokenType.OPERATOR, "+"),
                                                      new Token(TokenType.ATOM, "B"));

        final List<Token> actualTokens = converter.convert(inputTokens);

        final List<Token> expectedTokens = Arrays.asList(new Token(TokenType.ATOM, "A"),
                                                         new Token(TokenType.ATOM, "B"),
                                                         new Token(TokenType.OPERATOR, "+"));
        assertIterableEquals(expectedTokens, actualTokens);
    }

    @Test
    void twoOperatorsThreeOperandsTest() {
        final Converter converter = createConverter();
        converter.addOperator("+", 1);

        final List<Token> inputTokens = Arrays.asList(new Token(TokenType.ATOM, "A"),
                                                      new Token(TokenType.OPERATOR, "+"),
                                                      new Token(TokenType.ATOM, "B"),
                                                      new Token(TokenType.OPERATOR, "+"),
                                                      new Token(TokenType.ATOM, "C"));

        final List<Token> actualTokens = converter.convert(inputTokens);

        final List<Token> expectedTokens = Arrays.asList(new Token(TokenType.ATOM, "A"),
                                                         new Token(TokenType.ATOM, "B"),
                                                         new Token(TokenType.OPERATOR, "+"),
                                                         new Token(TokenType.ATOM, "C"),
                                                         new Token(TokenType.OPERATOR, "+"));
        assertIterableEquals(expectedTokens, actualTokens);
    }

    private Converter createConverter() {
        Converter result = new Converter();
        return result;
    }
}
