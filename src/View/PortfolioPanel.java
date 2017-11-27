package View;

import Controller.PortfolioListener;
import Model.IPortfolio;
import Model.IStockHolding;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class PortfolioPanel extends JPanel implements Observer, IPortfolioPanel {

    private DefaultTableModel model;
    private TextField addShareTickerSymbol;
    private TextField addShareName;
    private TextField buySellTickerSymbol;
    private TextField buySellTickerShareAmount;
    private JLabel totalValueLabel;
    private IPortfolio portfolio;

    public PortfolioPanel(IPortfolio portfolio) {
        this.portfolio = portfolio;
        setLayout(null);
        setButtons();
        initializeTable();
        setTotalValueLabel();
        update(null, null);
    }


    private void setButtons() {
        ActionListener portfolioController = new PortfolioListener(this, portfolio);

        JLabel buyTickerNameLabel = new JLabel("Ticker name");
        buyTickerNameLabel.setBounds(100, 20, 100, 20);
        addShareTickerSymbol = new TextField("", 20);
        addShareTickerSymbol.setBounds(200, 20, 100, 20);
        JLabel addShareNameLabel = new JLabel("Share name");
        addShareNameLabel.setBounds(300, 20, 120, 20);
        addShareName = new TextField("", 20);
        addShareName.setBounds(420, 20, 100, 20);
        JButton buyButton = new JButton("Buy");
        buyButton.addActionListener(portfolioController);
        buyButton.setBounds(530, 50, 60, 20);

        add(buyTickerNameLabel);
        add(addShareNameLabel);
        add(addShareTickerSymbol);
        add(addShareName);
        add(buyButton);

        JLabel sellTickerNameLabel = new JLabel("Ticker name");
        sellTickerNameLabel.setBounds(100, 50, 100, 20);
        buySellTickerSymbol = new TextField("", 20);
        buySellTickerSymbol.setBounds(200, 50, 100, 20);
        JLabel sellAmountLabel = new JLabel("Number of shares");
        sellAmountLabel.setBounds(300, 50, 120, 20);
        buySellTickerShareAmount = new TextField("", 20);
        buySellTickerShareAmount.setBounds(420, 50, 100, 20);
        JButton sellButton = new JButton("Sell");
        sellButton.addActionListener(portfolioController);
        sellButton.setBounds(590, 50, 60, 20);

        add(sellTickerNameLabel);
        add(buySellTickerSymbol);
        add(sellAmountLabel);
        add(buySellTickerShareAmount);
        add(sellButton);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(portfolioController);
        addButton.setBounds(530, 20, 60, 20);
        add(addButton);

    }

    private void setTotalValueLabel() {
        totalValueLabel = new JLabel("Total portfolio value is: 0.00");
        totalValueLabel.setBounds(550, 520, 300, 20);
        add(totalValueLabel);
    }

    private void initializeTable() {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setFocusable(true);

        String[] columnNames = {"Ticker Symbol", "Share name", "Number Of Shares", "Price per Share", "Value of Holding"};

        model = new DefaultTableModel(null, columnNames) {

            public Class<?> getColumnClass(int c) {
                return getValueAt(0, c).getClass();
            }
        };
        JTable table = new JTable(model);
        table.setEnabled(false);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        scrollPane.setBounds(20, 100, 750, 400);
        scrollPane.setViewportView(table);
        add(scrollPane);

    }

    private void addStock(IStockHolding sh) {

        model.addRow(new Object[]{sh.getTickerSymbol(), sh.getShareName(), sh.getNumberOfShares(), sh.getShareValue(), sh.getValueOfHolding()});
    }

    public void setModel(DefaultTableModel model) {
        this.model = model;
    }

    public TextField getBuySellTickerSymbol() {
        return buySellTickerSymbol;
    }

    public TextField getBuySellShareAmount() {
        return buySellTickerShareAmount;
    }

    public TextField getAddShareTickerSymbol() {
        return addShareTickerSymbol;
    }

    public TextField getAddShareName() {
        return addShareName;
    }

    private void clearTable() {
        int rowCount = model.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }
    }

    public void popupErrorMessage(String errorText) {
        JOptionPane.showMessageDialog(null, errorText);
    }


    @Override
    public void update(Observable o, Object arg) {
        clearTable();
        Map<String, IStockHolding> stocks = portfolio.getStocks();
        for (String name : stocks.keySet()) {
            addStock(stocks.get(name));
        }
        totalValueLabel.setText("Total portfolio value is: " + String.format("%.2f", portfolio.getTotalValue()));
    }
}
