package View;

import Model.IQuoteServer;
import Model.StrathQuoteServer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class PortfolioPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private JButton addButton;

    public PortfolioPanel() {
        setLayout(null);


        setAddStock();
        initializeTable();
    }

    private void setAddStock() {
        TextField inputPortfolioName = new TextField("", 20);
        inputPortfolioName.setBounds(100, 20, 100, 20);
        TextField inputShareAmount = new TextField("", 20);
        inputShareAmount.setBounds(200, 20, 100, 20);
        addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            addStock(inputPortfolioName.getText(), inputShareAmount.getText());
        });
        addButton.setBounds(300, 20, 100, 20);


        add(inputPortfolioName);
        add(inputShareAmount);
        add(addButton);
    }

    private void initializeTable() {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setFocusable(true);

        String[] columnNames = {"Ticker Symbol", "Number Of Shares", "Price per Share", "Value of Holding"};

        // StatsManager m = new StatsManager();
        // StatsObject stats = m.getStatsObject();
        // if (stats != null) {
        model = new DefaultTableModel(null, columnNames) {

            private static final long serialVersionUID = 1L;

            public Class<?> getColumnClass(int c) {
                return getValueAt(0, c).getClass();
            }
        };
        table = new JTable(model);
        table.setEnabled(false);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
        table.setRowSorter(sorter);
        scrollPane.setBounds(20, 240, 750, 300);
        scrollPane.setViewportView(table);
        add(scrollPane);
        //  }
    }

    private void addStock(String tickerSymbol, String numberOfShares) {
        IQuoteServer qs = new StrathQuoteServer();
        try {
            String pricePerShare = qs.getLastValue(tickerSymbol);
            Double totalValueInteger = Double.valueOf(numberOfShares) * Double.valueOf(pricePerShare);
            String totalValue = totalValueInteger.toString();
            model.addRow(new Object[]{tickerSymbol, numberOfShares, pricePerShare,totalValue});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
