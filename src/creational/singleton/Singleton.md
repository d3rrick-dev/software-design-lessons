### Rational

Sometimes, there may be a need to have one and only one instance of a given class during the lifetime of an application. 

This may be due to necessity or, more often, due to the fact that only a single instance of the class is sufficient. 

For example, we may need a single database connection object in an application. 

The Singleton pattern is useful in such cases because it ensures that there exists one and only one instance of a particular object ever. 

Further, it suggests that client objects should be able to access the single instance in a consistent manner.

**Therefore:**
A class that maintains its single instance nature by itself is referred to as a Singleton class.

**Thought:**
Having an instance of the class in a global variable seems like an easy way to maintain the single instance

**How to**
1. make constructor private
2. a static method to get the instance

For a multithreaded environment, employ double checking mechanism