// BankingSystem.java - Main Application Class
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BankingSystem {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/banking_system";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";
    
    private JFrame mainFrame;
    private Connection connection;
    
    public BankingSystem() {
        initializeDatabase();
        createMainWindow();
    }
    
    private void initializeDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Database connected successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Database connection failed: " + e.getMessage());
        }
    }
    
    private void createMainWindow() {
        mainFrame = new JFrame("Banking Management System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setLocationRelativeTo(null);
        
        // Create main panel with gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(41, 128, 185), 
                                                         0, getHeight(), new Color(109, 213, 250));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());
        
        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        JLabel titleLabel = new JLabel("🏦 BANKING MANAGEMENT SYSTEM", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        
        // Create stylish buttons
        JButton createAccountBtn = createStyledButton("Create Account", new Color(46, 204, 113));
        JButton depositBtn = createStyledButton("Deposit Money", new Color(52, 152, 219));
        JButton withdrawBtn = createStyledButton("Withdraw Money", new Color(231, 76, 60));
        JButton checkBalanceBtn = createStyledButton("Check Balance", new Color(155, 89, 182));
        JButton transactionHistoryBtn = createStyledButton("Transaction History", new Color(241, 196, 15));
        JButton exitBtn = createStyledButton("Exit", new Color(149, 165, 166));
        
        // Add action listeners
        createAccountBtn.addActionListener(e -> showCreateAccountDialog());
        depositBtn.addActionListener(e -> showDepositDialog());
        withdrawBtn.addActionListener(e -> showWithdrawDialog());
        checkBalanceBtn.addActionListener(e -> showCheckBalanceDialog());
        transactionHistoryBtn.addActionListener(e -> showTransactionHistoryDialog());
        exitBtn.addActionListener(e -> System.exit(0));
        
        // Add buttons to panel
        gbc.gridx = 0; gbc.gridy = 0; buttonPanel.add(createAccountBtn, gbc);
        gbc.gridx = 1; gbc.gridy = 0; buttonPanel.add(depositBtn, gbc);
        gbc.gridx = 0; gbc.gridy = 1; buttonPanel.add(withdrawBtn, gbc);
        gbc.gridx = 1; gbc.gridy = 1; buttonPanel.add(checkBalanceBtn, gbc);
        gbc.gridx = 0; gbc.gridy = 2; buttonPanel.add(transactionHistoryBtn, gbc);
        gbc.gridx = 1; gbc.gridy = 2; buttonPanel.add(exitBtn, gbc);
        
        // Footer Panel
        JPanel footerPanel = new JPanel();
        footerPanel.setOpaque(false);
        JLabel footerLabel = new JLabel("Developed by Ojas Mehta | Java & MySQL", JLabel.CENTER);
        footerLabel.setForeground(Color.WHITE);
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        footerPanel.add(footerLabel);
        
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }
    
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 50));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createRaisedBorderBorder());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.brighter());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });
        
        return button;
    }
    
    private void showCreateAccountDialog() {
        JDialog dialog = new JDialog(mainFrame, "Create New Account", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(mainFrame);
        dialog.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JTextField nameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField phoneField = new JTextField(20);
        JTextField initialDepositField = new JTextField(20);
        
        gbc.gridx = 0; gbc.gridy = 0; dialog.add(new JLabel("Full Name:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; dialog.add(nameField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; dialog.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; dialog.add(emailField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; dialog.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; dialog.add(phoneField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; dialog.add(new JLabel("Initial Deposit:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; dialog.add(initialDepositField, gbc);
        
        JButton createBtn = new JButton("Create Account");
        JButton cancelBtn = new JButton("Cancel");
        
        createBtn.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();
                String phone = phoneField.getText().trim();
                double initialDeposit = Double.parseDouble(initialDepositField.getText().trim());
                
                if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Please fill all fields!");
                    return;
                }
                
                if (initialDeposit < 500) {
                    JOptionPane.showMessageDialog(dialog, "Minimum initial deposit is ₹500!");
                    return;
                }
                
                long accountNumber = createAccount(name, email, phone, initialDeposit);
                if (accountNumber > 0) {
                    JOptionPane.showMessageDialog(dialog, 
                        "Account created successfully!\nAccount Number: " + accountNumber);
                    dialog.dispose();
                }
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter valid amount!");
            }
        });
        
        cancelBtn.addActionListener(e -> dialog.dispose());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createBtn);
        buttonPanel.add(cancelBtn);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; 
        dialog.add(buttonPanel, gbc);
        
        dialog.setVisible(true);
    }
    
    private void showDepositDialog() {
        String accountStr = JOptionPane.showInputDialog(mainFrame, "Enter Account Number:");
        if (accountStr == null || accountStr.trim().isEmpty()) return;
        
        String amountStr = JOptionPane.showInputDialog(mainFrame, "Enter Deposit Amount:");
        if (amountStr == null || amountStr.trim().isEmpty()) return;
        
        try {
            long accountNumber = Long.parseLong(accountStr.trim());
            double amount = Double.parseDouble(amountStr.trim());
            
            if (amount <= 0) {
                JOptionPane.showMessageDialog(mainFrame, "Amount must be positive!");
                return;
            }
            
            if (deposit(accountNumber, amount)) {
                JOptionPane.showMessageDialog(mainFrame, 
                    "₹" + amount + " deposited successfully!");
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(mainFrame, "Please enter valid numbers!");
        }
    }
    
    private void showWithdrawDialog() {
        String accountStr = JOptionPane.showInputDialog(mainFrame, "Enter Account Number:");
        if (accountStr == null || accountStr.trim().isEmpty()) return;
        
        String amountStr = JOptionPane.showInputDialog(mainFrame, "Enter Withdrawal Amount:");
        if (amountStr == null || amountStr.trim().isEmpty()) return;
        
        try {
            long accountNumber = Long.parseLong(accountStr.trim());
            double amount = Double.parseDouble(amountStr.trim());
            
            if (amount <= 0) {
                JOptionPane.showMessageDialog(mainFrame, "Amount must be positive!");
                return;
            }
            
            if (withdraw(accountNumber, amount)) {
                JOptionPane.showMessageDialog(mainFrame, 
                    "₹" + amount + " withdrawn successfully!");
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(mainFrame, "Please enter valid numbers!");
        }
    }
    
    private void showCheckBalanceDialog() {
        String accountStr = JOptionPane.showInputDialog(mainFrame, "Enter Account Number:");
        if (accountStr == null || accountStr.trim().isEmpty()) return;
        
        try {
            long accountNumber = Long.parseLong(accountStr.trim());
            double balance = getBalance(accountNumber);
            
            if (balance >= 0) {
                String accountDetails = getAccountDetails(accountNumber);
                JOptionPane.showMessageDialog(mainFrame, accountDetails);
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(mainFrame, "Please enter valid account number!");
        }
    }
    
    private void showTransactionHistoryDialog() {
        String accountStr = JOptionPane.showInputDialog(mainFrame, "Enter Account Number:");
        if (accountStr == null || accountStr.trim().isEmpty()) return;
        
        try {
            long accountNumber = Long.parseLong(accountStr.trim());
            showTransactionHistory(accountNumber);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(mainFrame, "Please enter valid account number!");
        }
    }
    
    // Database Operations
    private long createAccount(String name, String email, String phone, double initialDeposit) {
        try {
            // Generate account number
            long accountNumber = System.currentTimeMillis() % 1000000000L;
            
            String sql = "INSERT INTO accounts (account_number, full_name, email, phone, balance, created_date) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, accountNumber);
            pstmt.setString(2, name);
            pstmt.setString(3, email);
            pstmt.setString(4, phone);
            pstmt.setDouble(5, initialDeposit);
            pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            
            int result = pstmt.executeUpdate();
            if (result > 0) {
                // Record initial deposit transaction
                recordTransaction(accountNumber, "DEPOSIT", initialDeposit, "Initial deposit");
                return accountNumber;
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(mainFrame, "Error creating account: " + e.getMessage());
        }
        return -1;
    }
    
    private boolean deposit(long accountNumber, double amount) {
        try {
            // Check if account exists
            if (getBalance(accountNumber) < 0) return false;
            
            String sql = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setDouble(1, amount);
            pstmt.setLong(2, accountNumber);
            
            int result = pstmt.executeUpdate();
            if (result > 0) {
                recordTransaction(accountNumber, "DEPOSIT", amount, "Cash deposit");
                return true;
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(mainFrame, "Error processing deposit: " + e.getMessage());
        }
        return false;
    }
    
    private boolean withdraw(long accountNumber, double amount) {
        try {
            double currentBalance = getBalance(accountNumber);
            if (currentBalance < 0) return false;
            
            if (currentBalance < amount) {
                JOptionPane.showMessageDialog(mainFrame, "Insufficient balance!");
                return false;
            }
            
            String sql = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setDouble(1, amount);
            pstmt.setLong(2, accountNumber);
            
            int result = pstmt.executeUpdate();
            if (result > 0) {
                recordTransaction(accountNumber, "WITHDRAWAL", amount, "Cash withdrawal");
                return true;
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(mainFrame, "Error processing withdrawal: " + e.getMessage());
        }
        return false;
    }
    
    private double getBalance(long accountNumber) {
        try {
            String sql = "SELECT balance FROM accounts WHERE account_number = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, accountNumber);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("balance");
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Account not found!");
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(mainFrame, "Error retrieving balance: " + e.getMessage());
        }
        return -1;
    }
    
    private String getAccountDetails(long accountNumber) {
        try {
            String sql = "SELECT * FROM accounts WHERE account_number = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, accountNumber);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                StringBuilder details = new StringBuilder();
                details.append("Account Details:\n");
                details.append("Account Number: ").append(rs.getLong("account_number")).append("\n");
                details.append("Full Name: ").append(rs.getString("full_name")).append("\n");
                details.append("Email: ").append(rs.getString("email")).append("\n");
                details.append("Phone: ").append(rs.getString("phone")).append("\n");
                details.append("Current Balance: ₹").append(String.format("%.2f", rs.getDouble("balance"))).append("\n");
                details.append("Account Created: ").append(rs.getTimestamp("created_date"));
                
                return details.toString();
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(mainFrame, "Error retrieving account details: " + e.getMessage());
        }
        return "Account not found!";
    }
    
    private void recordTransaction(long accountNumber, String type, double amount, String description) {
        try {
            String sql = "INSERT INTO transactions (account_number, transaction_type, amount, description, transaction_date) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, accountNumber);
            pstmt.setString(2, type);
            pstmt.setDouble(3, amount);
            pstmt.setString(4, description);
            pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("Error recording transaction: " + e.getMessage());
        }
    }
    
    private void showTransactionHistory(long accountNumber) {
        try {
            String sql = "SELECT * FROM transactions WHERE account_number = ? ORDER BY transaction_date DESC LIMIT 10";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, accountNumber);
            
            ResultSet rs = pstmt.executeQuery();
            
            StringBuilder history = new StringBuilder();
            history.append("Last 10 Transactions for Account: ").append(accountNumber).append("\n\n");
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            boolean hasTransactions = false;
            
            while (rs.next()) {
                hasTransactions = true;
                history.append("Date: ").append(dateFormat.format(rs.getTimestamp("transaction_date"))).append("\n");
                history.append("Type: ").append(rs.getString("transaction_type")).append("\n");
                history.append("Amount: ₹").append(String.format("%.2f", rs.getDouble("amount"))).append("\n");
                history.append("Description: ").append(rs.getString("description")).append("\n");
                history.append("-----------------------------------\n");
            }
            
            if (!hasTransactions) {
                history.append("No transactions found for this account.");
            }
            
            JTextArea textArea = new JTextArea(history.toString());
            textArea.setEditable(false);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500, 400));
            
            JOptionPane.showMessageDialog(mainFrame, scrollPane, "Transaction History", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(mainFrame, "Error retrieving transaction history: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new BankingSystem();
        });
    }
}
