package com.shpp.p2p.cs.tperun.assignment17.Silhouettes;

import com.shpp.p2p.cs.tperun.assignment17.PrivateArrayList;
import com.shpp.p2p.cs.tperun.assignment17.PrivateHashMap;
import com.shpp.p2p.cs.tperun.assignment17.PrivateLinkedList;

import java.awt.image.BufferedImage;

/*
 * This program calculates the number of human silhouettes in an image using
 * a  breadth - first search (BFS) method. A two-dimensional black and white
 * image can be viewed as a graph, each vertex of which is associated with four
 * (or fewer if it is an edge of the image) adjacent vertices. The program is the
 * first parameter to accept the file. For example: "test.jpg". If there are no
 * parameters, then the first parameter is still "test.jpg".
 * Images should not have a complex background, ie the color of the silhouette
 * is clearly different from the background color. Also, it is assumed that in
 * addition to people, there can be only silhouettes of much smaller objects.
 *
 * The program is able to throw out  the small garbage from the calculations,
 * determine where the background and where the color of the silhouette.
 * The program can distinguish two silhouettes that are slightly stuck together.
 */
public class Silhouettes implements GraphConstants {

    /* The map of the visited vertices of the whole graph */
    private static final PrivateHashMap<String, Boolean> graphVisited = new PrivateHashMap<>();

    /* The initial graph */
    private static PrivateHashMap<String, PrivateArrayList<String>> silhouette = new PrivateHashMap<>();

    /* THe list of the number of vertices of individual silhouettes */
    private static final PrivateArrayList<Integer> numberOfVertices = new PrivateArrayList<>();

    /* The queue for storing the vertices of the graph. */
    private static final PrivateLinkedList<String> queue = new PrivateLinkedList<>();

    private static final ImageProcessor imageProc = new ImageProcessor();

    /* The flag controlling the output on double pass of the program. */
    private static int flag = 0;

    /* The maximum silhouette size. */
    public static int maxElement;

    public static void main(String[] args) {

        BufferedImage image;
        Graph graph = new Graph();
        boolean[][] array;

        /* fixing the start time of the program */
        long startTime = System.currentTimeMillis();

        /* If the file path is not entered in the command line. */
        if (args.length != 0) {
            image = imageProc.readGraphicFile(PATH_TO_FILE.concat(args[0]));
        } else {
            System.out.println("\nImage file path not entered.");
            System.out.println("Go to Run --> Edit Configuration " +
                    "and fill in the line <<Program arguments>>");
            /*
             If the line <<Program arguments>> is empty, read the file from
             the folder "assets/".
            */
            image = imageProc.readGraphicFile(PATH_TO_FILE.concat("test.jpg"));
            array = imageProc.intToBoolean(imageProc.equalizedImage(imageProc.blackAndWhiteImage(image)));
            silhouette = graph.createGraphAsMap(array);
            findSilhouettes(silhouette);
            /* Output the results of counting silhouettes to the console */
            displayMessage();
            System.exit(1);
        }

        /* If image has type .png with alpha channel */
        if (image.getType() == BufferedImage.TYPE_4BYTE_ABGR) {

            array = imageProc.getBooleanArrayAlpha(image);
            silhouette = graph.createGraphAsMap(array);
            findSilhouettes(silhouette);
            imageProc.displayFromImage(image);
            displayMessage();
            System.exit(1);

        } else {

            /* Boolean array formed from the transformed original image. */
            array = imageProc.intToBoolean(imageProc.equalizedImage(imageProc.blackAndWhiteImage(image)));
        }

        silhouette = graph.createGraphAsMap(array);

        /* Find the human silhouettes in the image */
        findSilhouettes(silhouette);

        if (flag == 0) {
            imageProc.displayFromImage(image);
        }

        if (flag == 1) {
            displayMessage();
        }

        /* Trim the edges of the image and write it to a temporary file. */
        BufferedImage tempImage = imageProc.etchEdge(image);
        imageProc.writeToFile(tempImage, PATH_TO_FILE.concat("tempImage.jpg"), "jpg");

        flag++;
        if (flag == 1) {
            graphVisited.clear();
            numberOfVertices.clear();
            args[0] = "tempImage.jpg";
            main(args);

        }

        /* fixing the end time of the program */
        long endTime = System.currentTimeMillis();

        if ((flag) == 3) {
            System.out.println("Time : " + (endTime - startTime) + " millisecond");
        }
        flag++;
    }

    /**
     * Searches for human silhouettes in a graph using a breadth-first search(BFS)
     * in a graph.
     *
     * @param image The graph in which to look for silhouettes.
     */
    static void findSilhouettes(PrivateHashMap<String, PrivateArrayList<String>> image) {

        int tempSize = 0;

        for (String lab : image.keySet()) {

            /* Place any vertex of the graph at the end of the queue */
            if (!graphVisited.containsKey(lab)) {
                queue.addLast(lab);
                bfs(lab);
            }

            /* Add the current size of the found silhouette to the array of silhouette sizes. */
            numberOfVertices.add(graphVisited.size() - tempSize);
            tempSize = graphVisited.size();
        }
    }

    /**
     * Analyzes the list of silhouette sizes, throws out trash.
     *
     * @param list The list of sizes of found silhouettes.
     * @return The right number of found silhouettes.
     */
    static int silhouetteAnalyzer(PrivateArrayList<Integer> list) {
        PrivateArrayList<Integer> array = new PrivateArrayList<>();

        /* Find the maximum silhouette size in the list. */
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) > maxElement) maxElement = list.get(i);
        }

        /* Discard garbage from the list of silhouettes */
        for (int elem : list) {
            if (elem > maxElement * GARBAGE_FACTOR) {
                array.add(elem);
            }
        }
        System.out.println("Sizes of silhouettes in pixels: " + array.toString());
        return array.size();
    }

    /**
     * The  breadth-first search method used to visit
     * all vertices of the graph.
     *
     * @param label The graph vertex.
     */
    static void bfs(String label) {

        /* Add a graph node to the queue. */
        queue.addLast(label);
        graphVisited.put(label, true);

        while (!queue.isEmpty()) {

            /* Take a node out of the queue and add it to the list of visited nodes. */
            if (!graphVisited.containsKey(queue.getFirst()))
                graphVisited.put(queue.getFirst(), true);

            String str = queue.removeFirst();

        /*
         For each unvisited vertex from the  list of the adjacent vertices,
         add the vertex to the queue.
        */
            for (String lab : silhouette.get(str)) {
                if (!graphVisited.containsKey(lab) && lab != null && !queue.contains(lab))
                    queue.addLast(lab);
            }
        }
    }

    /**
     * Outputs the results of counting silhouettes to the console.
     */
    static void displayMessage() {

        System.out.println("\nA total of " + graphVisited.size() + " vertices visited");

        System.out.println("Found " + silhouetteAnalyzer(numberOfVertices)
                + " silhouettes");
    }
}

