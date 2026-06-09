# Stock Trading Platform - Java OOP Project

## Overview
This is a complete Stock Trading Platform built with Java and Swing GUI. It demonstrates advanced OOP concepts including encapsulation, inheritance, polymorphism, and file handling. The platform allows users to buy/sell stocks, track their portfolio, and view transaction history.

## Output Screenshot

![Stock Trading Platform Output](screenshots/output.png)

## Project Features

### Core Features
- ✅ Display available stocks (TCS, Infosys, Wipro, Reliance, HCL)
- ✅ Show real-time stock prices with random updates
- ✅ Buy and sell stocks
- ✅ Update user balance after each transaction
- ✅ Track owned shares in portfolio
- ✅ Calculate portfolio value and profit/loss
- ✅ Display transaction history
- ✅ File handling for persistence

### OOP Concepts Implemented
- **Encapsulation**: Private variables with public getters/setters
- **Inheritance**: Base classes with derived functionality
- **Polymorphism**: Overridden methods and interfaces
- **Collections**: HashMap, ArrayList for data management
- **Exception Handling**: Try-catch blocks for error management

### File Handling
- **transactions.txt**: Stores all buy/sell transactions
- **portfolio.txt**: Stores portfolio snapshots
- Automatic loading of transaction history on startup

### GUI Components
- **Main Dashboard**: Shows account balance, portfolio value, profit/loss
- **Stock Market Panel**: Display all stocks with search functionality
- **Portfolio Panel**: Display user's current holdings
- **Transaction History Panel**: Show all past transactions
- **Trading Panel**: Buy and sell stocks
- **JTable**: Professional table display for data

## Project Structure

```
c:\Intern\
├── Stock.java              # Stock class - represents individual stocks
├── User.java               # User class - represents trader
├── Portfolio.java          # Portfolio class - manages user's stocks
├── Transaction.java        # Transaction class - records buy/sell actions
├── FileManager.java        # FileManager class - handles file I/O
├── MainFrame.java          # MainFrame class - Swing GUI implementation
├── Main.java               # Main class - entry point
├── transactions.txt        # Transaction history (generated at runtime)
├── portfolio.txt           # Portfolio data (generated at runtime)
└── README.md               # This file
```

## Class Descriptions

### 1. Stock.java
Represents a single stock in the market.

**Key Methods:**
- `Stock(symbol, companyName, currentPrice)` - Constructor
- `getCurrentPrice()` / `setCurrentPrice()` - Price management
- `getPriceChange()` - Calculate price change
- `getPriceChangePercentage()` - Calculate percentage change

**Encapsulation:** Private variables with public accessors

### 2. User.java
Represents a user/trader account.

**Key Methods:**
- `User(userId, username, email)` - Constructor
- `getAccountBalance()` - Get current balance
- `deposit(amount)` / `withdraw(amount)` - Balance management
- `hasSufficientBalance(amount)` - Check balance

**Initial Balance:** $100,000

### 3. Portfolio.java
Manages user's stock holdings.

**Key Methods:**
- `buyStock(symbol, quantity, price)` - Add shares to portfolio
- `sellStock(symbol, quantity)` - Remove shares from portfolio
- `calculatePortfolioValue(prices)` - Total current value
- `calculateProfitLoss(prices)` - Calculate gain/loss
- `getHoldings()` - Get all stocks owned

**Features:**
- Average cost price calculation
- Gain/loss tracking per stock
- Portfolio value calculation

### 4. Transaction.java
Records individual buy/sell transactions.

**Key Methods:**
- `Transaction(id, userId, symbol, type, quantity, price)` - Constructor
- `toFileFormat()` - Convert to file storage format
- `getFormattedTimestamp()` - Formatted date/time

**Stores:**
- Transaction type (BUY/SELL)
- Stock symbol
- Quantity and price
- Timestamp

### 5. FileManager.java
Handles all file I/O operations.

