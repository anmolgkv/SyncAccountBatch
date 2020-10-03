@IsTest
private class ProcessAccountsCtrlExt_Test {

    @IsTest
    private static void redirectToList() {

        // Setup
        Account account = (Account) new Account_t().persist();

        ApexPages.StandardSetController ctrl = new ApexPages.StandardSetController( new List<Account>{ account } );


        // Exercise
        new ProcessAccountsCtrlExt(ctrl).redirectToList();
        
        
        // Verify
        System.assertNotEquals(null, ctrl.getFilterId());
    }
    
    
    @IsTest
    private static void setSelectedRecords() {

        // Setup
        Account account = (Account) new Account_t().persist();
        List<Account> accounts = new List<Account>{ account };
        
        ApexPages.StandardSetController ctrl = new ApexPages.StandardSetController(accounts);
        ctrl.setSelected(accounts);


        // Exercise
        ProcessAccountsCtrlExt ctrlExt = new ProcessAccountsCtrlExt(ctrl);
        List<Id> recordIds = (List<Id>) JSON.deserialize(ctrlExt.recordIds, List<Id>.class);
        
        
        // Verify
        System.assertEquals(1, recordIds.size());
    }
}
