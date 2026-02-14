void main() {
    var billing = new BillingVisitor();
    var security = new SecurityVisitor();

    var computeResource = new ComputeInstance(16, 256, "RUNNING");
    var s3 = new S3Bucket(100, true);

    computeResource.accept(billing);
    s3.accept(billing);
    IO.println(billing.getTotalCost()); // total cost

    computeResource.accept(security);
    s3.accept(security);
    IO.println(security.getViolations()); // all violations
}


public interface ResourceVisitor {
    void visit(ComputeInstance instance);
    void visit(S3Bucket bucket);
}


// Element Interface
public interface CloudResource {
    void accept(ResourceVisitor visitor);
}

// Concrete Elements
record ComputeInstance(int cpuCores, int ramGb, String status) implements CloudResource {
    @Override
    public void accept(ResourceVisitor visitor) {
        visitor.visit(this);
    }
}

record S3Bucket(long storageBytes, boolean isPublic) implements CloudResource {
    @Override
    public void accept(ResourceVisitor visitor) {
        visitor.visit(this);
    }
}

// concrete visitors implementations
record BillingVisitor() implements ResourceVisitor {
    static double totalCost = 0;
    @Override
    public void visit(ComputeInstance instance) {
        // Simple linear cost model: $0.05 per core per hour if running
        if ("RUNNING".equals(instance.status)) {
            totalCost += instance.cpuCores * 0.05;
        }
    }

    @Override
    public void visit(S3Bucket bucket) {
        // $0.02 per GB
        totalCost += (bucket.storageBytes / 1e9) * 0.02;
    }
    public double getTotalCost() { return totalCost; }
}

record SecurityVisitor() implements ResourceVisitor {
    private static final List<String> violations = new ArrayList<>();

    @Override
    public void visit(ComputeInstance instance) {
        if (instance.ramGb > 128) {
            violations.add("High-spec instance requires manual approval.");
        }
    }

    @Override
    public void visit(S3Bucket bucket) {
        if (bucket.isPublic) {
            violations.add("Public S3 Bucket detected! Security risk.");
        }
    }

    public List<String> getViolations() { return violations; }
}
