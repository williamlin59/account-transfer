# account-transfer
To run this app with please add -Dspring.profiles.active=embedded to VM option
This is a simple app which expose a rest endpoint take input transaction data as format below:
[
  {
  "sourceAccountId" :1,
  "targetAccountId" :2,
  "amount" :100.00,
    "transactionTime":"2013-12-18T14:30:40.010"
	},
    {
  "sourceAccountId" :6,
  "targetAccountId" :7,
  "amount" :400.00,
   "transactionTime":"2014-12-18T14:30:40.010"
	}
  ]
  
 Account data and Invalid Transactions persisted to sql database using JPA(Hibernate) and H2 in memory database.
 The app also got a localCache caching all acocunt data using Java.concurrentHashMap, A schedule job synchronize data
 between cache and database.
 
 It also build in unit tests and integration tests with overall test coverage over 93%
 
 
 
  
