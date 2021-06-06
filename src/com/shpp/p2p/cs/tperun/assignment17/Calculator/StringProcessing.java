package com.shpp.p2p.cs.tperun.assignment17.Calculator;

import com.shpp.p2p.cs.tperun.assignment17.PrivateArrayList;
import com.shpp.p2p.cs.tperun.assignment17.PrivateHashMap;
import com.shpp.p2p.cs.tperun.assignment17.PrivateStack;

/* The class contains methods of working with strings */
public class StringProcessing implements CalculatorConstants {

    private static final MathProcessing mathPr = new MathProcessing();

    /**
     * Parses a string into a list of numbers(operands), operators and variables.
     *
     * @param line The input string to parse.
     * @return The parsed string into elements, also strings.
     */
    public PrivateArrayList<String> parseToFormula(String line) {

        PrivateArrayList<String> parsedFormula = new PrivateArrayList<>();
        StringBuilder buffer = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {

            if (Character.isDigit(line.charAt(i)) || line.charAt(i) == '.'
                    || Character.isLetter(line.charAt(i))) {
                buffer.append(line.charAt(i));
                if (i == line.length() - 1) parsedFormula.add(buffer.toString());
                continue;
            } else {
                if (!buffer.toString().equals("")) {
                    parsedFormula.add(buffer.toString());
                }
                buffer = new StringBuilder();
            }

            if (isMathOperation(Character.toString(line.charAt(i)))) {
                parsedFormula.add(Character.toString(line.charAt(i)));
                continue;
            }
            if (line.charAt(i) == '(' || line.charAt(i) == ')') {
                parsedFormula.add(Character.toString(line.charAt(i)));
            }
        }
        return formulaWithMinus(parsedFormula);
    }

    /**
     * Analyzes the symbols "-" in the list and determines whether they are
     * a mathematical operation or a unary minus, and, based on the results
     * of the analysis, forms a new list for calculate.
     *
     * @param formula The input list.
     * @return The result list.
     */
    public PrivateArrayList<String> formulaWithMinus(PrivateArrayList<String> formula) {

        PrivateArrayList<String> result = new PrivateArrayList<>();
        int start = 0;

        /* If a minus at the beginning of the formula */
        if (formula.get(0).equals("-")) {
            if (Character.isDigit(formula.get(1).charAt(0))) {
                result.add("-".concat(formula.get(1)));
                start += 2;

            } else if (Character.isLetter(formula.get(1).charAt(0)) || formula.get(1).equals("(")) {
                result.add("-1");
                result.add("*");
                result.add(formula.get(1));
                start += 2;
            }
        }

        for (int i = start; i < formula.size(); i++) {

            /* if "(" and following "-" */
            if (formula.get(i).equals("(") && formula.get(i + 1).equals("-")) {
                if (Character.isDigit(formula.get(i + 2).charAt(0))) {
                    result.add(formula.get(i));
                    result.add("-".concat(formula.get(i + 2)));
                    i += 2;
                    continue;
                } else if (Character.isLetter(formula.get(i + 2).charAt(0))) {
                    result.add(formula.get(i));
                    result.add("-1");
                    result.add("*");
                    result.add(formula.get(i + 2));
                    i += 2;
                    continue;
                }
            }

            /* if "-" and following "(" */
            if (i < formula.size() - 2 && isMathOperation(formula.get(i)) && isMathOperation(formula.get(i + 1))
                    && formula.get(i + 2).equals("(")) {
                result.add(formula.get(i));
                result.add("-1");
                result.add("*");
                i++;
                continue;
            }

            /* if math operation, "-" and following variable or function */
            if (i < formula.size() - 2 && isMathOperation(formula.get(i)) && (formula.get(i + 1).equals("-"))
                    && (isFunctions(formula.get(i + 2)) || isAllLetters(formula.get(i + 2)))) {
                result.add(formula.get(i));
                result.add("-1");
                result.add("*");
                i++;
                continue;
            }

            if (i < formula.size() - 2 && (isAllLetters(formula.get(i)) || isDoubleNumber(formula.get(i))) &&
                    formula.get(i + 1).equals("-") &&
                    formula.get(i + 2).equals("(")) {
                result.add(formula.get(i));
                continue;

            }

            /* if "(" , "-" and following "(" other case */
            if (formula.get(i).equals("(") && formula.get(i + 1).equals("-") &&
                    formula.get(i + 2).equals("(")) {
                result.add(formula.get(i));
                result.add("-1");
                result.add("*");
                i++;
                continue;

            }

            /* If two signs of a mathematical operation in a row, and the second one is minus */
            if (isMathOperation(formula.get(i)) && isMathOperation(formula.get(i + 1))
                    && i <= formula.size() - 1) {
                result.add(formula.get(i));
                result.add(formula.get(i + 1).concat(formula.get(i + 2)));
                i += 2;
                continue;
            }
            result.add(formula.get(i));
        }
        return result;
    }

