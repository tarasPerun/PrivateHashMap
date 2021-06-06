package com.shpp.p2p.cs.tperun.assignment17;

import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 This class represents a data structure operating on the FIFO (first in - first out)
 principle. That is, the earlier an item was added to the collection, the earlier
 it is removed from it. This is the standard one-way queuing model.
*/
public class PrivateQueue<E> implements Iterable<E> {

    private final PrivateLinkedList<E> list = new PrivateLinkedList<>();
    private int currentSize = 0;

    /**
     * Creates an empty Stack.
     */
    public PrivateQueue() {

    }

    /**
     * This method adds an element to the end of the queue.
     * In this case, the queue size increases by one.
     *
     * @param element The element to be inserted onto the queue.
     * @return The inserted element.
     */
    public boolean offer(E element) {

        list.addLast(element);
        currentSize++;
        return true;
    }

    /**
     * Returns the total number of elements present in the queue.
     *
     * @return The current number of queue elements.
     */
    public int size() {

        return currentSize;
    }

    /**
     * Returns the element from the front of the queue without
     * removing it from the queue.
     * If the queue is empty, generates a new NoSuchElementException().
     *
     * @return The element at the front of the queue.
     */
    public E element() {

        if (currentSize == 0) throw new NoSuchElementException();
        return list.getFirst();
    }

    /**
     * Returns the element at the front of the queue without
     * removing it from the queue.
     * If the queue is empty, returns null.
     *
     * @return The element at the front of the queue or null if
     * queue is empty.
     */
    public E peek() {

        if (currentSize == 0) return null;
        return list.getFirst();
    }

    /**
     * Removes the element from the front of the queue and returns
     * the removed item. The queue size is decreased by one.
     * If the queue is empty, generates a new NoSuchElementException().
     *
     * @return The removed element.
     */
    public E remove() {

        if (currentSize == 0) throw new NoSuchElementException();
        currentSize--;
        return list.removeFirst();
    }

    /**
     * Removes the element from the front of the queue and returns
     * the removed item. The queue size is decreased by one.
     * If the queue is empty, generates a new NoSuchElementException().
     *
     * @return The removed element.
     */
    public E poll() {

        if (currentSize == 0) return null;
        currentSize--;
        return list.removeFirst();
    }

    /**
     * Checks if this queue is empty.
     *
     * @return True if the queue is empty, false otherwise.
     */
    public boolean isEmpty() {

        return currentSize == 0;
    }

    /**
     * Builds a string from queue elements to display it.
     *
     * @return The string from queue elements to display it.
     */
    public String toString() {

        return list.toString();
    }


    @Override
    public Iterator<E> iterator() {

        return new QueueIterator<E>();
    }

    /**
     * The class contains methods  hasNext() and next() overrides
     * that allow iteration over the collection PrivateQueue.
     */
    public class QueueIterator<E> implements Iterator<E> {

        private int index;

        @Override
        public boolean hasNext() {

            return index < currentSize;
        }

        @Override
        public E next() {

            //noinspection unchecked
            return (E) list.get(index++);
        }
    }
}
