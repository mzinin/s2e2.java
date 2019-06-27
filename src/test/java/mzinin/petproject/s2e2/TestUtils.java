package mzinin.petproject.s2e2;

import java.util.Stack;


public class TestUtils {

    private TestUtils() {
    }

    public static void invoke(final BaseOperator operator, final Stack<Object> stack) {
        operator.invoke(stack);
    }
}