    /**
     * Finds literal variables in an input array of strings and fills in
     * a HashMap, where "key" is the name of the variable and "value" is its value.
     *
     * @param strings The input array of strings.
     * @return The HashMap of pairs "key" - "value".
     */
    public PrivateHashMap<String, String> parseToVariables(String[] strings) {

       PrivateHashMap<String, String> var = new PrivateHashMap<>();
        StringBuilder key = new StringBuilder();
        StringBuilder buffer = new StringBuilder();

        for (int i = 1; i < strings.length; i++) {
            for (int j = 0; j < strings[i].length(); j++) {
                if (Character.isLetter(strings[i].charAt(j))) {
                    key.append(strings[i].charAt(j));
                    continue;
                }
                if (strings[i].charAt(j) == '=') {
                    continue;
                }
                if (Character.isDigit(strings[i].charAt(j)) || strings[i].charAt(j) == '.'
                        || strings[i].charAt(j) == '-') {
                    buffer.append(strings[i].charAt(j));
                }
            }
            var.put(key.toString(), buffer.toString());
            key = new StringBuilder();
            buffer = new StringBuilder();
        }
        return var;
    }

    /**
     * Organizes the formula by the method of reverse Polish notation.
     *
     * @param formula The input formula.
     * @return The organized formula.
     */
    public PrivateArrayList<String> organizeFormula(PrivateArrayList<String> formula) {

        PrivateArrayList<String> outputArray = new PrivateArrayList<>();
        PrivateStack<String> operationStack = new PrivateStack<>();
        operationStack.push("&");

        /* While there are still characters to read: */
        for (int i = 0; i < formula.size(); i++) {

            /*
             Read the next character.
             If the string is a number, add it to the output array.
            */
            if (isDoubleNumber(formula.get(i))) {
                outputArray.add(formula.get(i));
                continue;
            }

            /* If the string is a prefix function, add it to the stack */
            if (isFunctions(formula.get(i))) {
                operationStack.push(formula.get(i));
                continue;
            }

            /* If the string is a left brace, add it to the stack */
            if (formula.get(i).equals("(")) {
                operationStack.push(formula.get(i));
                continue;
            }

            /* If the string is a right brace : */
            if (formula.get(i).equals(")")) {

                /* Until the top stack element becomes. "(" */
                while (!operationStack.peek().equals("(")) {

                    /* Push stack elements into the output array. */
                    outputArray.add(operationStack.pop());
                }

                /* Remove "(" from stack. */
                operationStack.pop();

                /*
                 If there is a function at the top of the stack,
                 push it into the output array.
                */
                if (isFunctions(operationStack.peek())) {
                    if (!operationStack.isEmpty())
                        outputArray.add(operationStack.pop());
                }
                continue;
            }

            /* If the string is a binary operation: */
            if (isMathOperation(formula.get(i))) {

                while (true) {
                    if (isFunctions(operationStack.peek()) || mathPr.mathPriority(formula.get(i))
                            <= mathPr.mathPriority(operationStack.peek())) {
                        outputArray.add(operationStack.pop());
                    } else break;
                }
                operationStack.push(formula.get(i));
            }
        }

        /* Push all stack elements into the output array. */
        while (!operationStack.peek().equals("&")) {
            outputArray.add(operationStack.pop());
        }
        return outputArray;
    }

