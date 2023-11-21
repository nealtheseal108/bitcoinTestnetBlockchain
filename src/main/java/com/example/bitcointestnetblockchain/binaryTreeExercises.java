package com.example.bitcointestnetblockchain;

import java.util.ArrayList;

public class binaryTreeExercises {
    public static void main(String[] args) {

    }

    public static void intTreeNode() {

    }
}

class intTreeNode {
    private int payload;
    private intTreeNode leftNode;
    private intTreeNode rightNode;
    public intTreeNode(int payload, intTreeNode leftNode, intTreeNode rightNode) {
        this.payload = payload;
        this.leftNode= leftNode;
        this.rightNode = rightNode;
    }

    public int getPayload() {
        return payload;
    }

    public void setPayload(int payload) {
        this.payload = payload;
    }

    public intTreeNode getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(intTreeNode leftNode) {
        this.leftNode = leftNode;
    }

    public intTreeNode getRightNode() {
        return rightNode;
    }

    public void setRightNode(intTreeNode rightNode) {
        this.rightNode = rightNode;
    }
}

class intTree {
    public intTree(ArrayList<Integer> treeListInOrder) {
    }
}