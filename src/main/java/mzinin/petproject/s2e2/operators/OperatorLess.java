package mzinin.petproject.s2e2.operators;

import mzinin.petproject.s2e2.Operator;


/**
 * Operator <
 * Compares two objects.
 */
public final class OperatorLess extends Operator {

    public OperatorLess() {
        super("<", Priorities.OPERATOR_LESS, 2);
    }

    @Override
    protected boolean checkArguments() {
        return arguments[0] instanceof Comparable &&
               arguments[1] instanceof Comparable &&
               arguments[0].getClass().equals(arguments[1].getClass());
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Object result() {
        final Comparable<Object> arg0 = (Comparable<Object>)arguments[0];
        final Comparable<Object> arg1 = (Comparable<Object>)arguments[1];

        return arg0.compareTo(arg1) < 0;
    }
}
