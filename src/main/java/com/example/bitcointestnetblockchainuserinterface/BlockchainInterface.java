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
        Blockchain blockchain = new Blockchain();

        // the BlockchainNode "shrihun" is the miner of the first block
        BlockchainNode shrihunNode = new BlockchainNode(blockchain, "shrihun");
        blockchain.addBlockchainNode(shrihunNode);

        // the BlockchainNodes "neal", "shubham", "jay", "shantanu", "shyam", and "saumitra" are also created
        BlockchainNode nealNode = new BlockchainNode(blockchain, "neal");
        blockchain.addBlockchainNode(nealNode);

        BlockchainNode shubhamNode = new BlockchainNode(blockchain, "shubham");
        blockchain.addBlockchainNode(shubhamNode);

        BlockchainNode jayNode = new BlockchainNode(blockchain, "jay");
        blockchain.addBlockchainNode(jayNode);

        BlockchainNode shantanuNode = new BlockchainNode(blockchain, "shantanu");
        blockchain.addBlockchainNode(shantanuNode);

        BlockchainNode shyamNode = new BlockchainNode(blockchain, "shyam");
        blockchain.addBlockchainNode(shyamNode);

        BlockchainNode saumitraNode = new BlockchainNode(blockchain, "saumitra");
        blockchain.addBlockchainNode(saumitraNode);

        // print entire list of nodes in blockchain
        blockchain.printTotalBlockchainNodeList();

        // Create block 0 with "shrihun" as the block miner
        Block block0 = new Block(blockchain,true, null, null, shrihunNode.getAddress());

        // Add the new block to the blockchain
        blockchain.addNewBlock(block0);

        // Construct transactions for block 1
        ArrayList<Transaction> transactions1 = new ArrayList<>();
        transactions1.add(new Transaction(blockchain, "shrihun", nealNode.getAddress(), 10, 1));
        transactions1.add(new Transaction(blockchain, "shrihun", shubhamNode.getAddress(), 10, 1));
        transactions1.add(new Transaction(blockchain, "shrihun", jayNode.getAddress(), 10, 1));
        transactions1.add(new Transaction(blockchain, "shrihun", shantanuNode.getAddress(), 10, 1));
        transactions1.add(new Transaction(blockchain, "shrihun", shyamNode.getAddress(), 10, 1));
        transactions1.add(new Transaction(blockchain, "shrihun", saumitraNode.getAddress(), 10, 1));

        System.out.println("\n\n");

        // construct block 1
        Block block1 = new Block(blockchain, false, blockchain.getBlockchain().getLast().getThisBlockHash(), transactions1, digest.digest("shubham".getBytes(StandardCharsets.UTF_16)));

        // add block 1 to blockchain
        blockchain.addNewBlock(block1);

        // print the updated blockchain
        blockchain.printBlockchain();

        blockchain.printTotalBlockchainNodeList();
    }
}