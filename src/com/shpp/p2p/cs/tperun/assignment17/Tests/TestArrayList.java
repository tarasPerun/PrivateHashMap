package com.shpp.p2p.cs.tperun.assignment17.Tests;

import com.shpp.p2p.cs.tperun.assignment17.PrivateArrayList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/* This class contains methods for testing the class PrivateArrayList */
public class TestArrayList {

    private static PrivateArrayList<Integer> testList = new PrivateArrayList<>();
    private static ArrayList<Integer> standardList = new ArrayList<>();
    private static final Random random = new Random();

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED = "\u001B[31m";



    public static void performTests() {

        addTest();
        addIndexTest();
        getTest();
        sizeTest();
        setTest();
        removeTest();
        indexOfTest();
        clearTest();
        containsTest();
        isEmptyTest();
        reverseTest();
        iteratorTest();
        crashTest();
        complexTest();
    }



    /**
     * Checks the add() method
     */
    static void addTest() {
        printDoubleLine();
        System.out.print(ANSI_BLUE + "Test of add(E element)" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " ArrayList" + ANSI_RESET);
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
        System.out.println(ANSI_YELLOW + " ArrayList" + ANSI_RESET);

        for (int i = 0; i < 10; i++) {
            testList.add(i);
            standardList.add(i);
        }
        displayLists();
        for (int i = 0; i < 10; i++) {
            testList.add(5, i * 10);
            standardList.add(5, i * 10);
        }
        for (int i = 0; i < 10; i++) {
            if (!testList.get(i).equals(standardList.get(i))) {
                throw new RuntimeException("Error: incorrect addition. The value of the "
                        + i + "-th element should be " + standardList.get(i));
            }
        }
        displayLists();
        printTestPassed();
    }

    /**
     * Checks the get(int index) method.
     */
    static void getTest() {
        System.out.print(ANSI_BLUE + "\nTest of get(int index)" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " ArrayList" + ANSI_RESET);
        addRandomValuesToLists(testList, standardList, 20);
        displayLists();

        for (int i = 0; i < 20; i++) {
            int randomIndex = random.nextInt(19);
            if (!testList.get(randomIndex).equals(standardList.get(randomIndex))) {
                throw new RuntimeException("Error: incorrect value. The value of the "
                        + randomIndex + "-th element should be " + standardList.get(randomIndex));
            }
        }
        displayLists();
        printTestPassed();
    }


    /**
     * Checks the size() method.
     */
    static void sizeTest() {
        System.out.print(ANSI_BLUE + "\nTest of size()" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " ArrayList" + ANSI_RESET);

        for (int j = 0; j < 5; j++) {
            addRandomValuesToLists(testList, standardList, 10);
            displayLists();
            System.out.println("size =  " + testList.size());

            if (testList.size() != standardList.size()) {
                throw new RuntimeException("Error: incorrect size. The value of the list size " +
                        " must be " + standardList.size());
            }
        }
        printTestPassed();
    }

