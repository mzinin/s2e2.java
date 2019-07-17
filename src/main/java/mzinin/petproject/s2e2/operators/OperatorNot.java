package mzinin.petproject.s2e2.operators;

import mzinin.petproject.s2e2.AbstractOperator;


/**
 * Operator !
 * Negates boolean value.
 */
public final class OperatorNot extends AbstractOperator {

    public OperatorNot() {
        super("!", Priorities.OPERATOR_NOT, 1);
    }

    @Override
    protected boolean checkArguments() {
        return arguments[0] instanceof Boolean;
    }

    @Override
    protected Object result() {
        return !((Boolean)arguments[0]);
    }
}
