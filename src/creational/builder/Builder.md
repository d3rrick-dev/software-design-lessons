**Builder Pattern**

**Purpose**:
The Builder pattern separates the construction of a complex object from its representation, so that the same construction process can create different representations.
It’s especially useful when an object has many optional parameters, or when the object creation process involves multiple steps.

**Key Points**
**1. Problem it solves:**
 * Constructors with too many parameters are confusing (Telescoping Constructor Anti-Pattern).
 * Optional parameters make code hard to read and maintain.
 * Object creation may require multiple steps or validations.

**2. How it works:**
 * You create a Builder class with methods to set each attribute.
 * A build() method returns the final object.
 * The object itself is usually immutable once built.

**3.Benefits:**
 * Improves readability and maintainability.
 * Makes object creation flexible.
 * Avoids constructors with long parameter lists.
 * Supports method chaining.