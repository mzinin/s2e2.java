package mzinin.petproject.s2e2;

import java.util.Stack;


public class TestUtils {

    private TestUtils() {
    }

    public static Stack<Object> createStack(final Object ... objects) {
        final Stack<Object> result = new Stack<>();
        for (final Object o : objects) {
            result.push(o);
        }
        return result;
    }

    public static void invoke(final AbstractBaseOperator operator, final Stack<Object> stack) {
        operator.invoke(stack);
    }
}
