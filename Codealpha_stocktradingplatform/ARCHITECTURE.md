# Stock Trading Platform - Complete Architecture & Class Documentation

## 📐 System Architecture

### Class Hierarchy & Relationships

```
┌─────────────────────────────────────────────────────────────┐
│                        Main.java                             │
│                   (Entry Point)                              │
└──────────────────────────┬──────────────────────────────────┘
                           │
        ┌──────────────────┼──────────────────┐
        │                  │                  │
        ▼                  ▼                  ▼
    ┌────────┐        ┌──────────┐      ┌──────────┐
    │ Stock  │        │  User    │      │Portfolio │
    │(Model) │        │ (Model)  │      │ (Model)  │
    └────────┘        └──────────┘      └──────────┘
        │                  │                  │
        └──────────────────┼──────────────────┘
                           │
                    ┌──────┴──────┐
                    │             │
                    ▼             ▼
              ┌────────────┐  ┌─────────────┐
              │Transaction │  │FileManager  │
              │ (Model)    │  │(Persistence)│
              └────────────┘  └─────────────┘
                    │                │
                    └────────┬───────┘
                             │
                    ┌────────▼────────┐
                    │  MainFrame.java │
                    │   (View/GUI)    │
                    └─────────────────┘
```

### Data Flow Diagram

```
User Input (GUI)
       ↓
MainFrame (Processes Input)
       ↓
Stock/User/Portfolio (Updates Data)
       ↓
FileManager (Saves Transaction)
       ↓
transactions.txt (Persistent Storage)
       ↓
MainFrame (Refreshes Display)
       ↓
User Sees Updated GUI
```

## 📚 Detailed Class Documentation

---

## 1. Stock.java

### Purpose
Represents a single stock in the market with price tracking and change calculations.

### UML Class Diagram
```
┌──────────────────────────────┐
│          Stock               │
├──────────────────────────────┤
│ - symbol: String             │
│ - companyName: String        │
│ - currentPrice: double       │
│ - previousPrice: double      │
├──────────────────────────────┤
│ + Stock(...)                 │
│ + getSymbol(): String        │
│ + setCurrentPrice(double)    │
│ + getPriceChange(): double   │
│ + getPriceChangePercentage() │
│ + toString(): String         │
└──────────────────────────────┘
```

### Class Variables
| Variable | Type | Purpose |
|----------|------|---------|
| `symbol` | String | Stock ticker (TCS, INFY, etc.) |
| `companyName` | String | Full company name |
| `currentPrice` | double | Current market price |
| `previousPrice` | double | Previous price for comparison |

### Key Methods

**Constructor**
```java
Stock(String symbol, String companyName, double currentPrice)
```
- Initializes a new stock
- Sets initial price as both current and previous

**Getters**
```java
String getSymbol()           // Returns stock symbol
String getCompanyName()      // Returns company name
double getCurrentPrice()     // Returns current price
double getPreviousPrice()    // Returns previous price
```

**Price Operations**
```java
void setCurrentPrice(double price)
// Updates price, stores old as previous
```

**Calculations**
```java
double getPriceChange()
// Returns absolute price change (current - previous)

double getPriceChangePercentage()
// Returns percentage change
```

### Encapsulation Features
- All data members are `private`
- Access through public getters only
- Price changes tracked automatically
- Immutable company name

### Example Usage
```java
Stock tcs = new Stock("TCS", "Tata Consultancy Services", 3500.00);
System.out.println(tcs.getSymbol());        // Output: TCS
System.out.println(tcs.getCurrentPrice()); // Output: 3500.0

tcs.setCurrentPrice(3550.00);
System.out.println(tcs.getPriceChange());       // Output: 50.0
System.out.println(tcs.getPriceChangePercentage()); // Output: 1.43%
```

---

## 2. User.java

### Purpose
Represents a user account with balance management and transaction capability.

