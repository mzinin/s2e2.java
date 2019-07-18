package mzinin.petproject.s2e2;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


/**
 * Splits plain string expressions into list of tokens.
 */
final class Tokenizer implements ITokenizer {

    // Set of expected functions.
    private final Set<String> functions = new HashSet<>();

    // Set of expected operators.
    private final Set<String> operators = new HashSet<>();

    // Operators sorted by their lengthes (for instance: 1 -> !, +; 2 -> ||, &&)
    private final Map<Integer, Set<String>> operatorsByLength = new TreeMap<>();

    /**
     * Add function expected within expression.
     * @param function Function's name.
     * @throws ExpressionException if function or operator with the same name is already added.
     */
    @Override
    public void addFunction(final String function) {
        checkUniqueness(function);

        functions.add(function);
    }

    /**
     * Add operator expected within expression.
     * @param operator Operator's symbols a.k.a. name.
     * @throws ExpressionException if function or operator with the same name is already added.
     */
    @Override
    public void addOperator(final String operator) {
        checkUniqueness(operator);

        operators.add(operator);
        operatorsByLength.putIfAbsent(operator.length(), new HashSet<>());
        operatorsByLength.get(operator.length()).add(operator);
    }

    /**
     * Split expression into tokens.
     * @param expression Input expression.
     * @return List of tokens.
     */
    @Override
    public List<Token> tokenize(final String expression) {
        final InitialSplitter splitter = new InitialSplitter();
        List<Token> tokens = splitter.splitIntoTokens(expression);
        tokens = splitTokensByOperators(tokens);
        return convertExpressionsIntoAtoms(tokens);
    }

    /**
     * Check is function's or operator's name is unique.
     * @param entityName Function's or operator's name.
     * @throws ExpressionException if the name is not unique.
     */
    private void checkUniqueness(final String entityName) {
        if (functions.contains(entityName)) {
            throw new ExpressionException("Function " + entityName + " is already added");
        }
        if (operators.contains(entityName)) {
            throw new ExpressionException("Operator " + entityName + " is already added");
        }
    }

    /**
     * Get token type by its value.
     * @param value Token's value.
     * @return Token's type.
     */
    /* package */ TokenType tokenTypeByValue(final String value) {
        if (operators.contains(value)) {
            return TokenType.OPERATOR;
        }
        if (functions.contains(value)) {
            return TokenType.FUNCTION;
        }
        return TokenType.EXPRESSION;
    }

    /**
     * Split all tokens by all expected operatos.
     * This is required since there can be no spaces between operator and its operands.
     * @param tokens List of tokens.
     * @return List of splitted tokens.
     */
    private List<Token> splitTokensByOperators(final List<Token> tokens) {
        final List<Integer> operatorLengths = new LinkedList<>(operatorsByLength.keySet());
        List<Token> result = tokens;

        for (int i = operatorLengths.size() - 1; i >= 0; --i) {
            final Integer length = operatorLengths.get(i);
            final Set<String> operatorsOfTheSameLength = operatorsByLength.get(length);
            for (final String operator : operatorsOfTheSameLength) {
                result = splitTokensBySingleOperator(result, operator);
            }
        }

        return result;
    }

    /**
     * Split all tokens by one operator.
     * @param tokens List of tokens.
     * @param operator Operator to split tokens by.
     * @return List of splitted tokens.
     */
    private List<Token> splitTokensBySingleOperator(final List<Token> tokens, final String operator) {
        final List<Token> result = new LinkedList<>();

        for (final Token token : tokens) {
            if (token.type.equals(TokenType.EXPRESSION)) {
                result.addAll(splitSingleTokenBySingleOperator(token.value, operator));
            } else {
                result.add(token);
            }
        }

        return result;
    }

    /**
     * Split one token by one operator.
     * @param token Token to split.
     * @param operator Operator.
     * @return List of splitted tokens.
     */
    private List<Token> splitSingleTokenBySingleOperator(final String token, final String operator) {
        final List<Token> result = new LinkedList<>();

        int start = 0;
        while (start < token.length()) {
            final int end = token.indexOf(operator, start);
            if (end == -1) {
                final String value = token.substring(start);
                result.add(new Token(tokenTypeByValue(value), value));
                break;
            } else {
                if (end != start) {
                    final String value = token.substring(start, end);
                    result.add(new Token(tokenTypeByValue(value), value));
                }
                result.add(new Token(TokenType.OPERATOR, token.substring(end, end + operator.length())));
                start = end + operator.length();
            }
        }

        return result;
    }

