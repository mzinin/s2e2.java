package mzinin.petproject.s2e2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;


/**
 * Converts infix token sequence into postfix one.
 * Convertion is done by Shunting Yard algorithm.
 */
final class Converter implements IConverter {

    /**
     * Output queue of all tokens.
     */
    private List<Token> outputQueue = null;

    /**
     * Stack of operators and functions.
     */
    private Stack<Token> operatorStack = null;

    /**
     * All expected operators and their priorities (precedences).
     */
    private final Map<String, Integer> operators = new HashMap<>();

    /**
     * Add operator expected within expression.
     * @param name Operator's symbols.
     * @param priority Operator's priority.
     */
    @Override
    public void addOperator(final String name, final int priority) {
        Objects.requireNonNull(name);

        if (operators.containsKey(name)) {
            throw new ExpressionException("Operator " + name + " is already added");
        }

        operators.put(name, priority);
    }

    /**
     * Convert infox token sequence into postfix one.
     * @param infixExpression Input sequence of tokens.
     * @return Postfix sequence of tokens.
     * @throws ExpressionException in case of an error.
     */
    @Override
    public List<Token> convert(final List<Token> infixExpression) {
        Objects.requireNonNull(infixExpression);

        outputQueue = new LinkedList<>();
        operatorStack = new Stack<>();

        processTokens(infixExpression);
        processOperators();

        final List<Token> result = outputQueue;
        outputQueue = null;
        return result;
    }

    /**
     * Process all tokens in the input sequence.
     * @param expression Token sequence.
     * @throws ExpressionException in case of an error.
     */
    private void processTokens(final List<Token> expression) {
        for (final Token token : expression) {
            switch (token.type) {
                case ATOM:
                    processAtom(token);
                    break;

                case COMMA:
                    processComma();
                    break;

                case FUNCTION:
                    processFunction(token);
                    break;

                case OPERATOR:
                    processOperator(token);
                    break;

                case LEFT_BRACKET:
                    processLeftBracket(token);
                    break;

                case RIGHT_BRACKET:
                    processRightBracket();
                    break;

                default:
                    throw new ExpressionException("Unexpected token type " + token.type.name());
            }
        }
    }

    /**
     * Process all operators left in the operator stack.
     * @throws ExpressionException in case of an error.
     */
    private void processOperators() {
        while (!operatorStack.isEmpty()) {
            final Token token = operatorStack.pop();

            if (token.type.equals(TokenType.LEFT_BRACKET)) {
                throw new ExpressionException("Unpaired bracket");
            }

            outputQueue.add(token);
        }
    } 

    /**
     * Process ATOM token.
     * @param token Token.
     */
    private void processAtom(final Token token) {
        outputQueue.add(token);
    }

    /**
     * Process COMMA token.
     */
    private void processComma() {
        while (!operatorStack.isEmpty() &&
               !operatorStack.peek().type.equals(TokenType.LEFT_BRACKET)) {
            outputQueue.add(operatorStack.pop());
        }
    }

    /**
     * Process FUNCTION token.
     * @param token Token.
     */
    private void processFunction(final Token token) {
        operatorStack.push(token);
    }

    /**
     * Process OPERATOR token.
     * @param token Token.
     * @throws ExpressionException in case of an unknown operator.
     */
    private void processOperator(final Token token) {
        final Integer priority = operators.get(token.value);
        if (priority == null) {
            throw new ExpressionException("Unknown operator " + token.value);
        }

        while (!operatorStack.isEmpty() &&
               operatorStack.peek().type.equals(TokenType.OPERATOR) &&
               priority <= operators.get(operatorStack.peek().value)) {
            outputQueue.add(operatorStack.pop());
        }

        operatorStack.push(token);
    }

    /**
     * Process LEFT BRACKET token.
     * @param token Token.
     */
    private void processLeftBracket(final Token token) {
        operatorStack.push(token);
    }

    /**
     * Process RIGHT BRACKET token.
     * @throws ExpressionException in case of an error.
     */
    private void processRightBracket() {
        while (!operatorStack.isEmpty() &&
               !operatorStack.peek().type.equals(TokenType.LEFT_BRACKET)) {
            outputQueue.add(operatorStack.pop());
        }

        if (operatorStack.isEmpty()) {
            throw new ExpressionException("Unpaired bracket");
        }
        operatorStack.pop();

        if (!operatorStack.isEmpty() &&
            operatorStack.peek().type.equals(TokenType.FUNCTION)) {
            outputQueue.add(operatorStack.pop());
        }
    }
}
