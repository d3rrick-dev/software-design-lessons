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