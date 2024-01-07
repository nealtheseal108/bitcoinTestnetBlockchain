package com.example.bitcointestnetblockchain;

import java.util.ArrayList;

public class BlockchainNodeBinarySearchTree {
    protected ArrayList<BlockchainNodeBinarySearchTreeNode> unsortedArrayList;
    protected BlockchainNodeBinarySearchTreeNode rootNode;
    protected int iterator;

    protected BlockchainNodeBinarySearchTree (ArrayList<BlockchainNodeBinarySearchTreeNode> unsortedArrayList) {
        this.unsortedArrayList = unsortedArrayList;
        rootNode = unsortedArrayList.get(0);
        binaryTreeFromArrayList(rootNode, 0);
    }

    private void binaryTreeFromArrayList(BlockchainNodeBinarySearchTreeNode node, int iterator)  {
        if (calculateBlockchainNodeAddressWithByteArray(unsortedArrayList.get(iterator).getRootBlockchainNode().getAddress()) < calculateBlockchainNodeAddressWithByteArray(node.getRootBlockchainNode().getAddress())) {
            this.iterator++;
            binaryTreeFromArrayList(new BlockchainNodeBinarySearchTreeNode(node.getLeftBlockchainNodeBinarySearchTreeNode().getRootBlockchainNode(), null, null), this.iterator);
            node.setLeftBlockchainNodeBinarySearchTreeNode(node.getBlockchainNodeBinarySearchTreeNode());
        }
        else {
            this.iterator++;
            binaryTreeFromArrayList(new BlockchainNodeBinarySearchTreeNode(node.getBlockchainNodeBinarySearchTreeNode().getRootBlockchainNode(), null, null), this.iterator);
            node.setRightBlockchainNodeBinarySearchTreeNode(node.getBlockchainNodeBinarySearchTreeNode());
        }
    }
    private long calculateBlockchainNodeAddressWithByteArray(byte[] blockHash) {
        long sum = 0;
        for (byte i: blockHash) {
            sum += i;
        }
        return sum;
    }

}