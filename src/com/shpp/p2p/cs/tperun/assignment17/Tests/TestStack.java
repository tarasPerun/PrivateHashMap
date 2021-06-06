package com.shpp.p2p.cs.tperun.assignment17.Tests;

import com.shpp.p2p.cs.tperun.assignment17.PrivateStack;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Stack;

/* This class contains methods for testing the class PrivateStack */
public class TestStack {

    private static PrivateStack<String> testStack = new PrivateStack<>();
    private static final Stack<String> standardStack = new Stack<>();

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RED = "\u001B[31m";

    private static final String[] colors = {"white", "black", "red", "yellow", "brown", "green",
            "blue", "orange", "pink", "grey"};

    public static void performTests() {

        pushTest();
        popTest();
        peekTest();
        sizeStackTest();
        isEmptyTest();
        iteratorTest();
        crashTest();
        complexTest();
    }

    /**
     * Checks the push(E element) method.
     */
    private static void pushTest() {

        printDoubleLine();
        System.out.print(ANSI_BLUE + "Test of push(E element)" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " Stack" + ANSI_RESET);
        for (int i = 0; i < colors.length; i++) {
            testStack.push(colors[i]);
            displayStack();
            System.out.print("  stack size = " + testStack.size());
            System.out.println("  the top element is " + " \"" + testStack.peek() + "\"");
            if (!testStack.peek().equals(colors[i])) {
                throw new RuntimeException("Error:  The stack top should be " + colors[i]);
            }
        }
        stackInitial();
        printTestPassed();
    }

    /**
     * Checks the E pop() method.
     */
    private static void popTest() {

        System.out.print(ANSI_BLUE + "Test of pop()" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " Stack" + ANSI_RESET);
        addValuesToStack(testStack, 10);
        for (int i = 0; i < 9; i++) {
            testStack.pop();
            displayStack();
            System.out.println();

            if (!testStack.peek().equals(colors[colors.length - i - 2])) {
                throw new RuntimeException("Error:  The stack top should be " + colors[colors.length - 2 - i]);
            }
        }
        stackInitial();
        printTestPassed();
    }

    /**
     * Checks the E peek() method.
     */
    private static void peekTest() {
        System.out.print(ANSI_BLUE + "Test of peek()" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " Stack" + ANSI_RESET);
        addValuesToStack(testStack, 10);
        displayStack();
        if (!testStack.peek().equals("grey")) {
            throw new RuntimeException("Error:  The stack top should be grey");
        }
        for (int i = 0; i < 3; i++) {
            testStack.pop();
        }
        System.out.println();
        displayStack();
        if (!testStack.peek().equals("blue")) {
            throw new RuntimeException("Error:  The stack top should be blue");
        }
        stackInitial();
        printTestPassed();
    }

    /**
     * Checks the size() method.
     */
    private static void sizeStackTest() {
        System.out.print(ANSI_BLUE + "Test of size()" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " Stack" + ANSI_RESET);
        stackInitial();

        if (testStack.size() != 0) {
            throw new RuntimeException("Error:  The stack size should be zero");
        }
        addValuesToStack(testStack, 10);
        displayStack();

        if (testStack.size() != 10) {
            throw new RuntimeException("Error:  The stack size should be ten");
        }
        for (int i = 0; i < 5; i++) {
            testStack.pop();
        }
        System.out.println();
        displayStack();
        if (testStack.size() != 5) {
            throw new RuntimeException("Error:  The stack size should be five");
        }
        stackInitial();
        printTestPassed();
    }

    /**
     * Checks the isEmpty() method.
     */
    private static void isEmptyTest() {
        System.out.print(ANSI_BLUE + "Test of isEmpty()" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " Stack" + ANSI_RESET);
        stackInitial();
        if (!testStack.isEmpty()) {
            throw new RuntimeException("Error:  The stack should be empty");
        }
        addValuesToStack(testStack, 10);
        displayStack();

        for (int i = 0; i < 5; i++) {
            testStack.push("aaa");
        }
        System.out.println();
        displayStack();
        for (int i = 0; i < 15; i++) {
            testStack.pop();
        }

        if (!testStack.isEmpty()) {
            throw new RuntimeException("Error:  The stack should be empty");
        }
        stackInitial();
        printTestPassed();
    }