**Key Methods:**
- `saveTransaction(transaction)` - Save to transactions.txt
- `loadTransactions()` - Load from transactions.txt
- `savePortfolio(userId, portfolio, prices)` - Save portfolio data
- `clearTransactionHistory()` - Clear all transactions

**Files Used:**
- `transactions.txt` - Transaction history
- `portfolio.txt` - Portfolio data

### 6. MainFrame.java
Swing GUI implementation - the heart of the UI.

**GUI Components:**
- JTabbedPane - Multiple tabs for different sections
- JTable - Display stocks, portfolio, transactions
- JComboBox - Select stocks for trading
- JTextField - Input quantity and search
- JButton - Buy, Sell, Search, Exit

**Panels:**
- Stock Market Panel
- Portfolio Panel
- Transaction History Panel
- Trading Panel (Buy/Sell)

**Features:**
- Real-time price updates (every 5 seconds)
- Search stocks by symbol or name
- Live balance and profit/loss display
- Automatic UI refresh after transactions

### 7. Main.java
Entry point of the application.

**Initialization:**
- Creates stock objects with sample data
- Initializes user account
- Creates portfolio
- Loads transaction history
- Launches GUI on EDT

## How to Run in VS Code

### Prerequisites
- Java Development Kit (JDK) 8 or higher installed
- VS Code with Extension Pack for Java

### Step-by-Step Instructions

#### Step 1: Install Java Extension Pack
1. Open VS Code
2. Go to Extensions (Ctrl+Shift+X)
3. Search for "Extension Pack for Java"
4. Click "Install"

#### Step 2: Create Workspace Folder
1. Create a new folder: `C:\Intern` (or your preferred location)
2. Open this folder in VS Code

#### Step 3: Copy All Java Files
1. Copy all the provided Java files to the `C:\Intern` folder:
   - Stock.java
   - User.java
   - Portfolio.java
   - Transaction.java
   - FileManager.java
   - MainFrame.java
   - Main.java

