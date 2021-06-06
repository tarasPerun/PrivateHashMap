package com.shpp.p2p.cs.tperun.assignment17.Tests;

import java.util.*;

import com.shpp.p2p.cs.tperun.assignment17.PrivatePriorityQueue;

public class TestPriorityQueue {

    private static PrivatePriorityQueue<Integer> testQueue = new PrivatePriorityQueue<>();
    private static final PriorityQueue<Integer> standardQueue = new PriorityQueue<>();

    private static final Random random = new Random();

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED = "\u001B[31m";

    private static final String[] colors = {"white", "black", "red", "yellow", "brown", "green",
            "blue", "orange", "pink", "grey"};

    private static final PrivatePriorityQueue<String> testQueueString = new PrivatePriorityQueue<>();
    private static final PriorityQueue<String> standardQueueString = new PriorityQueue<>();

    public static void performTests() {

        enqueueTest();
        dequeueMinTest();
        peekTest();
        clearTest();
        sizeTest();
        isEmptyTest();
        iteratorTest();
        complexTest();
    }

    /**
     * Checks the enqueue() method.
     */
    private static void enqueueTest() {

        printDoubleLine();
        System.out.print(ANSI_BLUE + "Test of enqueue() " + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " PriorityQueue" + ANSI_RESET);

        displayQueue();
        System.out.println("Expected: " + standardQueue.toString());

        for(int i = 0; i < 10; i ++) {

            int randomValue = random.nextInt(100);
            System.out.println("\nenqueue : " + randomValue);
            testQueue.enqueue(randomValue);
            standardQueue.add(randomValue);
            displayQueue();
            System.out.println("Expected: " + standardQueue.toString());

            if (!testQueue.peek().equals(standardQueue.peek())) {
                throw new RuntimeException("Error: incorrect value. The top of the queue should be "
                        + standardQueue.peek());
            }
        }

        /* checking if zero is added to the queue */
        try {
            testQueue.enqueue(null);
        } catch (NullPointerException exception) {
            System.out.println("\nCheck if zero is added to the queue");
            System.out.println(ANSI_RED + "Success! The null cannot be added to the queue "
                    + ANSI_RED);
        }
        printTestPassed();
    }

    /**
     * Checks the dequeueMin() method.
     */
    private static void dequeueMinTest() {

        System.out.print(ANSI_BLUE + "Test of dequeueMin() " + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " PriorityQueue" + ANSI_RESET);
        System.out.println();

        displayQueue();
        System.out.println("Expected: " + standardQueue.toString());

        for(int i = 0; i < 10; i ++) {

            if (!testQueue.peek().equals(standardQueue.peek())) {
                throw new RuntimeException("Error: incorrect value. The top of the queue should be "
                        + standardQueue.peek());
            }
            System.out.println("\ndequeue : " + testQueue.peek());
            testQueue.dequeueMin();
            standardQueue.poll();
            displayQueue();
            System.out.println("Expected: " + standardQueue.toString());
        }

        /* check the removing of an element from an empty queue */
        try {
            testQueue.dequeueMin();
        } catch (NoSuchElementException exception) {
            System.out.println("\nCheck the removing of an element from an empty queue");
            System.out.println(ANSI_RED + "Success! The element cannot be removed from an empty queue "
                    + ANSI_RED);
        }
        printTestPassed();
    }

    /**
     * Checks the E peek() method.
     */
    private static void peekTest() {
        System.out.print(ANSI_BLUE + "Test of peek()" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " PriorityQueue" + ANSI_RESET);
        addRandomValuesToPriorityQueue(testQueue, 20);
        displayQueue();

        for (int i = 0; i < 3; i++) {
            testQueue.dequeueMin();
            standardQueue.poll();
            if (!testQueue.peek().equals(standardQueue.peek())) {
                throw new RuntimeException("Error:  The queue front element should be " + standardQueue.peek());
            }
        }
        displayQueue();

        for (int i = 0; i < 17; i++) {
            testQueue.dequeueMin();
        }
        System.out.println();
        displayQueue();

        /* check the receiving of an element from an empty queue */
        try {
            testQueue.peek();
        } catch (NoSuchElementException exception) {
            System.out.println("\nCheck the receiving of an element from an empty queue");
            System.out.println(ANSI_RED + "Success! The element cannot be received from an empty queue "
                    + ANSI_RED);
        }

        printTestPassed();
    }

    /**
     * Checks the clear() method.
     */
    private static void clearTest() {

        System.out.print(ANSI_BLUE + "Test of clear() " + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " PriorityQueue" + ANSI_RESET);

        queueInitial();
        addRandomValuesToPriorityQueue(testQueue, 30);
        displayQueue();
        System.out.println("currentsize = " + testQueue.size());

        testQueue.clear();
        displayQueue();
        System.out.println("currentsize = " + testQueue.size());
        queueInitial();
        printTestPassed();
    }

