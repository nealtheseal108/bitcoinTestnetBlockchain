package com.example.bitcointestnetblockchain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Node {
    private String username;
    private byte[] address;
    private boolean isMining;
    private int balance;
    public Node(String username, boolean isMining) throws NoSuchAlgorithmException {
        this.username = username;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        this.address = digest.digest(username.getBytes(StandardCharsets.UTF_8));
        this.isMining = isMining;
        this.balance = 0;
    }

    public boolean transact(byte[] toAddress, int sentBalance) {
        if (this.balance < balance) {
            return false;
        }

        for (Node )
    }

    public String getUsername() {
        return username;
    }

    public byte[] getAddress() {
        return address;
    }

    public boolean isMining() {
        return isMining;
    }

    public int getBalance() {
        return balance;
    }
}