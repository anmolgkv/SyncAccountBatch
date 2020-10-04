public with sharing class ProcessAccountsCtrl {
    
    @AuraEnabled
    public static BackendApi.Response process(List<Id> accountIds) {
        List<Account> accounts = new AccountsSelector().byId(new Set<Id>(accountIds)); 
        
        try {
            new BackendApi().sendAccounts(accounts, true);
            
            return new BackendApi().process();
        } catch (ffhttp_AppException ex) {
            throw new AuraHandledException(ex.getMessage());
        }
    }
}
