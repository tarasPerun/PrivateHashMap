package com.shpp.p2p.cs.tperun.assignment17;

import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 The PrivateLinkedList class represents a generic collection.
 The generic PrivateLinkedList <E> class represents a data structure
 as a doubly linked list. It combines the functionality of the list
 and the functionality of the queue.
*/
public class PrivateLinkedList<E> implements Iterable<E> {

    /* Current list size */
    private int currentSize = 0;

    /* Pointer to first node.*/
    private Node<E> headList;

    /* Pointer to last node. */
    private Node<E> tailList;

    /* Constructor */
    public PrivateLinkedList() {
    }

    /**
     * Adds an element to the end of the list.
     *
     * @param element The element to add.
     */
    public void add(E element) {

        addLast(element);
    }

    /**
     * Adds an element with the specified index.
     * Throws IndexOutOfBoundsException.
     *
     * @param index   The element index.
     * @param element The element to add.
     */
    public void add(int index, E element) {

        if (isIndexValid(index)) {

            if (index == currentSize) {
                addLast(element);
            } else if (index == 0) {
                addFirst(element);
            } else {
                Node<E> rightNode = findNode(index);
                Node<E> leftNode = rightNode.previous;

                Node<E> newNode = new Node<>(leftNode, element, rightNode);
                rightNode.previous = newNode;

                if (leftNode == null) {
                    headList = newNode;
                } else {
                    leftNode.next = newNode;
                }
                currentSize++;
            }
        }
    }

    /**
     * Adds an element to the beginning of the list.
     *
     * @param element The element to add.
     */
    public void addFirst(E element) {

        Node<E> tempNode = headList;
        Node<E> newNode = new Node<>(null, element, tempNode);
        headList = newNode;

        if (tempNode != null) {
            tempNode.previous = newNode;
        } else {
            tailList = newNode;
        }
        currentSize++;
    }


    /**
     * Adds an element to the end of the list.
     *
     * @param element The element to add.
     */
    public void addLast(E element) {

        Node<E> tempNode = tailList;
        Node<E> newNode = new Node<>(tempNode, element, null);
        tailList = newNode;

        if (tempNode != null) {
            tempNode.next = newNode;
        } else {
            headList = newNode;
        }
        currentSize++;
    }

    /**
     * Finds the list element by index.
     * Throws  ArrayIndexOutOfBoundsException.
     *
     * @param index The index of the element to find.
     * @return The element value.
     */

    public E get(int index) {

        if (isIndexValid(index)) {
            return findNode(index).value;
        }
        return null;
    }

    /**
     * Finds and returns the first element in the list.
     *
     * @return The first element.
     * Throws the NoSuchElementException.
     */
    public E getFirst() {

        if (headList == null) {
            throw new NoSuchElementException();
        }
        return headList.value;
    }

    /**
     * Finds and returns the last element in the list.
     *
     * @return The last element.
     * Throws the NoSuchElementException.
     */
    public E getLast() {

        if (tailList == null) {
            throw new NoSuchElementException();
        }
        return tailList.value;
    }

    /**
     * Turns the current list size.
     *
     * @return The current number of list elements.
     */
    public int size() {

        return currentSize;
    }

    /**
     * Sets the specified value of the element at the specified index.
     * Throws IndexOutOfBoundsException.
     *
     * @param index   The element index.
     * @param element The element to set.
     */
    public void set(int index, E element) {
        if (isIndexValid(index)) {

            Node<E> currentNode = findNode(index);
            currentNode.value = element;
        }
    }

    /**
     * Removes the element at the specified index. In this case, the current size
     * the array is decremented by one, and all the elements that were  located
     * to the right of the deletion point are displaced one position to the left.
     * Throws IndexOutOfBoundsException.
     *
     * @param index The index of the element to remove.
     * @return The removed element.
     */
    public E remove(int index) {

        if (isIndexValid(index)) {

            Node<E> tempNode = findNode(index);
            Node<E> nextNode = tempNode.next;
            Node<E> prevNode = tempNode.previous;

            if (prevNode == null) {
                headList = nextNode;
            } else {
                prevNode.next = nextNode;
                tempNode.previous = null;
            }

            if (nextNode == null) {
                tailList = prevNode;
            } else {
                nextNode.previous = prevNode;
                tempNode.next = null;
            }
            currentSize--;
            return tempNode.value;
        }
        return null;
    }

    /**
     * Removes the first element from the list and returns its value.
     *
     * @return The value of the first element.
     * Throws the NoSuchElementException.
     */
    public E removeFirst() {

        if (headList == null) {
            throw new NoSuchElementException();
        }
        Node<E> tempNode = headList;
        E element = tempNode.value;
        Node<E> rightNode = tempNode.next;

        tempNode.value = null;
        tempNode.next = null;
        headList = rightNode;

        if (rightNode != null) {
            rightNode.previous = null;
        } else {
            tailList = null;
        }
        currentSize--;
        return element;
    }

