package com.example.bitcointestnetblockchain;

import java.util.ArrayList;
import java.util.Objects;

public class BlockchainNodeBinarySearchTree {
    protected ArrayList<BlockchainNodeBinarySearchTreeNode> unsortedArrayList;
    protected BlockchainNodeBinarySearchTreeNode rootNode;
    protected int iterator = 1;

    protected BlockchainNodeBinarySearchTree (ArrayList<BlockchainNodeBinarySearchTreeNode> unsortedArrayList) {
        this.unsortedArrayList = unsortedArrayList;
        rootNode = unsortedArrayList.get(0);
        binaryTreeFromArrayList(rootNode, 0);
    }

    private void binaryTreeFromArrayList(BlockchainNodeBinarySearchTreeNode node, int iterator)  {
        if (calculateBlockchainNodeAddressWithByteArray(unsortedArrayList.get(iterator).getRootBlockchainNode().getAddress()) < calculateBlockchainNodeAddressWithByteArray(node.getRootBlockchainNode().getAddress())) {
            this.iterator++;
            binaryTreeFromArrayList(new BlockchainNodeBinarySearchTreeNode(node.getLeftBlockchainNodeBinarySearchTreeNode().getRootBlockchainNode(), null, null), this.iterator);
            node.setLeftBlockchainNodeBinarySearchTreeNode(unsortedArrayList.get(iterator));
        }
        else {
            this.iterator++;
            binaryTreeFromArrayList(new BlockchainNodeBinarySearchTreeNode(node.getRightBlockchainNodeBinarySearchTreeNode().getRootBlockchainNode(), null, null), this.iterator);
            node.setRightBlockchainNodeBinarySearchTreeNode(unsortedArrayList.get(iterator));
        }
    }
    private long calculateBlockchainNodeAddressWithByteArray(byte[] blockHash) {
        long sum = 0;
        for (byte i: blockHash) {
            sum += i;
        }
        return sum;
    }

    public BlockchainNodeBinarySearchTreeNode findBlockchainNodeInTree(BlockchainNodeBinarySearchTreeNode rootNode, BlockchainNodeBinarySearchTreeNode blockchainNode) {
        if (Objects.equals(rootNode, blockchainNode)) {
            return rootNode;
        } else if (calculateBlockchainNodeAddressWithByteArray(blockchainNode.getRootBlockchainNode().getAddress()) < calculateBlockchainNodeAddressWithByteArray(rootNode.getRootBlockchainNode().getAddress())) {
            findBlockchainNodeInTree(rootNode.getLeftBlockchainNodeBinarySearchTreeNode(), blockchainNode);
        } else if (calculateBlockchainNodeAddressWithByteArray(blockchainNode.getRootBlockchainNode().getAddress()) > calculateBlockchainNodeAddressWithByteArray(rootNode.getRootBlockchainNode().getAddress()))
            findBlockchainNodeInTree(rootNode.getRightBlockchainNodeBinarySearchTreeNode(), blockchainNode);
        else if (rootNode == null) {
            return null;
        }
        return null;
    }

}