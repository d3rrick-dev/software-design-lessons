**Mediator Pattern**

Instead of objects talking to each other directly, they communicate through a central mediator object.

**Without Mediator:**

If you have 5 objects and they all talk to each other:
 - A talks to B, C, D, E 
 - B talks to A, C, D, E 
 - C talks to A, B, D, E

You get a web of dependencies.

This leads to:
* Tight coupling 
* Hard maintenance 
* Hard debugging 
* Ripple-effect changes

**With Mediator:**
* A -> Mediator
* B -> Mediator
* C -> Mediator
* D -> Mediator

**Why Mediator Is Useful:**
1.	Reduces coupling between objects
2.	Centralizes complex interaction logic
3.	Makes objects simpler
4.	Easier to modify interaction rules

Comparing with Observer.

**Mediator:**
- central control
- Control interaction logic
- Coordinated colleagues

**Observer:**
- Broadcasts nootification
- Notifies observers of state change
- Subject notifies observers





