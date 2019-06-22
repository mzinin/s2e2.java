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
class Tokenizer implements ITokenizer {

    // Flag of "inside quotes" state. If set it means that current symbol belongs to an atom.
    private boolean insideQuotes = false;

    // List of found tokens.
    private List<Token> tokens = null;

    // Currently parsing token value.
    private StringBuilder currentToken = null;

    // Set of expected functions.
    private Set<String> functions = new HashSet<>();

    // Set of expected operators.
    private Set<String> operators = new HashSet<>();

    // Operators sorted by their lengthes (for instance: 1 -> !, +; 2 -> ||, &&)
    private Map<Integer, Set<String>> operatorsByLength = new TreeMap<>();

    // Some special symbols.
    private static final char COMMA = ',';
    private static final char LEFT_BRACKET = '(';
    private static final char RIGHT_BRACKET = ')';
    private static final char QUOTE = '"';
    private static final char BACKSLASH = '\\';

    /**
     * Add function expected within expression.
     * @param function Function's name.
     */
    @Override
    public void addFunction(final String function) {
        functions.add(function);
    }

    /**
     * Add operator expected within expression.
     * @param operator Operator's symbols
     */
    @Override
    public void addOperator(final String operator) {
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
        insideQuotes = false;
        tokens = new LinkedList<>();
        currentToken = new StringBuilder();

        splitIntoTokens(expression);
        splitTokensByOperators();
        convertExpressionsIntoAtoms();

        final List<Token> result = tokens;
        tokens = null;
        return result;
    }

    /**
     * Split expression into tokens by spaces and brackets.
     * @param expression Input expression.
     */
    private void splitIntoTokens(final String expression) {
        for(int i = 0; i < expression.length(); ++i) {
            processSymbol(expression.charAt(i));
        }
        flushToken();
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
        if (insideQuotes || !isWhitespace(symbol)) {
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
     * Get token type by its value and current state of the tokenizer.
     * @param value Token's value.
     * @return Token's type.
     */
    private TokenType tokenTypeByValue(final String value) {
        if (insideQuotes) {
            return TokenType.ATOM;
        }
        if (operators.contains(value)) {
            return TokenType.OPERATOR;
        }
        if (functions.contains(value)) {
            return TokenType.FUNCTION;
        }
        return TokenType.EXPRESSION;
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
     * Check if symbol is a white space.
     * @param symbol Symbol to check.
     * @return true is symbol is a white space, false otherwise.
     */
    private boolean isWhitespace(final char symbol) {
        return Character.isWhitespace(symbol);
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

    /**
     * Split all found tokens by all expected operatos.
     * This is required since there can be no spaces between operator and its operands.
     */
    private void splitTokensByOperators() {
        final List<Integer> operatorLengths = new LinkedList<>(operatorsByLength.keySet());

        for (int i = operatorLengths.size() - 1; i >= 0; --i) {
            final Integer length = operatorLengths.get(i);
            final Set<String> operatorsOfTheSameLength = operatorsByLength.get(length);
            for (final String operator : operatorsOfTheSameLength) {
                tokens = splitTokensBySingleOperator(operator);
            }
        }
    }

    /**
     * Split all found tokens by one operator.
     * @param operator Operator to split tokens by.
     * @return New list of found tokens.
     */
    private List<Token> splitTokensBySingleOperator(final String operator) {
        final List<Token> result = new LinkedList<>();

        for (final Token token : tokens) {
            if (!token.type.equals(TokenType.EXPRESSION)) {
                result.add(token);
            } else {
                result.addAll(splitSingleTokenBySingleOperator(token.value, operator));
            }
        }

        return result;
    }

    /**
     * Split one token by one operator.
     * @param token Token to split.
     * @param operator Operator.
     * @return List of found tokens.
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
     * Convert all found EXPRESSION tokens into ATOM ones.
     */
    private void convertExpressionsIntoAtoms() {
        final List<Token> tmpTokens = new LinkedList<>();

        for (final Token token : tokens) {
            if (!token.type.equals(TokenType.EXPRESSION)) {
                tmpTokens.add(token);
            } else {
                tmpTokens.add(new Token(TokenType.ATOM, token.value));
            }
        }

        tokens = tmpTokens;
    }
}
