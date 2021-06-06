package com.shpp.p2p.cs.tperun.assignment17.Tests;

import com.shpp.p2p.cs.tperun.assignment17.PrivateQueue;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* This class contains methods for testing the class PrivateQueue */
public class TestQueue {

    private static PrivateQueue<String> testQueue = new PrivateQueue<>();
    private static final ArrayDeque<String> standardQueue = new ArrayDeque<>();

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RED = "\u001B[31m";

    private static final String[] colors = {"white", "black", "red", "yellow", "brown", "green",
            "blue", "orange", "pink", "grey"};

    public static void performTests() {

        offerTest();
        sizeQueueTest();
        elementTest();
        peekTest();
        removeTest();
        pollTest();
        isEmptyTest();
        iteratorTest();
        crashTest();
        complexTest();
    }




    /**
     * Checks the offer(E element) method.
     */
    private static void offerTest() {

        printDoubleLine();
        System.out.print(ANSI_BLUE + "Test of offer(E element)" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " Queue" + ANSI_RESET);
        for (int i = 0; i < colors.length; i++) {

            if (!testQueue.offer(colors[i])) {
                throw new RuntimeException("Error: the element wasn't offered into queue");
            }
            displayQueue();
        }
        if (testQueue.size() != 10) {
            throw new RuntimeException("Error: the queue size should be 10");
        }
        queueInitial();
        printTestPassed();
    }

    /**
     * Checks the size() method.
     */
    private static void sizeQueueTest() {
        System.out.print(ANSI_BLUE + "Test of size()" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " Queue" + ANSI_RESET);
        queueInitial();
        displayQueue();

        System.out.println("size = " + testQueue.size());
        if (testQueue.size() != 0) {
            throw new RuntimeException("Error:  The queue size should be " + testQueue.size());
        }
        for (int i = 1; i < 5; i++) {

            addValuesToQueue(testQueue, 10);
            displayQueue();
            System.out.println("size = " + testQueue.size());
            if (testQueue.size() != (i * 10)) {
                throw new RuntimeException("Error:  The queue size should be " + testQueue.size());
            }
        }
        queueInitial();
        printTestPassed();
    }

    /**
     * Checks the E element() method.
     */
    private static void elementTest() {
        System.out.print(ANSI_BLUE + "Test of element()" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " Queue" + ANSI_RESET);
        queueInitial();
        addValuesToQueue(testQueue, 10);
        displayQueue();

        if (!testQueue.element().equals("white")) {
            throw new RuntimeException("The element in front of the queue must be white");
        }
        for (int i = 0; i < 5; i++) {
            testQueue.poll();
        }
        displayQueue();
        if (!testQueue.element().equals("green")) {
            throw new RuntimeException("The element in front of the queue must be green");
        }
        queueInitial();
        printTestPassed();
    }

    /**
     * Checks the E peek() method.
     */
    private static void peekTest() {
        System.out.print(ANSI_BLUE + "Test of peek()" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " Queue" + ANSI_RESET);

        addValuesToQueue(testQueue, 10);
        displayQueue();

        if (!testQueue.peek().equals("white")) {
            throw new RuntimeException("Error:  The queue front element should be white");
        }

        for (int i = 0; i < 3; i++) {
            testQueue.poll();
        }
        displayQueue();

        if (!testQueue.peek().equals("yellow")) {
            throw new RuntimeException("Error:  The queue front element should be yellow");
        }

        for (int i = 0; i < 7; i++) {
            testQueue.poll();
        }
        System.out.println();
        displayQueue();

        if (testQueue.peek() != null) {
            throw new RuntimeException("Error:  The queue front element should be null");
        }
        System.out.println(testQueue.peek());
        queueInitial();
        printTestPassed();
    }

    /**
     * Checks the E remove() method.
     */
    private static void removeTest() {

        System.out.print(ANSI_BLUE + "Test of remove()" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " Queue" + ANSI_RESET);
        addValuesToQueue(testQueue, 10);

        for (int i = 1; i < 10; i++) {
            testQueue.remove();
            displayQueue();

            if (!testQueue.peek().equals(colors[i])) {
                throw new RuntimeException("Error:  The queue front element should be " + colors[i]);
            }
        }
        queueInitial();
        printTestPassed();
    }

    /**
     * Checks the E poll() method.
     */
    private static void pollTest() {

        System.out.print(ANSI_BLUE + "Test of poll()" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " Queue" + ANSI_RESET);
        addValuesToQueue(testQueue, 10);

        for (int i = 0; i < 9; i++) {

            testQueue.poll();
            displayQueue();

            if (!testQueue.peek().equals(colors[i + 1])) {
                throw new RuntimeException("Error:  The queue front element should be " + colors[i + 1]);
            }
        }
        testQueue.poll();
        if (testQueue.peek() != null) {
            throw new RuntimeException("Error:  The queue front element should be equal null");
        }
        queueInitial();
        printTestPassed();
    }


