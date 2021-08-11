package com.bankapp.peertopeer.controller;

import com.bankapp.peertopeer.model.request.*;
import com.bankapp.peertopeer.service.IPeer2PeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/peer2peer")
@Validated
public class Peer2PeerRestController {
    private IPeer2PeerService peer2PeerService;

    @Autowired
    public Peer2PeerRestController(IPeer2PeerService peer2PeerService){
        this.peer2PeerService = peer2PeerService;
    }

    @RequestMapping(value = "/account_balance", method = RequestMethod.POST)
    public ResponseEntity<?> accountBalance(@Valid @RequestBody Credential credential) {
        return peer2PeerService.getAccountBalance(credential);
    }

    @RequestMapping(value = "/deposit", method = RequestMethod.POST)
    public ResponseEntity<?> deposit(@Valid @RequestBody Deposit deposit) {
        return peer2PeerService.deposit(deposit);
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public ResponseEntity<?> send(@Valid @RequestBody Send send) {
        return peer2PeerService.send(send);
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public ResponseEntity<?> transfer(@Valid @RequestBody Transfer transfer) {
        return peer2PeerService.transfer(transfer);
    }

    @RequestMapping(value = "/create_account", method = RequestMethod.POST)
    public ResponseEntity<?> createAccount(@Valid @RequestBody AccountCreation accountCreation) {
        return peer2PeerService.createAccount(accountCreation);
    }
}
