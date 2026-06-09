# Stock Trading Platform - Quick Start Guide

## ✅ Project Compilation Status
**Status: Successfully Compiled ✓**

All 7 classes have been compiled without errors:
- ✓ Stock.java → Stock.class
- ✓ User.java → User.class
- ✓ Portfolio.java → Portfolio.class
- ✓ Transaction.java → Transaction.class
- ✓ FileManager.java → FileManager.class
- ✓ MainFrame.java → MainFrame.class
- ✓ Main.java → Main.class

## 🚀 Quick Start - Run the Application

### Method 1: Command Line (Recommended)

1. **Open Terminal in VS Code**
   - Press `Ctrl + ~` (backtick)
   - Or use View → Terminal

2. **Navigate to Project Folder**
   ```bash
   cd c:\Intern
   ```

3. **Run the Application**
   ```bash
   java Main
   ```

4. **Expected Output:**
   - Terminal shows: "=== Stock Trading Platform ===" message
   - A GUI window opens with the trading platform

### Method 2: VS Code Run Button

1. Open `Main.java` file
2. Look for a **"Run"** link above the `public static void main` method
3. Click **"Run"**
4. The application launches automatically

### Method 3: Debug Mode

1. Open `Main.java`
2. Look for a **"Debug"** link above the `public static void main` method
3. Click **"Debug"**
4. Application runs with debugger attached

## 📊 Using the Stock Trading Platform

### Initial State
- **Username:** Investor
- **Initial Balance:** $100,000
- **Available Stocks:** TCS, INFY, WIPRO, RELIANCE, HCL

### Main Window Sections

**Top Bar (Status Information)**
- Account Balance: Your current cash
- Portfolio Value: Current value of all holdings
- Profit/Loss: Gain/Loss amount and percentage
- User: Your username

**Tabbed Interface**

1. **Stock Market Tab**
   - View all 5 available stocks
   - See current prices and changes
   - Prices update every 5 seconds
   - Search for specific stocks

   **How to Search:**
   - Type stock symbol (e.g., "TCS") or company name (e.g., "Infosys")
   - Click "Search"
   - View filtered results

2. **Portfolio Tab**
   - View your current stock holdings
   - See average cost price
   - View current market price
   - Check current value of each position
   - View profit/loss per stock

3. **Transaction History Tab**
   - View all past buy/sell transactions
   - See transaction type (BUY/SELL)
   - Check stock symbols, quantities, prices
   - View exact date and time of transactions

4. **Trading Tab**
   - **Buy Stocks Section:**
     1. Select stock from dropdown
     2. Enter quantity to buy
     3. Current price displays automatically
     4. Click "Buy" to execute
   
   - **Sell Stocks Section:**
     1. Select stock you own from dropdown
     2. Enter quantity to sell
     3. Available shares displays automatically
     4. Click "Sell" to execute

## 💼 Example Trading Session

### Step 1: Buy First Stock
1. Click "Trading" tab
2. Select "TCS" from dropdown
3. Enter quantity: 10
4. Note current price displays
5. Click "Buy"
6. See success message
7. Balance decreases by (10 × price)
8. Stock appears in Portfolio tab

### Step 2: Monitor Prices
1. Go to "Stock Market" tab
2. Watch prices update (every 5 seconds)
3. Stock changes show in green (gain) or red (loss)

### Step 3: View Portfolio Status
1. Click "Portfolio" tab
2. See your holding: "TCS - 10 shares"
3. Average cost price shown
4. Current value calculated
5. Profit/loss displayed for this position

### Step 4: Sell Stock
1. Click "Trading" tab
2. Select "TCS" from Sell dropdown
3. Enter quantity: 5
4. Click "Sell"
5. Portfolio updated (now 5 shares TCS)
6. Balance increased
7. Transaction recorded

### Step 5: Check History
1. Click "Transaction History" tab
2. See both BUY transaction (TCS 10@price)
3. See SELL transaction (TCS 5@newprice)
4. Timestamps show when each occurred

## 💾 Data Persistence

### What Gets Saved?

1. **transactions.txt**
   - Created automatically on first transaction
   - Records every buy/sell operation
   - Persists after application exit
   - Format: TXN ID | User | Symbol | Type | Qty | Price | Total | Timestamp

2. **portfolio.txt**
   - Created on application exit
   - Snapshot of portfolio at that time
   - Can be used for analysis
   - Format: Summary data with values and profit/loss

### On Startup
- Application automatically loads all past transactions from `transactions.txt`
- Portfolio history is available to view
- All data from previous sessions is restored

## 🎯 OOP Concepts Demonstrated

### Stock.java
- **Encapsulation**: Private data, public accessors
- **Features**: Price tracking, change calculation

