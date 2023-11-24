package com.example.bitcointestnetblockchainuserinterface;

import com.example.bitcointestnetblockchain.Block;
import com.example.bitcointestnetblockchain.Blockchain;
import com.example.bitcointestnetblockchain.BlockchainNode;
import com.example.bitcointestnetblockchain.Transaction;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class BlockchainInterface {
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // Initialize SHA-256 hash algorithm
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        // create new blockchain
        Blockchain blockchain = new Blockchain();

        // the BlockchainNode "shrihun" is the miner of the first block
        BlockchainNode shrihunNode = new BlockchainNode("shrihun");
        blockchain.addBlockchainNode(shrihunNode);

        // the BlockchainNodes "neal", "shubham", "jay", "shantanu", "shyam", and "saumitra" are also created
        BlockchainNode nealNode = new BlockchainNode("neal");
        blockchain.addBlockchainNode(nealNode);

        BlockchainNode shubhamNode = new BlockchainNode("shubham");
        blockchain.addBlockchainNode(shubhamNode);

        BlockchainNode jayNode = new BlockchainNode("jay");
        blockchain.addBlockchainNode(jayNode);

        BlockchainNode shantanuNode = new BlockchainNode("shantanu");
        blockchain.addBlockchainNode(shantanuNode);

        BlockchainNode shyamNode = new BlockchainNode("shyam");
        blockchain.addBlockchainNode(shyamNode);

        BlockchainNode saumitraNode = new BlockchainNode("saumitra");
        blockchain.addBlockchainNode(saumitraNode);

        // print entire list of nodes in blockchain
        blockchain.printTotalBlockchainNodeList();

        // Create block 0 with "shrihun" as the block miner
        Block block0 = new Block(true, null, null, shrihunNode.getAddress());

        // Add the new block to the blockchain
        blockchain.addNewBlock(block0);

        // Construct transactions for block 1
        ArrayList<Transaction> transactions1 = new ArrayList<>();
        transactions1.add(new Transaction("shrihun", nealNode.getAddress(), 10));
        transactions1.add(new Transaction("shrihun", shubhamNode.getAddress(), 10));
        transactions1.add(new Transaction("shrihun", jayNode.getAddress(), 10));
        transactions1.add(new Transaction("shrihun", shantanuNode.getAddress(), 10));
        transactions1.add(new Transaction("shrihun", shyamNode.getAddress(), 10));
        transactions1.add(new Transaction("shrihun", saumitraNode.getAddress(), 10));

        System.out.println("\n\n");

        // Construct block 1
        Block block1 = new Block(false, blockchain.getBlockchain().getLast().getThisBlockHash(), transactions1, digest.digest("shubham".getBytes(StandardCharsets.UTF_16)));

        // add block 1 to blockchain
        blockchain.addNewBlock(block1);

        // Print the updated blockchain
        blockchain.printBlockchain();

        System.out.println(blockchain.getBlockchain().get(1).getPrevHash());
    }
}