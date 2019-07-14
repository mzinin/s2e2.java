package mzinin.petproject.s2e2.functions;

import mzinin.petproject.s2e2.TestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import java.util.Stack;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;


class FunctionNowTests {

    @Test
    void positiveTest_StackSize() {
        final FunctionNow function = new FunctionNow();
        final Stack<Object> stack = TestUtils.createStack();

        TestUtils.invoke(function, stack);

        assertEquals(1, stack.size());
    }

    @Test
    void positiveTest_ResultType() {
        final FunctionNow function = new FunctionNow();
        final Stack<Object> stack = TestUtils.createStack();

        TestUtils.invoke(function, stack);

        assertTrue(stack.peek() instanceof OffsetDateTime);
    }

    @Test
    void positiveTest_ResultValue() {
        final FunctionNow function = new FunctionNow();
        final Stack<Object> stack = TestUtils.createStack();

        TestUtils.invoke(function, stack);

        final OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        final OffsetDateTime functionResult = (OffsetDateTime)stack.peek();
        final long maxDifferenceInSeconds = 2;

        assertTrue(now.isEqual(functionResult) || now.isAfter(functionResult));
        assertTrue(now.toEpochSecond() - functionResult.toEpochSecond() < maxDifferenceInSeconds);
    }

    @Test
    void positiveTest_MoreArguments_StackSize() {
        final FunctionNow function = new FunctionNow();
        final Stack<Object> stack = TestUtils.createStack(Boolean.FALSE, "A", "B");

        TestUtils.invoke(function, stack);

        assertEquals(4, stack.size());
    }
}
