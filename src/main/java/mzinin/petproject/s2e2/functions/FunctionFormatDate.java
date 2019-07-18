package mzinin.petproject.s2e2.functions;

import mzinin.petproject.s2e2.AbstractFunction;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Function FORMAT_DATE(<datetime>, <format>)
 * Converts datetime to string according to format
 */
public final class FunctionFormatDate extends AbstractFunction {

    public FunctionFormatDate() {
        super("FORMAT_DATE", 2);
    }

    @Override
    protected boolean checkArguments() {
        if (!(arguments[0] instanceof OffsetDateTime) ||
            !(arguments[1] instanceof String)) {
            return false;
        }

        try {
            DateTimeFormatter.ofPattern((String)arguments[1]);
        } catch (final IllegalArgumentException e) {
            return false;
        }

        return true;
    }

    @Override
    protected Object result() {
        final OffsetDateTime datetime = (OffsetDateTime)arguments[0];
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern((String)arguments[1]);
        return datetime.format(formatter);
    }
}
