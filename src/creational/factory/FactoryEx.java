void main () {
    var factory = new LoggerFactory();
    var logger = factory.getLogger();
    logger.log("hello world");
}

interface Logger {
    public void log(String message);
}

static class ConsoleLogger implements Logger {
    public void log(String message) {
        System.out.println("Console: " + message);
    }
}

static class FileLogger implements Logger {
    public void log(String message) {
        System.out.println("File: " + message);
    }
}

static class DBLogger implements Logger {
    public void log(String message) {
        System.out.println("DB: " + message);
    }
}

static class LoggerFactory {
    public boolean isFileLoggingEnabled() {
        // read from a property file
        // return a bool based on the config
        return true;
    }

    public Logger getLogger() {
        if (isFileLoggingEnabled()) {
            return new FileLogger();
        } else {
            return new ConsoleLogger();
        }
    }
}