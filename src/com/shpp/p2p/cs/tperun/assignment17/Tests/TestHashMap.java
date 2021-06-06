package com.shpp.p2p.cs.tperun.assignment17.Tests;

import com.shpp.p2p.cs.tperun.assignment17.PrivateHashMap;

import java.util.*;


public class TestHashMap {

    public static PrivateHashMap<Integer, String> testMap = new PrivateHashMap<>();
    public static HashMap<Integer, String> standardMap = new HashMap<>();
    private static final Random random = new Random();

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public static void performTests() {

       // putTest();
      //  getKeyTest();
        removeKeyTest();
      //  sizeTest();
/*        isEmptyTest();
        containsKeyTest();
        keySetTest();
        iteratorTest();
        complexTest();*/
    }

    /**
     * Checks the put() method.
     */
    private static void putTest() {

        System.out.print(ANSI_BLUE + "Test of put()" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " HashMap" + ANSI_RESET);

        System.out.println();
        addRandomValuesToMaps(testMap, standardMap, 100);
        displayMaps();
        for (int i = 0; i < 100; i++) {
            if (!testMap.get(i).equals(standardMap.get(i))) {
                throw new RuntimeException("Error! The value of " + i + "th element should be " + standardMap.get(i));
            }
        }
        try {
            testMap.put(5, " ttt");
            displayTestedMap();
            for (int i = 0; i < 100; i++) {

                if (testMap.get(5).equals(" ttt")) {
                    throw new RuntimeException();
                }
            }
        } catch (RuntimeException exception) {
            System.out.println("\nCheck the wrong element with key 5");
            System.out.println(ANSI_RED + "Success! The element has the expected invalid ttt value instead of the correct " +
                    standardMap.get(5) + ANSI_RESET);

        }
        System.out.println();
        for (int i = 0; i < 10; i++) {
            testMap.put(5, " " + 100 * i);

        }


        if (!testMap.get(5).equals(" 900")) {
            throw new RuntimeException("Error! The value of " + 5 + "th element should be 900");
        }
        if (testMap.size() != standardMap.size()) {
            throw new RuntimeException("Error! The size of the map should be " + standardMap.size());
        }

        testMap.clear();
        standardMap.clear();
        testMap.put(null, "hjhj");
        standardMap.put(null, "hjhj");

        testMap.put(null, "ooooo");
        standardMap.put(null, "ooooo");

        testMap.put(null, "o");
        standardMap.put(null, "o");

        testMap.put(5, null);
        standardMap.put(5, null);
        testMap.put(0, null);
        standardMap.put(0, null);
        displayMaps();

        PrivateHashMap<String, String> tmap = new PrivateHashMap<>();
        HashMap<String, String> smap = new HashMap<>();

        tmap.put(null, "hjhj");
        smap.put(null, "hjhj");
        tmap.put("tt", null);
        smap.put("tt", null);
        System.out.println();
        System.out.println(tmap.get(null));
        System.out.println(smap.get(null));
        System.out.println();
        System.out.println(tmap.get("089080809809"));
        System.out.println(smap.get("089080809809"));
        System.out.println();
        System.out.println(tmap.get("tt"));
        System.out.println(smap.get("tt"));
        System.out.println();
        displayMaps();
        mapsInitial();

        testMap.clear();
        standardMap.clear();
        testMap.put(null, "hjhj");
        standardMap.put(null, "hjhj");

        testMap.put(null, "ooooo");
        standardMap.put(null, "ooooo");

        testMap.put(null, "o");
        standardMap.put(null, "o");

        testMap.put(5, null);
        standardMap.put(5, null);
        testMap.put(0, null);
        standardMap.put(0, null);
        displayMaps();
        printTestPassed();
    }

