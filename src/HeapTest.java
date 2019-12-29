/*
 * A set of unit tests covering the heap class.
 * Author: Spencer Little
 */


import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * @Author Spencer Little
 */
public class HeapTest {

    @Test
    public void testHeapify() {
        ArrayList<Integer> input;
        for (int i = 0; i < 100; i++) {
            input = new ArrayList<>();
            makeRandomIntegerArray(input);
            Integer[] intArr = input.toArray(Integer[]::new);
            Heap<Integer> testHeap = new Heap<>(intArr);
            Assert.assertTrue(isHeapified(testHeap.getHeapArray(), testHeap.size()));
        }
    }

    @Test
    public void testExtractMin() {
        ArrayList<Integer> input;
        for (int i = 0; i < 100; i++) {
            input = new ArrayList<>();
            makeRandomIntegerArray(input);
            Integer[] intArr = input.toArray(Integer[]::new);
            Heap<Integer> testHeap = new Heap<>(intArr);
            Integer firstMin = testHeap.extractMin();
            for (int j = 0; j < input.size() - 1; j++) {
                Assert.assertTrue(firstMin <= testHeap.findMin());
                firstMin = testHeap.extractMin();
                Assert.assertTrue(isHeapified(testHeap.getHeapArray(), testHeap.size()));
            }
        }
    }

    @Test
    public void testInsert() {
        ArrayList<Integer> input;
        for (int i = 0; i < 100; i++) {
            input = new ArrayList<>();
            makeRandomIntegerArray(input);
            Integer[] intArr = input.toArray(Integer[]::new);
            Heap<Integer> testHeap = new Heap<>(intArr);
            Random gen = new Random();
            for (int j = 0; j < 100; j++) {
                testHeap.insert(gen.nextInt());
                Assert.assertTrue(isHeapified(testHeap.getHeapArray(), testHeap.size()));
            }
        }
    }

    @Test
    public void testDelete() {
        ArrayList<Integer> input;
        for (int i = 0; i < 100; i++) {
            input = new ArrayList<>();
            makeRandomIntegerArray(input);
            Integer[] intArr = input.toArray(Integer[]::new);
            Heap<Integer> testHeap = new Heap<>(intArr.clone());

            for (int j = 0; j < intArr.length; j++) {
                testHeap.delete(intArr[j]);
                Assert.assertTrue(isHeapified(testHeap.getHeapArray(), testHeap.size()));
            }
        }
    }

    @Test
    public void testDeleteAll() {
        ArrayList<Integer> input;
        for (int i = 0; i < 100; i++) {
            input = new ArrayList<>();
            makeRandomIntegerArray(input);
            Integer[] intArr = input.toArray(Integer[]::new);
            Heap<Integer> testHeap = new Heap<>(intArr.clone());
            Integer x = new Random().nextInt();

            for (int j = 0; j < 100; j++) {
                testHeap.insert(x);
                Assert.assertTrue(isHeapified(testHeap.getHeapArray(), testHeap.size()));
            }

            testHeap.deleteAll(x);
            Assert.assertFalse(testHeap.contains(x));
            Assert.assertTrue(isHeapified(testHeap.getHeapArray(), testHeap.size()));
        }
    }

    @Test
    public void testDuplicateKeys() {
        ArrayList<Integer> input;
        for (int i = 0; i < 100; i++) {
            input = new ArrayList<>();
            makeRandomIntegerArray(input);
            Integer[] intArr = input.toArray(Integer[]::new);
            Heap<Integer> testHeap = new Heap<>(intArr.clone());

            Integer x = new Random().nextInt();
            for (int j = 0; j < 100; j++) {
                testHeap.insert(x);
                Assert.assertEquals(testHeap.elementCount(x), j + 1);
                Assert.assertTrue(isHeapified(testHeap.getHeapArray(), testHeap.size()));
            }

            for (int j = 0; j < 100; j++) {
                testHeap.delete(x);
                Assert.assertEquals(testHeap.elementCount(x), 99-j);
                Assert.assertTrue(isHeapified(testHeap.getHeapArray(), testHeap.size()));
            }
        }
    }
    
    @Test
    public void testHeapCreation() {
        Heap<Integer> testHeap = new Heap<>(Integer.class);
        Random gen = new Random();
        for (int i = 0; i < 100; i++) {
            testHeap.insert(gen.nextInt());
            Assert.assertTrue(isHeapified(testHeap.getHeapArray(), testHeap.size()));
        }
        Integer firstMin = testHeap.extractMin();
        for (int j = 0; j < 99; j++) {
            Assert.assertTrue(firstMin <= testHeap.findMin());
            firstMin = testHeap.extractMin();
            Assert.assertTrue(isHeapified(testHeap.getHeapArray(), testHeap.size()));
        }

    }

    /**
     * Tests the heap condition for each node (that each parent is less than both of it's
     * children.
     * @param testArr the heap array
     * @param elementCount the number of elements in the heap
     * @return a boolean indicating whether the heap property is fulfilled
     */
    private boolean isHeapified(Integer[] testArr, int elementCount) {
        boolean isHeapified = true;
        for (int i = 0; i < elementCount; i++) {
            if (2*i + 1 < elementCount && 2*i + 2 < elementCount) {
                isHeapified = isHeapified && testArr[i] <= testArr[2*i + 1] && testArr[i] <= testArr[2*i+2];
            } else if (2*i + 1 < elementCount) {
                isHeapified = isHeapified && testArr[i] <= testArr[2*i + 1];
            }
        }
        return isHeapified;
    }

    /**
     * Fills an ArrayList with a random amount of random integer values.
     * @param input the array to fill
     */
    private void makeRandomIntegerArray(List<Integer> input) {
        Random gen = new Random();
        int toFill = gen.nextInt(10000);
        for (int i = 0; i <= toFill; i++) {
            input.add(gen.nextInt());
        }
    }

}
