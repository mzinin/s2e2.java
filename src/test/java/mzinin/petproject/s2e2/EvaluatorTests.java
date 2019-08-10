package mzinin.petproject.s2e2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;


class EvaluatorTests {

    @Test
    void positiveTest_NothingAdded_SupportedFunctionsSize() {
        final Evaluator evaluator = new Evaluator();

        assertTrue(evaluator.getFunctions().isEmpty());
    }

    @Test
    void positiveTest_NothingAdded_SupportedOperatorsSize() {
        final Evaluator evaluator = new Evaluator();

        assertTrue(evaluator.getOperators().isEmpty());
    }

    @Test
    void positiveTest_NothingAdded_EvaluationResult() {
        final Evaluator evaluator = new Evaluator();
        final String inputExpression = "A B C";
        final String evaluatedExpression = evaluator.evaluate(inputExpression);

        assertEquals(inputExpression, evaluatedExpression);
    }

    @Test
    void positiveTest_AddFunction_SupportedFunctionsSize() {
        final Evaluator evaluator = new Evaluator();
        final AbstractFunction dummyFunction = makeDummyFunction();

        evaluator.addFunction(dummyFunction);

        assertEquals(1, evaluator.getFunctions().size());
    }

    @Test
    void positiveTest_AddFunction_VerifyTokenizer() {
        final ITokenizer tokenizerMock = mock(ITokenizer.class);
        final Evaluator evaluator = new Evaluator(null, tokenizerMock);
        final AbstractFunction dummyFunction = makeDummyFunction();

        evaluator.addFunction(dummyFunction);

        verify(tokenizerMock).addFunction(dummyFunction.name);
    }

    @Test
    void positiveTest_AddOperator_SupportedOperatorsSize() {
        final Evaluator evaluator = new Evaluator();
        final AbstractOperator dummyOperator = makeDummyOperator();

        evaluator.addOperator(dummyOperator);

        assertEquals(1, evaluator.getOperators().size());
    }

    @Test
    void positiveTest_AddOperator_VerifyConverter() {
        final IConverter converterMock = mock(IConverter.class);
        final ITokenizer tokenizerMock = mock(ITokenizer.class);
        final Evaluator evaluator = new Evaluator(converterMock, tokenizerMock);
        final AbstractOperator dummyOperator = makeDummyOperator();

        evaluator.addOperator(dummyOperator);

        verify(converterMock).addOperator(dummyOperator.name, dummyOperator.priority);
    }

    @Test
    void positiveTest_AddOperator_VerifyTokenizer() {
        final IConverter converterMock = mock(IConverter.class);
        final ITokenizer tokenizerMock = mock(ITokenizer.class);
        final Evaluator evaluator = new Evaluator(converterMock, tokenizerMock);
        final AbstractOperator dummyOperator = makeDummyOperator();

        evaluator.addOperator(dummyOperator);

        verify(tokenizerMock).addOperator(dummyOperator.name);
    }

    @Test
    void positiveTest_AddStandardFunctions_SupportedFunctionsSize() {
        final Evaluator evaluator = new Evaluator();
        evaluator.addStandardFunctions();

        assertFalse(evaluator.getFunctions().isEmpty());
    }

    @Test
    void positiveTest_AddStandardOperators_SupportedFunctionsSize() {
        final Evaluator evaluator = new Evaluator();
        evaluator.addStandardOperators();

        assertFalse(evaluator.getOperators().isEmpty());
    }

    @Test
    void positiveTest_Evaluate_VerifyConverter() {
        final IConverter converterMock = mock(IConverter.class);
        final ITokenizer tokenizer = new Tokenizer();
        final Evaluator evaluator = new Evaluator(converterMock, tokenizer);
        final AbstractOperator dummyOperator = makeDummyOperator();

        final String expression = "A " + dummyOperator.name + " B";
        final List<Token> infixTokens = Arrays.asList(new Token(TokenType.ATOM, "A"),
                                                      new Token(TokenType.OPERATOR, dummyOperator.name),
                                                      new Token(TokenType.ATOM, "B"));
        final List<Token> postfixTokens = Arrays.asList(new Token(TokenType.ATOM, "A"),
                                                        new Token(TokenType.ATOM, "B"),
                                                        new Token(TokenType.OPERATOR, dummyOperator.name));

        when(converterMock.convert(infixTokens)).thenReturn(postfixTokens);

        evaluator.addOperator(dummyOperator);
        evaluator.evaluate(expression);

        verify(converterMock).convert(infixTokens);
    }

