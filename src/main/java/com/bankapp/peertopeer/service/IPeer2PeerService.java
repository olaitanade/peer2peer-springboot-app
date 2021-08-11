package com.bankapp.peertopeer.service;

import org.springframework.http.ResponseEntity;

public interface IPeer2PeerService {
    public abstract void initializeData();


    public abstract ResponseEntity<?> getAccountBalance(Credential credential);

    public abstract ResponseEntity<?> deposit (Deposit deposit);

    public abstract ResponseEntity<?> send (Withdraw withdraw);

    public abstract ResponseEntity<?> transfer(AccountCreation accountCreation);
}
