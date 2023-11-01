package com.github.rhyrak.sorter;

import com.github.rhyrak.model.NISTEntry;

public class HeapSort implements Sorter<NISTEntry>{
    @Override
    public void sort(NISTEntry[] c) {
        // TODO: Implement Heap Sort
        NISTEntry temp = c[0];
        c[0] = c[1];
        c[1] = temp;
    }
}
