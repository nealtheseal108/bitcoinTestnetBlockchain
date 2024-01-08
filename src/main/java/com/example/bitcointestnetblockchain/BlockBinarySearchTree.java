package com.example.bitcointestnetblockchain;

import java.util.ArrayList;
import java.util.Objects;

public class BlockBinarySearchTree {
    protected ArrayList<Block> unsortedArrayList;
    protected BlockBinarySearchTreeNode rootNode;

    protected BlockBinarySearchTree (ArrayList<Block> unsortedArrayList) {
        this.unsortedArrayList = unsortedArrayList;
        if (unsortedArrayList != null) {
            rootNode = new BlockBinarySearchTreeNode(unsortedArrayList.get(0), null, null);
            binaryTreeFromArrayList(rootNode, unsortedArrayList);
        }
    }

    private void binaryTreeFromArrayList(BlockBinarySearchTreeNode node, ArrayList<Block> unsortedArrayList)  {
        for (Block block: unsortedArrayList) {
            if (block == null) {
                continue;
            } else {
                recursiveBlockPlacementInTree(node, block);
            }
        }
    }

    private void recursiveBlockPlacementInTree(BlockBinarySearchTreeNode node, Block block) {
        byte[] givenBlockHash = block.getThisBlockHash();
        byte[] nodeBlockHash = node.getRootBlock().getThisBlockHash();

        if (calculateBlockHashWithByteArray(givenBlockHash) <= calculateBlockHashWithByteArray(nodeBlockHash)) {
            if (node.getLeftBlockBinarySearchTreeNode() == null) {
                node.setLeftBlockBinarySearchTreeNode(new BlockBinarySearchTreeNode(block, null, null));
            } else {
                recursiveBlockPlacementInTree(node.getLeftBlockBinarySearchTreeNode(), block);
            }
        } else {
            if (node.getRightBlockBinarySearchTreeNode() == null) {
                node.setRightBlockBinarySearchTreeNode(new BlockBinarySearchTreeNode(block, null, null));
            } else {
                recursiveBlockPlacementInTree(node.getRightBlockBinarySearchTreeNode(), block);
            }
        }
    }

    private long calculateBlockHashWithByteArray(byte[] blockHash) {
        long sum = 0;
        for (byte i: blockHash) {
            sum += i;
        }
        return sum;
    }

    public Block findBlockInTree(BlockBinarySearchTreeNode rootNode, Block block) {
        if (rootNode != null) {
            if (Objects.equals(rootNode.getRootBlock(), block)) {
                return block;
            } else if (calculateBlockHashWithByteArray(block.getThisBlockHash()) < calculateBlockHashWithByteArray(rootNode.getRootBlock().getThisBlockHash())) {
                return findBlockInTree(rootNode.getLeftBlockBinarySearchTreeNode(), block);
            } else if (calculateBlockHashWithByteArray(block.getThisBlockHash()) > calculateBlockHashWithByteArray(rootNode.getRootBlock().getThisBlockHash()))
                return findBlockInTree(rootNode.getRightBlockBinarySearchTreeNode(), block);
            else if (rootNode == null) {
                return null;
            }
            return null;
        }
        return null;
    }

}
