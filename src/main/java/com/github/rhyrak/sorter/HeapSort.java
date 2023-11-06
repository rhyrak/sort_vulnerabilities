/*
19050111022, FURKAN DEMİR
20050111011, İBRAHİM BAHÇA
20050111034, MERTER ÇOBAN
20050111008, SELÇUK GENÇAY
21050141038, YOUSIF HARITH SUHAIL SUHAIL
 */

package com.github.rhyrak.sorter;

@SuppressWarnings("SpellCheckingInspection")
public class HeapSort<T extends Comparable<T>> implements Sorter<T> {
    private T[] local;
    private int heapsize = 0;
    @Override
    public void sort(T[] entries) {
        local = entries.clone();
        this.build();
        for(int i = local.length -1; i >= 0; i--) {
            swap(i, 0);
            heapsize--;
            max_heapify(0);
        }
        int size = local.length;
        System.arraycopy(local, 0, entries, 0, size);
    }
    
    //build a max heap
    private void build() {
        int size = local.length;
        heapsize = size;
        for(int i = size / 2 - 1; i >= 0; i--) {
             max_heapify(i);
        }
    }
    
    //check bounds
    private boolean inBound(int index) {
        return index < heapsize && index >= 0;
    }
    
    //get left child
    private int getLeft(int index) {
        if(inBound(index*2 +1))
            return index * 2 + 1;
        return -1;
    }
    
    //get right child
    private int getRight(int index) {
        if(inBound(index*2 + 2))
            return index * 2 + 2;
        return -1;
    }
    
    //swap entries
    private void swap(int index1, int index2) {
        T temp = local[index1];
        local[index1] = local[index2];
        local[index2] = temp;
    }
    
    //provide heap properties
    private void max_heapify(int index) {
        if(getRight(index) == -1 && getLeft(index) == -1)
            return;
      
        int maxIndex = index;
        
        if(getRight(index) != -1 && local[maxIndex].compareTo(local[getRight(index)]) < 0) {
            maxIndex = getRight(index);
        }
        if(getLeft(index) != -1 && local[maxIndex].compareTo(local[getLeft(index)]) < 0) {
            maxIndex = getLeft(index);
        }
        
        if(maxIndex == index)
            return;
        
        this.swap(maxIndex, index);
           
        max_heapify(maxIndex);
    }

}
