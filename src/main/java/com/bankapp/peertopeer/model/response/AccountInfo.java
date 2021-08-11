package com.bankapp.peertopeer.model.response;


import com.bankapp.peertopeer.model.Account;

public class AccountInfo extends DefaultResponse{
    private Account account;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
