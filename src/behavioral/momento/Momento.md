**Momento**
Used to capture and save an object's internal state—a "snapshot"—without violating encapsulation, allowing the object to be restored to this state later

**Key Components:**
* **Originator**:
- The object whose state needs to be saved. it has fields that needs to be built incrementally.
- These fields will build up the momento object.
- It creates a memento containing a snapshot of its current internal state and uses it for restoration.

* **Caretaker**:
- Manages the history of the memento objects (e.g., in a stack) but never operates on or examines the contents of a memento.
- So whatever happens to the originator, we save that snapshot.

* **Memento**: 
- An immutable value object that stores the state of the originator. 
- It acts as a "magic token" that protects the state from external access.

**Why Momentos are needed:**
- Database models external application state.
- Memento models internal working state

The LoanApplication table might look like:
```md
id | user_id | status | approved_limit
```

**But your orchestrator has:**
 * Internal score vectors
 * Partial fraud flags
 * Risk model feature weights
 * Aggregated telco signals
 * Temporary normalization values

Those do NOT belong in a clean relational schema.
 - It's a working memory, Memento territory.


**In microservices:**

Memento is about preserving computational progress.
**It's about:**
 * Avoiding recomputation
 * Avoiding re-costing
 * Avoiding latency regression
 * Maintaining distributed resilience