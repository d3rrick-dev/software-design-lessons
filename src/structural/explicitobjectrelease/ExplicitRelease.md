**Explicit Object Release Pattern – Summary**

**Purpose**

Ensure that objects **holding** external or system resources (files, network connections, database connections, etc.) **release** those resources explicitly and in a timely manner, rather than relying on **garbage** collection.

**Problem**
 - Garbage collection in Java is **non-deterministic**.
 - **finalize()** is unreliable because the JVM may run it at unpredictable times.
 - If resources aren't explicitly released, you risk **resource leaks** (open files, connections, memory, etc.).

**Solution**

**Explicitly** release resources when done using the object. In Java, this is typically done using a **finally block** or **try-with-resources**.

**Benefits**
1. **Predictable resource management** – resources freed as soon as they are no longer needed. 
2. **Prevents leaks** – avoids accumulation of open files, sockets, or connections. 
3. **Safe even on exceptions** – ensures cleanup happens for both checked and unchecked exceptions.

**Best Practices in Java**
- Use finally or try-with-resources (AutoCloseable) for deterministic cleanup. 
- Avoid relying on finalize() for releasing system resources. 
- Design classes so that resource acquisition and release are explicit and paired.

**Real-World Analogy**
- Closing a file after writing 
- Returning a database connection to the pool 
- Closing a network socket after sending/receiving data

The object may still exist in memory, but the external resources it held are released immediately.