### User.java
- **Encapsulation**: Protected balance data
- **Methods**: Deposit, withdraw, balance checking

### Portfolio.java
- **Collections**: HashMap for stock holdings
- **Calculations**: Portfolio value, profit/loss
- **Methods**: Buy, sell, average cost tracking

### Transaction.java
- **Data Class**: Immutable transaction records
- **Timestamp**: Transaction date/time tracking
- **File Format**: Custom serialization

### FileManager.java
- **Single Responsibility**: Only file I/O operations
- **Static Methods**: Utility pattern
- **Error Handling**: Try-catch blocks

### MainFrame.java
- **MVC Pattern**: Separation of UI from logic
- **Swing Components**: Professional GUI
- **Threading**: Background price updates
- **Event Handling**: Button clicks, dropdown changes

### Main.java
- **Factory Pattern**: Stock object creation
- **Initialization**: Application startup sequence
- **EDT**: Swing thread safety

## ⚠️ Troubleshooting

### Problem: "Class not found" error
**Solution:**
- Make sure all .class files are in c:\Intern
- Try recompiling: `javac *.java`

### Problem: "java" command not recognized
**Solution:**
- Add Java to PATH environment variable
- Or use full path: `C:\Program Files\Java\jdk-xx\bin\java Main`

### Problem: GUI window doesn't appear
**Solution:**
- Check terminal for error messages
- Ensure graphics/display drivers updated
- Try: `java -Dswing.systemlaf=com.sun.java.swing.plaf.windows.WindowsLookAndFeel Main`

### Problem: Cannot buy stock (balance shows as 0)
**Solution:**
- This is a display-only issue
- Actual balance is correctly maintained
- Try refreshing the tab

### Problem: Price doesn't update
**Solution:**
- Price updates happen every 5 seconds
- Wait a moment and observe changes
- Check that application window is in focus

## 📁 File Structure

```
c:\Intern\
├── Stock.java              (101 lines)
├── User.java               (76 lines)
├── Portfolio.java          (143 lines)
├── Transaction.java        (74 lines)
├── FileManager.java        (101 lines)
├── MainFrame.java          (623 lines)
├── Main.java               (48 lines)
├── README.md               (Complete documentation)
├── QUICKSTART.md           (This file)
├── transactions.txt        (Auto-created)
├── portfolio.txt           (Auto-created)
└── .class files            (Compiled bytecode)
```

## 🔧 System Requirements

- **Java**: JDK 8 or higher
- **RAM**: 256 MB minimum
- **Disk Space**: ~5 MB for compiled files
- **OS**: Windows, Mac, or Linux

## 📊 Features Checklist

- ✅ 5 Real stocks (TCS, INFY, WIPRO, RELIANCE, HCL)
- ✅ Buy/Sell functionality
- ✅ Portfolio tracking
- ✅ Transaction history
- ✅ Profit/Loss calculation
- ✅ Real-time price updates
- ✅ Search functionality
- ✅ File persistence
- ✅ Professional GUI with Swing
- ✅ Error handling
- ✅ JTable for data display
- ✅ Multi-threaded price updates

## 🎓 Learning Outcomes

After using this platform, you've learned:
- ✅ Object-Oriented Programming in Java
- ✅ Encapsulation and data protection
- ✅ Collections and data structures
- ✅ File I/O operations
- ✅ Swing GUI development
- ✅ Multi-threaded programming
- ✅ Exception handling
- ✅ Design patterns (MVC, Singleton, Factory)

## 🚀 Next Steps

1. **Modify Stock Prices**: Edit Main.java to change initial prices
2. **Add More Stocks**: Add more entries in initializeStocks()
3. **Increase Initial Balance**: Change INITIAL_BALANCE in User.java
4. **Customize Colors**: Modify colors in MainFrame.java
5. **Add More Features**: Extend functionality as needed

## 📞 Support

For issues or questions:
1. Check the full README.md file
2. Review inline code comments
3. Check error messages in terminal
4. Verify all files are compiled

## ✨ Features Highlights

| Feature | Status |
|---------|--------|
| Stock Display | ✅ Implemented |
| Stock Search | ✅ Implemented |
| Buy Stocks | ✅ Implemented |
| Sell Stocks | ✅ Implemented |
| Portfolio View | ✅ Implemented |
| Profit/Loss Calc | ✅ Implemented |
| Transaction History | ✅ Implemented |
| File Persistence | ✅ Implemented |
| GUI/Swing | ✅ Implemented |
| Price Updates | ✅ Implemented |
| Error Handling | ✅ Implemented |
| OOP Concepts | ✅ Implemented |

---

**Ready to start trading? Run `java Main` and happy trading! 📈**
