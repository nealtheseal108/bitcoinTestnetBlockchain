package com.example.bitcointestnetblockchainuserinterface;

import com.example.bitcointestnetblockchain.Blockchain;

import java.security.NoSuchAlgorithmException;

public class BlockchainInterface {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        Blockchain blockchain = new Blockchain();
        blockchain.addNode(new Blockchain.Node("Shrihun"));
    }
}

