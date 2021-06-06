package com.shpp.p2p.cs.tperun.assignment17.Tests;

import com.shpp.p2p.cs.tperun.assignment17.PrivateLinkedList;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;

/* This class contains methods for testing the class PrivateLinkedList */
public class TestLinkedList {

    private static PrivateLinkedList<Integer> testList = new PrivateLinkedList<>();
    private static LinkedList<Integer> standardList = new LinkedList<>();
    private static final Random random = new Random();

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RED = "\u001B[31m";


    public static void performTests() {

        addTest();
        addIndexTest();
        addFirstTest();
        addLastTest();
        getTest();
        getFirstTest();
        getLastTest();
        sizeTest();
        setTest();
        removeTest();
        removeFirstTest();
        removeLastTest();
        indexOfTest();
        clearTest();
        containsTest();
        isEmptyTest();
        iteratorTest();
        crashTest();
        complexTest();
    }


    /**
     * Checks the add() method.
     */
    static void addTest() {

        printDoubleLine();
        System.out.print(ANSI_BLUE + "Test of add(E element)" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " LinkedList" + ANSI_RESET);

        for (int j = 0; j < 5; j++) {
            addRandomValuesToLists(testList, standardList, j * 10);
            displayLists();

            for (int i = 0; i < j * 10; i++) {
                if (!testList.get(i).equals(standardList.get(i))) {
                    throw new RuntimeException("Error: incorrect addition. The value of the "
                            + i + "-th element should be " + standardList.get(i));
                }
            }
            listsInitial();
        }
        printTestPassed();
    }

    /**
     * Checks the add(int index, E element) method.
     */
    static void addIndexTest() {
        System.out.print(ANSI_BLUE + "\nTest of add(int index, E element)" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " LinkedList" + ANSI_RESET);

        addRandomValuesToLists(testList, standardList, 10);
        displayLists();

        for (int i = 0; i < 10; i++) {
            testList.add(5, i * 10);
            standardList.add(5, i * 10);
        }

        for (int i = 0; i < 10; i++) {
            if (!testList.get(5 + i).equals(standardList.get(5 + i))) {
                throw new RuntimeException("Error: incorrect addition. The value of the "
                        + (5 + i) + "-th element should be " + standardList.get(5 + i));
            }
        }
        displayLists();
        printTestPassed();
    }

    /**
     * Checks the addFirst() method.
     */
    private static void addFirstTest() {
        System.out.print(ANSI_BLUE + "Test of addFirst(E element)" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " LinkedList" + ANSI_RESET);
        addRandomValuesToLists(testList, standardList, 20);
        displayLists();

        for (int i = 0; i < 20; i++) {

            int randomIndex = random.nextInt(19);
            if (!testList.get(randomIndex).equals(standardList.get(randomIndex))) {
                throw new RuntimeException("Error: incorrect value. The value of the "
                        + randomIndex + "-th element should be " + standardList.get(randomIndex));
            }
        }
        testList.addFirst(500);
        standardList.addFirst(500);
        displayLists();
        if (testList.get(0) != 500) {
            throw new RuntimeException("Error: incorrect value. The last value of the list should be 500");
        }
        printTestPassed();
    }

    /**
     * Checks the addLast() method.
     */
    private static void addLastTest() {
        System.out.print(ANSI_BLUE + "Test of addLast(E element)" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " LinkedList" + ANSI_RESET);
        addRandomValuesToLists(testList, standardList, 20);
        displayLists();

        for (int i = 0; i < 20; i++) {
            int randomIndex = random.nextInt(19);
            if (!testList.get(randomIndex).equals(standardList.get(randomIndex))) {
                throw new RuntimeException("Error: incorrect value. The value of the "
                        + randomIndex + "-th element should be " + standardList.get(randomIndex));
            }
        }
        testList.addLast(500);
        standardList.addLast(500);

        displayLists();
        if (testList.get(testList.size() - 1) != 500) {
            throw new RuntimeException("Error: incorrect value. The last value of the list should be 500");
        }
        printTestPassed();
    }

