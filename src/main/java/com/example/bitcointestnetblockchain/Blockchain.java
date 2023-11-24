package com.example.bitcointestnetblockchain;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.LinkedList;

public class Blockchain {
    public LinkedList<Block> blockchain;

    private MessageDigest digest = MessageDigest.getInstance("SHA-256");
    private TotalBlockchainNodeList totalBlockchainNodeList;
    private Network network;
    public Blockchain() throws NoSuchAlgorithmException {
        blockchain = new LinkedList<>();
        totalBlockchainNodeList = new TotalBlockchainNodeList();
        network = new Network();
    }

    public boolean addNewBlock(Block block) throws NoSuchAlgorithmException {
        if (block.getMinerAddress() == null) {
            System.out.println("Null miner address. Needs a miner address to send coinbase transaction to.");
            return false;
        }
        if (blockchain.isEmpty()) {
             if (block.isGenesis()) {
                blockchain.add(block);
                network.coinbaseTransaction(block.getMinerAddress());
                totalBlockchainNodeList.getBlockchainNodeByAddress(block.getMinerAddress()).inputTransactionList.add(new Transaction(null, block.getMinerAddress(), 100));
                return true;
            } else {
                 return false;
            }
        } else if (block.getMinerAddress() != null && Arrays.equals(block.getPrevHash(), blockchain.getLast().getPrevHash())) {
            blockchain.add(block);
            network.coinbaseTransaction(block.getMinerAddress());
            totalBlockchainNodeList.getBlockchainNodeByAddress(block.getMinerAddress()).inputTransactionList.add(new Transaction(null, block.getMinerAddress(), 100));
            for (Transaction transaction: block.getTransactions()) {
                totalBlockchainNodeList.getBlockchainNodeByAddress(transaction.getToAddress()).addTransactionToInputList(transaction);
                totalBlockchainNodeList.getBlockchainNodeByAddress(transaction.getFromAddress()).addTransactionToOutputList(transaction);
            }
            return true;
        }

        return false;
    }

    public void printBlockchain() throws UnsupportedEncodingException {
        for (Block block: blockchain) {
            System.out.println("Block " + blockchain.indexOf(block) + "\n");
            if (block.getPrevHash() != null && !block.isGenesis()) {
                System.out.println("Previous block hash: " + Base64.getEncoder().encodeToString(block.getPrevHash()) + ".");
            }
            if (block.getTransactions() != null) {
                for (Transaction transaction: block.getTransactions()) {
                    System.out.print(Base64.getEncoder().encodeToString(transaction.getFromAddress()) + " sent " + transaction.getTransferAmount() + " coins to " + Base64.getEncoder().encodeToString(transaction.getToAddress()) + ". \n");
                }
            }

            System.out.print(Base64.getEncoder().encodeToString(block.getMinerAddress()) + " received 100 coins for their computational effort expended to create the block.\n");
            System.out.print("This block hash: " + Base64.getEncoder().encodeToString(block.getThisBlockHash()));
            System.out.println("\n\n");
        }
    }

    public void addBlockchainNode(BlockchainNode newBlockchainNode) {
        totalBlockchainNodeList.addBlockchainNodeToList(newBlockchainNode);
    }

    public void printTotalBlockchainNodeList() throws UnsupportedEncodingException {
        totalBlockchainNodeList.printTotalBlockchainNodeList();
    }

    public LinkedList<Block> getBlockchain() {
        return blockchain;
    }

}
