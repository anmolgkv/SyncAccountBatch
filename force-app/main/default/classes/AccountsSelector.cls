public with sharing class AccountsSelector extends fflib_SObjectSelector {
     
    public SObjectType getSObjectType() {
        return Account.SObjectType;
    }
    
    
    public List<SObjectField> getSObjectFieldList() {
        return new List<Schema.SObjectField> { 
            Account.Name, 
            Account.Type
        };
    }


    public List<Account> byId(Set<ID> idSet) {
        return (List<Account>) selectSObjectsById(idSet);
    }
}
