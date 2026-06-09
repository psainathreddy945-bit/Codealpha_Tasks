package stocktradingplatform;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileManager {
    private static final String TRANSACTIONS_FILE = "transactions.txt";
    private static final String PORTFOLIO_FILE = "portfolio.txt";
    
    /**
     * Save a transaction to transactions.txt
     * @param transaction - Transaction object to save
     */
    public static void saveTransaction(Transaction transaction) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TRANSACTIONS_FILE, true))) {
            bw.write(transaction.toFileFormat());
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error saving transaction: " + e.getMessage());
        }
    }
    
    /**
     * Load all transactions from transactions.txt
     * @return List of Transaction objects
     */
    public static List<Transaction> loadTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        File file = new File(TRANSACTIONS_FILE);
        
        // Create file if it doesn't exist
        if (!file.exists()) {
            return transactions;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(TRANSACTIONS_FILE))) {
            String line;
            while ((line = br.readLine()) != null && !line.trim().isEmpty()) {
                try {
                    String[] parts = line.split("\\|");
                    if (parts.length == 8) {
                        String transactionId = parts[0];
                        String userId = parts[1];
                        String stockSymbol = parts[2];
                        String transactionType = parts[3];
                        int quantity = Integer.parseInt(parts[4]);
                        double pricePerShare = Double.parseDouble(parts[5]);
                        
                        Transaction transaction = new Transaction(transactionId, userId, 
                                                            stockSymbol, transactionType, 
                                                            quantity, pricePerShare);
                        transactions.add(transaction);
                    }
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    System.err.println("Error parsing transaction line: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading transactions: " + e.getMessage());
        }
        
        return transactions;
    }
    
    /**
     * Save portfolio details to portfolio.txt
     * @param userId - User ID
     * @param portfolio - Portfolio object
     * @param currentPrices - Current prices map
     */
    public static void savePortfolio(String userId, Portfolio portfolio, 
                                HashMap<String, Double> currentPrices) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PORTFOLIO_FILE, true))) {
            bw.write("=== PORTFOLIO FOR USER: " + userId + " ===");
            bw.newLine();
            
            HashMap<String, Integer> holdings = portfolio.getHoldings();
            for (String symbol : holdings.keySet()) {
                int quantity = holdings.get(symbol);
                double avgPrice = portfolio.getAverageCostPrice(symbol);
                double currentPrice = currentPrices.getOrDefault(symbol, 0.0);
                
                bw.write(symbol + "|" + quantity + "|" + avgPrice + "|" + currentPrice);
                bw.newLine();
            }
            
            double portfolioValue = portfolio.calculatePortfolioValue(currentPrices);
            double profitLoss = portfolio.calculateProfitLoss(currentPrices);
            
            bw.write("SUMMARY|" + portfolio.getTotalInvestedAmount() + "|" + 
                    portfolioValue + "|" + profitLoss);
            bw.newLine();
            bw.write("---");
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error saving portfolio: " + e.getMessage());
        }
    }
    
    /**
     * Clear all transaction data (useful for testing)
     */
    public static void clearTransactionHistory() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TRANSACTIONS_FILE))) {
            bw.flush();
        } catch (IOException e) {
            System.err.println("Error clearing transactions: " + e.getMessage());
        }
    }
    
    /**
     * Clear portfolio data
     */
    public static void clearPortfolioHistory() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PORTFOLIO_FILE))) {
            bw.flush();
        } catch (IOException e) {
            System.err.println("Error clearing portfolio: " + e.getMessage());
        }
    }
    
    /**
     * Check if transactions file exists
     * @return true if file exists
     */
    public static boolean transactionsFileExists() {
        return new File(TRANSACTIONS_FILE).exists();
    }
    
    /**
     * Get transaction count
     * @return number of transactions
     */
    public static int getTransactionCount() {
        return loadTransactions().size();
    }
}