    /**
     * Checks the isEmpty() method.
     */
    private static void isEmptyTest() {
        System.out.print(ANSI_BLUE + "Test of isEmpty()" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " Queue" + ANSI_RESET);
        queueInitial();

        if (!testQueue.isEmpty()) {
            throw new RuntimeException("Error:  The stack should be empty");
        }

        addValuesToQueue(testQueue, 10);
        displayQueue();

        for (int i = 0; i < 5; i++) {
            testQueue.offer("aaa");
        }
        displayQueue();
        for (int i = 0; i < 15; i++) {
            testQueue.poll();
        }
        displayQueue();
        if (!testQueue.isEmpty()) {
            throw new RuntimeException("Error:  The queue should be empty");
        }
        queueInitial();
        printTestPassed();
    }

    /**
     * Checks the iterator() method.
     */
    static void iteratorTest() {
        System.out.print(ANSI_BLUE + "\nIterator test" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " Queue" + ANSI_RESET);

        addValuesToQueue(testQueue, 10);
        displayQueue();
        Iterator<String> iterator = testQueue.iterator();

        System.out.print("for each      ");
        for (Object c : testQueue) {
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
     * Check the crash cases of PrivateQueue
     */
    private static void crashTest() {
        System.out.print(ANSI_BLUE + "\nCrash test" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " Queue" + ANSI_RESET);

        System.out.println();
        addValuesToQueue(testQueue, 20);
        displayQueue();

        for (int i = 0; i < 20; i++) {
            testQueue.poll();
        }
        displayQueue();
        System.out.println();

        /* check the removing of an element from an empty stack */
        try {
            testQueue.remove();
        } catch (NoSuchElementException exception) {

            System.out.println(ANSI_RED + "Success! The element cannot be removed from an empty queue "
                    + ANSI_RED);
        }

        /* check the receiving of an element from an empty stack */
        try {
            testQueue.element();
        } catch (NoSuchElementException exception) {

            System.out.println(ANSI_RED + "Success! The element cannot be received from an empty queue "
                    + ANSI_RED);
        }

        printTestPassed();
    }


    /**
     * Checks all methods.
     */
    private static void complexTest() {
        System.out.print(ANSI_BLUE + "Complex test" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " Queue" + ANSI_RESET);

        for (int i = 0; i < 10; i++) {
            testQueue.offer(colors[i]);
            standardQueue.offerLast(colors[i]);
        }
        System.out.println(testQueue.toString());
        System.out.println(standardQueue.toString());

        if (testQueue.isEmpty()) {
            throw new RuntimeException("Error:  The queue shouldn't be empty");
        }
        if (testQueue.size() != 10) {
            throw new RuntimeException("Error:  The queue size should be 10");
        }

        for (int i = 0; i < 5; i++) {
            testQueue.remove();
            standardQueue.removeFirst();
        }
        System.out.println(testQueue.toString());
        System.out.println(standardQueue.toString());
        if (testQueue.size() != 5) {
            throw new RuntimeException("Error:  The queue size should be 5");
        }
        if (!testQueue.element().equals(standardQueue.element())) {
            throw new RuntimeException("Error:  The first element should be " + standardQueue.element());
        }

        for (int i = 0; i < 10; i++) {
            testQueue.offer(colors[i]);
            standardQueue.offerLast(colors[i]);
        }
        System.out.println(testQueue.toString());
        System.out.println(standardQueue.toString());

        for (int i = 0; i < 3; i++) {
            testQueue.remove();
            standardQueue.removeFirst();
        }

        System.out.println(testQueue.toString());
        System.out.println(standardQueue.toString());

        if (!testQueue.element().equals("pink")) {
            throw new RuntimeException("Error:  The queue first should be pink");
        }
        System.out.println(standardQueue.peekFirst());

        for (int i = 0; i < 10; i++) {
            testQueue.offer(colors[i]);
            standardQueue.offerLast(colors[i]);
        }
        System.out.println(testQueue.toString());
        System.out.println(standardQueue.toString());

        for (int i = 0; i < 15; i++) {
            testQueue.poll();
            standardQueue.poll();
        }
        System.out.println(testQueue.toString());
        System.out.println(standardQueue.toString());

        if (testQueue.size() != 7) {
            throw new RuntimeException("Error:  The queue size should be " + standardQueue.size());
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
        queueInitial();
        printDoubleLine();
    }

    /**
     * Initializes the tested and reference lists.
     */
    private static void queueInitial() {
        testQueue = new PrivateQueue<>();
    }

    /**
     * Displays the tested and reference lists.
     */
    private static void displayQueue() {

        System.out.println("testQueue " + testQueue.toString());
    }

    /**
     * Fills the stack with the string values.
     *
     * @param testQueue The tested Stack.
     * @param number    The stack size.
     */
    private static void addValuesToQueue(PrivateQueue<String> testQueue, int number) {

        for (int i = 0; i < number; i++) {
            testQueue.offer(colors[i % 10]);
        }
    }
}

