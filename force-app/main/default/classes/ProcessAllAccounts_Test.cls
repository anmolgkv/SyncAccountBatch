@IsTest
private class ProcessAllAccounts_Test {
    @IsTest
    private static void execute() {

        // Setup
        Account account = (Account) new Account_t().persist();
        List<BackendApi.AccountResponse> accountResponses = new List<BackendApi.AccountResponse> {
            new BackendApi.AccountResponse(account, 'Looks good')
        };

        new HttpMock().post('/batch', new BackendApi.Response('success'))
                      .post('/process', new BackendApi.Response('success', JSON.serialize(accountResponses)))
                      .mock();
                      

        // Exercise
        Test.startTest();
            new ProcessAllAccounts().synchronouslyInTests().execute();
        Test.stopTest();


        // Verify
        account = [Select SyncMessage__c From Account];
        System.assertEquals('Looks good', account.SyncMessage__c);
    }
    
    
    @IsTest
    private static void schedule() {
       
        // Setup
        Account account = (Account) new Account_t().persist();
        List<BackendApi.AccountResponse> accountResponses = new List<BackendApi.AccountResponse> {
            new BackendApi.AccountResponse(account, 'Looks good')
        };
        new HttpMock().post('/batch', new BackendApi.Response('success'))
                      .post('/process', new BackendApi.Response('success', JSON.serialize(accountResponses)))
                      .mock();
        
        
        // Exercise
        String everyDayAtMidnight = '0 00 00 * * ?';  
        Id jobId = System.schedule('Process all accounts', everyDayAtMidnight, new ProcessAllAccounts());


        // Verify
		System.assertEquals(1, [SELECT Count() FROM CronTrigger WHERE Id = :jobId]);
    }
}