    /**
     * Calculates the organized formula.
     * <p>
     * For all characters, follow these steps:
     * If Ai is a number, then push it on the result stack;
     * If Ai math operation, then:
     * Pop two numbers from the array stack;
     * Calculate the result and push it in the result stack;
     * If Ai is a function then:
     * Pop one number from the array stack;
     * Determine the value of the function with the appropriate argument and place
     * the result on the result stack;
     * At the end of the work in the stack will be the result of the expression.
     *
     * @param result The organized formula, which is written in a way
     *               of reverse polish notation
     * @return The result of calculates
     */
    public static String calculate(PrivateArrayList<String> result) {

        PrivateStack<String> resultStack = new PrivateStack<>();
        PrivateStack<String> arrayStack = new PrivateStack<>();
        String operator;
        String leftOperand;
        String rightOperand;
        String formula;
        String argument;

        /*
         Place the formula, which is written in the way of reverse polish
         notation, in the stack in reverse order.

        */
        for (int i = 0; i < result.size(); i++) {
            arrayStack.push(result.get(result.size() - 1 - i));
        }

        while (true) {
            if (resultStack.size() == 1) break;
            for (int i = 0; i < result.size(); i++) {
                if (isDoubleNumber(arrayStack.peek())) {
                    resultStack.push(arrayStack.pop());

                } else if (isMathOperation(arrayStack.peek())) {
                    operator = arrayStack.pop();
                    rightOperand = resultStack.pop();
                    leftOperand = resultStack.pop();
                    resultStack.push(mathPr.compute(leftOperand, operator, rightOperand));
                } else if (isFunctions(arrayStack.peek())) {
                    formula = arrayStack.pop();
                    argument = resultStack.pop();
                    resultStack.push(mathPr.computeFunction(formula, argument));
                }
            }
        }
        return resultStack.peek();
    }


    /**
     * Removes the spaces from input string.
     *
     * @param line The input string to remove spaces.
     * @return The output string without spaces.
     */
    public String removeSpaces(String line) {
        StringBuilder buffer = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {

            if (line.charAt(i) != ' ') {
                buffer.append(line.charAt(i));
            }
        }
        return buffer.toString();
    }

    /**
     * Inserts the values of the variables stored in the map into the formula.
     *
     * @param formula The formula in which to insert the values of the variables.
     * @return The formula with inserted variable values.
     */
    public PrivateArrayList<String> insertVariablesToFormula(PrivateArrayList<String> formula,
                                                             PrivateHashMap<String, String> var) {
        PrivateArrayList<String> result = new PrivateArrayList<>();
        for (int i = 0; i < formula.size(); i++) {
            if (var.containsKey(formula.get(i))) {
                result.add(var.get(formula.get(i)));
            } else {
                result.add(formula.get(i));
            }
        }
        return result;
    }

    /**
     * Checks if the string value is a mathematical operation
     * ( +, -, *, / or ^ ).
     *
     * @param expression The input string.
     * @return True or false.
     */
    public static boolean isMathOperation(String expression) {
        for (String operation : MATH_OPERATIONS) {
            if (expression.equals(operation)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the string value is a double number
     *
     * @param str The input string.
     * @return True or false.
     */
    public static boolean isDoubleNumber(String str) {
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException exception) {
            return false;
        }
        return true;
    }

    public static boolean isFunctions(String expression) {
        for (String operation : FUNCTIONS) {
            if (expression.equals(operation)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Inserts the value of the variable  into the formula.
     *
     * @param formula The formula in which to insert the value of the variable.
     * @return The formula with inserted variable value.
     */
    public PrivateArrayList<String> insertValueToFormula(PrivateArrayList<String> formula, double variable) {
        PrivateArrayList<String> result = new PrivateArrayList<>();

        for (int i = 0; i < formula.size(); i++) {
            if (formula.get(i).equals("x")) {
                result.add(Double.toString(variable));
                continue;
            }
            result.add(formula.get(i));
        }
        return result;
    }

    /**
     * Checks if the string value is letters.
     *
     * @param str The input string.
     * @return True or false.
     */
    public boolean isAllLetters(String str) {

        char[] chars = str.toCharArray();
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isLetter(chars[i])) {
                return false;
            }
        }
        return true;
    }
}

