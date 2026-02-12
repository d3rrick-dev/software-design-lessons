
### Rational
Subclasses may ovveride parent behaviour and offer something different.

##### Imagine I want to instantiate an object from those subclass (app object)
I may not know which class to instantiate because of:
1. The state of the application
2. App config settings (e.g via .properties file)
3. Changing requirements

##### I want to avoid:
 - coupling between objects(i.e imagine being coupled to one of the subclasses)
 - changes in my code because the dependent classes changed.
 - if different classes in the hierachy needs diverse manners, my object construction may be more difficult.
 - I need to be fully aware of the functionality offered by each class for me to gain the best out of it.

##### Factory method:
Recommends encapsulating the functionality required, to select and instantiate an appropriate class, inside a designated method referred to as a `factory method`.
This factory method:
 - Selects an appropriate class from a class hierarchy based on the application context and other influencing factor
 - Instantiates the selected class and returns it as an instance of the parent class type

##### Benefits:
1. I do not need to care soo much about varying classes.
2. The factory method will deal with `encapsulation`, details needed to instantiate selected class.
3. Since the factory method will return the parent class as the return type, I do not have to worry soo much about the subclasses.

##### How to:
 - via an interface or abstract class.
 - Concrete classes with default implementation with factory method in it. then different subclasses can ovveride the factory method.

##### TODO:
- Add more example + designs

