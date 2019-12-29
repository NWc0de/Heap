/*
 * Implementation of a heap data structure.
 * Author: Spencer Little
 */

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
    private HashMap<T, List<Integer>> objectIndices = new HashMap<>();

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
        int ind = 0;
        for (T t : userArray) {
            if (objectIndices.containsKey(t)) {
                objectIndices.get(t).add(ind++);
            } else {
                List<Integer> indexList = new ArrayList<>();
                indexList.add(ind++);
                objectIndices.put(t, indexList);
            }
        }

        heapArray = heapify(userArray);
        capacity = heapArray.length;
        nextNodeIndex = heapArray.length;
    }

    /** Returns the heap as an array. */
    public T[] getHeapArray() {return heapArray;}
    /** Returns the number of elements in the heap. */
    public int size() {return nextNodeIndex;}
    /** Returns true if the heap contains the specified element. O(1). */
    public boolean contains(T elem) {return objectIndices.containsKey(elem);}

    /**
     * Returns the number of occurrences of elem in the heap.
     * @param elem the element to search for
     * @return the number of occurrences of elem in the heap
     */
    public int elementCount(T elem) {
        if (objectIndices.containsKey(elem)) {
            return objectIndices.get(elem).size();
        } else {
            return 0;
        }
    }

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

        if (objectIndices.containsKey(toInsert)) {
            objectIndices.get(toInsert).add(nextNodeIndex);
        } else {
            List<Integer> indexList = new ArrayList<>();
            indexList.add(nextNodeIndex);
            objectIndices.put(toInsert, indexList);
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
        if (objectIndices.get(min).size() > 1) {
            objectIndices.get(min).remove(0);
        } else {
            objectIndices.remove(min);
        }
        heapArray[0] = heapArray[--nextNodeIndex];
        heapArray[nextNodeIndex] = null;
        swapDownTree(heapArray, 0, nextNodeIndex);
        return min;
    }

    /**
     * Deletes an arbitrary element from the heap. O(log(n)) because a HashMap of
     * object indicies is maintained. If heap contains elements with duplicate keys
     * then the elements are deleted in an arbitrary order.
     * @complexity O(log(n))
     * @param toDelete the object to delete from the heap
     */
    @SuppressWarnings("unchecked")
    public void delete(T toDelete) {
        if (!objectIndices.containsKey(toDelete)) {
            throw new IllegalArgumentException("Heap does not contain the specified element");
        }

        int pos;
        if (objectIndices.get(toDelete).size() > 1) {
            pos = objectIndices.get(toDelete).remove(0);
        } else {
            pos = objectIndices.remove(toDelete).remove(0);
        }

        if (pos != --nextNodeIndex) { // if toDelete is not leaf or last element (root with no other elements)
            objectIndices.get(heapArray[nextNodeIndex]).remove(Integer.valueOf(nextNodeIndex));
            objectIndices.get(heapArray[nextNodeIndex]).add(pos);
        }
        heapArray[pos] = heapArray[nextNodeIndex];
        heapArray[nextNodeIndex] = null;

        int parentNode = (pos+1)/2 - 1;
        int childNode = 2*pos + 1;

        if (childNode + 1 < nextNodeIndex && !lessThan(heapArray[childNode], heapArray[childNode + 1])) {
            childNode++;
        }

        if (childNode < nextNodeIndex && !lessThan(heapArray[pos], heapArray[childNode])) {
            swapDownTree(heapArray, pos, nextNodeIndex);
        } else if (parentNode != -1 && pos != nextNodeIndex && !lessThan(heapArray[parentNode], heapArray[pos])) {
            // node isn't root, deleted element wasn't last element, parent is greater than new child
            swapUpTree(heapArray, pos);
        }
    }

    /**
     * Deletes all occurrences of the given element in the heap.
     * @complexity O(log(n))
     * @throws IllegalArgumentException if the heap does not contain the specified element
     * @param toDelete the element to delete
     */
    public void deleteAll(T toDelete) {
        if (!objectIndices.containsKey(toDelete)) {
            throw new IllegalArgumentException("Heap does not contain the specified element");
        }
        while (objectIndices.get(toDelete) != null) {
            delete(toDelete);
        }
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
        int start = (toHeapify.length/2) - 1; // the parent of the last node
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
        int childNode = 2*parentNode + 1;
        if (childNode >= elementCount) return; // parent node is leaf (first base case)

        // swap parent with the lesser of it's children
        if (childNode + 1 < elementCount && !lessThan(toHeapify[childNode], toHeapify[childNode+1])) {
            childNode++;
        }

        // if parent > child swap, if not return (second base case)
        if (!lessThan(toHeapify[parentNode], toHeapify[childNode])) {
            swapArrayElements(toHeapify, childNode, parentNode);
        } else {
            return;
        }

        swapDownTree(toHeapify, childNode, elementCount);
    }

    /**
     * Recursively swaps the provided child node with it's parent node until
     * the parent node is less than the child
     * @param theArray the heap array to perform the swaps on
     * @param childNode the initial node to begin the swapping process
     */
    private void swapUpTree(T[] theArray, int childNode) {
        int parentNode = ((childNode+1)/2) - 1;
        if (parentNode < 0 || lessThan(theArray[parentNode], theArray[childNode])) return;
        swapArrayElements(theArray, childNode, parentNode);
        swapUpTree(theArray, parentNode);
    }

    /**
     * Compares two user generic objects by invoking compareTo
     * @param objOne the first object
     * @param objTwo the second object
     * @return true if objOne < objTwo false otherwise
     */
    @SuppressWarnings("unchecked")
    private boolean lessThan(T objOne, T objTwo) {
        return objOne.compareTo(objTwo) < 0;
    }

    /**
     * Swaps two elements in the specified array.
     * @param arr the array to swap elements in
     * @param pos1 the position of the first element to be swapped
     * @param pos2 the position of the second element to be swapped
     */
    private void swapArrayElements(T[] arr, int pos1, int pos2) {
        List indLst1 = objectIndices.get(arr[pos1]);
        List indLst2 = objectIndices.get(arr[pos2]);
        indLst1.remove(Integer.valueOf(pos1));
        indLst1.add(pos2);
        indLst2.remove(Integer.valueOf(pos2));
        indLst2.add(pos1);

        T temp = arr[pos1];
        arr[pos1] = arr[pos2];
        arr[pos2] = temp;
    }

}