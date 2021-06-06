package com.shpp.p2p.cs.tperun.assignment17.Silhouettes;

import com.shpp.p2p.cs.tperun.assignment17.PrivateArrayList;
import com.shpp.p2p.cs.tperun.assignment17.PrivateHashMap;

/* This class contains some methods for working with graphs -
 * creating a vertex, creating an edge, creating a graph from a
 * two-dimensional boolean array.
 *
 * The image is considered as a graph, the vertices of which are black dots.
 * Each vertex is connected to a maximum of four neighboring vertices - left,
 * right, top and bottom. There can be less than four of these links if the
 * vertex is at the edge of the image.
 */
public class Graph {

    private final PrivateHashMap<String, PrivateArrayList<String>> graph = new PrivateHashMap<>();

    /**
     * Adds the vertex of the graph. At the same time, a list of vertices
     * associated with this vertex is created.
     *
     * @param label The graph vertex name, string variable.
     */
    public void addVertex(String label) {

        if (!graph.containsKey(label)) {

            graph.put(label, new PrivateArrayList<>());
        }
    }

    /**
     * Adds an edge connecting two vertices,
     * label1 and label2.
     *
     * @param label1 The initial vertex.
     * @param label2 The final vertex.
     */
    public void addEdge(String label1, String label2) {

        if (!graph.get(label1).contains(label2)) {

            graph.get(label1).add(label2);
        }
    }

    /**
     * Creats a graph from a two-dimensional boolean array.
     * Each element of the boolean array with the value "true" can be considered
     * as the top of the graph.
     * It is associated with a maximum of four adjacent vertices.
     *
     * @param array The input boolean array.
     * @return The graph ie HashMap<String, ArrayList<String>>.
     */
    public PrivateHashMap<String, PrivateArrayList<String>> createGraphAsMap(boolean[][] array) {

        /*
         Create an array of the names of the graph vertices from the numerical
         coordinates of the input array.
        */
        String[][] label = new String[array.length][array[0].length];
        for (int row = 0; row < array.length; row++) {
            for (int col = 0; col < array[row].length; col++) {
                label[row][col] = Integer.toString(row).concat("|").concat(Integer.
                        toString(col));
            }
        }

        /*
         Create a graph, i.e. a map of vertices and associated lists
         of neighboring vertices
        */
        for (int row = 1; row < array.length - 1; row++) {
            for (int col = 1; col < array[row].length - 1; col++) {
                if (array[row][col]) {

                    addVertex(label[row][col]);
                    if (array[row - 1][col])
                        addEdge(label[row][col], label[row - 1][col]);
                    if (array[row + 1][col])
                        addEdge(label[row][col], label[row + 1][col]);
                    if (array[row][col - 1])
                        addEdge(label[row][col], label[row][col - 1]);
                    if (array[row][col + 1])
                        addEdge(label[row][col], label[row][col + 1]);
                }
            }
        }
        return graph;
    }
}




