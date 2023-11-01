package com.github.rhyrak.sorter;

public interface Sorter<T extends Comparable<T>> {
    void sort(T[] c);
}
