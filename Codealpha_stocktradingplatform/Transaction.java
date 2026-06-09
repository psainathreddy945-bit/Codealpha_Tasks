package stocktradingplatform;

/**
 * Transaction.java
 * Represents a transaction (buy/sell)
 * Uses OOP concepts: Encapsulation
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private String transactionId;     // Unique transaction ID
    private String userId;            // User who made the transaction
    private String stockSymbol;       // Stock symbol
    private String transactionType;   // "BUY" or "SELL"
    private int quantity;             // Number of shares
    private double pricePerShare;     // Price at time of transaction
    private double totalAmount;       // Total transaction amount
    private LocalDateTime timestamp;  // When transaction occurred
    
    /**
     * Constructor to initialize a transaction
     * @param transactionId - Unique transaction ID
     * @param userId - User ID
     * @param stockSymbol - Stock symbol
     * @param transactionType - "BUY" or "SELL"
     * @param quantity - Number of shares
     * @param pricePerShare - Price per share
     */
    public Transaction(String transactionId, String userId, String stockSymbol, 
                      String transactionType, int quantity, double pricePerShare) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.stockSymbol = stockSymbol;
        this.transactionType = transactionType;
        this.quantity = quantity;
        this.pricePerShare = pricePerShare;
        this.totalAmount = quantity * pricePerShare;
        this.timestamp = LocalDateTime.now();
    }
    
    // Getters - Encapsulation
    public String getTransactionId() {
        return transactionId;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public String getStockSymbol() {
        return stockSymbol;
    }
    
    public String getTransactionType() {
        return transactionType;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public double getPricePerShare() {
        return pricePerShare;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return timestamp.format(formatter);
    }
    
    /**
     * Convert transaction to file format
     * @return formatted string for file storage
     */
    public String toFileFormat() {
        return transactionId + "|" + userId + "|" + stockSymbol + "|" + 
               transactionType + "|" + quantity + "|" + pricePerShare + "|" + 
               totalAmount + "|" + getFormattedTimestamp();
    }
    
    @Override
    public String toString() {
        return getFormattedTimestamp() + " - " + transactionType + " " + quantity + 
               " shares of " + stockSymbol + " @ $" + String.format("%.2f", pricePerShare) + 
               " = $" + String.format("%.2f", totalAmount);
    }
}