    @Test
    void positiveTest_Evaluate_VerifyTokenizer() {
        final IConverter converter = new Converter();
        final ITokenizer tokenizerMock = mock(ITokenizer.class);
        final Evaluator evaluator = new Evaluator(converter, tokenizerMock);
        final AbstractOperator dummyOperator = makeDummyOperator();

        final String expression = "A " + dummyOperator.name + " B";
        final List<Token> infixTokens = Arrays.asList(new Token(TokenType.ATOM, "A"),
                                                      new Token(TokenType.OPERATOR, dummyOperator.name),
                                                      new Token(TokenType.ATOM, "B"));

        when(tokenizerMock.tokenize(expression)).thenReturn(infixTokens);

        evaluator.addOperator(dummyOperator);
        evaluator.evaluate(expression);

        verify(tokenizerMock).tokenize(expression);
    }

    @Test
    void positiveTest_OneOperator_EvaluationResult() {
        final Evaluator evaluator = new Evaluator();
        evaluator.addStandardFunctions();
        evaluator.addStandardOperators();

        final String result = evaluator.evaluate("A + B");

        assertEquals("AB", result);
    }

    @Test
    void positiveTest_TwoOperator_EvaluationResult() {
        final Evaluator evaluator = new Evaluator();
        evaluator.addStandardFunctions();
        evaluator.addStandardOperators();

        final String result = evaluator.evaluate("A + B + C");

        assertEquals("ABC", result);
    }

    @Test
    void positiveTest_OneFunction_EvaluationResult() {
        final Evaluator evaluator = new Evaluator();
        evaluator.addStandardFunctions();
        evaluator.addStandardOperators();

        final String result = evaluator.evaluate("IF(A < B, 1, 2)");

        assertEquals("1", result);
    }

    @Test
    void positiveTest_NestedFunction_EvaluationResult() {
        final Evaluator evaluator = new Evaluator();
        evaluator.addStandardFunctions();
        evaluator.addStandardOperators();

        final String result = evaluator.evaluate("IF(A > B, 1, REPLACE(ABC, A, E))");

        assertEquals("EBC", result);
    }

    @Test
    void positiveTest_TwoFunctionsOneOperator_EvaluationResult() {
        final Evaluator evaluator = new Evaluator();
        evaluator.addStandardFunctions();
        evaluator.addStandardOperators();

        final String result = evaluator.evaluate("IF(A < B, 1, 2) + IF(A > B, 3, 4)");

        assertEquals("14", result);
    }

    @Test
    void positiveTest_RedundantBrackets_EvaluationResult() {
        final Evaluator evaluator = new Evaluator();
        evaluator.addStandardFunctions();
        evaluator.addStandardOperators();

        final String result = evaluator.evaluate("(((A + B)))");

        assertEquals("AB", result);
    }

    @Test
    void positiveTest_CompareWithNull_EvaluationResult() {
        final Evaluator evaluator = new Evaluator();
        evaluator.addStandardFunctions();
        evaluator.addStandardOperators();

        final String result = evaluator.evaluate("IF(A == NULL, Wrong, Correct)");

        assertEquals("Correct", result);
    }

    @Test
    void positiveTest_NullAsResult_EvaluationResult() {
        final Evaluator evaluator = new Evaluator();
        evaluator.addStandardFunctions();
        evaluator.addStandardOperators();

        final String result = evaluator.evaluate("IF(A == B, Wrong, NULL)");

        assertEquals(null, result);
    }

    @Test
    void negativeTest_AddNullFunction() {
        final Evaluator evaluator = new Evaluator();

        assertThrows(
            NullPointerException.class,
            () -> evaluator.addFunction(null),
            "Expected Evaluator to throw, but it didn't");
    }

    @Test
    void negativeTest_AddNullOperator() {
        final Evaluator evaluator = new Evaluator();

        assertThrows(
            NullPointerException.class,
            () -> evaluator.addOperator(null),
            "Expected Evaluator to throw, but it didn't");
    }

    @Test
    void negativeTest_TwoFunctionsWithTheSameName() {
        final Evaluator evaluator = new Evaluator();
        evaluator.addFunction(makeDummyFunction());

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> evaluator.addFunction(makeDummyFunction()),
            "Expected Evaluator to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("is already added"));
    }

