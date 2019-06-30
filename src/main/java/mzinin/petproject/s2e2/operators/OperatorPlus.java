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
        return (arguments[0] == null || arguments[0] instanceof String) &&
               (arguments[1] == null || arguments[1] instanceof String);
    }

    @Override
    protected Object result() {
        if (arguments[0] == null && arguments[1] == null) {
            return null;
        }

        String result = "";
        if (arguments[0] != null) {
            result += (String)arguments[0];
        }
        if (arguments[1] != null) {
            result += (String)arguments[1];
        }
        return result;
    }
}