### UML Class Diagram
```
┌──────────────────────────────┐
│          User                │
├──────────────────────────────┤
│ - userId: String             │
│ - username: String           │
│ - email: String              │
│ - accountBalance: double     │
│ - INITIAL_BALANCE: 100000.0  │
├──────────────────────────────┤
│ + User(...)                  │
│ + getUserId(): String        │
│ + getAccountBalance(): double│
│ + deposit(double): boolean   │
│ + withdraw(double): boolean  │
│ + hasSufficientBalance()     │
│ + toString(): String         │
└──────────────────────────────┘
```

### Class Variables
| Variable | Type | Purpose |
|----------|------|---------|
| `userId` | String | Unique user identifier |
| `username` | String | Display name |
| `email` | String | User email address |
| `accountBalance` | double | Current cash balance |
| `INITIAL_BALANCE` | double (constant) | Starting balance ($100,000) |

### Key Methods

**Constructor**
```java
User(String userId, String username, String email)
```
- Creates new user with initial balance of $100,000
- Parameters are immutable after creation

**Balance Operations**
```java
boolean deposit(double amount)
// Adds money to account, returns success status

boolean withdraw(double amount)
// Removes money from account if sufficient balance
// Returns true if successful, false otherwise

boolean hasSufficientBalance(double amount)
// Checks if user has enough balance
// Returns true if balance >= amount
```

**Getters**
```java
String getUserId()           // Returns unique ID
String getUsername()         // Returns username
String getEmail()            // Returns email
double getAccountBalance()   // Returns current balance
```

### Balance Rules
- Deposits must be positive amount
- Withdrawals cannot exceed current balance
- Negative amounts are rejected
- Balance never goes below 0

### Example Usage
```java
User investor = new User("USER001", "Investor", "investor@platform.com");

System.out.println(investor.getAccountBalance()); // 100000.0

investor.withdraw(35000);  // Buy stocks
System.out.println(investor.getAccountBalance()); // 65000.0

investor.deposit(5000);    // Sell stocks
System.out.println(investor.getAccountBalance()); // 70000.0

if (investor.hasSufficientBalance(100000)) {
    investor.withdraw(100000); // This won't happen
}
```

---

## 3. Portfolio.java

### Purpose
Manages user's stock holdings, tracks investment, and calculates returns.

### UML Class Diagram
```
┌─────────────────────────────────────────┐
│          Portfolio                      │
├─────────────────────────────────────────┤
│ - userId: String                        │
│ - holdings: HashMap<String, Integer>    │
│ - avgCostPrice: HashMap<String, Double> │
│ - totalInvestedAmount: double           │
├─────────────────────────────────────────┤
│ + Portfolio(String)                     │
│ + buyStock(...)                         │
│ + sellStock(...): boolean               │
│ + calculatePortfolioValue(...): double  │
│ + calculateProfitLoss(...): double      │
│ + calculateProfitLossPercentage(...):d  │
│ + getPortfolioDetails(...): String      │
└─────────────────────────────────────────┘
```

### Class Variables
| Variable | Type | Purpose |
|----------|------|---------|
| `userId` | String | Owner of portfolio |
| `holdings` | HashMap | Symbol → Quantity mapping |
| `avgCostPrice` | HashMap | Symbol → Average price mapping |
| `totalInvestedAmount` | double | Total amount invested (cost basis) |

### Key Methods

**Constructor**
```java
Portfolio(String userId)
```
- Creates empty portfolio for user
- Initializes HashMaps for holdings and cost prices

**Stock Operations**
```java
void buyStock(String symbol, int quantity, double currentPrice)
// Adds shares to portfolio
// Recalculates average cost price
// Updates total invested amount

boolean sellStock(String symbol, int quantity)
// Removes shares from portfolio
// Returns false if insufficient shares
// Automatically removes stock if quantity becomes 0
```

**Valuation Methods**
```java
double calculatePortfolioValue(HashMap<String, Double> currentPrices)
// Returns: Sum of (quantity × current price) for all stocks

double calculateProfitLoss(HashMap<String, Double> currentPrices)
// Returns: Current value - Total invested
// Positive = Profit, Negative = Loss

double calculateProfitLossPercentage(HashMap<String, Double> currentPrices)
// Returns: (Profit/Loss / Invested) × 100
```

