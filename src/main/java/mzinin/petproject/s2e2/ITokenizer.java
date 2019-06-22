package mzinin.petproject.s2e2;

import java.util.List;


/**
 * Interface for splitting expression string onto list of tokens.
 */
interface ITokenizer {

    /**
     * Add function expected within expression.
     * @param function Function's name.
     */
    void addFunction(final String function);

    /**
     * Add operator expected within expression.
     * @param operator Operator's symbols
     */
    void addOperator(final String operator);

    /**
     * Split expression into tokens.
     * @param expression Input expression.
     * @return List of tokens.
     */
    List<Token> tokenize(final String expression);
}
