package mzinin.petproject.s2e2;

import java.util.List;


/**
 * Interface for converting infix token sequence into postfix one.
 */
interface IConverter {

    /**
     * Add operator expected within expression.
     * @param name Operator's symbols.
     * @param priority Operator's priority.
     */
    void addOperator(final String name, final int priority);

    /**
     * Convert infix token sequence into postfix one.
     * @param infixExpression Input sequence of tokens.
     * @return Postfix sequence of tokens.
     * @throws ExpressionException in case of an error.
     */
    List<Token> convert(final List<Token> infixExpression);
}
