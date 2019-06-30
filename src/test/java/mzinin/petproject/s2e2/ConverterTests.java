package mzinin.petproject.s2e2;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;


class ConverterTests {

    @Test
    void positiveTest_OneBInaryOperator_ResultValue() {
        final Converter converter = new Converter();
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
    void positiveTest_TwoBinaryOperatorsSamePriority_ResultValue() {
        final Converter converter = new Converter();
        converter.addOperator("+", 1);
        converter.addOperator("-", 1);

        final List<Token> inputTokens = Arrays.asList(new Token(TokenType.ATOM, "A"),
                                                      new Token(TokenType.OPERATOR, "+"),
                                                      new Token(TokenType.ATOM, "B"),
                                                      new Token(TokenType.OPERATOR, "-"),
                                                      new Token(TokenType.ATOM, "C"));

        final List<Token> actualTokens = converter.convert(inputTokens);

        final List<Token> expectedTokens = Arrays.asList(new Token(TokenType.ATOM, "A"),
                                                         new Token(TokenType.ATOM, "B"),
                                                         new Token(TokenType.OPERATOR, "+"),
                                                         new Token(TokenType.ATOM, "C"),
                                                         new Token(TokenType.OPERATOR, "-"));
        assertIterableEquals(expectedTokens, actualTokens);
    }

    @Test
    void positiveTest_TwoOperatorsDifferentPriorities_ResultValue() {
        final Converter converter = new Converter();
        converter.addOperator("+", 1);
        converter.addOperator("*", 2);

        final List<Token> inputTokens = Arrays.asList(new Token(TokenType.ATOM, "A"),
                                                      new Token(TokenType.OPERATOR, "+"),
                                                      new Token(TokenType.ATOM, "B"),
                                                      new Token(TokenType.OPERATOR, "*"),
                                                      new Token(TokenType.ATOM, "C"));

        final List<Token> actualTokens = converter.convert(inputTokens);

        final List<Token> expectedTokens = Arrays.asList(new Token(TokenType.ATOM, "A"),
                                                         new Token(TokenType.ATOM, "B"),
                                                         new Token(TokenType.ATOM, "C"),
                                                         new Token(TokenType.OPERATOR, "*"),
                                                         new Token(TokenType.OPERATOR, "+"));
        assertIterableEquals(expectedTokens, actualTokens);
    }

    @Test
    void positiveTest_UnaryOperatorAndBinaryOperator_ResultValue() {
        final Converter converter = new Converter();
        converter.addOperator("!=", 1);
        converter.addOperator("!", 2);

        final List<Token> inputTokens = Arrays.asList(new Token(TokenType.OPERATOR, "!"),
                                                      new Token(TokenType.ATOM, "A"),
                                                      new Token(TokenType.OPERATOR, "!="),
                                                      new Token(TokenType.ATOM, "B"));

        final List<Token> actualTokens = converter.convert(inputTokens);

        final List<Token> expectedTokens = Arrays.asList(new Token(TokenType.ATOM, "A"),
                                                         new Token(TokenType.OPERATOR, "!"),
                                                         new Token(TokenType.ATOM, "B"),
                                                         new Token(TokenType.OPERATOR, "!="));
        assertIterableEquals(expectedTokens, actualTokens);
    }
}
