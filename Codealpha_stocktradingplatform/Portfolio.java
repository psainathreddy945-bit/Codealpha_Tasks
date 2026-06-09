package stocktradingplatform;

/**
 * Portfolio.java
 * Represents a user's stock portfolio
 * Uses OOP concepts: Encapsulation, Collections
 */

import java.util.HashMap;

public class Portfolio {
    private String userId;                        // User ID
    private HashMap<String, Integer> holdings;    // Stock symbol -> quantity
    private HashMap<String, Double> avgCostPrice; // Stock symbol -> average cost price
    private double totalInvestedAmount;           // Total amount invested
    
    /**
     * Constructor to initialize portfolio
     * @param userId - User ID
     */
    public Portfolio(String userId) {
        this.userId = userId;
        this.holdings = new HashMap<>();
        this.avgCostPrice = new HashMap<>();
        this.totalInvestedAmount = 0.0;
    }
    
    // Getters
    public String getUserId() {
        return userId;
    }
    
    public HashMap<String, Integer> getHoldings() {
        return holdings;
    }
    
    public int getQuantity(String stockSymbol) {
        return holdings.getOrDefault(stockSymbol, 0);
    }
    
    public double getAverageCostPrice(String stockSymbol) {
        return avgCostPrice.getOrDefault(stockSymbol, 0.0);
    }
    
    public double getTotalInvestedAmount() {
        return totalInvestedAmount;
    }
    
    /**
     * Buy stocks - add to portfolio
     * @param stockSymbol - Stock symbol
     * @param quantity - Number of shares
     * @param currentPrice - Current price per share
     */
    public void buyStock(String stockSymbol, int quantity, double currentPrice) {
        int currentQuantity = holdings.getOrDefault(stockSymbol, 0);
        double currentAvgPrice = avgCostPrice.getOrDefault(stockSymbol, 0.0);
        
        // Calculate new average cost price
        double newAvgPrice = (currentQuantity * currentAvgPrice + quantity * currentPrice) / 
                            (currentQuantity + quantity);
        
        holdings.put(stockSymbol, currentQuantity + quantity);
        avgCostPrice.put(stockSymbol, newAvgPrice);
        totalInvestedAmount += quantity * currentPrice;
    }
    
    /**
     * Sell stocks - remove from portfolio
     * @param stockSymbol - Stock symbol
     * @param quantity - Number of shares
     * @return true if sell is successful, false otherwise
     */
    public boolean sellStock(String stockSymbol, int quantity) {
        int currentQuantity = holdings.getOrDefault(stockSymbol, 0);
        
        // Check if user has enough shares
        if (currentQuantity < quantity) {
            return false;
        }
        
        double avgPrice = avgCostPrice.getOrDefault(stockSymbol, 0.0);
        holdings.put(stockSymbol, currentQuantity - quantity);
        totalInvestedAmount -= quantity * avgPrice;
        
        // Remove from portfolio if quantity becomes 0
        if (holdings.get(stockSymbol) == 0) {
            holdings.remove(stockSymbol);
            avgCostPrice.remove(stockSymbol);
        }
        
        return true;
    }
    
    /**
     * Calculate portfolio value based on current prices
     * @param currentPrices - Map of stock symbol to current price
     * @return total portfolio value
     */
    public double calculatePortfolioValue(HashMap<String, Double> currentPrices) {
        double totalValue = 0.0;
        for (String symbol : holdings.keySet()) {
            int quantity = holdings.get(symbol);
            double price = currentPrices.getOrDefault(symbol, 0.0);
            totalValue += quantity * price;
        }
        return totalValue;
    }
    
    /**
     * Calculate profit/loss
     * @param currentPrices - Map of stock symbol to current price
     * @return profit/loss amount
     */
    public double calculateProfitLoss(HashMap<String, Double> currentPrices) {
        double currentValue = calculatePortfolioValue(currentPrices);
        return currentValue - totalInvestedAmount;
    }
    
    /**
     * Calculate profit/loss percentage
     * @param currentPrices - Map of stock symbol to current price
     * @return profit/loss percentage
     */
    public double calculateProfitLossPercentage(HashMap<String, Double> currentPrices) {
        if (totalInvestedAmount == 0) return 0.0;
        double profitLoss = calculateProfitLoss(currentPrices);
        return (profitLoss / totalInvestedAmount) * 100;
    }
    
    /**
     * Get portfolio details as string
     * @param currentPrices - Map of stock symbol to current price
     * @return formatted portfolio details
     */
    public String getPortfolioDetails(HashMap<String, Double> currentPrices) {
        StringBuilder details = new StringBuilder();
        details.append("=== PORTFOLIO FOR USER: ").append(userId).append(" ===\n");
        details.append("Holdings:\n");
        
        for (String symbol : holdings.keySet()) {
            int quantity = holdings.get(symbol);
            double avgPrice = avgCostPrice.get(symbol);
            double currentPrice = currentPrices.getOrDefault(symbol, 0.0);
            double value = quantity * currentPrice;
            double gain = quantity * (currentPrice - avgPrice);
            
            details.append(symbol).append(": ")
                   .append(quantity).append(" shares @ $")
                   .append(String.format("%.2f", avgPrice))
                   .append(" (Current: $").append(String.format("%.2f", currentPrice)).append(")")
                   .append(" = $").append(String.format("%.2f", value))
                   .append(" (Gain: $").append(String.format("%.2f", gain)).append(")\n");
        }
        
        double portfolioValue = calculatePortfolioValue(currentPrices);
        double profitLoss = calculateProfitLoss(currentPrices);
        double profitLossPercent = calculateProfitLossPercentage(currentPrices);
        
        details.append("\nTotal Invested: $").append(String.format("%.2f", totalInvestedAmount)).append("\n");
        details.append("Current Value: $").append(String.format("%.2f", portfolioValue)).append("\n");
        details.append("Profit/Loss: $").append(String.format("%.2f", profitLoss))
               .append(" (").append(String.format("%.2f", profitLossPercent)).append("%)\n");
        
        return details.toString();
    }
}
