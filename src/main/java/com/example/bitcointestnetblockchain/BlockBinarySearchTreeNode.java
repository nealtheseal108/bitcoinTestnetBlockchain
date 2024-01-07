package com.example.bitcointestnetblockchain;

public class BlockBinarySearchTreeNode {
    protected Block rootBlock;
    protected BlockBinarySearchTreeNode leftBlock;
    protected BlockBinarySearchTreeNode rightBlock;

    protected BlockBinarySearchTreeNode(Block rootBlock, BlockBinarySearchTreeNode leftBlock, BlockBinarySearchTreeNode rightBlock) {
        this.rootBlock = rootBlock;
        this.leftBlock = leftBlock;
        this.rightBlock = rightBlock;
    }

    protected Block getRootBlock() {
        return rootBlock;
    }

    protected void setRootBlock(Block rootBlock) {
        this.rootBlock = rootBlock;
    }

    protected BlockBinarySearchTreeNode getLeftBlockBinarySearchTreeNode() {
        return leftBlock;
    }

    protected void setLeftBlockBinarySearchTreeNode(BlockBinarySearchTreeNode leftBlock) {
        this.leftBlock = leftBlock;
    }

    protected BlockBinarySearchTreeNode getRightBlockBinarySearchTreeNode() {
        return rightBlock;
    }

    protected void setRightBlockBinarySearchTreeNode(BlockBinarySearchTreeNode rightBlock) {
        this.rightBlock = rightBlock;
    }
}
