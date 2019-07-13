package mzinin.petproject.s2e2.operators;

import mzinin.petproject.s2e2.ExpressionException;
import mzinin.petproject.s2e2.TestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import java.util.Stack;


class OperatorEqualTests {

    @Test
    void positiveTest_GoodArguments_StackSize() {
        final OperatorEqual operator = new OperatorEqual();
        final Stack<Object> stack = TestUtils.createStack("String1", "String2");

        TestUtils.invoke(operator, stack);

        assertEquals(1, stack.size());
    }

    @Test
    void positiveTest_GoodArguments_ResultType() {
        final OperatorEqual operator = new OperatorEqual();
        final Stack<Object> stack = TestUtils.createStack("String1", "String2");

        TestUtils.invoke(operator, stack);

        assertTrue(stack.peek() instanceof Boolean);
    }

    @Test
    void positiveTest_SameStrings_ResultValue() {
        final OperatorEqual operator = new OperatorEqual();
        final Stack<Object> stack = TestUtils.createStack("String1", "String1");

        TestUtils.invoke(operator, stack);

        assertTrue((Boolean)stack.peek());
    }

    @Test
    void positiveTest_EqualStrings_ResultValue() {
        final OperatorEqual operator = new OperatorEqual();
        final Stack<Object> stack = TestUtils.createStack(new String("String1"), new String("String1"));

        TestUtils.invoke(operator, stack);

        assertTrue((Boolean)stack.peek());
    }

    @Test
    void positiveTest_DifferentStrings_ResultValue() {
        final OperatorEqual operator = new OperatorEqual();
        final Stack<Object> stack = TestUtils.createStack(new String("String1"), new String("String2"));

        TestUtils.invoke(operator, stack);

        assertFalse((Boolean)stack.peek());
    }

    @Test
    void positiveTest_EqualIntegers_ResultValue() {
        final OperatorEqual operator = new OperatorEqual();
        final Stack<Object> stack = TestUtils.createStack(5, 5);

        TestUtils.invoke(operator, stack);

        assertTrue((Boolean)stack.peek());
    }

    @Test
    void positiveTest_DifferentIntegers_ResultValue() {
        final OperatorEqual operator = new OperatorEqual();
        final Stack<Object> stack = TestUtils.createStack(5, 6);

        TestUtils.invoke(operator, stack);

        assertFalse((Boolean)stack.peek());
    }

    @Test
    void positiveTest_DifferentTypes_ResultValue() {
        final OperatorEqual operator = new OperatorEqual();
        final Stack<Object> stack = TestUtils.createStack("5", 5);

        TestUtils.invoke(operator, stack);

        assertFalse((Boolean)stack.peek());
    }

    @Test
    void positiveTest_FirstArgumentNull_ResultValue() {
        final OperatorEqual operator = new OperatorEqual();
        final Stack<Object> stack = TestUtils.createStack(null, "String");

        TestUtils.invoke(operator, stack);

        assertFalse((Boolean)stack.peek());
    }

    @Test
    void positiveTest_SecondArgumentNull_ResultValue() {
        final OperatorEqual operator = new OperatorEqual();
        final Stack<Object> stack = TestUtils.createStack("String", null);

        TestUtils.invoke(operator, stack);

        assertFalse((Boolean)stack.peek());
    }

    @Test
    void positiveTest_BothArgumentsNull_ResultValue() {
        final OperatorEqual operator = new OperatorEqual();
        final Stack<Object> stack = TestUtils.createStack(null, null);

        TestUtils.invoke(operator, stack);

        assertTrue((Boolean)stack.peek());
    }

    @Test
    void positiveTest_MoreArguments_StackSize() {
        final OperatorEqual operator = new OperatorEqual();
        final Stack<Object> stack = TestUtils.createStack("String1", "String2", "String3");

        TestUtils.invoke(operator, stack);

        assertEquals(2, stack.size());
    }

    @Test
    void negativeTest_FewerArguments() {
        final OperatorEqual operator = new OperatorEqual();
        final Stack<Object> stack = TestUtils.createStack("String1");

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(operator, stack),
            "Expected OperatorEqual to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Not enough arguments"));
    }
}
