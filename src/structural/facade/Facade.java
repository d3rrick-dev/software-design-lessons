void main() {
//    var request = new LoanRequest(100, 2500, true); //true
    var request = new LoanRequest(100, 10, true); //false
    var facade = new LoanFacade();
    var approved = facade.processLoan(request);
    IO.println(approved);
}
record LoanRequest(int amount, int score,boolean kycVerified) { }

static class FraudService {
    boolean validate(LoanRequest request) { return request.score() > 20; }
}

static class KYCService {
    boolean verify(LoanRequest request) { return request.kycVerified(); }
}

static class CreditService {
    int score(LoanRequest request) { return request.score(); }
}

static class LoanAccounting {
    void createLoan(LoanRequest request) { /* ... */ }
}

static class NotificationService {
    void notifyCustomer(LoanRequest request) { /* ... */ }
}


// facade
static class LoanFacade {

    private FraudService fraud = new FraudService();
    private KYCService kyc = new KYCService();
    private CreditService credit = new CreditService();
    private LoanAccounting accounting = new LoanAccounting();
    private NotificationService notification = new NotificationService();

    public boolean processLoan(LoanRequest request) {

        if (!fraud.validate(request)) return false;
        if (!kyc.verify(request)) return false;
        if (credit.score(request) < 500) return false;

        accounting.createLoan(request);
        notification.notifyCustomer(request);

        return true;
    }
}
