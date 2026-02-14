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
3. JDBC

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

**Example 3**

**JDBC**
How do you allow any Java application to talk to any database without recompiling the application or the database?

- If JDBC used inheritance, you’d have classes like **MySqlConnection**, **PostgreSqlConnection**, and **OracleConnection**. 
- Your code would be tightly coupled to a specific database vendor.

Instead, JDBC separates the **Abstraction** (what the programmer uses) from the **Implementation** (what the database vendor provides).

**1. The Abstraction (The java.sql API)**
```java
java.sql.Connection;
java.sql.Statement;
java.sql.ResultSet;
```

**2. The Implementation (The JDBC Driver)**
These are the low-level classes provided by vendors (MySQL, Oracle, PostgreSQL). They implement the interfaces above but contain the messy, socket-level protocol logic specific to that database.
```java
com.mysql.cj.jdbc.ConnectionImpl;
org.postgresql.jdbc.PgConnection;
```

**Putting them together:**
```java
// 1. The Client (Abstraction side)
// We only ever deal with the INTERFACE 'Connection'
Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ezra", "user", "pass");

// 2. The Implementation side (Under the hood)
// The DriverManager found the MySQL Driver and returned a 'com.mysql...ConnectionImpl'
// object, but cast it to the 'java.sql.Connection' interface.

// 3. Using the Bridge
// When you call .createStatement(), the Abstraction calls the Implementation.
Statement stmt = conn.createStatement();
```