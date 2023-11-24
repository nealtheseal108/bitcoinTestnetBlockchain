package com.example.bitcointestnetblockchain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
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
        if (blockchain.isEmpty()) {
            if (block.getMinerAddress() == null) {
                System.out.println("Null miner address. Needs a miner address to send coinbase transaction to.");
            } else if (block.isGenesis()) {
                blockchain.add(block);
                network.coinbaseTransaction(block.getMinerAddress());
                totalBlockchainNodeList.getBlockchainNodeByAddress(block.getMinerAddress()).inputTransactionList.add(new Transaction(null, block.getMinerAddress(), 100));
                if (block.getTransactions() != null && !block.isGenesis()) {
                    for (Transaction transaction: block.getTransactions()) {
                        if (Arrays.equals(digest.digest(transaction.getFromUserName().getBytes(StandardCharsets.UTF_16)), transaction.getFromAddress()))
                        totalBlockchainNodeList.getBlockchainNodeByAddress(transaction.getToAddress()).addTransactionToInputList(transaction);
                        totalBlockchainNodeList.getBlockchainNodeByAddress(transaction.getFromAddress()).addTransactionToOutputList(transaction);
                        totalBlockchainNodeList.getBlockchainNodeByAddress(transaction.getToAddress()).transact(transaction.getFromUserName(), transaction.getToAddress(), transaction.getTransferAmount());
                    }
                }
                return true;
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

    public void printBlockchain() {
        for (Block block: blockchain) {
            System.out.println("Block " + blockchain.indexOf(block) + "\n");
            System.out.println("Previous block hash: " + block.getPrevHash() + ".");
            if (block.getTransactions() != null) {
                for (Transaction transaction: block.getTransactions()) {
                    System.out.print(transaction.getFromAddress() + " sent " + transaction.getTransferAmount() + " to " + transaction.getToAddress() + ". " + "The balance of " + transaction.getFromAddress() + " is now " + totalBlockchainNodeList.getBlockchainNodeByAddress(transaction.getFromAddress()).getBalance() + " and the balance of " + transaction.getToAddress() + " is now " + totalBlockchainNodeList.getBlockchainNodeByAddress(transaction.getToAddress()).getBalance() + ". \n");
                }
            }

            System.out.print(block.getMinerAddress() + " received 100 coins for their computational effort expended to create the block.\n");
            System.out.print("This block hash: " + block.getThisBlockHash());
            System.out.println("\n\n");
        }
    }

    public void addBlockchainNode(BlockchainNode newBlockchainNode) {
        totalBlockchainNodeList.addBlockchainNodeToList(newBlockchainNode);
    }

    public LinkedList<Block> getBlockchain() {
        return blockchain;
    }

}
