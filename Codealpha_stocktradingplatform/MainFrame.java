package stocktradingplatform;

/**
 * MainFrame.java
 * Main GUI for the Stock Trading Platform
 * Uses Swing components and OOP concepts for UI management
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

public class MainFrame extends JFrame {
    private User currentUser;
    private Portfolio portfolio;
    private HashMap<String, Stock> stocks;
    private List<Transaction> transactions;
    
    // GUI Components
    private JTabbedPane tabbedPane;
    private JTable stockTable;
    private JTable portfolioTable;
    private JTable transactionTable;
    private JLabel balanceLabel;
    private JLabel portfolioValueLabel;
    private JLabel profitLossLabel;
    private JTextField searchField;
    private JComboBox<String> stockComboBox;
    private JTextField quantityField;
    
    /**
     * Constructor to initialize the main frame
     * @param user - Current user
     * @param portfolio - User's portfolio
     * @param stocks - Available stocks
     */
    public MainFrame(User user, Portfolio portfolio, HashMap<String, Stock> stocks) {
        this.currentUser = user;
        this.portfolio = portfolio;
        this.stocks = stocks;
        this.transactions = FileManager.loadTransactions();
        
        // Frame setup
        setTitle("Stock Trading Platform - " + user.getUsername());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setResizable(true);
        
        initializeComponents();
        setVisible(true);
        
        // Start price update thread
        startPriceUpdateThread();
    }
    
    /**
     * Initialize all GUI components
     */
    private void initializeComponents() {
        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Create top panel with balance info
        JPanel topPanel = createTopPanel();
        mainPanel.add(topPanel, BorderLayout.NORTH);
        
        // Create tabbed pane for different sections
        tabbedPane = new JTabbedPane();
        
        // Add tabs
        tabbedPane.addTab("Stock Market", createStockMarketPanel());
        tabbedPane.addTab("Portfolio", createPortfolioPanel());
        tabbedPane.addTab("Transaction History", createTransactionHistoryPanel());
        tabbedPane.addTab("Trading", createTradingPanel());
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // Create bottom panel with exit button
        JPanel bottomPanel = createBottomPanel();
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    /**
     * Create top panel with account information
     * @return JPanel with account info
     */
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(new Color(240, 240, 240));
        
        // Balance label
        balanceLabel = new JLabel("Account Balance: $" + 
                                 String.format("%.2f", currentUser.getAccountBalance()));
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        topPanel.add(balanceLabel);
        
        // Portfolio value label
        HashMap<String, Double> currentPrices = getCurrentPrices();
        double portfolioValue = portfolio.calculatePortfolioValue(currentPrices);
        portfolioValueLabel = new JLabel("Portfolio Value: $" + String.format("%.2f", portfolioValue));
        portfolioValueLabel.setFont(new Font("Arial", Font.BOLD, 14));
        topPanel.add(portfolioValueLabel);
        
        // Profit/Loss label
        double profitLoss = portfolio.calculateProfitLoss(currentPrices);
        double profitLossPercent = portfolio.calculateProfitLossPercentage(currentPrices);
        profitLossLabel = new JLabel("Profit/Loss: $" + String.format("%.2f", profitLoss) + 
                                    " (" + String.format("%.2f", profitLossPercent) + "%)");
        profitLossLabel.setFont(new Font("Arial", Font.BOLD, 14));
        profitLossLabel.setForeground(profitLoss >= 0 ? Color.GREEN : Color.RED);
        topPanel.add(profitLossLabel);
        
        // User info
        JLabel userLabel = new JLabel("User: " + currentUser.getUsername());
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        topPanel.add(userLabel);
        
        return topPanel;
    }
    
    /**
     * Create stock market panel showing all available stocks
     * @return JPanel for stock market
     */
    private JPanel createStockMarketPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.add(new JLabel("Search Stock:"));
        searchField = new JTextField(15);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchStock());
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        
        panel.add(searchPanel, BorderLayout.NORTH);
        
        // Create stock table
        stockTable = new JTable();
        updateStockTable();
        
        JScrollPane scrollPane = new JScrollPane(stockTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Create portfolio panel showing user's holdings
     * @return JPanel for portfolio
     */
    private JPanel createPortfolioPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Portfolio table
        portfolioTable = new JTable();
        updatePortfolioTable();
        
        JScrollPane scrollPane = new JScrollPane(portfolioTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Create transaction history panel
     * @return JPanel for transaction history
     */
    private JPanel createTransactionHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Transaction table
        transactionTable = new JTable();
        updateTransactionTable();
        
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Refresh button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> updateTransactionTable());
        buttonPanel.add(refreshButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Create trading panel for buy/sell operations
     * @return JPanel for trading
     */
    private JPanel createTradingPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Buy panel
        JPanel buyPanel = createBuyPanel();
        panel.add(buyPanel);
        
        // Sell panel
        JPanel sellPanel = createSellPanel();
        panel.add(sellPanel);
        
        return panel;
    }
    
    /**
     * Create buy stocks panel
     * @return JPanel for buying stocks
     */
    private JPanel createBuyPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Buy Stocks"));
        
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Stock selection
        inputPanel.add(new JLabel("Select Stock:"));
        stockComboBox = new JComboBox<>(stocks.keySet().toArray(new String[0]));
        inputPanel.add(stockComboBox);
        
        // Quantity input
        inputPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        inputPanel.add(quantityField);
        
        // Current price display
        JLabel priceLabel = new JLabel("Price: $0.00");
        stockComboBox.addActionListener(e -> {
            String symbol = (String) stockComboBox.getSelectedItem();
            if (symbol != null) {
                Stock stock = stocks.get(symbol);
                priceLabel.setText("Price: $" + String.format("%.2f", stock.getCurrentPrice()));
            }
        });
        inputPanel.add(new JLabel("Current Price:"));
        inputPanel.add(priceLabel);
        
        panel.add(inputPanel, BorderLayout.CENTER);
        
        // Buy button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton buyButton = new JButton("Buy");
        buyButton.addActionListener(e -> buyStock());
        buttonPanel.add(buyButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Create sell stocks panel
     * @return JPanel for selling stocks
     */
    private JPanel createSellPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Sell Stocks"));
        
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Stock selection (from portfolio)
        inputPanel.add(new JLabel("Select Stock:"));
        JComboBox<String> sellStockCombo = new JComboBox<>();
        refreshSellComboBox(sellStockCombo);
        inputPanel.add(sellStockCombo);
        
        // Quantity input
        inputPanel.add(new JLabel("Quantity:"));
        JTextField sellQuantityField = new JTextField();
        inputPanel.add(sellQuantityField);
        
        // Available shares display
        JLabel availableLabel = new JLabel("Available: 0");
        sellStockCombo.addActionListener(e -> {
            String symbol = (String) sellStockCombo.getSelectedItem();
            if (symbol != null) {
                int available = portfolio.getQuantity(symbol);
                availableLabel.setText("Available: " + available);
            }
        });
        inputPanel.add(new JLabel("Available Shares:"));
        inputPanel.add(availableLabel);
        
        panel.add(inputPanel, BorderLayout.CENTER);
        
        // Sell button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton sellButton = new JButton("Sell");
        sellButton.addActionListener(e -> {
            Object selectedSymbol = sellStockCombo.getSelectedItem();
            if (selectedSymbol != null) {
                try {
                    String symbol = selectedSymbol.toString();
                    int qty = Integer.parseInt(sellQuantityField.getText());
                    sellStock(symbol, qty);
                    sellQuantityField.setText("");
                    refreshSellComboBox(sellStockCombo);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter valid quantity", 
                                                 "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(sellButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Create bottom panel with exit button
     * @return JPanel with exit button
     */
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 12));
        exitButton.addActionListener(e -> {
            FileManager.savePortfolio(currentUser.getUserId(), portfolio, getCurrentPrices());
            System.exit(0);
        });
        
        panel.add(exitButton);
        return panel;
    }
    
    /**
     * Update stock table with current stock data
     */
    private void updateStockTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Symbol");
        model.addColumn("Company Name");
        model.addColumn("Current Price");
        model.addColumn("Previous Price");
        model.addColumn("Change");
        model.addColumn("Change %");
        
        for (Stock stock : stocks.values()) {
            Object[] row = {
                stock.getSymbol(),
                stock.getCompanyName(),
                String.format("$%.2f", stock.getCurrentPrice()),
                String.format("$%.2f", stock.getPreviousPrice()),
                String.format("$%.2f", stock.getPriceChange()),
                String.format("%.2f%%", stock.getPriceChangePercentage())
            };
            model.addRow(row);
        }
        
        stockTable.setModel(model);
        stockTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    /**
     * Update portfolio table with user's holdings
     */
    private void updatePortfolioTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Symbol");
        model.addColumn("Quantity");
        model.addColumn("Avg Cost");
        model.addColumn("Current Price");
        model.addColumn("Current Value");
        model.addColumn("Gain/Loss");
        
        HashMap<String, Integer> holdings = portfolio.getHoldings();
        HashMap<String, Double> currentPrices = getCurrentPrices();
        
        for (String symbol : holdings.keySet()) {
            int qty = holdings.get(symbol);
            double avgCost = portfolio.getAverageCostPrice(symbol);
            double currentPrice = currentPrices.getOrDefault(symbol, 0.0);
            double value = qty * currentPrice;
            double gainLoss = qty * (currentPrice - avgCost);
            
            Object[] row = {
                symbol,
                qty,
                String.format("$%.2f", avgCost),
                String.format("$%.2f", currentPrice),
                String.format("$%.2f", value),
                String.format("$%.2f", gainLoss)
            };
            model.addRow(row);
        }
        
        portfolioTable.setModel(model);
    }
    
    /**
     * Update transaction history table
     */
    private void updateTransactionTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Transaction ID");
        model.addColumn("Type");
        model.addColumn("Stock");
        model.addColumn("Quantity");
        model.addColumn("Price");
        model.addColumn("Total");
        model.addColumn("Date/Time");
        
        // Load transactions for current user
        for (Transaction transaction : transactions) {
            if (transaction.getUserId().equals(currentUser.getUserId())) {
                Object[] row = {
                    transaction.getTransactionId(),
                    transaction.getTransactionType(),
                    transaction.getStockSymbol(),
                    transaction.getQuantity(),
                    String.format("$%.2f", transaction.getPricePerShare()),
                    String.format("$%.2f", transaction.getTotalAmount()),
                    transaction.getFormattedTimestamp()
                };
                model.addRow(row);
            }
        }
        
        transactionTable.setModel(model);
    }
    
    /**
     * Refresh sell combo box with current holdings
     */
    private void refreshSellComboBox(JComboBox<String> comboBox) {
        comboBox.removeAllItems();
        for (String symbol : portfolio.getHoldings().keySet()) {
            comboBox.addItem(symbol);
        }
    }
    
    /**
     * Handle buy stock action
     */
    private void buyStock() {
        try {
            String symbol = (String) stockComboBox.getSelectedItem();
            int quantity = Integer.parseInt(quantityField.getText());
            
            if (symbol == null || quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Please select stock and enter valid quantity",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Stock stock = stocks.get(symbol);
            double totalCost = quantity * stock.getCurrentPrice();
            
            if (!currentUser.hasSufficientBalance(totalCost)) {
                JOptionPane.showMessageDialog(this, "Insufficient balance",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Process buy transaction
            currentUser.withdraw(totalCost);
            portfolio.buyStock(symbol, quantity, stock.getCurrentPrice());
            
            // Create and save transaction
            String transactionId = "TXN" + System.currentTimeMillis();
            Transaction transaction = new Transaction(transactionId, currentUser.getUserId(), 
                                                     symbol, "BUY", quantity, 
                                                     stock.getCurrentPrice());
            transactions.add(transaction);
            FileManager.saveTransaction(transaction);
            
            // Update UI
            updateAllPanels();
            quantityField.setText("");
            
            JOptionPane.showMessageDialog(this, "Successfully bought " + quantity + 
                                         " shares of " + symbol, "Success", 
                                         JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid quantity",
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Handle sell stock action
     */
    private void sellStock(String symbol, int quantity) {
        try {
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Please enter valid quantity",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!portfolio.sellStock(symbol, quantity)) {
                JOptionPane.showMessageDialog(this, "Insufficient shares to sell",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Stock stock = stocks.get(symbol);
            double totalProceeds = quantity * stock.getCurrentPrice();
            
            // Process sell transaction
            currentUser.deposit(totalProceeds);
            
            // Create and save transaction
            String transactionId = "TXN" + System.currentTimeMillis();
            Transaction transaction = new Transaction(transactionId, currentUser.getUserId(),
                                                     symbol, "SELL", quantity,
                                                     stock.getCurrentPrice());
            transactions.add(transaction);
            FileManager.saveTransaction(transaction);
            
            // Update UI
            updateAllPanels();
            
            JOptionPane.showMessageDialog(this, "Successfully sold " + quantity +
                                         " shares of " + symbol, "Success",
                                         JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error selling stock: " + e.getMessage(),
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Search for stock by name or symbol
     */
    private void searchStock() {
        String searchTerm = searchField.getText().toUpperCase();
        
        if (searchTerm.isEmpty()) {
            updateStockTable();
            return;
        }
        
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Symbol");
        model.addColumn("Company Name");
        model.addColumn("Current Price");
        model.addColumn("Previous Price");
        model.addColumn("Change");
        model.addColumn("Change %");
        
        for (Stock stock : stocks.values()) {
            if (stock.getSymbol().contains(searchTerm) || 
                stock.getCompanyName().toUpperCase().contains(searchTerm)) {
                Object[] row = {
                    stock.getSymbol(),
                    stock.getCompanyName(),
                    String.format("$%.2f", stock.getCurrentPrice()),
                    String.format("$%.2f", stock.getPreviousPrice()),
                    String.format("$%.2f", stock.getPriceChange()),
                    String.format("%.2f%%", stock.getPriceChangePercentage())
                };
                model.addRow(row);
            }
        }
        
        stockTable.setModel(model);
    }
    
    /**
     * Get current prices as HashMap
     */
    private HashMap<String, Double> getCurrentPrices() {
        HashMap<String, Double> prices = new HashMap<>();
        for (Stock stock : stocks.values()) {
            prices.put(stock.getSymbol(), stock.getCurrentPrice());
        }
        return prices;
    }
    
    /**
     * Update all panels with fresh data
     */
    private void updateAllPanels() {
        HashMap<String, Double> currentPrices = getCurrentPrices();
        
        // Update top panel
        balanceLabel.setText("Account Balance: $" + 
                            String.format("%.2f", currentUser.getAccountBalance()));
        
        double portfolioValue = portfolio.calculatePortfolioValue(currentPrices);
        portfolioValueLabel.setText("Portfolio Value: $" + String.format("%.2f", portfolioValue));
        
        double profitLoss = portfolio.calculateProfitLoss(currentPrices);
        double profitLossPercent = portfolio.calculateProfitLossPercentage(currentPrices);
        profitLossLabel.setText("Profit/Loss: $" + String.format("%.2f", profitLoss) +
                               " (" + String.format("%.2f", profitLossPercent) + "%)");
        profitLossLabel.setForeground(profitLoss >= 0 ? Color.GREEN : Color.RED);
        
        // Update tables
        updateStockTable();
        updatePortfolioTable();
        updateTransactionTable();
    }
    
    /**
     * Start thread for random stock price updates
     */
    private void startPriceUpdateThread() {
        Thread priceUpdateThread = new Thread(() -> {
            Random random = new Random();
            while (true) {
                try {
                    Thread.sleep(5000); // Update every 5 seconds
                    
                    // Update stock prices randomly
                    for (Stock stock : stocks.values()) {
                        double changePercent = (random.nextDouble() - 0.5) * 2; // -1 to 1
                        double change = stock.getCurrentPrice() * (changePercent / 100);
                        stock.setCurrentPrice(stock.getCurrentPrice() + change);
                    }
                    
                    // Update UI on EDT
                    SwingUtilities.invokeLater(this::updateAllPanels);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        priceUpdateThread.setDaemon(true);
        priceUpdateThread.start();
    }
}
