package mzinin.petproject.s2e2;

import java.util.Stack;


/**
 * Base class of all functions and operators.
 */
abstract class BaseOperator {

    /** 
     * Name of the operator or function.
     */
    public final String name;

    /**
     * Priority of the operator.
     */ 
    public final int priority;

    /**
     * Array of arguments.
     */ 
    protected Object arguments[];

    /**
     * Constructor.
     * @param name Operator's or function's name.
     * @param priority Operator's priority.
     * @param numberOfArguments Number of arguments.
     */
    protected BaseOperator(final String name, final int priority, final int numberOfArguments) {
        this.name = name;
        this.priority = priority;
        this.arguments = new Object[numberOfArguments];
    }

    /**
     * Invoke the operator - pop all its arguments from the stack and put result in.
     * @param stack Stack with arguments.
     * @throws ExpressionException in case of an error.
     */
    void invoke(final Stack<Object> stack) {
        if (stack.size() < arguments.length) {
            throw new ExpressionException("Not enough arguments for operator " + name);
        }

        for (int i = arguments.length - 1; i >= 0; --i) {
            arguments[i] = stack.pop();
        }

        if (checkArguments()) {
            stack.push(result());
        } else {
            throw new ExpressionException("Invalid arguments for operator " + name);
        }
    }

    /**
     * Check if arguments are correct.
     * @return true is arguments are correct, false otherwise.
     */
    protected abstract boolean checkArguments();

    /**
     * Calculate result of the operator.
     * @return Result.
     */
    protected abstract Object result();
}
