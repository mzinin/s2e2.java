package mzinin.petproject.s2e2;


/**
 * Base class of all functions.
 */
public abstract class AbstractFunction extends AbstractBaseOperator {

    /**
     * Constructor.
     * @param name Function's name.
     * @param numberOfArguments Number of arguments.
     */
    protected AbstractFunction(final String name, final int numberOfArguments) {
        super(name, Constants.HIGHEST_PRIORITY, numberOfArguments);
    }
}
