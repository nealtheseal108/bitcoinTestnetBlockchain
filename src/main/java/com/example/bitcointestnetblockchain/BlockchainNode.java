package com.example.bitcointestnetblockchain;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

public class BlockchainNode {
    private Blockchain blockchain;
    private final String username;
    private byte[] address;
    private int balance;
    ArrayList<Transaction> inputTransactionList;
    private ArrayList<Transaction> outputTransactionList;
    private MessageDigest digest = MessageDigest.getInstance("SHA-256");

    public BlockchainNode(Blockchain blockchain, String username) throws NoSuchAlgorithmException {
        this.blockchain = blockchain;
        this.username = username;
        if (username != null) {
            this.address = digest.digest(username.getBytes(StandardCharsets.UTF_16));
        } else {
            this.address = null;
        }
        this.balance = 0;
        inputTransactionList = new ArrayList<>();
        outputTransactionList = new ArrayList<>();
        addToTotalBlockchainNodeList();
    }

    public BlockchainNode(Blockchain blockchain, byte[] address) throws NoSuchAlgorithmException {
        this.blockchain = blockchain;
        this.username = null;
        this.address = address;
        this.balance = 0;
        inputTransactionList = new ArrayList<>();
        outputTransactionList = new ArrayList<>();
        addToTotalBlockchainNodeList();
    }

    protected boolean transact(String username, byte[] toAddress, int sentBalance) {
        if (Objects.equals(this.address, toAddress) || this.balance < sentBalance || !(Arrays.equals(digest.digest(username.getBytes(StandardCharsets.UTF_16)), address)) || Objects.equals(blockchain.totalBlockchainNodeList.getBlockchainNodeByAddress(toAddress), null)) {
            return false;
        }

        blockchain.totalBlockchainNodeList.getBlockchainNodeByAddress(toAddress).receiveFunds(sentBalance);
        this.balance -= sentBalance;
        return true;

    }

    public boolean addToTotalBlockchainNodeList() {
        if (Objects.equals(blockchain.totalBlockchainNodeList.getBlockchainNodeByAddress(this.address), null)) {
            blockchain.totalBlockchainNodeList.addBlockchainNodeToList(this);
            return true;
        }

        return false;
    }

    private String getUsername() {
        return username;
    }

    public byte[] getAddress() {
        return address;
    }

    public int getBalance() {
        return balance;
    }

    public void receiveFunds(int funds) {
        this.balance += funds;
    }

    public void addTransactionToInputList(Transaction transaction) {
        this.inputTransactionList.add(transaction);
    }

    public void addTransactionToOutputList(Transaction transaction) {
        this.outputTransactionList.add(transaction);
    }

    public void printBlockchainNode() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        System.out.println(Base64.getEncoder().encodeToString(getAddress()) + " has a balance of " + getBalance() + " coins.");
        System.out.println("\t This node's input transactions");
        System.out.println("\t\tNon-coinbase transactions");
        ArrayList<byte[]> coinbaseTransactions = new ArrayList<>();

        for (Transaction inputTransaction : inputTransactionList) {
            if (Arrays.equals(inputTransaction.getFromAddress(), null)) {
                coinbaseTransactions.add(inputTransaction.getTransactionHash());
            } else {
                if (inputTransaction.getFromAddress() != null) {
                    System.out.println("\t\t\t" + Base64.getEncoder().encodeToString(inputTransaction.getTransactionHash()) + ": " + Base64.getEncoder().encodeToString(inputTransaction.getFromAddress()) + " sent " + inputTransaction.getTransferAmount() + " coins to this node.");
                }
            }
        }
        if (coinbaseTransactions.size() == inputTransactionList.size()) {
            System.out.println("\t\t\tNone.");
        }
        System.out.println("\t\tCoinbase transactions");
        if (coinbaseTransactions.isEmpty()) {
            System.out.println("\t\t\tNone.");
        } else {
            for (byte[] coinbaseTransactionHash: coinbaseTransactions) {
                System.out.print("\t\t\t");
                System.out.println(Base64.getEncoder().encodeToString(coinbaseTransactionHash) + ": the network rewarded this node 100 coins for mining block " + blockchain.getBlockByCoinbaseTransactionHash(coinbaseTransactionHash).getBlockHeight() + ".");
            }
        }

        System.out.println("\t This node's output transactions");
        if (!outputTransactionList.isEmpty()) {
            for (Transaction transaction : outputTransactionList) {
                System.out.print("\t\t\t");
                Transaction outputTransaction = transaction;
                System.out.println(Base64.getEncoder().encodeToString(outputTransaction.getTransactionHash()) + ": this address sent " + outputTransaction.getTransferAmount() + " coins to " + Base64.getEncoder().encodeToString(outputTransaction.getToAddress()) + ".");
            }
        } else {
            System.out.println("\t\t\tNone.");
        }

        System.out.println();
        System.out.println();
        System.out.println();

    }
}
