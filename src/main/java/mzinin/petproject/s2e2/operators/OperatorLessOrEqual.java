package mzinin.petproject.s2e2.operators;

import mzinin.petproject.s2e2.AbstractOperator;


/**
 * Operator <=
 * Compares two objects.
 */
public final class OperatorLessOrEqual extends AbstractOperator {

    public OperatorLessOrEqual() {
        super("<=", Priorities.OPERATOR_LESS_OR_EQUAL, 2);
    }

    @Override
    protected boolean checkArguments() {
        return arguments[0] == null && arguments[1] == null ||
               arguments[0] instanceof Comparable &&
               arguments[1] instanceof Comparable &&
               arguments[0].getClass().equals(arguments[1].getClass());
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Object result() {
        if (arguments[0] == null) {
            return arguments[1] == null;
        }
        if (arguments[1] == null) {
            return arguments[0] == null;
        }

        final Comparable<Object> arg0 = (Comparable<Object>)arguments[0];
        final Comparable<Object> arg1 = (Comparable<Object>)arguments[1];

        return arg0.compareTo(arg1) <= 0;
    }
}
