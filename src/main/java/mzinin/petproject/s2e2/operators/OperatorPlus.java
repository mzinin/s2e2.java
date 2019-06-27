package mzinin.petproject.s2e2.operators;

import mzinin.petproject.s2e2.Operator;


/**
 * Operator +
 * Concatenates two strings.
 */
public final class OperatorPlus extends Operator {

    public OperatorPlus() {
        super("+", Priorities.OPERATOR_PLUS, 2);
    }

    @Override
    protected boolean checkArguments() {
        return arguments[0] instanceof String &&
               arguments[1] instanceof String;
    }

    @Override
    protected Object result() {
        return (String)arguments[0] + (String)arguments[1];
    }
}
