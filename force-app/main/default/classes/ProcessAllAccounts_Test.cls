@IsTest
private class ProcessAllAccounts_Test {
    @IsTest
    private static void execute() {

        // Given
        Account account = (Account) new Account_t().persist();
        List<BackendApi.AccountResponse> accountResponses = new List<BackendApi.AccountResponse> {
            new BackendApi.AccountResponse(account, 'Looks good')
        };

        new HttpMock().post('/batch', new BackendApi.Response('success'))
                      .post('/process', new BackendApi.Response('success', accountResponses))
                      .mock();
                      

        // When
        Test.startTest();
            new ProcessAllAccounts().synchronouslyInTests().execute();
        Test.stopTest();

        
        // Then
        account = [Select SyncMessage__c From Account];
        System.assertEquals('Looks good', account.SyncMessage__c);
    }
    
    
    @IsTest
    private static void schedule() {
       
        // Given
        Account account = (Account) new Account_t().persist();
        List<BackendApi.AccountResponse> accountResponses = new List<BackendApi.AccountResponse> {
            new BackendApi.AccountResponse(account, 'Looks good')
        };
        new HttpMock().post('/batch', new BackendApi.Response('success'))
                      .post('/process', new BackendApi.Response('success', accountResponses))
                      .mock();
        
        
        // When
        String everyDayAtMidnight = '0 00 00 * * ?';  
        Id jobId = System.schedule('Process all accounts', everyDayAtMidnight, new ProcessAllAccounts());


        // Then
		System.assertEquals(1, [SELECT Count() FROM CronTrigger WHERE Id = :jobId]);
    }
}