package stocktradingplatform;

/**
 * Stock.java
 * Represents a stock in the trading platform
 * Uses OOP concepts: Encapsulation
 */

public class Stock {
    private String symbol;        // Stock ticker symbol
    private String companyName;   // Full company name
    private double currentPrice;  // Current price of the stock
    private double previousPrice; // Previous price for tracking changes
    
    /**
     * Constructor to initialize a stock
     * @param symbol - Stock ticker symbol
     * @param companyName - Company name
     * @param currentPrice - Initial stock price
     */
    public Stock(String symbol, String companyName, double currentPrice) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.currentPrice = currentPrice;
        this.previousPrice = currentPrice;
    }
    
    // Getters - Encapsulation
    public String getSymbol() {
        return symbol;
    }
    
    public String getCompanyName() {
        return companyName;
    }
    
    public double getCurrentPrice() {
        return currentPrice;
    }
    
    public double getPreviousPrice() {
        return previousPrice;
    }
    
    // Setters - Encapsulation
    public void setCurrentPrice(double price) {
        this.previousPrice = this.currentPrice;
        this.currentPrice = price;
    }
    
    /**
     * Calculate percentage change in price
     * @return percentage change
     */
    public double getPriceChangePercentage() {
        if (previousPrice == 0) return 0;
        return ((currentPrice - previousPrice) / previousPrice) * 100;
    }
    
    /**
     * Get absolute price change
     * @return price change amount
     */
    public double getPriceChange() {
        return currentPrice - previousPrice;
    }
    
    @Override
    public String toString() {
        return symbol + " - " + companyName + " ($" + String.format("%.2f", currentPrice) + ")";
    }
}
