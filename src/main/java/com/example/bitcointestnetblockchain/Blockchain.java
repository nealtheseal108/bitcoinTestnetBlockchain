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
    protected TotalBlockchainNodeList totalBlockchainNodeList;
    private Network network;
    public Blockchain() throws NoSuchAlgorithmException {
        blockchain = new LinkedList<>();
        totalBlockchainNodeList = new TotalBlockchainNodeList();
        network = new Network(this);
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
                totalBlockchainNodeList.getBlockchainNodeByAddress(block.getMinerAddress()).inputTransactionList.add(new Transaction(this, null, block.getMinerAddress(), 100, 0));
                return true;
            } else {
                 return false;
            }
        } else if (!(Arrays.equals(block.getMinerAddress(), null)) && Arrays.equals(block.getPrevHash(), blockchain.getLast().getThisBlockHash())) {
            blockchain.add(block);
            network.coinbaseTransaction(block.getMinerAddress());
            totalBlockchainNodeList.getBlockchainNodeByAddress(block.getMinerAddress()).inputTransactionList.add(new Transaction(this, null, block.getMinerAddress(), 100, blockchain.size()));
            for (int i = 0; i < block.getTransactions().size() - 1; i++) {
                Transaction transaction = block.getTransactions().get(i);
                if (transaction.getBlockHeight() == blockchain.size()) {
                    totalBlockchainNodeList.getBlockchainNodeByAddress(transaction.getToAddress()).addTransactionToInputList(transaction);
                    totalBlockchainNodeList.getBlockchainNodeByAddress(transaction.getFromAddress()).addTransactionToOutputList(transaction);
                    totalBlockchainNodeList.getBlockchainNodeByAddress(transaction.getFromAddress()).transact(transaction.getFromUserName(), transaction.getToAddress(), transaction.getTransferAmount());
                }
            }
            return true;
        }
        return false;
    }

    public void printBlockchain() throws UnsupportedEncodingException {
        for (int i = 0; i < blockchain.size(); i++) {
            Block block = blockchain.get(i);
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

    public void printTotalBlockchainNodeList() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        totalBlockchainNodeList.printTotalBlockchainNodeList();
    }

    public LinkedList<Block> getBlockchain() {
        return blockchain;
    }

    public Block getBlockByCoinbaseTransactionHash(byte[] coinbaseTransactionHash) throws NoSuchAlgorithmException {
        for (int i = 0; i < blockchain.size(); i++) {
            Block block = blockchain.get(i);
            for (int j = 0; j < block.getTransactions().size(); j++) {
                Transaction transaction = block.getTransactions().get(j);
                if (Arrays.equals(transaction.getFromAddress(), null)) {
                    if (Arrays.equals(transaction.getTransactionHash(), coinbaseTransactionHash)) {
                        return block;
                    }
                }
            }
        }
        return null;
    }

}
