void main() {
    var loan1 = new PersonalLoan(new MpesaProvider());
    loan1.processLoan(50000);

    var loan2 = new BusinessLoan(new BankTransferProvider());
    loan2.processLoan(200000);
}

// Payments
interface PaymentProvider {
    void pay(double amount);
}

static class MpesaProvider implements PaymentProvider {
    public void pay(double amount) {
        System.out.println("Processing via M-Pesa: " + amount);
    }
}

static class BankTransferProvider implements PaymentProvider {
    public void pay(double amount) {
        System.out.println("Processing via Bank: " + amount);
    }
}

// Loan
abstract class Loan {
    protected PaymentProvider provider;

    public Loan(PaymentProvider provider) {
        this.provider = provider;
    }
    abstract void processLoan(double amount);
}


class PersonalLoan extends Loan {

    public PersonalLoan(PaymentProvider provider) {
        super(provider);
    }

    @Override
    void processLoan(double amount) {
        System.out.println("Processing personal loan");
        provider.pay(amount);
    }
}

class BusinessLoan extends Loan {

    public BusinessLoan(PaymentProvider provider) {
        super(provider);
    }

    @Override
    void processLoan(double amount) {
        System.out.println("Processing business loan");
        provider.pay(amount);
    }
}