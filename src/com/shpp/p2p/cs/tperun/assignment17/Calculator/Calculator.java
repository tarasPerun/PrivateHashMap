package com.shpp.p2p.cs.tperun.assignment17.Calculator;

import com.shpp.p2p.cs.tperun.assignment17.PrivateArrayList;
import com.shpp.p2p.cs.tperun.assignment17.PrivateHashMap;

import java.util.Scanner;
import java.util.regex.Pattern;

/* The program is a calculator for calculating the values of mathematical expressions.
 * ------------------------------------------------------------------------------------
 * The first parameter of the program is a mathematical expression (formula).
 * The following mathematical operations are allowed: exponentiation (^), multiplication (*),
 * division (/), addition (+) and subtraction (-), as well as opening and closing parentheses.
 * The formula can also contain variables and functions sine(sin), cosine(cos), tangent(tan),
 * logarithm(log2, log10), square root(sqrt), arctangent(atan).
 * For example: 1+ (2 + 3 * (4 + 5-sin (45 * cos (a)))) / 7 (can be much more difficult and longer).
 * The remaining (optional) parameters look like variable name = value,
 * for example a = 2, and are entered with a space after the formula. If you enter data in
 * command line, separate the parameters with one space, and when using spaces inside the
 * expression enclose the expression in quotation marks. Variables must be denoted by a single
 * Latin letter, between the variable and the number there must be one sign of the mathematical
 * operation. If after the sign mathematical operation minus, then this minus is not an operation,
 * but an unary minus, which means that the number following it is negative, and if it is a variable
 * or a function, it changes its sign. It is allowed to separate a unary minus together with an
 * operand by brackets. You can also enter from the console, but you must first fill in the command line.
 */
public class Calculator implements CalculatorConstants {

    private static PrivateHashMap<String, String> variables = new PrivateHashMap<>();
    private static final StringProcessing strPr = new StringProcessing();
    private static final PrivateArrayList<String> vars = new PrivateArrayList<>();
    private static PrivateArrayList<String> parsedFormula = new PrivateArrayList<>();


    public static void main(String[] args) {

        /* fixing the start time of the program */
        long startTime = System.currentTimeMillis();

        Scanner in = new Scanner(System.in);

        System.out.println("\nDo you want to enter the formula and variables from the console?");
        System.out.print("If not, press ENTER , but before go to Run --> Edit Configuration " +
                "\nand fill in the line Program arguments. Otherwise, press ANY KEY + ENTER to continue: ");

        String line = in.nextLine();
        if (line.equals("")) {
            if (args.length == 0) {
                System.out.println("\nNo calculation formula entered. Please enter program arguments args[]");
                goodBye();
            }
            if (!isValid(args[0])) {
                goodBye();
            }
            performCalculation(args);

            /* fixing the end time of the program */
            long endTime = System.currentTimeMillis();
            System.out.println("Time : " + (endTime - startTime) + " millisecond");

            goodBye();
        }

        while (true) {

            args = inputFormulaAndVariables();
            performCalculation(args);

            while (true) {
                if (variables.size() == 0) {
                    goodBye();
                }
                System.out.print("\nDo you want to enter new values of variables? Y/N ");
                String line1 = in.nextLine();
                if (Pattern.matches("[yY]*", line1)) {

                    for (String str : variables.keySet()) {
                        System.out.print(str + " = ");

                        String line2 = in.nextLine();
                        variables.put(str, line2);
                    }
                    System.out.println(variables.toString());
                    displayCalculations(variables);

                } else {
                    goodBye();
                }
            }
        }
    }

    /**
     * Performs the calculations
     *
     * @param strings input formula and variables
     */
    private static void performCalculation(String[] strings) {

        System.out.println("\nformula : " + strPr.removeSpaces(strings[0]));
        parsedFormula = strPr.parseToFormula(strPr.removeSpaces(strings[0]));
        variables = strPr.parseToVariables(strings);
        displayCalculations(variables);
    }

    /**
     * Displays a message "Good bye" and terminates the program.
     */
    private static void goodBye() {

        System.out.println("\nGood bye");
        System.exit(1);
    }

    /**
     * Displays the results of calculations.
     *
     * @param var The variables.
     */
    private static void displayCalculations(PrivateHashMap<String, String> var) {

        System.out.println("parsed formula :                         " + parsedFormula.toString());
        PrivateArrayList<String> insertedFormula = strPr.insertVariablesToFormula(parsedFormula, var);
        System.out.println("parsed formula with inserted variables : " +
                insertedFormula.toString());
        System.out.println("organized formula for calculate :        " +
                strPr.organizeFormula(insertedFormula).toString());
        System.out.println("\nresult of calculates :                   " +
                StringProcessing.calculate(strPr.organizeFormula((insertedFormula))));
    }