    /**
     * Checks the get(int index) method.
     */
    private static void getTest() {
        System.out.print(ANSI_BLUE + "\nTest of get(int index)" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " LinkedList" + ANSI_RESET);

        addRandomValuesToLists(testList, standardList, 20);
        displayLists();

        for (int i = 0; i < 20; i++) {
            int randomIndex = random.nextInt(19);

            if (!testList.get(randomIndex).equals(standardList.get(randomIndex))) {
                throw new RuntimeException("Error: incorrect value. The value of the "
                        + randomIndex + "-th element should be " + standardList.get(randomIndex));
            }
        }
        printTestPassed();
    }

    /**
     * Checks the getFirst() method.
     */
    private static void getFirstTest() {

        System.out.print(ANSI_BLUE + "Test of getFirst()" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " LinkedList" + ANSI_RESET);
        addRandomValuesToLists(testList, standardList, 20);
        displayLists();

        if (!testList.getFirst().equals(standardList.getFirst())) {
            throw new RuntimeException("Error: incorrect value. The first value of the list should be " +
                    standardList.getFirst());
        }
        for (int i = 0; i < 10; i++) {
            testList.addFirst(500 + i);
            System.out.println(testList.toString());
            if (testList.getFirst() != 500 + i) {
                throw new RuntimeException("Error: incorrect value. The value of the first element " +
                        "should be " + (500 + i));
            }
        }
        printTestPassed();
    }

    /**
     * Checks the getLast() method.
     */
    private static void getLastTest() {

        System.out.print(ANSI_BLUE + "Test of getLast()" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " LinkedList" + ANSI_RESET);

        addRandomValuesToLists(testList, standardList, 20);
        displayLists();

        if (!testList.getLast().equals(standardList.getLast())) {
            throw new RuntimeException("Error: incorrect value. The last value of the list should be " +
                    standardList.getLast());
        }

        for (int i = 0; i < 10; i++) {
            testList.addLast(500 + i);
            System.out.println(testList.toString());
            if (testList.getLast() != 500 + i) {
                throw new RuntimeException("Error: incorrect value. The value of the last element " +
                        "should be " + (500 + i));
            }
        }
        printTestPassed();
    }

    /**
     * Checks the size() method.
     */
    static void sizeTest() {
        System.out.print(ANSI_BLUE + "\nTest of size()" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " LinkedList" + ANSI_RESET);
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 10; i++) {
                int randomValue = random.nextInt(100);

                testList.add(randomValue);
                standardList.add(randomValue);
            }

            displayLists();
            System.out.println("size =  " + testList.size());

