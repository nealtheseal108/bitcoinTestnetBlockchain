package com.example.bitcointestnetblockchain;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

class TotalBlockchainNodeList {
    protected static ArrayList<BlockchainNode> BlockchainNodeList;

    TotalBlockchainNodeList() {
        BlockchainNodeList = new ArrayList<>();
    }

    static boolean addBlockchainNodeToList(BlockchainNode newBlockchainNode) {
        if ((Objects.equals(getBlockchainNodeByBlockchainNode(newBlockchainNode), null))) {
            BlockchainNodeList.add(newBlockchainNode);
            return true;
        }

        return false;
    }

    public static BlockchainNode getBlockchainNodeByBlockchainNode(BlockchainNode searchedBlockchainNode) {
        int indexOfSearchedBlockchainNode = BlockchainNodeList.indexOf(searchedBlockchainNode);
        if (indexOfSearchedBlockchainNode == -1) {
            return null;
        } else {
            return BlockchainNodeList.get(indexOfSearchedBlockchainNode);
        }
    }

    public static BlockchainNode getBlockchainNodeByAddress(byte[] address) {
        for (int i = 0; i < BlockchainNodeList.size(); i++) {
            BlockchainNode blockchainNode = BlockchainNodeList.get(i);
            if (Arrays.equals(blockchainNode.getAddress(), address)) {
                return blockchainNode;
            }
        }

        return null;
    }

    public static void printTotalBlockchainNodeList() throws UnsupportedEncodingException {
        for (int i = 0; i < BlockchainNodeList.size(); i++) {
            BlockchainNode blockchainNode = BlockchainNodeList.get(i);
            blockchainNode.printBlockchainNode();
        }
    }

}
