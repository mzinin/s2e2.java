package mzinin.petproject.s2e2;


/**
 * Token i.e. a unit of some expression.
 */
final class Token {

    /**
     * Token type.
     */
    public final TokenType type;

    /**
     * Token string value.
     */
    public final String value;

    /**
     * Construstor.
     * @param type Type of the token.
     * @param value Value of the token.
     */
    public Token(final TokenType type, final String value) {
        this.type = type;
        this.value = value;
    }
    
    /**
     * Compare token with another token.
     * @param another Another token.
     * @return true if tokens are equal, false otherwise.
     */
    @Override
    public boolean equals(final Object another) {
        if (this == another) {
            return true;
        }
        if (!(another instanceof Token)) {
            return false;
        }
        final Token anotherToken = (Token)another;

        return compare(type, anotherToken.type) && 
               compare(value, anotherToken.value);
    }

    /**
     * Compare any two objects for equality.
     * @param a First object.
     * @param b Second object.
     * @return true is objects are equal, false otherwise.
     */
    private static <T> boolean compare(final T left, final T right) {
        if (left == null) {
            return right == null;
        }
        return left.equals(right);
    }
}