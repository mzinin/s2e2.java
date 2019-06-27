package mzinin.petproject.s2e2.operators;

import mzinin.petproject.s2e2.Operator;


/**
 * Operator !=
 * Compares any two objects.
 */
public final class OperatorNotEqual extends Operator {

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
