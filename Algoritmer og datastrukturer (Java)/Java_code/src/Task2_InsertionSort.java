import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Task2_InsertionSort {
    public static void main(String[] args) {

        /* Original Array */
        ArrayList<Wine> wines = ReadFromFile.readFile();

        /* Array only with unique alcoholValues */
        HashSet<Double> seen = new HashSet<>();
        for (Wine wine : wines) {
            seen.add(wine.alcohol());
        }
        ArrayList<Double> alcoholValues = new ArrayList<>(seen);

        System.out.println("=== Insertion Sort ===");

        System.out.println("\n[Natural order]");
        System.out.println("Before: " + new ArrayList<>(alcoholValues));
        int[] result1 = insertionSort(alcoholValues);
        System.out.println("After:  " + alcoholValues);
        System.out.println("Passes: " + result1[0]);
        System.out.println("Shifts: " + result1[1]);
        System.out.println("Comparisons: " + result1[2]);

        Collections.shuffle(alcoholValues);

        System.out.println("\n[Shuffled]");
        System.out.println("Before: " + new ArrayList<>(alcoholValues));
        int[] result2 = insertionSort(alcoholValues);
        System.out.println("After:  " + alcoholValues);
        System.out.println("Passes: " + result2[0]);
        System.out.println("Shifts: " + result2[1]);
        System.out.println("Comparisons: " + result2[2]);
    }

    /**************************************************************************************
     *   SOURCE FOR INSERTION SORT
     *   Title   : Insertion Sort Algorithm
     *   Author  : GeeksForGeeks
     *   URL     : https://www.geeksforgeeks.org/insertion-sort-algorithm/
     *   Note    : Adapted to sort unique alcohol values from the Wine Quality dataset
     **************************************************************************************/

    public static int[] insertionSort(ArrayList<Double> list) {
        int passes = 0;
        int shifts = 0;
        int comparisons = 0;
        int n = list.size();

        for (int i = 1; i < n; i++) {
            passes++;
            double key = list.get(i);
            int j = i - 1;

            while (j >= 0) {
                comparisons++;
                if (list.get(j) > key) {
                    list.set(j + 1, list.get(j));
                    j--;
                    shifts++;
                } else {
                    break;
                }
            }
            list.set(j + 1, key);
        }
        return new int[]{
                passes,
                shifts,
                comparisons
        };
    }

}
