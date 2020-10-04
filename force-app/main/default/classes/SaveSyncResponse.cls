public with sharing class SaveSyncResponse {
    List<BackendApi.AccountResponse> accounts;

    public SaveSyncResponse(List<BackendApi.AccountResponse> accounts) {
        this.accounts = accounts;
    }

    
    public void execute() {
        DMLManager.updateAsSystem( asSobject(accounts) );
    }


    private List<Account> asSobject(List<BackendApi.AccountResponse> accounts) {
        List<Account> result = new List<Account>();

        for(BackendApi.AccountResponse account : accounts) {
            result.add(new Account(Id = account.Id, SyncMessage__c = account.message));
        }

        return result;
    }
}