package mzinin.petproject.s2e2;


/**
 * Token i.e. a unit of some expression.
 */
class Token {

    /**
     * Token type.
     */
    final TokenType type;

    /**
     * Token string value.
     */
    final String value;

    /**
     * Construstor.
     * @param type Type of the token.
     * @param value Value of the token.
     */
    Token(final TokenType type, final String value) {
        this.type = type;
        this.value = value;
    }
    
    /**
     * Compare token with another token.
     * @param another Another token.
     * @return true if tokens are equal, false otherwise.
     */
    @Override
    public boolean equals(Object another) {
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
    private static <T, U> boolean compare(T a, U b) {
        if (a == null) {
            return b == null;
        }
        return a.equals(b);
    }
}