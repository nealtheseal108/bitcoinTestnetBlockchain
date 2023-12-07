package com.example.bitcointestnetblockchain;

class Network {
    private int totalSupply;
    private int blockSubsidy;
    private Blockchain blockchain;

    protected Network(Blockchain blockchain, int blockSubsidy, int totalSupply) {
        this.blockchain = blockchain;
        this.blockSubsidy = blockSubsidy;
        this.totalSupply = totalSupply;
    }

    protected void coinbaseTransaction(byte[] minerAddress) {
        blockchain.totalBlockchainNodeList.getBlockchainNodeByAddress(minerAddress).receiveFunds(100);
        totalSupply -= 100;
    }
}