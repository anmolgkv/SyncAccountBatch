package com.copado.api.service;

import com.copado.api.db.InMemoryAccounts;
import com.copado.api.model.Account;
import com.copado.api.model.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    private static InMemoryAccounts DB;

    @Autowired
    public AccountService(InMemoryAccounts DB) {
        this.DB = DB;
    }


    public void addAccount(String taskId, Account account) {
        DB.addAccount(taskId, account);
    }

    public void clear(String taskId) {
        DB.clear(taskId);
    }

    public Response process(String taskId) throws JsonProcessingException {
        List<Account> accounts = DB.hasAccount(taskId) ? DB.getAccounts(taskId) : new ArrayList<>();

        boolean hasError = false;
        List<Account> processedAccounts = new ArrayList<>();

        for(Account account : accounts) {
            hasError = (hasError || account.hasError());
            processedAccounts.add(account.process());
        }

        String status = (hasError) ? "error" : "success";
        String message = new ObjectMapper().writeValueAsString(processedAccounts);

        return new Response(status, message);
    }
}
