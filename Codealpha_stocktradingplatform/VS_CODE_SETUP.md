# Stock Trading Platform - VS Code Setup & Execution Guide

## ✅ Project Status: READY TO RUN

All 7 classes have been compiled successfully and are ready to execute.

## 🛠️ VS Code Setup

### Step 1: Install Required Extension

1. **Open VS Code**
2. **Open Extensions** (Ctrl+Shift+X)
3. **Search for**: "Extension Pack for Java"
4. **Click Install** (by Microsoft)

This installs:
- Language Support for Java (Red Hat)
- Debugger for Java
- Test Runner for Java
- Maven for Java
- Project Manager for Java
- Visual Studio IntelliCode

### Step 2: Verify Java Installation

1. **Open Terminal** in VS Code: Ctrl+`
2. **Run**: `java -version`
3. **Expected Output**:
   ```
   java version "1.8.0_xxx" or higher
   Java(TM) SE Runtime Environment
   ```

If "java is not recognized":
- Download Java from: https://www.oracle.com/java/technologies/downloads/
- Install JDK 8 or higher
- Add to system PATH

### Step 3: Open Project Folder

1. **File → Open Folder** (Ctrl+K, Ctrl+O)
2. **Navigate to**: `c:\Intern`
3. **Click Select Folder**
4. **Trust the folder** if prompted

### Step 4: Verify All Files Present

In VS Code Explorer, you should see:
```
Intern/
├── Stock.java
├── User.java
├── Portfolio.java
├── Transaction.java
├── FileManager.java
├── MainFrame.java
├── Main.java
├── README.md
├── QUICKSTART.md
└── ARCHITECTURE.md
```

All files should show no red underlines (no compilation errors).

## ▶️ Running the Application - 3 Methods

### Method 1: Terminal Command (Recommended)

**Step 1: Open Terminal**
- Press: `Ctrl+` (backtick)
- Or: View → Terminal

**Step 2: Compile (if needed)**
```bash
cd c:\Intern
javac Stock.java User.java Portfolio.java Transaction.java FileManager.java MainFrame.java Main.java
```

**Step 3: Run**
```bash
java Main
```

**Expected Output:**
```
=== Stock Trading Platform ===
Available Stocks initialized:
  - TCS - Tata Consultancy Services ($3500.00)
  - INFY - Infosys ($1650.00)
  - WIPRO - Wipro Limited ($425.00)
  - RELIANCE - Reliance Industries ($2500.00)
  - HCL - HCL Technologies ($1200.00)
