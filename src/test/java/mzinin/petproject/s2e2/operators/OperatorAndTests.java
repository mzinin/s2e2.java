package mzinin.petproject.s2e2.operators;

import mzinin.petproject.s2e2.TestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import java.util.Stack;


class OperatorAndTests {

    @Test
    void positiveTest_TrueTrue_StackSize() {
        final OperatorAnd operator = new OperatorAnd();
        final Stack<Object> stack = TestUtils.createStack(Boolean.TRUE, Boolean.TRUE);

        TestUtils.invoke(operator, stack);

        assertEquals(1, stack.size());
    }

    @Test
    void positiveTest_TrueTrue_ResultType() {
        final OperatorAnd operator = new OperatorAnd();
        final Stack<Object> stack = TestUtils.createStack(Boolean.TRUE, Boolean.TRUE);

        TestUtils.invoke(operator, stack);

        assertTrue(stack.peek() instanceof Boolean);
    }

    @Test
    void positiveTest_TrueTrue_ResultValue() {
        final OperatorAnd operator = new OperatorAnd();
        final Stack<Object> stack = TestUtils.createStack(Boolean.TRUE, Boolean.TRUE);

        TestUtils.invoke(operator, stack);

        assertTrue((Boolean)stack.peek());
    }
}

