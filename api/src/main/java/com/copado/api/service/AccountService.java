package com.copado.api.service;

import com.copado.api.db.InMemoryAccounts;
import com.copado.api.model.Account;
import com.copado.api.model.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    private static InMemoryAccounts DB;


    // CONSTRUCTOR

    @Autowired
    public AccountService(InMemoryAccounts database) {
        DB = database;
    }


    // PUBLIC

    public void addAccount(String taskId, Account account) {
        DB.addAccount(taskId, account);
    }


    public void clear(String taskId) { 
        DB.clear(taskId); 
    }


    public Response process(String taskId) throws JsonProcessingException {
        List<Account> result = new ArrayList<>();

        List<Account> accounts = DB.hasAccount(taskId) ? DB.getAccounts(taskId) : new ArrayList<>();
        boolean hasError = false;

        for(Account account : accounts) {
            hasError = (hasError || account.hasError());
            result.add(account.process());
        }

        String status = (hasError) ? "error" : "success";

        return new Response(status, result);
    }
}
