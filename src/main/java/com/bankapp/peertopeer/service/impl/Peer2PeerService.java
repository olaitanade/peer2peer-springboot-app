package com.bankapp.peertopeer.service.impl;


import com.bankapp.peertopeer.model.Account;
import com.bankapp.peertopeer.model.Transaction;
import com.bankapp.peertopeer.model.request.*;
import com.bankapp.peertopeer.model.response.AccountInfo;
import com.bankapp.peertopeer.model.type.TransactionType;
import com.bankapp.peertopeer.repository.Peer2PeerApplicationRepo;
import com.bankapp.peertopeer.service.IPeer2PeerService;
import com.bankapp.peertopeer.service.util.PasswordUtil;
import com.bankapp.peertopeer.service.util.Peer2PeerUtil;
import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.bankapp.peertopeer.model.response.DefaultResponse;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class Peer2PeerService implements IPeer2PeerService {

    private Peer2PeerApplicationRepo peer2PeerRepo;

    private PasswordUtil passwordUtil;

    private Peer2PeerUtil peer2PeerUtil;

    @Autowired
    public Peer2PeerService(Peer2PeerApplicationRepo peer2PeerRepo, PasswordUtil passwordUtil, Peer2PeerUtil peer2PeerUtil){
        this.peer2PeerRepo = peer2PeerRepo;
        this.passwordUtil = passwordUtil;
        this.peer2PeerUtil = peer2PeerUtil;
    }

    @Override
    public void initializeData() {
        peer2PeerRepo.initialize();
    }

    @Override
    public ResponseEntity<?> getAccountBalance(@NotNull Credential credential) {
        Account account = peer2PeerRepo.getAccountByNumber(credential.getAccountNumber());
        DefaultResponse authStatus = peer2PeerUtil.authorize(account,passwordUtil.hashPassword(credential.getAccountPassword()));

        if(!authStatus.isSuccess()){
            return new ResponseEntity<>(authStatus, HttpStatus.resolve(authStatus.getResponseCode()));
        }else{
            AccountInfo accountInfo = new AccountInfo();
            accountInfo.setAccount(account);
            return new ResponseEntity<>(accountInfo, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> deposit(@NotNull Deposit deposit) {
        Account account = peer2PeerRepo.getAccountByNumber(deposit.getAccountNumber());
        DefaultResponse defaultResponse = peer2PeerUtil.authorize(account);

        if(!defaultResponse.isSuccess()){
            return new ResponseEntity<>(defaultResponse, HttpStatus.resolve(defaultResponse.getResponseCode()));
        }else{
            Transaction transaction = new Transaction();
            transaction.setTransactionType(TransactionType.DEPOSIT);
            transaction.setTransactionDate(new Date());
            transaction.setAccountBalance(account.getBalance()+deposit.getAmount());
            transaction.setAmount(deposit.getAmount());
            transaction.setAccountNumber(account.getAccountNumber());
            transaction.setNarration("Deposit");
            transaction.setTransactionId(UUID.randomUUID().toString());
            peer2PeerRepo.insertTransaction(transaction);

            account.setBalance(transaction.getAccountBalance());
            peer2PeerRepo.updateAccount(account);
            return new ResponseEntity<>(new DefaultResponse(), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> send(@NotNull Send send) {
        Account account = peer2PeerRepo.getAccountByNumber(send.getAccountNumber());
        Account beneficiaryAccount = peer2PeerRepo.getAccountByNumber(send.getBeneficiaryAccountNumber());
        DefaultResponse defaultResponse = peer2PeerUtil.authorize(account,passwordUtil.hashPassword(send.getAccountPassword()));

        if(!defaultResponse.isSuccess()){
            return new ResponseEntity<>(defaultResponse, HttpStatus.resolve(defaultResponse.getResponseCode()));
        }else{
            if(peer2PeerUtil.canSend(send,account)){
                Transaction transactionSent = new Transaction();
                transactionSent.setTransactionType(TransactionType.SEND);
                transactionSent.setTransactionDate(new Date());
                transactionSent.setAccountBalance(account.getBalance()-send.getSendAmount());
                transactionSent.setAmount(send.getSendAmount());
                transactionSent.setAccountNumber(account.getAccountNumber());
                transactionSent.setBeneficiaryaccountNumber(send.getBeneficiaryAccountNumber());
                transactionSent.setTransactionId(UUID.randomUUID().toString());
                transactionSent.setNarration("Send");
                peer2PeerRepo.insertTransaction(transactionSent);

                Transaction transactionReceived = new Transaction();
                transactionReceived.setTransactionType(TransactionType.RECEIVE);
                transactionReceived.setTransactionDate(new Date());
                transactionReceived.setAccountBalance(beneficiaryAccount.getBalance()+send.getSendAmount());
                transactionReceived.setAmount(send.getSendAmount());
                transactionReceived.setAccountNumber(beneficiaryAccount.getAccountNumber());
                transactionReceived.setBeneficiaryaccountNumber(send.getAccountNumber());
                transactionReceived.setTransactionId(UUID.randomUUID().toString());
                transactionReceived.setNarration("Receive");
                peer2PeerRepo.insertTransaction(transactionReceived);


                account.setBalance(transactionSent.getAccountBalance());
                peer2PeerRepo.updateAccount(account);

                beneficiaryAccount.addToBalance(send.getSendAmount());
                peer2PeerRepo.updateAccount(beneficiaryAccount);

                return new ResponseEntity<>(new DefaultResponse(), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new DefaultResponse(400,false,"You need at least 500 Naira to be left in you account after withdrawal"), HttpStatus.resolve(400));
            }
        }
    }

    @Override
    public ResponseEntity<?> transfer(@NotNull Transfer transfer) {
        Account account = peer2PeerRepo.getAccountByNumber(transfer.getAccountNumber());
        DefaultResponse defaultResponse = peer2PeerUtil.authorize(account,passwordUtil.hashPassword(transfer.getAccountPassword()));

        if(!defaultResponse.isSuccess()){
            return new ResponseEntity<>(defaultResponse, HttpStatus.resolve(defaultResponse.getResponseCode()));
        }else{
            if(peer2PeerUtil.canTransfer(transfer,account)){
                Transaction transactionSent = new Transaction();
                transactionSent.setTransactionType(TransactionType.SEND);
                transactionSent.setTransactionDate(new Date());
                transactionSent.setAccountBalance(account.getBalance()-transfer.getTransferAmount());
                transactionSent.setAmount(transfer.getTransferAmount());
                transactionSent.setAccountNumber(account.getAccountNumber());
                transactionSent.setBeneficiaryaccountNumber(transfer.getBeneficiaryAccountNumber());
                transactionSent.setTransactionId(UUID.randomUUID().toString());
                transactionSent.setNarration("Transfer");
                transactionSent.setBank(transfer.getBank());
                peer2PeerRepo.insertTransaction(transactionSent);


                account.setBalance(transactionSent.getAccountBalance());
                peer2PeerRepo.updateAccount(account);

                //transfer out

                return new ResponseEntity<>(new DefaultResponse(), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new DefaultResponse(400,false,"You need at least 500 Naira to be left in you account after withdrawal"), HttpStatus.resolve(400));
            }
        }
    }


    @Override
    public ResponseEntity<?> createAccount(@NotNull AccountCreation accountCreation) {
        Account newAccount = new Account();
        newAccount.setAccountName(accountCreation.getAccountName());
        newAccount.setAccountPassword(passwordUtil.hashPassword(accountCreation.getAccountPassword()));
        newAccount.setAccountNumber(peer2PeerUtil.generateAccountNumber());
        newAccount.setBalance(0.0);
        String statusMessage = peer2PeerRepo.insertAccount(newAccount);
        if(statusMessage!=null){
            return new ResponseEntity<>(new DefaultResponse(400,false,statusMessage), HttpStatus.resolve(400));
        }else{
            return new ResponseEntity<>(new DefaultResponse(String.format("Account successfully created with account number %s",newAccount.getAccountNumber())), HttpStatus.OK);
        }
    }
}
