package com.example.bitcointestnetblockchain;

public class TransactionBinarySearchTreeNode {
    protected Transaction rootTransaction;
    protected TransactionBinarySearchTreeNode leftTransaction;
    protected TransactionBinarySearchTreeNode rightTransaction;

    protected TransactionBinarySearchTreeNode(Transaction rootTransaction, TransactionBinarySearchTreeNode leftTransaction, TransactionBinarySearchTreeNode rightTransaction) {
        this.rootTransaction = rootTransaction;
        this.leftTransaction = leftTransaction;
        this.rightTransaction = rightTransaction;
    }

    protected Transaction getRootTransaction() {
        return rootTransaction;
    }

    protected void setRootTransaction(Transaction rootTransaction) {
        this.rootTransaction = rootTransaction;
    }

    protected TransactionBinarySearchTreeNode getLeftTransactionBinarySearchTreeNode() {
        return leftTransaction;
    }

    protected void setLeftTransactionBinarySearchTreeNode(TransactionBinarySearchTreeNode leftTransaction) {
        this.leftTransaction = leftTransaction;
    }

    protected TransactionBinarySearchTreeNode getRightTransactionBinarySearchTreeNode() {
        return rightTransaction;
    }

    protected void setRightTransactionBinarySearchTreeNode(TransactionBinarySearchTreeNode rightTransaction) {
        this.rightTransaction = rightTransaction;
    }
}

