package com.example.bitcointestnetblockchain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Objects;

public class Block {
    private Blockchain blockchain;
    private boolean isGenesis;
    private int blockHeight;
    private byte[] prevHash;
    private ArrayList<Transaction> transactions;
    private BinarySearchTree transactionSearchTree;
    private byte[] minerAddress;
    private byte[] thisBlockHash;
    private MessageDigest digest = MessageDigest.getInstance("SHA-256");
    public Block(Blockchain blockchain, boolean isGenesis, byte[] prevHash, ArrayList<Transaction> transactions, byte[] minerAddress) throws NoSuchAlgorithmException {
        this.blockchain = blockchain;
        this.blockHeight = blockchain.getBlockchain().size();
        this.isGenesis = isGenesis;
        this.prevHash = prevHash;
        this.transactions = transactions;
        if (!Objects.equals(blockchain.totalBlockchainNodeList.getBlockchainNodeByAddress(minerAddress), null)) {
            this.minerAddress = minerAddress;
        } else {
            this.minerAddress = null;
        }
        byte[] blockHashPreHashConcat = new byte[2048];
        int iterator = 0;
        if (!(Objects.equals(transactions, null))) {
            for (int i = 0; i < transactions.size(); i++) {
                Transaction transaction = transactions.get(i);
                for (int j = 0; j < transaction.getTransactionHash().length; j++) {
                    byte b = transaction.getTransactionHash()[j];
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

    public boolean isGenesis() {
        return isGenesis;
    }

    public byte[] getPrevHash() {
        return this.prevHash;
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

    public int getBlockHeight() {
        return blockHeight;
    }

    protected void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    protected void removeTransaction(Transaction transaction) {
        this.transactions.remove(transaction);
    }

    public Transaction getTransactionByTransactionHashWithTreeInBlock() {

    }

    public Transaction getFirstTransactionFromSenderWithTreeInBlock() {

    }

    public Transaction getFirstTransactionToRecipientWithTreeInBlock() {

    }

    public Transaction getLatestTransactionFromSenderWithTreeInBlock() {

    }

    public Transaction getLatestTransactionToRecipientWithTreeInBlock() {

    }

    public BlockchainNode getSenderHistoryByTransactionHashWithTreeInBlock() {

    }

    public BlockchainNode getSenderHistoryByAddressWithTreeInBlockInBlock() {

    }

    public BlockchainNode getRecipientHistoryTransactionHashWithTreeInBlock() {

    }

    public BlockchainNode getRecipientHistoryByAddressWithTreeInBlock() {

    }

    public BlockchainNode getNodeByAddress
}
