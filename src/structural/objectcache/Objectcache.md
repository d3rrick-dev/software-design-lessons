**Object Cache Pattern – Summary**

**Purpose**

- The Object Cache pattern is used to keep a copy of **expensive-to-create** objects in memory to provide faster response times for repeated requests. 
- The cache is transparent to the client, and helps reduce redundant processing or database access.

**Problem**
- Repeatedly reading or constructing the same object can be time-consuming. 
- Redundant operations (like multiple database updates) waste resources. 
- A strategy is needed to manage how many objects to cache and how long they are kept.

**Solution**

Maintain a cache (in-memory collection) of frequently used objects.

**On request:**
1.	Check if object exists in the cache.
2.	If yes, return the cached object.
3.	If no, retrieve/create it, then add it to the cache.

Implement a cache replacement policy (e.g., Least Recently Used, Least Frequently Used) when the cache is full.

**Example**: Item Activation

**Scenario**: Department store scans product bar codes to activate items.

**Classes:**
- **ItemCache** – maintains recently activated items.
- **ItemManager** – uses ItemCache and DB access to activate items.
- **Client** – interacts with ItemManager.

**ItemCache Example**
```java
public class ItemCache {
private final static int MAX_CACHE_SIZE = 5;
Vector<String> cache;

    public ItemCache() {
        cache = new Vector<>();
    }

    public String getItem(String code) {
        int pos = cache.indexOf(code);
        return (pos != -1) ? cache.get(pos) : null;
    }

    public void addItem(String code) {
        if (cache.size() == MAX_CACHE_SIZE) {
            cache.remove(0); // remove LRU item
        }
        cache.add(code);
    }
}

// ItemManager Example

public class ItemManager {
ItemCache cache;
DBManager manager;

    public ItemManager() {
        cache = new ItemCache();
        manager = new DBManager();
    }

    public void activate(String code) {
        if (cache.getItem(code) != null) {
            System.out.println("Item Already Activated - cache");
        } else if (manager.isActiveItem(code)) {
            System.out.println("Item Already Activated - DB Access");
        } else {
            manager.activateItem(code);
            System.out.println("Item Activated successfully");
            cache.addItem(code);
        }
    }
}
```
**Behavior:**
•	First activation of an item adds it to cache.
•	Repeated activation checks the cache first and avoids redundant DB updates.
•	Cache uses a simple LRU replacement policy.

**Benefits**
1.	Faster access for repeated object requests.
2.	Reduces redundant computation or database operations.
3.	Transparent to clients – no changes needed in client code.

**Cache Management Considerations**
•	Maximum cache size
•	Replacement policy (LRU, LFU, random)
•	Object expiration or invalidation criteria
•	Frequency counters for LFU strategies

**Real-World Analogy**
•	Browser caches recently visited web pages.
•	Database connection pools keep frequently used connections ready.
•	In-memory session stores for web applications.