module com.example.bitcointestnetblockchain {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.bitcointestnetblockchain to javafx.fxml;
    exports com.example.bitcointestnetblockchain;
    exports com.example.bitcointestnetblockchainuserinterface;
    opens com.example.bitcointestnetblockchainuserinterface to javafx.fxml;
}