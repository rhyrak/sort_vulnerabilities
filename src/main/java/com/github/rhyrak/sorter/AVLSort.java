package com.github.rhyrak.sorter;

public class AVLSort<T extends Comparable<T>> implements Sorter<T>{

    /* vars */
    private int index;
    private Node<T> root;

    @Override
    public void sort(T[] entries) {
        /*
        * Build the AVL-Tree
        * */
        for (T entry : entries) {
            insertNode(entry);
        }

        /*
        * Copy back results to initial array
        * */
        retrieveInorder(root, entries);

    }

    /* default constructor */
    public AVLSort(){
        this.root = null;
    }

    /* apply fixes to tree */
    public Node<T> AVLBalance(T val, Node<T> localRoot){
        int heightDelta = getNodeWeight(localRoot);
        boolean leftNull = !(localRoot.getLeft() == null);
        boolean rightNull = !(localRoot.getRight() == null);

        try {
            /* left rotate around the node */
            if (heightDelta < -1 && rightNull && (val.compareTo(localRoot.getRight().getValue()) >= 0)) {
                return rotateLeft(localRoot);
            }
            /* right rotate around the node */
            if (heightDelta > 1 && leftNull && (localRoot.getLeft().getValue().compareTo(val) >= 0)) {
                return rotateRight(localRoot);
            }

            /* right and left rotate around the updated node */
            if (heightDelta < -1 && leftNull && (localRoot.getLeft().getValue().compareTo(val) >= 0)) {
                localRoot.setRight(rotateRight(localRoot.getRight()));
                return rotateLeft(localRoot);
            }
            /* left and right rotate around the updated node */
            if (heightDelta > 1 && rightNull && (val.compareTo(localRoot.getLeft().getValue()) >= 0)) {
                localRoot.setLeft(rotateLeft(localRoot.getLeft()));
                return rotateRight(localRoot);
            }
        }catch(NullPointerException ignored){}

        return localRoot;
    }

    /* swap references around to do a left rotation around the node */
    public Node<T> rotateLeft(Node<T> node){
        Node<T> localRoot = node.getRight();
        Node<T> tNode = localRoot.getLeft();
        localRoot.setLeft(node);
        node.setRight(tNode);

        /* update height values */
        node.setHeight(Math.max(getNodeHeight(node.getLeft()), getNodeHeight(node.getRight())) + 1);
        localRoot.setHeight(Math.max(getNodeHeight(localRoot.getLeft()), getNodeHeight(localRoot.getRight())) + 1);

        return localRoot;
    }

    /* swap references around to do a right rotation around the node */
    public Node<T> rotateRight(Node<T> node){
        Node<T> localRoot = node.getLeft();
        Node<T> tNode = localRoot.getRight();
        localRoot.setRight(node);
        node.setLeft(tNode);

        /* update height values */
        node.setHeight(Math.max(getNodeHeight(node.getLeft()), getNodeHeight(node.getRight())) + 1);
        localRoot.setHeight(Math.max(getNodeHeight(localRoot.getLeft()), getNodeHeight(localRoot.getRight())) + 1);

        return localRoot;
    }

    /* overloaded method */
    public void insertNode(T val){
        root = insertNode(val, root);
    }

    /* insert a node into the tree and apply fixes when necessary */
    public Node<T> insertNode(T val, Node<T> localRoot){
        /* if subtree empty then return a new node */
        if(localRoot == null){
            localRoot = new Node<>(val);
            return localRoot;
        }
        /* compare key values against subtree's root and insert to left or right */
        else if((localRoot.getValue().compareTo(val)) >= 0){
            localRoot.setLeft(insertNode(val, localRoot.getLeft()));
        }
        /* compare key values against subtree's root and insert to left or right */
        else if((val.compareTo(localRoot.getValue())) >= 0){
            localRoot.setRight(insertNode(val, localRoot.getRight()));
        }

        /* update height value*/
        localRoot.setHeight(Math.max(getNodeHeight(localRoot.getLeft()), getNodeHeight(localRoot.getRight())) + 1);

        /* balance the tree if necessary */
        return AVLBalance(val, localRoot);
    }

    /* retrieve smallest node */
    @SuppressWarnings("unused")
    public Node<T> getMinNode(Node<T> node){
        if(node.getLeft() == null){
            return node;
        }
        return getMinNode(node.getLeft());
    }

    /* get a node's height */
    public int getNodeHeight(Node<T> node){
        if(node == null){
            return 0;
        }
        return node.getHeight();
    }

    /* get a node's heaviness factor (right-heavy or left-heavy)*/
    public int getNodeWeight(Node<T> localRoot){
        if(localRoot == null){
            return 0;
        }
        return getNodeHeight(localRoot.getLeft()) - getNodeHeight(localRoot.getRight());
    }

    /* print key values */
    public void printInorder(Node<T> localRoot){
        if(localRoot == null) {
            return;
        }

        printInorder(localRoot.getLeft());
        System.out.println(localRoot.getValue() + " ");
        printInorder(localRoot.getRight());
    }

    /* populate given array with sorted values */
    public void retrieveInorder(Node<T> localRoot, T[] arr){
        if(localRoot == null) {
            return;
        }

        retrieveInorder(localRoot.getLeft(), arr);
        arr[index++] = localRoot.getValue();
        retrieveInorder(localRoot.getRight(), arr);
    }

    /* count up number of nodes in the tree */
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

    /* nested anonymous Node class */
    public static class Node<T> {
        /* vars */
        private T val;
        private int height;
        private Node<T> left;
        private Node<T> right;

        /* default constructor */
        public Node(T val){
            this.val = val;
            this.height = 1;
            this.left = null;
            this.right = null;
        }

        /* Accessors and Mutators */

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
