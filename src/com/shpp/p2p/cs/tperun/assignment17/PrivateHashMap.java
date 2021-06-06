package com.shpp.p2p.cs.tperun.assignment17;

import java.util.*;

/*
 The PrivateHashMap class uses a hash table to store data, providing fast
 execution times for get () and put () queries on large sets. The class
 implements the Map interface (storing data in the form of key-value pairs).
 Keys and values can be of any type, including null. Moreover, all keys must
 be unique, and the values can be repeated. This implementation does not
 guarantee the order of the elements.
*/
public class PrivateHashMap<K, V> implements Iterable<K> {

    private final int INITIAL_SIZE = 16;
    private HashNode<K, V>[] hashNodes;
    public int currentSize = 0;
    private int basketSize = INITIAL_SIZE;

    public PrivateHashMap() {

        //noinspection unchecked
        hashNodes = new HashNode[INITIAL_SIZE];
    }


    /**
     * Adds the specified key-value pair to the map. If the map already contains
     * the given key, the old value is replaced with the new one, the key
     * remains the same.
     *
     * @param key   The specified key.
     * @param value The specified key.
     */
    public void put(K key, V value) {

        /*
         Check the degree of filling the array, if necessary,
         increase the array of elements and again fill it.
        */
        if (checkArray(currentSize)) {
            refactorArray();
        }

        if (key == null) {
            putNullASKey(value);
            return;
        }

        /* Create a new node from a key-value pair. */
        HashNode<K, V> newNode = new HashNode<>(key, value);

        /* Get the index of a cell of an array using a hash function. */
        int index = getHashIndex(key);

        /*
         If the cell is empty, then just fill it
         created by a new node.
        */
        if (hashNodes[index] == null) {
            justAddNode(index, newNode);

        } else {
            HashNode<K, V> tempNode = hashNodes[index];
            while (tempNode != null) {

                /*
                 If the keys match, we replace the value by this key,
                 if this value differs from the existing one.
                */
                if (tempNode.getKey().equals(key)) {
                    tempNode.setValue(value);
                    break;
                }

                if (tempNode.next == null) {
                    tempNode.next = newNode;
                    currentSize++;
                    break;
                }
                tempNode = tempNode.next;
            }
        }
    }

    /**
     * Inserts a null-value pair into the map.
     *
     * @param value The node value to insert.
     */
    private void putNullASKey(V value) {

        HashNode<K, V> newNode = new HashNode<>(null, value);
        if (hashNodes[0] == null) {
            justAddNode(0, newNode);

        } else {
            for (HashNode<K, V> tempNode = hashNodes[0]; tempNode != null; tempNode = tempNode.next) {

                /* If the keys match, we replace the value by this key.*/
                if (tempNode.getKey() == null) {
                    tempNode.setValue(value);
                    break;
                }
                if (tempNode.next == null) {
                    tempNode.next = newNode;
                    currentSize++;
                    break;
                }
            }
        }
    }

    /**
     * Returns the value of a map element by its key
     * without deleting the element
     *
     * @param key The element key.
     * @return The element value.
     */
    public V get(K key) {

        if (findNode(key) == null) {
            return null;
        }
        return findNode(key).getValue();
    }


    /**
     * Removes a map element by its key and returns the value of
     * the deleted element.
     *
     * @param key The element key.
     * @return The removed element value.
     */
    public V remove(K key) {

        if (findNode(key) == null) {
            return null;
        }

        V value;
        int index = getHashIndex(key);
        HashNode<K, V> tempNode = hashNodes[index];

        value = tempNode.getValue();
       // if (key == tempNode.getKey() || key.equals(tempNode.getKey())) {
        if (Objects.equals(key,tempNode.getKey())) {

            hashNodes[index] = tempNode.next;
            currentSize--;
            return value;
        }
        while (tempNode.next != null) {
          //  if (tempNode.next.getKey() == key || tempNode.next.getKey().equals(key)) {
                if (Objects.equals(key,tempNode.getKey())) {
                tempNode.next = tempNode.next.next;
                currentSize--;
                return value;
            }
            tempNode = tempNode.next;
        }
        return null;
    }

    /**
     * This is a hash function, the algorithm of which is taken from here:
     * https://habr.com/ru/post/169733/
     *
     * @param key The key of the element by which the hash code is calculated.
     * @return The hash code.
     */
    private int getHashIndex(K key) {

        if (key == null) {
            return 0;
        }
        int hash = 17;
        hash = hash * 37 + key.hashCode();
        return Math.abs(hash % basketSize);
    }

    /**
     * Increases the size of the nodes array and overwrites the nodes
     * by new cells according to the new indices.
     * <p>
     * Throws RunTimeException when the size of the array is exceeded.
     */
    private void refactorArray() {

        if (hashNodes.length >= Integer.MAX_VALUE / 2) {
            throw new RuntimeException("Maximum array size exceeded");
        }
        int newBasketSize = basketSize * 2;

        //noinspection unchecked
        HashNode<K, V>[] newHashNodes = new HashNode[newBasketSize];
        PrivateArrayList<HashNode<K, V>> list = new PrivateArrayList<>();

        for (int i = 0; i < hashNodes.length; i++) {

            if (hashNodes[i] != null) {
                HashNode<K, V> tempNode = hashNodes[i];
                while (tempNode != null) {
                    list.add(tempNode);
                    tempNode = tempNode.next;
                }
            }
        }
        currentSize = 0;
        hashNodes = newHashNodes;
        basketSize = newBasketSize;

        for (int i = 0; i < list.size(); i++) {
            put(list.get(i).getKey(), list.get(i).getValue());
        }
    }