    /**
     * Checks the iterator() method.
     */
    static void iteratorTest() {
        System.out.print(ANSI_BLUE + "\nIterator test" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " Stack" + ANSI_RESET);

        addValuesToStack(testStack, 10);
        displayStack();
        Iterator<String> iterator = testStack.iterator();

        System.out.print("\nfor each      ");
        for (Object c : testStack) {
            System.out.print(c + " ");
        }
        System.out.println();
        System.out.print("while-next()  ");

        while (iterator.hasNext())
            System.out.print(iterator.next() + " ");
        System.out.println();

        printTestPassed();
    }

    /**
     * Checks all methods.
     */
    private static void complexTest() {
        System.out.print(ANSI_BLUE + "Complex test" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " Stack" + ANSI_RESET);

        addValuesToStack(testStack, 10);
        for (int i = 0; i < 10; i++) {
            standardStack.push(colors[i]);
        }
        System.out.println(testStack.toString());
        System.out.println(standardStack.toString());

        for (int i = 0; i < 5; i++) {
            testStack.pop();
            standardStack.pop();
        }
        if (!testStack.peek().equals(standardStack.peek())) {
            throw new RuntimeException("Error:  The stack top should be " + standardStack.peek());
        }

        for (int i = 0; i < 10; i++) {
            testStack.push(colors[i]);
            standardStack.push(colors[i]);
        }
        if (testStack.size() != 15) {
            throw new RuntimeException("Error:  The stack size should be " + standardStack.size());
        }
        for (int i = 0; i < 14; i++) {
            testStack.pop();
            standardStack.pop();
        }
        System.out.println(testStack.toString());
        System.out.println(standardStack.toString());

        if (!testStack.peek().equals("white")) {
            throw new RuntimeException("Error:  The stack top should be " + standardStack.peek());
        }
        printTestPassed();
    }

    /**
     * Check the crash cases of PrivateStack
     */
    private static void crashTest() {
        System.out.print(ANSI_BLUE + "\nCrash test" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " Stack" + ANSI_RESET);

        System.out.println();
        addValuesToStack(testStack, 20);
        displayStack();

        for (int i = 0; i<20; i ++) {
            testStack.pop();
        }
        displayStack();
        System.out.println();

        /* check the removing of an element from an empty stack */
        try {
            testStack.pop();
        } catch (EmptyStackException exception) {

            System.out.println(ANSI_RED + "Success! The element cannot be removed from an empty stack "
                    + ANSI_RED);
        }

        /* check the receiving of an element from an empty stack */
        try {
            testStack.peek();
        } catch (EmptyStackException exception) {

            System.out.println(ANSI_RED + "Success! The element cannot be received from an empty stack "
                    + ANSI_RED);
        }

        printTestPassed();
    }


    /**
     * Prints double separator line.
     */
    private static void printDoubleLine() {
        for (int i = 0; i < 100; i++) {
            System.out.print("=");
        }
        System.out.println();
    }

    /**
     * Displays a positive test result.
     */
    private static void printTestPassed() {

        System.out.println(ANSI_GREEN + "\nTest passed" + ANSI_RESET);
        stackInitial();
        printDoubleLine();
    }

    /**
     * Initializes the tested and reference lists.
     */
    private static void stackInitial() {
        testStack = new PrivateStack<>();
    }

    /**
     * Displays the tested and reference lists.
     */
    private static void displayStack() {

        System.out.print("testStack " + testStack.toString());
    }

    /**
     * Fills the stack with the string values.
     *
     * @param testStack The tested Stack.
     * @param number    The stack size.
     */
    private static void addValuesToStack(PrivateStack<String> testStack, int number) {

        for (int i = 0; i < number; i++) {
            testStack.push(colors[i % colors.length]);
        }
    }
}
