public with sharing class ProcessAccountsCtrl {
    
    @AuraEnabled
    public static BackendApi.Response process(List<Id> accountIds) {
        List<Account> accounts = new AccountsSelector().byId(new Set<Id>(accountIds));        
        BackendApi.Response syncResponse = new BackendApi().sendAccounts(accounts, true);

        if(!syncResponse.isSuccess()) {
            throw new AuraHandledException(syncResponse.message);
        }

        return new BackendApi().process();
    }
}
