package mzinin.petproject.s2e2;


/**
 * All token types.
 */
enum TokenType {

    /**
     * String literal, unsplittable.
     */
    ATOM,

    /**
     * Comma, used to separate function arguments.
     */
    COMMA,

    /**
     * Function, always followed by brackets with arguments.
     */
    FUNCTION,

    /**
     * Expression is either an atom or conbination of several tokens.
     * Can be splitted.
     */
    EXPRESSION,

    /**
     * Left, opening round bracket.
     */
    LEFT_BRACKET,

    /**
     * Right, closed round bracket.
     */
    RIGHT_BRACKET,

    /**
     * Infix operator. Unlike functions does not use brackets,
     * can have arguments both before and after itself.
     */
    OPERATOR;
}
