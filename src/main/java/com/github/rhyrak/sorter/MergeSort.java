/*
19050111022, FURKAN DEMİR
20050111011, İBRAHİM BAHÇA
20050111034, MERTER ÇOBAN
20050111008, SELÇUK GENÇAY
21050141038, YOUSIF HARITH SUHAIL SUHAIL
 */

package com.github.rhyrak.sorter;

import java.util.Arrays;

public class MergeSort<T extends Comparable<T>> implements Sorter<T> {
    @Override
    public void sort(T[] entries) {
        mergeSort(entries, 0, entries.length - 1);
    }

    private void merge(T[] entries, int left, int middle, int right) {
        int first = middle - left + 1;
        int second = right - middle;

        // Create arrays
        T[] leftArr = Arrays.copyOfRange(entries, left, middle + 1);
        T[] rightArr = Arrays.copyOfRange(entries, middle + 1, right + 1);

        // Compare and merge two sub-arrays
        int z = 0, p = 0, k = left;
        while (z < first && p < second) {
            if (leftArr[z].compareTo(rightArr[p]) < 0) {
                entries[k] = leftArr[z];
                z++;
            } else {
                entries[k] = rightArr[p];
                p++;
            }
            k++;
        }

        // Merge the elements
        while (z < first) {
            entries[k] = leftArr[z];
            z++;
            k++;
        }
        // Merge the elements
        while (p < second) {
            entries[k] = rightArr[p];
            p++;
            k++;
        }
    }

    private void mergeSort(T[] entries, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;
            mergeSort(entries, left, middle);
            mergeSort(entries, middle + 1, right);
            merge(entries, left, middle, right);
            //Merge sorted arrays
        }
    }
}
