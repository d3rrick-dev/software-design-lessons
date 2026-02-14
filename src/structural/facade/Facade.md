**Facade**

**It's about how objects are connected:**
 * The client interacts with one object
 * The subsystem classes stay decoupled
 * Internal complexity is hidden behind a structure

Facade is just a simplified interface

**Structural pattern** - hides complexity, organizes objects
**Doesn’t implement the logic itself** — delegates
**Often used in combination with other patterns:**
 * Decorator for enhancing subsystems
 * Chain of Responsibility for request evaluation
 * Builder for complex objects

**It's about how objects are connected:**
 * The client interacts with one object
 * The subsystem classes stay decoupled
 * Internal complexity is hidden behind a structure


**Use cases:**
 * **Payment gateways**: PaymentFacade.pay() wraps multiple payment processors
 * **Web frameworks**: RestTemplate or HttpClient wraps low-level HTTP classes
 * **Microservices**: API Gateway is basically a Facade
 * **Expose to partners a service**: so they never touch individual services