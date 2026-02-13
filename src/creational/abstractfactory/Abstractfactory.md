### Recap

### Factory pattern
- We have an hierachy of subclasses.
- A factory method understands this hierachy and using abstraction, builds the necessary object.

### Abstract Factory pattern
 - Provides an interface to produce a family of objects. (interfaces or abstract classes)

### usecases:
    - dealing with related dependant classes
    - A group of concrete factory classes that implements the interface provided by the abstract factory class.

### When needed
 - A client object wants to create an instance of one of a suite of related, 
    dependent classes without having to know which specific concrete class is to be instantiated

**Without abstract factory** the class selection criteria, needs to be present when instantiating objects.
Therefore, it removes this duplication by providing an interface to create such instances.

Client objects need not know the concrete classes.


### Deep dive
Example 1:

JDBC driver

Each driver contains a class that implements a connection, statement and resultset.
Now we have 1+n providers, oracle, postgres, mysql etc...

Goal: Client code shouldn’t about  how Postgres, MySQL, or Oracle implements those interfaces.

Example 2:
Customer data management system.

Functional requirements.
1. Validate and save input data(account, address, credit card)
2. function in both local and remote modes.
3. In remote mode, use REST api and save data to remote central server

Example 3:
- A company does web hosting on windows and Unix platforms
- It has three hosting packages (Basic, premium and premium plus)
- Design an app to query features offered by the company.

**N.B**
 * Patterns like Abstract Factory are conceptual tools, meant to teach encapsulation of family-of-objects problems.
 * They often create many small classes in examples to make the relationships explicit.
 * Real systems usually combine classes, use configuration, or data-driven designs to avoid this combinatorial explosion.

e.g
```java
record HostingPackage(String platform, String name, List<String> features) {}

class HostingService {
    private final List<HostingPackage> packages = List.of(
        new HostingPackage("Windows", "Basic", List.of("1 GB disk", "1 website")),
        new HostingPackage("Windows", "Premium", List.of("10 GB disk", "5 websites")),
        new HostingPackage("Unix", "Basic", List.of("2 GB disk", "2 websites")),
        new HostingPackage("Unix", "Premium", List.of("15 GB disk", "5 websites"))
    );

    public void showFeatures(String platform, String name) {
        packages.stream()
                .filter(p -> p.platform().equalsIgnoreCase(platform)
                         && p.name().equalsIgnoreCase(name))
                .forEach(p -> System.out.println(p.features()));
    }
}
```

