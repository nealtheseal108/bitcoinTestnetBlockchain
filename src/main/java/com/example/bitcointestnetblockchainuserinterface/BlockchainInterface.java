package com.example.bitcointestnetblockchainuserinterface;

import com.example.bitcointestnetblockchain.Block;
import com.example.bitcointestnetblockchain.Blockchain;
import com.example.bitcointestnetblockchain.BlockchainNode;

import java.security.NoSuchAlgorithmException;

public class BlockchainInterface {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        Blockchain blockchain = new Blockchain();
        BlockchainNode genesisBlockMiner = new BlockchainNode("Shrihun");
        blockchain.addBlockchainNode(genesisBlockMiner);

        blockchain.addNewBlock(new Block(null, null, genesisBlockMiner.getAddress()));

        blockchain.printBlockchain();
    }
}

