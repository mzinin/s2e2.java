package mzinin.petproject.s2e2.functions;

import mzinin.petproject.s2e2.Function;


/**
 * Function IF(<conition>, <value1>, <value2>)
 * Returns value1 if boolean condition is true, and value2 otherwise.
 */
public final class FunctionIf extends Function {

    public FunctionIf() {
        super("IF", 3);
    }

    @Override
    protected boolean checkArguments() {
        return arguments[0] instanceof Boolean;
    }

    @Override
    protected Object result() {
        return ((Boolean)arguments[0]) ? arguments[1] : arguments[2];
    }
}
