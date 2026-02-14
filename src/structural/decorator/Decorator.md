
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