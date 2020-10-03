public with sharing class ProcessAccountsCtrlExt {
    
    private ApexPages.StandardSetController ctrl;
    public String recordIds { get; private set; }


    // CONSTRUCTOR

    public ProcessAccountsCtrlExt(ApexPages.StandardSetController ctrl) {
        this.ctrl = ctrl;

        List<Account> accounts = ctrl.getSelected();
        if(accounts.isEmpty()) {
            accounts = ctrl.getRecords();
        }

        Set<Id> ids = new Map<Id, Account>(accounts).keySet();
        recordIds = JSON.serialize(ids);
    }


    // PUBLIC

    public PageReference redirectToList() {
        return new ApexPages.Action('{!List}').invoke();
    }
}
