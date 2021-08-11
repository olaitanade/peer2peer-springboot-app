package com.bankapp.peertopeer.service.util;


import com.bankapp.peertopeer.model.Account;
import com.sun.istack.internal.NotNull;
import org.springframework.stereotype.Service;

@Service
public class Peer2PeerUtil {
    private static final long LIMIT = 10000000000L;
    private static long last = 0;

    public DefaultResponse authorize(@NotNull Account account, String password){
        if(account==null){
            return new DefaultResponse(400,false,"Account does not exist");
        }else if(account!=null && !account.getAccountPassword().equals(password)){
            return new DefaultResponse(401,false,"Account password incorrect");
        }else {
            return new DefaultResponse();
        }
    }

    public DefaultResponse authorize(@NotNull Account account){
        if(account==null){
            return new DefaultResponse(400,false,"Account does not exist");
        }else {
            return new DefaultResponse();
        }
    }

    public boolean canWithDraw(Withdraw withdraw, Account account){
        if(withdraw.getWithdrawnAmount()<account.getBalance()&&(account.getBalance()-withdraw.getWithdrawnAmount())>500.0){
            return true;
        }else{
            return false;
        }
    }

    public String generateAccountNumber(){
        long id = System.currentTimeMillis() % LIMIT;
        if ( id <= last ) {
            id = (last + 1) % LIMIT;
        }
        return String.valueOf(id);
    }
}
