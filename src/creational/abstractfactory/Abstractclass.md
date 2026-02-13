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
JDBC driver

Each driver contains a class that implements a connection, statement and resultset.
Now we have 1+n providers, oracle, postgres, mysql etc...

Goal: Client code shouldn’t about  how Postgres, MySQL, or Oracle implements those interfaces.

