/*
19050111022, FURKAN DEMİR
20050111011, İBRAHİM BAHÇA
20050111034, MERTER ÇOBAN
20050111008, SELÇUK GENÇAY
21050141038, YOUSIF HARITH SUHAIL SUHAIL
 */

package com.github.rhyrak.sorter;

/**
 * An interface for sorting an array of elements of a generic type.
 *
 * @param <T> The type of elements to be sorted, which must implement Comparable.
 */
public interface Sorter<T extends Comparable<T>> {
    /**
     * Sorts the given array
     *
     * @param entries Array of elements to be sorted.
     */
    void sort(T[] entries);
}
