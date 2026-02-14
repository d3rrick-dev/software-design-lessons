void main() {
    var request = new LoanRequest(1000, 13);
    var processor = new FraudCheckDecorator(new BaseLoanProcessor());
    var loan = processor.process(request); // or throw exception
}

record Loan(int principal, boolean approved) { }
record LoanRequest(int amount, int score) { }

public interface LoanProcessor {
    Loan process(LoanRequest request);
}

public class BaseLoanProcessor implements LoanProcessor {
    @Override
    public Loan process(LoanRequest request) {
        var loan = new Loan(request.amount(), true);
        return loan;
    }
}

public abstract class LoanProcessorDecorator implements LoanProcessor {

    protected LoanProcessor processor;

    public LoanProcessorDecorator(LoanProcessor processor) {
        this.processor = processor;
    }

    @Override
    public Loan process(LoanRequest request) {
        return processor.process(request);
    }
}

// we can add multiple Decorators
// could involve calling external API
public class FraudCheckDecorator extends LoanProcessorDecorator {
    public FraudCheckDecorator(LoanProcessor processor) {
        super(processor);
    }

    @Override
    public Loan process(LoanRequest request) {
        if (isFraudulent(request)) {
            throw new RuntimeException("Fraud detected");
        }
        return super.process(request);
    }
    private boolean isFraudulent(LoanRequest request) {
        return request.score() < 20;
    }
}

// Compliance hits KYC microservice