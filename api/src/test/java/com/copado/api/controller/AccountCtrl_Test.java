package com.copado.api.controller;

import com.copado.api.db.InMemoryAccounts;
import com.copado.api.model.Account;
import com.copado.api.model.Request;
import com.copado.api.model.Response;
import com.copado.api.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.copado.api.controller.helper.Random.string;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(BlockJUnit4ClassRunner.class)
public class AccountCtrl_Test {

    @Test
    public void whenNoAccount_thenFail() throws Exception {

        // Given
        Exception expectedException = null;
        AccountCtrl controller = new AccountCtrl(new AccountService(new InMemoryAccounts()));



        // When
        try {
            controller.addPerson(new Request(string(18), string(18), null), true);
        }
        catch(Exception ex) {
            expectedException = ex;
        }


        // Then
        assertNotEquals(null, expectedException);
        assertEquals("Please provide accounts", expectedException.getMessage());
    }


    @Test
    public void whenValidInput_thenReturnsSuccess() throws Exception {

        // Given
        AccountCtrl controller = new AccountCtrl(new AccountService(new InMemoryAccounts()));
        String orgId = string(18);
        String userId = string(18);

        controller.addPerson(new Request(orgId, userId, accounts(1)), true);

        // When
        Response response = controller.process(new Request(orgId, userId));


        // Then
        assertEquals("success", response.getStatus());
    }


    @Test
    public void whenFakeAccount_thenReturnError() throws Exception {

        // Given
        AccountCtrl controller = new AccountCtrl(new AccountService(new InMemoryAccounts()));
        String orgId = string(18);
        String userId = string(18);

        controller.addPerson(new Request(orgId, userId, accounts(1, true)), true);

        // When
        Response response = controller.process(new Request(orgId, userId));


        // Then
        assertEquals("error", response.getStatus());
    }


    @Test
    public void whenMixedAccounts_thenReturnError() throws Exception {

        // Given
        AccountCtrl controller = new AccountCtrl(new AccountService(new InMemoryAccounts()));
        String orgId = string(18);
        String userId = string(18);
        List<Account> accounts = new ArrayList<>();
        accounts.addAll(accounts(1, true));
        accounts.addAll(accounts(1, false));

        controller.addPerson(new Request(orgId, userId, accounts), true);


        // When
        Response response = controller.process(new Request(orgId, userId));


        // Then
        assertEquals("error", response.getStatus());
        assertEquals(2, response.getAccounts().size());
        assertEquals("Fake account", response.getAccounts().get(0).message);
        assertEquals("Looks good", response.getAccounts().get(1).message);
    }


    @Test
    public void whenInitTrue_thenUpdate() throws Exception {

        // Given
        AccountCtrl controller = new AccountCtrl(new AccountService(new InMemoryAccounts()));
        String orgId = string(18);
        String userId = string(18);

        Account account = accounts(1, true).get(0);
        controller.addPerson(new Request(orgId, userId, Arrays.asList(account)), true);
        account.setName("Sample Account");
        controller.addPerson(new Request(orgId, userId, Arrays.asList(account)), true);


        // When
        Response response = controller.process(new Request(orgId, userId));


        // Then
        assertEquals("success", response.getStatus());
    }


    @Test
    public void whenInitIsTrue_thenClearOldAccounts() throws Exception {

        // Given
        AccountCtrl controller = new AccountCtrl(new AccountService(new InMemoryAccounts()));
        String orgId = string(18);
        String userId = string(18);

        controller.addPerson(new Request(orgId, userId, accounts(10)), true);
        controller.addPerson(new Request(orgId, userId, accounts(15)), true);

        
        // When
        Response response = controller.process(new Request(orgId, userId));


        // Then
        assertEquals("success", response.getStatus());
        assertEquals(15, response.getAccounts().size());
    }


    // Helper

    private List<Account> accounts(int size) {
        return accounts(size, false);
    }


    private List<Account> accounts(int size, boolean isFake) {
        List<Account> result = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            String id = string(18);
            String name = ((isFake) ? "Fake " : "Sample") + string(8) + i;
            result.add(new Account(id, name, "New"));
        }

        return result;
    }
}