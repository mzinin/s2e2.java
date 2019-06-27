package mzinin.petproject.s2e2.operators;

import mzinin.petproject.s2e2.Operator;


/**
 * Operator ||
 * Computes disjunction of two boolean values.
 */
public final class OperatorOr extends Operator {

    public OperatorOr() {
        super("||", Priorities.OPERATOR_OR, 2);
    }

    @Override
    protected boolean checkArguments() {
        return arguments[0] instanceof Boolean &&
               arguments[1] instanceof Boolean;
    }

    @Override
    protected Object result() {
        return ((Boolean)arguments[0]) || ((Boolean)arguments[1]);
    }
}
