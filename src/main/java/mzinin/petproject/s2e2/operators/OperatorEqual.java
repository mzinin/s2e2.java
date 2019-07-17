package mzinin.petproject.s2e2.operators;

import mzinin.petproject.s2e2.AbstractOperator;


/**
 * Operator ==
 * Compares any two objects.
 */
public final class OperatorEqual extends AbstractOperator {

    public OperatorEqual() {
        super("==", Priorities.OPERATOR_EQUAL, 2);
    }

    @Override
    protected boolean checkArguments() {
        return true;
    }

    @Override
    protected Object result() {
        if (arguments[0] == null) {
            return arguments[1] == null;
        }
        if (arguments[1] == null) {
            return arguments[0] == null;
        }
        return arguments[0].equals(arguments[1]);
    }
}
