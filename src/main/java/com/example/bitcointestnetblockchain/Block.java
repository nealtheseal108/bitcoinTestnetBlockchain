package com.example.bitcointestnetblockchain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Block {
    private Blockchain blockchain;
    private boolean isGenesis;
    private int blockHeight;
    private byte[] prevHash;
    private ArrayList<Transaction> transactions;
    private TransactionBinarySearchTree transactionSearchTree;
    private ArrayList<BlockchainNode> blockchainNodes;
    private BlockchainNodeBinarySearchTree blockchainNodeSearchTree;

    private byte[] minerAddress;
    private byte[] thisBlockHash;
    private MessageDigest digest = MessageDigest.getInstance("SHA-256");
    public Block(Blockchain blockchain, boolean isGenesis, byte[] prevHash, ArrayList<Transaction> transactions, byte[] minerAddress) throws NoSuchAlgorithmException {
        this.blockchain = blockchain;
        this.blockHeight = blockchain.getBlockchain().size();
        this.isGenesis = isGenesis;
        this.prevHash = prevHash;
        this.transactions = transactions;
        this.blockchainNodes = blockchainNodes;
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
                if (!(blockchainNodes.contains(new BlockchainNode(this.blockchain, transaction.getFromUserName())) || blockchainNodes.contains(new BlockchainNode(this.blockchain, digest.digest(transaction.getFromUserName().getBytes(StandardCharsets.UTF_16)))))) {
                    blockchainNodes.add(new BlockchainNode(this.blockchain, transaction.getFromUserName()));
                }
                boolean isToNodePresent = false;
                for (BlockchainNode currentNode: blockchainNodes) {
                    if (Arrays.equals(currentNode.getAddress(), transaction.getToAddress())) {
                        isToNodePresent = true;
                    }
                }
                if (!isToNodePresent) {
                    blockchainNodes.add(new BlockchainNode(this.blockchain, transaction.getToAddress()));
                }
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

        transactionSearchTree = new TransactionBinarySearchTree(transactions);

        blockchainNodeSearchTree = new BlockchainNodeBinarySearchTree(blockchainNodes);
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

    public TransactionBinarySearchTree getTransactionBinarySearchTree() {
        return transactionSearchTree;
    }

    public BlockchainNodeBinarySearchTree getBlockchainNodeBinarySearchTree() {
        return blockchainNodeSearchTree;
    }

    public Transaction getTransactionWithTree(Transaction transaction) {
        ArrayList<Transaction> arrayListOfTransactions = new ArrayList<Transaction>();
        for (Transaction currentTransaction: transactions) {
            arrayListOfTransactions.add(currentTransaction);
        }
        TransactionBinarySearchTree transactionBinarySearchTree = new TransactionBinarySearchTree(arrayListOfTransactions);
        return transactionBinarySearchTree.findTransactionInTree(transactionBinarySearchTree.rootNode, transaction);
    }

    public BlockchainNode getBlockchainNodeWithTree(BlockchainNode blockchainNode) {
        ArrayList<BlockchainNode> arrayListOfBlockchainNodes = new ArrayList<BlockchainNode>();
        for (BlockchainNode currentBlockchainNode: blockchainNodes) {
            arrayListOfBlockchainNodes.add(currentBlockchainNode);
        }
        BlockchainNodeBinarySearchTree blockchainNodeBinarySearchTree = new BlockchainNodeBinarySearchTree(arrayListOfBlockchainNodes);
        return blockchainNodeBinarySearchTree.findBlockchainNodeInTree(blockchainNodeBinarySearchTree.rootNode, blockchainNode);
    }
}
