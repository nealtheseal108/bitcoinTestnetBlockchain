package com.example.bitcointestnetblockchain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Objects;

public class Block {
    private byte[] prevHash;
    private ArrayList<Transaction> transactions;
    private byte[] minerAddress;
    private byte[] thisBlockHash;
    private MessageDigest digest = MessageDigest.getInstance("SHA-256");
    public Block(byte[] prevHash, ArrayList<Transaction> transactions, byte[] minerAddress) throws NoSuchAlgorithmException {
        this.prevHash = prevHash;
        this.transactions = transactions;
        if (!Objects.equals(TotalBlockchainNodeList.getBlockchainNodeByAddress(minerAddress), null)) {
            this.minerAddress = minerAddress;
        } else {
            this.minerAddress = null;
        }
        byte[] blockHashPreHashConcat = new byte[2048];
        int iterator = 0;
        if (!(Objects.equals(transactions, null))) {
            for (Transaction transaction: transactions) {
                for (byte b: transaction.getTransactionHash()) {
                    blockHashPreHashConcat[iterator] = b;
                    iterator++;
                }
            }
        }

        for (int i = 0; i < minerAddress.length; i++) {
            blockHashPreHashConcat[iterator] = minerAddress[i];
            iterator++;
        }
        this.thisBlockHash = digest.digest(blockHashPreHashConcat);
    }

    public byte[] getPrevHash() {
        return this.prevHash;
    }

    public void setPrevHashNull() {
        this.prevHash = null;
    }

    public ArrayList<Transaction> getTransactions() {
        return this.transactions;
    }

    public byte[] getMinerAddress() {
        return this.minerAddress;
    }

    public byte[] getThisBlockHash() {
        return this.thisBlockHash;
    }
}
