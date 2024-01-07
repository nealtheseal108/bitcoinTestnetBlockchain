package com.example.bitcointestnetblockchain;

public class BlockchainNodeBinarySearchTreeNode {
    private BlockchainNode rootBlockchainNode;
    private BlockchainNodeBinarySearchTreeNode leftBlockchainNode;
    private BlockchainNodeBinarySearchTreeNode rightBlockchainNode;

    protected BlockchainNodeBinarySearchTreeNode(BlockchainNode rootBlockchainNode, BlockchainNodeBinarySearchTreeNode leftBlockchainNode, BlockchainNodeBinarySearchTreeNode rightBlockchainNode) {
        this.rootBlockchainNode = rootBlockchainNode;
        this.leftBlockchainNode = leftBlockchainNode;
        this.rightBlockchainNode = rightBlockchainNode;
    }

    protected BlockchainNode getRootBlockchainNode() {
        return rootBlockchainNode;
    }

    protected void setRootBlockchainNode(BlockchainNode rootBlockchainNode) {
        this.rootBlockchainNode = rootBlockchainNode;
    }

    protected BlockchainNodeBinarySearchTreeNode getLeftBlockchainNodeBinarySearchTreeNode() {
        return leftBlockchainNode;
    }

    protected void setLeftBlockchainNodeBinarySearchTreeNode(BlockchainNodeBinarySearchTreeNode leftBlockchainNode) {
        this.leftBlockchainNode = leftBlockchainNode;
    }

    protected BlockchainNodeBinarySearchTreeNode getRightBlockchainNodeBinarySearchTreeNode() {
        return rightBlockchainNode;
    }

    protected void setRightBlockchainNodeBinarySearchTreeNode(BlockchainNodeBinarySearchTreeNode rightBlockchainNode) {
        this.rightBlockchainNode = rightBlockchainNode;
    }
}