    @Test
    void negativeTest_TwoOperatorsWithTheSameName() {
        final Evaluator evaluator = new Evaluator();
        evaluator.addOperator(makeDummyOperator());

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> evaluator.addOperator(makeDummyOperator()),
            "Expected Evaluator to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("is already added"));
    }

    @Test
    void negativeTest_UnpairedBracket() {
        final Evaluator evaluator = new Evaluator();
        evaluator.addStandardFunctions();
        evaluator.addStandardOperators();

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> evaluator.evaluate("A + (B + C"),
            "Expected Evaluator to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Unpaired bracket"));
    }

    @Test
    void negativeTest_UnexpectedTokenType() {
        final IConverter converterMock = mock(IConverter.class);
        final ITokenizer tokenizer = new Tokenizer();
        final Evaluator evaluator = new Evaluator(converterMock, tokenizer);
        evaluator.addStandardFunctions();
        evaluator.addStandardOperators();

        final List<Token> wrongTokens = Arrays.asList(new Token(TokenType.ATOM, "A"),
                                                      new Token(TokenType.ATOM, "B"),
                                                      new Token(TokenType.LEFT_BRACKET, "("));
        when(converterMock.convert(anyList())).thenReturn(wrongTokens);

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> evaluator.evaluate("A + B"),
            "Expected Evaluator to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Unexpected token type LEFT_BRACKET"));
    }

    @Test
    void negativeTest_UnsupportedOperator() {
        final IConverter converterMock = mock(IConverter.class);
        final ITokenizer tokenizer = new Tokenizer();
        final Evaluator evaluator = new Evaluator(converterMock, tokenizer);
        evaluator.addStandardFunctions();
        evaluator.addStandardOperators();

        final List<Token> wrongTokens = Arrays.asList(new Token(TokenType.ATOM, "A"),
                                                      new Token(TokenType.ATOM, "B"),
                                                      new Token(TokenType.OPERATOR, "<>"));
        when(converterMock.convert(anyList())).thenReturn(wrongTokens);

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> evaluator.evaluate("A + B"),
            "Expected Evaluator to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Unsupported operator <>"));
    }

    @Test
    void negativeTest_UnsupportedFunction() {
        final IConverter converterMock = mock(IConverter.class);
        final ITokenizer tokenizer = new Tokenizer();
        final Evaluator evaluator = new Evaluator(converterMock, tokenizer);
        evaluator.addStandardFunctions();
        evaluator.addStandardOperators();

        final List<Token> wrongTokens = Arrays.asList(new Token(TokenType.ATOM, "A"),
                                                      new Token(TokenType.ATOM, "B"),
                                                      new Token(TokenType.FUNCTION, "FUNC"));
        when(converterMock.convert(anyList())).thenReturn(wrongTokens);

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> evaluator.evaluate("A + B"),
            "Expected Evaluator to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Unsupported function FUNC"));
    }

    @Test
    void negativeTest_FewArguments() {
        final Evaluator evaluator = new Evaluator();
        evaluator.addStandardFunctions();
        evaluator.addStandardOperators();

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> evaluator.evaluate("A + "),
            "Expected Evaluator to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Not enough arguments"));
    }

    @Test
    void negativeTest_FewOperators() {
        final Evaluator evaluator = new Evaluator();
        evaluator.addStandardFunctions();
        evaluator.addStandardOperators();

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> evaluator.evaluate("A + B C"),
            "Expected Evaluator to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Invalid expression"));
    }

    private static AbstractFunction makeDummyFunction() {
        final String dummyName = "Function";
        final int dummyNumberOfArguments = 2;

        return new AbstractFunction(dummyName, dummyNumberOfArguments){
            protected boolean checkArguments() {
                return true;
            }
            protected Object result() {
                return "FunctionResult";
            }
        };
    }

    private static AbstractOperator makeDummyOperator() {
        final String dummyName = "Operator";
        final int dummyPriority = 1;
        final int dummyNumberOfArguments = 2;

        return new AbstractOperator(dummyName, dummyPriority, dummyNumberOfArguments){
            protected boolean checkArguments() {
                return true;
            }
            protected Object result() {
                return "OperatorResult";
            }
        };
    }
}

