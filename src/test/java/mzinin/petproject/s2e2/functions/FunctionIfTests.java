package mzinin.petproject.s2e2.functions;

import mzinin.petproject.s2e2.ExpressionException;
import mzinin.petproject.s2e2.TestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import java.util.Stack;


class FunctionIfTests {

    @Test
    void positiveTest_FirstArgumentTrue_StackSize() {
        final FunctionIf function = new FunctionIf();
        final Stack<Object> stack = TestUtils.createStack(Boolean.TRUE, "A", "B");

        TestUtils.invoke(function, stack);

        assertEquals(1, stack.size());
    }

    @Test
    void positiveTest_FirstArgumentTrue_ResultType() {
        final FunctionIf function = new FunctionIf();
        final Stack<Object> stack = TestUtils.createStack(Boolean.TRUE, "A", "B");

        TestUtils.invoke(function, stack);

        assertTrue(stack.peek() instanceof String);
    }

    @Test
    void positiveTest_FirstArgumentTrue_ResultValue() {
        final FunctionIf function = new FunctionIf();
        final Stack<Object> stack = TestUtils.createStack(Boolean.TRUE, "A", "B");

        TestUtils.invoke(function, stack);

        assertEquals("A", stack.peek());
    }

    @Test
    void positiveTest_FirstArgumentFalse_ResultValue() {
        final FunctionIf function = new FunctionIf();
        final Stack<Object> stack = TestUtils.createStack(Boolean.FALSE, "A", "B");

        TestUtils.invoke(function, stack);

        assertEquals("B", stack.peek());
    }

    @Test
    void positiveTest_MoreArguments_StackSize() {
        final FunctionIf function = new FunctionIf();
        final Stack<Object> stack = TestUtils.createStack("ARG", Boolean.FALSE, "A", "B");

        TestUtils.invoke(function, stack);

        assertEquals(2, stack.size());
    }

    @Test
    void negativeTest_FewerArguments() {
        final FunctionIf function = new FunctionIf();
        final Stack<Object> stack = TestUtils.createStack(Boolean.TRUE);

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(function, stack),
            "Expected FunctionIf to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Not enough arguments"));
    }

    @Test
    void negativeTest_FirstArgumentWrongType() {
        final FunctionIf function = new FunctionIf();
        final Stack<Object> stack = TestUtils.createStack("False", "A", "B");

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(function, stack),
            "Expected FunctionIf to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Invalid arguments"));
    }

    @Test
    void negativeTest_FirstArgumentNull() {
        final FunctionIf function = new FunctionIf();
        final Stack<Object> stack = TestUtils.createStack(null, "A", "B");

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(function, stack),
            "Expected FunctionIf to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Invalid arguments"));
    }

    @Test
    void positiveTest_SecondArgumentNull_ResultValue() {
        final FunctionIf function = new FunctionIf();
        final Stack<Object> stack = TestUtils.createStack(Boolean.TRUE, null, "B");

        TestUtils.invoke(function, stack);

        assertEquals(null, stack.peek());
    }

    @Test
    void positiveTest_ThirdArgumentNull_ResultValue() {
        final FunctionIf function = new FunctionIf();
        final Stack<Object> stack = TestUtils.createStack(Boolean.FALSE, "A", null);

        TestUtils.invoke(function, stack);

        assertEquals(null, stack.peek());
    }
}
