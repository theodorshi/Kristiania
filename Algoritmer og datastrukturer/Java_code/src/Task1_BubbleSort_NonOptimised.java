import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Task1_BubbleSort_NonOptimised {
    public static void main(String[] args) {
        /* Original Array */
        ArrayList<Wine> wines = ReadFromFile.readFile();

        /* Array only with unique alcoholValues */
        HashSet<Double> seen = new HashSet<>();
        for (Wine wine : wines) {
            seen.add(wine.alcohol());
        }
        ArrayList<Double> alcoholValues = new ArrayList<>(seen);

        System.out.println("=== Bubble Sort (Non-Optimised) ===");

        System.out.println("\n[Natural order]");
        System.out.println("Before: " + new ArrayList<>(alcoholValues));
        int[] result1 = bubbleSort(alcoholValues);
        System.out.println("After:  " + alcoholValues);
        System.out.println("Passes: " + result1[0]);
        System.out.println("Swaps:  " + result1[1]);

        Collections.shuffle(alcoholValues);

        System.out.println("\n[Shuffled]");
        System.out.println("Before: " + new ArrayList<>(alcoholValues));
        int[] result2 = bubbleSort(alcoholValues);
        System.out.println("After:  " + alcoholValues);
        System.out.println("Passes: " + result2[0]);
        System.out.println("Swaps:  " + result2[1]);
    }

    /**************************************************************************************
     *   SOURCE FOR BUBBLE SORT
     *   Title   : Bubble Sort Algorithm
     *   Author  : GeeksForGeeks
     *   Date    : January 21, 2025
     *   URL     : https://www.geeksforgeeks.org/bubble-sort-algorithm/
     *   Accessed: 2025-04-23
     *   Note    : Adapted to sort unique alcohol values from the Wine Quality dataset
     **************************************************************************************/

    public static int[] bubbleSort(ArrayList<Double> list) {
        int iteration = 0;
        int swap = 0;

        // Outer loop - will run exactly n-1 times
        for (int i = 0; i < list.size() - 1; i++) {
            iteration++;
            // Inner loop compares and swaps adjacent elements if needed
            for (int j = 0; j < list.size() - 1 - i; j++) {
                if (list.get(j) > list.get(j + 1)) {
                    double temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                    swap++;
                }
            }
        }
        return new int[]{
                iteration,
                swap
        };
    }
}
