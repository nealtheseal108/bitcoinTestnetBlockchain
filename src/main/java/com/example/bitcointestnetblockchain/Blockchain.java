package com.example.bitcointestnetblockchain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Blockchain {
    static LinkedList<Block> blockchain;
    static TotalNodeList totalNodeList;
    public Blockchain() {
        blockchain = new LinkedList<>();
        totalNodeList = new TotalNodeList();
    }

    public boolean addNewBlock

    class TotalNodeList {
        private static ArrayList<Node> nodeList;
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
        private byte[] prevHash;
        private ArrayList<Transaction> transactions;
    }

    class Node {
        private String username;
        private byte[] address;
        private boolean isMining;
        private int balance;

        public ArrayList<Transaction> transactions;
        private MessageDigest digest = MessageDigest.getInstance("SHA-256");

        public Node(String username, boolean isMining) throws NoSuchAlgorithmException {
            this.username = username;
            this.address = digest.digest(username.getBytes(StandardCharsets.UTF_8));
            this.isMining = isMining;
            this.balance = 0;
            ArrayList<Transaction>
            addToTotalNodeList();
        }

        public boolean transact(String username, byte[] toAddress, int sentBalance) {
            if (this.balance < balance || digest.digest(username.getBytes(StandardCharsets.UTF_8)) != address || totalNodeList.getNodeByAddress(toAddress) == null) {
                return false;
            }


            totalNodeList.getNodeByAddress(toAddress).receiveFunds(sentBalance);
            this.balance -= sentBalance;
            return true;

        }

        private boolean addToTotalNodeList() {
            if (totalNodeList.getNodeByAddress(this.address) == null) {
                totalNodeList.addNodeToList(this);
                return true;
            }

            return false;
        }

        private byte[] getAddress() {
            return address;
        }

        private boolean isMining() {
            return isMining;
        }

        private int getBalance() {
            return balance;
        }

        private void receiveFunds(int funds) {
            this.balance += funds;
        }
    }

    class Transaction {
        private byte[] fromAddress;
        private byte[] toAddress;
        private byte[] minerAddress;
        private int transferAmount;
        private boolean isCoinbaseTransaction;

        private MessageDigest digest = MessageDigest.getInstance("SHA-256");
        private byte[] transactionHash;
        protected Transaction(byte[] fromAddress, byte[] toAddress, byte[] minerAddress, int transferAmount, boolean isCoinbaseTransaction) throws NoSuchAlgorithmException {
            this.fromAddress = fromAddress;
            this.toAddress = toAddress;
            this.minerAddress = minerAddress;
            this.transferAmount = transferAmount;
            byte[] transferAmountBytesArray = new byte[]{(byte) transferAmount};
            this.isCoinbaseTransaction = isCoinbaseTransaction;
            byte[] isCoinbaseTransactionBytesArray = new byte[]{true ? 1 : 0};
            byte[] transactionHashPreHashConcat = new byte[2048];
            int iterator = 0;
            for (int i = 0; iterator < fromAddress.length; iterator++) {
                transactionHashPreHashConcat[i] = transactionHashPreHashConcat[iterator];
                iterator++;
            }
            for (int i = 0; i < toAddress.length; i++) {
                transactionHashPreHashConcat[i] = transactionHashPreHashConcat[iterator];
            }
            this.transactionHash = digest.digest(new byte[fromAddress + toAddress + minerAddress + transferAmountBytesArray + isCoinbaseTransactionBytesArray]);
        }

        public byte[] getFromAddress() {
            return fromAddress;
        }

        public byte[] getToAddress() {
            return toAddress;
        }

        public byte[] getMinerAddress() {
            return minerAddress;
        }

        public int getTransferAmount() {
            return transferAmount;
        }

        public boolean isCoinbaseTransaction() {
            return isCoinbaseTransaction;
        }

        public byte[] getTransactionHash() {
            return transactionHash;
        }
    }

}