**Getters**
```java
int getQuantity(String symbol)           // Shares owned
double getAverageCostPrice(String symbol) // Average buy price
HashMap<String, Integer> getHoldings()    // All holdings
double getTotalInvestedAmount()           // Total cost basis
```

### Example Usage
```java
Portfolio port = new Portfolio("USER001");

// Buy 10 shares of TCS at $3500
port.buyStock("TCS", 10, 3500.00);

// Buy 20 more shares at $3600 (avg becomes $3557.14)
port.buyStock("TCS", 10, 3600.00);

System.out.println(port.getQuantity("TCS")); // 20
System.out.println(port.getAverageCostPrice("TCS")); // 3550.00

// Create current prices map
HashMap<String, Double> prices = new HashMap<>();
prices.put("TCS", 3700.00);

System.out.println(port.calculatePortfolioValue(prices)); // 74000.00
System.out.println(port.calculateProfitLoss(prices));    // 3000.00
System.out.println(port.calculateProfitLossPercentage(prices)); // 4.29%

// Sell 5 shares
port.sellStock("TCS", 5);
System.out.println(port.getQuantity("TCS")); // 15
```

---

## 4. Transaction.java

### Purpose
Records individual buy/sell transactions with timestamp and file serialization.

### UML Class Diagram
```
┌──────────────────────────────────┐
│       Transaction                │
├──────────────────────────────────┤
│ - transactionId: String          │
│ - userId: String                 │
│ - stockSymbol: String            │
│ - transactionType: String        │
│ - quantity: int                  │
│ - pricePerShare: double          │
│ - totalAmount: double            │
│ - timestamp: LocalDateTime       │
├──────────────────────────────────┤
│ + Transaction(...)               │
│ + getTransactionId(): String     │
│ + getStockSymbol(): String       │
│ + getTransactionType(): String   │
│ + toFileFormat(): String         │
│ + getFormattedTimestamp(): String│
│ + toString(): String             │
└──────────────────────────────────┘
```

### Class Variables
| Variable | Type | Purpose |
|----------|------|---------|
| `transactionId` | String | Unique transaction ID (TXN+timestamp) |
| `userId` | String | User who made transaction |
| `stockSymbol` | String | Stock symbol (TCS, INFY, etc.) |
| `transactionType` | String | "BUY" or "SELL" |
| `quantity` | int | Number of shares |
| `pricePerShare` | double | Price at transaction time |
| `totalAmount` | double | quantity × pricePerShare |
| `timestamp` | LocalDateTime | Transaction date/time |

### Key Methods

**Constructor**
```java
Transaction(String transactionId, String userId, String stockSymbol, 
            String transactionType, int quantity, double pricePerShare)
```
- Creates transaction record
- Auto-calculates totalAmount
- Sets timestamp to current time

**Getters** (All read-only)
```java
String getTransactionId()        // Unique ID
String getUserId()               // User who made it
String getStockSymbol()          // Stock symbol
String getTransactionType()      // "BUY" or "SELL"
int getQuantity()                // Shares count
double getPricePerShare()        // Price per share
double getTotalAmount()          // Total transaction amount
LocalDateTime getTimestamp()     // Date/time
```

**File Operations**
```java
String toFileFormat()
// Returns: "TXN001|USER001|TCS|BUY|10|3500.00|35000.00|2024-01-15 10:30:45"

String getFormattedTimestamp()
// Returns: "2024-01-15 10:30:45"
```

### Example Usage
```java
// Create a buy transaction
Transaction t1 = new Transaction("TXN001", "USER001", "TCS", "BUY", 10, 3500.00);

System.out.println(t1.getStockSymbol());     // TCS
System.out.println(t1.getQuantity());        // 10
System.out.println(t1.getTotalAmount());     // 35000.0
System.out.println(t1.getFormattedTimestamp()); // 2024-01-15 10:30:45

// Save to file
String fileFormat = t1.toFileFormat();
// "TXN001|USER001|TCS|BUY|10|3500.0|35000.0|2024-01-15 10:30:45"

// User readable output
System.out.println(t1.toString());
// "2024-01-15 10:30:45 - BUY 10 shares of TCS @ $3500.00 = $35000.00"
```

