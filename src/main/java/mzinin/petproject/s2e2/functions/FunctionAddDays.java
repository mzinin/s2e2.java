package mzinin.petproject.s2e2.functions;

import mzinin.petproject.s2e2.Function;

import java.time.OffsetDateTime;


/**
 * Function ADD_DAYS(<datetime>, <days>)
 * Adds days number of days to datetime.
 */
public final class FunctionAddDays extends Function {

    public FunctionAddDays() {
        super("ADD_DAYS", 2);
    }

    @Override
    protected boolean checkArguments() {
        if (!(arguments[0] instanceof OffsetDateTime) ||
            !(arguments[1] instanceof String)) {
            return false;
        }

        try {
            Long.parseLong((String)arguments[1]);
        } catch (final NumberFormatException e) {
            return false;
        }
        return true;
    }

    @Override
    protected Object result() {
        final OffsetDateTime datetime = (OffsetDateTime)arguments[0];
        final long days = Long.parseLong((String)arguments[1]);
        return datetime.plusDays(days);
    }
}