package com.copado.api.db;

import com.copado.api.model.Account;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryAccounts {

    private static Map<String, List<Account>> accountsByTaskId = new HashMap<>();

    public void addAccount(String taskId, Account account) {
        if(!accountsByTaskId.containsKey(taskId)) {
            accountsByTaskId.put(taskId, new ArrayList<>());
        }

        addOrUpdate(taskId, account);
    }


    public void clear(String taskId) {
        if(hasAccount(taskId)) {
            accountsByTaskId.get(taskId).clear();
        }
    }


    public List<Account> getAccounts(String taskId) {
        return accountsByTaskId.get(taskId);
    }


    public boolean hasAccount(String taskId) {
        return  (accountsByTaskId.containsKey(taskId) && !accountsByTaskId.get(taskId).isEmpty());
    }


    // PRIVATE

    private void addOrUpdate(String taskId, Account account) {

        Account existingAccount = find(taskId, account.getId());

        if(existingAccount != null) {
            existingAccount.setName(account.getName());
            existingAccount.setType(account.getType());
        }
        else {
            accountsByTaskId.get(taskId).add(account);
        }
    }


    private Account find(String taskId, String accountId) {
        Account result = null;

        for(Account account : accountsByTaskId.get(taskId)) {
            if(account.getId().equals(accountId)) {
                result = account;
                break;
            }
        }

        return result;
    }
}
