/*
 * A set of unit tests covering the heap class.
 * Author: Spencer Little
 */


import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * @author Spencer Little
 */
public class HeapTest {

    @Test
    public void testMinHeapify() {
        ArrayList<Integer> input;
        for (int i = 0; i < 100; i++) {
            input = new ArrayList<>();
            makeRandomIntegerArray(input);
            Integer[] intArr = input.toArray(Integer[]::new);
            Heap<Integer> testMinHeap = new Heap<>(intArr, Heap.Type.MIN);
            Assert.assertTrue(isMinHeap(testMinHeap.getHeapArray(), testMinHeap.size()));
        }
    }

    @Test
    public void testMinExtractMin() {
        ArrayList<Integer> input;
        for (int i = 0; i < 100; i++) {
            input = new ArrayList<>();
            makeRandomIntegerArray(input);
            Integer[] intArr = input.toArray(Integer[]::new);
            Heap<Integer> testMinHeap = new Heap<>(intArr, Heap.Type.MIN);
            Integer firstMin = testMinHeap.extractRoot();
            for (int j = 0; j < input.size() - 1; j++) {
                Assert.assertTrue(firstMin <= testMinHeap.getRoot());
                firstMin = testMinHeap.extractRoot();
                Assert.assertTrue(isMinHeap(testMinHeap.getHeapArray(), testMinHeap.size()));
            }
        }
    }

    @Test
    public void testMinInsert() {
        ArrayList<Integer> input;
        for (int i = 0; i < 100; i++) {
            input = new ArrayList<>();
            makeRandomIntegerArray(input);
            Integer[] intArr = input.toArray(Integer[]::new);
            Heap<Integer> testMinHeap = new Heap<>(intArr, Heap.Type.MIN);
            Random gen = new Random();
            for (int j = 0; j < 100; j++) {
                testMinHeap.insert(gen.nextInt());
                Assert.assertTrue(isMinHeap(testMinHeap.getHeapArray(), testMinHeap.size()));
            }
        }
    }

    @Test
    public void testMinDelete() {
        ArrayList<Integer> input;
        for (int i = 0; i < 100; i++) {
            input = new ArrayList<>();
            makeRandomIntegerArray(input);
            Integer[] intArr = input.toArray(Integer[]::new);
            Heap<Integer> testMinHeap = new Heap<>(intArr.clone(), Heap.Type.MIN);

            for (int j = 0; j < intArr.length; j++) {
                testMinHeap.delete(intArr[j]);
                Assert.assertTrue(isMinHeap(testMinHeap.getHeapArray(), testMinHeap.size()));
            }
        }
    }

    @Test
    public void testMinDeleteAll() {
        ArrayList<Integer> input;
        for (int i = 0; i < 100; i++) {
            input = new ArrayList<>();
            makeRandomIntegerArray(input);
            Integer[] intArr = input.toArray(Integer[]::new);
            Heap<Integer> testMinHeap = new Heap<>(intArr.clone(), Heap.Type.MIN);
            Integer x = new Random().nextInt();

            for (int j = 0; j < 100; j++) {
                testMinHeap.insert(x);
                Assert.assertTrue(isMinHeap(testMinHeap.getHeapArray(), testMinHeap.size()));
            }

            testMinHeap.deleteAll(x);
            Assert.assertFalse(testMinHeap.contains(x));
            Assert.assertTrue(isMinHeap(testMinHeap.getHeapArray(), testMinHeap.size()));
        }
    }

    @Test
    public void testMinDuplicateKeys() {
        ArrayList<Integer> input;
        for (int i = 0; i < 100; i++) {
            input = new ArrayList<>();
            makeRandomIntegerArray(input);
            Integer[] intArr = input.toArray(Integer[]::new);
            Heap<Integer> testMinHeap = new Heap<>(intArr.clone(), Heap.Type.MIN);

            Integer x = new Random().nextInt();
            for (int j = 0; j < 100; j++) {
                testMinHeap.insert(x);
                Assert.assertEquals(testMinHeap.elementCount(x), j + 1);
                Assert.assertTrue(isMinHeap(testMinHeap.getHeapArray(), testMinHeap.size()));
                for (int k = 0; k < j; k++) {
                    testMinHeap.delete(x);
                }
                for (int k = 0; k < j; k++) {
                    testMinHeap.insert(x);
                }
            }

            for (int j = 0; j < 100; j++) {
                testMinHeap.delete(x);
                Assert.assertEquals(testMinHeap.elementCount(x), 99-j);
                Assert.assertTrue(isMinHeap(testMinHeap.getHeapArray(), testMinHeap.size()));
                for (int k = 0; k < 99 - j; k++) {
                    testMinHeap.delete(x);
                }
                for (int k = 0; k < 99 - j; k++) {
                    testMinHeap.insert(x);
                }
            }
            Assert.assertTrue(isMinHeap(testMinHeap.getHeapArray(), testMinHeap.size()));
        }
    }

