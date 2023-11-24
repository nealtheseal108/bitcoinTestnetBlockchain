package com.example.bitcointestnetblockchain;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
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

    public boolean transact(String username, byte[] toAddress, int sentBalance) {
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

    public void printBlockchainNode() throws UnsupportedEncodingException {
        System.out.println("'" + getUsername() + "' or '" + new String(getAddress(), StandardCharsets.US_ASCII) + "', has a balance of " + getBalance() + ".");
        if (!inputTransactionList.isEmpty()) {
            System.out.print("Its input transactions include ");
            for (Transaction inputTransaction: inputTransactionList) {
                if (inputTransactionList.get(inputTransactionList.size() - 1).equals(inputTransaction)) {
                    System.out.print(" and ");
                }
                System.out.print(new String(inputTransaction.getFromAddress(), StandardCharsets.US_ASCII) + " sending " + inputTransaction.getTransferAmount() + " to this address, ");
            }
            System.out.println(".");
        }
        if (!outputTransactionList.isEmpty()) {
            System.out.print("Its output transactions include ");
            for (Transaction outputTransaction: outputTransactionList) {
                if (outputTransactionList.get(outputTransactionList.size() - 1).equals(outputTransaction)) {
                    System.out.print(" and ");
                }
                System.out.print("This address sending " + outputTransaction.getTransferAmount() + " to " + new String(outputTransaction.getToAddress(), StandardCharsets.US_ASCII) + ", ");
            }
            System.out.println(".");
        }

    }
}