    /**
     * Checks the get(K key) method.
     */
    private static void getKeyTest() {

        System.out.print(ANSI_BLUE + "\nTest of get(K key)" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " HashMap" + ANSI_RESET);

        System.out.println();
        mapsInitial();
        addRandomValuesToMaps(testMap, standardMap, 20);
        displayMaps();

        for (int i = 0; i < 20; i++) {
            int randomIndex = random.nextInt(19);
            System.out.println("testMap.get(" + randomIndex + ")     = " + testMap.get(randomIndex));
            System.out.println("standardMap.get(" + randomIndex + ") = " + standardMap.get(randomIndex));
            System.out.println();


            if (!testMap.get(randomIndex).equals(standardMap.get(randomIndex))) {

                throw new RuntimeException("Error: incorrect value. The value of the element with the key "
                        + randomIndex + " should be " + standardMap.get(randomIndex));
            }
        }
        if (testMap.get(1000) != null) {

            throw new RuntimeException("Error: incorrect value. The value of the element with the " +
                    "non-existent key  1000 should be null");
        }

        try {
            if (testMap.get(2000) == null) {
                throw new RuntimeException();
            }
        } catch (RuntimeException exception) {
            System.out.println("\nCheck if null gives the value of an element by a non-existent key");
            System.out.println(ANSI_RED + "Success!  An attempt to return the value of an element with " +
                    "a nonexistent index is expected to be null " + ANSI_RESET);
        }

        printTestPassed();
    }


    /**
     * Checks the remove(K key) method.
     */
    static void removeKeyTest() {

        System.out.print(ANSI_BLUE + "\nTest of remove(int index)" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " HashMap" + ANSI_RESET);
        System.out.println();
        addRandomValuesToMaps(testMap, standardMap, 20);
        displayMaps();

        for (int i = 0; i < 10; i++) {
            testMap.remove(i);
            standardMap.remove(i);
        }
        displayMaps();

        if (testMap.size() != 10) {
            throw new RuntimeException("Error: incorrect value. The map size should be equal to ten");
        }

        for (int i = 10; i < 19; i++) {
            testMap.remove(i);
            standardMap.remove(i);
        }
        displayMaps();

        if (testMap.size() != 1) {
            throw new RuntimeException("Error: incorrect value. The map size should be equal to one");
        }

        if (!testMap.get(19).equals(standardMap.get(19))) {
            throw new RuntimeException("Error: incorrect value. The value of this element should be " +
                    standardMap.get(19));
        }

        try {
            if (testMap.get(2000) == null) {
                throw new RuntimeException();
            }
        } catch (RuntimeException exception) {
            System.out.println("\nCheck if null returns the removing of an element with a nonexistent key");
            System.out.println(ANSI_RED + "Success!  An attempt to remove the element with " +
                    "a nonexistent index is expected to be null " + ANSI_RESET);
        }

        mapsInitial();
        displayMaps();

        PrivateHashMap<String, String> tmap = new PrivateHashMap<>();
        HashMap<String, String> smap = new HashMap<>();

        tmap.put(null, "hjhj");
        smap.put(null, "hjhj");
        tmap.put("tt", null);
        smap.put("tt", null);
        System.out.println();
        System.out.println(tmap.remove(null));
        System.out.println(smap.remove(null));
        System.out.println();
        System.out.println(tmap.remove("089080809809"));
        System.out.println(smap.remove("089080809809"));
        System.out.println();
        System.out.println(tmap.remove("tt"));
        System.out.println(smap.remove("tt"));
        System.out.println();
        displayMaps();


        PrivateHashMap<String,String> testMap1 = new PrivateHashMap<>();
        for (int i = 0; i < 10; i++) {

            testMap1.put("Key " + i, "Value " + i);
        }
        System.out.println(testMap1.toString());

        for (int i = 0; i < 5; i++) {

          testMap1.remove("Key " + i);
        }
        System.out.println(testMap1.toString());

        printTestPassed();
    }

    /**
     * Checks the size() method.
     */
    static void sizeTest() {

        System.out.print(ANSI_BLUE + "\nTest of size()" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " HashMap" + ANSI_RESET);
        System.out.println();

        for (int j = 0; j < 3; j++) {
            for (int i = 10 * j; i < 10 + 10 * j; i++) {
                int randomValue = random.nextInt(100);

                testMap.put(i, " " + randomValue);
                standardMap.put(i, " " + randomValue);
            }

            displayMaps();

            System.out.println("size =  " + testMap.size());
            System.out.println();

            if (testMap.size() != standardMap.size()) {
                throw new RuntimeException("Error: incorrect size. The value of the map size " +
                        " should be " + standardMap.size());
            }
        }
        mapsInitial();
        displayMaps();
        System.out.println("size =  " + testMap.size());
        if (testMap.size() != 0) {
            throw new RuntimeException("Error: incorrect size. The value of the map size " +
                    " should be " + standardMap.size());
        }

        printTestPassed();
    }

