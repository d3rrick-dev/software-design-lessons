
**Flyweight pattern** 
- Is about extreme dieting for your computer's RAM.
- Used when you need to create a massive number of similar objects (think thousands or millions) without crashing the system due to memory exhaustion
- By sharing as much data as possible instead of keeping it all inside every single object.

**Problem being solved**
Imagine you’re building a video game with a massive forest. 
You want 10,000 trees. Each Tree object has:
 - A high-resolution texture (big)
 - A 3D model (big)
 - Coordinates ($x, y, z$)
 - Health points

The Flyweight pattern says: "Wait, why are we storing the same 10MB texture 10,000 times? Let's store it once and just point to it."

**The Solution: Split the State**

**1. Intrinsic**
Constant data that is the same for many objects. It stays inside the Flyweight.
The texture, the 3D mesh, the color "Green."

**2. Extrinsic**
Unique data that changes based on context. It is passed into the Flyweight from the outside.
The coordinates $(x, y)$, the current health, the age.

**In practice:**
**1. Flyweight:** 
The object that contains the Intrinsic state (the "Heavy" stuff).

**2. Flyweight Factory:**
A manager that ensures Flyweights are shared. If you ask for a "Pine Tree," it checks if it already made one. If yes, it hands you the existing one.

**3. Context (Client):** 
The part of the code that stores the Extrinsic state (the "Light" stuff, like coordinates) and passes it to the Flyweight when needed.

**When to use it:**
You shouldn't use Flyweight just because it sounds "optimized." Only use it if:

 - Your application uses a staggering number of objects. 
 - Memory storage costs are high. 
 - Most object state can be made "Intrinsic" (shared). 
 - You don't mind the code becoming a bit more complex (since you have to manage state separately).

**N.B Performance:** While Flyweight saves RAM, it might slightly increase CPU usage because you’re constantly passing parameters (Extrinsic state) into methods instead of just reading them from the object. 
It’s a classic space-vs-time trade-off.

In `Java`, we typically use a `HashMap` within a Factory to manage our shared objects.

`Java internals`
**String Interning:** When you write `String s1 = "hello";` `String s2 = "hello";`, Java doesn't create two objects. It points both to the same entry in the **String Constant Pool**.
That pool is a **Flyweight Factory**.

**Integer valueOf:**
- Java caches Integer objects for values between -128 and 127.
- Calling valueOf() returns a cached Flyweight object for these common numbers to save memory.

```shell
jshell> Integer.valueOf(200) == Integer.valueOf(200)
$1 ==> false

jshell> Integer.valueOf(10) == Integer.valueOf(10)
$2 ==> true
```
**Note:**
This is why you should always use .equals() for object comparison in Java. 
If you rely on ==, you're gambling on whether the Flyweight cache is active for that specific value!

**Practical use:**
 - **Game Development:** Rendering particles, bullets, or map tiles. 
 - **Text Editors:** Representing every single character as an object (storing the font/size as Flyweight, and position as Extrinsic). 
 - **Data Processing:** When handling millions of rows where many columns share the same repetitive categorical data.

**Example**
**Data Processing:**
Customer Rewards System handling 10 million records.
Categorical data maybe like 
* **Country** | **City** | **membership**
* Kenya | Nairobi | Platinum 
* Kenya | Nairobi | Basic    
* Kenya | Embu    | Platinum 
* Uganda | Kampala    | Platinum 

**Approach :**
- **The Flyweight** (Shared Category): Instead of a raw string, we create a Location object that stores "City" and "Country."
- **The Factory:** We use a Map to ensure we only ever have one instance of "New York, USA."
- **The Row (Context):** Each CustomerRecord object only stores a reference (a tiny 4-8 byte pointer) to that shared Location object, plus their unique data (Name, ID).

```java
// 1. The Flyweight: Stores the repetitive categorical data
class Location {
    final String city;
    final String country;

    Location(String city, String country) {
        this.city = city;
        this.country = country;
    }
}

// 2. The Factory: Returns existing Location or creates a new one
class LocationFactory {
    private static final Map<String, Location> cache = new HashMap<>();

    public static Location getLocation(String city, String country) {
        String key = city + "-" + country;
        return cache.computeIfAbsent(key, k -> new Location(city, country));
    }
}

// 3. The Data Row: Stores unique data + reference to Flyweight
class CustomerRecord {
    private String name;        // Unique
    private String email;       // Unique
    private Location location;  // SHARED (The Flyweight)

    public CustomerRecord(String name, String email, Location loc) {
        this.name = name;
        this.email = email;
        this.location = loc;
    }
}
```

**The Impact**
**Without Flyweight**: 10,000,000 rows $\times$ 100 bytes of repetitive strings $\approx$ 1 GB of RAM.
**With Flyweight:** 10,000,000 rows $\times$ 8-byte reference + a few hundred shared objects $\approx$ 80 MB of RAM.
Here memory footprint is reduced by over 90%.

**Notes**
**In a real application,** the Flyweight Pattern is the mirror image of Database Normalization. When you "normalize" a database, you are essentially creating "Flyweights" at the storage level.

```csv
locations

id(PK), country,city, region
1,  Kenya, Nairobi, EA
2,  Uganda,Kampala , EA
2,  Tanzania,Dodoma , EA


id(PK), name, email,location_id (FK)
101, Alice, alice@email.com, 1
102, Bob, bob@email.com, 1
103, Charlie, charlie@email.com, 2
```

When we query the db, When your Java application queries the database, you don't want to create $1,000,000$ Location objects if they all point to "Nairobi."
When you pull data from the DB, your Repository or DAO (Data Access Object) uses the Flyweight Factory to "hydrate" the objects.

```java
public List<User> loadUsersFromDB() {
    List<User> users = new ArrayList<>();
    var rs = db.query("SELECT * FROM users JOIN locations ON users.location_id = locations.id");

    while (rs.next()) {
        // We use the Factory to get a SHARED Location object
        var sharedLoc = LocationFactory.getLocation(
            rs.getString("city"), 
            rs.getString("country")
        );

        // We create a NEW User object (Context), but it points to the SHARED Location
        users.add(new User(rs.getString("name"), sharedLoc));
    }
    return users;
}
```

**Another usecase**
- For bigger apps Columnar Storage (e.g., Apache Parquet) is used.
- In these systems, the Flyweight pattern is baked into the file format itself using something called Dictionary Encoding. 
- It stores a small dictionary of unique values and then a long list of integers (indexes) pointing to that dictionary. 
- This allows them to compress $1$ TB of data down to $100$ GB.
- For simple CRUD app, don't over-engineer this. But the moment you see your RAM usage spiking because of "duplicate strings," the Flyweight + DB Normalization combo is your best friend.
- Consider redis and other caching mechanisms.