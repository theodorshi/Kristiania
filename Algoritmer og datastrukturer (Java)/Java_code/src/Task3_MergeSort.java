import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Task3_MergeSort {
    public static void main(String[] args) {
        /* Original Array */
        ArrayList<Wine> wines = ReadFromFile.readFile();

        /* Array only with unique alcoholValues */
        HashSet<Double> seen = new HashSet<>();
        for (Wine wine : wines) {
            seen.add(wine.alcohol());
        }
        ArrayList<Double> alcoholValues = new ArrayList<>(seen);

        System.out.println("=== Merge Sort ===");

        System.out.println("\n[Natural order]");
        System.out.println("Before: " + new ArrayList<>(alcoholValues));
        mergeCount = 0;
        mergeSort(alcoholValues);
        System.out.println("After:  " + alcoholValues);
        System.out.println("Merges: " + mergeCount);

        Collections.shuffle(alcoholValues);

        System.out.println("\n[Shuffled]");
        System.out.println("Before: " + new ArrayList<>(alcoholValues));
        mergeCount = 0;
        mergeSort(alcoholValues);
        System.out.println("After:  " + alcoholValues);
        System.out.println("Merges: " + mergeCount);
    }

    /**************************************************************************************
     *   SOURCE FOR MERGE SORT
     *   Title   : Writing a Merge Sort in Pseudocode (Guide)
     *   Author  : PseudoEditor.com
     *   Date    : n.d.
     *   URL     : https://pseudoeditor.com/guides/merge-sort
     *   Accessed: 2025-03-29
     *   Note    : Adapted to sort unique alcohol values from the Wine Quality dataset
     **************************************************************************************/

    // Tracks total number of merge operations performed
    public static int mergeCount = 0;

    // Recursively splits the list in half and merges the sorted halves back together
    public static void mergeSort(ArrayList<Double> list) {
        // Base case: a list of 0 or 1 element is already sorted
        if (list.size() <= 1) return;

        int mid = list.size() / 2;

        // Split into left and right halves
        ArrayList<Double> left  = new ArrayList<>(list.subList(0, mid));
        ArrayList<Double> right = new ArrayList<>(list.subList(mid, list.size()));

        // Recursively sort each half
        mergeSort(left);
        mergeSort(right);

        // Merge the two sorted halves back into the original list
        merge(list, left, right);
    }

    private static void merge(ArrayList<Double> dest, ArrayList<Double> left, ArrayList<Double> right) {
        mergeCount++; // Count every merge operation

        int li = 0; // pointer into left
        int ri = 0; // pointer into right
        int di = 0; // pointer into destination

        // Compare elements from both halves and place the smaller one first
        while (li < left.size() && ri < right.size()) {
            if (left.get(li) <= right.get(ri)) {
                dest.set(di, left.get(li));
                li++;
            } else {
                dest.set(di, right.get(ri));
                ri++;
            }
            di++;
        }

        // Copy any remaining elements from the left half
        while (li < left.size()) {
            dest.set(di, left.get(li));
            li++;
            di++;
        }

        // Copy any remaining elements from the right half
        while (ri < right.size()) {
            dest.set(di, right.get(ri));
            ri++;
            di++;
        }
    }

}
