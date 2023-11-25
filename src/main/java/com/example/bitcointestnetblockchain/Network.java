package com.example.bitcointestnetblockchain;

class Network {
    private int coins;
    private Blockchain blockchain;

    protected Network(Blockchain blockchain) {
        this.coins = 21000000;
    }

    protected void coinbaseTransaction(byte[] minerAddress) {
        blockchain.totalBlockchainNodeList.getBlockchainNodeByAddress(minerAddress).receiveFunds(100);
        coins -= 100;
    }
}