package mzinin.petproject.s2e2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


class EvaluatorTests {

    @Test
    void positiveTest_AddFunction_VerifyTokenizer() {
        final ITokenizer tokenizerMock = mock(ITokenizer.class);
        final Evaluator evaluator = new Evaluator(null, tokenizerMock);
        final Function dummyFunction = makeDummyFunction();

        evaluator.addFunction(dummyFunction);

        verify(tokenizerMock).addFunction(dummyFunction.name);
    }

    @Test
    void positiveTest_AddOperator_VerifyConverter() {
        final IConverter converterMock = mock(IConverter.class);
        final ITokenizer tokenizerMock = mock(ITokenizer.class);
        final Evaluator evaluator = new Evaluator(converterMock, tokenizerMock);
        final Operator dummyOperator = makeDummyOperator();

        evaluator.addOperator(dummyOperator);

        verify(converterMock).addOperator(dummyOperator.name, dummyOperator.priority);
    }

    @Test
    void positiveTest_AddOperator_VerifyTokenizer() {
        final IConverter converterMock = mock(IConverter.class);
        final ITokenizer tokenizerMock = mock(ITokenizer.class);
        final Evaluator evaluator = new Evaluator(converterMock, tokenizerMock);
        final Operator dummyOperator = makeDummyOperator();

        evaluator.addOperator(dummyOperator);

        verify(tokenizerMock).addOperator(dummyOperator.name);
    }

    @Test
    void positiveTest_NothingAdded_SupportedFunctions() {
        final Evaluator evaluator = new Evaluator();

        assertTrue(evaluator.getFunctions().isEmpty());
    }

    @Test
    void positiveTest_NothingAdded_SupportedOperators() {
        final Evaluator evaluator = new Evaluator();

        assertTrue(evaluator.getOperators().isEmpty());
    }

    @Test
    void positiveTest_NothingAdded_EvaluationResult() {
        final Evaluator evaluator = new Evaluator();
        final String inputExpression = "A B C";
        final String evaluatedExpression = evaluator.evaluate(inputExpression);

        assertEquals(inputExpression, evaluatedExpression);
    }

    private static Function makeDummyFunction() {
        final String dummyName = "Function";
        final int dummyNumberOfArguments = 2;

        return new Function(dummyName, dummyNumberOfArguments){
            protected boolean checkArguments() {
                return false;
            }
            protected Object result() {
                return null;
            }
        };
    }

    private static Operator makeDummyOperator() {
        final String dummyName = "Operator";
        final int dummyPriority = 1;
        final int dummyNumberOfArguments = 1;

        return new Operator(dummyName, dummyPriority, dummyNumberOfArguments){
            protected boolean checkArguments() {
                return false;
            }
            protected Object result() {
                return null;
            }
        };
    }
}

