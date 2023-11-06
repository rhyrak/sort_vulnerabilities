package com.github.rhyrak.sorter;

import java.util.ArrayList;
public class AVLSort<T extends Comparable<T>> implements Sorter<T>{

    @Override
    public void sort(T[] entries) {
        int size = entries.length;
        ArrayList<T> sorted = new ArrayList<>();
        for (T entry : entries) {
            insertNode(entry);
        }

        retrieveInorder(root, sorted);
        for(int i = 0; i < size; i++){
            entries[i] = sorted.get(i);
        }
    }

    Node<T> root;

    public AVLSort(){
        this.root = null;
    }

    public boolean boolCompare(int i){
        return i >= 0;
    }

    public Node<T> AVLBalance(T val, Node<T> localRoot){
        int heightDelta = getNodeWeight(localRoot);
        boolean leftNull = !(localRoot.getLeft() == null);
        boolean rightNull = !(localRoot.getRight() == null);

        try {
            if (heightDelta < -1 && rightNull &&  boolCompare(val.compareTo(localRoot.getRight().getValue()))) {
                return rotateLeft(localRoot);
            }
            if (heightDelta > 1 && leftNull && boolCompare(localRoot.getLeft().getValue().compareTo(val))) {
                return rotateRight(localRoot);
            }

            if (heightDelta < -1 && leftNull && boolCompare(localRoot.getLeft().getValue().compareTo(val))) {
                localRoot.setRight(rotateRight(localRoot.getRight()));
                return rotateLeft(localRoot);
            }
            if (heightDelta > 1 && rightNull && boolCompare(val.compareTo(localRoot.getLeft().getValue()))) {
                localRoot.setLeft(rotateLeft(localRoot.getLeft()));
                return rotateRight(localRoot);
            }
        }catch(Exception e){
            // do nothing
        }

        return localRoot;
    }

    public Node<T> rotateLeft(Node<T> node){
        Node<T> localRoot = node.getRight();
        Node<T> tNode = localRoot.getLeft();
        localRoot.setLeft(node);
        node.setRight(tNode);

        node.setHeight(Math.max(getNodeHeight(node.getLeft()), getNodeHeight(node.getRight())) + 1);
        localRoot.setHeight(Math.max(getNodeHeight(localRoot.getLeft()), getNodeHeight(localRoot.getRight())) + 1);

        return localRoot;
    }

    public Node<T> rotateRight(Node<T> node){
        Node<T> localRoot = node.getLeft();
        Node<T> tNode = localRoot.getRight();
        localRoot.setRight(node);
        node.setLeft(tNode);

        node.setHeight(Math.max(getNodeHeight(node.getLeft()), getNodeHeight(node.getRight())) + 1);
        localRoot.setHeight(Math.max(getNodeHeight(localRoot.getLeft()), getNodeHeight(localRoot.getRight())) + 1);

        return localRoot;
    }

    public void insertNode(T val){
        root = insertNode(val, root);
    }

    public Node<T> insertNode(T val, Node<T> localRoot){
        if(localRoot == null){
            localRoot = new Node<>(val);
            return localRoot;
        }
        else if(boolCompare(localRoot.getValue().compareTo(val))){
            localRoot.setLeft(insertNode(val, localRoot.getLeft()));
        }
        else if(boolCompare(val.compareTo(localRoot.getValue()))){
            localRoot.setRight(insertNode(val, localRoot.getRight()));
        }
        else return localRoot;

        localRoot.setHeight(Math.max(getNodeHeight(localRoot.getLeft()), getNodeHeight(localRoot.getRight())) + 1);

        return AVLBalance(val, localRoot);
    }

    @SuppressWarnings("unused")
    public Node<T> getMinNode(Node<T> node){
        if(node.getLeft() == null){
            return node;
        }
        return getMinNode(node.getLeft());
    }

    public int getNodeHeight(Node<T> node){
        if(node == null){
            return 0;
        }
        return node.getHeight();
    }

    public int getNodeWeight(Node<T> localRoot){
        if(localRoot == null){
            return 0;
        }
        return getNodeHeight(localRoot.getLeft()) - getNodeHeight(localRoot.getRight());
    }

    public void printInorder(Node<T> localRoot){
        if(localRoot == null) {
            return;
        }

        printInorder(localRoot.getLeft());
        System.out.println(localRoot.getValue() + " ");
        printInorder(localRoot.getRight());
    }

    public void retrieveInorder(Node<T> localRoot, ArrayList<T> arr){
        if(localRoot == null) {
            return;
        }

        retrieveInorder(localRoot.getLeft(), arr);
        arr.add(localRoot.getValue());
        retrieveInorder(localRoot.getRight(), arr);
    }

    public int getNumberOfNodes(Node<T> localRoot){
        if(localRoot == null){
            return 0;
        }
        int i = 1;
        i = i + getNumberOfNodes(localRoot.getLeft());
        i = i + getNumberOfNodes(localRoot.getRight());
        return i;
    }

    @SuppressWarnings("unused")
    public int getNumberOfNodes(){
        return getNumberOfNodes(this.root);
    }

    @SuppressWarnings("unused")
    public void display(){
        printInorder(this.root);
    }

    public static class Node<T> {
        private T val;
        private int height;
        private Node<T> left;
        private Node<T> right;

        public Node(T val){
            this.val = val;
            this.height = 1;
            this.left = null;
            this.right = null;
        }

        public T getValue(){
            return val;
        }

        @SuppressWarnings("unused")
        public void setValue(T val){
            this.val = val;
        }

        public int getHeight(){
            return this.height;
        }

        public void setHeight(int height){
            this.height= height;
        }

        public Node<T> getLeft(){
            return this.left;
        }

        public void setLeft(Node<T> node){
            this.left = node;
        }

        public Node<T> getRight(){
            return this.right;
        }

        public void setRight(Node<T> node){
            this.right = node;
        }

    }

}
