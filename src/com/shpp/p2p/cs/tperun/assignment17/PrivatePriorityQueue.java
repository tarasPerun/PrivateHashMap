package com.shpp.p2p.cs.tperun.assignment17;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* This priority queue implementation is based on a data structure such as a binary heap.
 * A binary heap is a data structure that is an array that can be thought of as an almost
 * complete binary tree. Each node of this tree corresponds to a certain element of the array.
 * At all levels, except perhaps the last, the tree is completely filled (filled level -
 * one that contains the maximum possible number of nodes). The last level is filled
 * sequentially from left to right until the elements in the array run out.
 */
public class PrivatePriorityQueue<E extends Comparable<E>> implements Iterable<E> {

    /* An array of priority queue elements */
    private PrivateArrayList<E> list = new PrivateArrayList<>();

    /* The current number of queue elements */
    private int currentSize = 0;

    /* Constructor */
    public PrivatePriorityQueue() {
        list.add(null);
    }

    /**
     * Adds the element to priority queue and sorts this queue.
     * Throws a NullPointerException if element is null.
     *
     * @param element The element to add.
     */
    public void enqueue(E element) {

        if (element == null) {
            throw new NullPointerException();
        }
        /* add the specified element and increase the size. */
        list.add(element);
        currentSize++;

        /* Move a child up the binary heap if its value is less
           than the value of its parent */
        int childIndex = currentSize;
        while (childIndex != 1) {

            /* In a binary heap, the parent's index is n/2, where n is the child's index. */
            int parentIndex = childIndex / 2;

            E tempElement = list.get(childIndex);

            /* If the child is smaller than its parent, we swap them in the array. */
            if (tempElement.compareTo(list.get(parentIndex)) < 0) {
                swapElements(list, parentIndex, childIndex);
                childIndex = parentIndex;
            } else {
                break;
            }
        }
    }

    /**
     * Returns and removes the head (minimum element) of the queue.
     * If the queue is empty, throws an NoSuchElementException.
     *
     * @return The head of this queue or throws an NoSuchElementException
     * if the queue is empty.
     */
    public E dequeueMin() {

        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        /*
         Swap the top of the binary heap (the first element of the list)
         and the last element of the list.
        */
        swapElements(list, 1, currentSize);

        /* remove the minimum value and decrease the size. */
        E minimumValue = list.remove(currentSize);
        currentSize--;

        /* lower the first element down if it is larger than its children */
        if (!isEmpty()) {

            int parentIndex = 1;

            E element = list.get(1);
            while (parentIndex * 2 <= currentSize) {

                /*
                 In a binary heap, the child's indices are n*2 and n*2+1,
                 where n is the parent's index.
                */
                int leftChildIndex = parentIndex * 2;
                int rightChildIndex = parentIndex * 2 + 1;

                /* Look for the minimum value of children. */
                int minIndex;
                if (rightChildIndex <= currentSize &&
                        (list.get(leftChildIndex).compareTo(list.get(rightChildIndex))) > 0) {
                    minIndex = rightChildIndex;
                } else {
                    minIndex = leftChildIndex;
                }
                if (element.compareTo(list.get(minIndex)) > 0) {
                    swapElements(list, parentIndex, minIndex);

                    parentIndex = minIndex;
                } else {
                    break;
                }
            }
        }
        return minimumValue;
    }

    /**
     * Returns the first (minimum) element in the queue without removing it.
     * Throws new NoSuchElementException if the queue is empty.
     *
     * @return The first (minimum) element.
     */
    public E peek() {

        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return list.get(1);
    }

    /**
     * Returns the number of elements in priority queue.
     *
     * @return The number of elements in priority queue.
     */
    public int size() {

        return currentSize;
    }

    /**
     * Checks if the queue is empty
     *
     * @return True if there is no element in this priority queue,
     * false otherwise.
     */

    public boolean isEmpty() {

        return currentSize == 0;
    }

    /**
     * Builds a string from priority queue elements to display it.
     *
     * @return The string from priority queue elements to display it.
     */
    public String toString() {
        String result = "{";
        for (int i = 1; i <= currentSize; i++) {
            if (i != currentSize)
                result = result.concat(list.get(i) + ", ");
            else result = result.concat(list.get(i) + "");
        }
        return result.concat("}");
    }

    /**
     * Removes all of the elements from priority queue. The queue will be empty.
     */
    public void clear() {

        list = new PrivateArrayList<>();
        currentSize = 0;
        list.add(null);
    }

    @Override
    public Iterator<E> iterator() {

        return new PrivatePriorityQueueIterator<>();
    }

    /**
     * The class contains methods  hasNext() and next() overrides
     * that allow iteration over the collection PrivatePriorityQueue.
     */
    public class PrivatePriorityQueueIterator<T> implements Iterator<T> {

        private int index = 1;

        @Override
        public boolean hasNext() {

            return index <= currentSize;
        }

        @Override
        public T next() {

            //noinspection unchecked
            return (T) list.get(index++);
        }
    }

    /**
     * Checks the validity of the index.
     * Throws ArrayIndexOutOfBoundsException
     *
     * @param index The checked index.
     * @return True if index is valid, otherwise throws an exception.
     */
    private boolean isIndexValid(int index) {

        if (index >= 0 && index <= currentSize) {
            return true;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /**
     * Swaps the elements with the specified indices in the list.
     *
     * @param list   THe list that contains the specified elements.
     * @param index1 The first list element index to swap.
     * @param index2 The first list element index to swap.
     */
    public void swapElements(PrivateArrayList<E> list, int index1, int index2) {

        if (isIndexValid(index1) && isIndexValid(index2)) {

            E tempElement = list.get(index1);
            list.set(index1, list.get(index2));
            list.set(index2, tempElement);
        }
    }
}
