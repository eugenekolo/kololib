package kolobyte.common;

import java.util.Random;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Comparator;

/**
* Provides utility functions. Also known as, no clue where else to put these. */
public class Util {
    private static String TAG = "Util";
    static Float eventX1, eventX2;

    public static void print(String s) {
        System.out.println(s);
    }

    public static class Pair<L, R> {
        private L l;
        private R r;

        public Pair(L l, R r) {
            this.l = l;
            this.r = r;
        }

        public L getL() {
            return l;
        }

        public R getR() {
            return r;
        }

        public void setL(L l) {
            this.l = l;
        }

        public void setR(R r) {
            this.r = r;
        }
    }

    public static int randint(int max) {
        return new Random().nextInt(max);
    }

    /*
    Recursively binary search for the kth element (or the first k elements)
    of 2 sorted arrays, of sizes  where m+n.

    Essentially finds the first k/2 elements, then the next (k/2)/2 elements
    then the next ((k/2)/2)/2 elements and so on until k elements are found
    NOTE: arrays in this are 1 indexed for simplicity

        findKFirstElements(arr1, arr2, k):
            if (arr1.length == 0):
                return arr2[1:k] // return the remaining arr2 k first elements
            elsif (arr2.length == 0):
                return arr1[1:k] // return the remaining arr1 k first elements
            if (k == 1):
                return min(arr1[1], arr2[1])

            arr1_ptr = k/2th element of arr1
            arr2_ptr = k/2th element of arr2

            if (arr1[arr1_ptr] <= arr2[arr2_ptr]):
                first k/2 elements = arr1[1:arr1_ptr]
                return findKFirstElements(arr1[arr1_ptr+1:end], arr2, k-arr1_ptr)
            else:
                first k/2 elements = arr2[arr2_ptr]
                return findKFirstElements(arr1, arr2[arr2_ptr+1:end, k-arr2_ptr])

    */

    public int findKthSortedArrays(int A[], int B[], int k) {
        // Note: k, Aptr, Bptr is 1 indexed
        if (A.length == 0) {
            return B[k-1];
        } else if (B.length == 0) {
            return A[k-1];
        } else if (k == 1) {
            return Math.min(A[0], B[0]);
        }

        int Aptr, Bptr;
        if (A.length < B.length) {
            Aptr = Math.min(k/2, A.length);
            Bptr = k - Aptr;
        } else {
            Bptr = Math.min(k/2, B.length);
            Aptr = k - Bptr;
        }

        if (A[Aptr-1] <= B[Bptr-1]) {
            return findKthSortedArrays(Arrays.copyOfRange(A, Aptr, A.length), B, k-Aptr);
        }
        else {
            return findKthSortedArrays(A, Arrays.copyOfRange(B, Bptr, B.length), k-Bptr);
        }
    }

    public int[] mergeSortedArrays(int arr1[], int arr2[]) {
        /* Algo to merge sorted arrays:
            while (read ptrs arr1 & arr2 are within limits of arr1.length & arr2.length)
                m = min of arr1[arr1_ptr], arr2[arr2_ptr]
                increment array read ptr of array that contained min by 1
                write that min to final_merged_array
            write rest of the array that didn't get fully looped through down into final_merged_array

            This should look something like this, * is the array's read_ptr
            arr1 = [1*, 2, 3]; arr2 = [1*, 3, 4, 5], final = []
            arr1[*] = 1; arr2[*] = 1 ---> min = arr1, 1
            arr1 = [1, 2*, 3]; arr2 = [1*, 3, 4, 5], final = [1, 1]
            arr1[*] = 2; arr2[*] = 1 ---> min = arr2, 1
            arr1 = [1, 2*, 3]; arr2 = [1, 3*, 4, 5], final = [1, 1, 2]
            arr1[*] = 2; arr2[*] = 3 ---> min = arr1, 2
            ....
            arr1 = [1, 2, 3, *]; arr2 = [1, 3*, 4, 5]; final = [1, 1, 2, 3, 3!, 4!, 5!]
            // ! means 3, 4, 5 of arr2 got brought down as arr1[*] is out of bound
        */

        int[] end_arr = new int[arr1.length + arr2.length];
        int arr1_ptr = 0, arr2_ptr = 0;
        int end_ptr = 0;
        while (arr1_ptr < arr1.length && arr2_ptr < arr2.length) {
            // Find the min of the currently read ints and the array that had it
            // Write that min down and find the next lowest number in arrays
            if (arr1[arr1_ptr] <= arr2[arr2_ptr]) {
                end_arr[end_ptr] = arr1[arr1_ptr];
                arr1_ptr += 1;
                end_ptr += 1;
            } else {
                end_arr[end_ptr] = arr2[arr2_ptr];
                arr2_ptr += 1;
                end_ptr += 1;
            }
        }

        if (arr1_ptr >= arr1.length) {
            for (; arr2_ptr < arr2.length; arr2_ptr++, end_ptr++) {
                end_arr[end_ptr] = arr2[arr2_ptr];
            }

        }
        else if (arr2_ptr >= arr2.length) {
            for (; arr1_ptr < arr1.length; arr1_ptr++, end_ptr++) {
                end_arr[end_ptr] = arr1[arr1_ptr];
            }
        }

        return end_arr;
    }

    public static DataSet generateBigArray(final int size) {
        DataSet dataSet = new DataSet();
        List<Integer> A = new LinkedList<Integer>();
        List<Integer> B = new LinkedList<Integer>();
        int number = (int) ((Math.random() - 0.5) * 10000);
        for (int i = 0; i < size; i++) {
            if (Math.random() - 0.4 > 0) {
                A.add(number);
            } else {
                B.add(number);
            }
            if (i == size / 2) {
                dataSet.median = number;
            }
            number += (Math.random() + 0.01 * 10);
        }
        dataSet.setA(A);
        dataSet.setB(B);
        return dataSet;

    }

    public class HeapCollections {
        static PriorityQueue<Double> leftSide; // Max Heap
        static PriorityQueue<Double> rightSide; // Min Heap
        static int num_elements;

        public HeapCollections() {
            leftSide = new PriorityQueue<Double>(11, Collections.reverseOrder());
            rightSide = new PriorityQueue<Double>();
            num_elements = 0;
        }

        public static Double runningMedian(double input) {
            // Init
            if (num_elements == 0) {
                leftSide.add(input);
                num_elements += 1;
                return leftSide.peek();
            } else if (num_elements == 1) {
                if (input <= leftSide.peek()) {
                    rightSide.add(leftSide.poll());
                    leftSide.add(input);
                } else {
                    rightSide.add(input);
                }
                num_elements += 1;
                return (leftSide.peek() + rightSide.peek())/2;
            }

            // Add the input to which heap it belongs to
            if (input <= leftSide.peek()) {
                leftSide.add(input);
            } else {
                rightSide.add(input);
            }

            // Balance heaps to keep it running
            if (leftSide.size() - rightSide.size() > 1) { // leftSide is greater by 2
                rightSide.add(leftSide.poll());
            } else if (rightSide.size() - leftSide.size() > 1) { // rightSide is greater by 2
                leftSide.add(rightSide.poll());
            } else { // Both are within +/-1 size of each other
                // Do nothing
            }

            // Return the median
            if (leftSide.size() == rightSide.size()) { // Both sides are the same size - even # of elements
                return (leftSide.peek() + rightSide.peek())/2;
            }
            else { // Return the root of the larger side
                return leftSide.size() > rightSide.size() ? leftSide.peek() : rightSide.peek();
            }
        }
    }

}
