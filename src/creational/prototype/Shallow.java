void main() {
    var person = new Person("derrick", "bmw");
    var person1 = (Person) person.clone();

    IO.println(person.getCar().getName());
    person1.getCar().setName("civic");
    person1.setName("ups");
    IO.println(person.getCar().getName()); // civic
    IO.println(person.getName()); // does not change
}
static class Person implements Cloneable {
    //Lower-level object
    private Car car;

    private String name;
    public Car getCar() {
        return car;
    }
    public String getName() {
        return name;
    }
    public void setName(String s) {
        name = s;
    }
    public Person(String s, String t) {
        name = s;
        car = new Car(t);
    }
    public Object clone() {
        //shallow copy
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
static class Car {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String s) {
        name = s;
    }

    public Car(String s) {
        name = s;
    }
}