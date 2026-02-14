void main() {
    var light = new Light();
    // command object is request and receiver
    var on = new TurnOnCommand(light);
    var off = new TurnOffCommand(light);

    var remote = new Remote();
    remote.setCommand(on);
    // invoker only triggers the command
    remote.pressButton(); // Light ON

    remote.setCommand(off);
    // invoker only triggers the command
    remote.pressButton(); // Light OFF
}

//1. Invoker: only knows about commands, not the receivervvv
static class Remote {
    private Command command;
    void setCommand(Command command) { this.command = command; }
    void pressButton() { command.execute(); }
}

//	2.	Command Interface: standard method execute()
interface Command {
    void execute();
//    void unExecute();
}

//	3.	Concrete Commands: wrap requests for the receiver
static class TurnOnCommand implements Command {
    private final Light light;
    TurnOnCommand(Light light) { this.light = light; }
    public void execute() { light.turnOn(); }
}

static class TurnOffCommand implements Command {
    private final Light light;
    TurnOffCommand(Light light) { this.light = light; }
    public void execute() { light.turnOff(); }
}

//	4.	Receiver: knows how to do the work
static class Light {
    void turnOn() { System.out.println("Light ON"); }
    void turnOff() { System.out.println("Light OFF"); }
}