package Model;

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class PortfolioContainer extends Observable implements IPortfolioContainer, Serializable {
    private List<Portfolio> portfolioList = new ArrayList<>();
    private Map<String, Double> sharePrices;
    private Lock sharePriceLock = new ReentrantLock();

    public PortfolioContainer() {
        sharePrices = new Hashtable<>();
        Thread updater = new Thread(new SharePriceUpdaterThread(this, sharePrices));
        updater.start();
    }

    public List<Portfolio> getPortfolioList() {
        return new ArrayList<>(portfolioList);

    }


//      requires: tickerSymbol != null && value != null
//      modifies: this
//      effects: Sets the specified share price
    public void setSharePrice(String tickerSymbol, Double value) {
        //sharePriceLock.lock();
        //try {
           assert(tickerSymbol == null): "The ticker symbol" + "" + tickerSymbol + "" + "" + "must not contain a null value";
           assert(value == null):"The value of" + "" + value + "" + "" + "must not contain a null value";

           sharePrices.put(tickerSymbol, value);
       // } finally {
        //    sharePriceLock.unlock();
       // }

    }


//      requires: portfolio != null
//      modifies: this
//      effects: returns true if portfolio is added else return false.
    public boolean addToPortfolioList(String portfolio) {
        Portfolio pf = getPortfolioBytName(portfolio);
        assert(portfolio == null): "The ticker symbol" + "" + portfolio + "" + "" + "must not contain a null value";
        if (pf == null) {
            Portfolio newPortfolio1 = new Portfolio(portfolio, sharePrices);
            portfolioList.add(newPortfolio1);
            assert(portfolioList.contains(newPortfolio1)): "The portfolio" + newPortfolio1 + "has not been added";
            setChangedAndNotify();
            return true;
        }
        return false;
    }



//      effects: returns portfolio if portfolio's Name == name else return null.

    private Portfolio getPortfolioBytName(String name) {
        for (Portfolio portfolio : portfolioList) {
            if (portfolio.getName().equals(name)) {
                assert(portfolio.getName().equals(name)):"The names" + "" + portfolio.getName() + "and" + name + "" + "are not equal";
                return portfolio;
            }

        }
        return null;
    }


//      modifies: this
//      effects: Updates all the portfolio share prices.
    public void updateShareValues() {
        for (Portfolio portfolio : portfolioList) {
            portfolio.updateShareValues();
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
                portfolioList = ((PortfolioContainer) newStateO.readObject()).getPortfolioList();
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
        Portfolio portToDel = getPortfolioBytName(name);
        assert(name == null): "The name" + "" + name + "" + "must not be null";
        if (portToDel != null) {
            portfolioList.remove(portToDel);
            setChangedAndNotify();
            return true;
        }
        return false;
    }

//      requires: name != null
//      effects: returns true if the List contains this portfolio else return false.
    public boolean containsPortfolio(String name) {
        assert(name == null) : "The name" + ""  +  name + "" + "must not contain an null value";
        return getPortfolioBytName(name) != null;
    }

       // requires: portfolioList is not empty
//      effects: returns the name of each portfolio in the portfolioList.
    public String[] getPortfolioNames() {
        String[] portfolioNames = new String[portfolioList.size()];
        assert(portfolioList.size() > 0): "The portfolio list is empty, it must contain at least one in order to return a portfolio";
        int i = 0;
        for (Portfolio portfolio : portfolioList) {
            portfolioNames[i] = portfolio.getName();
            i++;
        }
        return portfolioNames;
    }

}
