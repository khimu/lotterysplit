wire up authorize.net
wire up facebook & twitter

Terms - take 10% of transaction

 - sign up page
 - fund the account with x dollars page
 
 Admin page
 - lottery type
 - enter validation type
 
 (state, type, validation type, winning amount)
  
 Main State (power ball (6), Megabucks Plus (6), Mega Millions (6), Hot Lotto (6), Lucky For Life (6), Gimme 5 (5),
 Mega Millions (6)
 Power Ball (6)
 California Super Lotto PLUS (6)
 New York Sweet Million (6)
 
 Account page
 - show balance
 
 balance (user, amount)
 credit( user, amount, date)
 debit(user, amount, date)
 
 input page
 
 - upload your lottery ticket
 - enter split #
 - facebook share button
 - twitter share button
 (LOTTERY_ID, state, ticket #, type, #'s, split #, cost, ticket date, flag)
 
 display page

if buyer count < split # then show pay button
 - pay button
 (buyer, LOTTERY_ID, lottery ticket ID, share amount)

 input page
 
 - enter winning lottery #'s
 - enter winning date for purchase
 - facebook share button
 - twitter share button
 (DROID_LOTTERY_ID, state, type, #'s, split #, cost, ticket date)
 
 display page
 
 - upload lottery ticket
 - facebook share button
 - twitter share button
 if buyer count < split # then show share button
 (buyer, DROID_LOTTERY_ID, lottery ticket ID, share amount)
 
 show list of lottery tickets purchased 
  - order by remaining split
  - order by type
  - order by expired date
 
 show list of lottery tickets unpurchased
  - order by split #
  - order by type
  - order by expired date
  
  
Account
- balance
- credit
- debit

Payment
- transactions 
  
  

 