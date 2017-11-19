package View;

import Model.IPortfolio;
import Model.IStockHolding;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class PortfolioPanel extends JPanel implements Observer,IPortfolioPanel {

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

    public PortfolioPanel() {
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

        addButton.setBounds(530, 20, 100, 20);


        add(buyTickerNameLabel);
        add(buyShareAmountLabel);
        add(buyTickerName);
        add(buyShareAmount);
        add(addButton);

    }

    private void setSellStock(){
        JLabel sellTickerNameLabel = new JLabel("Ticker name");
        sellTickerNameLabel.setBounds(100, 50, 100, 20);
        sellTickerName = new TextField("MSFT", 20);
        sellTickerName.setBounds(200, 50, 100, 20);
        JLabel sellAmountLabel = new JLabel("Number of shares");
        sellAmountLabel.setBounds(300, 50, 120, 20);
        sellTickerShareAmount = new TextField("", 20);
        sellTickerShareAmount.setBounds(420, 50, 100, 20);
        sellButton = new JButton("Sell");
        sellButton.setBounds(530, 50, 100, 20);

        add(sellTickerNameLabel);
        add(sellTickerName);
        add(sellAmountLabel);
        add(sellTickerShareAmount);
        add(sellButton);

    }
    private void setTotalValueLabel(){
        totalValueLabel = new JLabel(totalValue.toString());
        totalValueLabel.setBounds(300, 450, 300, 20);
        add(totalValueLabel);
    }
//    private void setTotalValueLabel(){
//        closeButton = new JButton("Close");
//        closeButton.setBounds(250, 580, 120, 20);
//        deleteButton = new JButton("Delete");
//        deleteButton.setBounds(400, 580, 120, 20);
//        //add(deleteButton);
//        //add(closeButton);
//    }

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
        scrollPane.setBounds(20, 100, 750, 300);
        scrollPane.setViewportView(table);
        add(scrollPane);
        //  }
    }

    public void addStock(IStockHolding sh) {

        model.addRow(new Object[]{sh.getTickerSymbol(), sh.getNumberOfShares(), sh.getShareValue(), sh.getValueOfHolding()});
    }

//    public void setupCloseButton(JTabbedPane tp){
//      //  int index = tabPane.indexOfTab(this);
//        closeButton = new JButton("Close");
//        closeButton.setBounds(250, 580, 120, 20);
//       // tp.settab
//        closeButton.addActionListener(e->{
//            int i = tp.indexOfTabComponent(this);
//            //parentTabPane.remove(0);
//            System.out.println(this + "DOG2" + i);
//            //if (i != -1) {
//                System.out.println("DOG1");
//                tp.remove(tp.getSelectedIndex());
//            //}
//        });
//        add(closeButton);
//    }


//    public DefaultTableModel getModel() {
//        return model;
//    }

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


    void clearTable() {
        int rowCount = model.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }
    }

    public void popupErrorMessage(String errorText){
        JOptionPane.showMessageDialog(null, errorText);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof IPortfolio) {
            clearTable();
            List<IStockHolding> stockArray = ((IPortfolio) arg).getStocks();
            for (IStockHolding sh : stockArray) {
                addStock(sh);
            }
            totalValueLabel.setText("Total portfolio value is: " + String.format("%.2f",((IPortfolio) arg).getTotalValue()));
        }
    }
}
