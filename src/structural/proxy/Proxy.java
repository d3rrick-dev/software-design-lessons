void main() throws InterruptedException {
    // example 1
    var img = new ImageProxy("photo.jpg");
    img.display(); // loads only when needed

    // ecxample 2
//    var db = new DatabaseProxy("guest");//Access Denied
    var db = new DatabaseProxy("admin");
    db.query("DELETE FROM users");

    // ex 3
    var printer = new PrinterProxy("192.168.1.100");
    printer.print("Hello World");

    //ex 4
    var service = new ExpensiveServiceProxy();
    var updated = service.fetchData("mydoc");
    var update1 = service.fetchData("mydoc"); // from cache
    var update2 = service.fetchData("mydo1"); // not from cache

    IO.println(updated);
    IO.println(update1);
    IO.println(update2);

}

//Virtual Proxy
// - Immediately, you show a placeholder
// - Real image loads lazily
// - Only blocks when you actually need to display it

interface Image {
    void display() throws InterruptedException;
}

static class RealImage implements Image {
    private final String fileName;
    public RealImage(String fileName) throws InterruptedException {
        this.fileName = fileName;
        loadFromDisk();
    }

    private void loadFromDisk() throws InterruptedException {
        Thread.sleep(1000);
        IO.println("Loading " + fileName);
    }

    @Override
    public void display() {
        IO.println("Displaying " + fileName);
    }
}

static class ImageProxy implements Image {
    private RealImage realImage;
    private final String filename;

    public ImageProxy(String filename) {
        this.filename = filename;
    }

    public void display() throws InterruptedException {
        if (realImage == null) {
            IO.println("Showing placeholder for " + filename); // immediate feedback
            realImage = new RealImage(filename); // lazy load or on another thread
        }
        realImage.display();
    }
}



// Example 2
// Protection Proxy controls access.
interface Database {
    void query(String sql);
}

static class RealDatabase implements Database {
    public void query(String sql) {
        IO.println("Executing: " + sql);
    }
}

static class DatabaseProxy implements Database {
    private final RealDatabase realDatabase = new RealDatabase();
    private final String role;

    public DatabaseProxy(String role) {
        this.role = role;
    }

    public void query(String sql) {
        if (!role.equals("admin")) {
            throw new RuntimeException("Access Denied");
        }
        realDatabase.query(sql);
    }
}

//example 3
// Remote Proxy (Distributed Systems)
interface Printer {
    void print(String document);
}

static class RealPrinter implements Printer {
    public void print(String document) {
        IO.println("Printing: " + document);
    }
}

static class PrinterProxy implements Printer {
    private final String serverAddress;
    private RealPrinter printer;

    public PrinterProxy(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public void print(String document) {
        IO.println("Sending print request to server at " + serverAddress);
        if (printer == null) printer = new RealPrinter(); // could be remote call
        printer.print(document);
    }
}

//Smart Proxy
// Suppose you have an expensive computation or web service call
// Proxy caches results so repeated calls are faster
// improves performance transparently.

interface Service {
    String fetchData(String document);
}

static class RealService implements Service {
    @Override
    public String fetchData(String document) {
        IO.println("Fetching data from " + document);
        return "updated: " + document;
    }
}

class ExpensiveServiceProxy implements Service {
    private RealService realService;
    private final Map<String, String> cache = new HashMap<>();

    @Override
    public String fetchData(String key) {
        if (cache.containsKey(key)) return cache.get(key);
        if (realService == null) realService = new RealService();
        var result = realService.fetchData(key);
        cache.put(key, result);
        return result;
    }
}