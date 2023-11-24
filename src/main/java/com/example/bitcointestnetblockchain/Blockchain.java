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

    public boolean addNewBlock(Block block) {
        if (blockchain == null) {
            block.setPrevHashNull();
            if (block.getMinerAddress() != null) {
                blockchain.add(block);

                return true;
            } else {
                System.out.println("Null miner address. Needs a miner address to send coinbase transaction to.");
            }
        } else if (block.prevHash == blockchain.getLast().getPrevHash()) {
            blockchain.add(block);
            Transaction coinbaseTransaction = new Transaction(null, )
            return true;
        }

        return false;
    }

    public void printBlockchain() {
        for (Block block: blockchain) {
            System.out.println("Block " + blockchain.indexOf(block) + "\n");
            System.out.println("Previous block hash: " + block.getPrevHash() + ".");
            for (Transaction transaction: block.getTransactions()) {
                System.out.print(transaction.getFromAddress() + "sent " + transaction.getTransferAmount() + "" ". \n");
            }
        }
    }

    static private class TotalNodeList {
        private static ArrayList<Node> nodeList;
        private TotalNodeList() {
            nodeList = new ArrayList<>();
        }
        private boolean addNodeToList(Blockchain.Node newNode) {
            if (getNodeByNode(newNode) != null) {
                nodeList.add(newNode);
                return true;
            }

            return false;
        }

        private Node getNodeByNode(Node searchedNode) {
            int indexOfSearchedNode = nodeList.indexOf(searchedNode);
            if (indexOfSearchedNode == -1) {
                return null;
            }

            else {
                return nodeList.get(indexOfSearchedNode);
            }
        }

        private Node getNodeByAddress(byte[] address) {
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
        private byte[] minerAddress;
        private byte[] thisBlockHash;
        private MessageDigest digest = MessageDigest.getInstance("SHA-256");
        public Block(byte[] prevHash, ArrayList<Transaction> transactions, byte[] minerAddress) throws NoSuchAlgorithmException {
            this.prevHash = prevHash;
            this.transactions = transactions;
            if (totalNodeList.getNodeByAddress(minerAddress) != null) {
                this.minerAddress = minerAddress;
            } else {
                this.minerAddress = null;
            }
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

        public byte[] getPrevHash() {
            return prevHash;
        }

        public void setPrevHashNull() {
            this.prevHash = null;
        }

        public ArrayList<Transaction> getTransactions() {
            return transactions;
        }

        public byte[] getMinerAddress() {
            return minerAddress;
        }

        public byte[] getThisBlockHash() {
            return thisBlockHash;
        }
    }

    private static class Network {
        private static int coins;

        private Network() {
            coins = 21000000;
        }

        private static void coinbaseTransaction(byte[] minerAddress) {
            totalNodeList.getNodeByAddress(minerAddress).receiveFunds(100);
            coins -= 100;
        }
    }

    private class Node {
        private final String username;
        private final byte[] address;
        private final boolean isMining;
        private int balance;

        public ArrayList<Transaction> inputTransactionList;
        public ArrayList<Transaction> outputTransactionList;
        private MessageDigest digest = MessageDigest.getInstance("SHA-256");

        private Node(String username, boolean isMining) throws NoSuchAlgorithmException {
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

    private class Transaction {
        private final byte[] fromAddress;
        private final byte[] toAddress;
        private final byte[] minerAddress;
        private final int transferAmount;
        private final boolean isCoinbaseTransaction;

        private MessageDigest digest = MessageDigest.getInstance("SHA-256");
        private final byte[] transactionHash;
        private Transaction(byte[] fromAddress, byte[] toAddress, byte[] minerAddress, int transferAmount, boolean isCoinbaseTransaction) throws NoSuchAlgorithmException {
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

        private byte[] getFromAddress() {
            return fromAddress;
        }

        private byte[] getToAddress() {
            return toAddress;
        }

        private byte[] getMinerAddress() {
            return minerAddress;
        }

        private int getTransferAmount() {
            return transferAmount;
        }

        private boolean isCoinbaseTransaction() {
            return isCoinbaseTransaction;
        }

        private byte[] getTransactionHash() {
            return transactionHash;
        }
    }
}
