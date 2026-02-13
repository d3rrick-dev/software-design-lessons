void main(){
    var postgres = new PostgresFactory();
    var conn = postgres.createConnection();
    conn.connect();
}

//Step 1: Abstract Factory Interface
public interface DbFactory {
    DbConnection createConnection();
    DbStatement createStatement();
    DbResultSet createResultSet();
}

//Step 2: Concrete Factories for Each Database
public class PostgresFactory implements DbFactory {
    @Override
    public DbConnection createConnection() {
        return new PostgresConnection();
    }

    @Override
    public DbStatement createStatement() {
        return new PostgresStatement();
    }

    @Override
    public DbResultSet createResultSet() {
        return new PostgresResultSet();
    }
}

public interface DbConnection {
    void connect();
    void disconnect();
}

public interface DbStatement {
    void executeQuery(String sql);
}

public interface DbResultSet {
    boolean next();
}