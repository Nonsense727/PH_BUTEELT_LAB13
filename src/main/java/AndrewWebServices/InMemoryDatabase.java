package AndrewWebServices;

public class InMemoryDatabase extends Database {
    @Override
    public int getPassword(String accountName) {
        // Immediate response without the 10-second delay
        if ("Scotty".equals(accountName)) {
            return 17214;
        }
        return 0;
    }
}