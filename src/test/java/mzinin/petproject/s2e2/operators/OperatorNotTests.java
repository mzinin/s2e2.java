package mzinin.petproject.s2e2.operators;

import mzinin.petproject.s2e2.ExpressionException;
import mzinin.petproject.s2e2.TestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import java.util.Stack;


class OperatorNotTests {

    @Test
    void positiveTest_GoorArguments_StackSize() {
        final OperatorNot operator = new OperatorNot();
        final Stack<Object> stack = TestUtils.createStack(Boolean.TRUE);

        TestUtils.invoke(operator, stack);

        assertEquals(1, stack.size());
    }

    @Test
    void positiveTest_GoorArguments_ResultType() {
        final OperatorNot operator = new OperatorNot();
        final Stack<Object> stack = TestUtils.createStack(Boolean.TRUE);

        TestUtils.invoke(operator, stack);

        assertTrue(stack.peek() instanceof Boolean);
    }

    @Test
    void positiveTest_ArgumentTrue_ResultValue() {
        final OperatorNot operator = new OperatorNot();
        final Stack<Object> stack = TestUtils.createStack(Boolean.TRUE);

        TestUtils.invoke(operator, stack);

        assertFalse((Boolean)stack.peek());
    }

    @Test
    void positiveTest_ArgumentFalse_ResultValue() {
        final OperatorNot operator = new OperatorNot();
        final Stack<Object> stack = TestUtils.createStack(Boolean.FALSE);

        TestUtils.invoke(operator, stack);

        assertTrue((Boolean)stack.peek());
    }

    @Test
    void positiveTest_MoreArguments_StackSize() {
        final OperatorNot operator = new OperatorNot();
        final Stack<Object> stack = TestUtils.createStack(Boolean.FALSE, Boolean.TRUE);

        TestUtils.invoke(operator, stack);

        assertEquals(2, stack.size());
    }

    @Test
    void negativeTest_FewerArguments() {
        final OperatorNot operator = new OperatorNot();
        final Stack<Object> stack = TestUtils.createStack();

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(operator, stack),
            "Expected OperatorNot to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Not enough arguments"));
    }

    @Test
    void negativeTest_ArgumentWrongType() {
        final OperatorNot operator = new OperatorNot();
        final Stack<Object> stack = TestUtils.createStack("True");

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(operator, stack),
            "Expected OperatorNot to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Invalid arguments"));
    }

    @Test
    void negativeTest_ArgumentNull() {
        final OperatorNot operator = new OperatorNot();
        final Stack<Object> stack = TestUtils.createStack((Object)null);

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(operator, stack),
            "Expected OperatorNot to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Invalid arguments"));
    }
}
