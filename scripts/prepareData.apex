List<Account> accounts = new List<Account>();

for(Integer i = 0; i < 10000; i++) {
    accounts.add(new Account(Name = 'Sample Account ' + i, Type = 'Prospect'));
}

accounts[0].Name = 'Fake Account';
insert accounts;