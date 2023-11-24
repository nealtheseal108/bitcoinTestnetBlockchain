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

        // Create a new block
        Block newBlock = new Block(null, null, genesisBlockMiner.getAddress());

        // Add the new block to the blockchain
        blockchain.addNewBlock(newBlock);

        // Print the updated blockchain
        blockchain.printBlockchain();
    }
}