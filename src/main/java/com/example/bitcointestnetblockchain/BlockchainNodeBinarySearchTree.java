package com.example.bitcointestnetblockchain;

import java.util.ArrayList;
import java.util.Objects;

public class BlockchainNodeBinarySearchTree {
    protected ArrayList<BlockchainNode> unsortedArrayList;
    protected BlockchainNodeBinarySearchTreeNode rootNode;

    protected BlockchainNodeBinarySearchTree (ArrayList<BlockchainNode> unsortedArrayList) {
        this.unsortedArrayList = unsortedArrayList;
        if (unsortedArrayList != null) {
            rootNode = new BlockchainNodeBinarySearchTreeNode(unsortedArrayList.get(0), null, null);
            binaryTreeFromArrayList(rootNode, unsortedArrayList);
        }
    }

    private void binaryTreeFromArrayList(BlockchainNodeBinarySearchTreeNode node, ArrayList<BlockchainNode> unsortedArrayList)  {
        for (BlockchainNode blockchainNode: unsortedArrayList) {
            if (blockchainNode == null) {
                continue;
            } else {
                recursiveBlockchainNodePlacementInTree(node, blockchainNode);
            }
        }
    }

    private void recursiveBlockchainNodePlacementInTree(BlockchainNodeBinarySearchTreeNode node, BlockchainNode blockchainNode) {
        byte[] givenBlockchainNodeAddress = blockchainNode.getAddress();
        byte[] nodeAddress = node.getRootBlockchainNode().getAddress();

        if (calculateBlockchainNodeAddressWithByteArray(givenBlockchainNodeAddress) <= calculateBlockchainNodeAddressWithByteArray(nodeAddress)) {
            if (node.getLeftBlockchainNodeBinarySearchTreeNode() == null) {
                node.setLeftBlockchainNodeBinarySearchTreeNode(new BlockchainNodeBinarySearchTreeNode(blockchainNode, null, null));
            } else {
                recursiveBlockchainNodePlacementInTree(node.getLeftBlockchainNodeBinarySearchTreeNode(), blockchainNode);
            }
        } else {
            if (node.getRightBlockchainNodeBinarySearchTreeNode() == null) {
                node.setRightBlockchainNodeBinarySearchTreeNode(new BlockchainNodeBinarySearchTreeNode(blockchainNode, null, null));
            } else {
                recursiveBlockchainNodePlacementInTree(node.getRightBlockchainNodeBinarySearchTreeNode(), blockchainNode);
            }
        }
    }

    private long calculateBlockchainNodeAddressWithByteArray(byte[] blockHash) {
        long sum = 0;
        for (byte i: blockHash) {
            sum += i;
        }
        return sum;
    }

    public BlockchainNode findBlockchainNodeInTree(BlockchainNodeBinarySearchTreeNode rootNode, BlockchainNode blockchainNode) {
        if (rootNode != null) {
            if (Objects.equals(rootNode.getRootBlockchainNode(), blockchainNode)) {
                return blockchainNode;
            } else if (calculateBlockchainNodeAddressWithByteArray(blockchainNode.getAddress()) < calculateBlockchainNodeAddressWithByteArray(rootNode.getRootBlockchainNode().getAddress())) {
                return findBlockchainNodeInTree(rootNode.getLeftBlockchainNodeBinarySearchTreeNode(), blockchainNode);
            } else if (calculateBlockchainNodeAddressWithByteArray(blockchainNode.getAddress()) > calculateBlockchainNodeAddressWithByteArray(rootNode.getRootBlockchainNode().getAddress()))
                return findBlockchainNodeInTree(rootNode.getRightBlockchainNodeBinarySearchTreeNode(), blockchainNode);
            else if (rootNode == null) {
                return null;
            }
            return null;
        }
        return null;

    }

}