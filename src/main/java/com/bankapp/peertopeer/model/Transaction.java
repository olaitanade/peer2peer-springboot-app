package com.bankapp.peertopeer.model;

import com.bankapp.peertopeer.model.type.TransactionType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Transaction {
    private String transactionId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String bank;
    private String accountNumber;
    private String beneficiaryaccountNumber;
    private Date transactionDate;
    private TransactionType transactionType;
    private String narration;
    private Double amount;
    private Double accountBalance;

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBeneficiaryaccountNumber() {
        return beneficiaryaccountNumber;
    }

    public void setBeneficiaryaccountNumber(String beneficiaryaccountNumber) {
        this.beneficiaryaccountNumber = beneficiaryaccountNumber;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }
}
