package View;

import Controller.PortfolioController;
import Model.IPortfolio;
import Model.IStockHolding;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class PortfolioPanel extends JPanel implements Observer, IPortfolioPanel {

    private JTable table;
    private DefaultTableModel model;
    private JButton addButton;
    private JButton sellButton;
    private TextField buyTickerName;
    private TextField buyShareAmount;
    private TextField sellTickerName;
    private TextField sellTickerShareAmount;
    private JLabel totalValueLabel;
    private Double totalValue = 0.0;
    private IPortfolio portfolio;

    public PortfolioPanel(IPortfolio portfolio) {
        this.portfolio = portfolio;
        setLayout(null);
        setAddStock();
        setSellStock();
        initializeTable();
        setTotalValueLabel();
    }


    private void setAddStock() {

        JLabel buyTickerNameLabel = new JLabel("Ticker name");
        buyTickerNameLabel.setBounds(100, 20, 100, 20);
        buyTickerName = new TextField("MSFT", 20);
        buyTickerName.setBounds(200, 20, 100, 20);
        JLabel buyShareAmountLabel = new JLabel("Number of shares");
        buyShareAmountLabel.setBounds(300, 20, 120, 20);
        buyShareAmount = new TextField("", 20);
        buyShareAmount.setBounds(420, 20, 100, 20);
        addButton = new JButton("Buy");
        addButton.addActionListener(new PortfolioController(this,portfolio));

        addButton.setBounds(530, 20, 100, 20);

        add(buyTickerNameLabel);
        add(buyShareAmountLabel);
        add(buyTickerName);
        add(buyShareAmount);
        add(addButton);

    }

    private void setSellStock() {
        JLabel sellTickerNameLabel = new JLabel("Ticker name");
        sellTickerNameLabel.setBounds(100, 50, 100, 20);
        sellTickerName = new TextField("MSFT", 20);
        sellTickerName.setBounds(200, 50, 100, 20);
        JLabel sellAmountLabel = new JLabel("Number of shares");
        sellAmountLabel.setBounds(300, 50, 120, 20);
        sellTickerShareAmount = new TextField("", 20);
        sellTickerShareAmount.setBounds(420, 50, 100, 20);
        sellButton = new JButton("Sell");
        sellButton.addActionListener(new PortfolioController(this,portfolio));
        sellButton.setBounds(530, 50, 100, 20);

        add(sellTickerNameLabel);
        add(sellTickerName);
        add(sellAmountLabel);
        add(sellTickerShareAmount);
        add(sellButton);

    }

    private void setTotalValueLabel() {
        totalValueLabel = new JLabel("Total portfolio value is: " + totalValue.toString());
        totalValueLabel.setBounds(550, 520, 300, 20);
        add(totalValueLabel);
    }

    private void initializeTable() {
        System.out.println("Initialize Table Was Called!");
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setFocusable(true);

        String[] columnNames = {"Ticker Symbol", "Number Of Shares", "Price per Share", "Value of Holding"};

        model = new DefaultTableModel(null, columnNames) {

            public Class<?> getColumnClass(int c) {
                return getValueAt(0, c).getClass();
            }
        };
        table = new JTable(model);
        table.setEnabled(false);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
        table.setRowSorter(sorter);
        scrollPane.setBounds(20, 100, 750, 400);
        scrollPane.setViewportView(table);
        add(scrollPane);

    }

    public void addStock(IStockHolding sh) {

        model.addRow(new Object[]{sh.getTickerSymbol(), sh.getNumberOfShares(), sh.getShareValue(), sh.getValueOfHolding()});
    }

    public void setModel(DefaultTableModel model) {
        this.model = model;
    }

    public TextField getSellTickerName() {
        return sellTickerName;
    }

    public TextField getSellTickerShareAmount() {
        return sellTickerShareAmount;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public TextField getBuyTickerName() {
        return buyTickerName;
    }

    public TextField getBuyShareAmount() {
        return buyShareAmount;
    }

    public AbstractButton getSellButton() {
        return sellButton;
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
        List<IStockHolding> stockArray = portfolio.getStocks();
        for (IStockHolding sh : stockArray) {
            addStock(sh);
        }
        totalValueLabel.setText("Total portfolio value is: " + String.format("%.2f", portfolio.getTotalValue()));
    }
}
