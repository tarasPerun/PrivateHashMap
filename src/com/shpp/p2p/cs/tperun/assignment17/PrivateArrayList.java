package com.shpp.p2p.cs.tperun.assignment17;

import java.util.Arrays;
import java.util.Iterator;

/*
 The PrivateArrayList class represents a generic collection.
 PrivateArrayList is a simple list, similar to an array, except
 that the number of elements in it is not fixed.
*/
public class PrivateArrayList<E> implements Iterable<E> {

    private Object[] array;

    /* The current number of elements in the list. */
    private int currentSize = 0;

    public PrivateArrayList() {
        /* The initial list capacity. */
        int INITIAL_CAPACITY = 10;
        array = new Object[INITIAL_CAPACITY];
    }


    /**
     * Adds an element to the end of the list.
     *
     * @param element The new element to add to the list.
     */
    public void add(E element) {

        /*
         If there is no space in the internal array,
         copy it into a new array of one more size.
        */
        if (currentSize == array.length - 1) {
            int newLength = array.length * 2;
            array = Arrays.copyOf(array, newLength);
        }
        array[currentSize] = element;
        currentSize++;
    }


    /**
     * Adds an element at the given index. In this case, the size
     * of the array increases by one, and all elements that were
     * located to the right of the insertion point are shifted
     * one position to the right.
     *
     * @param index   The index of the element to add.
     * @param element The new element with specified index.
     *                Throws: IndexOutOfBoundsException.
     */
    public void add(int index, E element) {

        if (isIndexValid(index)) {
            Object[] temp1 = Arrays.copyOf(array, index);
            Object[] temp2 = Arrays.copyOfRange(array, index, array.length);

            array = Arrays.copyOf(array, array.length + 1);
            for (int i = 0; i < array.length; i++) {

                if (i < index) array[i] = temp1[i];
                if (i == index) array[i] = element;
                if (i > index) array[i] = temp2[i - 1 - index];
            }
            currentSize++;
        }
    }

    /**
     * Returns the element at the specified index without removing it.
     *
     * @param index The index of the element to be returned.
     * @return The element at the specified index.
     * Throws: IndexOutOfBoundsException.
     */
    public E get(int index) {

        if (isIndexValid(index)) {
            return (E) array[index];
        }
        return null;
    }


    /**
     * Returns the number of elements in list.
     *
     * @return The current number of elements.
     */
    public int size() {

        return currentSize;
    }

    /**
     * Converts an array to a string for display it.
     *
     * @return The string with array elements.
     */
    public String toString() {

        String result = "{";
        for (int i = 0; i < currentSize; i++) {
            if (i != currentSize - 1)
                result = result.concat(array[i] + ", ");
            else result = result.concat(array[i] + "");
        }
        return result.concat("}");
    }

    /**
     * Replaces the element at the specified position in list with the specified element.
     *
     * @param index   The index of the element to be replaced.
     * @param element The new value of the element at the specified index.
     *                Throws: IndexOutOfBoundsException.
     */
    public void set(int index, E element) {

        if (isIndexValid(index)) {
            array[index] = element;
        }
    }

    /**
     * Removes the element at the specified index. In this case, the current size
     * the array is decremented by one, and all the elements that were  located
     * to the right of the deletion point are displaced one position to the left.
     *
     * @param index The index of the element to remove.
     * @return The removed element.
     * Throws: IndexOutOfBoundsException.
     */
    public E remove(int index) {

        if (isIndexValid(index)) {
            Object[] temp1 = Arrays.copyOf(array, index);
            Object[] temp2 = Arrays.copyOfRange(array, index + 1, array.length);

            Object removed = array[index];
            for (int i = 0; i < array.length - 1; i++) {

                if (i < index) array[i] = temp1[i];
                else array[i] = temp2[i - index];
            }
            array = Arrays.copyOf(array, array.length - 1);
            currentSize--;

            //noinspection unchecked
            return (E) removed;
        }
        return null;
    }

    /**
     * Finds the index of an element by link to that element.
     *
     * @param element The element to find the index of.
     * @return The index of element.
     */
    public int indexOf(E element) {
        for (int i = 0; i < array.length; i++) {
            if (element.equals(array[i]))

                return i;
        }
        return -1;
    }

    /**
     * Clears the array, writing null to each cell.
     * The class variable currentSize is also set to zero.
     */
    public void clear() {

        Arrays.fill(array, null);
        currentSize = 0;
    }

    /**
     * Checks if the list contains a specific element. If so, it returns true.
     * Otherwise returns false.
     *
     * @param element The element to check for in the list.
     * @return True or false depending on whether the given element is present
     * in the list or not.
     */
    public boolean contains(E element) {

        for (int i = 0; i < array.length; i++) {
            if (element.equals(array[i]))
                return true;
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
     * Reverse the order of items in the list.
     * <p>
     * return the reversed list.
     */
    public void reverse() {

        Object[] temp = Arrays.copyOf(array, currentSize);
        for (int i = 0; i < currentSize; i++) {
            array[i] = temp[currentSize - 1 - i];
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

        if (index >= 0 && index < currentSize) {
            return true;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    @Override
    public Iterator<E> iterator() {

        return new ListIterator<E>();
    }

    /**
     * The class contains methods  hasNext() and next() overrides
     * that allow iteration over the collection PrivateArrayList.
     */
    public class ListIterator<E> implements Iterator<E> {

        private int index;

        @Override
        public boolean hasNext() {

            return index < currentSize;
        }

        @Override
        public E next() {

            //noinspection unchecked
            return (E) array[index++];
        }
    }
}


