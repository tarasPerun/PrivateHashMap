package com.shpp.p2p.cs.tperun.assignment17.Calculator;

/* The class contains the mathematical methods of working with strings */
public class MathProcessing implements CalculatorConstants {

    /**
     * Determines the priority of mathematical operations.
     *
     * @param operation The math operation.
     * @return Fn integer from 0 to 2, where 0 is the lowest priority,
     * 2 is the highest, -1 if the operation is wrong.
     */
    public int mathPriority(String operation) {

        if (operation.equals("^")) return 2;
        if (operation.equals("*") || operation.equals("/")) return 1;
        if (operation.equals("+") || operation.equals("-")) return 0;

        return -1;
    }

    /**
     * Calculates the result of mathematical operations.
     *
     * @param leftOperand  The string containing the left operand.
     * @param operator     The string containing the math operation.
     * @param rightOperand The string containing the right operand.
     * @return The string containing the result of the math operation.
     */
    public String compute(String leftOperand, String operator, String rightOperand) {
        double result = 0;

        switch (operator) {

            case "^":
                result = Math.pow(Double.parseDouble(leftOperand), Double.parseDouble(rightOperand));
                break;

            case "*":
                result = Double.parseDouble(leftOperand) * Double.parseDouble(rightOperand);
                break;

            case "/":
                result = Double.parseDouble(leftOperand) / Double.parseDouble(rightOperand);
                break;

            case "+":
                result = Double.parseDouble(leftOperand) + Double.parseDouble(rightOperand);
                break;

            case "-":
                result = Double.parseDouble(leftOperand) - Double.parseDouble(rightOperand);
                break;
        }
        return Double.toString(result);
    }

    /**
     * Calculates the result of mathematical operations with functions.
     *
     * @param function The string containing the function name.
     * @param argument The string containing the function argument.
     * @return The string containing the result of the function computing.
     */
    public String computeFunction(String function, String argument) {
        double result = 0;

        switch (function) {

            case "sin":
                result = Math.sin(Double.parseDouble(argument));
                break;

            case "cos":
                result = Math.cos(Double.parseDouble(argument));
                break;

            case "sqrt":
                result = Math.sqrt(Double.parseDouble(argument));
                break;

            case "tan":
                result = Math.tan(Double.parseDouble(argument));
                break;

            case "atan":
                result = Math.atan(Double.parseDouble(argument));
                break;

            case "log10":
                result = Math.log10(Double.parseDouble(argument));
                break;

            case "log2":
                result = Math.log10(Double.parseDouble(argument)) / Math.log10(2);
                break;

        }
        return Double.toString(result);
    }
}
