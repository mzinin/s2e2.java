package mzinin.petproject.s2e2.operators;

import mzinin.petproject.s2e2.AbstractOperator;


/**
 * Operator !=
 * Compares any two objects.
 */
public final class OperatorNotEqual extends AbstractOperator {

    public OperatorNotEqual() {
        super("!=", Priorities.OPERATOR_NOT_EQUAL, 2);
    }

    @Override
    protected boolean checkArguments() {
        return true;
    }

    @Override
    protected Object result() {
        if (arguments[0] == null) {
            return arguments[1] != null;
        }
        if (arguments[1] == null) {
            return arguments[0] != null;
        }
        return !arguments[0].equals(arguments[1]);
    }
}
