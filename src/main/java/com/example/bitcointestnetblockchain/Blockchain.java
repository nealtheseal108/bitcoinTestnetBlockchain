package com.example.bitcointestnetblockchain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;

public class Blockchain {
    public LinkedList<Block> blockchain;
    private TotalNodeList totalNodeList;
    private Network network;
    public Blockchain() {
        blockchain = new LinkedList<>();
        totalNodeList = new TotalNodeList();
        network = new Network();
    }

    public boolean addNewBlock(Block block) throws NoSuchAlgorithmException {
        if (blockchain.isEmpty()) {
            block.setPrevHashNull();
            if (!(Arrays.equals(block.getMinerAddress(), null))) {
                blockchain.add(block);
                network.coinbaseTransaction(block.getMinerAddress());
                totalNodeList.getNodeByAddress(block.getMinerAddress()).inputTransactionList.add(new Transaction(null, block.getMinerAddress(), 100));
                for (Transaction transaction: block.getTransactions()) {
                    totalNodeList.getNodeByAddress(transaction.getToAddress()).addTransactionToInputList(transaction);
                    totalNodeList.getNodeByAddress(transaction.getFromAddress()).addTransactionToOutputList(transaction);
                }
                return true;
            } else {
                System.out.println("Null miner address. Needs a miner address to send coinbase transaction to.");
            }
        } else if (Arrays.equals(block.getMinerAddress(), null) && (Arrays.equals(block.prevHash, blockchain.getLast().getPrevHash()))) {
            blockchain.add(block);
            network.coinbaseTransaction(block.getMinerAddress());
            totalNodeList.getNodeByAddress(block.getMinerAddress()).inputTransactionList.add(new Transaction(null, block.getMinerAddress(), 100));
            for (Transaction transaction: block.getTransactions()) {
                totalNodeList.getNodeByAddress(transaction.getToAddress()).addTransactionToInputList(transaction);
                totalNodeList.getNodeByAddress(transaction.getFromAddress()).addTransactionToOutputList(transaction);
            }
            return true;
        }

        return false;
    }

    public void printBlockchain() {
        for (Block block: blockchain) {
            System.out.println("Block " + blockchain.indexOf(block) + "\n");
            System.out.println("Previous block hash: " + block.getPrevHash() + ".");
            for (Transaction transaction: block.getTransactions()) {
                System.out.print(transaction.getFromAddress() + "sent " + transaction.getTransferAmount() + " to " + transaction.getToAddress() + "." + "The balance of " + transaction.getFromAddress() + " is now " + totalNodeList.getNodeByAddress(transaction.getFromAddress()).getBalance() + " and the balance of " + transaction.getToAddress() + " is now " + totalNodeList.getNodeByAddress(transaction.getToAddress()).getBalance() + ". \n");
                System.out.print(block.getMinerAddress() + " received 100 coins for their computational effort expended to create the block.\n");
            }
            System.out.println("\n\n");
        }
    }

    public void addNode(Blockchain.Node newNode) {
        totalNodeList.addNodeToList(newNode);
    }

    public static class TotalNodeList {
        private static ArrayList<Blockchain.Node> nodeList;
        private TotalNodeList() {
            nodeList = new ArrayList<>();
        }
        private boolean addNodeToList(Blockchain.Node newNode) {
            if ((Objects.equals(getNodeByNode(newNode), null))) {
                nodeList.add(newNode);
                return true;
            }

            return false;
        }

        public Node getNodeByNode(Blockchain.Node searchedNode) {
            int indexOfSearchedNode = nodeList.indexOf(searchedNode);
            if (indexOfSearchedNode == -1) {
                return null;
            }

            else {
                return nodeList.get(indexOfSearchedNode);
            }
        }

        public Node getNodeByAddress(byte[] address) {
            for (Blockchain.Node node: nodeList) {
                if (Arrays.equals(node.getAddress(), address)) {
                    return node;
                }
            }

            return null;
        }

    }

    public class Block {
        private byte[] prevHash;
        private ArrayList<Transaction> transactions;
        private byte[] minerAddress;
        private byte[] thisBlockHash;
        private MessageDigest digest = MessageDigest.getInstance("SHA-256");
        public Block(byte[] prevHash, ArrayList<Transaction> transactions, byte[] minerAddress) throws NoSuchAlgorithmException {
            this.prevHash = prevHash;
            this.transactions = transactions;
            if (Objects.equals(totalNodeList.getNodeByAddress(minerAddress), null)) {
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

    private class Network {
        private int coins;

        private Network() {
            coins = 21000000;
        }

        private void coinbaseTransaction(byte[] minerAddress) {
            totalNodeList.getNodeByAddress(minerAddress).receiveFunds(100);
            coins -= 100;
        }
    }

    public class Node {
        private final String username;
        private final byte[] address;
        private int balance;

        private ArrayList<Transaction> inputTransactionList;
        private ArrayList<Transaction> outputTransactionList;
        private MessageDigest digest = MessageDigest.getInstance("SHA-256");

        public Node(String username) throws NoSuchAlgorithmException {
            this.username = username;
            this.address = digest.digest(username.getBytes(StandardCharsets.UTF_8));
            this.balance = 0;
            inputTransactionList = new ArrayList<>();
            outputTransactionList = new ArrayList<>();
            addToTotalNodeList();
        }

        public boolean transact(String username, byte[] toAddress, int sentBalance) {
            if (this.balance < sentBalance || !(Arrays.equals(digest.digest(username.getBytes(StandardCharsets.UTF_8)), address)) || Objects.equals(totalNodeList.getNodeByAddress(toAddress), null)) {
                return false;
            }

            totalNodeList.getNodeByAddress(toAddress).receiveFunds(sentBalance);
            this.balance -= sentBalance;
            return true;

        }

        public boolean addToTotalNodeList() {
            if (Objects.equals(totalNodeList.getNodeByAddress(this.address), null)) {
                totalNodeList.addNodeToList(this);
                return true;
            }

            return false;
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
    }

    public class Transaction {
        private final byte[] fromAddress;
        private final byte[] toAddress;
        private final int transferAmount;

        private MessageDigest digest = MessageDigest.getInstance("SHA-256");
        private final byte[] transactionHash;
        private Transaction(byte[] fromAddress, byte[] toAddress, int transferAmount) throws NoSuchAlgorithmException {
            this.fromAddress = fromAddress;
            this.toAddress = toAddress;
            this.transferAmount = transferAmount;
            byte[] transferAmountBytesArray = new byte[]{(byte) transferAmount};
            byte[] transactionHashPreHashConcat = new byte[2048];
            int iterator = 0;
            for (byte address : fromAddress) {
                transactionHashPreHashConcat[iterator] = address;
                iterator++;
            }
            for (byte address : toAddress) {
                transactionHashPreHashConcat[iterator] = address;
                iterator++;
            }
            for (byte amount : transferAmountBytesArray) {
                transactionHashPreHashConcat[iterator] = amount;
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
    }
}