```

A GUI window opens with the trading platform.

---

### Method 2: VS Code Run Button

**Step 1: Open Main.java**
- Click on `Main.java` in Explorer
- Or use File → Open File

**Step 2: Look for Run Link**
- Hover above: `public static void main(String[] args)`
- You should see a **"Run"** link
- Click **Run**

**Result:** Application launches immediately

---

### Method 3: VS Code Debug Mode

**Step 1: Open Main.java**
- File → Open File → Main.java

**Step 2: Set Breakpoint (Optional)**
- Click left of any line number to set breakpoint (red dot)

**Step 3: Debug**
- Hover above `public static void main`
- Click **"Debug"** link

**Result:** 
- Application runs with debugger
- Can inspect variables
- Can step through code

---

## 🧪 Testing the Application

### Quick Test: Buy and Sell

1. **GUI Loads?** ✓
   - Check: Main window appears with tabs
   
2. **Stock Market Tab**
   - Check: See 5 stocks (TCS, INFY, WIPRO, RELIANCE, HCL)
   - Check: Prices display correctly
   - Check: Prices update every 5 seconds

3. **Buy Stock**
   - Go to "Trading" tab
   - Select "TCS" from dropdown
   - Enter quantity: "5"
   - Click "Buy"
   - Check: Success message appears
   - Check: Balance decreased in top
   - Check: Stock appears in Portfolio tab

4. **Sell Stock**
   - Go to "Trading" tab (Sell section)
   - Select "TCS" from dropdown
   - Enter quantity: "2"
   - Click "Sell"
   - Check: Success message
   - Check: Balance increased
   - Check: Portfolio updated

5. **Transaction History**
   - Go to "Transaction History" tab
   - Check: Both BUY and SELL transactions visible
   - Check: Timestamp shows correctly

### Data Persistence Test

1. **Make transactions** (Buy/Sell a few stocks)
2. **Close application** (Click Exit button)
3. **Reopen application** (Run `java Main` again)
4. **Check**: Transaction history still there
5. **Verify**: All past transactions loaded

---

## ⚙️ Compilation Verification

### Check Compiled Classes

```bash
cd c:\Intern
dir *.class
```

You should see:
```
Stock.class
User.class
Portfolio.class
Transaction.java
FileManager.class
MainFrame.class
Main.class
```

If missing, recompile:
```bash
javac *.java
```

### Fix Compilation Errors

**Error: "cannot find symbol"**
```bash
# Solution: Recompile all files
javac Stock.java User.java Portfolio.java Transaction.java FileManager.java MainFrame.java Main.java
```

**Error: "class not found"**
```bash
# Solution: Make sure you're in correct directory
cd c:\Intern
java Main
```

**Error: "java: not found"**
```bash
# Solution: Add Java to PATH
# Windows: Search "Environment Variables" → Edit PATH
# Add: C:\Program Files\Java\jdk-xx\bin
```

---

## 📱 GUI Navigation Guide

### Main Window Layout

```
┌─────────────────────────────────────────────┐
│ Stock Trading Platform - Investor           │ (Title Bar)
├─────────────────────────────────────────────┤
│ Balance: $100k │ Portfolio: $0 │ P/L: $0 │ │ (Top Info)
├─────────────────────────────────────────────┤
│ Stock|Portfolio|Transactions|Trading        │ (Tabs)
├─────────────────────────────────────────────┤
│                                             │
│          (Tab Content Area)                 │
│                                             │
├─────────────────────────────────────────────┤
│                    [Exit]                   │ (Bottom Button)
└─────────────────────────────────────────────┘
```

### Tab Description

| Tab | Purpose |
|-----|---------|
| Stock Market | View all available stocks with real-time prices |
| Portfolio | See your current holdings and valuations |
| Transactions | View complete transaction history |
| Trading | Buy and sell stocks |

---

## 🔍 Troubleshooting

### Problem: GUI doesn't open

**Symptom:** Running `java Main` but no window appears

**Solution 1:** Check for errors in terminal
```bash
# Look for any error messages
# Common: "Exception in thread main"
```

**Solution 2:** Use explicit Swing LookAndFeel
```bash
java -Dswing.systemlaf=com.sun.java.swing.plaf.windows.WindowsLookAndFeel Main
```

**Solution 3:** Update graphics drivers
- Update your GPU drivers
- Or: Use basic rendering
```bash
java -Dsun.java2d.uiScale=1.0 Main
```

---

### Problem: Prices don't update

**Symptom:** Prices stay same, don't change every 5 seconds

**Solution:** 
- Wait 5+ seconds
- Switch tabs and come back
- Application is running background thread - just wait

---

### Problem: Can't buy stock - "Insufficient balance"

**Symptom:** Trying to buy but getting error

**Solution:**
1. Check balance at top of window
2. Calculate: Quantity × Price = Cost
3. Make sure: Balance > Cost
4. Example: Buy 50 TCS @ $3500 = $175,000
   - But only have $100,000 - ERROR ✓

---

### Problem: File operations errors

**Symptom:** "Error saving transaction" in terminal

**Solution:**
1. Check folder permissions
2. Make sure c:\Intern is writable
3. Close the files if open in other programs
4. Restart application

---

## 📁 File Descriptions

### Source Code Files

| File | Lines | Purpose |
|------|-------|---------|
| Stock.java | 67 | Stock data model |
| User.java | 84 | User account model |
| Portfolio.java | 145 | Portfolio management |
| Transaction.java | 80 | Transaction record |
| FileManager.java | 106 | File I/O operations |
| MainFrame.java | 628 | Swing GUI interface |
| Main.java | 48 | Entry point |

### Generated Files (created at runtime)

| File | Purpose |
|------|---------|
| transactions.txt | Stores all buy/sell records |
| portfolio.txt | Stores portfolio snapshots |
| *.class | Compiled Java bytecode |

### Documentation Files

| File | Purpose |
|------|---------|
| README.md | Complete project documentation |
| QUICKSTART.md | Quick start guide |
| ARCHITECTURE.md | System design and class details |

---

## 📊 Command Reference

### Compilation Commands

```bash
# Compile all files
javac *.java

# Compile specific files
javac Stock.java Main.java

# Compile with warnings
javac -Xlint:all *.java
```

### Execution Commands

```bash
# Run application
java Main

# Run with debug output
java -verbose Main

# Run with custom memory
java -Xmx512m Main

# Run with specific Look and Feel (if GUI issues)
java -Dswing.systemlaf=com.sun.java.swing.plaf.windows.WindowsLookAndFeel Main
```

### File Operations

```bash
# List all files
dir

# List only .class files
dir *.class

# List only .java files
dir *.java

# Delete compiled files
del *.class

# Recompile everything
del *.class && javac *.java && java Main
```

---

## 🎓 Learning Tips

1. **Read the Comments**: Each class has detailed comments
2. **Try Modifications**: Edit initial prices in Main.java
3. **Add Features**: Extend portfolio calculations
4. **Debug**: Use VS Code debugger to step through code
5. **Experiment**: Buy/sell to see profit/loss calculations

---

## 📞 Support Resources

| Resource | Link |
|----------|------|
| Java Documentation | https://docs.oracle.com/javase/ |
| Swing Tutorial | https://docs.oracle.com/javase/tutorial/uiswing/ |
| VS Code Java Help | https://code.visualstudio.com/docs/languages/java |
| Stack Overflow | https://stackoverflow.com/questions/tagged/java |

---

## ✨ Key Features Ready

- ✅ All 7 classes compiled and tested
- ✅ Full GUI with Swing components
- ✅ Buy/Sell functionality working
- ✅ Portfolio tracking operational
- ✅ Transaction history persistence
- ✅ Real-time price updates (every 5 seconds)
- ✅ Error handling in place
- ✅ File I/O functional

---

**Your Stock Trading Platform is ready to use! 🚀**

Run `java Main` in the terminal and start trading!

