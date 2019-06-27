package mzinin.petproject.s2e2.operators;

import mzinin.petproject.s2e2.Operator;


/**
 * Operator <
 * Compares two strings.
 */
public final class OperatorLess extends Operator {

    public OperatorLess() {
        super("<", Priorities.OPERATOR_LESS, 2);
    }

    @Override
    protected boolean checkArguments() {
        return arguments[0] instanceof String &&
                arguments[1] instanceof String;
    }

    @Override
    protected Object result() {
        if (arguments[0] == null || arguments[1] == null) {
            return false;
        }
        return ((String)arguments[0]).compareTo((String)arguments[1]) < 0;
    }
}
