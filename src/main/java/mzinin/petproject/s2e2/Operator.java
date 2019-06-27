package mzinin.petproject.s2e2;


/**
 * Base class of all operators.
 */
public abstract class Operator extends BaseOperator {

    /**
     * Constructor.
     * @param name Operator's name.
     * @param priority Operator's priority.
     * @param numberOfArguments Number of arguments.
     */
    protected Operator(final String name, final int priority, final int numberOfArguments) {
        super(name, priority, numberOfArguments);
    }
}
