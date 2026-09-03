import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Task4_QuickSort {

    static int comparisons = 0;

    public static void main(String[] args) {
        /* Original Array */
        ArrayList<Wine> wines = ReadFromFile.readFile();

        /* Array only with unique alcoholValues */
        HashSet<Double> seen = new HashSet<>();
        for (Wine wine : wines) {
            seen.add(wine.alcohol());
        }
        ArrayList<Double> alcoholValues = new ArrayList<>(seen);

        System.out.println("=== Quick Sort ===");

        // a. First element as pivot
        ArrayList<Double> list1 = new ArrayList<>(alcoholValues);
        comparisons = 0;
        quickSortFirst(list1, 0, list1.size() - 1);
        System.out.println("\n[a. First element as pivot]");
        System.out.println("After:       " + list1);
        System.out.println("Comparisons: " + comparisons);

        // b. Last element as pivot
        ArrayList<Double> list2 = new ArrayList<>(alcoholValues);
        comparisons = 0;
        quickSortLast(list2, 0, list2.size() - 1);
        System.out.println("\n[b. Last element as pivot]");
        System.out.println("After:       " + list2);
        System.out.println("Comparisons: " + comparisons);

        // c. Random element as pivot
        ArrayList<Double> list3 = new ArrayList<>(alcoholValues);
        comparisons = 0;
        quickSortRandom(list3, 0, list3.size() - 1);
        System.out.println("\n[c. Random element as pivot]");
        System.out.println("After:       " + list3);
        System.out.println("Comparisons: " + comparisons);

        // d. Median of three as pivot
        ArrayList<Double> list4 = new ArrayList<>(alcoholValues);
        comparisons = 0;
        quickSortMedian(list4, 0, list4.size() - 1);
        System.out.println("\n[d. Median of three as pivot]");
        System.out.println("After:       " + list4);
        System.out.println("Comparisons: " + comparisons);
    }

    /**************************************************************************************
     *   SOURCE FOR QUICK SORT
     *   Title   : QuickSort Algorithm
     *   Author  : GeeksForGeeks
     *   URL     : https://www.geeksforgeeks.org/quick-sort-algorithm/
     *   Note    : Adapted to sort unique alcohol values from the Wine Quality dataset,
     *             with four different pivot selection strategies
     **************************************************************************************/

    // --- Partition (shared by all strategies, pivot is always placed at high before calling) ---

    static int partition(ArrayList<Double> arr, int low, int high) {
        double pivot = arr.get(high);
        int i = low - 1;

        for (int j = low; j <= high - 1; j++) {
            comparisons++;
            if (arr.get(j) < pivot) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return i + 1;
    }

    static void swap(ArrayList<Double> arr, int i, int j) {
        double temp = arr.get(i);
        arr.set(i, arr.get(j));
        arr.set(j, temp);
    }

    // --- a. First element as pivot ---

    static void quickSortFirst(ArrayList<Double> arr, int low, int high) {
        if (low < high) {
            // Move first element to end so partition() can use it as pivot
            swap(arr, low, high);
            int pi = partition(arr, low, high);
            quickSortFirst(arr, low, pi - 1);
            quickSortFirst(arr, pi + 1, high);
        }
    }

    // --- b. Last element as pivot (base GfG algorithm) ---

    static void quickSortLast(ArrayList<Double> arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSortLast(arr, low, pi - 1);
            quickSortLast(arr, pi + 1, high);
        }
    }

    // --- c. Random element as pivot ---

    static void quickSortRandom(ArrayList<Double> arr, int low, int high) {
        if (low < high) {
            // Move random element to end so partition() can use it as pivot
            int randomIndex = low + new Random().nextInt(high - low + 1);
            swap(arr, randomIndex, high);
            int pi = partition(arr, low, high);
            quickSortRandom(arr, low, pi - 1);
            quickSortRandom(arr, pi + 1, high);
        }
    }

    // --- d. Median of three as pivot ---

    static void quickSortMedian(ArrayList<Double> arr, int low, int high) {
        if (low < high) {
            // Find median of first, middle and last element, move it to end
            int mid = low + (high - low) / 2;
            int medianIndex = medianOfThree(arr, low, mid, high);
            swap(arr, medianIndex, high);
            int pi = partition(arr, low, high);
            quickSortMedian(arr, low, pi - 1);
            quickSortMedian(arr, pi + 1, high);
        }
    }

    // Returns the index of the median value among arr[a], arr[b], arr[c]
    static int medianOfThree(ArrayList<Double> arr, int a, int b, int c) {
        double va = arr.get(a), vb = arr.get(b), vc = arr.get(c);
        if ((va <= vb && vb <= vc) || (vc <= vb && vb <= va)) return b;
        if ((vb <= va && va <= vc) || (vc <= va && va <= vb)) return a;
        return c;
    }
}
