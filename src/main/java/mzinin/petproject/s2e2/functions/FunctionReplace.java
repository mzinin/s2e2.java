package mzinin.petproject.s2e2.functions;

import mzinin.petproject.s2e2.Function;


/**
 * Function REPLACE(<source>, <regex>, <replacement>)
 * Returns copy of source with all matches of regex replaced by replacement.
 */
public final class FunctionReplace extends Function {

    public FunctionReplace() {
        super("REPLACE", 3);
    }

    @Override
    protected boolean checkArguments() {
        return (arguments[0] == null || arguments[0] instanceof String) &&
               arguments[1] instanceof String && !((String)arguments[1]).isEmpty() &&
               arguments[2] instanceof String;
    }

    @Override
    protected Object result() {
        final String source = (String)arguments[0];
        if (source == null) {
            return null;
        }

        final String regex = (String)arguments[1];
        final String replacement = (String)arguments[2];

        return source.replaceAll(regex, replacement);
    }
}
