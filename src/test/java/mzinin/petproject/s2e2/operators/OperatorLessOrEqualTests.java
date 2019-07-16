package mzinin.petproject.s2e2.operators;

import mzinin.petproject.s2e2.ExpressionException;
import mzinin.petproject.s2e2.TestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import java.util.Stack;


class OperatorLessOrEqualTests {

    @Test
    void positiveTest_GoodArguments_StackSize() {
        final OperatorLessOrEqual operator = new OperatorLessOrEqual();
        final Stack<Object> stack = TestUtils.createStack("String1", "String2");

        TestUtils.invoke(operator, stack);

        assertEquals(1, stack.size());
    }

    @Test
    void positiveTest_GoodArguments_ResultType() {
        final OperatorLessOrEqual operator = new OperatorLessOrEqual();
        final Stack<Object> stack = TestUtils.createStack("String1", "String2");

        TestUtils.invoke(operator, stack);

        assertTrue(stack.peek() instanceof Boolean);
    }

    @Test
    void positiveTest_SameStrings_ResultValue() {
        final OperatorLessOrEqual operator = new OperatorLessOrEqual();
        final Stack<Object> stack = TestUtils.createStack("String1", "String1");

        TestUtils.invoke(operator, stack);

        assertTrue((Boolean)stack.peek());
    }

    @Test
    void positiveTest_EqualStrings_ResultValue() {
        final OperatorLessOrEqual operator = new OperatorLessOrEqual();
        final Stack<Object> stack = TestUtils.createStack(new String("String1"), new String("String1"));

        TestUtils.invoke(operator, stack);

        assertTrue((Boolean)stack.peek());
    }

    @Test
    void positiveTest_SameIntegers_ResultValue() {
        final OperatorLessOrEqual operator = new OperatorLessOrEqual();
        final Stack<Object> stack = TestUtils.createStack(5, 5);

        TestUtils.invoke(operator, stack);

        assertTrue((Boolean)stack.peek());
    }

    @Test
    void positiveTest_FirstArgumentLess_ResultValue() {
        final OperatorLessOrEqual operator = new OperatorLessOrEqual();
        final Stack<Object> stack = TestUtils.createStack("String1", "String2");

        TestUtils.invoke(operator, stack);

        assertTrue((Boolean)stack.peek());
    }

    @Test
    void positiveTest_SecondArgumentLess_ResultValue() {
        final OperatorLessOrEqual operator = new OperatorLessOrEqual();
        final Stack<Object> stack = TestUtils.createStack(55, 5);

        TestUtils.invoke(operator, stack);

        assertFalse((Boolean)stack.peek());
    }

    @Test
    void positiveTest_MoreArguments_StackSize() {
        final OperatorLessOrEqual operator = new OperatorLessOrEqual();
        final Stack<Object> stack = TestUtils.createStack("String1", "String2", "String3");

        TestUtils.invoke(operator, stack);

        assertEquals(2, stack.size());
    }

    @Test
    void negativeTest_FewerArguments() {
        final OperatorLessOrEqual operator = new OperatorLessOrEqual();
        final Stack<Object> stack = TestUtils.createStack("String");

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(operator, stack),
            "Expected OperatorLessOrEqual to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Not enough arguments"));
    }

    @Test
    void negativeTest_FirstArgumentNull() {
        final OperatorLessOrEqual operator = new OperatorLessOrEqual();
        final Stack<Object> stack = TestUtils.createStack(null, "String");

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(operator, stack),
            "Expected OperatorLessOrEqual to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Invalid arguments"));
    }

    @Test
    void negativeTest_SecondArgumentNull() {
        final OperatorLessOrEqual operator = new OperatorLessOrEqual();
        final Stack<Object> stack = TestUtils.createStack("String", null);

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(operator, stack),
            "Expected OperatorLessOrEqual to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Invalid arguments"));
    }

    @Test
    void positiveTest_BothArgumentsNull_ResultValue() {
        final OperatorLessOrEqual operator = new OperatorLessOrEqual();
        final Stack<Object> stack = TestUtils.createStack(null, null);

        TestUtils.invoke(operator, stack);

        assertTrue((Boolean)stack.peek());
    }

    @Test
    void negativeTest_ArgumentsOfDifferentTypes() {
        final OperatorLessOrEqual operator = new OperatorLessOrEqual();
        final Stack<Object> stack = TestUtils.createStack(5, "55");

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(operator, stack),
            "Expected OperatorLessOrEqual to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Invalid arguments"));
    }
}
