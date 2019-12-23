/*
 * Implementation of a heap data structure.
 * Author: Spencer Little
 */

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * An implementation of the heap data structure. Complexity:
 * heapify - O(n)
 * insert - O(log(n))
 * extractMin - O(log(n))
 * findMin - O(1)
 * @Author Spencer Little
 */
public class Heap<T extends Comparable> {

    public int nextNodeIndex;
    private T[] heapArray;
    private int capacity;

    /**
     * Initializes the heapArray when no initialize array is passed.
     */
    @SuppressWarnings("unchecked")
    public Heap(Class<T> t) {
        // generic array creation: https://stackoverflow.com/questions/529085/how-to-create-a-generic-array-in-java
        heapArray = (T[]) Array.newInstance(t, 10);
        capacity = 10;
        nextNodeIndex = 0;
    }

    /**
     * Initializes the heap array by heapifying the supplied array.
     * Assumes array is full.
     * @param userArray the user provided array to be heapified
     */
    public Heap(T[] userArray) {
        heapArray = heapify(userArray);
        capacity = heapArray.length;
        nextNodeIndex = heapArray.length;
    }

    public T[] getHeapArray() {return heapArray;}
    public int getElementCount() {return nextNodeIndex;}

    /********************************************************************
     *                          Heap Operations                         *
     ********************************************************************/

    /**
     * Inserts an element into the heap.
     * @complexity O(log(n)), n = heapArray.length, can be O(n) if resizing is necessary
     * @param toInsert element to insert
     */
    public void insert(T toInsert) {
        if (nextNodeIndex > capacity>>>1) {
            capacity = capacity + (capacity>>>1); // increase capacity by 1.5
            heapArray = Arrays.copyOf(heapArray, capacity);
        }
        heapArray[nextNodeIndex] = toInsert;
        swapUpTree(heapArray, nextNodeIndex++);
    }

    /**
     * Extracts and returns the minimum value.
     * @complexity O(log(n))
     * @return the minimum value
     */
    public T extractMin() {
        if (nextNodeIndex < 0) throw new IllegalStateException("Cannot extract from empty heap.");
        T min = heapArray[0];
        heapArray[0] = heapArray[nextNodeIndex - 1];
        heapArray[--nextNodeIndex] = null; // erase last element
        swapDownTree(heapArray, 0, nextNodeIndex);
        return min;
    }

    /**
     * Returns but does not extract the minimum value in the heap.
     * @complexity O(1)
     * @return the minimum value in the heap
     */
    public T findMin() {
        if (heapArray[0] == null) throw new IllegalStateException("Minimum does not exist: all elements have been extracted");
        return heapArray[0];
    }

    /**
     * Constructs a heap from an arbitrary array.
     * @complexity O(n), n = toHeapify.length
     * @param toHeapify the array to heapify
     * @return the heapified array
     */
    private T[] heapify(T[] toHeapify) {
        int start = (int) Math.floor(toHeapify.length/2) - 1; // the parent of the last node
        for (int i = start; i >= 0; i--) {
            swapDownTree(toHeapify, i, toHeapify.length);
        }
        return toHeapify;
    }

    /**
     * Compares the child nodes of T[parentNode] and swaps parentNode
     * with the least of the two, if parent node is greater than those
     * element, all the way to the leafs of the tree.
     * @complexity O(log(n))
     * @param toHeapify the array being heapified
     * @param parentNode the node current being processed
     */
    @SuppressWarnings("unchecked")
    private void swapDownTree(T[] toHeapify, int parentNode, int elementCount) {
        int swappedChild = Integer.MAX_VALUE;
        if (2*parentNode + 1 >= elementCount) return; // parent node is leaf
        if (2*parentNode + 2 >= elementCount && // parent node has one child
                toHeapify[parentNode].compareTo(toHeapify[2*parentNode + 1]) > 0) {

            swappedChild = 2*parentNode + 1;
            swapArrayElements(toHeapify, 2*parentNode + 1, parentNode);

        } else if (2*parentNode + 2 >= elementCount) {

            return; // one child no swap

        } else if (toHeapify[parentNode].compareTo(toHeapify[2*parentNode + 1]) < 0 &&
                toHeapify[parentNode].compareTo(toHeapify[2*parentNode + 2]) < 0) {

            return; // heap is satisfied

        } else if (toHeapify[2*parentNode + 1].
                compareTo(toHeapify[2*parentNode + 2]) < 0) { // two leafs, heap violation

            swappedChild = 2*parentNode + 1;
            swapArrayElements(toHeapify, 2*parentNode + 1, parentNode);

        } else if (toHeapify[2*parentNode + 1].
                compareTo(toHeapify[2*parentNode + 2]) > 0){

            swappedChild = 2*parentNode + 2;
            swapArrayElements(toHeapify, 2*parentNode + 2, parentNode);

        } else if (toHeapify[2*parentNode + 1].
                compareTo(toHeapify[2*parentNode + 2]) == 0) {

            swappedChild = 2*parentNode + 2;
            swapArrayElements(toHeapify, 2*parentNode + 2, parentNode);

        }
        swapDownTree(toHeapify, swappedChild, elementCount);
    }

    /**
     * Recursively swaps the provided child node with it's parent node until
     * the parent node is less than the child
     * @param theArray the heap array to perform the swaps on
     * @param childNode the initial node to begin the swapping process
     */
    private void swapUpTree(T[] theArray, int childNode) {
        int parentNode = (int) Math.floor((childNode+1)/2) - 1; // figure out why childNode+1 computes parent node here
        if (parentNode < 0 || theArray[parentNode].compareTo(theArray[childNode]) < 0) return;
        swapArrayElements(theArray, childNode, parentNode);
        swapUpTree(theArray, parentNode);
    }

    /**
     * Swaps two elements in the specified array.
     * @param arr the array to swap elements in
     * @param pos the position of the first element to be swapped
     * @param pos2 the position of the second element to be swapped
     */
    private void swapArrayElements(T[] arr, int pos, int pos2) {
        T temp = arr[pos];
        arr[pos] = arr[pos2];
        arr[pos2] = temp;
    }

}