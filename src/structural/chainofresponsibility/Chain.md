**Core Idea of Chain of Responsibility**
Pass a request along a chain of handlers.

**Each handler decides:**
 * Handle it
 * Or pass it to the next one


**Think:**
 * Customer complaint
 * Support agent receives it
 * If they can solve → done
 * If not → escalate to supervisor
 * If not → escalate to manager
Each person decides whether to handle or forward.


**When a loan request arrives:**
1.	Fraud team checks
2.	Compliance checks
3.	Risk team checks
4.	Final approval decides

- Not everyone must modify the result
- Someone may stop the chain entirely

**Where CoR Is Used in Real Systems**
* Spring Security filter chains
* Servlet filters
* Logging pipelines
* Validation frameworks
* Middleware systems
* Exception handling systems

**When used:**
 * Multiple handlers evaluate eligibility
 * First rejection stops flow
 * Routing logic decides next step