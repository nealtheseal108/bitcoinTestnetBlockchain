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
    private final String username;
    private final byte[] address;
    private int balance;
    ArrayList<Transaction> inputTransactionList;
    private ArrayList<Transaction> outputTransactionList;
    private MessageDigest digest = MessageDigest.getInstance("SHA-256");

    public BlockchainNode(String username) throws NoSuchAlgorithmException {
        this.username = username;
        this.address = digest.digest(username.getBytes(StandardCharsets.UTF_16));
        this.balance = 0;
        inputTransactionList = new ArrayList<>();
        outputTransactionList = new ArrayList<>();
        addToTotalBlockchainNodeList();
    }

    protected boolean transact(String username, byte[] toAddress, int sentBalance) {
        if (this.balance < sentBalance || !(Arrays.equals(digest.digest(username.getBytes(StandardCharsets.UTF_16)), address)) || Objects.equals(TotalBlockchainNodeList.getBlockchainNodeByAddress(toAddress), null)) {
            return false;
        }

        TotalBlockchainNodeList.getBlockchainNodeByAddress(toAddress).receiveFunds(sentBalance);
        this.balance -= sentBalance;
        return true;

    }

    public boolean addToTotalBlockchainNodeList() {
        if (Objects.equals(TotalBlockchainNodeList.getBlockchainNodeByAddress(this.address), null)) {
            TotalBlockchainNodeList.addBlockchainNodeToList(this);
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
        if (!inputTransactionList.isEmpty()) {
            System.out.println("\t This node's input transactions");
            System.out.println("\t\tNon-coinbase transactions");
            for (int i = 0; i < inputTransactionList.size(); i++) {
                ArrayList<byte[]> coinbaseTransactions = new ArrayList<>();
                Transaction inputTransaction = inputTransactionList.get(i);
                if (Arrays.equals(inputTransaction.getFromAddress(), null)) {
                    coinbaseTransactions.add(inputTransaction.getTransactionHash());
                } else {
                    if (inputTransaction.getFromAddress() != null) {
                        System.out.println("\t\t\t" +  Base64.getEncoder().encodeToString(inputTransaction.getTransactionHash()) + ": " + Base64.getEncoder().encodeToString(inputTransaction.getFromAddress()) + " sent " + inputTransaction.getTransferAmount() + " coins to this node.");
                    }
                }
                if (inputTransactionList.get(inputTransactionList.size() - 1).equals(inputTransaction)) {
                    System.out.println("\t\tCoinbase transactions");
                }
                if (coinbaseTransactions.isEmpty()) {
                    System.out.println("None.");
                } else {
                    for (byte[] coinbaseTransactionHash: coinbaseTransactions) {
                        System.out.print("\t\t\t");
                        System.out.println(coinbaseTransactionHash + ": the network rewarded this node 100 coins for mining block " + Blockchain.getBlockByCoinbaseTransactionHash(coinbaseTransactionHash).getBlockHeight() + ".");
                    }
                }
            }
        }
        if (!outputTransactionList.isEmpty()) {
            System.out.print("\t\t This node's output transactions include ");
            for (int i = 0; i < outputTransactionList.size(); i++) {
                Transaction outputTransaction = outputTransactionList.get(i);
                if (outputTransactionList.get(outputTransactionList.size() - 1).equals(outputTransaction)) {
                    System.out.print(" and ");
                }
                System.out.print("This address sending " + outputTransaction.getTransferAmount() + " to " + Base64.getEncoder().encodeToString(outputTransaction.getToAddress()) + ", ");
            }
            System.out.println(".");
        }

    }
}
