@IsTest
private class ProcessAccountsCtrl_Test {
    
    @IsTest
    private static void process() {
        // Given
        Account account = (Account) new Account_t().persist();
        new HttpMock().post('/batch', new BackendApi.Response('success'))
                      .post('/process', new BackendApi.Response('success', accountResponse(account)))
            		  .mock();

        
        // When
        Test.startTest();
            ProcessAccountsCtrl.process(new List<Id> { account.Id });
		Test.stopTest();

        
        // Then
        System.assertEquals('Looks good', [Select SyncMessage__c From Account].SyncMessage__c);
    }


    @IsTest
    private static void processFailsWithoutAccount() {
        // Given
        Exception expectedException = null;
        new HttpMock().post('/batch', new CalloutException())
            		  .mock();

        
        // When
        try {
            Test.startTest();
                ProcessAccountsCtrl.process(new List<Id> ());
            Test.stopTest();
        } catch (AuraHandledException ex) {
            expectedException = ex;
        } finally {
            
        }

        
        // Then
        System.assertNotEquals(null, expectedException);
    }


    // Helper
    
    private static List<BackendApi.AccountResponse> accountResponse(Account account) {
        BackendApi.AccountResponse result = new BackendApi.AccountResponse();
        result.id = account.Id;
        result.name = account.Name;
        result.message = 'Looks good';

        return new List<BackendApi.AccountResponse> { result };
    }
}