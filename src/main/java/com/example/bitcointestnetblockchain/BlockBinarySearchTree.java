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

    protected BlockchainNodeBinarySearchTreeNode findBlockchainNodeInBlock(BlockchainNodeBinarySearchTreeNode rootNode, BlockchainNodeBinarySearchTreeNode blockchainNode) {
        if (Objects.equals(rootNode, blockchainNode)) {
            return rootNode;
        } else if (calculateBlockHashWithByteArray(blockchainNode.getRootBlockchainNode().getAddress()) < calculateBlockHashWithByteArray(rootNode.getRootBlockchainNode().getAddress())) {
            findBlockchainNodeInBlock(rootNode.getLeftBlockchainNodeBinarySearchTreeNode(), blockchainNode);
        } else if (calculateBlockHashWithByteArray(blockchainNode.getRootBlockchainNode().getAddress()) > calculateBlockHashWithByteArray(rootNode.getRootBlockchainNode().getAddress()))
            findBlockchainNodeInBlock(rootNode.getRightBlockchainNodeBinarySearchTreeNode(), blockchainNode);
        else if (rootNode == null) {
            return null;
        }
        return null;
    }

}
