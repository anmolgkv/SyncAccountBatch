public with sharing class ProcessAllAccounts implements Database.Batchable<sObject>, Database.Stateful, Database.AllowsCallouts, Schedulable {
    Boolean init = true;
	private Boolean synchronouslyInTests = false;

    
    public void execute() {
		if(Test.isRunningTest() && synchronouslyInTests) {
			executeSynchronously();
		}
		else {
			Database.executeBatch(this, batchSize());
		}
    }


    public Database.QueryLocator start(Database.BatchableContext bc) {
        return Database.getQueryLocator('SELECT Id, Name, Type FROM Account LIMIT 10000');
    }


    public void execute(Database.BatchableContext bc, List<Account> accounts) {
        new BackendApi().sendAccounts(accounts, init);
        init = false;
    }  

     
    public void finish(Database.BatchableContext bc) {
        new BackendApi().process();
    }
    

    public void execute(SchedulableContext sc) {
        execute();
    }


    // PRIVATE

	private void executeSynchronously() {
		Database.BatchableContext nullCtx = null;

        Iterator<SObject> fullScope = start(nullCtx).iterator();
        
        execute(nullCtx, toList(fullScope));

        finish(nullCtx);
    }
    

    private List<SObject> toList(Iterator<SObject> iterator) {
        List<SObject> result = new List<SObject>();

        while(iterator.hasNext()) {
            result.add(iterator.next());
        }

        return result;
    }
    

    private Integer batchSize() {
        return 1000;
    }
    
    
	@TestVisible
	private ProcessAllAccounts synchronouslyInTests() {
		synchronouslyInTests = true;
		return this;
	}
}