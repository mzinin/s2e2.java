package mzinin.petproject.s2e2.functions;

import mzinin.petproject.s2e2.AbstractFunction;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;


/**
 * Function NOW()
 * Returns current UTC datetime.
 */
public final class FunctionNow extends AbstractFunction {

    public FunctionNow() {
        super("NOW", 0);
    }

    @Override
    protected boolean checkArguments() {
        return true;
    }

    @Override
    protected Object result() {
        return OffsetDateTime.now(ZoneOffset.UTC);
    }
}
