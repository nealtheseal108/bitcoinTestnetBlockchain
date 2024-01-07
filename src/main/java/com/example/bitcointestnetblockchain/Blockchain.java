package com.example.bitcointestnetblockchain;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.LinkedList;

public class Blockchain {
    public LinkedList<Block> blockchain;

    private MessageDigest digest = MessageDigest.getInstance("SHA-256");
    protected TotalBlockchainNodeList totalBlockchainNodeList;
    private Network network;
    private int totalSupply;
    private int blockSubsidy;
    public Blockchain(int totalSupply, int blockSubsidy) throws NoSuchAlgorithmException {
        blockchain = new LinkedList<>();
        totalBlockchainNodeList = new TotalBlockchainNodeList();
        network = new Network(this, totalSupply, blockSubsidy);
    }

    public boolean addNewBlock(Block block) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if (block.getMinerAddress() == null) {
            System.out.println("Null miner address. Needs a miner address to send coinbase transaction to.");
            return false;
        }
        if (blockchain.isEmpty()) {
            if (block.isGenesis()) {
                blockchain.add(block);
                network.coinbaseTransaction(block.getMinerAddress());
                Transaction coinbaseTransaction = new Transaction(this, null, block.getMinerAddress(), 100, 0);
                totalBlockchainNodeList.getBlockchainNodeByAddress(block.getMinerAddress()).inputTransactionList.add(coinbaseTransaction);
                ArrayList<Transaction> transactions = new ArrayList<>();
                transactions.add(coinbaseTransaction);
                block.setTransactions(transactions);
                return true;
            } else {
                return false;
            }
        } else if (!(Arrays.equals(block.getMinerAddress(), null)) && (block.getBlockHeight() == getBlockchain().size()) && Arrays.equals(block.getPrevHash(), blockchain.getLast().getThisBlockHash())) {
            ArrayList<Transaction> newTransactionsList = new ArrayList<>();
            ArrayList<Integer> temporaryBlockchainNodeBalanceList = new ArrayList<>();
            for (int i = 0; i < totalBlockchainNodeList.BlockchainNodeList.size(); i++) {
                temporaryBlockchainNodeBalanceList.add(totalBlockchainNodeList.BlockchainNodeList.get(i).getBalance());
            }
            for (int i = 0; i < block.getTransactions().size(); i++) {
                Transaction transaction = block.getTransactions().get(i);
                // keep track of output of each address in system and whether they exceed sending what they own
                transaction.setBlockHeight(blockchain.size());
                temporaryBlockchainNodeBalanceList.set(totalBlockchainNodeList.BlockchainNodeList.indexOf(totalBlockchainNodeList.getBlockchainNodeByAddress(transaction.getFromAddress())), temporaryBlockchainNodeBalanceList.get(totalBlockchainNodeList.BlockchainNodeList.indexOf(totalBlockchainNodeList.getBlockchainNodeByAddress(transaction.getFromAddress()))) - -1 * transaction.getTransferAmount());
                if (temporaryBlockchainNodeBalanceList.get(totalBlockchainNodeList.BlockchainNodeList.indexOf(totalBlockchainNodeList.getBlockchainNodeByAddress(transaction.getFromAddress()))) > 0) {
                    totalBlockchainNodeList.getBlockchainNodeByAddress(transaction.getToAddress()).addTransactionToInputList(transaction);
                    totalBlockchainNodeList.getBlockchainNodeByAddress(transaction.getFromAddress()).addTransactionToOutputList(transaction);
                    totalBlockchainNodeList.getBlockchainNodeByAddress(transaction.getFromAddress()).transact(transaction.getFromUserName(), transaction.getToAddress(), transaction.getTransferAmount());
                    newTransactionsList.add(transaction);
                }
            }
            network.coinbaseTransaction(block.getMinerAddress());
            Transaction coinbaseTransaction = new Transaction(this, null, block.getMinerAddress(), 100, blockchain.size());
            totalBlockchainNodeList.getBlockchainNodeByAddress(block.getMinerAddress()).inputTransactionList.add(coinbaseTransaction);
            newTransactionsList.add(coinbaseTransaction);
            block.setTransactions(newTransactionsList);
            blockchain.add(block);
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
                    if (!(Arrays.equals(transaction.getFromAddress(), null))) {
                        System.out.print(Base64.getEncoder().encodeToString(transaction.getFromAddress()) + " sent " + transaction.getTransferAmount() + " coins to " + Base64.getEncoder().encodeToString(transaction.getToAddress()) + ". \n");
                    }
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
