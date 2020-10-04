package com.copado.api.service;

import com.copado.api.db.InMemoryAccounts;
import com.copado.api.model.Account;
import com.copado.api.model.Response;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class AccountServiceTest {

    private AccountService service = new AccountService(new InMemoryAccounts());

    @Test
    public void whenGoodAccount_thenSucceed() throws Exception {

        // Setup
        service.addAccount("taskId", new Account("id", "Good Account", "New"));


        // Exercise
        Response response = service.process("taskId");


        // Verify
        assertEquals("success", response.getStatus());
    }


    @Test
    public void whenFakeAccount_thenFail() throws Exception {

        // Setup
        service.addAccount("taskId", new Account("id", "Fake Account", "New"));


        // Exercise
        Response response = service.process("taskId");


        // Verify
        assertEquals("error", response.getStatus());
    }
}