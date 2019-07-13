package mzinin.petproject.s2e2.operators;

import mzinin.petproject.s2e2.Operator;


/**
 * Operator >
 * Compares two strings.
 */
public final class OperatorGreater extends Operator {

    public OperatorGreater() {
        super(">", Priorities.OPERATOR_GREATER, 2);
    }

    @Override
    protected boolean checkArguments() {
        return arguments[0] instanceof String &&
               arguments[1] instanceof String;
    }

    @Override
    protected Object result() {
        return ((String)arguments[0]).compareTo((String)arguments[1]) > 0;
    }
}