    /**
     * Checks the size() method.
     */
    private static void sizeTest() {
        System.out.print(ANSI_BLUE + "Test of size()" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " PriorityQueue" + ANSI_RESET);
        queueInitial();
        System.out.println();
        if (testQueue.size() != 0) {
            throw new RuntimeException("Error:  The queue size should be zero");
        }
        addRandomValuesToPriorityQueue(testQueue, 30);
        displayQueue();
        System.out.println("size = " + standardQueue.size());

        if (testQueue.size() != 30) {
            throw new RuntimeException("Error:  The queue size should be " + standardQueue.size());
        }
        for (int i = 0; i < 15; i++) {
            testQueue.dequeueMin();
            standardQueue.poll();
        }
        System.out.println();
        displayQueue();
        System.out.println("size = " + testQueue.size());
        if (testQueue.size() != 15) {
            throw new RuntimeException("Error:  The queue size should be " + standardQueue.size());
        }
        queueInitial();

        System.out.println();
        displayQueue();
        System.out.println("size = " + testQueue.size());
        if (testQueue.size() != 0) {
            throw new RuntimeException("Error:  The queue size should be zero");
        }
        printTestPassed();
    }



    /**
     * Checks the isEmpty() method.
     */
    private static void isEmptyTest() {
        System.out.print(ANSI_BLUE + "Test of isEmpty()" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " PriorityQueue" + ANSI_RESET);
        queueInitial();

        if (!testQueue.isEmpty()) {
            throw new RuntimeException("Error:  The queue should be empty");
        }

        addRandomValuesToPriorityQueue(testQueue, 20);
        displayQueue();

        for (int i = 0; i < 5; i++) {
            testQueue.enqueue(i);
        }
        displayQueue();

        for (int i = 0; i < 25; i++) {
            testQueue.dequeueMin();
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
        System.out.println(ANSI_YELLOW + " PriorityQueue" + ANSI_RESET);

        addRandomValuesToPriorityQueue(testQueue, 30);
        System.out.println();
        displayQueue();

        Iterator<Integer> iterator = testQueue.iterator();

        System.out.print("for each      ");
        for (Object object : testQueue) {
            System.out.print(object + " ");
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
        System.out.println(ANSI_YELLOW + " PriorityQueue" + ANSI_RESET);

        for (int i = 0; i < 10; i++) {
            testQueueString.enqueue(colors[i]);
            standardQueueString.add(colors[i]);
        }
        System.out.println(testQueueString.toString());
        System.out.println(standardQueueString.toString());

        if (testQueueString.isEmpty()) {
            throw new RuntimeException("Error:  The queue shouldn't be empty");
        }
        if (testQueueString.size() != 10) {
            throw new RuntimeException("Error:  The queue size should be 10");
        }

        for (int i = 0; i < 5; i++) {
            testQueueString.dequeueMin();
            standardQueueString.poll();
        }
        System.out.println(testQueueString.toString());
        System.out.println(standardQueueString.toString());
        if (testQueueString.size() != 5) {
            throw new RuntimeException("Error:  The queue size should be 5");
        }
        if (!testQueueString.peek().equals(standardQueueString.element())) {
            throw new RuntimeException("Error:  The first element should be " + standardQueueString.element());
        }

        for (int i = 0; i < 10; i++) {
            testQueueString.enqueue(colors[i]);
            standardQueueString.add(colors[i]);
        }
        System.out.println(testQueueString.toString());
        System.out.println(standardQueueString.toString());

        for (int i = 0; i < 3; i++) {
            testQueueString.dequeueMin();
            standardQueueString.poll();
        }

        System.out.println(testQueueString.toString());
        System.out.println(standardQueueString.toString());

        if (!testQueueString.peek().equals("green")) {
            throw new RuntimeException("Error:  The queue first should be green");
        }
        System.out.println(standardQueueString.element());

        for (int i = 0; i < 10; i++) {
            testQueueString.enqueue(colors[i]);
            standardQueueString.add(colors[i]);
        }
        System.out.println(testQueueString.toString());
        System.out.println(standardQueueString.toString());

        for (int i = 0; i < 15; i++) {
            testQueueString.dequeueMin();
            standardQueueString.poll();
        }
        System.out.println(testQueueString.toString());
        System.out.println(standardQueueString.toString());

        if (testQueueString.size() != 7) {
            throw new RuntimeException("Error:  The queue size should be " + standardQueueString.size());
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
        //  queueInitial();
        printDoubleLine();
    }


    /**
     * Initializes the tested and reference lists.
     */
    private static void queueInitial() {
        testQueue = new PrivatePriorityQueue<Integer>();
    }

    /**
     * Fills the queue with the random integer numbers.
     *  @param testQueue    The tested queue.
     * @param number       The lists size.
     */
    static void addRandomValuesToPriorityQueue(PrivatePriorityQueue<Integer> testQueue,
                                               int number) {
        for (int i = 1; i <= number; i++) {
            int randomValue = random.nextInt(100);
            testQueue.enqueue(randomValue);
            TestPriorityQueue.standardQueue.add(randomValue);
        }
    }

    /**
     * Displays the tested queue.
     */
    private static void displayQueue() {

        System.out.println("testQueue " + testQueue.toString());
    }

}
