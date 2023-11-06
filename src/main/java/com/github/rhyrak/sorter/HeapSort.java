package com.github.rhyrak.sorter;

import com.github.rhyrak.model.NISTEntry;

public class HeapSort<T extends Comparable<T>> implements Sorter<T> {
    private T[] local;
    private int heapsize = 0;
    @Override
    public void sort(T[] c) {
        // TODO: Implement Heap Sort
        local = c.clone();
        this.build();
        for(int i = local.length -1; i >= 0; i--) {
            swap(i, 0);
            heapsize--;
            max_heapify(0);
        }
        int size = local.length;
        for(int i = 0; i < size; i++){
            c[i] = local[i];
        }
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
        return (index < heapsize && index >= 0 ) ? true : false;
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
