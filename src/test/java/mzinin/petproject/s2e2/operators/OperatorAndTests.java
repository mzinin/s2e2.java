package mzinin.petproject.s2e2.operators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.Stack;

import static mzinin.petproject.s2e2.TestUtils.invoke;


class OperatorAndTests {

    @Test
    void positiveTest_TrueTrue_StackSize() {
        final OperatorAnd operator = new OperatorAnd();
        final Stack<Object> stack = createStack(Boolean.TRUE, Boolean.TRUE);

        invoke(operator, stack);

        assertEquals(1, stack.size());
    }

    @Test
    void positiveTest_TrueTrue_ResultType() {
        final OperatorAnd operator = new OperatorAnd();
        final Stack<Object> stack = createStack(Boolean.TRUE, Boolean.TRUE);

        invoke(operator, stack);

        assertTrue(stack.peek() instanceof Boolean);
    }

    @Test
    void positiveTest_TrueTrue_ResultValue() {
        final OperatorAnd operator = new OperatorAnd();
        final Stack<Object> stack = createStack(Boolean.TRUE, Boolean.TRUE);

        invoke(operator, stack);

        assertTrue((Boolean)stack.peek());
    }

    private Stack<Object> createStack(final Object ... objects) {
        final Stack<Object> result = new Stack<>();

        for (final Object o : objects) {
            result.push(o);
        }

        return result;
    }
}

