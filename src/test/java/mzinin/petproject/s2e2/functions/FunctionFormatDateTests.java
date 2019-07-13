package mzinin.petproject.s2e2.functions;

import mzinin.petproject.s2e2.ExpressionException;
import mzinin.petproject.s2e2.TestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import java.util.Stack;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;


class FunctionFormatDateTests {

    @Test
    void positiveTest_GoodArguments_StackSize() {
        final FunctionFormatDate function = new FunctionFormatDate();
        final Stack<Object> stack = TestUtils.createStack(OffsetDateTime.now(), "yyyy-MM-dd");

        TestUtils.invoke(function, stack);

        assertEquals(1, stack.size());
    }

    @Test
    void positiveTest_GoodArguments_ResultType() {
        final FunctionFormatDate function = new FunctionFormatDate();
        final Stack<Object> stack = TestUtils.createStack(OffsetDateTime.now(), "yyyy-MM-dd");

        TestUtils.invoke(function, stack);

        assertTrue(stack.peek() instanceof String);
    }

    @Test
    void positiveTest_GoodArguments_ResultValue() {
        final FunctionFormatDate function = new FunctionFormatDate();
        final OffsetDateTime firstAgument = OffsetDateTime.of(2019, 7, 13, 12, 15, 0, 0, ZoneOffset.UTC);
        final Stack<Object> stack = TestUtils.createStack(firstAgument, "yyyy-MM-dd");

        TestUtils.invoke(function, stack);

        assertEquals("2019-07-13", stack.peek());
    }

    @Test
    void positiveTest_MoreArguments_StackSize() {
        final FunctionFormatDate function = new FunctionFormatDate();
        final Stack<Object> stack = TestUtils.createStack(Boolean.FALSE, OffsetDateTime.now(), "yyyy-MM-dd");

        TestUtils.invoke(function, stack);

        assertEquals(2, stack.size());
    }

    @Test
    void negativeTest_FewerArguments() {
        final FunctionFormatDate function = new FunctionFormatDate();
        final Stack<Object> stack = TestUtils.createStack(OffsetDateTime.now());

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(function, stack),
            "Expected FunctionFormatDate to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Not enough arguments"));
    }

    @Test
    void negativeTest_FirstArgumentWrongType() {
        final FunctionFormatDate function = new FunctionFormatDate();
        final Stack<Object> stack = TestUtils.createStack("2019-07-13", "yyyy-MM-dd");

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(function, stack),
            "Expected FunctionFormatDate to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Invalid arguments"));
    }

    @Test
    void negativeTest_FirstArgumentNull() {
        final FunctionFormatDate function = new FunctionFormatDate();
        final Stack<Object> stack = TestUtils.createStack(null, "yyyy-MM-dd");

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(function, stack),
            "Expected FunctionFormatDate to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Invalid arguments"));
    }

    @Test
    void negativeTest_SecondArgumentWrongType() {
        final FunctionFormatDate function = new FunctionFormatDate();
        final Stack<Object> stack = TestUtils.createStack(OffsetDateTime.now(), 15);

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(function, stack),
            "Expected FunctionFormatDate to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Invalid arguments"));
    }

    @Test
    void negativeTest_SecondArgumentWrongValue() {
        final FunctionFormatDate function = new FunctionFormatDate();
        final Stack<Object> stack = TestUtils.createStack(OffsetDateTime.now(), "year-month-day");

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(function, stack),
            "Expected FunctionFormatDate to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Invalid arguments"));
    }

    @Test
    void negativeTest_SecondArgumentNull() {
        final FunctionFormatDate function = new FunctionFormatDate();
        final Stack<Object> stack = TestUtils.createStack(OffsetDateTime.now(), null);

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(function, stack),
            "Expected FunctionFormatDate to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Invalid arguments"));
    }
}
