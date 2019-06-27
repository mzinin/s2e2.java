package mzinin.petproject.s2e2.operators;


final class Priorities {

    private Priorities() {
    }

    static final int OPERATOR_AND = 200;
    static final int OPERATOR_EQUAL = 300;
    static final int OPERATOR_GREATER = 400;
    static final int OPERATOR_GREATER_OR_EQUAL = 400;
    static final int OPERATOR_LESS = 400;
    static final int OPERATOR_LESS_OR_EQUAL = 400;
    static final int OPERATOR_NOT = 600;
    static final int OPERATOR_NOT_EQUAL = 300;
    static final int OPERATOR_OR = 100;
    static final int OPERATOR_PLUS = 500;
}