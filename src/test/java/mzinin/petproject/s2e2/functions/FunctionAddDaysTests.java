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


class FunctionAddDaysTests {

    private static final long secondsPerDay = 86400;

    @Test
    void positiveTest_GoodArguments_StackSize() {
        final FunctionAddDays function = new FunctionAddDays();
        final Stack<Object> stack = TestUtils.createStack(OffsetDateTime.now(), "1");

        TestUtils.invoke(function, stack);

        assertEquals(1, stack.size());
    }

    @Test
    void positiveTest_GoodArguments_ResultType() {
        final FunctionAddDays function = new FunctionAddDays();
        final Stack<Object> stack = TestUtils.createStack(OffsetDateTime.now(), "1");

        TestUtils.invoke(function, stack);

        assertTrue(stack.peek() instanceof OffsetDateTime);
    }

    @Test
    void positiveTest_SecondArgumentPositive_ResultValue() {
        final FunctionAddDays function = new FunctionAddDays();
        final OffsetDateTime firstAgument = OffsetDateTime.of(2019, 7, 13, 12, 15, 0, 0, ZoneOffset.UTC);
        final Stack<Object> stack = TestUtils.createStack(firstAgument, "1");

        TestUtils.invoke(function, stack);
        final OffsetDateTime functionResult = (OffsetDateTime)stack.peek();

        assertEquals(secondsPerDay, functionResult.toEpochSecond() - firstAgument.toEpochSecond());
    }

    @Test
    void positiveTest_SecondArgumentZero_ResultValue() {
        final FunctionAddDays function = new FunctionAddDays();
        final OffsetDateTime firstAgument = OffsetDateTime.of(2019, 7, 13, 12, 15, 0, 0, ZoneOffset.UTC);
        final Stack<Object> stack = TestUtils.createStack(firstAgument, "0");

        TestUtils.invoke(function, stack);
        final OffsetDateTime functionResult = (OffsetDateTime)stack.peek();

        assertEquals(firstAgument, functionResult);
    }

    @Test
    void positiveTest_SecondArgumentNegative_ResultValue() {
        final FunctionAddDays function = new FunctionAddDays();
        final OffsetDateTime firstAgument = OffsetDateTime.of(2019, 7, 13, 12, 15, 0, 0, ZoneOffset.UTC);
        final Stack<Object> stack = TestUtils.createStack(firstAgument, "-1");

        TestUtils.invoke(function, stack);
        final OffsetDateTime functionResult = (OffsetDateTime)stack.peek();

        assertEquals(secondsPerDay, firstAgument.toEpochSecond() - functionResult.toEpochSecond());
    }

    @Test
    void positiveTest_MoreArguments_StackSize() {
        final FunctionAddDays function = new FunctionAddDays();
        final Stack<Object> stack = TestUtils.createStack("ARG", OffsetDateTime.now(), "1");

        TestUtils.invoke(function, stack);

        assertEquals(2, stack.size());
    }

    @Test
    void negativeTest_FewerArguments() {
        final FunctionAddDays function = new FunctionAddDays();
        final Stack<Object> stack = TestUtils.createStack(OffsetDateTime.now());

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(function, stack),
            "Expected FunctionAddDays to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Not enough arguments"));
    }

    @Test
    void negativeTest_FirstArgumentWrongType() {
        final FunctionAddDays function = new FunctionAddDays();
        final Stack<Object> stack = TestUtils.createStack("2019-07-13 00:00:00", "1");

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(function, stack),
            "Expected FunctionAddDays to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Invalid arguments"));
    }

    @Test
    void negativeTest_FirstArgumentNull() {
        final FunctionAddDays function = new FunctionAddDays();
        final Stack<Object> stack = TestUtils.createStack(null, "1");

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(function, stack),
            "Expected FunctionAddDays to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Invalid arguments"));
    }

    @Test
    void negativeTest_SecondArgumentWrongType() {
        final FunctionAddDays function = new FunctionAddDays();
        final Stack<Object> stack = TestUtils.createStack(OffsetDateTime.now(), 1);

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(function, stack),
            "Expected FunctionAddDays to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Invalid arguments"));
    }

    @Test
    void negativeTest_SecondArgumentWrongValue() {
        final FunctionAddDays function = new FunctionAddDays();
        final Stack<Object> stack = TestUtils.createStack(OffsetDateTime.now(), "A");

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(function, stack),
            "Expected FunctionAddDays to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Invalid arguments"));
    }

    @Test
    void negativeTest_SecondArgumentNull() {
        final FunctionAddDays function = new FunctionAddDays();
        final Stack<Object> stack = TestUtils.createStack(OffsetDateTime.now(), null);

        final ExpressionException thrown = assertThrows(
            ExpressionException.class,
            () -> TestUtils.invoke(function, stack),
            "Expected FunctionAddDays to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Invalid arguments"));
    }
}
