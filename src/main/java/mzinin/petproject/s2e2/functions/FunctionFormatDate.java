package mzinin.petproject.s2e2.functions;

import mzinin.petproject.s2e2.Function;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Function FORMAT_DATE(<datetime>, <format>)
 * Converts datetime to string according to format
 */
public final class FunctionFormatDate extends Function {

    public FunctionFormatDate() {
        super("FORMAT_DATE", 2);
    }

    @Override
    protected boolean checkArguments() {
        return arguments[0] instanceof OffsetDateTime &&
               arguments[1] instanceof String;
    }

    @Override
    protected Object result() {
        final OffsetDateTime datetime = (OffsetDateTime)arguments[0];
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern((String)arguments[1]);
        return datetime.format(formatter);
    }
}
