/*
19050111022, FURKAN DEMİR
20050111011, İBRAHİM BAHÇA
20050111034, MERTER ÇOBAN
20050111008, SELÇUK GENÇAY
21050141038, YOUSIF HARITH SUHAIL SUHAIL
 */

package com.github.rhyrak.sorter;

public class InsertionSort<T extends Comparable<T>> implements Sorter<T>{
    private T[] local;
    @Override
    public void sort(T[] entries) {
        local = entries.clone();
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

        System.arraycopy(local, 0, entries, 0, size);
    }
    // swap entries
    private void swap(int index1, int index2) {
        T temp = local[index1];
        local[index1] = local[index2];
        local[index2] = temp;
    }
}
