package com.example.bitcointestnetblockchain;

import java.util.ArrayList;
import java.util.Objects;

public class BlockBinarySearchTree {
    protected ArrayList<BlockBinarySearchTreeNode> unsortedArrayList;
    protected BlockBinarySearchTreeNode rootNode;
    protected int iterator;

    protected BlockBinarySearchTree (ArrayList<BlockBinarySearchTreeNode> unsortedArrayList) {
        this.unsortedArrayList = unsortedArrayList;
        rootNode = unsortedArrayList.get(0);
        binaryTreeFromArrayList(rootNode, 0);
    }

    private void binaryTreeFromArrayList(BlockBinarySearchTreeNode node, int iterator)  {
        if (calculateBlockHashWithByteArray(unsortedArrayList.get(iterator).getRootBlock().getThisBlockHash()) < calculateBlockHashWithByteArray(node.rootBlock.getThisBlockHash())) {
            this.iterator++;
            binaryTreeFromArrayList(new BlockBinarySearchTreeNode(node.getLeftBlockBinarySearchTreeNode().getRootBlock(), null, null), this.iterator);
            node.setLeftBlockBinarySearchTreeNode(node.getLeftBlockBinarySearchTreeNode());
        }
        else {
            this.iterator++;
            binaryTreeFromArrayList(new BlockBinarySearchTreeNode(node.getRightBlockBinarySearchTreeNode().getRootBlock(), null, null), this.iterator);
            node.setRightBlockBinarySearchTreeNode(node.getRightBlockBinarySearchTreeNode());
        }
    }
    private long calculateBlockHashWithByteArray(byte[] blockHash) {
        long sum = 0;
        for (byte i: blockHash) {
            sum += i;
        }
        return sum;
    }

    public BlockBinarySearchTreeNode findBlockInTree(BlockBinarySearchTreeNode rootNode, BlockBinarySearchTreeNode blockNode) {
        if (Objects.equals(rootNode, blockNode)) {
            return rootNode;
        } else if (calculateBlockHashWithByteArray(blockNode.getRootBlock().getThisBlockHash()) < calculateBlockHashWithByteArray(rootNode.getRootBlock().getThisBlockHash())) {
            findBlockInTree(rootNode.getLeftBlockBinarySearchTreeNode(), blockNode);
        } else if (calculateBlockHashWithByteArray(blockNode.getRootBlock().getThisBlockHash()) > calculateBlockHashWithByteArray(rootNode.getRootBlock().getThisBlockHash()))
            findBlockInTree(rootNode.getRightBlockBinarySearchTreeNode(), blockNode);
        else if (rootNode == null) {
            return null;
        }
        return null;
    }

}