#### Step 4: Compile the Project
Open Terminal in VS Code (Ctrl+`) and run:

```bash
# Navigate to project folder (if not already there)
cd C:\Intern

# Compile all Java files
javac *.java
```

If you see no errors, compilation is successful!

#### Step 5: Run the Application
In the terminal, run:

```bash
java Main
```

The Stock Trading Platform GUI should launch!

#### Alternative: Run Directly from VS Code
1. Open `Main.java`
2. You should see a "Run" link above the `public static void main` method
3. Click "Run" to execute

### Troubleshooting

**Error: "javac is not recognized"**
- Add Java to your system PATH
- Or provide full path to Java: `C:\Program Files\Java\jdk-xx\bin\javac *.java`

**Error: "Cannot find symbol"**
- Make sure all Java files are in the same folder
- Check file names match exactly
- Recompile all files: `javac *.java`

**Error: "Exception in thread main"**
- Check that Main.java exists and is properly named
- Ensure all files are compiled

**GUI doesn't appear**
- Check terminal for error messages
- Ensure Swing components are supported on your system
- Try running with: `java -Dswing.systemlaf=com.sun.java.swing.plaf.windows.WindowsLookAndFeel Main`

## User Guide

### Getting Started
1. Application starts with initial balance of **$100,000**
2. Five stocks are available: TCS, INFY, WIPRO, RELIANCE, HCL

### Viewing Stocks
1. Go to "Stock Market" tab
2. See all stocks with current prices
3. Use "Search Stock" to find by symbol or name
4. Prices update automatically every 5 seconds

### Buying Stocks
1. Go to "Trading" tab
2. Select stock from dropdown
3. Enter quantity
4. Click "Buy"
5. Funds deducted from account
6. Transaction recorded

### Selling Stocks
1. Go to "Trading" tab
2. Select stock from dropdown (only shows owned stocks)
3. Enter quantity to sell
4. Click "Sell"
5. Funds added to account
6. Transaction recorded

### Viewing Portfolio
1. Go to "Portfolio" tab
2. See all held stocks
3. View average cost price
4. See current value and gain/loss

### Transaction History
1. Go to "Transaction History" tab
2. See all past buy/sell transactions
3. Includes date, time, price, and total amount

### Data Persistence
- All transactions automatically saved to `transactions.txt`
- Portfolio data saved to `portfolio.txt`
- On restart, all transaction history is loaded automatically

## Sample Trading Session

### Scenario
Let's say you want to trade with initial $100,000:

1. **Buy TCS**: 
   - Current price: $3,500
   - Quantity: 10
   - Cost: $35,000
   - Remaining balance: $65,000

2. **Buy Infosys**:
   - Current price: $1,650
   - Quantity: 20
   - Cost: $33,000
   - Remaining balance: $32,000

3. **Monitor Portfolio**:
   - As prices change, your portfolio value updates
   - Gain/Loss calculated automatically

4. **Sell Some Shares**:
   - If profitable, sell to lock in gains
   - If loss-making, hold or average down

## Advanced Features

### Random Price Updates
- Stock prices update every 5 seconds
- Random change between -1% and +1%
- Reflects real market volatility

### Search Functionality
- Search by stock symbol (e.g., "TCS")
- Search by company name (e.g., "Infosys")
- Partial matches supported

### Error Handling
- Insufficient balance checking
- Invalid quantity validation
- Non-existent stock handling
- File I/O error management

### Data Persistence
- Transactions saved immediately upon completion
- Portfolio snapshots saved on exit
- Transaction history loaded on startup

## Code Quality

### Best Practices Implemented
- ✅ Proper encapsulation with private/public
- ✅ Meaningful variable names
- ✅ Comprehensive comments and JavaDoc
- ✅ Error handling with try-catch
- ✅ Collections for flexible data management
- ✅ Thread-safe GUI updates with SwingUtilities
- ✅ Separation of concerns (Model-View separation)

### Design Patterns Used
- **MVC Pattern**: Data (Model) separated from UI (View)
- **Singleton Pattern**: FileManager static methods
- **Factory Pattern**: Stock creation in Main
- **Observer Pattern**: GUI updates on data changes

## Performance Characteristics

- **Startup Time**: < 2 seconds
- **GUI Responsiveness**: Smooth, no freezing
- **Memory Usage**: Minimal (< 100MB)
- **File Operations**: Instant save/load

## Future Enhancement Ideas

1. **Database Integration**: Replace file storage with database
2. **Real Market Data**: Connect to real stock market APIs
3. **Advanced Charts**: Technical analysis charts
4. **Multiple Users**: User authentication and multiple accounts
5. **Portfolio Comparison**: Compare against market indices
6. **Alerts**: Price alerts and notifications
7. **Mobile App**: Android/iOS companion app
8. **Cloud Sync**: Sync data across devices

## Testing

### Manual Testing Scenarios

**Test 1: Buy Stock**
- Navigate to Trading tab
- Select TCS
- Enter quantity 5
- Click Buy
- Verify: Balance decreased, Portfolio updated, Transaction recorded

**Test 2: Sell Stock**
- Navigate to Trading tab
- Select owned stock
- Enter valid quantity
- Click Sell
- Verify: Balance increased, Stock removed from portfolio

**Test 3: Search Stock**
- Navigate to Stock Market tab
- Search for "Infosys"
- Verify: Only matching stocks displayed

**Test 4: Price Updates**
- Watch Stock Market tab
- Prices should change every 5 seconds
- Verify: Gain/Loss updates automatically

**Test 5: Persistence**
- Add some transactions
- Close application
- Reopen application
- Verify: All transactions still there

## Support and Documentation

For each class, JavaDoc comments explain:
- Purpose and responsibility
- OOP concepts used
- Method signatures and parameters
- Return values and exceptions

## Credits

- **Project**: Stock Trading Platform
- **Type**: CodeAlpha Internship Project
- **Language**: Java
- **GUI Framework**: Swing
- **Features**: 45+ implemented

## License

This project is provided as-is for educational purposes.

---

**Happy Trading! 📈📉**

For questions or issues, refer to the inline comments in the source code.
