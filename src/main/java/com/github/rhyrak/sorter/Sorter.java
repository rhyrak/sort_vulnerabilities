/*
19050111022, FURKAN DEMİR
20050111011, İBRAHİM BAHÇA
20050111034, MERTER ÇOBAN
20050111008, SELÇUK GENÇAY
21050141038, YOUSIF HARITH SUHAIL SUHAIL
 */

package com.github.rhyrak.sorter;

public interface Sorter<T extends Comparable<T>> {
    void sort(T[] entries);
}
