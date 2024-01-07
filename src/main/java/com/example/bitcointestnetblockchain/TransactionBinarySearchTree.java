package com.example.bitcointestnetblockchain;

import java.util.ArrayList;
import java.util.Objects;

public class TransactionBinarySearchTree {
    protected ArrayList<TransactionBinarySearchTreeNode> unsortedArrayList;
    protected TransactionBinarySearchTreeNode rootNode;
    protected int iterator;

    protected TransactionBinarySearchTree (ArrayList<TransactionBinarySearchTreeNode> unsortedArrayList) {
        this.unsortedArrayList = unsortedArrayList;
        rootNode = unsortedArrayList.get(0);
        binaryTreeFromArrayList(rootNode, 0);
    }

    private void binaryTreeFromArrayList(TransactionBinarySearchTreeNode node, int iterator)  {
        if (calculateTransactionHashWithByteArray(unsortedArrayList.get(iterator).getRootTransaction().getTransactionHash()) < calculateTransactionHashWithByteArray(node.rootTransaction.getTransactionHash())) {
            this.iterator++;
            binaryTreeFromArrayList(new TransactionBinarySearchTreeNode(node.getLeftTransactionBinarySearchTreeNode().getRootTransaction(), null, null), this.iterator);
            node.setLeftTransactionBinarySearchTreeNode(node.getLeftTransactionBinarySearchTreeNode());
        }
        else {
            this.iterator++;
            binaryTreeFromArrayList(new TransactionBinarySearchTreeNode(node.getRightTransactionBinarySearchTreeNode().getRootTransaction(), null, null), this.iterator);
            node.setRightTransactionBinarySearchTreeNode(node.getRightTransactionBinarySearchTreeNode());
        }
    }
    private long calculateTransactionHashWithByteArray(byte[] TransactionHash) {
        long sum = 0;
        for (byte i: TransactionHash) {
            sum += i;
        }
        return sum;
    }

    public TransactionBinarySearchTreeNode findTransactionInTree(TransactionBinarySearchTreeNode rootNode, TransactionBinarySearchTreeNode blockchainNode) {
        if (Objects.equals(rootNode, blockchainNode)) {
            return rootNode;
        } else if (calculateTransactionHashWithByteArray(blockchainNode.getRootTransaction().getTransactionHash()) < calculateTransactionHashWithByteArray(rootNode.getRootTransaction().getTransactionHash())) {
            findTransactionInTree(rootNode.getLeftTransactionBinarySearchTreeNode(), blockchainNode);
        } else if (calculateTransactionHashWithByteArray(blockchainNode.getRootTransaction().getTransactionHash()) > calculateTransactionHashWithByteArray(rootNode.getRootTransaction().getTransactionHash())) {
            findTransactionInTree(rootNode.getRightTransactionBinarySearchTreeNode(), blockchainNode);
        }
        else if (rootNode == null) {
            return null;
        }
        return null;
    }

}


