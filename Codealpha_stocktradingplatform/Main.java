package stocktradingplatform;

/**
 * Main.java
 * Entry point for the Stock Trading Platform application
 * Initializes the application with sample data and launches the GUI
 */

import java.util.HashMap;
import javax.swing.SwingUtilities;

public class Main {
    /**
     * Main method - Entry point of the application
     * @param args - Command line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            // Initialize stocks
            HashMap<String, Stock> stocks = initializeStocks();
            
            // Initialize user
            User user = new User("USER001", "Investor", "investor@tradingplatform.com");
            
            // Initialize portfolio
            Portfolio portfolio = new Portfolio(user.getUserId());
            
            // Load transaction history from file
            // This automatically loads previous transactions if they exist
            
            // Launch GUI on EDT
            SwingUtilities.invokeLater(() -> {
                new MainFrame(user, portfolio, stocks);
            });
            
        } catch (Exception e) {
            System.err.println("Error starting application: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Initialize available stocks with sample data
     * @return HashMap of stocks
     */
    private static HashMap<String, Stock> initializeStocks() {
        HashMap<String, Stock> stocks = new HashMap<>();
        
        // Add Indian stocks with current prices
        stocks.put("TCS", new Stock("TCS", "Tata Consultancy Services", 3500.00));
        stocks.put("INFY", new Stock("INFY", "Infosys", 1650.00));
        stocks.put("WIPRO", new Stock("WIPRO", "Wipro Limited", 425.00));
        stocks.put("RELIANCE", new Stock("RELIANCE", "Reliance Industries", 2500.00));
        stocks.put("HCL", new Stock("HCL", "HCL Technologies", 1200.00));
        
        System.out.println("=== Stock Trading Platform ===");
        System.out.println("Available Stocks initialized:");
        for (Stock stock : stocks.values()) {
            System.out.println("  - " + stock);
        }
        System.out.println();
        
        return stocks;
    }
}
