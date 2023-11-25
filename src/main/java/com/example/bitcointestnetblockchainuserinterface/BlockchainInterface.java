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

        // hash(block number, nonce) -> value lower than target value

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

        // print the status and transaction history of all nodes
        blockchain.printTotalBlockchainNodeList();

        // compile transactions for block 2
        ArrayList<Transaction> transactions2 = new ArrayList<>();
        transactions2.add(new Transaction(blockchain, "shubham", nealNode.getAddress(), 40, 2));
        transactions2.add(new Transaction(blockchain, "shubham", jayNode.getAddress(), 20, 2));
        transactions2.add(new Transaction(blockchain, "saumitra", saumitraNode.getAddress(), 20, 2));
        transactions2.add(new Transaction(blockchain, "saumitra", saumitraNode.getAddress(), 20, 2));
        transactions2.add(new Transaction(blockchain, "saumitra", saumitraNode.getAddress(), 20, 2));

        // construct block 2
        Block block2 = new Block(blockchain, false, blockchain.getBlockchain().getLast().getThisBlockHash(), transactions2, saumitraNode.getAddress());

        // add block 2; the malicious transactions of 'saumitraNode' will be omitted
        blockchain.addNewBlock(block2);

        // print blockchain
        blockchain.printBlockchain();

        // print status and transaction history of all nodes
        blockchain.printTotalBlockchainNodeList();

        // compile transactions for block3
        ArrayList<Transaction> transactions3 = new ArrayList<>();
        transactions3.add(new Transaction(blockchain, "saumitra", shantanuNode.getAddress(), 40, 3));
        transactions3.add(new Transaction(blockchain, "shubham", jayNode.getAddress(), 20, 3));
        transactions3.add(new Transaction(blockchain, "shrihun", saumitraNode.getAddress(), 20, 3));
        transactions3.add(new Transaction(blockchain, "saumitra", shubhamNode.getAddress(), 20, 3));
        transactions3.add(new Transaction(blockchain, "jay", saumitraNode.getAddress(), 10, 3));

        // construct block3
        Block block3 = new Block(blockchain, false, blockchain.getBlockchain().get(0).getThisBlockHash(), transactions3, saumitraNode.getAddress());

        // attempt to add block3; cannot be added due to incorrect 'prevHash' field
        blockchain.addNewBlock(block3);

        // print blockchain
        blockchain.printBlockchain();

        // print status and transaction history of all nodes
        blockchain.printTotalBlockchainNodeList();
    }
}