---

## 5. FileManager.java

### Purpose
Handles all file I/O operations for persistence of transactions and portfolio data.

### UML Class Diagram
```
┌──────────────────────────────────────┐
│       FileManager                    │
├──────────────────────────────────────┤
│ - TRANSACTIONS_FILE: String (static) │
│ - PORTFOLIO_FILE: String (static)    │
├──────────────────────────────────────┤
│ + saveTransaction(Transaction): void │
│ + loadTransactions(): List<Transaction>
│ + savePortfolio(...): void           │
│ + clearTransactionHistory(): void    │
│ + clearPortfolioHistory(): void      │
│ + transactionsFileExists(): boolean  │
│ + getTransactionCount(): int         │
└──────────────────────────────────────┘
```

### File Structure

**transactions.txt Format:**
```
TXN001|USER001|TCS|BUY|10|3500.00|35000.00|2024-01-15 10:30:45
TXN002|USER001|TCS|BUY|5|3550.00|17750.00|2024-01-15 11:00:00
TXN003|USER001|INFY|BUY|20|1650.00|33000.00|2024-01-15 11:30:00
TXN004|USER001|TCS|SELL|5|3700.00|18500.00|2024-01-15 14:00:00
```

**portfolio.txt Format:**
```
=== PORTFOLIO FOR USER: USER001 ===
TCS|15|3550.00|3700.00
INFY|20|1650.00|1700.00
SUMMARY|88250.00|90500.00|2250.00
---
```

### Key Methods

**Transaction Operations**
```java
static void saveTransaction(Transaction transaction)
// Appends transaction to transactions.txt
// Creates file if it doesn't exist
// Uses FileWriter in append mode

static List<Transaction> loadTransactions()
// Reads all transactions from transactions.txt
// Parses pipe-separated format
// Returns List<Transaction>
```

**Portfolio Operations**
```java
static void savePortfolio(String userId, Portfolio portfolio, 
                         HashMap<String, Double> currentPrices)
// Saves portfolio snapshot to portfolio.txt
// Includes all holdings with current values
// Appends to file (keeps history)
```

**Utility Methods**
```java
static void clearTransactionHistory()  // Empties transactions.txt
static void clearPortfolioHistory()    // Empties portfolio.txt
static boolean transactionsFileExists() // Checks if file exists
static int getTransactionCount()       // Count of transactions
```

### Error Handling
```java
try (FileWriter fw = new FileWriter(file, true);
     BufferedWriter bw = new BufferedWriter(fw)) {
    // Write operation
} catch (IOException e) {
    System.err.println("Error saving transaction: " + e.getMessage());
}
```

### Example Usage
```java
// Save transaction
Transaction t = new Transaction("TXN001", "USER001", "TCS", "BUY", 10, 3500.00);
FileManager.saveTransaction(t);

// Load all transactions
List<Transaction> allTransactions = FileManager.loadTransactions();
for (Transaction tx : allTransactions) {
    System.out.println(tx);
}

// Save portfolio snapshot
Portfolio port = new Portfolio("USER001");
HashMap<String, Double> prices = new HashMap<>();
prices.put("TCS", 3700.00);
FileManager.savePortfolio("USER001", port, prices);

// Check file exists
if (FileManager.transactionsFileExists()) {
    int count = FileManager.getTransactionCount();
    System.out.println("Total transactions: " + count);
}
```

---

## 6. MainFrame.java

### Purpose
Swing GUI implementation - the complete user interface for the application.

### GUI Architecture
```
MainFrame (JFrame)
├── Top Panel (Account Info)
│   ├── Account Balance Label
│   ├── Portfolio Value Label
│   ├── Profit/Loss Label
│   └── User Info Label
├── TabbedPane (JTabbedPane)
│   ├── Stock Market Tab
│   │   ├── Search Panel
│   │   └── Stock Table (JTable)
│   ├── Portfolio Tab
│   │   └── Portfolio Table (JTable)
│   ├── Transaction History Tab
│   │   ├── Transaction Table (JTable)
│   │   └── Refresh Button
│   └── Trading Tab
│       ├── Buy Stocks Section
│       │   ├── Stock Combo
│       │   ├── Quantity Field
│       │   ├── Price Display
│       │   └── Buy Button
│       └── Sell Stocks Section
│           ├── Stock Combo (Holdings)
│           ├── Quantity Field
│           ├── Available Display
│           └── Sell Button
└── Bottom Panel
    └── Exit Button
```

