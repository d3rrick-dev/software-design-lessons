void main() {
    var invoker = new Invoker();
    var loanProcessor = new LoanProcessor();
    var approve = new ApproveICommand(loanProcessor);
    invoker.executeCommand(approve); // Approve
    invoker.revertCommand(approve); // Reject
}

// 1. Invoker
// with undo then it should be able to know how to deal with undo
static class Invoker {
    List<ICommand> tasks = new ArrayList<>();
    public void executeCommand(ICommand ICommand) {
        tasks.add(ICommand);
        ICommand.execute();
    }
    public void revertCommand(ICommand ICommand) {
        var task = tasks.removeLast();
        task.undo();
    }
}

// 2. command
interface ICommand {
    void execute();
    void undo();
}

//3. concrete commands
static class ApproveICommand implements ICommand {
    private final LoanProcessor loanProcessor;

    ApproveICommand(LoanProcessor loanProcessor) {
        this.loanProcessor = loanProcessor;
    }

    @Override
    public void execute() {
        loanProcessor.approveLoan();
    }

    @Override
    public void undo() {
        loanProcessor.rejectLoan();
    }
}

static class RejectICommand implements ICommand {
    private final LoanProcessor loanProcessor;

    RejectICommand(LoanProcessor loanProcessor) {
        this.loanProcessor = loanProcessor;
    }

    @Override
    public void execute() {
        this.loanProcessor.rejectLoan();
    }

    @Override
    public void undo() {
        this.loanProcessor.approveLoan();
    }
}

// 4. Receiver
// where actual action happens
static class LoanProcessor {
    void approveLoan() {
        IO.println("Approve");
    }
    void rejectLoan() {
        IO.println("Reject");
    }
}