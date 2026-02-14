
**Command pattern**

- In general, an object-oriented application consists of a set of interacting objects each offering limited, focused functionality.
- In response to user interaction, the application carries out some kind of processing.
- For this purpose, the application makes use of the services of different objects for the processing requirement.
- In terms of implementation, the application may depend on a designated object that invokes methods on these objects by passing the required data as arguments

- It's about encapsulating the request.
- Between the client and the receiver.

- the request is a ICommand, if there are multiple commands then they can be passed around.
- Other objects can be parametrized with these commands objects.
- In a list, means putting them in a list and executing them.
- Reverting commands, undo e.d add 1, substract 1

**Ｗe want to avoid this:**
```md
       … 
  if (RequestType=TypeA){ 
     //do something 
  } 
  if (RequestType=TypeB){ 
     //do something 
  } 

  // adding more requests types violates opened-close principal
  if (RequestType=NewType){
  //do something
  } 

```
**Open for extension** — It should be possible to alter the behavior of a module or add new features to the module functionality.
**Closed for modification** — Such a module should not allow its code to be modified.

**Invoker -> ICommand -> Receiver**
**Invoker:**
- Invoker is a class (Remote control class)
- Has 0-N commands(Icommand)

**ICommand:**
- Has a couple of implementations (commands)
- Each command **has-a** Receiver i.e each command works on some receiver (turn-off, turn-on)

**Receiver:**
- Is the object being acted on, where execution happens, e.g on a light bulb

1. [Remote control Example](RemoteControl.java)
2. [Loan Processor example](LoanProcessor.java)

**In java**
```java
//Wrap behavior inside an object.
class MyTask implements Runnable {
    public void run() {
        System.out.println("Task running..."); //concrete command
    }
}

public class Main {
    public static void main(String[] args) {
        Runnable task = new MyTask();  // Command
        Thread thread = new Thread(task); // Invoker
        thread.start();
    }
}
```