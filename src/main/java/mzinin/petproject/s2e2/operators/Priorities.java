package mzinin.petproject.s2e2.operators;


/**
 * Standard operators' priorities.
 */
final class Priorities {

    public static final int OPERATOR_AND = 200;
    public static final int OPERATOR_EQUAL = 300;
    public static final int OPERATOR_GREATER = 400;
    public static final int OPERATOR_GREATER_OR_EQUAL = 400;
    public static final int OPERATOR_LESS = 400;
    public static final int OPERATOR_LESS_OR_EQUAL = 400;
    public static final int OPERATOR_NOT = 600;
    public static final int OPERATOR_NOT_EQUAL = 300;
    public static final int OPERATOR_OR = 100;
    public static final int OPERATOR_PLUS = 500;

    private Priorities() {
    }
}