### Key Components

**Window Setup**
```java
setTitle("Stock Trading Platform - " + user.getUsername());
setSize(1200, 700);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setLocationRelativeTo(null); // Center on screen
```

**Tabbed Pane**
```
Tab 1: Stock Market   - View all stocks, search, track changes
Tab 2: Portfolio      - View holdings, costs, current values
Tab 3: Transactions   - View all past transactions
Tab 4: Trading        - Buy/Sell interface
```

**Tables Used**
```
Stock Table Columns:
  - Symbol
  - Company Name
  - Current Price
  - Previous Price
  - Change (Amount)
  - Change %

Portfolio Table Columns:
  - Symbol
  - Quantity
  - Avg Cost
  - Current Price
  - Current Value
  - Gain/Loss

Transaction Table Columns:
  - Transaction ID
  - Type (BUY/SELL)
  - Stock
  - Quantity
  - Price
  - Total
  - Date/Time
```

### Key Methods

**Initialization**
```java
public MainFrame(User user, Portfolio portfolio, HashMap<String, Stock> stocks)
// Constructor that sets up the entire GUI

private void initializeComponents()
// Creates all GUI components and layout

private void startPriceUpdateThread()
// Starts background thread for price updates
```

**Panel Creation**
```java
private JPanel createTopPanel()       // Account info
private JPanel createStockMarketPanel() // Stocks view
private JPanel createPortfolioPanel()   // Holdings view
private JPanel createTransactionHistoryPanel() // Transactions
private JPanel createTradingPanel()     // Buy/Sell
private JPanel createBuyPanel()         // Buy section
private JPanel createSellPanel()        // Sell section
private JPanel createBottomPanel()      // Exit button
```

**Data Updates**
```java
private void updateStockTable()           // Refresh stock display
private void updatePortfolioTable()       // Refresh portfolio
private void updateTransactionTable()     // Refresh transactions
private void updateAllPanels()            // Refresh everything

private void buyStock()                   // Handle buy action
private void sellStock(String s, int q)   // Handle sell action
private void searchStock()                // Search by name/symbol
```

**Background Operations**
```java
private void startPriceUpdateThread()
// Creates daemon thread that:
// - Updates prices every 5 seconds
// - Random change between -1% to +1%
// - Refreshes GUI on EDT
```

### Event Handling Flow
```
User Action (Click Button)
        ↓
ActionListener Event
        ↓
Buy/Sell/Search Method
        ↓
Update Model (User, Portfolio)
        ↓
Save Transaction to File
        ↓
Refresh GUI Tables
        ↓
Show Success/Error Message
```

---

## 7. Main.java

### Purpose
Entry point of the application - initializes all components and launches GUI.

### Execution Flow
```
main()
  ├── initializeStocks()
  │   ├── Create Stock objects (5 stocks)
  │   ├── Print initialization message
  │   └── Return HashMap<String, Stock>
  │
  ├── Create User object
  │   └── Initialize with $100,000
  │
  ├── Create Portfolio object
  │   └── Empty initially
  │
  └── Launch GUI on EDT
      └── SwingUtilities.invokeLater()
          └── new MainFrame()
```

### Code Breakdown

**Stock Initialization**
```java
stocks.put("TCS", new Stock("TCS", "Tata Consultancy Services", 3500.00));
stocks.put("INFY", new Stock("INFY", "Infosys", 1650.00));
stocks.put("WIPRO", new Stock("WIPRO", "Wipro Limited", 425.00));
stocks.put("RELIANCE", new Stock("RELIANCE", "Reliance Industries", 2500.00));
stocks.put("HCL", new Stock("HCL", "HCL Technologies", 1200.00));
```

