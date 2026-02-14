**Bridge Pattern**

Decouple **abstraction** from **implementation** so the two can vary independently.

**Example:**

Imagine building a notification system:

**You support:**
 * Email
 * SMS
 * Push notifications

Later, product says:

**We also need:**
 * Urgent notifications
 * Scheduled notifications

**Messy Sol.** adding UrgentNotification amd SchedulesNotificationclasses for each i.e 6 more classes.
Problem is classes explode, difficult to maintain.
- Transforms ($N X M$) to ($N + M$) problem of exploding classes

**You're mixing:**
 * What it does (urgent, scheduled)
 * How it sends (email, sms, push)

**Bridge**
Split into two:
**Abstraction**: What the user interacts with
**Implementation**: How it actually work

**Example:**
1. Notifications
2. Loan service

**we have loan types**
 - PersonalLoan
- BusinessLoan

**Loan providers**
- M-Pesa
- BankTransfer
- Stripe

**Problem**
Every loan type must work with every payment provider.

Let's decouple payment from loan logic.

**You have two independent dimensions:**
1.	What the loan is
2.	How it processes payment

Those two should not depend on each other. Therefore we use bridge

From [example](LoanExample.java)
**It's easy to answer these questions:**
 - Add new provider? → just implement PaymentProvider
 - Add new loan type? → just extend Loan

**Because:**
 - Loan hierarchy varies independently
 - PaymentProvider hierarchy varies independently
 - They are connected by composition (the “bridge”)


Quiz 1.
Supporse I want to extend, say 
```
If loan < 1000 → Mpesa
If loan > 1000 → Bank
```
**Bad code.**
Because now Loan knows concrete providers again.
Broken decoupling.
```java
void processLoan(double amount) {
    if (amount < 1000) {
        new MpesaProvider().pay(amount);
    } else {
        new BankTransferProvider().pay(amount);
    }
}
```

**Fixing**
Introduce a selector (this is where Strategy or a simple factory fits).
```java
class PaymentProviderSelector {

    public PaymentProvider select(double amount) {
        if (amount < 1000) {
            return new MpesaProvider();
        }
        return new BankTransferProvider();
    }
}

//Usage
PaymentProviderSelector selector = new PaymentProviderSelector();
PaymentProvider provider = selector.select(amount);

Loan loan = new PersonalLoan(provider);
loan.processLoan(amount);
```

Quiz 2.
Suppose we want to split payments 70/30
> Introduce a class to handle Splitting and send to each payment provider this is
extending the behavior by adding a new implementation.