    /**
     * Checks the entered string for compliance with the criteria
     * of a mathematical expression.
     *
     * @param line The input string.
     * @return true or false.
     */
    private static boolean isValid(String line) {

        /* Check for valid characters */
        if (!Pattern.matches("[a-zA-Z0-9. ()+*^/\\-]*", line)) {
            System.out.println("\nWrong formula");
            System.out.println("Invalid character");
            return false;
        }

        /*
         Check for two signs of a mathematical operation in a row,
         excluding the second unary minus.
        */
        for (int i = 0; i < line.length() - 1; i++) {
            if (StringProcessing.isMathOperation(Character.toString(line.charAt(i))) &&
                    StringProcessing.isMathOperation(Character.toString(line.charAt(i + 1))) &&
                    (line.charAt(i + 1) != '-')) {
                System.out.println("\nWrong formula");
                System.out.println("Invalid double math operation");
                return false;
            }

            /* Check for number and letter in a row */
            if (Character.isDigit(line.charAt(i)) && Character.isLetter(line.charAt(i + 1))) {
                System.out.println("\nWrong formula");
                System.out.println("There must be a mathematical operation between a number " +
                        "and a variable");
                return false;
            }

            /* Check for letter and "." in a row */
            if (((line.charAt(i) == '.') && Character.isLetter(line.charAt(i + 1))) ||
                    ((line.charAt(i+1) == '.') && Character.isLetter(line.charAt(i)))) {
                System.out.println("\nWrong formula");
                System.out.println("Invalid character sequence");

                return false;
            }
        }

        /* Check for an invalid character at the end of the string. */
        if (Pattern.matches("[.\\-+*/]*", Character.toString(line.charAt(line.length() - 1)))) {
            System.out.println("\nWrong formula");
            System.out.println("Invalid character at the end of the string");
            return false;
        }

        /* Check for an invalid character at the start of the string. */
        if (Pattern.matches("[.+*/]*", Character.toString(line.charAt(0)))) {
            System.out.println("\nWrong formula");
            System.out.println("Invalid character at the start of the string");
            return false;
        }

        /* checking if the number of open parentheses is equal to the number of closed ones */
        int leftCount = 0;
        int rightCount = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '(') {
                leftCount++;
                continue;
            }
            if (line.charAt(i) == ')') {
                rightCount++;
            }
        }
        if (leftCount != rightCount) {
            System.out.println("\nWrong formula");
            System.out.println("The number of open parentheses is not equal to the number of closed ones");
            return false;
        }

        /* Check for open parenthesis and closed one in a row  */
        for (int i = 0; i < line.length() - 1; i++) {
            if (line.charAt(i) == '(' && line.charAt(i + 1) == ')') {
                System.out.println("\nWrong formula");
                System.out.println("Invalid character sequence ()");
                return false;
            }
        }
        return true;
    }

    /**
     * Input the formula and variables from console.
     *
     */
    private static String[] inputFormulaAndVariables() {

        PrivateArrayList<String> input = new PrivateArrayList<>();
        Scanner in = new Scanner(System.in);

        System.out.print("\nEnter the formula: ");
        String line = in.nextLine();
        input.add(line);

        if (!isValid(input.get(0))) {
            goodBye();
        }

        System.out.println("formula :" + input.get(0));
        String buffer = "";
        for (int i = 0; i < input.get(0).length(); i++) {

            if (Character.isLetter(line.charAt(i))) {
                buffer += Character.toString(line.charAt(i));

               if (i == line.length() - 1 && !vars.contains(buffer)) {
                    vars.add(buffer);
                }
            } else {
                if(vars.contains(buffer)) buffer = "";
                if (!vars.contains(buffer)) {
                    if (!buffer.equals("")) {
                        vars.add(buffer);
                    }
                    buffer = "";
                }
            }
        }
        for (int i = 0; i < vars.size(); i++) {
            for (int j = 0; j < FUNCTIONS.length; j++) {
                if (vars.contains(FUNCTIONS[j]))

                 vars.remove(vars.indexOf(FUNCTIONS[j]));
            }
        }

        for (int i = 0; i < vars.size(); i++) {
            if (vars.get(i).length() > 1)
                vars.remove(i);
        }

        System.out.println("variables :" + vars.toString());
        int size = vars.size();
        String[] data = new String[size + 1];

        for (int i = 0; i < vars.size(); i++) {

            System.out.print("\nEnter the variable " + vars.get(i) + " = ");
            String line1 = in.nextLine();
            String temp = vars.get(i).concat("=");
            input.add(temp.concat(line1));
        }
        for (int i = 0; i < input.size(); i++) {
            data[i] = input.get(i);
        }
        return data;
    }
}
