package com.bankapp.peertopeer.repository;

import com.bankapp.peertopeer.model.Account;
import com.bankapp.peertopeer.model.Transaction;
import com.bankapp.peertopeer.model.type.TransactionType;
import com.bankapp.peertopeer.service.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class Peer2PeerApplicationRepo {
    private List<Account> accounts = new ArrayList<Account>();
    private List<Transaction> transactions = new ArrayList<Transaction>();

    private PasswordUtil passwordUtil;

    @Autowired
    public Peer2PeerApplicationRepo(PasswordUtil passwordUtil){
        this.passwordUtil = passwordUtil;
    }

    public void initialize(){
        Account userA = new Account();
        userA.setAccountName("John Elon");
        userA.setAccountNumber("1234567893");
        userA.setAccountPassword(passwordUtil.hashPassword("123456"));
        userA.setBalance(10.0);
        accounts.add(userA);

        Account userB = new Account();
        userB.setAccountName("Mercy Johnson");
        userB.setAccountNumber("1231345654");
        userB.setAccountPassword(passwordUtil.hashPassword("uiopty"));
        userB.setBalance(20.0);
        accounts.add(userB);

        Transaction transaction = new Transaction();
        transaction.setTransactionDate(new Date());
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setNarration("Savings");
        transaction.setAmount(10.0);
        transaction.setAccountNumber(userA.getAccountNumber());
        transaction.setAccountBalance(10.0);
        transaction.setTransactionId(UUID.randomUUID().toString());
        transactions.add(transaction);

        Transaction transaction1 = new Transaction();
        transaction1.setTransactionDate(new Date());
        transaction1.setTransactionType(TransactionType.DEPOSIT);
        transaction1.setNarration("Savings");
        transaction1.setAmount(20.0);
        transaction1.setAccountNumber(userB.getAccountNumber());
        transaction1.setAccountBalance(20.0);
        transaction1.setTransactionId(UUID.randomUUID().toString());
        transactions.add(transaction1);
    }

    public List<Account> getAccounts(){
        return accounts;
    }

    public List<Transaction> getTransactions(){
        return transactions;
    }

    public Account getAccountByNumber(String accountNumber){
        return accounts.stream().filter(x -> x.getAccountNumber().equals(accountNumber)).findAny().orElse(null);
    }

    public List<Transaction> getTransactionByNumber(String accountNumber){
        return transactions.stream().filter(x -> x.getAccountNumber().equals(accountNumber)).collect(Collectors.toList());
    }

    public boolean insertTransaction(Transaction transaction) {
        transactions.add(transaction);
        return true;
    }

    public boolean updateAccount(Account account) {
        int index = 0;
        boolean found = false;
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getAccountNumber().equals(account.getAccountNumber())) {
                index = i;
                found = true;
                break;
            }
        }
        accounts.set(index,account);
        return found;
    }

    public String insertAccount(Account account){
        for (int i = 0; i < accounts.size(); i++) {
            Account tempAccount = accounts.get(i);
            if (tempAccount.getAccountNumber().equals(account.getAccountNumber())) {
                return "Account Number already exist";
            }else if(tempAccount.getAccountName().equals(account.getAccountName())){
                return "Account Name already exist";
            }
        }
        accounts.add(account);
        return null;
    }
}
