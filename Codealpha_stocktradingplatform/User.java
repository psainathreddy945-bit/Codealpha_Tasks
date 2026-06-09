package stocktradingplatform;

/**
 * User.java
 * Represents a user of the trading platform
 * Uses OOP concepts: Encapsulation, Data Protection
 */

public class User {
    private String userId;        // Unique user identifier
    private String username;      // Username
    private String email;         // User email
    private double accountBalance; // Current account balance
    private static final double INITIAL_BALANCE = 100000.0; // Initial balance for new users
    
    /**
     * Constructor to initialize a user
     * @param userId - Unique user ID
     * @param username - Username
     * @param email - Email address
     */
    public User(String userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.accountBalance = INITIAL_BALANCE;
    }
    
    // Getters - Encapsulation
    public String getUserId() {
        return userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public double getAccountBalance() {
        return accountBalance;
    }
    
    // Balance operations
    
    /**
     * Deposit money to account
     * @param amount - Amount to deposit
     * @return true if successful, false otherwise
     */
    public boolean deposit(double amount) {
        if (amount <= 0) {
            return false;
        }
        this.accountBalance += amount;
        return true;
    }
    
    /**
     * Withdraw money from account
     * @param amount - Amount to withdraw
     * @return true if successful, false otherwise
     */
    public boolean withdraw(double amount) {
        if (amount <= 0 || amount > accountBalance) {
            return false;
        }
        this.accountBalance -= amount;
        return true;
    }
    
    /**
     * Check if user has sufficient balance
     * @param amount - Amount to check
     * @return true if balance is sufficient
     */
    public boolean hasSufficientBalance(double amount) {
        return accountBalance >= amount;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", accountBalance=" + String.format("%.2f", accountBalance) +
                '}';
    }
}
