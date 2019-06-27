package mzinin.petproject.s2e2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


class EvaluatorTests {

    @Test
    void noFunctionsNoOperatorsEmptyFunctions() {
        final Evaluator evaluator = new Evaluator();

        assertTrue(evaluator.getFunctions().isEmpty());
    }

    @Test
    void noFunctionsNoOperatorsEmptyOperators() {
        final Evaluator evaluator = new Evaluator();

        assertTrue(evaluator.getOperators().isEmpty());
    }

    @Test
    void noFunctionsNoOperatorsEvaluate() {
        final Evaluator evaluator = new Evaluator();
        final String inputExpression = "A B C";
        final String evaluatedExpression = evaluator.evaluate(inputExpression);

        assertEquals(inputExpression, evaluatedExpression);
    }
}

