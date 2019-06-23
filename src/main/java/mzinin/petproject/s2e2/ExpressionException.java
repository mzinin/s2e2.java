package mzinin.petproject.s2e2;


/**
 * Class of the exception thrown out of s2e2 package.
 */
public class ExpressionException extends RuntimeException {

    // Required by java.io.Serializable
    private static final long serialVersionUID = 7542235700214183L;

    /**
     * Constructor.
     * @param message Text content of the exception.
     */
    public ExpressionException(final String message) {
        super(message);
    }
}
