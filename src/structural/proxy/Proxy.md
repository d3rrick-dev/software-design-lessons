**Proxy**

**Think of a proxy like:**
 * A gatekeeper
 * A lazy loader
 * Or a security wrapper

It implements the same interface as the real object, so clients don’t know they’re interacting with a proxy.


**Types of Proxy**
1.	**Virtual Proxy** – delays object creation until needed
2.	**Protection Proxy** – controls access (permissions, validation)
3.	**Remote Proxy** – represents an object in another address space (RPC, microservices)
4.	**Cache / Smart Proxy** – caches expensive calls, adds logging, metrics



**Decorator vs Proxy**
**Decorator** = “Enhancer” → adds new behavior
- The client object does have the ability to access the target object directly, if needed
- A Decorator object does not control access to the target object
- A Decorator adds additional functionality to an object.
**Proxy** = "Gatekeeper / Manager" → controls when or if real object is accessed
- The client object cannot access the target object directly
- A proxy object provides access control to the target object (in the case of the protection proxy)
- A proxy object does not add any additional functionality

```java
// Proxy: lazy loads the image
// Decorator: logs usage
// combo

Image img = new LoggingDecorator(new ImageProxy("photo.jpg"));
img.display();
```

**Facade vs Proxy**

**Facade**
- A Façade object represents a subsystem of objects.
- The client object does have the ability to access the subsystem objects directly, if needed
- A Façade object provides a simplified higher level interface to a subsystem of components

**Proxy**
- A Proxy object represents a single object.
- The client object cannot access the target object directly
- A Proxy object provides access control to the single target object

**Chain of responsibility vs Proxy**

**Chain of Responsibility**
- Chain can contain many objects.
- The object that receives the client request first could process the request.
- Client requests are forwarded to the next object in the chain only if the current receiver cannot process the request.
- Response to the request is not guaranteed. It means that the request may end up reaching the end of the chain and still might not be processed.

**Proxy**
- A Proxy object represents a single object.
- Client requests are first received by the Proxy object, but are never processed directly by the Proxy object.
- Client requests are always forwarded to the target object.
- Response to the request is guaranteed, provided the communication between the client and the server locations is working.