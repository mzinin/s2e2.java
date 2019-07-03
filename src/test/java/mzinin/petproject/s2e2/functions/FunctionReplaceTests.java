package mzinin.petproject.s2e2.functions;

import mzinin.petproject.s2e2.ExpressionException;
import mzinin.petproject.s2e2.TestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import java.util.Stack;


class FunctionReplaceTests {

    @Test
    void positiveTest_StringReplace_StackSize() {
        final FunctionReplace function = new FunctionReplace();
        final Stack<Object> stack = TestUtils.createStack("ABA", "A", "B");

        TestUtils.invoke(function, stack);

        assertEquals(1, stack.size());
    }

    @Test
    void positiveTest_StringReplace_ResultType() {
        final FunctionReplace function = new FunctionReplace();
        final Stack<Object> stack = TestUtils.createStack("ABA", "A", "B");

        TestUtils.invoke(function, stack);

        assertTrue(stack.peek() instanceof String);
    }

    @Test
    void positiveTest_StringReplace_ResultValue() {
        final FunctionReplace function = new FunctionReplace();
        final Stack<Object> stack = TestUtils.createStack("ABA", "A", "B");

        TestUtils.invoke(function, stack);

        assertEquals("BBB", stack.peek());
    }

    @Test
    void positiveTest_RegexReplace_ResultValue() {
        final FunctionReplace function = new FunctionReplace();
        final Stack<Object> stack = TestUtils.createStack("ABCABA", "A.*?C", "D");

        TestUtils.invoke(function, stack);

        assertEquals("DABA", stack.peek());
    }

    @Test
    void positiveTest_SpecialSymbolReplace_ResultValue() {
        final FunctionReplace function = new FunctionReplace();
        final Stack<Object> stack = TestUtils.createStack("A * B == C", "\\*", "+");

        TestUtils.invoke(function, stack);

        assertEquals("A + B == C", stack.peek());
    }

    @Test
    void positiveTest_FirstArgumentNull_ResultValue() {
        final FunctionReplace function = new FunctionReplace();
        final Stack<Object> stack = TestUtils.createStack(null, "A", "B");

        TestUtils.invoke(function, stack);

        assertEquals(null, stack.peek());
    }

    @Test
    void positiveTest_FirstArgumentEmptyString_ResultValue() {
        final FunctionReplace function = new FunctionReplace();
        final Stack<Object> stack = TestUtils.createStack("", "A", "B");

        TestUtils.invoke(function, stack);

        assertEquals("", stack.peek());
    }

    @Test
    void negativeTest_FirstArgument_WrongType() {
        final FunctionReplace function = new FunctionReplace();
        final Stack<Object> stack = TestUtils.createStack(5, "A", "B");

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(function, stack),
            "Expected FunctionReplace to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Invalid arguments"));
    }

    @Test
    void negativeTest_SecondArgument_EmptyString() {
        final FunctionReplace function = new FunctionReplace();
        final Stack<Object> stack = TestUtils.createStack("ABA", "", "B");

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(function, stack),
            "Expected FunctionReplace to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Invalid arguments"));
    }

    @Test
    void negativeTest_SecondArgument_Null() {
        final FunctionReplace function = new FunctionReplace();
        final Stack<Object> stack = TestUtils.createStack("ABA", null, "B");

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(function, stack),
            "Expected FunctionReplace to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Invalid arguments"));
    }

    @Test
    void negativeTest_SecondArgument_WrongType() {
        final FunctionReplace function = new FunctionReplace();
        final Stack<Object> stack = TestUtils.createStack("AB5", 5, "B");

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(function, stack),
            "Expected FunctionReplace to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Invalid arguments"));
    }

    @Test
    void positiveTest_ThirdArgumentEmptyString_ResultValue() {
        final FunctionReplace function = new FunctionReplace();
        final Stack<Object> stack = TestUtils.createStack("ABA", "B", "");

        TestUtils.invoke(function, stack);

        assertEquals("AA", stack.peek());
    }

    @Test
    void negativeTest_ThirdArgument_Null() {
        final FunctionReplace function = new FunctionReplace();
        final Stack<Object> stack = TestUtils.createStack("ABA", "B", null);

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(function, stack),
            "Expected FunctionReplace to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Invalid arguments"));
    }

    @Test
    void negativeTest_ThirdArgument_WrongType() {
        final FunctionReplace function = new FunctionReplace();
        final Stack<Object> stack = TestUtils.createStack("ABA", "B", 5);

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(function, stack),
            "Expected FunctionReplace to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Invalid arguments"));
    }

    @Test
    void positiveTest_MoreArguments_StackSize() {
        final FunctionReplace function = new FunctionReplace();
        final Stack<Object> stack = TestUtils.createStack(Boolean.FALSE, "ABA", "A", "B");

        TestUtils.invoke(function, stack);

        assertEquals(2, stack.size());
    }

    @Test
    void negativeTest_FewerArguments() {
        final FunctionReplace function = new FunctionReplace();
        final Stack<Object> stack = TestUtils.createStack("ABA", "A");

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(function, stack),
            "Expected FunctionReplace to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Not enough arguments"));
    }
}
