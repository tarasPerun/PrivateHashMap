package com.shpp.p2p.cs.tperun.assignment17;

import java.util.EmptyStackException;
import java.util.Iterator;

/* The class PrivateStack implements a simple last-in-first-out (LIFO) mechanism. */
public class PrivateStack<E> implements Iterable<E> {

    private final PrivateLinkedList<E> list = new PrivateLinkedList<>();
    private int currentSize = 0;

    /**
     * Creates an empty Stack.
     */
    public PrivateStack()  {

    }

    /**
     * This method pushes the item to the top of the stack,
     * on top of the current top item. The stack size will be
     * increment by one.
     *
     * @param element The element to be pushed onto the stack.
     * @return The pushed element.
     */
    public E push(E element) {

        list.add(element);
        currentSize++;
        return element;
    }

    /**
     * Returns the number of elements in the stack.
     *
     * @return The current number of stack elements.
     */
    public int size() {

        return currentSize;
    }

    /**
     * Removes the element from the top of the stack and returns
     * the removed item. The stack size is decreased by one.
     * If the stack is empty, generates a new EmptyStackException().
     *
     * @return The removed element.
     * Throws the EmptyStackException.
     */
    public E pop() {

        if (currentSize == 0) throw new EmptyStackException();
        currentSize--;
        return list.removeLast();
    }

    /**
     * Returns the value of the element at the top of the stack without
     * removing it from the stack.
     * If the stack is empty, generates a new EmptyStackException().
     *
     * @return The element at the top of the stack.
     * Throws the EmptyStackException.
     */
    public E peek() {

        if (currentSize == 0) throw new EmptyStackException();
        return list.getLast();
    }

    /**
     * Checks if this stack is empty.
     *
     * @return True if the stack is empty, false otherwise.
     */
    public boolean isEmpty() {

        return currentSize == 0;
    }

    /**
     * Builds a string from stack elements to display it.
     *
     * @return The string from stack elements to display it.
     */
    public String toString() {

        return list.toString();
    }



    @Override
    public Iterator<E> iterator() {

        return new StackIterator<>();
    }

    /**
     * The class contains methods  hasNext() and next() overrides
     * that allow iteration over the collection PrivateQueue.
     */
    public class StackIterator<E> implements Iterator<E> {

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