    @Test
    public void testMinHeapCreation() {
        Heap<Integer> testMinHeap = new Heap<>(Integer.class, Heap.Type.MIN);
        Random gen = new Random();
        for (int i = 0; i < 100; i++) {
            testMinHeap.insert(gen.nextInt());
            Assert.assertTrue(isMinHeap(testMinHeap.getHeapArray(), testMinHeap.size()));
        }
        Integer firstMin = testMinHeap.extractRoot();
        for (int j = 0; j < 99; j++) {
            Assert.assertTrue(firstMin <= testMinHeap.getRoot());
            firstMin = testMinHeap.extractRoot();
            Assert.assertTrue(isMinHeap(testMinHeap.getHeapArray(), testMinHeap.size()));
        }
    }

    @Test
    public void testMaxHeapify() {
        ArrayList<Integer> input;
        for (int i = 0; i < 100; i++) {
            input = new ArrayList<>();
            makeRandomIntegerArray(input);
            Integer[] intArr = input.toArray(Integer[]::new);
            Heap<Integer> testMaxHeap = new Heap<>(intArr, Heap.Type.MAX);
            Assert.assertTrue(isMaxHeap(testMaxHeap.getHeapArray(), testMaxHeap.size()));
        }
    }

    @Test
    public void testMaxExtractMax() {
        ArrayList<Integer> input;
        for (int i = 0; i < 100; i++) {
            input = new ArrayList<>();
            makeRandomIntegerArray(input);
            Integer[] intArr = input.toArray(Integer[]::new);
            Heap<Integer> testMaxHeap = new Heap<>(intArr, Heap.Type.MAX);
            Integer firstMax = testMaxHeap.extractRoot();
            for (int j = 0; j < input.size() - 1; j++) {
                Assert.assertTrue(firstMax >= testMaxHeap.getRoot());
                firstMax = testMaxHeap.extractRoot();
                Assert.assertTrue(isMaxHeap(testMaxHeap.getHeapArray(), testMaxHeap.size()));
            }
        }
    }

    @Test
    public void testMaxInsert() {
        ArrayList<Integer> input;
        for (int i = 0; i < 100; i++) {
            input = new ArrayList<>();
            makeRandomIntegerArray(input);
            Integer[] intArr = input.toArray(Integer[]::new);
            Heap<Integer> testMaxHeap = new Heap<>(intArr, Heap.Type.MAX);
            Random gen = new Random();
            for (int j = 0; j < 100; j++) {
                testMaxHeap.insert(gen.nextInt());
                Assert.assertTrue(isMaxHeap(testMaxHeap.getHeapArray(), testMaxHeap.size()));
            }
        }
    }

    @Test
    public void testMaxDelete() {
        ArrayList<Integer> input;
        for (int i = 0; i < 100; i++) {
            input = new ArrayList<>();
            makeRandomIntegerArray(input);
            Integer[] intArr = input.toArray(Integer[]::new);
            Heap<Integer> testMaxHeap = new Heap<>(intArr.clone(), Heap.Type.MAX);

            for (int j = 0; j < intArr.length; j++) {
                testMaxHeap.delete(intArr[j]);
                Assert.assertTrue(isMaxHeap(testMaxHeap.getHeapArray(), testMaxHeap.size()));
            }
        }
    }

