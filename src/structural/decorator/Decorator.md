
**Decorator - f(g(h(x)))**  

changes the structure of the ovbject graph.
Like building a layered structure

**It’s about:**
 * Wrapping
 * Composing
 * Building object layers
 * Maintaining the same interface

**Example**
```
ComplianceCheckDecorator
    → FraudCheckDecorator
        → RiskAdjustmentDecorator
            → BaseLoanProcessor
```

**Imagine:**
 * Some markets require stricter compliance
 * Some partners disable fraud checks
 * Some products need dynamic risk pricing
 * BNPL uses different interest rules

**This design allows:**
 * Feature toggling
 * Product configuration
 * A/B testing risk models
 * Market-specific compliance
 * Partner-specific customization

In a real system:
This is discouraged:
```java
    var processor = new ComplianceDecorator(new FraudCheckDecorator(new BaseLoanProcessor()));

```
**Because:**
 * It's hard to read
 * Hard to test in isolation
 * Hard to change order
 * Hard to inject dependencies
 * Violates clean configuration separation

**How to:**
```java

// ex1
@Configuration
public class LoanProcessingConfig {

    @Bean
    public LoanProcessor loanProcessor() {
        LoanProcessor base = new BaseLoanProcessor();
        LoanProcessor fraud = new FraudCheckDecorator(base);
        LoanProcessor compliance = new ComplianceDecorator(fraud);
        return compliance;
    }
}

// usage via Dependency Injection
    @Autowired
    private LoanProcessor processor;

// ex2

public class LoanProcessorFactory {
    public static LoanProcessor create(boolean fraudEnabled, boolean complianceEnabled) {
        var processor = new BaseLoanProcessor();
        if (fraudEnabled) {
            processor = new FraudCheckDecorator(processor);
        }
        if (complianceEnabled) {
            processor = new ComplianceDecorator(processor);
        }
        return processor;
    }
}

// ex3
List<LoanProcessor> steps = List.of(new ComplianceDecorator(), new FraudCheckDecorator());
var processor = new BaseLoanProcessor();
for (LoanProcessor step : steps) {
    processor = step.wrap(processor);
}
```

**Reason:**
* Composition is separated from usage
* Wiring is handled by DI container
* Order is configurable
* Logic is testable independently