    /**
     * Convert all EXPRESSION tokens into ATOM ones.
     * @param tokens List of tokens.
     * @return List of adjusted tokens.
     */
    private static List<Token> convertExpressionsIntoAtoms(final List<Token> tokens) {
        final List<Token> result = new LinkedList<>();

        for (final Token token : tokens) {
            if (token.type.equals(TokenType.EXPRESSION)) {
                result.add(new Token(TokenType.ATOM, token.value));
            } else {
                result.add(token);
            }
        }

        return result;
    }

    /**
     * Class initially splits expression into tokens.
     */
    private final class InitialSplitter {

        // Flag of "inside quotes" state. If set it means that current symbol belongs to an atom.
        private boolean insideQuotes = false;

        // Currently parsing token value.
        private StringBuilder currentToken = new StringBuilder();

        // List of found tokens.
        final private List<Token> tokens = new LinkedList<>();

        // Some special symbols.
        private static final char COMMA = ',';
        private static final char LEFT_BRACKET = '(';
        private static final char RIGHT_BRACKET = ')';
        private static final char QUOTE = '"';
        private static final char BACKSLASH = '\\';

        /**
         * Split expression into tokens by spaces and brackets.
         * @param expression Input expression.
         */
        public List<Token> splitIntoTokens(final String expression) {
            for(int i = 0; i < expression.length(); ++i) {
                processSymbol(expression.charAt(i));
            }
            flushToken();
            return tokens;
        }

        /**
         * Process one symbol of the input expression.
         * @param symbol Symbol of expression.
         */
        private void processSymbol(final char symbol) {
            switch (symbol) {
                case COMMA:
                case LEFT_BRACKET:
                case RIGHT_BRACKET:
                    processSpecialSymbol(symbol);
                    break;

                case QUOTE:
                    processQuoteSymbol(symbol);
                    break;

                default:
                    processCommonSymbol(symbol);
                    break;
            }
        }

        /**
         * Process one special symbol of the input expression.
         * @param symbol Special symbol of expression.
         */
        private void processSpecialSymbol(final char symbol) {
            if (insideQuotes) {
                addSymbolToToken(symbol);
                return;
            }
            
            flushToken();
            switch (symbol) {
                case COMMA:
                    addFoundToken(TokenType.COMMA, String.valueOf(COMMA));
                    break;

                case LEFT_BRACKET:
                    addFoundToken(TokenType.LEFT_BRACKET, String.valueOf(LEFT_BRACKET));
                    break;

                case RIGHT_BRACKET:
                    addFoundToken(TokenType.RIGHT_BRACKET, String.valueOf(RIGHT_BRACKET));
                    break;

                default:
                    throw new ExpressionException("Unexpected special symbol " + symbol);
            }
        }

        /**
         * Process one quote symbol of the input expression.
         * @param symbol Quote symbol of expression.
         */
        private void processQuoteSymbol(final char symbol) {
            if (insideQuotes && isEscaped()) {
                addSymbolToToken(symbol);
                return;
            }
            
            flushToken();
            insideQuotes = !insideQuotes;
        }

        /**
         * Process one common symbol of the input expression.
         * @param symbol Common symbol of expression.
         */
        private void processCommonSymbol(final char symbol) {
            if (insideQuotes || !Character.isWhitespace(symbol)) {
                addSymbolToToken(symbol);
                return;
            }

            flushToken();
        }

        /**
         * Add current token if there is such to the list of found tokens.
         */
        private void flushToken() {
            final String value =  insideQuotes ? currentToken.toString() : currentToken.toString().trim();

            if (!value.isEmpty() || insideQuotes) {
                addFoundToken(tokenTypeByValue(value), value);
            }
            currentToken = new StringBuilder();
        }

        /**
         * Get token type by its value and current state of the splitter.
         * @param value Token's value.
         * @return Token's type.
         */
        private TokenType tokenTypeByValue(final String value) {
            if (insideQuotes) {
                return TokenType.ATOM;
            }
            return Tokenizer.this.tokenTypeByValue(value);
        }

        /**
         * Add token to the list of found tokens.
         * @param type Token's type.
         * @param value Token's value.
         */
        private void addFoundToken(final TokenType type, final String value) {
            tokens.add(new Token(type, value));
        }

        /**
         * Check if current symbol is escaped, i.e. preceded by a backslash.
         * @return true is symbol is escaped, false otherwise.
         */
        private boolean isEscaped() {
            return currentToken.length() > 0 &&
                currentToken.charAt(currentToken.length() - 1) == BACKSLASH;
        }

        /**
         * Add symbol to currently parsed token.
         * @param symbol Symbol to add.
         */
        private void addSymbolToToken(final char symbol) {
            if (symbol == QUOTE) {
                currentToken.setCharAt(currentToken.length() - 1, symbol);
            } else {
                currentToken.append(symbol);
            }
        }
    }
}
