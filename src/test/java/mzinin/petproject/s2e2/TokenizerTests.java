package mzinin.petproject.s2e2;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;


class TokenizerTests {

    @Test
    void positiveTest_OneOperatorWithSpaces_ResultValue() {
        final Tokenizer tokenizer = new Tokenizer();
        tokenizer.addOperator("+");

        final List<Token> actualTokens = tokenizer.tokenize("A + B");

        final List<Token> expectedTokens = Arrays.asList(new Token(TokenType.ATOM, "A"),
                                                         new Token(TokenType.OPERATOR, "+"),
                                                         new Token(TokenType.ATOM, "B"));
        assertIterableEquals(expectedTokens, actualTokens);
    }

    @Test
    void positiveTest_OneOperatorWithoutSpaces_ResultValue() {
        final Tokenizer tokenizer = new Tokenizer();
        tokenizer.addOperator("+");

        final List<Token> actualTokens = tokenizer.tokenize("A+B");

        final List<Token> expectedTokens = Arrays.asList(new Token(TokenType.ATOM, "A"),
                                                         new Token(TokenType.OPERATOR, "+"),
                                                         new Token(TokenType.ATOM, "B"));
        assertIterableEquals(expectedTokens, actualTokens);
    }

    @Test
    void positiveTest_TwoOperatorWithSpaces_ResultValue() {
        final Tokenizer tokenizer = new Tokenizer();
        tokenizer.addOperator("+");
        tokenizer.addOperator("&&");

        final List<Token> actualTokens = tokenizer.tokenize("A + B && C");

        final List<Token> expectedTokens = Arrays.asList(new Token(TokenType.ATOM, "A"),
                                                         new Token(TokenType.OPERATOR, "+"),
                                                         new Token(TokenType.ATOM, "B"),
                                                         new Token(TokenType.OPERATOR, "&&"),
                                                         new Token(TokenType.ATOM, "C"));
        assertIterableEquals(expectedTokens, actualTokens);
    }

    @Test
    void positiveTest_TwoOperatorWithoutSpaces_ResultValue() {
        final Tokenizer tokenizer = new Tokenizer();
        tokenizer.addOperator("+");
        tokenizer.addOperator("&&");

        final List<Token> actualTokens = tokenizer.tokenize("A+B&&C");

        final List<Token> expectedTokens = Arrays.asList(new Token(TokenType.ATOM, "A"),
                                                         new Token(TokenType.OPERATOR, "+"),
                                                         new Token(TokenType.ATOM, "B"),
                                                         new Token(TokenType.OPERATOR, "&&"),
                                                         new Token(TokenType.ATOM, "C"));
        assertIterableEquals(expectedTokens, actualTokens);
    }

    @Test
    void positiveTest_OneOperatorIsSubstringOfAnother_ResultValue() {
        final Tokenizer tokenizer = new Tokenizer();
        tokenizer.addOperator("!");
        tokenizer.addOperator("!=");

        final List<Token> actualTokens = tokenizer.tokenize("A != !B");

        final List<Token> expectedTokens = Arrays.asList(new Token(TokenType.ATOM, "A"),
                                                         new Token(TokenType.OPERATOR, "!="),
                                                         new Token(TokenType.OPERATOR, "!"),
                                                         new Token(TokenType.ATOM, "B"));
        assertIterableEquals(expectedTokens, actualTokens);
    }

    @Test
    void positiveTest_OneFunctionWithoutArguments_ResultValue() {
        final Tokenizer tokenizer = new Tokenizer();
        tokenizer.addFunction("FUN1");

        final List<Token> actualTokens = tokenizer.tokenize("FUN1()");

        final List<Token> expectedTokens = Arrays.asList(new Token(TokenType.FUNCTION, "FUN1"),
                                                         new Token(TokenType.LEFT_BRACKET, "("),
                                                         new Token(TokenType.RIGHT_BRACKET, ")"));
        assertIterableEquals(expectedTokens, actualTokens);
    }

    @Test
    void positiveTest_OneFunctionOneArgument_ResultValue() {
        final Tokenizer tokenizer = new Tokenizer();
        tokenizer.addFunction("FUN1");

        final List<Token> actualTokens = tokenizer.tokenize("FUN1(Arg1)");

        final List<Token> expectedTokens = Arrays.asList(new Token(TokenType.FUNCTION, "FUN1"),
                                                         new Token(TokenType.LEFT_BRACKET, "("),
                                                         new Token(TokenType.ATOM, "Arg1"),
                                                         new Token(TokenType.RIGHT_BRACKET, ")"));
        assertIterableEquals(expectedTokens, actualTokens);
    }

    @Test
    void positiveTest_OneFunctionThreeArguments_ResultValue() {
        final Tokenizer tokenizer = new Tokenizer();
        tokenizer.addFunction("FUN1");

        final List<Token> actualTokens = tokenizer.tokenize("FUN1(Arg1, Arg2,Arg3)");

        final List<Token> expectedTokens = Arrays.asList(new Token(TokenType.FUNCTION, "FUN1"),
                                                         new Token(TokenType.LEFT_BRACKET, "("),
                                                         new Token(TokenType.ATOM, "Arg1"),
                                                         new Token(TokenType.COMMA, ","),
                                                         new Token(TokenType.ATOM, "Arg2"),
                                                         new Token(TokenType.COMMA, ","),
                                                         new Token(TokenType.ATOM, "Arg3"),
                                                         new Token(TokenType.RIGHT_BRACKET, ")"));
        assertIterableEquals(expectedTokens, actualTokens);
    }

