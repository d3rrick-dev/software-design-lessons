import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

void main() {
    //based on some precondition i.e pinging service

    var mode = "remote"; // or local
    var factory = CustomerFactoryProvider.getFactory(mode);
    var service = factory.createCustomerService();

    var account = new Account("Derrick", "Mutwiri");
    var address = new Address("Nairobi Street", "Nairobi");
    var card = new CreditCard(
            "VISA",
            "4111111111111111",
            "12",
            "2030"
    );

    try {
        service.validate(account, address, card);
        service.save(account, address, card);
    } catch (Exception e) {
        IO.println("Error: " + e.getMessage());
    }
}


// domain objects
record Account(String firstName, String lastName) {}
record Address(String address, String city) {}
record CreditCard(String cardType, String cardNumber,
                  String expirationMonth, String expirationYear) {}


// factories

// util class
static class CustomerFactoryProvider {
    public static CustomerServiceFactory getFactory(String mode) {
        if ("remote".equalsIgnoreCase(mode)) {
            return new RemoteCustomerServiceFactory();
        }
        return new LocalCustomerServiceFactory();
    }
}

// main interface
interface CustomerServiceFactory {
    CustomerService createCustomerService();
}

// concrete impl
static class LocalCustomerServiceFactory implements CustomerServiceFactory {
    public CustomerService createCustomerService() {
        return new LocalCustomerService();
    }
}

static class RemoteCustomerServiceFactory implements CustomerServiceFactory{
        public CustomerService createCustomerService() {
            return new RemoteCustomerService();
        }
}

interface CustomerService {
    void validate(Account account, Address address, CreditCard card) throws Exception;
    void save(Account account, Address address, CreditCard card) throws Exception;
}

static class LocalCustomerService implements CustomerService {

    @Override
    public void validate(Account a, Address ad, CreditCard c) {
        IO.println("Validating locally..."); //could be external
    }

    @Override
    public void save(Account a, Address ad, CreditCard c) {
        IO.println("Saving locally...");
    }
}

static class RemoteCustomerService implements CustomerService {
    private final HttpClient client = HttpClient.newHttpClient();
    private final String baseUrl = "http://localhost:8080/api/customers";

    @Override
    public void validate(Account a, Address ad, CreditCard c) {
        try {
            var body = """
                {
                  "firstName": "%s",
                  "lastName": "%s"
                }
                """.formatted(a.firstName(), a.lastName());

            var request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/validate"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            var resp = client.send(request, HttpResponse.BodyHandlers.ofString());
            IO.println(resp.body()); // handle failure, assuming success
            IO.println("Validated via REST call");

        } catch (Exception e) {
            throw new ServiceUnavailableException("Remote validation failed", e);
        }
    }

    @Override
    public void save(Account a, Address ad, CreditCard c) {
        try {
            var request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/save"))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();

            client.send(request, HttpResponse.BodyHandlers.ofString());
            IO.println("Saved via REST call");

        } catch (Exception e) {
            throw new ServiceUnavailableException("Remote save failed", e);
        }
    }
}

static class ServiceUnavailableException extends RuntimeException {
    public ServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}