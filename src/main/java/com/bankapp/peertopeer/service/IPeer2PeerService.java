package com.bankapp.peertopeer.service;

import com.bankapp.peertopeer.model.request.*;
import org.springframework.http.ResponseEntity;

public interface IPeer2PeerService {
    public abstract void initializeData();


    public abstract ResponseEntity<?> getAccountBalance(Credential credential);

    public abstract ResponseEntity<?> deposit (Deposit deposit);

    public abstract ResponseEntity<?> send (Send send);

    public abstract ResponseEntity<?> transfer(Transfer transfer);

    public abstract ResponseEntity<?> createAccount(AccountCreation accountCreation);
}
