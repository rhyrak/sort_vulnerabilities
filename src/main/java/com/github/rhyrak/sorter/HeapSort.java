package com.github.rhyrak.sorter;

import com.github.rhyrak.model.NISTEntry;

public class HeapSort implements Sorter<NISTEntry> {
    NISTEntry[] local;
    @Override
    public void sort(NISTEntry[] c) {
        // TODO: Implement Heap Sort
        local = c;
        this.build();
        for(int i = local.length -1; i > 0; i--) {
           this.swap(0, i);
           this.max_heapify(0);
        }
//        NISTEntry temp = c[0];
//        c[0] = c[1];
//        c[1] = temp;
    }
    
    //build a max heap
    public void build() {
        int size = local.length;
        
        for(int i = size / 2 - 1; i > 0; i--) {
             max_heapify(i);
        }
        
    }
    
    //check bounds
    public boolean inBound(int index) {
        return (index < local.length && index >= 0 ) ? true : false;
    }
    
    //get left child
    public int getLeft(int index) {
        if(inBound(index*2 +1))
            return index * 2 + 1;
        return -1;
    }
    
    //get right child
    public int getRight(int index) {
        if(inBound(index*2 + 2))
            return index * 2 + 2;
        return -1;
    }
    
    //get parent
    public int getParent(int index) {
        if(inBound((index - 1) / 2))
            return (index - 1) / 2;
        return -1;
    }
    
    //swap entries
    public void swap(int index1, int index2) {
        if(!inBound(index1) && !inBound(index2))
            return;
        NISTEntry temp = local[index1];
        local[index1] = local[index2];
        local[index2] = temp;
    }
    
    //provide heap properties
    public void max_heapify(int index) {
        if(getRight(index) == -1 && getLeft(index) == -1)
            return;
      
        NISTEntry max = local[index];
        int maxIndex = index;
        
        if(getRight(index) != -1 && max.compareTo(local[getRight(index)]) < 0) {
            max = local[getRight(index)];
            maxIndex = getRight(index);
        }
        if(getLeft(index) != -1 && max.compareTo(local[getLeft(index)]) < 0) {
            max = local[getLeft(index)];
            maxIndex = getLeft(index);
        }
        
        if(maxIndex == index)
            return;
        
        this.swap(maxIndex, index);
           
        max_heapify(maxIndex);
    }
}
