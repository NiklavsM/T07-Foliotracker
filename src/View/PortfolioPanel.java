package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class PortfolioPanel extends JPanel implements Observer {

    private JTable table;
    private DefaultTableModel model;
    private JButton addButton;
    private JButton closeButton;
    private TextField inputTickerName;
    private TextField inputShareAmount;
    private JTabbedPane parentTabPane;

    public PortfolioPanel(final JTabbedPane tabPane) {
        setLayout(null);

        //parentTabPane = tabPane;
        System.out.println(parentTabPane);
        setAddStock();
        initializeTable();
        //setUpCloseButton();
    }

    public void setParentTabPane(JTabbedPane tp){
        parentTabPane = tp;
        System.out.println("Does this");
        setupCloseButton(tp);
    }

    private void setAddStock() {
        JLabel tickerNameLabel = new JLabel("Ticker name");
        tickerNameLabel.setBounds(100, 20, 100, 20);
        inputTickerName = new TextField("MSFT", 20);
        inputTickerName.setBounds(200, 20, 100, 20);
        JLabel shareAmountLabel = new JLabel("Number of shares");
        shareAmountLabel.setBounds(300, 20, 120, 20);
        inputShareAmount = new TextField("", 20);
        inputShareAmount.setBounds(420, 20, 100, 20);
        addButton = new JButton("Add");

        addButton.setBounds(530, 20, 100, 20);
        closeButton = new JButton("Close");
        closeButton.setBounds(400, 580, 120, 20);

        add(tickerNameLabel);
        add(shareAmountLabel);
        add(inputTickerName);
        add(inputShareAmount);
        add(addButton);
        add(closeButton);
    }

    private void initializeTable() {
        System.out.println("Initialize Table Was Called!");
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setFocusable(true);

        String[] columnNames = {"Ticker Symbol", "Number Of Shares", "Price per Share", "Value of Holding"};

        // StatsManager m = new StatsManager();
        // StatsObject stats = m.getStatsObject();
        // if (stats != null) {
        model = new DefaultTableModel(null, columnNames) {

            //private static final long serialVersionUID = 1L;

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

    public void addStock(String tickerSymbol, String numberOfShares, String pricePerShare, String totalValue) {

        model.addRow(new Object[]{tickerSymbol, numberOfShares, pricePerShare, totalValue});
        System.out.println("TESST plese work " + parentTabPane.indexOfTabComponent(PortfolioPanel.this));

    }

    public void setupCloseButton(JTabbedPane tp){
        closeButton.addActionListener(e->{
            int i = tp.indexOfTabComponent(this.table);
            //parentTabPane.remove(0);
            System.out.println(this + "DOG2" + i);
            if (i != -1) {
                System.out.println("DOG1");
                tp.remove(i);
            }
        });
    }

    public JTable getTable() {
        return table;
    }

    public JButton getCloseButton() {
        return closeButton;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

//    public DefaultTableModel getModel() {
//        return model;
//    }

    public void setModel(DefaultTableModel model) {
        this.model = model;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public void setAddButton(JButton addButton) {
        this.addButton = addButton;
    }

    public TextField getInputTickerName() {
        return inputTickerName;
    }

    public void setInputTickerName(TextField inputTickerName) {
        this.inputTickerName = inputTickerName;
    }

    public TextField getInputShareAmount() {
        return inputShareAmount;
    }

    public void setInputShareAmount(TextField inputShareAmount) {
        this.inputShareAmount = inputShareAmount;
    }

    void clearTable() {
        int rowCount = model.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String[][]) {
            clearTable();
            String[][] stockArray = (String[][]) arg;
            for (int i = 0; i < stockArray.length; i++) {
                addStock(stockArray[i][0], stockArray[i][1], stockArray[i][2], stockArray[i][3]);
            }
        }
    }
}
