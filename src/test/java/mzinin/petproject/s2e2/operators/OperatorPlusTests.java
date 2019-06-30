package mzinin.petproject.s2e2.operators;

import mzinin.petproject.s2e2.ExpressionException;
import mzinin.petproject.s2e2.TestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import java.util.Stack;


class OperatorPlusTests {

    @Test
    void positiveTest_StringString_StackSize() {
        final OperatorPlus operator = new OperatorPlus();
        final Stack<Object> stack = TestUtils.createStack("A", "B");

        TestUtils.invoke(operator, stack);

        assertEquals(1, stack.size());
    }

    @Test
    void positiveTest_StringString_ResultType() {
        final OperatorPlus operator = new OperatorPlus();
        final Stack<Object> stack = TestUtils.createStack("A", "B");

        TestUtils.invoke(operator, stack);

        assertTrue(stack.peek() instanceof String);
    }

    @Test
    void positiveTest_StringString_ResultValue() {
        final OperatorPlus operator = new OperatorPlus();
        final Stack<Object> stack = TestUtils.createStack("A", "B");

        TestUtils.invoke(operator, stack);

        assertEquals("AB", (String)stack.peek());
    }

    @Test
    void positiveTest_StringNull_ResultValue() {
        final OperatorPlus operator = new OperatorPlus();
        final Stack<Object> stack = TestUtils.createStack("A", null);

        TestUtils.invoke(operator, stack);

        assertEquals("A", (String)stack.peek());
    }

    @Test
    void positiveTest_NullString_ResultValue() {
        final OperatorPlus operator = new OperatorPlus();
        final Stack<Object> stack = TestUtils.createStack(null, "B");

        TestUtils.invoke(operator, stack);

        assertEquals("B", (String)stack.peek());
    }

    @Test
    void positiveTest_NullNull_ResultValue() {
        final OperatorPlus operator = new OperatorPlus();
        final Stack<Object> stack = TestUtils.createStack(null, null);

        TestUtils.invoke(operator, stack);

        assertEquals(null, stack.peek());
    }

    @Test
    void positiveTest_MoreArguments_StackSize() {
        final OperatorPlus operator = new OperatorPlus();
        final Stack<Object> stack = TestUtils.createStack("A", "B", "C");

        TestUtils.invoke(operator, stack);

        assertEquals(2, stack.size());
    }

    @Test
    void negativeTest_FewerArguments() {
        final OperatorPlus operator = new OperatorPlus();
        final Stack<Object> stack = TestUtils.createStack(Boolean.TRUE);

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(operator, stack),
            "Expected OperatorPlus to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Not enough arguments"));
    }

    @Test
    void negativeTest_FirstArgumentWrongType() {
        final OperatorPlus operator = new OperatorPlus();
        final Stack<Object> stack = TestUtils.createStack(5, "B");

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(operator, stack),
            "Expected OperatorPlus to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Invalid arguments"));
    }

    @Test
    void negativeTest_SecondArgumentWrongType() {
        final OperatorPlus operator = new OperatorPlus();
        final Stack<Object> stack = TestUtils.createStack("A", 1);

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(operator, stack),
            "Expected OperatorPlus to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Invalid arguments"));
    }
}