    /**
     * Checks the clear() method.
     */
    static void clearTest() {
        System.out.print(ANSI_BLUE + "\nTest of clear()" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " HashMap" + ANSI_RESET);

        System.out.println();
        mapsInitial();
        addRandomValuesToMaps(testMap, standardMap, 20);
        displayMaps();

        testMap.clear();
        standardMap.clear();

        System.out.println();
        displayMaps();

        if (testMap.size() != 0) {
            throw new RuntimeException("Error: incorrect value. The map size must be equal zero");
        }
        printTestPassed();
    }

    /**
     * Checks the isEmpty() method.
     */
    static void isEmptyTest() {

        System.out.print(ANSI_BLUE + "\nTest of isEmpty()" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " HashMap" + ANSI_RESET);
        mapsInitial();
        if (testMap.size() != 0) {
            throw new RuntimeException("Error:  This map is not empty!");
        }
        System.out.println();
        mapsInitial();
        addRandomValuesToMaps(testMap, standardMap, 20);
        displayMaps();

        testMap.clear();
        standardMap.clear();

        displayMaps();
        if (!testMap.isEmpty()) {
            throw new RuntimeException("Error:  This map is not empty!");
        }
        printTestPassed();
    }

    /**
     * Checks the containsKey(K key) method.
     */
    static void containsKeyTest() {
        System.out.print(ANSI_BLUE + "\nTest of containsKey(K key)" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " HashMap" + ANSI_RESET);

        System.out.println();
        mapsInitial();
        addRandomValuesToMaps(testMap, standardMap, 20);
        displayMaps();

        for (int i = 0; i < 20; i++) {
            int randomIndex = random.nextInt(19);
            if (!(testMap.containsKey(randomIndex) && standardMap.containsKey(randomIndex))) {
                throw new RuntimeException("Error:  This map doesn't contain referred key");
            }
        }
        printTestPassed();
    }

    /**
     * Checks the keySet() method.
     */
    private static void keySetTest() {

        System.out.print(ANSI_BLUE + "Test of keySet()" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " HashMap" + ANSI_RESET);

        System.out.println();
        addRandomValuesToMaps(testMap, standardMap, 100);
        displayMaps();

        System.out.println("testMap.keySet     " + testMap.keySet().toString());
        System.out.println("standardMap.keySet " + standardMap.keySet().toString());
        System.out.println();

        System.out.print("testMap.get(testMap.keySet)    ");

        for (Integer i : testMap.keySet()) {
            System.out.print(testMap.get(i) + " ");
        }
        System.out.println();

        if (!testMap.keySet().equals(standardMap.keySet())) {

            throw new RuntimeException("Error: incorrect values. The keySets are not equal ");
        }

        printTestPassed();
    }

    /**
     * Checks the iterator() method.
     */
    private static void iteratorTest() {

        System.out.print(ANSI_BLUE + "\nIterator test" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " HashMap" + ANSI_RESET);

        addRandomValuesToMaps(testMap, standardMap, 20);
        System.out.println();
        displayMaps();

        Iterator<Integer> iterator = testMap.iterator();

        System.out.print("\nfor each (keys):        ");
        for (Integer c : testMap.keySet()) {
            System.out.print(c + " ");
        }
        System.out.println();
        System.out.print("for each (values):     ");
        for (Integer c : testMap.keySet()) {
            System.out.print(testMap.get(c) + " ");
        }

        System.out.println("\n\nmap size = " + testMap.size() + "\n");

        System.out.print("hasNext()-next()(keys):    ");
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println();
        iterator = testMap.iterator();

        System.out.print("hasNext()-next()(values): ");
        while (iterator.hasNext()) {
            System.out.print(testMap.get(iterator.next()) + " ");
        }
        System.out.println();
        printTestPassed();
    }

