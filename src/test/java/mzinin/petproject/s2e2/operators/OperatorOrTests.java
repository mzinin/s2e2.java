package mzinin.petproject.s2e2.operators;

import mzinin.petproject.s2e2.ExpressionException;
import mzinin.petproject.s2e2.TestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import java.util.Stack;


class OperatorOrTests {

    @Test
    void positiveTest_GoorArguments_StackSize() {
        final OperatorOr operator = new OperatorOr();
        final Stack<Object> stack = TestUtils.createStack(Boolean.TRUE, Boolean.TRUE);

        TestUtils.invoke(operator, stack);

        assertEquals(1, stack.size());
    }

    @Test
    void positiveTest_GoorArguments_ResultType() {
        final OperatorOr operator = new OperatorOr();
        final Stack<Object> stack = TestUtils.createStack(Boolean.TRUE, Boolean.TRUE);

        TestUtils.invoke(operator, stack);

        assertTrue(stack.peek() instanceof Boolean);
    }

    @Test
    void positiveTest_TrueTrue_ResultValue() {
        final OperatorOr operator = new OperatorOr();
        final Stack<Object> stack = TestUtils.createStack(Boolean.TRUE, Boolean.TRUE);

        TestUtils.invoke(operator, stack);

        assertTrue((Boolean)stack.peek());
    }

    @Test
    void positiveTest_TrueFalse_ResultValue() {
        final OperatorOr operator = new OperatorOr();
        final Stack<Object> stack = TestUtils.createStack(Boolean.TRUE, Boolean.FALSE);

        TestUtils.invoke(operator, stack);

        assertTrue((Boolean)stack.peek());
    }

    @Test
    void positiveTest_FalseTrue_ResultValue() {
        final OperatorOr operator = new OperatorOr();
        final Stack<Object> stack = TestUtils.createStack(Boolean.FALSE, Boolean.TRUE);

        TestUtils.invoke(operator, stack);

        assertTrue((Boolean)stack.peek());
    }

    @Test
    void positiveTest_FalseFalse_ResultValue() {
        final OperatorOr operator = new OperatorOr();
        final Stack<Object> stack = TestUtils.createStack(Boolean.FALSE, Boolean.FALSE);

        TestUtils.invoke(operator, stack);

        assertFalse((Boolean)stack.peek());
    }

    @Test
    void positiveTest_MoreArguments_StackSize() {
        final OperatorOr operator = new OperatorOr();
        final Stack<Object> stack = TestUtils.createStack("ARG", Boolean.FALSE, Boolean.TRUE);

        TestUtils.invoke(operator, stack);

        assertEquals(2, stack.size());
    }

    @Test
    void negativeTest_FewerArguments() {
        final OperatorOr operator = new OperatorOr();
        final Stack<Object> stack = TestUtils.createStack(Boolean.TRUE);

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(operator, stack),
            "Expected OperatorOr to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Not enough arguments"));
    }

    @Test
    void negativeTest_FirstArgumentWrongType() {
        final OperatorOr operator = new OperatorOr();
        final Stack<Object> stack = TestUtils.createStack("True", Boolean.TRUE);

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(operator, stack),
            "Expected OperatorOr to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Invalid arguments"));
    }

    @Test
    void negativeTest_FirstArgumentNull() {
        final OperatorOr operator = new OperatorOr();
        final Stack<Object> stack = TestUtils.createStack(null, Boolean.TRUE);

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(operator, stack),
            "Expected OperatorOr to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Invalid arguments"));
    }

    @Test
    void negativeTest_SecondArgumentWrongType() {
        final OperatorOr operator = new OperatorOr();
        final Stack<Object> stack = TestUtils.createStack(Boolean.TRUE, "True");

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(operator, stack),
            "Expected OperatorOr to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Invalid arguments"));
    }

    @Test
    void negativeTest_SecondArgumentNull() {
        final OperatorOr operator = new OperatorOr();
        final Stack<Object> stack = TestUtils.createStack(Boolean.TRUE, null);

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(operator, stack),
            "Expected OperatorOr to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Invalid arguments"));
    }
}

