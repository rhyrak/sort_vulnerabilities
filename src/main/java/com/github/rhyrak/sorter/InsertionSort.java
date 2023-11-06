package com.github.rhyrak.sorter;

import com.github.rhyrak.model.NISTEntry;

public class InsertionSort<T extends Comparable<T>> implements Sorter<T>{
    private T[] local;
    @Override
    public void sort(T[] c) {
        // TODO: Implement Insertion Sort
        local = c.clone();
        T value;
        int size = local.length;
        for(int i = 1; i < size; i++) {
            value = local[i];
            for(int j = i - 1; j >= 0; j--) {
                if(value.compareTo(local[j]) < 0)
                    swap(j + 1, j);
                else
                    break;
            }
        }

        for(int i = 0; i < size; i++){
            c[i] = local[i];
        }
    }
    // swap entries
    private void swap(int index1, int index2) {
        T temp = local[index1];
        local[index1] = local[index2];
        local[index2] = temp;
    }
}
