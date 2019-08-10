package mzinin.petproject.s2e2;

import mzinin.petproject.s2e2.functions.FunctionAddDays;
import mzinin.petproject.s2e2.functions.FunctionFormatDate;
import mzinin.petproject.s2e2.functions.FunctionIf;
import mzinin.petproject.s2e2.functions.FunctionNow;
import mzinin.petproject.s2e2.functions.FunctionReplace;

import mzinin.petproject.s2e2.operators.OperatorAnd;
import mzinin.petproject.s2e2.operators.OperatorEqual;
import mzinin.petproject.s2e2.operators.OperatorGreater;
import mzinin.petproject.s2e2.operators.OperatorGreaterOrEqual;
import mzinin.petproject.s2e2.operators.OperatorLess;
import mzinin.petproject.s2e2.operators.OperatorLessOrEqual;
import mzinin.petproject.s2e2.operators.OperatorNot;
import mzinin.petproject.s2e2.operators.OperatorNotEqual;
import mzinin.petproject.s2e2.operators.OperatorOr;
import mzinin.petproject.s2e2.operators.OperatorPlus;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;


/**
 * Evaluates string values of expressions.
 */
public final class Evaluator {

    /**
     * Set of all supported functions.
     */
    private final Map<String, AbstractFunction> functions = new HashMap<>();

    /**
     * Set of all supported operators.
     */
    private final Map<String, AbstractOperator> operators = new HashMap<>();

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
     * Expected stack size after processing all tokens.
     */
    private static final int FINAL_STACK_SIZE = 1;

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
     * @throws ExpressionException if function or operator with the same name is already added.
     */
    public void addFunction(final AbstractFunction function) {
        Objects.requireNonNull(function);
        checkUniqueness(function.name);

        functions.put(function.name, function);
        tokenizer.addFunction(function.name);
    }

    /**
     * Add operator to set of supported operators.
     * @param operator New supported operator.
     * @throws ExpressionException if function or operator with the same name is already added.
     */
    public void addOperator(final AbstractOperator operator) {
        Objects.requireNonNull(operator);
        checkUniqueness(operator.name);

        operators.put(operator.name, operator);
        converter.addOperator(operator.name, operator.priority);
        tokenizer.addOperator(operator.name);
    }

    /**
     * Add standard functions to set of supported functions.
     * @throws ExpressionException if there is a collision between functions names.
     */
    public void addStandardFunctions() {
        addFunction(new FunctionAddDays());
        addFunction(new FunctionFormatDate());
        addFunction(new FunctionIf());
        addFunction(new FunctionNow());
        addFunction(new FunctionReplace());
    }

    /**
     * Add standard operators to set of supported operators.
     * @throws ExpressionException if there is a collision between operators names.
     */
    public void addStandardOperators() {
        addOperator(new OperatorAnd());
        addOperator(new OperatorEqual());
        addOperator(new OperatorGreater());
        addOperator(new OperatorGreaterOrEqual());
        addOperator(new OperatorLess());
        addOperator(new OperatorLessOrEqual());
        addOperator(new OperatorNot());
        addOperator(new OperatorNotEqual());
        addOperator(new OperatorOr());
        addOperator(new OperatorPlus());
    }

    /**
     * Get unmodifiable collection of all supported functions.
     * @return Collection of functions.
     */
    public Collection<AbstractFunction> getFunctions() {
        return Collections.unmodifiableCollection(functions.values());
    }

    /**
     * Get unmodifiable collection of all supported operators.
     * @return Collection of operators.
     */
    public Collection<AbstractOperator> getOperators() {
        return Collections.unmodifiableCollection(operators.values());
    }

    /**
     * Evaluate the expression.
     * @param expression Input expression.
     * @return Value of expression as a string.
     * @throws ExpressionException in case of an error.
     */
    public String evaluate(final String expression) {
        Objects.requireNonNull(expression);

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
    /* package */ Evaluator(final IConverter converter, final ITokenizer tokenizer) {
        this.converter = converter;
        this.tokenizer = tokenizer;
    }

    /**
     * Check is function's or operator's name is unique.
     * @param entityName Function's or operator's name.
     * @throws ExpressionException if the name is not unique.
     */
    private void checkUniqueness(final String entityName) {
        Objects.requireNonNull(entityName);

        if (functions.containsKey(entityName)) {
            throw new ExpressionException("Function " + entityName + " is already added");
        }
        if (operators.containsKey(entityName)) {
            throw new ExpressionException("Operator " + entityName + " is already added");
        }
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
        final AbstractOperator operator = operators.get(token.value);

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
        final AbstractFunction function = functions.get(token.value);

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
        if (stack.size() != FINAL_STACK_SIZE) {
            throw new ExpressionException("Invalid expression");
        }

        final Object result = stack.pop();
        return result == null ? null : result.toString();
    }
}
