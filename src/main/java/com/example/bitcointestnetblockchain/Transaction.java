package com.example.bitcointestnetblockchain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Transaction {
    private Blockchain blockchain;
    private String fromUserName;
    private int blockHeight;
    private byte[] fromAddress = null;
    private final byte[] toAddress;
    private final int transferAmount;

    private MessageDigest digest = MessageDigest.getInstance("SHA-256");
    private final byte[] transactionHash;
    public Transaction(Blockchain blockchain, String fromUserName, byte[] toAddress, int transferAmount, int blockHeight) throws NoSuchAlgorithmException {
        this.blockchain = blockchain;
        this.blockHeight = blockHeight;
        this.fromUserName = fromUserName;
        if (fromUserName != null) {
            this.fromAddress = digest.digest(fromUserName.getBytes(StandardCharsets.UTF_16));
        }
        this.toAddress = toAddress;
        this.transferAmount = transferAmount;
        byte[] transferAmountBytesArray = new byte[]{(byte) transferAmount};
        byte[] transactionHashPreHashConcat = new byte[2048];
        int iterator = 0;
        if (fromAddress != null) {
            for (int i = 0; i < fromAddress.length; i++) {
                transactionHashPreHashConcat[iterator] = fromAddress[i];
                iterator++;
            }
        }

        if (toAddress != null) {
            for (int i = 0; i < toAddress.length; i++) {
                transactionHashPreHashConcat[iterator] = toAddress[i];
                iterator++;
            }
        }

        if (transferAmount > 0) {
            for (int i = 0; i < transferAmountBytesArray.length; i++) {
                transactionHashPreHashConcat[iterator] = transferAmountBytesArray[i];
                iterator++;
            }
        }

        transactionHashPreHashConcat[iterator] = (byte) this.blockHeight;

        this.transactionHash = digest.digest(transactionHashPreHashConcat);
    }

    protected String getFromUserName() {
        return fromUserName;
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

    public int getBlockHeight() {
        return blockHeight;
    }

    public void printTransaction() {
        System.out.println("This transaction, " + Base64.getEncoder().encodeToString(getTransactionHash()) + ", proposes that " + getFromUserName() + ", or " + Base64.getEncoder().encodeToString(getFromAddress()) + " sends " + getTransferAmount() + " coins to " + Base64.getEncoder().encodeToString(getToAddress()) + ".");
    }

    protected void setBlockHeight(int blockHeight) {
        this.blockHeight = blockHeight;
    }
}