**EDT Safety**
```java
SwingUtilities.invokeLater(() -> {
    new MainFrame(user, portfolio, stocks);
});
// Ensures GUI creation happens on Event Dispatch Thread
```

**Console Output**
```
=== Stock Trading Platform ===
Available Stocks initialized:
  - TCS - Tata Consultancy Services ($3500.00)
  - INFY - Infosys ($1650.00)
  - WIPRO - Wipro Limited ($425.00)
  - RELIANCE - Reliance Industries ($2500.00)
  - HCL - HCL Technologies ($1200.00)
```

---

## 🔄 Complete Transaction Flow

### Buy Transaction Example

```
1. User Action
   └─ User selects "TCS" and enters quantity "10"

2. GUI Processing (MainFrame.buyStock())
   ├─ Validates input
   ├─ Checks sufficient balance
   └─ Proceeds if valid

3. Model Updates
   ├─ User.withdraw(cost)  → Balance decreases
   ├─ Portfolio.buyStock() → Holdings increase
   │   └─ Average cost calculated
   └─ Return success

4. Transaction Recording
   ├─ Create Transaction object
   ├─ FileManager.saveTransaction() → Save to file
   └─ Add to transactions list

5. GUI Update
   ├─ Update balance label
   ├── Update portfolio table
   ├─ Update transaction history
   └─ Show success message
```

### Sell Transaction Example

```
1. User Action
   └─ User selects "TCS" and enters quantity "5"

2. GUI Processing (MainFrame.sellStock())
   ├─ Validates input
   ├─ Checks portfolio has shares
   └─ Proceeds if valid

3. Model Updates
   ├─ Portfolio.sellStock() → Holdings decrease
   ├─ User.deposit(proceeds) → Balance increases
   └─ Return success

4. Transaction Recording
   ├─ Create Transaction object
   ├─ FileManager.saveTransaction() → Save to file
   └─ Add to transactions list

5. GUI Update
   ├─ Update balance label
   ├─ Update portfolio table
   ├─ Update transaction history
   └─ Show success message
```

---

## 📊 Data Relationships

### User-Portfolio Relationship
```
User (1) ──── (1) Portfolio
  │
  ├─ userId
  ├─ username
  └─ accountBalance
```

### Portfolio-Stock Relationship
```
Portfolio (1) ──── (Many) Stocks
  │
  ├─ holdings HashMap
  │  └─ TCS → 10 shares
  │  └─ INFY → 20 shares
  │
  └─ avgCostPrice HashMap
     └─ TCS → $3500
     └─ INFY → $1650
```

### Transaction Recording
```
Transaction ────────→ User
    │                  │
    ├─ userId ─────────┘
    ├─ stockSymbol
    ├─ quantity
    └─ pricePerShare
```

---

## 🎯 OOP Principles Implementation

| Principle | Implementation |
|-----------|-----------------|
| **Encapsulation** | Private data with public getters/setters |
| **Abstraction** | Complex logic hidden in methods |
| **Inheritance** | JFrame inheritance for GUI |
| **Polymorphism** | Method overriding in GUI components |
| **Collections** | HashMap for flexible storage |
| **Exception Handling** | Try-catch blocks in FileManager |
| **Thread Safety** | SwingUtilities for GUI threading |
| **Design Patterns** | MVC, Singleton, Factory, Observer |

---

## 📈 Running the Application - Complete View

```
START
  │
  └─→ java Main
      │
      ├─→ Main.main()
      │   │
      │   ├─→ initializeStocks()
      │   │   └─→ Create 5 Stock objects
      │   │
      │   ├─→ Create User ("Investor")
      │   │   └─→ Initial balance: $100,000
      │   │
      │   ├─→ Create Portfolio
      │   │
      │   └─→ SwingUtilities.invokeLater()
      │       └─→ new MainFrame()
      │
      └─→ GUI Launches
          │
          ├─→ Load transaction history
          │
          ├─→ Start price update thread
          │
          └─→ User interaction begins
```

---

**This architecture ensures clean separation of concerns, easy maintenance, and extensibility for future features.**