    /**
     * Checks the set(int index, E element) method.
     */
    static void setTest() {
        System.out.print(ANSI_BLUE + "\nTest of set(int index, E element )" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " ArrayList" + ANSI_RESET);
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
        System.out.println(ANSI_YELLOW + " ArrayList" + ANSI_RESET);
        addRandomValuesToLists(testList, standardList, 20);
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
     * Checks the indexOf(E element) method.
     */
    static void indexOfTest() {
        System.out.print(ANSI_BLUE + "\nTest of indexOf(E element)" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " ArrayList" + ANSI_RESET);
        addRandomValuesToLists(testList, standardList, 20);
        displayLists();

        for (int i = 0; i < 20; i++) {
            int randomIndex = random.nextInt(19);

            if (testList.indexOf(randomIndex) != standardList.indexOf(randomIndex)) {
                throw new RuntimeException("Error: incorrect value. The value of the index should be "
                        + standardList.indexOf(randomIndex));
            }
        }
        displayLists();
        printTestPassed();
    }

    /**
     * Checks the clear() method.
     */
    static void clearTest() {
        System.out.print(ANSI_BLUE + "\nTest of clear()" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " ArrayList" + ANSI_RESET);
        addRandomValuesToLists(testList, standardList, 20);
        displayLists();

        testList.clear();
        standardList.clear();

        displayLists();

        if (testList.size() != 0) {
            throw new RuntimeException("Error: incorrect value. The list size should be equal zero");
        }
        printTestPassed();
    }

    /**
     * Checks the contains(E element) method.
     */
    static void containsTest() {
        System.out.print(ANSI_BLUE + "\nTest of contains(E element)" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " ArrayList" + ANSI_RESET);
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
        System.out.println(ANSI_YELLOW + " ArrayList" + ANSI_RESET);
        if (testList.size() != 0) {
            throw new RuntimeException("Error:  This list is not empty!");
        }

        addRandomValuesToLists(testList, standardList, 20);
        displayLists();

        testList.clear();
        standardList.clear();

        if (testList.size() != 0) {
            throw new RuntimeException("Error:  This list is not empty!");
        }
        printTestPassed();
    }

    /**
     * Checks the reverse() method.
     */
    static void reverseTest() {
        System.out.print(ANSI_BLUE + "\nTest of reverse()" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " ArrayList" + ANSI_RESET);
        addRandomValuesToLists(testList, standardList, 20);

        testList.reverse();
        displayLists();

        for (int i = 0; i < 20; i++) {
            if (!(testList.get(i).equals(standardList.get(20 - i - 1))))
                throw new RuntimeException("Error: The value of the " + i + "-th element should be " +
                        standardList.get(20 - i - 1) + "!");
        }
        printTestPassed();
    }

    /**
     * Checks the iterator() method.
     */
    static void iteratorTest() {
        System.out.print(ANSI_BLUE + "\nIterator test" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " ArrayList" + ANSI_RESET);
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
        System.out.println(ANSI_YELLOW + " ArrayList" + ANSI_RESET);

        addRandomValuesToLists(testList, standardList, 20);
        displayLists();

        if (testList.size() != 20) {
            throw new RuntimeException("Error: The list size should be 20!");
        }

        for (int i = 0; i < 20; i++) {
            testList.remove(0);
            standardList.remove(0);
        }

        displayLists();
        if (testList.size() != 0) {
            throw new RuntimeException("Error: The list size should be zero!");
        }

        addRandomValuesToLists(testList, standardList, 10);
        for (int i = 0; i < 3; i++) {
            testList.add(5, 1000);
        }
        displayLists();
        if (testList.get(6) != 1000) {
            throw new RuntimeException("Error: The 6-th list element  should be 1000!");
        }
        testList.set(8, 5000);
        displayLists();
        if (testList.get(8) != 5000) {
            throw new RuntimeException("Error: The 8-th list element  should be 5000!");
        }
        testList.clear();
        displayLists();
        if (!testList.isEmpty()) {
            throw new RuntimeException("Error: The list should be empty!");
        }
        listsInitial();
        addRandomValuesToLists(testList, standardList, 30);
        displayLists();
        int item = standardList.get(20);
        if (!testList.contains(item)) {
            throw new RuntimeException("Error: The list should contain the element " + item);
        }
        testList.reverse();
        displayLists();

        for (int i = 0; i < 30; i++) {

            if (!(testList.get(i).equals(standardList.get(30 - i - 1))))
                throw new RuntimeException("Error: The value of the " + i + "-th element should be " +
                        standardList.get(30 - i - 1) + "!");
        }
        printTestPassed();

    }

    /**
     * Check the crash cases of PrivateArrayList
     */
    private static void crashTest() {
        System.out.print(ANSI_BLUE + "\nCrash test" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " ArrayList" + ANSI_RESET);

        addRandomValuesToLists(testList, standardList, 30);
        displayLists();
        System.out.println();

        /* checking removing of element with wrong index. */
        int invalidIndex = -5;
        try {
            testList.remove(invalidIndex);
        } catch (ArrayIndexOutOfBoundsException exception) {

            System.out.println(ANSI_RED+"Success! The element with the wrong index has not been removed : "
                    + invalidIndex + ANSI_RED);
                   }

        /* checking to get the element with wrong index. */
        invalidIndex = 40;
        try {
            testList.get(invalidIndex);
        } catch (ArrayIndexOutOfBoundsException exception) {

            System.out.println(ANSI_RED+"Success! The element with the wrong index was not received : "
                    + invalidIndex + ANSI_RED);
                    }

        /* checking adding of element with wrong index. */
        invalidIndex = 345;
        try {
            testList.add(invalidIndex, 1000);
        } catch (ArrayIndexOutOfBoundsException exception) {

            System.out.println(ANSI_RED+"Success! The element with the wrong index has not been added : "
                    + invalidIndex + ANSI_RED);
        }


        /* checking replacing of element with wrong index. */
        invalidIndex = 3457;
        try {
            testList.set(invalidIndex, 1000);
        } catch (ArrayIndexOutOfBoundsException exception) {

            System.out.println(ANSI_RED+"Success! The element with the wrong index has not been replaced : "
                    + invalidIndex + ANSI_RED);
        }

        testList.clear();
        invalidIndex = 5;

        /* checking receiving of element from empty list. */
        try {
            testList.get(invalidIndex);
        } catch (ArrayIndexOutOfBoundsException exception) {

            System.out.println(ANSI_RED+"Success! The element from empty list was not received : "
                    + invalidIndex + ANSI_RED);
        }
        invalidIndex = 15;
        /* checking removing of element from empty list. */
        try {
            testList.remove(invalidIndex);
        } catch (ArrayIndexOutOfBoundsException exception) {

            System.out.println(ANSI_RED+"Success! The element from empty list was not removed : "
                    + invalidIndex + ANSI_RED);
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
        testList = new PrivateArrayList<>();
        standardList = new ArrayList<>();
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
    static void addRandomValuesToLists(PrivateArrayList<Integer> testList,
                                       ArrayList<Integer> standardList, int number) {
        for (int i = 0; i < number; i++) {
            int randomValue = random.nextInt(100);
            testList.add(randomValue);
            standardList.add(randomValue);
        }
    }


}
