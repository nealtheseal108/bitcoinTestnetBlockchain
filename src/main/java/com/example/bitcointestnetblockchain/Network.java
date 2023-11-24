package com.example.bitcointestnetblockchain;

class Network {
    private int coins;

    protected Network() {
        coins = 21000000;
    }

    protected void coinbaseTransaction(byte[] minerAddress) {
        TotalBlockchainNodeList.getBlockchainNodeByAddress(minerAddress).receiveFunds(100);
        coins -= 100;
    }
}