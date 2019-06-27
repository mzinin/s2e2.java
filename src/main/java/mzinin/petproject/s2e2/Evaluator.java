package mzinin.petproject.s2e2;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Evaluates string values of expressions.
 */
public final class Evaluator {

    /**
     * Set of all supported functions.
     */
    private final Map<String, Function> functions = new HashMap<String, Function>();

    /**
     * Set of all supported operators.
     */
    private final Map<String, Operator> operators = new HashMap<String, Operator>();

    /**
     * Converter of infix token sequence into postfix one.
     */
    private final IConverter converter;

    /**
     * Tokenizer of expression onto list of tokens.
     */
    private final ITokenizer tokenizer;

    /**
     * Stack of intermediate values.
     */
    private Stack<Object> stack;

    /**
     * Default constructor.
     */
    public Evaluator() {
        this.converter = new Converter();
        this.tokenizer = new Tokenizer();
    }

    /**
     * Add function to set of supported functions.
     * @param function New supported function.
     * @throws ExpressionException if function with the same name is already added.
     */
    public void addFunction(final Function function) {
        if (functions.containsKey(function.name)) {
            throw new ExpressionException("Function " + function.name + " is already added");
        }

        functions.put(function.name, function);
        tokenizer.addFunction(function.name);
    }

    /**
     * Add operator to set of supported operators.
     * @param operator New supported operator.
     * @throws ExpressionException if operator with the same name is already added.
     */
    public void addOperator(final Operator operator) {
        if (operators.containsKey(operator.name)) {
            throw new ExpressionException("Operator " + operator.name + " is already added");
        }

        operators.put(operator.name, operator);
        converter.addOperator(operator.name, operator.priority);
        tokenizer.addOperator(operator.name);
    }

    /**
     * Get unmodifiable collection of all supported functions.
     * @return Collection of functions.
     */
    public Collection<Function> getFunctions() {
        return Collections.unmodifiableCollection(functions.values());
    }

    /**
     * Get unmodifiable collection of all supported operators.
     * @return Collection of operators.
     */
    public Collection<Operator> getOperators() {
        return Collections.unmodifiableCollection(operators.values());
    }

    /**
     * Evaluate the expression.
     * @param expression Input expression.
     * @return Value of expression as a string.
     * @throws ExpressionException in case of an error.
     */
    public String evaluate(final String expression) {
        final List<Token> infixExpression = tokenizer.tokenize(expression);

        // a bit of syntax sugar: if expression contains only atoms
        // consider it as just a string literal
        if (infixExpression.stream().allMatch(token -> token.type.equals(TokenType.ATOM))) {
            return expression;
        }

        final List<Token> postfixExpression = converter.convert(infixExpression);
        return evaluateExpression(postfixExpression);
    }

    /**
     * Constructor for unit testing.
     * @param converter External IConverter object.
     * @param tokenizer External ITokenizer object.
     */
    Evaluator(final IConverter converter, final ITokenizer tokenizer) {
        this.converter = converter;
        this.tokenizer = tokenizer;
    }

    /**
     * Get value of the postfix sequence of tokens.
     * @param postfixExpression Sequence of tokens.
     * @return String value.
     * @throws ExpressionException in case of an error.
     */
    private String evaluateExpression(final List<Token> postfixExpression) {
        stack = new Stack<>();

        for (final Token token : postfixExpression) {
            switch (token.type) {
                case ATOM:
                    processAtom(token);
                    break;

                case OPERATOR:
                    processOperator(token);
                    break;

                case FUNCTION:
                    processFunction(token);
                    break;

                default:
                    throw new ExpressionException("Unexpected token type " + token.type.name());
            }
        }

        final String result = getResultValueFromStack();
        stack = null;
        return result;
    }

    /**
     * Process ATOM token.
     * @param token ATOM token.
     */
    private void processAtom(final Token token) {
        final String value = token.value.equals(Constants.NULL_VALUE) ? null : token.value;
        stack.push(value);
    }

    /**
     * Process OPERATOR token.
     * @param token OPERATOR token.
     * @throws ExpressionException in case of an error.
     */
    private void processOperator(final Token token) {
        final Operator operator = operators.get(token.value);

        if (operator == null) {
            throw new ExpressionException("Unsupported operator " + token.value);
        }

        operator.invoke(stack);
    }

    /**
     * Process FUNCTION token.
     * @param token FUNCTION token.
     * @throws ExpressionException in case of an error.
     */
    private void processFunction(final Token token) {
        final Function function = functions.get(token.value);

        if (function == null) {
            throw new ExpressionException("Unsupported function " + token.value);
        }

        function.invoke(stack);
    }

    /**
     * Get result value from the stack of intermediate values.
     * @return String value.
     * @throws ExpressionException in case of an error.
     */
    private String getResultValueFromStack() {
        if (stack.size() != 1) {
            throw new ExpressionException("Invalid expression");
        }

        return stack.pop().toString();
    }
}
