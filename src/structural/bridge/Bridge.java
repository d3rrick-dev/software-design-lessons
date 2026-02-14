void main() {
    var email = new EmailSender();
    var sms = new SmsSender();

    var urgentEmail = new UrgentNotification(email);
    urgentEmail.notifyUser("Server is down!");

    var simpleSms = new SimpleNotification(sms);
    simpleSms.notifyUser("Daily report ready.");
}

interface MessageSender {
    void sendMessage(String message);
}

// implementations
static class EmailSender implements MessageSender {
    public void sendMessage(String message) {
        System.out.println("Sending EMAIL: " + message);
    }
}

static class SmsSender implements MessageSender {
    public void sendMessage(String message) {
        System.out.println("Sending SMS: " + message);
    }
}

// Abstraction
// now imagine we have one email sender and 1 sms sender, tell them to send
// now or after 1 week.

abstract static class Notification {
    protected MessageSender sender;
    protected Notification(MessageSender sender) {
        this.sender = sender;
    }
    abstract void notifyUser(String message);
}

static class SimpleNotification extends Notification {
    public SimpleNotification(MessageSender sender) {
        super(sender);
    }

    @Override
    void notifyUser(String message) {
        sender.sendMessage(message);
    }
}

static class UrgentNotification extends Notification {
    public UrgentNotification(MessageSender sender) {
        super(sender);
    }

    @Override
    void notifyUser(String message) {
        sender.sendMessage("URGENT: " + message);
    }
}

