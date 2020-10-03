@IsTest
private class ProcessAccountsCtrl_Test {
    
    @IsTest
    private static void process() {
        // Setup
        Account account = (Account) new Account_t().persist();
        new HttpMock().post('/batch', new BackendApi.Response('success'))
                      .post('/process', new BackendApi.Response('success', accountResponse(account)))
            		  .mock();

        
        // Exercise
        Test.startTest();
            ProcessAccountsCtrl.process(new List<Id> { account.Id });
		Test.stopTest();

        
        // Verify
        account = [Select SyncMessage__c From Account];
        System.assertEquals('Looks good', account.SyncMessage__c);
    }


    // Helper
    
    private static String accountResponse(Account account) {
        BackendApi.AccountResponse result = new BackendApi.AccountResponse();
        result.id = account.Id;
        result.name = account.Name;
        result.message = 'Looks good';

        return JSON.serialize(new List<BackendApi.AccountResponse> { result });
    }
}