package com.example.bitcointestnetblockchain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Blockchain {
    public static LinkedList<Block> blockchain;
    public static TotalNodeList totalNodeList;
    public Blockchain() {
        blockchain = new LinkedList<>();
        totalNodeList = new TotalNodeList();
    }

    public boolean addNewBlock

    protected class TotalNodeList {
        protected static ArrayList<Node> nodeList;
        public TotalNodeList() {
            nodeList = new ArrayList<>();
        }
        public boolean addNodeToList(Node newNode) {
            if (getNodeByNode(newNode) != null) {
                nodeList.add(newNode);
                return true;
            }

            return false;
        }

        public Node getNodeByNode(Node searchedNode) {
            int indexOfSearchedNode = nodeList.indexOf(searchedNode);
            if (indexOfSearchedNode == -1) {
                return null;
            }

            else {
                return nodeList.get(indexOfSearchedNode);
            }
        }

        public Node getNodeByAddress(byte[] address) {
            for (Node node: nodeList) {
                if (node.getAddress() == address) {
                    return node;
                }
            }

            return null;
        }

    }

    class Block {
        protected byte[] prevHash;
        protected ArrayList<Transaction> transactions;
        protected byte[] minerAddress;
        protected byte[] thisBlockHash;
        protected MessageDigest digest = MessageDigest.getInstance("SHA-256");
        public Block(byte[] prevHash, ArrayList<Transaction> transactions, byte[] minerAddress) throws NoSuchAlgorithmException {
            this.prevHash = prevHash;
            this.transactions = transactions;
            this.minerAddress = minerAddress;
            byte[] blockHashPreHashConcat = new byte[2048];
            int iterator = 0;
            for (Transaction transaction: transactions) {
                for (byte b: transaction.getTransactionHash()) {
                    blockHashPreHashConcat[iterator] = b;
                    iterator++;
                }
            }


            for (byte b: this.minerAddress) {
                blockHashPreHashConcat[iterator] = b;
                iterator++;
            }
            this.thisBlockHash = digest.digest(blockHashPreHashConcat);
        }
    }

    class Node {
        protected final String username;
        protected final byte[] address;
        protected final boolean isMining;
        protected int balance;

        public ArrayList<Transaction> inputTransactionList;
        public ArrayList<Transaction> outputTransactionList;
        protected MessageDigest digest = MessageDigest.getInstance("SHA-256");

        protected Node(String username, boolean isMining) throws NoSuchAlgorithmException {
            this.username = username;
            this.address = digest.digest(username.getBytes(StandardCharsets.UTF_8));
            this.isMining = isMining;
            this.balance = 0;
            inputTransactionList = new ArrayList<>();
            outputTransactionList = new ArrayList<>();
            addToTotalNodeList();
        }

        public boolean transact(String username, byte[] toAddress, int sentBalance) {
            if (this.balance < sentBalance || digest.digest(username.getBytes(StandardCharsets.UTF_8)) != address || totalNodeList.getNodeByAddress(toAddress) == null) {
                return false;
            }

            totalNodeList.getNodeByAddress(toAddress).receiveFunds(sentBalance);
            this.balance -= sentBalance;
            return true;

        }

        protected boolean addToTotalNodeList() {
            if (totalNodeList.getNodeByAddress(this.address) == null) {
                totalNodeList.addNodeToList(this);
                return true;
            }

            return false;
        }

        protected byte[] getAddress() {
            return address;
        }

        protected boolean isMining() {
            return isMining;
        }

        protected int getBalance() {
            return balance;
        }

        protected void receiveFunds(int funds) {
            this.balance += funds;
        }
    }

    class Transaction {
        protected final byte[] fromAddress;
        protected final byte[] toAddress;
        protected final byte[] minerAddress;
        protected final int transferAmount;
        protected final boolean isCoinbaseTransaction;

        protected MessageDigest digest = MessageDigest.getInstance("SHA-256");
        protected final byte[] transactionHash;
        protected Transaction(byte[] fromAddress, byte[] toAddress, byte[] minerAddress, int transferAmount, boolean isCoinbaseTransaction) throws NoSuchAlgorithmException {
            this.fromAddress = fromAddress;
            this.toAddress = toAddress;
            this.minerAddress = minerAddress;
            this.transferAmount = transferAmount;
            byte[] transferAmountBytesArray = new byte[]{(byte) transferAmount};
            this.isCoinbaseTransaction = isCoinbaseTransaction;
            byte[] isCoinbaseTransactionBytesArray = new byte[]{(byte) (this.isCoinbaseTransaction ? 1 : 0)};
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
            for (byte address : minerAddress) {
                transactionHashPreHashConcat[iterator] = address;
                iterator++;
            }
            for (byte amount : transferAmountBytesArray) {
                transactionHashPreHashConcat[iterator] = amount;
                iterator++;
            }
            for (byte b : isCoinbaseTransactionBytesArray) {
                transactionHashPreHashConcat[iterator] = b;
                iterator++;
            }
            this.transactionHash = digest.digest(transactionHashPreHashConcat);
        }

        protected byte[] getFromAddress() {
            return fromAddress;
        }

        protected byte[] getToAddress() {
            return toAddress;
        }

        protected byte[] getMinerAddress() {
            return minerAddress;
        }

        protected int getTransferAmount() {
            return transferAmount;
        }

        protected boolean isCoinbaseTransaction() {
            return isCoinbaseTransaction;
        }

        protected byte[] getTransactionHash() {
            return transactionHash;
        }
    }
}