    /**
     * Complex test of map.
     */
    private static void complexTest() {

        System.out.print(ANSI_BLUE + "Complex test" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + " HashMap" + ANSI_RESET);
        mapsInitial();

        if (!testMap.isEmpty()) {
            throw new RuntimeException("Error: The map should be empty!");
        }

        System.out.println();
        addRandomValuesToMaps(testMap, standardMap, 20);
        displayMaps();


        for (Integer i : testMap.keySet()) {
            testMap.remove(i);
        }
        displayMaps();
        if (testMap.size() != 0) {
            throw new RuntimeException("Error: The map size should be zero!");
        }
        standardMapInitial();

        System.out.println();
        addRandomValuesToMaps(testMap, standardMap, 20);
        displayMaps();

        testMap.clear();
        standardMap.clear();
        displayMaps();

        if (testMap.size() != 0) {
            throw new RuntimeException("Error: The map size should be zero!");
        }

        System.out.println();
        addRandomValuesToMaps(testMap, standardMap, 20);
        displayMaps();

        for (Integer i : testMap.keySet()) {
            if (!testMap.get(i).equals(standardMap.get(i))) {
                throw new RuntimeException("Error: incorrect value. The value with the key " + i +
                        " should be " + standardMap.get(i));
            }
        }
        for (int i = 0; i < 5; i++) {
            int randomKey = random.nextInt(19);
            if (!testMap.get(randomKey).equals(standardMap.get(randomKey))) {
                throw new RuntimeException("Error: incorrect value. The value with the key " + randomKey +
                        " should be " + standardMap.get(randomKey));
            }
        }
        mapsInitial();
        System.out.println("testMap.size() = " + testMap.size());
        System.out.println("standardMap.size() = " + testMap.size());
        if (testMap.size() != 0) {
            throw new RuntimeException("Error: The map size should be zero!");
        }

        for (int i = 0; i < 10; i++) {
            testMap.put(5, " 1000");
            standardMap.put(5, " 1000");
        }
        displayMaps();
        System.out.println("testMap.size() = " + testMap.size());
        System.out.println("standardMap.size() = " + standardMap.size());
        if (testMap.size() != standardMap.size()) {
            throw new RuntimeException("Error: The map size should be zero!");
        }

        addRandomValuesToMaps(testMap, standardMap, 20);
        displayMaps();


        testMap.put(5, " 5000"); // intentional error generation
        if (testMap.get(5).equals(" 5000")) {
            System.out.println(ANSI_RED + "Expected error: The value with the key 5" +
                    " should be " + standardMap.get(5) + ANSI_RESET);
        }

        testMap.put(10, " 5000"); // intentional error generation
        if (testMap.remove(10).equals(" 5000")) {
            System.out.println(ANSI_RED + "Expected error: The value with the key 10" +
                    " should be " + standardMap.remove(10) + ANSI_RESET);
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
        printDoubleLine();
    }


    /**
     * Initializes the standard map.
     */
    private static void standardMapInitial() {
        standardMap = new HashMap<>();
    }

    /**
     * Initializes the tested and standard maps.
     */
    private static void mapsInitial() {
        testMap = new PrivateHashMap<>();
        standardMap = new HashMap<>();
    }

    /**
     * Fills the maps with the pairs of integer numbers and random strings;
     *
     * @param testMap     The tested map.
     * @param standardMap The standard map.
     * @param number      The map's size.
     */
    static void addRandomValuesToMaps(PrivateHashMap<Integer, String> testMap,
                                      HashMap<Integer, String> standardMap, int number) {
        for (int i = 0; i < number; i++) {
            String randomValue = " " + random.nextInt(1000);
            testMap.put(i, randomValue);
            standardMap.put(i, randomValue);
        }
    }

    /**
     * Displays the tested map.
     */
    private static void displayTestedMap() {

        System.out.println("testMap     " + testMap.toString());
    }

    /**
     * Displays the tested and reference maps.
     */
    private static void displayMaps() {

        System.out.println("testMap     " + testMap.toString());
        System.out.println("standardMap " + standardMap.toString());
    }
}
