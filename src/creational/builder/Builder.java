void main() {
    var customer = new Customer.Builder("Alice", "Smith")
            .email("aliceexample.com")
            .age(30)
            .build();
    IO.println(customer);
}

static class Customer {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final int age;

    private Customer(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.age = builder.age;
    }

    public static class Builder {
        private final String firstName;
        private final String lastName;
        private String email = "";
        private int age = 0;

        public Builder(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }
        public Customer build() {
            if (!this.email.isEmpty() && !this.email.contains("@")) {
                throw new IllegalArgumentException("Email must contain '@'");
            }
            return new Customer(this);
        }
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}