package mzinin.petproject.s2e2.functions;

import mzinin.petproject.s2e2.TestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import java.util.Stack;



class FunctionIfTests {

    @Test
    void positiveTest_True_StackSize() {
        final FunctionIf function = new FunctionIf();
        final Stack<Object> stack = TestUtils.createStack(Boolean.TRUE, "A", "B");

        TestUtils.invoke(function, stack);

        assertEquals(1, stack.size());
    }

    @Test
    void positiveTest_True_ResultType() {
        final FunctionIf function = new FunctionIf();
        final Stack<Object> stack = TestUtils.createStack(Boolean.TRUE, "A", "B");

        TestUtils.invoke(function, stack);

        assertTrue(stack.peek() instanceof String);
    }

    @Test
    void positiveTest_True_ResultValue() {
        final FunctionIf function = new FunctionIf();
        final Stack<Object> stack = TestUtils.createStack(Boolean.TRUE, "A", "B");

        TestUtils.invoke(function, stack);

        assertEquals("A", stack.peek());
    }
}