    /**
     * Removes the last element from the list and returns its value.
     *
     * @return The value of the last element.
     * Throws the NoSuchElementException.
     */
    public E removeLast() {

        if (tailList == null) {
            throw new NoSuchElementException();
        }
        Node<E> tempNode = tailList;
        E element = tempNode.value;
        Node<E> leftNode = tempNode.previous;

        tempNode.value = null;
        tempNode.previous = null;
        tailList = leftNode;

        if (leftNode != null) {
            leftNode.next = null;
        } else {
            headList = null;
        }
        currentSize--;
        return element;
    }

    /**
     * Returns the first index of the specified element.
     * If the specified item is not in the list, returns -1.
     *
     * @param element The element to find the index of.
     * @return The first index of the specified element. If the
     * specified item is not in the list, returns -1.
     */
    public int indexOf(E element) {
        int index = 0;
        if (element == null) {
            while (index < currentSize) {
                Node<E> tempNode = findNode(index);
                if (tempNode.value == null)
                    return index;
                index++;
            }
        } else {
            while (index < currentSize) {
                Node<E> tempNode = findNode(index);
                if (tempNode.value == element)
                    return index;
                index++;
            }
        }
        return -1;
    }

    /**
     * Clears the list and all of the links between nodes.
     * Removes all the elements from list.
     */
    public void clear() {

        Node<E> node = headList;
        while (node != tailList) {
            node = node.next;
            node.value = null;
        }
        headList = null;
        tailList = null;
        currentSize = 0;
    }

    /**
     * Checks if the specified element is in the list.
     *
     * @param element The tested element.
     * @return true if list contains the specified element,
     * false otherwise.
     */
    public boolean contains(E element) {
        int index = 0;
        while (index < currentSize) {
            Node<E> tempNode = findNode(index);
            if (tempNode.value == element)
                return true;
            index++;
        }
        return false;
    }

    /**
     * Checks if the list is empty.
     *
     * @return Returns true if this list contains no elements.
     * Returns false if this list contains some elements.
     */
    public boolean isEmpty() {

        return currentSize == 0;
    }


    /**
     * Finds a node in the list at the given index. If the index value is less than
     * half the size of the list, the search starts from the beginning of the list,
     * otherwise - from the end of the list.
     *
     * @param index The index of the node to find.
     * @return The node.
     */
   private Node<E> findNode(int index) {

        if (index < currentSize / 2) {
            Node<E> result = headList;
            for (int i = 0; i < index; i++) {
                result = result.next;
            }
            return result;
        } else {
            Node<E> result = tailList;
            for (int i = currentSize - 1; i > index; i--) {
                result = result.previous;
            }
            return result;
        }
    }

    /**
     * Builds a string from list elements to display it.
     *
     * @return The string from list elements to display it.
     */
    public String toString() {

        StringBuilder tempString = new StringBuilder("{");

        if (headList == null) return "{}";
        Node<E> node = headList;
        tempString.append(node.value);
        tempString.append(", ");
        while (node != tailList) {
            node = node.next;
            tempString.append(node.value);
            tempString.append(", ");
        }
        tempString = new StringBuilder(tempString.substring(0, tempString.length() - 2));
        tempString.append("}");
        if (headList == null) return "{}";

        return tempString.toString();
    }

    /**
     * Checks the validity of the index.
     *
     * @param index The checked index.
     * @return True if index is valid, otherwise throws an
     * ArrayIndexOutOfBoundsException() exception.
     */
    private boolean isIndexValid(int index) {

        if (index >= 0 && index < currentSize) {
            return true;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    @Override
    public Iterator<E> iterator() {

        return new ListIterator<>();
    }


    /**
     * The class contains methods  hasNext() and next() overrides
     * that allow iteration over the collection PrivateLinkedList.
     */
    public class ListIterator<E> implements Iterator<E> {

        private Node tempNode =  headList;

        @Override
        public boolean hasNext() {

            return tempNode != null;
        }

        @Override
        public E next() {

            Node swapNode = tempNode;
            tempNode = tempNode.next;
            //noinspection unchecked
            return (E) swapNode.value;
        }
    }

    /**
     * This class describes the data structure of a doubly-linked list.
     * Each node, in addition to the value that it stores, has a link
     * to the previous and next element.
     */
    private static class Node<E> {

        E value;
        Node<E> next;
        Node<E> previous;

        /* Constructor */
        public Node(Node<E> previous, E elem, Node<E> next) {
            this.value = elem;
            this.next = next;
            this.previous = previous;
        }
    }
}


