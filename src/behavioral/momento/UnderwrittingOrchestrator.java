void main() {
    var correlationId = "loan-123";
    var caretaker = new InMemoryStateCaretaker();
    var originator = new UnderwritingOriginator();

    try {
        // Step 1
        originator.validateIdentity();
        // save a momento attriute to this point
        //Nb, the momento is not fully conclusively constructed
        caretaker.save(correlationId, originator.checkpoint());

        // Step 2
        originator.fetchCreditScore();
        caretaker.save(correlationId, originator.checkpoint());

        // ---- Simulate crash before Step 3 ----
        throw new RuntimeException("Pod crashed!");

    } catch (Exception ex) {
        IO.println("System crashed. Recovering from checkpoint...");
        var snapshot = caretaker.get(correlationId);

        // New instance (simulating new pod)
        var recovered = new UnderwritingOriginator();
        recovered.restore(snapshot);

        // Resume execution
        if (recovered.getCurrentStep() < 3) {
            recovered.runRiskModel();
            caretaker.save(correlationId, recovered.checkpoint());
        }
        recovered.approveLoan();
    }
}

// originator
public static class UnderwritingOriginator {
    private String internalScore;
    private List<String> verifiedAttributes = new ArrayList<>();
    private double preliminaryLimit;
    private int currentStep = 0;

    // ---- Business Steps ----
    public void validateIdentity() {
        IO.println("Calling KYC provider...");
        verifiedAttributes.add("identity_verified");
        currentStep = 1;
    }

    public void fetchCreditScore() {
        IO.println("Calling Credit Bureau...");
        internalScore = "CREDIT_720";
        currentStep = 2;
    }

    // Assuming it could crash
    // or async, I have to wait for results
    public void runRiskModel() {
        IO.println("Running Risk Model...");
        preliminaryLimit = 5000.0;
        currentStep = 3;
    }

    public void approveLoan() {
        IO.println("Loan Approved with limit: " + preliminaryLimit);
        currentStep = 4;
    }

    // ---- Memento Operations ----
    public UnderwritingMemento checkpoint() {
        return new UnderwritingMemento(
                internalScore,
                verifiedAttributes,
                preliminaryLimit,
                currentStep
        );
    }

    public void restore(UnderwritingMemento memento) {
        this.internalScore = memento.internalScore();
        this.verifiedAttributes = new ArrayList<>(memento.verifiedAttributes());
        this.preliminaryLimit = memento.preliminaryLimit();
        this.currentStep = memento.currentStep();
    }

    public int getCurrentStep() {
        return currentStep;
    }
}


// momento
public record UnderwritingMemento(
        String internalScore,
        List<String> verifiedAttributes,
        double preliminaryLimit,
        int currentStep
) implements Serializable {}


public static class InMemoryStateCaretaker {
    private final Map<String, UnderwritingMemento> store = new HashMap<>();

    public void save(String correlationId, UnderwritingMemento memento) {
        store.put(correlationId, memento);
    }

    public UnderwritingMemento get(String correlationId) {
        return store.get(correlationId);
    }

    public void clear(String correlationId) {
        store.remove(correlationId);
    }
}