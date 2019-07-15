package mzinin.petproject.s2e2;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;


class ConverterTests {

    @Test
    void positiveTest_OneBinaryOperator_ResultValue() {
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

    @Test
    void positiveTest_OneFunctionWithoutArguments_ResultValue() {
        final Converter converter = new Converter();

        final List<Token> inputTokens = Arrays.asList(new Token(TokenType.FUNCTION, "FUN"),
                                                      new Token(TokenType.LEFT_BRACKET, "("),
                                                      new Token(TokenType.RIGHT_BRACKET, ")"));

        final List<Token> actualTokens = converter.convert(inputTokens);

        final List<Token> expectedTokens = Arrays.asList(new Token(TokenType.FUNCTION, "FUN"));
        assertIterableEquals(expectedTokens, actualTokens);
    }

    @Test
    void positiveTest_OneFunctionOneArgument_ResultValue() {
        final Converter converter = new Converter();

        final List<Token> inputTokens = Arrays.asList(new Token(TokenType.FUNCTION, "FUN"),
                                                      new Token(TokenType.LEFT_BRACKET, "("),
                                                      new Token(TokenType.ATOM, "Arg1"),
                                                      new Token(TokenType.RIGHT_BRACKET, ")"));

        final List<Token> actualTokens = converter.convert(inputTokens);

        final List<Token> expectedTokens = Arrays.asList(new Token(TokenType.ATOM, "Arg1"), 
                                                         new Token(TokenType.FUNCTION, "FUN"));
        assertIterableEquals(expectedTokens, actualTokens);
    }

    @Test
    void positiveTest_OneFunctionThreeArguments_ResultValue() {
        final Converter converter = new Converter();

        final List<Token> inputTokens = Arrays.asList(new Token(TokenType.FUNCTION, "FUN"),
                                                      new Token(TokenType.LEFT_BRACKET, "("),
                                                      new Token(TokenType.ATOM, "Arg1"),
                                                      new Token(TokenType.ATOM, "Arg2"),
                                                      new Token(TokenType.ATOM, "Arg3"),
                                                      new Token(TokenType.RIGHT_BRACKET, ")"));

        final List<Token> actualTokens = converter.convert(inputTokens);

        final List<Token> expectedTokens = Arrays.asList(new Token(TokenType.ATOM, "Arg1"), 
                                                         new Token(TokenType.ATOM, "Arg2"),
                                                         new Token(TokenType.ATOM, "Arg3"),
                                                         new Token(TokenType.FUNCTION, "FUN"));
        assertIterableEquals(expectedTokens, actualTokens);
    }

    @Test
    void positiveTest_FunctionAndExernalOperator_ResultValue() {
        final Converter converter = new Converter();
        converter.addOperator("+", 1);

        final List<Token> inputTokens = Arrays.asList(new Token(TokenType.FUNCTION, "FUN"),
                                                      new Token(TokenType.LEFT_BRACKET, "("),
                                                      new Token(TokenType.ATOM, "Arg1"),
                                                      new Token(TokenType.RIGHT_BRACKET, ")"),
                                                      new Token(TokenType.OPERATOR, "+"),
                                                      new Token(TokenType.FUNCTION, "FUN"),
                                                      new Token(TokenType.LEFT_BRACKET, "("),
                                                      new Token(TokenType.ATOM, "Arg2"),
                                                      new Token(TokenType.RIGHT_BRACKET, ")"));

        final List<Token> actualTokens = converter.convert(inputTokens);

        final List<Token> expectedTokens = Arrays.asList(new Token(TokenType.ATOM, "Arg1"), 
                                                         new Token(TokenType.FUNCTION, "FUN"),
                                                         new Token(TokenType.ATOM, "Arg2"),
                                                         new Token(TokenType.FUNCTION, "FUN"),
                                                         new Token(TokenType.OPERATOR, "+"));
        assertIterableEquals(expectedTokens, actualTokens);
    }

    @Test
    void positiveTest_FunctionAndInternalOperator_ResultValue() {
        final Converter converter = new Converter();
        converter.addOperator("+", 1);

        final List<Token> inputTokens = Arrays.asList(new Token(TokenType.FUNCTION, "FUN"),
                                                      new Token(TokenType.LEFT_BRACKET, "("),
                                                      new Token(TokenType.ATOM, "Arg1"),
                                                      new Token(TokenType.OPERATOR, "+"),
                                                      new Token(TokenType.ATOM, "Arg2"),
                                                      new Token(TokenType.COMMA, ","),
                                                      new Token(TokenType.ATOM, "Arg3"),
                                                      new Token(TokenType.OPERATOR, "+"),
                                                      new Token(TokenType.ATOM, "Arg4"),
                                                      new Token(TokenType.RIGHT_BRACKET, ")"));

        final List<Token> actualTokens = converter.convert(inputTokens);

        final List<Token> expectedTokens = Arrays.asList(new Token(TokenType.ATOM, "Arg1"), 
                                                         new Token(TokenType.ATOM, "Arg2"),
                                                         new Token(TokenType.OPERATOR, "+"),
                                                         new Token(TokenType.ATOM, "Arg3"), 
                                                         new Token(TokenType.ATOM, "Arg4"), 
                                                         new Token(TokenType.OPERATOR, "+"),
                                                         new Token(TokenType.FUNCTION, "FUN"));
        assertIterableEquals(expectedTokens, actualTokens);
    }

    @Test
    void positiveTest_NestedFunctions_ResultValue() {
        final Converter converter = new Converter();

        final List<Token> inputTokens = Arrays.asList(new Token(TokenType.FUNCTION, "FUN1"),
                                                      new Token(TokenType.LEFT_BRACKET, "("),
                                                      new Token(TokenType.FUNCTION, "FUN2"),
                                                      new Token(TokenType.LEFT_BRACKET, "("),
                                                      new Token(TokenType.RIGHT_BRACKET, ")"),
                                                      new Token(TokenType.COMMA, ","),
                                                      new Token(TokenType.FUNCTION, "FUN3"),
                                                      new Token(TokenType.LEFT_BRACKET, "("),
                                                      new Token(TokenType.ATOM, "Arg1"),
                                                      new Token(TokenType.COMMA, ","),
                                                      new Token(TokenType.ATOM, "Arg2"),
                                                      new Token(TokenType.RIGHT_BRACKET, ")"),
                                                      new Token(TokenType.RIGHT_BRACKET, ")"));

        final List<Token> actualTokens = converter.convert(inputTokens);

        final List<Token> expectedTokens = Arrays.asList(new Token(TokenType.FUNCTION, "FUN2"), 
                                                         new Token(TokenType.ATOM, "Arg1"),
                                                         new Token(TokenType.ATOM, "Arg2"), 
                                                         new Token(TokenType.FUNCTION, "FUN3"), 
                                                         new Token(TokenType.FUNCTION, "FUN1"));
        assertIterableEquals(expectedTokens, actualTokens);
    }

    @Test
    void positiveTest_OperatorsWithoutArguments_ResultValue() {
        final Converter converter = new Converter();
        converter.addOperator("+", 1);

        final List<Token> inputTokens = Arrays.asList(new Token(TokenType.OPERATOR, "+"),
                                                      new Token(TokenType.OPERATOR, "+"),
                                                      new Token(TokenType.OPERATOR, "+"));

        final List<Token> actualTokens = converter.convert(inputTokens);

        final List<Token> expectedTokens = Arrays.asList(new Token(TokenType.OPERATOR, "+"), 
                                                         new Token(TokenType.OPERATOR, "+"),
                                                         new Token(TokenType.OPERATOR, "+"));
        assertIterableEquals(expectedTokens, actualTokens);
    }

    @Test
    void positiveTest_FunctionWithoutCommas_ResultValue() {
        final Converter converter = new Converter();

        final List<Token> inputTokens = Arrays.asList(new Token(TokenType.FUNCTION, "FUN"),
                                                      new Token(TokenType.LEFT_BRACKET, "("),
                                                      new Token(TokenType.ATOM, "Arg1"),
                                                      new Token(TokenType.ATOM, "Arg2"),
                                                      new Token(TokenType.RIGHT_BRACKET, ")"));

        final List<Token> actualTokens = converter.convert(inputTokens);

        final List<Token> expectedTokens = Arrays.asList(new Token(TokenType.ATOM, "Arg1"), 
                                                         new Token(TokenType.ATOM, "Arg2"),
                                                         new Token(TokenType.FUNCTION, "FUN"));
        assertIterableEquals(expectedTokens, actualTokens);
    }

    @Test
    void positiveTest_FunctionOfOperators_ResultValue() {
        final Converter converter = new Converter();
        converter.addOperator("+", 1);

        final List<Token> inputTokens = Arrays.asList(new Token(TokenType.FUNCTION, "FUN"),
                                                      new Token(TokenType.LEFT_BRACKET, "("),
                                                      new Token(TokenType.OPERATOR, "+"),
                                                      new Token(TokenType.OPERATOR, "+"),
                                                      new Token(TokenType.RIGHT_BRACKET, ")"));

        final List<Token> actualTokens = converter.convert(inputTokens);

        final List<Token> expectedTokens = Arrays.asList(new Token(TokenType.OPERATOR, "+"), 
                                                         new Token(TokenType.OPERATOR, "+"),
                                                         new Token(TokenType.FUNCTION, "FUN"));
        assertIterableEquals(expectedTokens, actualTokens);
    }

    @Test
    void negativeTest_UnpairedLeftBracket() {
        final Converter converter = new Converter();

        final List<Token> inputTokens = Arrays.asList(new Token(TokenType.FUNCTION, "FUN"),
                                                      new Token(TokenType.LEFT_BRACKET, "("),
                                                      new Token(TokenType.ATOM, "Arg1"));

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> converter.convert(inputTokens),
            "Expected Converter to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Unpaired bracket"));
    }

    @Test
    void negativeTest_UnpairedRightBracket() {
        final Converter converter = new Converter();

        final List<Token> inputTokens = Arrays.asList(new Token(TokenType.FUNCTION, "FUN"),
                                                      new Token(TokenType.ATOM, "Arg1"),
                                                      new Token(TokenType.RIGHT_BRACKET, ")"));

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> converter.convert(inputTokens),
            "Expected Converter to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Unpaired bracket"));
    }

    @Test
    void negativeTest_TwoOperatorsWithTheSameName() {
        final Converter converter = new Converter();
        converter.addOperator("+", 1);

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> converter.addOperator("+", 1),
            "Expected Converter to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Operator + is already added"));
    }

    @Test
    void negativeTest_UnknownOperator() {
        final Converter converter = new Converter();
        converter.addOperator("+", 1);

        final List<Token> inputTokens = Arrays.asList(new Token(TokenType.ATOM, "Arg1"),
                                                      new Token(TokenType.OPERATOR, "+"),
                                                      new Token(TokenType.ATOM, "Arg2"),
                                                      new Token(TokenType.OPERATOR, "*"),
                                                      new Token(TokenType.ATOM, "Arg3"));

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> converter.convert(inputTokens),
            "Expected Converter to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Unknown operator *"));
    }
}
