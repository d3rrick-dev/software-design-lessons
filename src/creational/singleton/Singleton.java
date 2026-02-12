void main () {
 var logger = LoggerSingleton.getInstance();
 var logger2 = LoggerSingleton.getInstance();
 IO.println(logger2 == logger); // true
}

static class LoggerSingleton {
    private static volatile LoggerSingleton instance;
    private LoggerSingleton() {}
    public static LoggerSingleton getInstance() {
        LoggerSingleton result = instance;
        if (result == null) {
            synchronized (LoggerSingleton.class) {
                result = instance;
                if (result == null) {
                    instance = result = new LoggerSingleton();
                }
            }
        }
        return result;
    }
}
