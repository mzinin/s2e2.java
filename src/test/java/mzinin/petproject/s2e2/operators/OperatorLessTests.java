package mzinin.petproject.s2e2.operators;

import mzinin.petproject.s2e2.ExpressionException;
import mzinin.petproject.s2e2.TestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import java.util.Stack;


class OperatorLessTests {

    @Test
    void positiveTest_GoodArguments_StackSize() {
        final OperatorLess operator = new OperatorLess();
        final Stack<Object> stack = TestUtils.createStack("String1", "String2");

        TestUtils.invoke(operator, stack);

        assertEquals(1, stack.size());
    }

    @Test
    void positiveTest_GoodArguments_ResultType() {
        final OperatorLess operator = new OperatorLess();
        final Stack<Object> stack = TestUtils.createStack("String1", "String2");

        TestUtils.invoke(operator, stack);

        assertTrue(stack.peek() instanceof Boolean);
    }

    @Test
    void positiveTest_SameArguments_ResultValue() {
        final OperatorLess operator = new OperatorLess();
        final Stack<Object> stack = TestUtils.createStack("String1", "String1");

        TestUtils.invoke(operator, stack);

        assertFalse((Boolean)stack.peek());
    }

    @Test
    void positiveTest_EqualArguments_ResultValue() {
        final OperatorLess operator = new OperatorLess();
        final Stack<Object> stack = TestUtils.createStack(new String("String1"), new String("String1"));

        TestUtils.invoke(operator, stack);

        assertFalse((Boolean)stack.peek());
    }

    @Test
    void positiveTest_FirstArgumentLess_ResultValue() {
        final OperatorLess operator = new OperatorLess();
        final Stack<Object> stack = TestUtils.createStack("String1", "String2");

        TestUtils.invoke(operator, stack);

        assertTrue((Boolean)stack.peek());
    }

    @Test
    void positiveTest_SecondArgumentLess_ResultValue() {
        final OperatorLess operator = new OperatorLess();
        final Stack<Object> stack = TestUtils.createStack("String2", "String1");

        TestUtils.invoke(operator, stack);

        assertFalse((Boolean)stack.peek());
    }

    @Test
    void positiveTest_MoreArguments_StackSize() {
        final OperatorLess operator = new OperatorLess();
        final Stack<Object> stack = TestUtils.createStack("String1", "String2", "String3");

        TestUtils.invoke(operator, stack);

        assertEquals(2, stack.size());
    }

    @Test
    void negativeTest_FewerArguments() {
        final OperatorLess operator = new OperatorLess();
        final Stack<Object> stack = TestUtils.createStack("String");

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(operator, stack),
            "Expected OperatorLess to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Not enough arguments"));
    }

    @Test
    void negativeTest_FirstArgumentNull() {
        final OperatorLess operator = new OperatorLess();
        final Stack<Object> stack = TestUtils.createStack(null, "String");

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(operator, stack),
            "Expected OperatorLess to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Invalid arguments"));
    }

    @Test
    void negativeTest_SecondArgumentNull() {
        final OperatorLess operator = new OperatorLess();
        final Stack<Object> stack = TestUtils.createStack("String", null);

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(operator, stack),
            "Expected OperatorLess to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Invalid arguments"));
    }

    @Test
    void negativeTest_FirstArgumentWrongType() {
        final OperatorLess operator = new OperatorLess();
        final Stack<Object> stack = TestUtils.createStack(5, "55");

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(operator, stack),
            "Expected OperatorLess to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Invalid arguments"));
    }

    @Test
    void negativeTest_SecondArgumentWrongType() {
        final OperatorLess operator = new OperatorLess();
        final Stack<Object> stack = TestUtils.createStack("55", 5);

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(operator, stack),
            "Expected OperatorLess to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Invalid arguments"));
    }
}
