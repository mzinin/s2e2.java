package mzinin.petproject.s2e2;


/**
 * Base class of all functions.
 */
public abstract class Function extends BaseOperator {

    /**
     * Constructor.
     * @param name Function's name.
     * @param numberOfArguments Number of arguments.
     */
    protected Function(final String name, final int numberOfArguments) {
        super(name, Constants.HIGHEST_PRIORITY, numberOfArguments);
    }
}
