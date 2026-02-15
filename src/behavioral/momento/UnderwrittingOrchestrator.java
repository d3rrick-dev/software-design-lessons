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

        var recovered = new UnderwritingOriginator();
        recovered.restore(snapshot);

        // Resume intelligently
        recovered.resume();

        caretaker.save(correlationId, recovered.checkpoint());
    }
}


public enum UnderwritingStep {
    KYC,
    CREDIT_BUREAU,
    RISK_MODEL,
    FINAL_DECISION
}

public enum StepStatus {
    NOT_STARTED,
    IN_PROGRESS,
    COMPLETED,
    FAILED
}
// originator
public static class UnderwritingOriginator {
    private String internalScore;
    private List<String> verifiedAttributes = new ArrayList<>();
    private double preliminaryLimit;
    private final Map<UnderwritingStep, StepStatus> stepStatuses =
            Arrays.stream(UnderwritingStep.values())
                    .collect(Collectors.toMap(
                            step -> step,
                            step -> StepStatus.NOT_STARTED,
                            (a,b) -> a,
                            () -> new EnumMap<>(UnderwritingStep.class)
                    ));

    // ---- Business Steps ----
    public void validateIdentity() {
        if (isCompleted(UnderwritingStep.KYC)) return;

        stepStatuses.put(UnderwritingStep.KYC, StepStatus.IN_PROGRESS);
        IO.println("Calling KYC provider...");
        verifiedAttributes.add("identity_verified");
        stepStatuses.put(UnderwritingStep.KYC, StepStatus.COMPLETED);
    }

    public void fetchCreditScore() {
        if (isCompleted(UnderwritingStep.CREDIT_BUREAU)) return;

        stepStatuses.put(UnderwritingStep.CREDIT_BUREAU, StepStatus.IN_PROGRESS);
        IO.println("Calling Credit Bureau...");
        internalScore = "CREDIT_720";
        stepStatuses.put(UnderwritingStep.CREDIT_BUREAU, StepStatus.COMPLETED);
    }

    // Assuming it could crash
    // or async, I have to wait for results
    public void runRiskModel() {
        if (isCompleted(UnderwritingStep.RISK_MODEL)) return;

        stepStatuses.put(UnderwritingStep.RISK_MODEL, StepStatus.IN_PROGRESS);
        IO.println("Running Risk Model...");
        preliminaryLimit = 5000.0;
        stepStatuses.put(UnderwritingStep.RISK_MODEL, StepStatus.COMPLETED);
    }

    public void approveLoan() {
        if (isCompleted(UnderwritingStep.FINAL_DECISION)) return;

        stepStatuses.put(UnderwritingStep.FINAL_DECISION, StepStatus.IN_PROGRESS);
        IO.println("Loan Approved with limit: " + preliminaryLimit);
        stepStatuses.put(UnderwritingStep.FINAL_DECISION, StepStatus.COMPLETED);
    }

    private boolean isCompleted(UnderwritingStep step) {
        return stepStatuses.get(step) == StepStatus.COMPLETED;
    }

    // ---- Memento Operations ----
    public UnderwritingMemento checkpoint() {
        return new UnderwritingMemento(
                internalScore,
                new ArrayList<>(verifiedAttributes),
                preliminaryLimit,
                new EnumMap<>(stepStatuses)
        );
    }

    // ----------------------------
    // Resume Logic (Reconciliation)
    // ----------------------------

    public void resume() {

        if (!isCompleted(UnderwritingStep.KYC)) {
            validateIdentity();
        }

        if (!isCompleted(UnderwritingStep.CREDIT_BUREAU)) {
            fetchCreditScore();
        }

        if (!isCompleted(UnderwritingStep.RISK_MODEL)) {
            runRiskModel();
        }

        if (!isCompleted(UnderwritingStep.FINAL_DECISION)) {
            approveLoan();
        }
    }

    public void restore(UnderwritingMemento memento) {
        this.internalScore = memento.internalScore();
        this.verifiedAttributes = new ArrayList<>(memento.verifiedAttributes());
        this.preliminaryLimit = memento.preliminaryLimit();
        this.stepStatuses.clear();
        this.stepStatuses.putAll(memento.stepStatuses());
    }


}


// momento
public record UnderwritingMemento(
        String internalScore,
        List<String> verifiedAttributes,
        double preliminaryLimit,
        Map<UnderwritingStep, StepStatus> stepStatuses
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