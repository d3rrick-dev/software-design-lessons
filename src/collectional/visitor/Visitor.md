**Visitor pattern** 
It's about separation of concerns.

**Analogy:**
- Think of it as a "Specialist" visiting a "Department." 
- The department doesn't need to know how to do the specialist's job; 
it just needs to open the door and let them in.

**Usecases:**
- You have existing classes but want to intoduce some behaviours without touching the classes.
- Imagine changing requirements and wants to avoid bloating the classes with unrelared logic.

- **The Visitor solution:** Move all those "operations" into separate classes (Visitors) and have the existing classes simply "accept" them.

**Participants:**
1. **Visitor (Interface)** - Declares "visit" methods for every type of element.
2. **Concrete Visitor** - Implements a specific operation.
3. **Element (Interface)** - Declared an accept method
4. **Concrete Element** - Implements accept to call the visitor back.

**Example:**
- Imagine building a Cloud Management Platform. 
- with a complex hierarchy of resources: **ComputeInstances**, **StorageBuckets**, and **ServerlessFunctions**.

- **The Problem**
 - Your core resource classes (the "Elements") are stable. 
 - You don't want to touch the S3Bucket code every time the Finance team changes the billing logic or the Security team adds a new compliance check.
 - If you put `calculateCost()`, `checkSecurity()`, and `generateTerraform()` inside the resource classes, you violate the `Single Responsibility Principle` and create a `maintenance nightmare`.

**In this scenario,** 
- we treat our cloud resources as the **Elements** 
- and our different business logics as **Visitors**.

[Example of AWS resources](Visitor.java)

**Benefits**
- In a typical service layer, you might see a loop that checks if (res instanceof S3Bucket). That is a "code smell" because it's fragile. 
- With Visitor, the accept method handles the dispatch.
- If you have a ResourceGroup (a Composite) that contains a list of CloudResource objects, the ResourceGroup's accept method simply iterates through its children and calls child.accept(visitor).
- Extensibility - You can create a `TerraformExportVisitor`, a `ResourceOptimizationVisitor`, or a `InventoryReportVisitor` next month. You won't have to re-test or re-deploy the core S3Bucket or ComputeInstance code.

**Challenges**
- If you add a **LambdaFunction** resource, you must update the **ResourceVisitor** interface and every existing **visitor (Billing, Security, etc.).**
- If your hierarchy changes often, look into the **Acyclic Visitor** or **Reflective Visitor**