    @Test
    public void testMaxDeleteAll() {
        ArrayList<Integer> input;
        for (int i = 0; i < 100; i++) {
            input = new ArrayList<>();
            makeRandomIntegerArray(input);
            Integer[] intArr = input.toArray(Integer[]::new);
            Heap<Integer> testMaxHeap = new Heap<>(intArr.clone(), Heap.Type.MAX);
            Integer x = new Random().nextInt();

            for (int j = 0; j < 100; j++) {
                testMaxHeap.insert(x);
                Assert.assertTrue(isMaxHeap(testMaxHeap.getHeapArray(), testMaxHeap.size()));
            }

            testMaxHeap.deleteAll(x);
            Assert.assertFalse(testMaxHeap.contains(x));
            Assert.assertTrue(isMaxHeap(testMaxHeap.getHeapArray(), testMaxHeap.size()));
        }
    }

    @Test
    public void testMaxDuplicateKeys() {
        ArrayList<Integer> input;
        for (int i = 0; i < 100; i++) {
            input = new ArrayList<>();
            makeRandomIntegerArray(input);
            Integer[] intArr = input.toArray(Integer[]::new);
            Heap<Integer> testMaxHeap = new Heap<>(intArr.clone(), Heap.Type.MAX);

            Integer x = new Random().nextInt();
            for (int j = 0; j < 100; j++) {
                testMaxHeap.insert(x);
                Assert.assertEquals(testMaxHeap.elementCount(x), j + 1);
                Assert.assertTrue(isMaxHeap(testMaxHeap.getHeapArray(), testMaxHeap.size()));
                for (int k = 0; k < j; k++) {
                    testMaxHeap.delete(x);
                }
                for (int k = 0; k < j; k++) {
                    testMaxHeap.insert(x);
                }
            }

            for (int j = 0; j < 100; j++) {
                testMaxHeap.delete(x);
                Assert.assertEquals(testMaxHeap.elementCount(x), 99-j);
                Assert.assertTrue(isMaxHeap(testMaxHeap.getHeapArray(), testMaxHeap.size()));
                for (int k = 0; k < 99 - j; k++) {
                    testMaxHeap.delete(x);
                }
                for (int k = 0; k < 99 - j; k++) {
                    testMaxHeap.insert(x);
                }
            }
            Assert.assertTrue(isMaxHeap(testMaxHeap.getHeapArray(), testMaxHeap.size()));
        }
    }

    @Test
    public void testMaxHeapCreation() {
        Heap<Integer> testMaxHeap = new Heap<>(Integer.class, Heap.Type.MAX);
        Random gen = new Random();
        for (int i = 0; i < 100; i++) {
            testMaxHeap.insert(gen.nextInt());
            Assert.assertTrue(isMaxHeap(testMaxHeap.getHeapArray(), testMaxHeap.size()));
        }
        Integer firstMax = testMaxHeap.extractRoot();
        for (int j = 0; j < 99; j++) {
            Assert.assertTrue(firstMax >= testMaxHeap.getRoot());
            firstMax = testMaxHeap.extractRoot();
            Assert.assertTrue(isMaxHeap(testMaxHeap.getHeapArray(), testMaxHeap.size()));
        }
    }

    /**
     * Tests the min heap condition for each node (that each parent is less than
     * both of it's children.
     * @param testArr the heap array
     * @param elementCount the number of elements in the heap
     * @return a boolean indicating whether the min heap property is fulfilled
     */
    private boolean isMinHeap(Integer[] testArr, int elementCount) {
        boolean isHeapified = true;
        for (int i = 0; i < elementCount; i++) {
            if (2*i + 1 < elementCount && 2*i + 2 < elementCount) {
                isHeapified = isHeapified && testArr[i] <= testArr[2*i + 1] && testArr[i] <= testArr[2*i + 2];
            } else if (2*i + 1 < elementCount) {
                isHeapified = isHeapified && testArr[i] <= testArr[2*i + 1];
            }
        }
        return isHeapified;
    }

    /**
     * Tests the max heap condition for each node (that each parent is less than
     * both of it's children).
     * @param testArr the heap array
     * @param elementCount the number of elements in the heap
     * @return a boolean indicating whether the max heap property is fulfilled
     */
    private boolean isMaxHeap(Integer[] testArr, int elementCount) {
        boolean isHeapified = true;
        for (int i = 0; i < elementCount; i++) {
            if (2*i + 1 < elementCount && 2*i + 2 < elementCount) {
                isHeapified = isHeapified && testArr[i] >= testArr[2*i + 1] && testArr[i] >= testArr[2*i + 2];
            } else if (2*i + 1 < elementCount) {
                isHeapified = isHeapified && testArr[i] >= testArr[2*i + 1];
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
