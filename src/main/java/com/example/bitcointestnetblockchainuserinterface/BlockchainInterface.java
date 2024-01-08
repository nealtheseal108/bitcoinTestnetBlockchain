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
        Blockchain blockchain = new Blockchain(21000000, 100);

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
        Transaction block3transaction0 = new Transaction(blockchain, "saumitra", shantanuNode.getAddress(), 40, 3);
        transactions3.add(block3transaction0);
        Transaction block3transaction1 = new Transaction(blockchain, "shubham", jayNode.getAddress(), 20, 3);
        transactions3.add(block3transaction1);
        Transaction block3transaction2 = new Transaction(blockchain, "shrihun", saumitraNode.getAddress(), 20, 3);
        transactions3.add(block3transaction2);
        Transaction block3transaction3 = new Transaction(blockchain, "saumitra", shubhamNode.getAddress(), 20, 3);
        transactions3.add(block3transaction3);
        Transaction block3transaction4 = new Transaction(blockchain, "jay", saumitraNode.getAddress(), 10, 3);
        transactions3.add(block3transaction4);

        // construct block3
        Block block3 = new Block(blockchain, false, blockchain.getBlockchain().get(0).getThisBlockHash(), transactions3, saumitraNode.getAddress());

        // attempt to add block3; cannot be added due to incorrect 'prevHash' field
        blockchain.addNewBlock(block3);

        // print blockchain
        blockchain.printBlockchain();

        // print status and transaction history of all nodes
        blockchain.printTotalBlockchainNodeList();

        // attempt to retrieve one blockchain nodes that has interacted with the blockchain
        /* should return */ System.out.println(blockchain.getBlockchainNodeWithTree(saumitraNode));
        BlockchainNode neelNode = new BlockchainNode(blockchain, "neel");
        /* should return */ System.out.println(blockchain.getBlockchainNodeWithTree(neelNode));

        // attempt to retrieve three blocks, two of which are present in the blockchain and one of which is not
        /* should return */ System.out.println((blockchain.getBlockWithTree(block0)));
        /* should be null */ System.out.println(blockchain.getBlockWithTree(new Block(blockchain, false, block1.getPrevHash(), block1.getTransactions(), block1.getMinerAddress())));
        /* should be null */ System.out.println((blockchain.getBlockWithTree(new Block(blockchain, false, block2.getPrevHash(), block3.getTransactions(), block1.getMinerAddress()))));

        // attempt to retrieve two blockchain nodes in 'block3', both of which are not present in the block
        /* should be null */ System.out.println(block3.getBlockchainNodeWithTree(neelNode));
        /* should be null */ System.out.println(block3.getBlockchainNodeWithTree(nealNode));

        // attempt to retrieve three transactions in 'block3', two of which are present in the block and one of which is not
        /* should return */ System.out.println(block3.getTransactionWithTree(block3transaction0));
        /* should return */ System.out.println(block3.getTransactionWithTree(block3transaction2));
        /* should be null */ System.out.println(block3.getTransactionWithTree(new Transaction(blockchain, "neal", shantanuNode.getAddress(), 10, 3)));

    }
}