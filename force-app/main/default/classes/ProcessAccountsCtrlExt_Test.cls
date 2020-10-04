@IsTest
private class ProcessAccountsCtrlExt_Test {

    @IsTest
    private static void redirectToList() {

        // Given
        Account account = (Account) new Account_t().persist();

        ApexPages.StandardSetController ctrl = new ApexPages.StandardSetController( new List<Account>{ account } );


        // When
        new ProcessAccountsCtrlExt(ctrl).redirectToList();
        
        
        // Then
        System.assertNotEquals(null, ctrl.getFilterId());
    }
    
    
    @IsTest
    private static void setSelectedRecords() {

        // Given
        Account account = (Account) new Account_t().persist();
        List<Account> accounts = new List<Account>{ account };
        
        ApexPages.StandardSetController ctrl = new ApexPages.StandardSetController(accounts);
        ctrl.setSelected(accounts);


        // When
        ProcessAccountsCtrlExt ctrlExt = new ProcessAccountsCtrlExt(ctrl);
        List<Id> recordIds = (List<Id>) JSON.deserialize(ctrlExt.recordIds, List<Id>.class);
        
        
        // Then
        System.assertEquals(1, recordIds.size());
    }
}
