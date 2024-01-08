package com.example.bitcointestnetblockchain;

import java.util.ArrayList;
import java.util.Objects;

public class TransactionBinarySearchTree {
    protected ArrayList<Transaction> unsortedArrayList;
    protected TransactionBinarySearchTreeNode rootNode;
    protected int iterator;

    protected TransactionBinarySearchTree (ArrayList<Transaction> unsortedArrayList) {
        this.unsortedArrayList = unsortedArrayList;
        rootNode = new TransactionBinarySearchTreeNode(unsortedArrayList.get(0), null, null);
        binaryTreeFromArrayList(rootNode, unsortedArrayList);
    }

    private void binaryTreeFromArrayList(TransactionBinarySearchTreeNode node, ArrayList<Transaction> unsortedArrayList)  {
        for (Transaction transaction: unsortedArrayList) {
            if (transaction == null) {
                continue;
            } else {
                recursiveTransactionPlacementInTree(node, transaction);
            }
        }
    }

    private void recursiveTransactionPlacementInTree(TransactionBinarySearchTreeNode node, Transaction transaction) {
        byte[] givenTransactionHash = transaction.getTransactionHash();
        byte[] nodeTransactionHash = node.getRootTransaction().getTransactionHash();

        if (calculateTransactionHashWithByteArray(givenTransactionHash) <= calculateTransactionHashWithByteArray(nodeTransactionHash)) {
            if (node.getLeftTransactionBinarySearchTreeNode() == null) {
                node.setLeftTransactionBinarySearchTreeNode(new TransactionBinarySearchTreeNode(transaction, null, null));
            } else {
                recursiveTransactionPlacementInTree(node.getLeftTransactionBinarySearchTreeNode(), transaction);
            }
        } else {
            if (node.getRightTransactionBinarySearchTreeNode() == null) {
                node.setRightTransactionBinarySearchTreeNode(new TransactionBinarySearchTreeNode(transaction, null, null));
            } else {
                recursiveTransactionPlacementInTree(node.getRightTransactionBinarySearchTreeNode(), transaction);
            }
        }
    }

    private long calculateTransactionHashWithByteArray(byte[] TransactionHash) {
        long sum = 0;
        for (byte i: TransactionHash) {
            sum += i;
        }
        return sum;
    }

    public Transaction findTransactionInTree(TransactionBinarySearchTreeNode rootNode, Transaction transaction) {
        if (Objects.equals(rootNode.getRootTransaction(), transaction)) {
            return transaction;
        } else if (calculateTransactionHashWithByteArray(transaction.getTransactionHash()) < calculateTransactionHashWithByteArray(rootNode.getRootTransaction().getTransactionHash())) {
            findTransactionInTree(rootNode.getLeftTransactionBinarySearchTreeNode(), transaction);
        } else if (calculateTransactionHashWithByteArray(transaction.getTransactionHash()) > calculateTransactionHashWithByteArray(rootNode.getRootTransaction().getTransactionHash()))
            findTransactionInTree(rootNode.getRightTransactionBinarySearchTreeNode(), transaction);
        else if (rootNode == null) {
            return null;
        }
        return null;
    }

}


