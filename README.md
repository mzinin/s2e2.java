# s2e2.java

This library provides Java implementation of Simple String Expression Evaluator a.k.a. `s2e2`. The Evaluator returns value of an input expression. Unlike commonly known mathematical expression evaluators this one treats all parts of the expression as a strings and its output value is also a string.

For example:
* the value of the expression `A + B` is `AB`
* the value of `REPLACE("The cat is black", cat, dog)` is `The dog is black`

This is how one can use Evaluator to get value of some expression:
```java
import mzinin.petproject.s2e2.Evaluator;

final Evaluator evaluator = new Evaluator();

evaluator.addStandardFunctions();
evaluator.addStandardOperators();

final String expression = "A + B";
final String result = evaluator.evaluate(expression);
```

## Supported expressions

Supported expressions consist of the following tokens: string literals, operators (unary and binary), functions, predefined constants, round brackets for function's's arguments denoting, commas for function's arguments separation and double quotes for characters escaping. 

The difference between a function and an operator is that a function is always followed by a pair of round brackets with a list of function's arguments (probably empty) in between, while an operator does not use brackets and, if it is a binary operator, stick between its operands. Also operators can have different priorities a.k.a. precedence.

For example:
* this is a function of 2 arguments: `FUNC(Arg1, Arg2)`
* and this is a binary operator: `Arg1 OP Arg2`


## Constants

There is only one predefined constant - `NULL` - which corresponds to an `null` value in Java. It can be used to check if some sub-expression is evaluated into some result: `IF(SUBEXPR(Arg1, Arg2) == NULL, NULL, Value)`


## Functions

`s2e2` provides a small set of predefined functions. They are:

* Function `IF(Condition, Value1, Value2)`
  
  Returns `Value1` if `Condition` is true, and `Value2` otherwise. `Condition` must be a boolean value.

* Function `REPLACE(Source, Regex, Replacement)`

  Returns copy of `Source` with all matches of `Regex` replaced by `Replacement`. All three arguments are strings, `Regex` cannot be `null` or an empty string, `Replacement` cannot be `null`.

* Function `NOW()`

  Returns current UTC datetime. The result is of `java.time.OffsetDateTime` type.

* Function `ADD_DAYS(Datetime, NumberOfDays)`

  Adds days to the provided datetime. `Datetime` must be of `java.time.OffsetDateTime` type and not `null`. `NumberOfDays` is a not `null `string parsable into an any integer. The result is of `java.time.OffsetDateTime` type.

* Function `FORMAT_DATE(Datetime, Format)`

  Converts `Datetime` into a string according to `Format`. `Datetime` must be of `java.time.OffsetDateTime` type and not `null`. `Format` is a not `null` string.
  

### Custom functions

It is possible to create and use any custom function. Here is a simple example:
```java
import mzinin.petproject.s2e2.AbstractFunction;
import mzinin.petproject.s2e2.Evaluator;

import java.util.HashSet;
import java.util.Set;

final class CustomFunction extends AbstractFunction {

    private final Set<String> set;

    public CustomFunction(final Set<String> set) {
        // CONTAINS - name of the custom function
        // 1 - number of its arguments
        super("CONTAINS", 1);
        this.set = set;
    }

    @Override
    protected boolean checkArguments() {
        return arguments[0] instanceof String;
    }

    @Override
    protected Object result() {
        final String key = (String)arguments[0];
        return set.contains(key);
    }
}

void customOperatorExample() {
    final Evaluator evaluator = new Evaluator();

    evaluator.addStandardFunctions();
    evaluator.addStandardOperators();

    final Set<String> someSet = new HashSet<>();
    someSet.add("key1");
    someSet.add("key2");

    final CustomFunction customFunction = new CustomFunction(someSet);
    evaluator.addFunction(customFunction);

    final String expression = "IF(CONTAINS(key1), YES, NO)";
    final String result = evaluator.evaluate(expression);
}
```

## Operators

As it was mentioned before, every operator has a priority. Within `s2e2` the range of priorities is from 1 to 999. A set of predefined operators is provided. They are:

* Binary operator `+`, priority `500`

  Concatenates two strings. Every operand can be either a `null` or a string. The result is a string.

* Binary operator `==`, priority `300`

  Compares any two objects, including `null`. If both operands are `null` the result is `True`. The type of the result is boolean.

* Binary operator `!=`, priority `300`

  The same as `==`, but checks objects for inequality. 

* Binary operator `>`, priority `400`

  Compares two comparable objects of the same type. None of the operands can be `null`. The result is a boolean.

* Binary operator `>=`, priority `400`

  Compares two comparable objects of the same type. Both operands must be not `null` or both must be `null`. In the latter case the result is `True`.

* Binary operator `<`, priority `400`

  Same as `>`, but checks if first operand is less that the second one.

* Binary operator `<=`, priority `400`

  Same as `>=`, but checks if first operand is less or equal that the second one.

* Binary operator `&&`, priority `200`

  Computes logical conjunction of two boolean values. Both arguments are boolean, not `null` value. The result is a boolean.

* Binary operator `||`, priority `100`

  Computes logical disjunction of two boolean values. Both arguments are boolean, not `null` value. The result is a boolean.

* Unary operator `!`, priority `600`

  Negates boolean value. Operand cannot be `null`. The result is a boolean.


### Custom operators

It is possible to create and use any custom operator. Here is a simple example:
```java
import mzinin.petproject.s2e2.AbstractOperator;
import mzinin.petproject.s2e2.Evaluator;

final class CustomOperator extends AbstractOperator {

    public CustomOperator() {
        // ~ - symbols of the custon operator
        // 600 - priority
        // 1 - number of arguments
        super("~", 600, 1);
    }

    @Override
    protected boolean checkArguments() {
        return arguments[0] instanceof String;
    }

    @Override
    protected Object result() {
        final String arg = (String)arguments[0];
        return new StringBuilder(arg).reverse().toString();
    }
}

void customOperatorExample() {
    final Evaluator evaluator = new Evaluator();

    evaluator.addStandardFunctions();
    evaluator.addStandardOperators();

    final CustomOperator customOperator = new CustomOperator();
    evaluator.addOperator(customOperator);

    final String expression = "~Foo";
    final String result = evaluator.evaluate(expression);
}
```

## Getting Started

### Prerequisites

To compile this project one would need:
* [JDK](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) >= 8
* [Maven](https://maven.apache.org/) >= 3.3.9 (minimal tested version)

The rest of dependencies will be handled by maven.


### Compile library

This will compile the library into a `jar` file and run all unit tests:
```
mvn package
```
The output jar file can be found in the created `target` folder.


### Run tests

This will only compile and run unit tests:
```
mvn test
```

### Run static code analysis

Static code analysis is done by [PMD](https://pmd.github.io/) maven plugin:
```
mvn site
```
If PDM founds some issues the report `./target/site/pmd.html` is created.


## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