    /**
     * Returns the current number of map elements.
     *
     * @return The current number of map elements.
     */
    public int size() {

        return currentSize;
    }

    /**
     * Clears the map and fills the node array with null.
     */
    public void clear() {

        Arrays.fill(hashNodes, null);
        currentSize = 0;
    }

    /**
     * Checks if the map is empty.
     *
     * @return Returns true if this map contains no elements.
     * Returns false if this map contains some elements.
     */
    public boolean isEmpty() {

        return currentSize == 0;
    }

    /**
     * Checks if the map contains an element with the specified key.
     *
     * @param key The element key.
     * @return True if the map contains an element with the specified key,
     * false otherwise.
     */
    public boolean containsKey(K key) {

        return findNode(key) != null;
    }

    /**
     * This method creates a Set of K items and puts all of the keys from the
     * HashMap into this Set
     *
     * @return The set of keys.
     */
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for (int i = 0; i < hashNodes.length; i++) {
            if (hashNodes[i] != null) {
                keySet.add(hashNodes[i].getKey());
                for (HashNode<K, V> tempNode = hashNodes[i]; tempNode != null; tempNode = tempNode.next) {
                    if (tempNode.next != null) {
                        keySet.add(tempNode.next.getKey());
                    }
                }
            }
        }
        return keySet;
    }


    /**
     * Builds a string from list elements to display it.
     *
     * @return The string from list elements to display it.
     */
    public String toString() {

        StringBuilder tempString = new StringBuilder("{");
        if (currentSize == 0) return "{}";

        for (int i = 0; i < hashNodes.length; i++) {

            HashNode<K, V> tempNode = hashNodes[i];
            if (tempNode != null) {
                tempString.append(tempNode.getKey()).append("=").append(tempNode.getValue()).append(", ");
                tempNode = tempNode.next;
                while (tempNode != null) {

                    tempString.append(tempNode.getKey()).append("=").append(tempNode.getValue()).append(", ");
                    tempNode = tempNode.next;
                }
            }
        }
        String result = tempString.toString();
        return result.substring(0, result.length() - 2).concat("}");
    }


    /**
     * Checks the degree of fullness of an array.
     *
     * @param size The current number of array elements.
     * @return True if size > basketSize * loadFactor,
     * false otherwise.
     */
    private boolean checkArray(int size) {

        double loadFactor = 0.75;
        return size > basketSize * loadFactor;
    }

    /**
     * Adds the node to array.
     *
     * @param index   The node index.
     * @param newNode The node to add.
     */
    private void justAddNode(int index, HashNode<K, V> newNode) {
        hashNodes[index] = newNode;
        currentSize++;
    }

    @Override
    public Iterator<K> iterator() {

        return new MapIterator();
    }

    /**
     * The class contains methods  hasNext() and next() overrides
     * that allow iteration over the collection PrivateHashMap.
     */
    public class MapIterator implements Iterator<K> {

        int counter = 0;
        PrivateArrayList<K> tempNodes = new PrivateArrayList<>();

        MapIterator() {

            for (int i = 0; i < hashNodes.length; i++) {
                if (hashNodes[i] != null) {
                    tempNodes.add(hashNodes[i].getKey());
                    for (HashNode<K, V> temp = hashNodes[i]; temp != null; temp = temp.next) {
                        if (temp.next != null) {
                            tempNodes.add(temp.next.getKey());
                        }
                    }
                }
            }
        }

        @Override
        public final boolean hasNext() {
            return counter < currentSize;
        }

        @Override
        public K next() {
            return tempNodes.get(counter++);
        }
    }

    /**
     * This class describes the data structure of a hash map.
     * Each node, in addition to the pair(key and value) that it stores, has a link
     * to the next node.
     *
     * @param <K> - Key in pair
     * @param <V> - Value in pair
     */
    public class HashNode<K, V> {

        private final K key;
        private V value;
        private HashNode<K, V> next;

        public HashNode(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", value=" + value +
                    ", next=" + next +
                    '}';
        }

    }


    /**
     * Search for a node by key.
     *
     * @param key The key by which to find the node.
     * @return The node with specified key.
     */
    public HashNode<K, V> findNode(K key) {

        int index = getHashIndex(key);
        if (key == null) {
            for (HashNode<K, V> tempNode = hashNodes[0]; tempNode != null; tempNode = tempNode.next) {
                if (tempNode.getKey() == null) {
                    return tempNode;
                }
            }
        }
        for (HashNode<K, V> tempNode = hashNodes[index]; tempNode != null; tempNode = tempNode.next) {
            if (tempNode.getKey().equals(key)) {
                return tempNode;
            }
        }
        return null;
    }
}


