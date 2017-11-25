package Model;

import javax.swing.*;
import java.io.*;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;


public class PortfolioContainer extends Observable implements IPortfolioContainer, Serializable {
    private Map<String, Portfolio> portfolioList = new ConcurrentHashMap<>();
    private Map<String, Double> sharePrices = new ConcurrentHashMap<>();

    public PortfolioContainer() {
        Thread updater = new Thread(new SharePriceUpdaterThread(this, sharePrices));
        updater.start();
    }

    public Map<String, IPortfolio> getPortfolios() {
        return new ConcurrentHashMap<>(portfolioList);
    }

    private Map<String, Portfolio> getPortfolioMap() {
        return new ConcurrentHashMap<>(portfolioList);
    }


    //      requires: tickerSymbol != null && value != null
//      modifies: this
//      effects: Sets the specified share price
    public void setSharePrice(String tickerSymbol, Double value) {
        assert (tickerSymbol != null) : "The ticker symbol must not be null";
        assert (value != null) : "The value must not be null";

        sharePrices.put(tickerSymbol, value);

    }


    //      requires: portfolio != null
//      modifies: this
//      effects: returns true if portfolio is added else returns false if portfolio with this name already exists
    public boolean addPortfolio(String portfolio) {

        assert (portfolio != null) : "Portfolio must not be null";
        if (portfolioList.get(portfolio) == null) {
            Portfolio newPortfolio1 = new Portfolio(portfolio, sharePrices);
            portfolioList.put(portfolio, newPortfolio1);
            setChangedAndNotify();
            return true;
        }
        return false;
    }

    //      modifies: this
//      effects: Updates all portfolios share prices.
    public void updateShareValues() {
        for (String key : portfolioList.keySet()) {
            portfolioList.get(key).updateShareValues();
        }
    }

    public boolean loadFromFile() {

        File saveFolder = new File("Saved Sessions");
        if (!saveFolder.exists()) {
            saveFolder.mkdir();
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(saveFolder);
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            saveFolder = fileChooser.getSelectedFile();
            try {
                ObjectInputStream newStateO = new ObjectInputStream(new FileInputStream(saveFolder.getPath()));
                portfolioList = ((PortfolioContainer) newStateO.readObject()).getPortfolioMap();
                newStateO.close();
                setChangedAndNotify();
                return true;

            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "File not found", "File not found", JOptionPane.ERROR_MESSAGE);
            } catch (IOException s) {
                JOptionPane.showMessageDialog(null, "File not right IO", "File not right IO",
                        JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Class not right", "Class not right", JOptionPane.ERROR_MESSAGE);
            }
        }
        return false;
    }

    public void saveToFile() {
        File saveFolder = new File("Saved Sessions");
        if (!saveFolder.exists()) {
            saveFolder.mkdir();
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(saveFolder);
        int fileAdded = fileChooser.showSaveDialog(null);
        if (fileAdded == JFileChooser.APPROVE_OPTION) {
            try {
                FileWriter fileCreater = new FileWriter(fileChooser.getSelectedFile() + ".bin");
                fileCreater.close();
                ObjectOutputStream stateObj = new ObjectOutputStream(
                        new FileOutputStream(fileChooser.getSelectedFile().getPath() + ".bin"));
                stateObj.writeObject(this);
                stateObj.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void setChangedAndNotify() {
        setChanged();
        notifyObservers();
    }


    //      requires: name != null
//      modifies: this
//      effects: returns true if the portfolio is removed from the List else return false.
    public boolean deletePortfolio(String name) {

        assert (name != null) : "The name must not be null";
        if (!portfolioList.containsKey(name)) {
            portfolioList.remove(name);
            setChangedAndNotify();
            return true;
        }
        return false;
    }

    //      requires: name != null
//      effects: returns true if the List contains this portfolio else return false.
    public boolean containsPortfolio(String name) {
        assert (name != null) : "The name must not contain an null value";
        return portfolioList.containsKey(name);
    }


}
