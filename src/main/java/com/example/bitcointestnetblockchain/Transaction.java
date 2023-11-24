package com.example.bitcointestnetblockchain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

class Transaction {
    private final byte[] fromAddress;
    private final byte[] toAddress;
    private final int transferAmount;

    private ArrayList<Transaction> inputTransactionList;
    private ArrayList<Transaction> outputTransactionList;
    private MessageDigest digest = MessageDigest.getInstance("SHA-256");
    private final byte[] transactionHash;
    Transaction(byte[] fromAddress, byte[] toAddress, int transferAmount) throws NoSuchAlgorithmException {
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.transferAmount = transferAmount;
        byte[] transferAmountBytesArray = new byte[]{(byte) transferAmount};
        byte[] transactionHashPreHashConcat = new byte[2048];
        int iterator = 0;
        for (int i = 0; i < fromAddress.length; i++) {
            transactionHashPreHashConcat[iterator] = fromAddress[i];
            iterator++;
        }
        for (int i = 0; i < toAddress.length; i++) {
            transactionHashPreHashConcat[iterator] = toAddress[i];
            iterator++;
        }
        for (int i = 0; i < transferAmountBytesArray.length; i++) {
            transactionHashPreHashConcat[iterator] = transferAmountBytesArray[i];
            iterator++;
        }
        this.transactionHash = digest.digest(transactionHashPreHashConcat);
    }

    public byte[] getFromAddress() {
        return fromAddress;
    }

    public byte[] getToAddress() {
        return toAddress;
    }

    public int getTransferAmount() {
        return transferAmount;
    }

    public byte[] getTransactionHash() {
        return transactionHash;
    }

    public void addTransactionToInputList(Transaction transaction) {
        this.inputTransactionList.add(transaction);
    }

    public void addTransactionToOutputList(Transaction transaction) {
        this.outputTransactionList.add(transaction);
    }
}
