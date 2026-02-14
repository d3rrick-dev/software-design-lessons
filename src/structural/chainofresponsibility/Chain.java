void main() {
    var request = new LoanRequest(100, 13, false);

    var fraud = new FraudHandler();
    var compliance = new ComplianceHandler();
    var approval = new ApprovalHandler();

    fraud.next(compliance);
    compliance.next(approval);

    fraud.handle(request);
}

record LoanRequest(int amount, int score,boolean kycVerified) { }

static abstract class LoanHandler {
    protected LoanHandler next;
    public LoanHandler next(LoanHandler next) {
        this.next = next;
        return next;
    }
    public abstract void handle(LoanRequest request);
}

static class FraudHandler extends LoanHandler {
    @Override
    public void handle(LoanRequest request) {
        if (request.score() < 20) {
            throw new RuntimeException("Fraud detected");
        }
        if (next != null) {
            next.handle(request);
        }
    }
}

static class ComplianceHandler extends LoanHandler {
    @Override
    public void handle(LoanRequest request) {
        if (!request.kycVerified()) {
            throw new RuntimeException("KYC not verified");
        }
        if (next != null) {
            next.handle(request);
        }
    }
}

static class ApprovalHandler extends LoanHandler {
    @Override
    public void handle(LoanRequest request) {
        // update a loan
        new LoanRequest(request.amount(), request.score(), true);
    }
}