    @Test
    void positiveTest_TwoFunctionsOneOperator_ResultValue() {
        final Tokenizer tokenizer = new Tokenizer();
        tokenizer.addFunction("FUN1");
        tokenizer.addFunction("FUN2");
        tokenizer.addOperator("+");

        final List<Token> actualTokens = tokenizer.tokenize("FUN1(Arg1) + FUN2(Arg2)");

        final List<Token> expectedTokens = Arrays.asList(new Token(TokenType.FUNCTION, "FUN1"),
                                                         new Token(TokenType.LEFT_BRACKET, "("),
                                                         new Token(TokenType.ATOM, "Arg1"),
                                                         new Token(TokenType.RIGHT_BRACKET, ")"),
                                                         new Token(TokenType.OPERATOR, "+"),
                                                         new Token(TokenType.FUNCTION, "FUN2"),
                                                         new Token(TokenType.LEFT_BRACKET, "("),
                                                         new Token(TokenType.ATOM, "Arg2"),
                                                         new Token(TokenType.RIGHT_BRACKET, ")"));
        assertIterableEquals(expectedTokens, actualTokens);
    }

    @Test
    void positiveTest_NestedFunctions_ResultValue() {
        final Tokenizer tokenizer = new Tokenizer();
        tokenizer.addFunction("FUN1");
        tokenizer.addFunction("FUN2");
        tokenizer.addFunction("FUN3");

        final List<Token> actualTokens = tokenizer.tokenize("FUN1(FUN2(), FUN3())");

        final List<Token> expectedTokens = Arrays.asList(new Token(TokenType.FUNCTION, "FUN1"),
                                                         new Token(TokenType.LEFT_BRACKET, "("),
                                                         new Token(TokenType.FUNCTION, "FUN2"),
                                                         new Token(TokenType.LEFT_BRACKET, "("),
                                                         new Token(TokenType.RIGHT_BRACKET, ")"),
                                                         new Token(TokenType.COMMA, ","),
                                                         new Token(TokenType.FUNCTION, "FUN3"),
                                                         new Token(TokenType.LEFT_BRACKET, "("),
                                                         new Token(TokenType.RIGHT_BRACKET, ")"),
                                                         new Token(TokenType.RIGHT_BRACKET, ")"));
        assertIterableEquals(expectedTokens, actualTokens);
    }

    @Test
    void positiveTest_NestedBrackets_ResultValue() {
        final Tokenizer tokenizer = new Tokenizer();
        tokenizer.addOperator("+");

        final List<Token> actualTokens = tokenizer.tokenize("(((A + B)))");

        final List<Token> expectedTokens = Arrays.asList(new Token(TokenType.LEFT_BRACKET, "("),
                                                         new Token(TokenType.LEFT_BRACKET, "("),
                                                         new Token(TokenType.LEFT_BRACKET, "("),
                                                         new Token(TokenType.ATOM, "A"),
                                                         new Token(TokenType.OPERATOR, "+"),
                                                         new Token(TokenType.ATOM, "B"),
                                                         new Token(TokenType.RIGHT_BRACKET, ")"),
                                                         new Token(TokenType.RIGHT_BRACKET, ")"),
                                                         new Token(TokenType.RIGHT_BRACKET, ")"));
        assertIterableEquals(expectedTokens, actualTokens);
    }

    @Test
    void positiveTest_OperatorsWithoutArguments_ResultValue() {
        final Tokenizer tokenizer = new Tokenizer();
        tokenizer.addOperator("+");

        final List<Token> actualTokens = tokenizer.tokenize("+ + +");

        final List<Token> expectedTokens = Arrays.asList(new Token(TokenType.OPERATOR, "+"),
                                                         new Token(TokenType.OPERATOR, "+"),
                                                         new Token(TokenType.OPERATOR, "+"));
        assertIterableEquals(expectedTokens, actualTokens);
    }

    @Test
    void positiveTest_UnpairedBrackets_ResultValue() {
        final Tokenizer tokenizer = new Tokenizer();

        final List<Token> actualTokens = tokenizer.tokenize("((()");

        final List<Token> expectedTokens = Arrays.asList(new Token(TokenType.LEFT_BRACKET, "("),
                                                         new Token(TokenType.LEFT_BRACKET, "("),
                                                         new Token(TokenType.LEFT_BRACKET, "("),
                                                         new Token(TokenType.RIGHT_BRACKET, ")"));
        assertIterableEquals(expectedTokens, actualTokens);
    }

    @Test
    void negativeTest_TwoOperatorsWithTheSameName() {
        final Tokenizer tokenizer = new Tokenizer();
        tokenizer.addOperator("+");

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> tokenizer.addOperator("+"),
            "Expected Tokenizer to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Operator + is already added"));
    }

    @Test
    void negativeTest_TwoFunctionsWithTheSameName() {
        final Tokenizer tokenizer = new Tokenizer();
        tokenizer.addFunction("FUN");

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> tokenizer.addFunction("FUN"),
            "Expected Tokenizer to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Function FUN is already added"));
    }

    @Test
    void negativeTest_FunctionAndOperatorWithTheSameName() {
        final Tokenizer tokenizer = new Tokenizer();
        tokenizer.addFunction("FF");

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> tokenizer.addOperator("FF"),
            "Expected Tokenizer to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Function FF is already added"));
    }

    @Test
    void negativeTest_dOperatorAndFunctionWithTheSameName() {
        final Tokenizer tokenizer = new Tokenizer();
        tokenizer.addOperator("FF");

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> tokenizer.addFunction("FF"),
            "Expected Tokenizer to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Operator FF is already added"));
    }
}