            if (testList.size() != standardList.size()) {
                throw new RuntimeException("Error: incorrect size. The value of the list size " +
                        " should be " + standardList.size());
            }
        }
        printTestPassed();
    }

    /**
     * Checks the set(int index, E element) method.
     */
    static void setTest() {
        System.out.print(ANSI_BLUE + "\nTest of set(int index, E element )" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " LinkedList" + ANSI_RESET);
        for (int i = 0; i < 20; i++) {
            testList.add(i);
            standardList.add(i);
        }
        for (int i = 0; i < 10; i++) {
            int randomValue = random.nextInt(100);
            int randomIndex = random.nextInt(19);

            testList.set(randomIndex, randomValue);
            standardList.set(randomIndex, randomValue);
        }
        displayLists();

        int randomIndex = random.nextInt(19);

        if (!testList.get(randomIndex).equals(standardList.get(randomIndex))) {
            throw new RuntimeException("Error: incorrect value. The value of the "
                    + randomIndex + "-th element should be " + standardList.get(randomIndex));
        }
        printTestPassed();
    }

    /**
     * Checks the remove(int index) method.
     */
    static void removeTest() {
        System.out.print(ANSI_BLUE + "\nTest of remove(int index)" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " LinkedList" + ANSI_RESET);
        for (int i = 0; i < 20; i++) {
            testList.add(i);
            standardList.add(i);
        }
        displayLists();

        for (int i = 0; i < 20; i++) {
            testList.remove(0);
            standardList.remove(0);
        }
        displayLists();

        if (testList.size() != 0) {
            throw new RuntimeException("Error: incorrect value. The list size should be equal to zero");
        }
        addRandomValuesToLists(testList, standardList, 20);
        displayLists();

        for (int i = 0; i < 5; i++) {
            int randomIndex = random.nextInt(4);
            if (!testList.remove(randomIndex).equals(standardList.remove(randomIndex))) {
                throw new RuntimeException("Error: incorrect value. The value of the removed "
                        + randomIndex + "-th element should be " + standardList.remove(randomIndex));
            }
        }
        displayLists();
        printTestPassed();
    }

    /**
     * Checks the removeFirst() method.
     */
    private static void removeFirstTest() {
        System.out.print(ANSI_BLUE + "Test of removeFirst()" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " LinkedList" + ANSI_RESET);

        addRandomValuesToLists(testList, standardList, 20);
        displayLists();

        for (int i = 0; i < 5; i++) {
            if (!testList.removeFirst().equals(standardList.removeFirst())) {
                throw new RuntimeException("Error: incorrect value. The removed first value of the list should be " +
                        standardList.removeFirst());
            }
        }
        displayLists();
        printTestPassed();
    }


    /**
     * Checks the removeLast() method.
     */
    private static void removeLastTest() {
        System.out.print(ANSI_BLUE + "Test of removeLast()" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " LinkedList" + ANSI_RESET);

        addRandomValuesToLists(testList, standardList, 20);
        displayLists();

        for (int i = 0; i < 5; i++) {
            if (!testList.removeLast().equals(standardList.removeLast())) {
                throw new RuntimeException("Error: incorrect value. The last value of the list should be " +
                        standardList.removeLast());
            }
        }
        displayLists();
        printTestPassed();

    }

    /**
     * Checks the indexOf(E element) method.
     */
    static void indexOfTest() {
        System.out.print(ANSI_BLUE + "\nTest of indexOf(E element)" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " LinkedList" + ANSI_RESET);
        addRandomValuesToLists(testList, standardList, 20);
        displayLists();

        for (int i = 0; i < 20; i++) {
            int randomIndex = random.nextInt(19);

            if (testList.indexOf(randomIndex) != standardList.indexOf(randomIndex)) {
                throw new RuntimeException("Error: incorrect value. The value of the index should be "
                        + standardList.indexOf(randomIndex));
            }
        }
        printTestPassed();
    }

    /**
     * Checks the clear() method.
     */
    static void clearTest() {
        System.out.print(ANSI_BLUE + "\nTest of clear()" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " LinkedList" + ANSI_RESET);
        addRandomValuesToLists(testList, standardList, 20);
        displayLists();

        testList.clear();
        standardList.clear();
        displayLists();

        if (testList.size() != 0) {
            throw new RuntimeException("Error: incorrect value. The list size must be equal zero");
        }
        printTestPassed();
    }

    /**
     * Checks the contains(E element) method.
     */
    static void containsTest() {
        System.out.print(ANSI_BLUE + "\nTest of contains(E element)" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " LinkedList" + ANSI_RESET);

        addRandomValuesToLists(testList, standardList, 20);
        displayLists();

        for (int i = 0; i < 20; i++) {
            int randomIndex = random.nextInt(19);
            int randomValue = testList.get(randomIndex);

            if (!(testList.contains(randomValue) && standardList.contains(randomValue))) {
                throw new RuntimeException("Error:  This list doesn't contain referred value");
            }
        }
        printTestPassed();
    }

    /**
     * Checks the isEmpty() method.
     */
    static void isEmptyTest() {
        System.out.print(ANSI_BLUE + "\nTest of isEmpty()" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " LinkedList" + ANSI_RESET);

        if (testList.size() != 0) {
            throw new RuntimeException("Error:  This list is not empty!");
        }
        addRandomValuesToLists(testList, standardList, 20);
        displayLists();

        testList.clear();
        standardList.clear();

        displayLists();
        System.out.println(!testList.isEmpty());

        if (!testList.isEmpty()) {
            throw new RuntimeException("Error:  This list is not empty!");
        }
        printTestPassed();
    }

    /**
     * Checks the iterator() method.
     */
    static void iteratorTest() {
        System.out.print(ANSI_BLUE + "\nIterator test" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " LinkedList" + ANSI_RESET);
        addRandomValuesToLists(testList, standardList, 50);

        Iterator<Integer> iterator = testList.iterator();

        displayLists();
        System.out.print("for each      ");
        for (Object c : testList) {
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
     * Complex test of list.
     */
    static void complexTest() {
        System.out.print(ANSI_BLUE + "\nComplex test" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " LinkedList" + ANSI_RESET);

        addRandomValuesToLists(testList, standardList, 20);
        displayLists();

        if (testList.isEmpty()) {
            throw new RuntimeException("Error: The list isn't empty!");
        }

        for (int i = 0; i < 20; i++) {
            testList.removeFirst();
            standardList.removeFirst();
            displayLists();
        }

        if (testList.size() != 0) {
            throw new RuntimeException("Error: The list size should be zero!");
        }

        addRandomValuesToLists(testList, standardList, 20);
        displayLists();
        testList.clear();
        standardList.clear();
        displayLists();
        if (!testList.isEmpty()) {
            throw new RuntimeException("Error: The list should be empty!");
        }
        addRandomValuesToLists(testList, standardList, 20);
        displayLists();

        for (int i = 0; i < 20; i++) {
            if (!testList.get(i).equals(standardList.get(i))) {
                throw new RuntimeException("Error: incorrect value. The value of the " + i +
                        "-th element should be " + standardList.get(i));
            }
        }
        if (!testList.getFirst().equals(standardList.getFirst())) {
            throw new RuntimeException("Error: incorrect value. The value of the first" +
                    " element should be " + standardList.getFirst());
        }
        if (!testList.getLast().equals(standardList.getLast())) {
            throw new RuntimeException("Error: incorrect value. The value of the first" +
                    " element should be " + standardList.getLast());
        }

        for (int i = 0; i < 3; i++) {
            testList.addFirst(1000);
            testList.addLast(3000);
        }
        testList.add(10, 2000);
        testList.set(5, 4000);
        displayLists();

        if (testList.getFirst() != 1000) {
            throw new RuntimeException("Error: incorrect value. The value of the first" +
                    " element should be 1000");
        }
        if (testList.getLast() != 3000) {
            throw new RuntimeException("Error: incorrect value. The value of the last" +
                    " element should be 3000");
        }

        if (testList.get(5) != 4000) {
            throw new RuntimeException("Error: incorrect value. The value of the " +
                    " 5-th element should be 4000");
        }

        if (testList.get(10) != 2000) {
            throw new RuntimeException("Error: incorrect value. The value of the " +
                    " 10-th element should be 2000");
        }

        listsInitial();
        addRandomValuesToLists(testList, standardList, 20);
        displayLists();

        for (int i = 0; i < 3; i++) {
            testList.removeFirst();
            standardList.removeFirst();
            testList.removeLast();
            standardList.removeLast();
        }
        testList.remove(2);
        standardList.remove(2);
        displayLists();

        for (int i = 0; i < 13; i++) {
            if (!testList.get(i).equals(standardList.get(i))) {
                throw new RuntimeException("Error: incorrect value. The value of the " + i +
                        "-th element should be " + standardList.get(i));
            }
        }

        int item = standardList.get(10);
        if (!testList.contains(item)) {
            throw new RuntimeException("Error: The list should contain the element " + item);
        }


        int randomIndex = random.nextInt(9);

        if (testList.indexOf(testList.get(randomIndex)) != standardList.indexOf(standardList.get(randomIndex))) {
            throw new RuntimeException("Error: incorrect value. The value of the index should be "
                    + standardList.indexOf(randomIndex));

        }

        printTestPassed();
    }

    /**
     * Check the crash cases of PrivateLinkedList
     */
    private static void crashTest() {
        System.out.print(ANSI_BLUE + "\nCrash test" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " LinkedList" + ANSI_RESET);

        addRandomValuesToLists(testList, standardList, 30);
        displayLists();
        System.out.println();

        /* checking removing of element with wrong index. */
        int invalidIndex = -20;
        try {
            testList.remove(invalidIndex);
        } catch (ArrayIndexOutOfBoundsException exception) {

            System.out.println(ANSI_RED + "Success! The element with the wrong index has not been removed : "
                    + invalidIndex + ANSI_RED);
        }

        /* checking to get the element with wrong index. */
        invalidIndex = 55;
        try {
            testList.get(invalidIndex);
        } catch (ArrayIndexOutOfBoundsException exception) {

            System.out.println(ANSI_RED + "Success! The element with the wrong index was not received : "
                    + invalidIndex + ANSI_RED);
        }

        /* checking adding of element with wrong index. */
        invalidIndex = 2145;
        try {
            testList.add(invalidIndex, 1000);
        } catch (ArrayIndexOutOfBoundsException exception) {

            System.out.println(ANSI_RED + "Success! The element with the wrong index has not been added : "
                    + invalidIndex + ANSI_RED);
        }

        /* checking replacing of element with wrong index. */
        invalidIndex = 9457;
        try {
            testList.set(invalidIndex, 1000);
        } catch (ArrayIndexOutOfBoundsException exception) {

            System.out.println(ANSI_RED + "Success! The element with the wrong index has not been replaced : "
                    + invalidIndex + ANSI_RED);
        }

        testList.clear();
        invalidIndex = 3;

        /* checking receiving of element from empty list. */
        try {
            testList.get(invalidIndex);
        } catch (ArrayIndexOutOfBoundsException exception) {

            System.out.println(ANSI_RED + "Success! The element was not received from empty list : "
                    + invalidIndex + ANSI_RED);
        }

        invalidIndex = 9;
        /* checking removing of element from empty list. */
        try {
            testList.remove(invalidIndex);
        } catch (ArrayIndexOutOfBoundsException exception) {

            System.out.println(ANSI_RED + "Success! The element was not removed from empty list : "
                    + invalidIndex + ANSI_RED);
        }

        /* checking removing of first element from empty list. */
        try {
            testList.removeFirst();
        } catch (NoSuchElementException exception) {

            System.out.println(ANSI_RED + "Success! The first element was not removed from empty list "
                    + ANSI_RED);
        }

        /* checking removing of Last element from empty list. */
        try {
            testList.removeLast();
        } catch (NoSuchElementException exception) {

            System.out.println(ANSI_RED + "Success! The last element was not removed from empty list "
                    + ANSI_RED);
        }

        /* checking receiving of first element from empty list. */
        try {
            testList.getFirst();
        } catch (NoSuchElementException exception) {

            System.out.println(ANSI_RED + "Success! The first element was not received from empty list "
                    + ANSI_RED);
        }

        /* checking receiving of last element from empty list. */
        try {
            testList.getLast();
        } catch (NoSuchElementException exception) {

            System.out.println(ANSI_RED + "Success! The last element was not received from empty list "
                    + ANSI_RED);
        }

        printTestPassed();
    }

    /**
     * Prints double separator line.
     */
    static void printDoubleLine() {
        for (int i = 0; i < 100; i++) {
            System.out.print("=");
        }
        System.out.println();
    }

    /**
     * Displays a positive test result.
     */
    static void printTestPassed() {

        System.out.println(ANSI_GREEN + "\nTest passed" + ANSI_RESET);
        listsInitial();
        printDoubleLine();
    }

    /**
     * Initializes the tested and reference lists.
     */
    static void listsInitial() {
        testList = new PrivateLinkedList<>();
        standardList = new LinkedList<>();
    }

    /**
     * Displays the tested and reference lists.
     */
    static void displayLists() {
        System.out.println("\ntestList     " + testList.toString());
        System.out.println("standardList " + standardList.toString());
    }

    /**
     * Fills the lists with the random integer numbers.
     *
     * @param testList     The tested list.
     * @param standardList The standard list.
     * @param number       The lists size.
     */
    static void addRandomValuesToLists(PrivateLinkedList<Integer> testList,
                                       LinkedList<Integer> standardList, int number) {
        for (int i = 0; i < number; i++) {
            int randomValue = random.nextInt(100);
            testList.add(randomValue);
            standardList.add(randomValue);
        }
    }
}

