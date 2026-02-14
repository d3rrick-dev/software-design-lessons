void main() {
    var chatRoom = new ChatRoom();
    var user1 = new ChatUser(chatRoom, "Alice");
    var user2 = new ChatUser(chatRoom, "Bob");
    var user3 = new ChatUser(chatRoom, "Charlie");

    chatRoom.addUser(user1);
    chatRoom.addUser(user2);
    chatRoom.addUser(user3);

    user1.send("Hello everyone!");
    IO.println();
    user2.send("Hi there!");
    IO.println();
    user3.send("Feeling sick!");
    IO.println();
    user1.send("Get well soon!");

}

// mediator interface
interface ChatMediator {
    void sendMessage(String message, User user);
    void addUser(User user);
}

// concrete mediator
static class ChatRoom implements ChatMediator {
    final private List<User> users = new ArrayList<>();
    @Override
    public void sendMessage(String message, User sender) {
        for (var u: users) {
            if (u != sender) {
                u.receive(message);
            }
        }
    }

    @Override
    public void addUser(User user) {
        this.users.add(user);
    }
}

// user
// each knows a mediator
abstract static class User {
    protected ChatMediator mediator;
    protected String name;

    public User(ChatMediator mediator, String name) {
        this.mediator = mediator;
        this.name = name;
    }

    abstract void send(String message);
    abstract void receive(String message);
}


// concrete user
class ChatUser extends User {

    public ChatUser(ChatMediator mediator, String name) {
        super(mediator, name);
    }

    void send(String message) {
        System.out.println(name + " sends: " + message);
        mediator.sendMessage(message, this);
    }

    void receive(String message) {
        System.out.println(name + " receives: " + message);
    }
}