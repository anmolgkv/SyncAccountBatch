package com.copado.api.service;

import com.copado.api.db.InMemoryAccounts;
import com.copado.api.model.Account;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class AccountServiceTest {

    private AccountService service = new AccountService(new InMemoryAccounts());

    @Test
    public void whenGoodAccount_thenSucceed() throws Exception {

        // Setup
        Exception expectedException = null;
        service.addAccount("taskId", new Account("id", "Good Account", "New"));


        // Exercise
        try {
            service.process("taskId");
        }
        catch(Exception ex) {
            expectedException = ex;
        }


        // Verify
        assertEquals(null, expectedException);
    }


    @Test
    public void whenFakeAccount_thenFail() throws Exception {

        // Setup
        Exception expectedException = null;
        service.addAccount("taskId", new Account("id", "Fake Account", "New"));


        // Exercise
        try {
            service.process("taskId");
        }
        catch(Exception ex) {
            expectedException = ex;
        }


        // Verify
        assertNotEquals(null, expectedException);
    }
}