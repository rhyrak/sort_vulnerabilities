package com.github.rhyrak.sorter;

import java.util.Arrays;

public class MergeSort<T extends Comparable<T>> implements Sorter<T> {
    @Override
    public void sort(T[] entries) {
        mergeSort(entries, 0, entries.length - 1);
    }

    private void merge(T[] entries, int left, int middle, int right) {
        int n1 = middle - left + 1;
        int n2 = right - middle;

        T[] leftArray = Arrays.copyOfRange(entries, left, middle + 1);
        T[] rightArray = Arrays.copyOfRange(entries, middle + 1, right + 1);

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (leftArray[i].compareTo(rightArray[j]) < 0) {
                entries[k] = leftArray[i];
                i++;
            } else {
                entries[k] = rightArray[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            entries[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < n2) {
            entries[k] = rightArray[j];
            j++;
            k++;
        }
    }

    private void mergeSort(T[] entries, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;
            mergeSort(entries, left, middle);
            mergeSort(entries, middle + 1, right);
            merge(entries, left, middle, right);
        }
    }
}
