package com.github.rhyrak.sorter;

import com.github.rhyrak.model.NISTEntry;

public class InsertionSort implements Sorter<NISTEntry>{
    NISTEntry[] local;
    @Override
    public void sort(NISTEntry[] c) {
        // TODO: Implement Insertion Sort
        local = c;
        int size = local.length;
        for(int i = 0; i < size; i++) {
            for(int j = i - 1; j >= 0; j--) {
                if(local[i].compareTo(local[j]) < 0)
                    swap(i, j);
                else
                    break;
            }
        }
    }
    // swap entries
    public void swap(int index1, int index2) {
        NISTEntry temp = local[index1];
        local[index1] = local[index2];
        local[index2] = temp;
    }
}
